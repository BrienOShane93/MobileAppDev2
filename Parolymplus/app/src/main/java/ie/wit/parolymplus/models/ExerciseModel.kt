package ie.wit.parolymplus.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ExerciseModel(
        var uid: String? = "",
        var title: String = "",
        var set: String = "",
        var duration: String = "",
        var profilepic: String = "",
        val email: String = "")
        : Parcelable
{
        @Exclude
        fun toMap(): Map<String, Any?> {
                return mapOf(
                        "uid" to uid,
                        "title" to title,
                        "set" to set,
                        "duration" to duration,
                        "profilepic" to profilepic,
                        "email" to email
                )
        }
}