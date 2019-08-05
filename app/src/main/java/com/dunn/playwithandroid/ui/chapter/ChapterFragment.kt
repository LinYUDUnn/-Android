package com.dunn.playwithandroid.ui.chapter

import androidx.recyclerview.widget.GridLayoutManager
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.adapter.ChapterAdapter
import com.dunn.playwithandroid.base.fragment.BaseMvpFragment
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.ChapterBean
import com.dunn.playwithandroid.ui.chapter.chapterDetail.ChapterDetailActivity
import kotlinx.android.synthetic.main.fragment_chapter.*

/**
 * Created by DunnLin on 2019/7/27.
 */
class ChapterFragment : BaseMvpFragment<ChapterPresenterImpl>(), ChapterContract.View {

    private lateinit var chapterAdapter: ChapterAdapter

    companion object {
        fun newInstance() = ChapterFragment()
    }

    override fun initPresenter(): ChapterPresenterImpl {
        return ChapterPresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_chapter
    }

    override fun initData() {
    }

    override fun initView() {
        chapterAdapter = ChapterAdapter(context, null, false)
        chapterAdapter.setOnItemClickListener { _, data, _ ->
            ChapterDetailActivity.start(mContext, data.name, data.id)
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        chapterRv.layoutManager = gridLayoutManager
        chapterRv.adapter = chapterAdapter

        initStatusView(chapterRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getChapter()
    }

    override fun onChapterSuccess(data: List<ChapterBean>) {
        statusView.showContentView()
        chapterAdapter.setNewData(data)
    }

    override fun onChapterError(e: ResponseException) {
        statusView.showErrorView()
    }
}