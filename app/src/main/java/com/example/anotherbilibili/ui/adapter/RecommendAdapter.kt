package com.example.anotherbilibili.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import cn.leancloud.AVException
import cn.leancloud.AVObject
import cn.leancloud.AVUser
import com.example.anotherbilibili.MyViewHolder
import com.example.anotherbilibili.R
import com.example.anotherbilibili.base.BaseAdapter
import com.example.anotherbilibili.mvp.Bean.NewRecommendBean
import com.example.anotherbilibili.mvp.Bean.RecommendBean
import com.example.anotherbilibili.ui.activity.VideoAcitvity
import org.jetbrains.anko.startActivity



/**
 *
 * 推荐页面adapter
 */
class RecommendAdapter(
    mContext: Context, mData: ArrayList<NewRecommendBean.Result>,
    private var itemLayoutId: Int
) : BaseAdapter<NewRecommendBean.Result>(mContext, mData, itemLayoutId) {


    override fun bindData(holder: MyViewHolder, data: NewRecommendBean.Result, position: Int) {
        with(holder) {
            setText(R.id.tv_title, data.tiltle)
            setImagePath(R.id.im_item, data.bimageuri)
            setText(R.id.tv_author, data.name)
            setImagePath(R.id.im_mini, data.profileImage)
            holder.setOnItemClickListener(View.OnClickListener {

                //   (mContext as Activity).startActivity<VideoAcitvity>(pair)
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.im_item), data)
            })
        }


    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: NewRecommendBean.Result) {
        val intent = Intent(activity, VideoAcitvity::class.java)
        intent.putExtra("recommendData", itemData)
        intent.putExtra(VideoAcitvity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pairView = androidx.core.util.Pair(view, VideoAcitvity.TRANSITIONVIEW)
            //  val pairUrl  = Pair("url", itemData.videouri)

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pairView
            )
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }

}