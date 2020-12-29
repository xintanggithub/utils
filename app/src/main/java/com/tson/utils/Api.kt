package com.tson.utils

import com.tson.utils.entity.Gm
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Date 2020-02-27 15:23
 *
 * @author Tson
 */
interface Api {

    @GET("api/v1/game/queryList")
    suspend fun getGmAsync(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Gm
}