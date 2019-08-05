package com.dunn.playwithandroid.ui.tree.treeDetail


import android.content.Intent
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ViewPagerAdapter
import com.dunn.playwithandroid.base.activity.BaseActivity
import com.dunn.playwithandroid.base.fragment.BaseFragment
import com.dunn.playwithandroid.bean.tree.ChildrenItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tree_detail.*

class TreeDetailActivity : BaseActivity() {

    private lateinit var title: String
    private lateinit var secondaryTree: ArrayList<ChildrenItem>

    companion object {
        fun start(context: BaseActivity, title: String, secondaryTree: ArrayList<ChildrenItem>) {
            val intent = Intent(context, TreeDetailActivity::class.java)
            intent.apply {
                putExtra("title", title)
                putParcelableArrayListExtra("secondaryTree", secondaryTree)
            }
            context.startActivity(intent)
        }
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_tree_detail
    }

    override fun initData() {
        intent?.let {
            title = it.getStringExtra("title")
            secondaryTree = it.getParcelableArrayListExtra("secondaryTree")
        }
    }

    override fun initView() {
        initToolbar(title)

        val titles = arrayListOf<String>()
        val fragments = arrayListOf<BaseFragment>()
        for (tree in secondaryTree) {
            titles.add(tree.name)
            fragments.add(TreeDetailFragment.newInstance(tree.id))
        }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.setFragmentsAndTitles(fragments, titles)
        treeDetailViewPager.offscreenPageLimit = secondaryTree.size
        treeDetailViewPager.adapter = viewPagerAdapter
        if (titles.size > 3) {
            treeDetailTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }
        treeDetailTabLayout.setupWithViewPager(treeDetailViewPager)
    }


    override fun initLoad() {
    }


}
