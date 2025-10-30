package at.willhaben.applicants_test_project.usecasemodel.detail

import android.os.Parcelable
import at.willhaben.applicants_test_project.models.DetailModel
import kotlinx.parcelize.Parcelize

sealed class DetailUseCaseState : Parcelable {
    @Parcelize
    object Init : DetailUseCaseState()

    @Parcelize
    object Loading : DetailUseCaseState()

    @Parcelize
    class Loaded(val model: DetailModel) : DetailUseCaseState()

    @Parcelize
    class Error(val errorMessage: String?) : DetailUseCaseState()
}