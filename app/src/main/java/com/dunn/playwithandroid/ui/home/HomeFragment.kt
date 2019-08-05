package com.dunn.playwithandroid.ui.home

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ArticleListAdapter
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.BannerBean
import com.dunn.playwithandroid.bean.article.ArticleBean
import com.dunn.playwithandroid.bean.article.DatasItem
import com.dunn.playwithandroid.ui.article.ArticleActivity
import com.dunn.playwithandroid.ui.widget.BannerImageLoader
import com.dunn.playwithandroid.ui.widget.DividerItemDecoration
import com.dunn.playwithandroid.utils.ToastUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.floating_button_layout.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by DunnLin on 2019/7/27.
 */
class HomeFragment : BaseMvpFragment<HomePresenterImpl>(), HomeContract.View {

    private var pageNum: Int = 0
    private lateinit var articleListAdapter: ArticleListAdapter

    private lateinit var bannerBeans: List<BannerBean>
    private lateinit var banner: Banner

    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }

    override fun initView() {
        banner = LayoutInflater.from(context).inflate(R.layout.home_banner_layout, homeRootLayout, false) as Banner
        banner.run {
            setImageLoader(BannerImageLoader())
            setDelayTime(3000)
            setIndicatorGravity(BannerConfig.RIGHT)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setOnBannerListener {
                ArticleActivity.start(mContext, bannerBeans[it].title, bannerBeans[it].url)
            }
        }

        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            articleRv.smoothScrollToPosition(0)
        }

        articleListAdapter = ArticleListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)
            addHeaderView(banner)

            setOnItemClickListener { viewHolder, data, i ->
                ArticleActivity.start(mContext, data.title, data.link)
            }

            setOnItemChildClickListener(R.id.articleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }

            setOnLoadMoreListener {
                presenter.getArticleList(pageNum)
            }
        }

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        articleRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = articleListAdapter
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

        initStatusView(homeRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getArticleList(pageNum)
        presenter.getBannerData()
    }

    override fun onBannerSuccess(data: List<BannerBean>) {
        statusView.showContentView()
        bannerBeans = data
        val images = arrayListOf<String>()
        val titles = arrayListOf<String>()
        for (bannerBean in data) {
            images.add(bannerBean.imagePath)
            titles.add(bannerBean.title)
        }
        banner.run {
            setImages(images)
            setBannerTitles(titles)
            start()
        }
    }

    override fun onBannerError(e: ResponseException) {
        statusView.showErrorView()
    }

    override fun onArticleListSuccess(data: ArticleBean) {
        if (pageNum == 0) {
            articleListAdapter.setNewData(data.datas)
        } else {
            articleListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            articleListAdapter.loadEnd()
            return
        }
    }

    override fun onArticleListError(e: ResponseException) {
        articleListAdapter.loadFailed()
    }

    override fun initPresenter(): HomePresenterImpl {
        return HomePresenterImpl(this)
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        articleListAdapter.change(collectPosition + 1)
        ToastUtil.show(mContext, R.string.collect_success)

    }

    override fun onCollectError(e: ResponseException) {
    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        articleListAdapter.change(collectPosition + 1)
        ToastUtil.show(mContext, R.string.uncollect_success)
    }

    override fun onUncollectError(e: ResponseException) {
    }

}