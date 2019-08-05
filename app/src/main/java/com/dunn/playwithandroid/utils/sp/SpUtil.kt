package com.dunn.playwithandroid.utils.sp

/**
 * Created by DunnLin on 2019/7/25.
 */
class SpUtil {

    companion object {
        fun getCookies(): String {
            return SharedPreferencesHelper.get("cookies", "")
        }

        fun setCookies(version: String) {
            SharedPreferencesHelper.put("cookies", version)
        }

        fun removeCookies() {
            SharedPreferencesHelper.remove("cookies")
        }

        fun getUsername(): String {
            return SharedPreferencesHelper.get("username", "登录")
        }

        fun setUsername(username: String) {
            SharedPreferencesHelper.put("username", username)
        }

        fun removeUsername() {
            SharedPreferencesHelper.remove("username")
        }
    }

}