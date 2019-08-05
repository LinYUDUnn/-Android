package com.dunn.playwithandroid.ui.nav

import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.fragment.BaseFragment
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.navi.ArticlesItem
import com.dunn.playwithandroid.bean.navi.NaviBean
import com.dunn.playwithandroid.ui.website.HotWebsiteFragment
import com.dunn.playwithandroid.ui.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_navi.*

/**
 * Created by DunnLin on 2019/7/27.
 */
class NavFragment : BaseMvpFragment<NavPresenterImpl>(), NavContract.View {

    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    companion object {
        fun newInstance() = NavFragment()
    }

    override fun initPresenter(): NavPresenterImpl {
        return NavPresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_navi
    }

    override fun initData() {
    }

    override fun initView() {
        naviTabLayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager?.beginTransaction()
                    ?.hide(fragments[oldTabIndex])
                    ?.show(fragments[newTabIndex])
                    ?.commit()
            }
        })

        initStatusView(navRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.nav()
    }

    override fun onNavSuccess(data: List<NaviBean>) {
        statusView.showContentView()
        val tabNames = arrayListOf<String>()
        tabNames.add(getString(R.string.hot_website))
        fragments.add(HotWebsiteFragment.newInstance())
        for (navBean in data) {
            tabNames.add(navBean.name)
            fragments.add(NavDetailFragment.newInstance(navBean.articles as ArrayList<ArticlesItem>))
        }
        naviTabLayout.addTabs(tabNames)
        initDetailFragment()
    }

    override fun onNavError(e: ResponseException) {
    }

    private fun initDetailFragment() {
        val transition = fragmentManager!!.beginTransaction()
        for (f in fragments) {
            transition.add(R.id.naviDetailContainer, f).hide(f)
        }
        transition.show(fragments[0])
        transition.commit()
    }
}