package com.kalashnyk.denys.main_operation.fragments.main_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_1.setOnClickListener {
            val action = MainFragmentDirections.actionListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        button_2.setOnClickListener {
            val action = MainFragmentDirections.actionListFilesFragment()
            Navigation.findNavController(it).navigate(action)
        }

        button_3.setOnClickListener {
            val action = MainFragmentDirections.dialogsAction()
            Navigation.findNavController(it).navigate(action)
        }
    }

}