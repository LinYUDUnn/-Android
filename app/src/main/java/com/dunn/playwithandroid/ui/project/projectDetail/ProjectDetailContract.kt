package com.dunn.playwithandroid.ui.project.projectDetail

import com.dunn.playwithandroid.base.BaseView
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.project.ProjectBean
import com.dunn.playwithandroid.ui.collection.CollectContract

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectDetailContract {

    interface View : BaseView, CollectContract.View {
        fun onNewProjectListSuccess(data: ProjectBean)
        fun onNewProjectListError(e: ResponseException)

        fun onProjectDetailSuccess(data: ProjectBean)
        fun onProjectDetailError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun getNewProjectList(pageNum: Int)

        fun getProjectDetail(pageNum: Int, cid: Int)
    }
}