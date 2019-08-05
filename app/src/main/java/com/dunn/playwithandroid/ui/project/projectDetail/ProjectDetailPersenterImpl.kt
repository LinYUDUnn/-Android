package com.dunn.playwithandroid.ui.project.projectDetail

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.base.net.observer.LoadingObserver
import com.dunn.playwithandroid.bean.project.ProjectBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectDetailPersenterImpl(view: ProjectDetailContract.View) : BasePresenter<ProjectDetailContract.View>(view),
    ProjectDetailContract.Presenter {
    override fun getNewProjectList(pageNum: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).newProject(pageNum),
            object : BaseObserver<ProjectBean>() {
                override fun onSuccess(data: ProjectBean) {
                    view.onNewProjectListSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onNewProjectListError(e)
                }

            })
    }

    override fun getProjectDetail(pageNum: Int, cid: Int) {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).projectDetail(pageNum, cid),
            object : BaseObserver<ProjectBean>() {
                override fun onSuccess(data: ProjectBean) {
                    view.onProjectDetailSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onProjectDetailError(e)
                }

            })
    }

    override fun collect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id),
            object : LoadingObserver<String>(context, false, true) {
                override fun onSuccess(data: String) {
                    view.onCollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onCollectError(e)
                }
            })
    }

    override fun uncollect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
            object : LoadingObserver<String>(context, false, true) {
                override fun onSuccess(data: String) {
                    view.onUncollectSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onUncollectError(e)
                }
            })
    }
}