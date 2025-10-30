package at.willhaben.applicants_test_project.usecase

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val useCaseModule = module {
    single {
        OkHttpClient.Builder().apply {
            callTimeout(50, TimeUnit.SECONDS)
        }.build()
    }
    single<Gson> {
        GsonBuilder().create()
    }
}