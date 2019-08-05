package com.dunn.playwithandroid.adapter

import android.content.Context
import android.text.Html
import android.widget.ImageView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.bean.project.DatasItem
import com.dunn.playwithandroid.utils.ImageLoader
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
    CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_project_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        with(viewHolder) {
            ImageLoader.load(mContext, data.envelopePic, getView(R.id.projectIv))
            setText(R.id.projectTitleTv, Html.fromHtml(data.title).toString())
            setText(R.id.projectDescTv, data.desc)
            setText(R.id.projectAuthorTv, data.author)
            setText(R.id.projectTimeTv, data.niceDate)

            getView<ImageView>(R.id.projectCollectIv).run {
                if (data.collect) {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                } else {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                }
            }
        }
    }
}