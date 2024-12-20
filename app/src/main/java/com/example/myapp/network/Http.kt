package com.example.myapp.network

import com.example.myapp.model.AccountDevices
import com.example.myapp.model.AccountRequest
import com.example.myapp.model.ApiResponse
import com.example.myapp.model.AreaAdd
import com.example.myapp.model.AreaInfo
import com.example.myapp.model.DeviceAdd
import com.example.myapp.model.HouseAdd
import com.example.myapp.model.HouseInfo
import com.example.myapp.model.Jwt
import com.example.myapp.model.Member
import com.example.myapp.model.AccountUpdate
import com.example.myapp.model.UserInfo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.IOException

interface Api {
    @POST("login")
    suspend fun login(@Body accountRequest: AccountRequest): ApiResponse<Jwt>

    @POST("signup")
    suspend fun signup(@Body accountRequest: AccountRequest): ApiResponse<Unit>
}


interface Tapi {
    @GET("auth")
    suspend fun auth(): ApiResponse<Unit>

    @GET("userinfo")
    suspend fun getUserInfo(): ApiResponse<UserInfo>

    @PATCH("userinfo")
    suspend fun updateUserInfo(@Body userinfo: UserInfo): ApiResponse<Unit>

    @POST("userinfo")
    suspend fun newUserInfo(@Body userinfo: UserInfo): ApiResponse<UserInfo>

    @PATCH("account")
    suspend fun updateAccount(@Body newAccountInfo: AccountUpdate): ApiResponse<Unit>

    @GET("my/device")
    suspend fun getAllAccountDevices(): ApiResponse<AccountDevices>

    @GET("my/member")
    suspend fun getMemberInfo(): ApiResponse<Member>

    @GET("my/area")
    suspend fun getAreasInfo(): ApiResponse<List<AreaInfo>>

    @POST("my/device")
    suspend fun addDevice(@Body deviceAdd: DeviceAdd): ApiResponse<Int>

    @POST("my/house")
    suspend fun addHouse(@Body houseAdd: HouseAdd): ApiResponse<Int>

    @POST("my/area")
    suspend fun addArea(@Body areaAdd: AreaAdd): ApiResponse<Int>

    @GET("my/device/{id}/status")
    suspend fun getDeviceStatus(@Path("id") deviceId: Int): ApiResponse<JsonObject>

    @POST("my/area")
    suspend fun createArea(@Body addArea: AreaAdd): ApiResponse<Int>

    @PATCH("my/area/{areaId}")
    suspend fun updateArea(@Path("areaId") areaId: Int, areaName:String): ApiResponse<Unit>

    @DELETE("my/area/{areaId}")
    suspend fun deleteArea(@Path("areaId") areaId: Int): ApiResponse<Unit>

    @GET("my/house")
    suspend fun getHouseInfo(): ApiResponse<List<HouseInfo>>

    @POST("my/house")
    suspend fun createHouse(@Body houseAdd: HouseAdd): ApiResponse<Int>
}


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val builder = chain.request().newBuilder()
        AccountManager.token?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(builder.build())
    }
}


class HttpExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        try {
            val response = chain.proceed(chain.request())

            // 检查 HTTP 响应码是否为错误代码（例如 4xx，5xx）
            if (!response.isSuccessful) {
                println("HTTP Error: ${response.code}")
                println("URL: ${response.request.url}")
                println("Response Body: ${response.body?.string()}")
            }

            return response
        } catch (e: IOException) {
            println("IOException: ${e.message}")
            throw e  // 重新抛出异常
        }
    }
}

val okhttp = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor())
//    .addInterceptor(HttpExceptionInterceptor())
    .build()


@OptIn(ExperimentalSerializationApi::class)
val api: Api = retrofit2.Retrofit.Builder()
    .baseUrl("http://47.108.27.238/api/")
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(Api::class.java)

@OptIn(ExperimentalSerializationApi::class)
val apiWithToken: Tapi = retrofit2.Retrofit.Builder()
    .baseUrl("http://47.108.27.238/api/")
    .client(okhttp)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(Tapi::class.java)