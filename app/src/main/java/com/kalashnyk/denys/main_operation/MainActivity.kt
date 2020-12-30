package com.kalashnyk.denys.main_operation

import com.example.myapplication.R
import com.example.myapplication.databinding.ContainerDataBinding
import com.kalashnyk.denys.main_operation.base.BaseActivity

class MainActivity : BaseActivity<ContainerDataBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun setupViewLogic(binder: ContainerDataBinding) {

    }

}
