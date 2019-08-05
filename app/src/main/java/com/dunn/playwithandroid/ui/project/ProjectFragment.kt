package com.dunn.playwithandroid.ui.project

import android.text.Html
import android.view.View
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ViewPagerAdapter
import com.dunn.playwithandroid.base.fragment.BaseFragment
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.ProjectCategoryBean
import com.dunn.playwithandroid.ui.project.projectDetail.ProjectDetailFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectFragment : BaseMvpFragment<ProjectPresenterImpl>(), ProjectContract.View {

    override fun onProjectCategorySuccess(data: List<ProjectCategoryBean>) {
        statusView.showContentView()
        projectTabLayout.visibility = View.VISIBLE

        val titles = arrayListOf<String>()
        val fragments = arrayListOf<BaseFragment>()
        titles.add(getString(R.string.new_project))
        fragments.add(ProjectDetailFragment.newInstance(-1))
        for (category in data) {
            titles.add(Html.fromHtml(category.name).toString())
            fragments.add(ProjectDetailFragment.newInstance(category.id))
        }

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.setFragmentsAndTitles(fragments, titles)
        projectViewPager.offscreenPageLimit = data.size + 1
        projectViewPager.adapter = viewPagerAdapter
        projectTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        projectTabLayout.setupWithViewPager(projectViewPager)
    }

    override fun onProjectCategoryError(e: ResponseException) {
    }

    override fun initPresenter(): ProjectPresenterImpl {
        return ProjectPresenterImpl(this)
    }

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {
    }

    override fun initView() {
        initStatusView(R.id.projectViewPager) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getProjectCategory()
    }
}