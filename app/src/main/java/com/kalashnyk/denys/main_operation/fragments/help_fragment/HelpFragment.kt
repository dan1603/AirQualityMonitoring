package com.kalashnyk.denys.main_operation.fragments.help_fragment

import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.HelpDataBinding
import com.kalashnyk.denys.main_operation.base.BaseFragment


class HelpFragment : BaseFragment<HelpDataBinding>() {

    private val viewModel: HelpViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_help

    override fun setupViewLogic(binder: HelpDataBinding) {

    }
}