package com.dunn.playwithandroid.ui.collection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.CollectionListAdapter
import com.dunn.playwithandroid.base.activity.BaseMvpActivity
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.article.ArticleBean
import com.dunn.playwithandroid.ui.article.ArticleActivity
import com.dunn.playwithandroid.ui.widget.DividerItemDecoration
import com.dunn.playwithandroid.ui.widget.WrapLinearLayoutManager
import com.dunn.playwithandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_my_collection.*

class MyCollectionActivity : BaseMvpActivity<MyCollectPrsenterImpl>(), MyCollectContract.View {

    private var pageNum: Int = 0

    private lateinit var collectionListAdapter: CollectionListAdapter
    private var collectPosition: Int = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyCollectionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCollectionListSuccess(data: ArticleBean) {
        if (pageNum == 0) {
            statusView.showContentView()
            collectionListAdapter.setNewData(data.datas)
        } else {
            collectionListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++;
        if (pageNum == data.pageCount) {
            collectionListAdapter.loadEnd()
            return
        }
    }

    override fun onCollectionListError(e: ResponseException) {
        if (pageNum == 0) {
            statusView.showErrorView()
        } else {
            collectionListAdapter.loadFailed()
        }
    }

    override fun onCancelCollectionSuccess(data: String) {
        collectionListAdapter.remove(collectPosition)
        ToastUtil.show(context, R.string.uncollect_success)
    }

    override fun onCancelCollectionError(e: ResponseException) {
    }

    override fun initPresenter(): MyCollectPrsenterImpl {
        return MyCollectPrsenterImpl(this)
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_my_collection
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar(R.string.collect)
        collectionListAdapter = CollectionListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { viewHolder, data, i ->
                ArticleActivity.start(context, data.title, data.link)
            }

            setOnItemChildClickListener(R.id.articleCollectIv) { _, data, position ->
                collectPosition = position
                presenter.cancelCollection(data.id, data.originId)
            }

            setOnLoadMoreListener {
                presenter.getCollectionList(pageNum)
            }
        }

        val linearLayoutManager = WrapLinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        collectionRv.layoutManager = linearLayoutManager
        collectionRv.addItemDecoration(DividerItemDecoration())
        collectionRv.adapter = collectionListAdapter

        initStatusView(R.id.collectionRv) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getCollectionList(pageNum)
    }

}
