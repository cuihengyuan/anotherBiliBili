package com.example.anotherbilibili.mvp.contract

import com.example.anotherbilibili.base.IBaseView
import com.example.anotherbilibili.mvp.Bean.RecommendBean

interface RecommendContract {
    interface view : IBaseView {
        fun setRecommendData(recommendBean: RecommendBean?)
        fun setMoreData(recommendBean: RecommendBean?)
        fun setTopMoreData(recommendBean: RecommendBean?)
    }

    interface presenter {
        fun requestData()
        fun requestMoreData()
        fun requestTopMoreData()

    }


}