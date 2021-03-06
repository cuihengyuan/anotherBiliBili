package com.example.anotherbilibili.mvp.presenter

import android.annotation.SuppressLint
import com.example.anotherbilibili.base.BasePresenter
import com.example.anotherbilibili.mvp.Bean.NewRecommendBean
import com.example.anotherbilibili.mvp.model.RecommendmModel
import com.example.anotherbilibili.mvp.contract.RecommendContract
import kotlin.Int as Int1

/**
 * 首页推荐页面的presenter
 */
class RecommendPresenter : BasePresenter<RecommendContract.view>(), RecommendContract.presenter {

    var newRecommendBean: NewRecommendBean? = null

    val recommendmModel by lazy {
        RecommendmModel()
    }


    @SuppressLint("CheckResult")
    override fun requestData(count: kotlin.Int) {
        finalView?.showIsLoading()
        val disposable = recommendmModel.resquestRecommendData(count).subscribe {
            newRecommendBean = it
            finalView?.removeLoading()
            finalView?.setRecommendData(newRecommendBean)

        }
        addSubscription(disposable)
    }


    @SuppressLint("CheckResult")
    override fun requestMoreData(count: kotlin.Int) {

        val disposable = recommendmModel.resquestMoreRecommendData(count).subscribe {
            finalView?.setMoreData(it)

        }
        addSubscription(disposable)
    }


    @SuppressLint("CheckResult")
    override fun requestTopMoreData(count: kotlin.Int) {

        recommendmModel.resquestMoreRecommendData(count).subscribe {
            finalView?.setTopMoreData(it)
        }

    }


}