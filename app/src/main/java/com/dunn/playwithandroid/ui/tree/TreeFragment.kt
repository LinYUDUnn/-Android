package com.dunn.playwithandroid.ui.tree

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.TreeListAdapter
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.tree.TreeBean
import com.dunn.playwithandroid.ui.tree.treeDetail.TreeDetailActivity
import com.dunn.playwithandroid.ui.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_tree.*

/**
 * Created by DunnLim on 2019/7/27.
 */
class TreeFragment : BaseMvpFragment<TreePresenterImpl>(), TreeContract.View {

    private lateinit var treeAdapter: TreeListAdapter

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun initPresenter(): TreePresenterImpl {
        return TreePresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree
    }

    override fun initData() {
    }

    override fun initView() {
        treeAdapter = TreeListAdapter(context, null, false)
        treeAdapter.setOnItemClickListener { _, data, _ ->
            TreeDetailActivity.start(mContext, data.name, data.children)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        treeRv.layoutManager = linearLayoutManager
        treeRv.addItemDecoration(
            DividerItemDecoration()
                .setDividerHeight(20)
                .setDividerColor(resources.getColor(R.color.cEEEEF5))
        )
        treeRv.adapter = treeAdapter

        initStatusView(treeRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getTree()
    }

    override fun onTreeSuccess(data: List<TreeBean>) {
        statusView.showContentView()
        treeAdapter.setNewData(data)
    }

    override fun onTreeError(e: ResponseException) {
        statusView.showErrorView()
    }
}