package at.willhaben.applicants_test_project.models

import android.os.Parcelable
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import java.lang.reflect.Type

@Parcelize
data class DetailModel(val parse: Parse?) : Parcelable

@Parcelize
data class Parse(val title: String, val text: String) : Parcelable