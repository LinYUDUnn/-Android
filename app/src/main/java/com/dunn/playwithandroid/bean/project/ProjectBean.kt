package com.dunn.playwithandroid.bean.project

/**
 * Created by DunnLin on 2019/7/27.
 */
class ProjectBean(val over: Boolean = false,
                  val pageCount: Int = 0,
                  val total: Int = 0,
                  val curPage: Int = 0,
                  val offset: Int = 0,
                  val size: Int = 0,
                  val datas: List<DatasItem>?)