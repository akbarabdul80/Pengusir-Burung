package com.zero.pengusirburung.ui.main

import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.visible
import com.zero.pengusirburung.data.DataItemGraph
import com.zero.pengusirburung.databinding.ActivityMainBinding
import com.zero.pengusirburung.root.RootViewModel
import com.zero.pengusirburung.ui.main.adapter.GraphAdapter
import com.zero.pengusirburung.ui.main.bottom.MainBottomFragment
import com.zero.zerobase.presentation.viewbinding.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>(), ValueEventListener {

    private lateinit var database: DatabaseReference
    private val adapterData = mutableListOf<DataItemGraph>()
    private val viewModel by viewModels<RootViewModel>()

    private val adapter: GraphAdapter by lazy {
        GraphAdapter(
            viewModel
        )
    }


    override fun initData() {
        super.initData()
        database = Firebase.database.reference
        viewModel.setDataTotal(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        viewModel.setDataDay(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvData.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            fab.onClick {
                if (listTower.isNotEmpty()) {
                    MainBottomFragment.newInstance(listTower)
                        .show(supportFragmentManager, "Bottom User")
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        database.addValueEventListener(this)

        viewModel.dataDay.observe(this) {
            binding.tvToday.text = it.sum().toString()
            Log.e("Total Day", it.toString())
        }

        viewModel.dataTotal.observe(this) {
            Log.e("Total Observe", it.toString())
            binding.tvTotal.text = it.sum().toString()
        }
    }

    var listTower: MutableList<DataItemGraph> = mutableListOf()

    override fun onDataChange(snapshot: DataSnapshot) {
        adapterData.clear()
        snapshot.children.forEach {
            adapterData.add(it.getValue(DataItemGraph::class.java)!!)
            listTower.add(it.getValue(DataItemGraph::class.java)!!)
            Log.e("Data", it.getValue(DataItemGraph::class.java)?.id.toString())
        }

        adapter.setNewInstance(adapterData)

        binding.tvToday.visible()
        binding.tvTotal.visible()

        binding.avToday.gone()
        binding.avKas.gone()

        database.removeEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) {
    }
}