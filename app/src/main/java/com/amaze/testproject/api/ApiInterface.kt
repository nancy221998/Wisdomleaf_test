package com.amaze.testproject

import com.amaze.testproject.model.ImageModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("v2/list")
    fun getListData(
        @Query("page") page: String?,
        @Query("limit") limit: String?,
    ): Call<ImageModel>?

        companion object {
                      const val BASE_URL = "https://picsum.photos/"
                }
            }
