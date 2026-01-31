package dev.supergooey.mongoose.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ApiClient private constructor(
    private val retrofit: Retrofit
) {
    // Add API service interfaces here as needed
    // Example:
    // val itemsApi: ItemsApi by lazy { retrofit.create(ItemsApi::class.java) }

    companion object {
        private const val BASE_URL = "https://api.example.com/"

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        fun create(): ApiClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()

            return ApiClient(retrofit)
        }
    }
}
