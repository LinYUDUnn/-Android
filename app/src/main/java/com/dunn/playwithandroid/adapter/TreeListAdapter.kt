package com.dunn.playwithandroid.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.bean.tree.ChildrenItem
import com.dunn.playwithandroid.bean.tree.TreeBean
import com.dunn.playwithandroid.utils.CommonUtil
import com.google.android.flexbox.FlexboxLayout
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter

/**
 * Created by DunnLin on 2019/7/27.
 */
class TreeListAdapter(context: Context?, data: List<TreeBean>?, isOpenLoadMore: Boolean) :
    CommonBaseAdapter<TreeBean>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_tree_layout
    }

    override fun convert(viewHolder: ViewHolder, data: TreeBean, position: Int) {
        with(viewHolder) {
            setText(R.id.treeTitleTv, data.name)
            setTreeData(mContext, getView(R.id.treeFL), data.children)
        }
    }

    private fun setTreeData(context: Context, fl: FlexboxLayout, data: List<ChildrenItem>) {
        fl.removeAllViews()
        for (tree in data) {
            val view = TextView(mContext)
            view.text = tree.name
            view.setTextColor(context.resources.getColor(R.color.c8A8A8A))
            val params =
                FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 3)
            val margin2 = CommonUtil.dp2px(mContext, 15)
            params.setMargins(0, margin1, margin2, margin1)
            fl.addView(view, params)
        }
    }
}