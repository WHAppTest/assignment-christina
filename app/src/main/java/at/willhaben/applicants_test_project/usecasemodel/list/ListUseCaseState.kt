package at.willhaben.applicants_test_project.usecasemodel.list

import android.os.Parcelable
import at.willhaben.applicants_test_project.models.ListModel
import kotlinx.parcelize.Parcelize

sealed class ListUseCaseState : Parcelable {
    @Parcelize
    object Init : ListUseCaseState()

    @Parcelize
    object Loading : ListUseCaseState()

    @Parcelize
    class Loaded(val models: ListModel) : ListUseCaseState()

    @Parcelize
    class Error(val errorMessage: String?) : ListUseCaseState()
}