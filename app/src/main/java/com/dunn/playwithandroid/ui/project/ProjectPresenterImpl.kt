package com.dunn.playwithandroid.ui.project

import com.dunn.playwithandroid.apis.WanAndroidApis
import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.net.RequestManager
import com.dunn.playwithandroid.base.net.RetrofitManager
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import com.dunn.playwithandroid.bean.ProjectCategoryBean

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectPresenterImpl(view: ProjectContract.View) : BasePresenter<ProjectContract.View>(view),
    ProjectContract.Presenter {
    override fun getProjectCategory() {
        RequestManager.execute(
            this,
            RetrofitManager.create(WanAndroidApis::class.java).projectCategory(),
            object : BaseObserver<List<ProjectCategoryBean>>() {
                override fun onSuccess(data: List<ProjectCategoryBean>) {
                    view.onProjectCategorySuccess(data)
                }

                override fun onError(e: ResponseException) {
                    view.onProjectCategoryError(e)
                }

            })
    }
}