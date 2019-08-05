package com.dunn.playwithandroid.ui.website

import android.annotation.SuppressLint
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.FriendBean
import com.dunn.playwithandroid.ui.article.ArticleActivity
import com.dunn.playwithandroid.utils.addCommonView
import kotlinx.android.synthetic.main.fragment_hot_website.*

/**
 * Created by DunnLin on 2019/7/29.
 */
class HotWebsiteFragment : BaseMvpFragment<HotWebsitePresenterImpl>(), HotWebsiteContract.View {
    companion object {
        fun newInstance() = HotWebsiteFragment()
    }

    override fun initPresenter(): HotWebsitePresenterImpl {
        return HotWebsitePresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_hot_website
    }

    override fun initData() {

    }

    override fun initView() {
        initStatusView(hotWebsiteHomeLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getFriendData()
    }

    @SuppressLint("ResourceType")
    override fun onFriedSuccess(data: List<FriendBean>) {
        statusView.showContentView()
        for (website in data) {
            websiteFL.addCommonView(mContext, website.name, R.color.c2C2C2C, R.drawable.website_selecter) {
                ArticleActivity.start(mContext, website.name, website.link)
            }
        }
    }

    override fun onFriendError(e: ResponseException) {
        statusView.showErrorView()
    }
}