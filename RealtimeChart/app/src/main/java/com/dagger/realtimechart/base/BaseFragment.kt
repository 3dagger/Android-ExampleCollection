package com.dagger.realtimechart.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding, R : BaseViewModel<*>>: Fragment() {
    lateinit var mActivity: BaseActivity<*, *>

    lateinit var viewDataBinding: T

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract val bindingVariableVM: Int

    abstract val bindingVariableActivity: Int

    abstract fun initView(savedInstanceState : Bundle?)

    abstract fun onProcess()

    abstract fun onViewModelCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.mActivity = context
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)

        viewDataBinding.setVariable(bindingVariableVM, viewModel)
        viewDataBinding.setVariable(bindingVariableActivity, mActivity)
        viewDataBinding.lifecycleOwner = this

        return viewDataBinding.root
    }

    override fun onDetach() {
        onViewModelCleared()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(savedInstanceState)
        onProcess()
    }

    fun getBaseActivity(): BaseActivity<*, *> {
        return mActivity
    }
}