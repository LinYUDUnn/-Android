package com.dunn.playwithandroid.ui.main

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ViewPagerAdapter
import com.dunn.playwithandroid.base.activity.BaseMvpActivity
import com.dunn.playwithandroid.base.fragment.BaseFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.event.AccountEvent
import com.dunn.playwithandroid.ui.chapter.ChapterFragment
import com.dunn.playwithandroid.ui.collection.MyCollectionActivity
import com.dunn.playwithandroid.ui.home.HomeFragment
import com.dunn.playwithandroid.ui.login.LoginActivity
import com.dunn.playwithandroid.ui.nav.NavFragment
import com.dunn.playwithandroid.ui.project.ProjectFragment
import com.dunn.playwithandroid.ui.query.QueryActivity
import com.dunn.playwithandroid.ui.tree.TreeFragment
import com.dunn.playwithandroid.ui.widget.BottomTabLayout
import com.dunn.playwithandroid.ui.widget.LogoutDialog
import com.dunn.playwithandroid.utils.ToastUtil
import com.dunn.playwithandroid.utils.sp.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseMvpActivity<MainPresenterImpl>(), MainContract.View {

    private var isBackPress: Boolean = false

    private lateinit var userNameTv: TextView

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): MainPresenterImpl {
        return MainPresenterImpl(this)
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        mainMenuIv.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        mainQueryIv.setOnClickListener { QueryActivity.start(this) }

        userNameTv = navigationView.getHeaderView(0).findViewById(R.id.usernameTv)
        userNameTv.text = SpUtil.getUsername()
        userNameTv.setOnClickListener {
            LoginActivity.start(this)
        }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_collect -> collection()
                R.id.nav_item_setting -> setting()
                R.id.nav_item_about -> about()
                R.id.nav_item_logout -> logout()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        val fragments = arrayListOf<BaseFragment>().apply {
            add(HomeFragment.newInstance())
            add(ProjectFragment.newInstance())
            add(TreeFragment.newInstance())
            add(NavFragment.newInstance())
            add(ChapterFragment.newInstance())
        }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.setFragments(fragments)
        mainViewpager.adapter = viewPagerAdapter
        mainViewpager.offscreenPageLimit = fragments.size


        mainBottomTabLayout.run {

            setupWithViewPager(mainViewpager)
            setOnTabSwitchListener(object : BottomTabLayout.OnTabSwitchListener {
                override fun onTabSwitch(tabIndex: Int, tabName: String) {
                    tabNameTv.text = tabName
                }

            })

            addTab(getString(R.string.home), R.drawable.ic_homepage, R.drawable.ic_homepage_fill)
            addTab(getString(R.string.project), R.drawable.ic_createtask, R.drawable.ic_createtask_fill)
            addTab(getString(R.string.tree), R.drawable.ic_manage, R.drawable.ic_manage_fill)
            addTab(getString(R.string.nav), R.drawable.ic_coordinates, R.drawable.ic_coordinates_fill)
            addTab(getString(R.string.chapter), R.drawable.ic_select, R.drawable.ic_select_fill)
        }
    }

    override fun initLoad() {
        EventBus.getDefault().register(this)
    }

    override fun onLogoutSuccess(data: String) {
        SpUtil.removeCookies()
        SpUtil.removeUsername()
        userNameTv.text = SpUtil.getUsername()
        ToastUtil.show(context, R.string.logout_tip)
    }

    override fun onLogoutError(e: ResponseException) {
    }

    private fun collection() {
        if (SpUtil.getCookies().isEmpty()) {
            ToastUtil.show(context, R.string.login_tip)
            return
        }
        MyCollectionActivity.start(context)
    }

    private fun setting() {
        ToastUtil.show(context, R.string.setting_tip)
    }

    private fun about() {
        //AboutActivity.start(this)
    }

    private fun logout() {
        if (SpUtil.getCookies().isEmpty()) {
            ToastUtil.show(context, R.string.login_tip)
            return
        }

        LogoutDialog.show(supportFragmentManager, object : LogoutDialog.OnLogoutListener {
            override fun logout() {
                presenter.logout()
            }
        })

    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (isBackPress) {
                super.onBackPressed()
                return
            }
            isBackPress = true
            ToastUtil.show(context, R.string.exit_tip)
            Handler().postDelayed({ isBackPress = false }, 2000)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAccountEvent(event: AccountEvent) {
        userNameTv.text = SpUtil.getUsername()
    }
}
