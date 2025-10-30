package at.willhaben.applicants_test_project.usecase

import at.willhaben.applicants_test_project.models.ListModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class ListUseCase(private val client: OkHttpClient, private val gson: Gson) {

    val url =
        "https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=apple"

    fun getListModel(): ListModel {
        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
            .build()
        val response = client.newCall(request).execute()

        return gson.fromJson(response.body?.string(), ListModel::class.java)
    }
}