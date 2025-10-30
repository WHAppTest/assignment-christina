package at.willhaben.applicants_test_project.usecasemodel.list

import android.os.Bundle
import at.willhaben.applicants_test_project.usecase.DetailUseCase
import at.willhaben.applicants_test_project.usecase.ListUseCase
import at.willhaben.applicants_test_project.usecasemodel.BaseUseCaseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.core.component.inject

class ListUseCaseModel : BaseUseCaseModel<ListUseCaseState>() {
    private val gson: Gson by inject()
    private val okHttpClient: OkHttpClient by inject()
    private var listUseCase: ListUseCase =  ListUseCase(okHttpClient, gson)

    fun fetchData() {
        if (lastState != null) {
            launch {
                lastState?.let { state -> channel.send(state) }
            }
        } else {
            launch(Dispatchers.IO) {
                lastState = ListUseCaseState.Loading.apply {
                    channel.send(this)
                }
                lastState = try {
                    ListUseCaseState.Loaded(listUseCase.getListModel()).apply {
                        channel.send(this)
                    }
                } catch (e: Exception) {
                    ListUseCaseState.Error(e.message ?: e.toString()).apply {
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