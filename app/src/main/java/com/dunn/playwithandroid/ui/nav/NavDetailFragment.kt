package com.dunn.playwithandroid.ui.nav

import android.os.Bundle
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.fragment.BaseFragment
import com.dunn.playwithandroid.bean.navi.ArticlesItem
import com.dunn.playwithandroid.ui.article.ArticleActivity
import com.dunn.playwithandroid.utils.addCommonView
import kotlinx.android.synthetic.main.fragment_navi_detail.*

private const val ARTICLES = "articles"

/**
 * Created by DunnLin on 2019/7/27.
 */
class NavDetailFragment : BaseFragment() {

    private lateinit var articles: ArrayList<ArticlesItem>

    companion object {
        fun newInstance(param: ArrayList<ArticlesItem>) =
            NavDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARTICLES, param)
                }
            }
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_navi_detail
    }

    override fun initData() {
        arguments?.let {
            articles = it.getParcelableArrayList<ArticlesItem>(ARTICLES)
        }
    }

    override fun initView() {
        for (website in articles) {
            navDetailFL.addCommonView(mContext, website.title, R.color.c2C2C2C, R.drawable.website_selecter) {
                ArticleActivity.start(mContext, website.title, website.link)
            }
        }
    }

    override fun initLoad() {
    }
}