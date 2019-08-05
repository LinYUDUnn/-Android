package com.dunn.playwithandroid.base.net.convert

import com.dunn.playwithandroid.base.BaseResponse
import com.dunn.playwithandroid.base.net.exception.ApiException
import io.reactivex.functions.Function

/**
 * Created by DunnLin on 2019/7/25.
 */
class ResponseConvert<E> : Function<BaseResponse<E>, E> {
    override fun apply(t: BaseResponse<E>): E {
        if ("0" != t.errorCode) {
            throw ApiException(t.errorCode, t.errorMsg)
        }
        if (t.data == null) {
            t.data = "" as E
        }
        return t.data
    }
}