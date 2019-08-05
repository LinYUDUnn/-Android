package com.dunn.playwithandroid.bean.tree

/**
 * Created by DunnLin on 2019/7/27.
 */
class TreeBean(
    val visible: Int = 0,
    val children: ArrayList<ChildrenItem>,
    val name: String = "",
    val userControlSetTop: Boolean = false,
    val id: Int = 0,
    val courseId: Int = 0,
    val parentChapterId: Int = 0,
    val order: Int = 0
)