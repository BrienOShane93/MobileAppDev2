package org.wit.parolymplus.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(var id: Long = 0,
                         var title: String = "",
                         var set: String = "",
                         var duration: String = "",
                         var image: Uri = Uri.EMPTY) : Parcelable