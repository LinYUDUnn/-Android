package com.dunn.playwithandroid.base.net.exception

import java.lang.RuntimeException

/**
 * Created by DunnLin on 2019/7/25.
 */
class ApiException(val errorCode: String, val errorMessage: String) : RuntimeException() {
}