package com.kenetic.savepass.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenetic.savepass.databinding.FragmentPassListBinding

class PassListFragment : Fragment() {
    private lateinit var binding: FragmentPassListBinding

    private var isLinear = true//todo - change this to be initiated from preferences
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPassListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.passwordRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }

    private fun changeLayout() {
        isLinear = !isLinear
        setLayoutAndIcon(isLinear)
    }

    private fun setLayoutAndIcon(isLinear: Boolean) {//this is used as the menu icon
        if (isLinear) {
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            //todo - set menu icon
            //todo - store preference
        } else {
            recyclerView.layoutManager = GridLayoutManager(this.requireContext(),2)
            //todo - set menu icon
            //todo - store preference
        }
    }
}