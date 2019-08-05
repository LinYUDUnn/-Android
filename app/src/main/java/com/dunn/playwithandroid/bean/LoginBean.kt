package com.dunn.playwithandroid.bean

/**
 * Created by DunnLin on 2019/7/26.
 */
class LoginBean(
    val password: String = "",
    val icon: String = "",
    val collectIds: List<Int>?,
    val id: Int = 0,
    val type: Int = 0,
    val email: String = "",
    val token: String = "",
    val username: String = ""
) {
}