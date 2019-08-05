package com.dunn.playwithandroid.ui.chapter.chapterDetail

import android.content.Intent
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ChapterDetailListAdapter
import com.dunn.playwithandroid.base.activity.BaseActivity
import com.dunn.playwithandroid.base.activity.BaseMvpActivity
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.chapter.ChapterArticleBean
import com.dunn.playwithandroid.bean.chapter.DatasItem
import com.dunn.playwithandroid.ui.article.ArticleActivity
import com.dunn.playwithandroid.ui.widget.DividerItemDecoration
import com.dunn.playwithandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_chapter_detail.*
import kotlinx.android.synthetic.main.floating_button_layout.*

class ChapterDetailActivity : BaseMvpActivity<ChapterDetailPresenterImpl>(), ChapterDetailContract.View {

    private var pageNum: Int = 0
    private var chapterId: Int = 0
    private lateinit var title: String

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

    private var queryPageNum: Int = 0
    private lateinit var keyWord: String
    private lateinit var queryResultAdapter: ChapterDetailListAdapter
    private var isInitQuery: Boolean = false
    // 搜索结果是否为空
    private var isEmpty: Boolean = false

    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    companion object {
        fun start(context: BaseActivity, title: String, chapterId: Int) {
            val intent = Intent(context, ChapterDetailActivity::class.java)
            intent.apply {
                putExtra("chapterId", chapterId)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): ChapterDetailPresenterImpl {
        return ChapterDetailPresenterImpl(this)
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_chapter_detail
    }

    override fun initData() {
        intent?.let {
            title = it.getStringExtra("title")
            chapterId = it.getIntExtra("chapterId", 0)
        }
    }

    override fun initView() {
        initToolbar(title)
        initChapterList()
        initQueryChapterList()

        initStatusView(R.id.contentFl) {
            initLoad()
        }
    }

    /**
     * 文章列表初始化
     */
    private fun initChapterList() {
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            chapterDetailRv.smoothScrollToPosition(0)
        }

        chapterDetailListAdapter = ChapterDetailListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(context, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }
            setOnLoadMoreListener {
                presenter.getChapterArticleList(chapterId, pageNum)
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        chapterDetailRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = chapterDetailListAdapter
            // 控制悬浮按钮
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findLastVisibleItemPosition() > 10) {
                        floatBtn.show()
                    } else {
                        floatBtn.hide()
                    }
                }
            })
        }
    }

    /**
     * 搜索列表初始化
     */
    private fun initQueryChapterList() {
        queryResultAdapter = ChapterDetailListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(context, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }
            setOnLoadMoreListener {
                presenter.queryChapterArticle(chapterId, pageNum, keyWord)
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        queryChapterRv.layoutManager = linearLayoutManager
        queryChapterRv.addItemDecoration(DividerItemDecoration())
        queryChapterRv.adapter = queryResultAdapter
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getChapterArticleList(chapterId, pageNum)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.run {
            // 是否显示提交按钮
            isSubmitButtonEnabled = false
            // 搜索框是否展开,false表示展开
            isIconified = true
            queryHint = getString(R.string.query_tip)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(content: String): Boolean {
                    searchView.clearFocus()
                    keyWord = content
                    if (!keyWord.isEmpty()) {
                        queryPageNum = 0
                        isInitQuery = true
                        presenter.queryChapterArticle(chapterId, queryPageNum, keyWord)
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })

            // 关闭搜索
            setOnCloseListener {
                isEmpty = false
                statusView.showContentView()
                queryChapterRv.visibility = View.GONE
                return@setOnCloseListener false
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onChapterArticleListSuccess(data: ChapterArticleBean) {
        if (pageNum == 0) {
            statusView.showContentView()
            chapterDetailListAdapter.setNewData(data.datas)
        } else {
            chapterDetailListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            chapterDetailListAdapter.loadEnd()
        }
    }

    override fun onChapterArticleListError(e: ResponseException) {
        if (pageNum == 0) {
            statusView.showErrorView()
        } else {
            chapterDetailListAdapter.loadFailed()
        }
    }

    override fun onQueryChapterArticleListSuccess(data: ChapterArticleBean) {
        if (queryChapterRv.visibility == View.GONE) {
            queryChapterRv.visibility = View.VISIBLE
        }

        if (isInitQuery) {
            isInitQuery = false
            queryChapterRv.scrollToPosition(0)
            queryResultAdapter.reset()
        }

        if (queryPageNum == 0) {
            if (data.datas.isEmpty()) {
                isEmpty = true
                statusView.showEmptyView()
                return
            } else if (isEmpty) {
                statusView.showContentView()
            }
            queryResultAdapter.setNewData(data.datas)
        } else {
            queryResultAdapter.setLoadMoreData(data.datas)
        }
        queryPageNum++
        if (queryPageNum == data.pageCount) {
            queryResultAdapter.loadEnd()
        }
    }

    override fun onQueryChapterArticleListError(e: ResponseException) {
        queryResultAdapter.loadEnd()
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.show(context, R.string.collect_success)
    }

    override fun onCollectError(e: ResponseException) {
    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.show(context, R.string.uncollect_success)
    }

    override fun onUncollectError(e: ResponseException) {
    }

}
