package  com.amaze.testproject

import android.util.Log
import com.amaze.testproject.ApiInterface.Companion.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    interface TimeoutInterceptor : Interceptor
    class TimeoutInterceptorImpl : TimeoutInterceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            if (isConnectionTimedOut(chain))
                throw SocketTimeoutException()
            return chain.proceed(chain.request())
        }

        private fun isConnectionTimedOut(chain: Interceptor.Chain): Boolean {
            try {
                val response = chain.proceed(chain.request())
                val content = response.toString()
                response.close()
                Log.d("tag", "isConnectionTimedOut() => $content")
                } catch (e: SocketTimeoutException) {
                    return true
                }
                return false
            }
        }

        private val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

        var gson=GsonBuilder().setLenient().create()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service) }

}