package com.example.anotherbilibili.ui.fragment

import android.os.Bundle
import com.example.anotherbilibili.R
import com.example.anotherbilibili.base.baseFragment

class AnimationFragment: baseFragment() {
    companion object {
        fun getInstance(): AnimationFragment {
            val fragment =AnimationFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }
    override fun getLayoutId(): Int= R.layout.fragment_animation
}