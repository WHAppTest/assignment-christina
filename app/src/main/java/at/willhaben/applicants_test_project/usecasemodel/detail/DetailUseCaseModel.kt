package at.willhaben.applicants_test_project.usecasemodel.detail

import android.os.Bundle
import at.willhaben.applicants_test_project.usecase.DetailUseCase
import at.willhaben.applicants_test_project.usecasemodel.BaseUseCaseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.inject

class DetailUseCaseModel : BaseUseCaseModel<DetailUseCaseState>() {

    private val gson: Gson by inject()
    private val okHttpClient: OkHttpClient by inject()
    private var detailUseCase: DetailUseCase = DetailUseCase(okHttpClient, gson)

    fun fetchData(title: String) {
        if (lastState != null) {
            launch {
                lastState?.let { state -> channel.send(state) }
            }
        } else {
            launch {
                lastState = DetailUseCaseState.Loading.apply {
                    channel.send(this)
                }
                lastState = try {
                    DetailUseCaseState.Loaded(detailUseCase.getDetailModel(title)).apply {
                        channel.send(this)
                    }
                } catch (e: Exception) {
                    DetailUseCaseState.Error(e.message ?: e.toString()).apply {
                        channel.send(this)
                    }
                }
            }
        }
    }

    override fun saveState(bundle: Bundle) {
        bundle.putParcelable(EXTRA_LAST_STATE, lastState)
    }

    override fun restoreState(bundle: Bundle) {
        lastState = bundle.getParcelable(EXTRA_LAST_STATE)
    }

    companion object {
        private const val EXTRA_LAST_STATE = "EXTRA_LAST_STATE"
    }
}