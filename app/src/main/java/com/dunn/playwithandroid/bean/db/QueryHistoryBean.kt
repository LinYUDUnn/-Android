package com.dunn.playwithandroid.bean.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * Created by DunnLin on 2019/7/27.
 */
class QueryHistoryBean(
    @Column(nullable = false) var time: Long,
    @Column(nullable = false, unique = true) val name: String
) : LitePalSupport()