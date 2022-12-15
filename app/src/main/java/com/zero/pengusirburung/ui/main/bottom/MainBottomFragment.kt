package com.zero.pengusirburung.ui.main.bottom

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.toast
import com.zero.pengusirburung.R
import com.zero.pengusirburung.data.DataItemGraph
import com.zero.pengusirburung.databinding.FragmentMainBottomBinding
import dmax.dialog.SpotsDialog

class MainBottomFragment(
    val data: List<DataItemGraph>
) : SuperBottomSheetFragment() {
    private lateinit var database: DatabaseReference

    val binding: FragmentMainBottomBinding by viewBinding()
    val items: MutableList<String> = mutableListOf()
    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    private val spotsDialog: SpotsDialog by lazy {
        SpotsDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database.reference

        data.forEach {
            items.add(it.name.toString())
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.menuTower.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.btnOk.onClick {
            if (binding.menuTower.editText?.text.toString().isNotEmpty()) {
                spotsDialog.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    database.child(id).child("trigger").setValue(true)
                    checkValue()
                }, 10000)
            }
        }

        binding.ddTower.setOnItemClickListener { _, _, position, _ ->
            id = data[position].id.toString()
        }
    }

    private fun checkValue() {

        val listener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val value = dataSnapshot.getValue(Boolean::class.java)
                    if (value == false) {
                        spotsDialog.dismiss()
                        toast("Berhasil Trigger")
                        dismiss()
                        database.child(id).child("trigger").removeEventListener(this)
                    } else {
                        spotsDialog.dismiss()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                spotsDialog.dismiss()
                toast("Gagal")
            }
        }

        database.child(id).child("trigger").addValueEventListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance(data: List<DataItemGraph>) = MainBottomFragment(data)
    }

    override fun getExpandedHeight(): Int {
        return -2
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }
}