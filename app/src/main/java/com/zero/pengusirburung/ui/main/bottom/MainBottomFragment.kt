package com.zero.pengusirburung.ui.main.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.zero.pengusirburung.R
import com.zero.pengusirburung.data.DataItemGraph
import com.zero.pengusirburung.databinding.FragmentMainBottomBinding

class MainBottomFragment(
    val data: List<DataItemGraph>
) : SuperBottomSheetFragment() {
    val binding: FragmentMainBottomBinding by viewBinding()
    val items: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data.forEach {
            items.add(it.name.toString())
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.menuTower.editText as? AutoCompleteTextView)?.setAdapter(adapter)
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