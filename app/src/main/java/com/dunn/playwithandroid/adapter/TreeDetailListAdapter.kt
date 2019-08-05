package com.dunn.playwithandroid.adapter

import android.content.Context
import android.text.Html
import android.widget.ImageView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.bean.treeDetail.DatasItem
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter

/**
 * Created by DunnLin on 2019/7/27.
 */
class TreeDetailListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
    CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_tree_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        with(viewHolder) {
            setText(R.id.articleTitleTv, Html.fromHtml(data.title).toString())
            setText(R.id.articleAuthorTv, data.author)
            setText(R.id.articleTimeTv, data.niceDate)

            getView<ImageView>(R.id.treeArticleCollectIv).run {
                if (data.collect) {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                } else {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                }
            }
        }
    }
}