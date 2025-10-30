package at.willhaben.applicants_test_project.usecase

import at.willhaben.applicants_test_project.models.DetailModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailUseCase(private val client: OkHttpClient, private val gson: Gson) {

    fun getDetailModel(title: String): DetailModel {
        val url =
            "https://en.wikipedia.org/w/api.php?action=parse&prop=text&page=$title&format=json"
        val request = Request
            .Builder()
            .url(url)
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
            .build()
        val response = client.newCall(request).execute()

        return gson.fromJson(response.body?.string(), DetailModel::class.java)
    }
}