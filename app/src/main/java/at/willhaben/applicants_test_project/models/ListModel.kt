package at.willhaben.applicants_test_project.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListModel(val query: Query?) : Parcelable

@Parcelize
data class Query(val search: List<Page>?) : Parcelable

@Parcelize
data class Page(val title: String?, val snippet: String?) : Parcelable
