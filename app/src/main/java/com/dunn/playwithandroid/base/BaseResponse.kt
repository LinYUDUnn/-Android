package com.dunn.playwithandroid.base

/**
 * Created by DunnLin on 2019/7/25.
 */
data class BaseResponse<T>(val errorMsg: String, val errorCode: String, var data: T) {
}