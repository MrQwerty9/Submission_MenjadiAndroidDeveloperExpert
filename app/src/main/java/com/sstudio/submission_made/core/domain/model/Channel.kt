package com.sstudio.submission_made.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(
    val id: Int,
    val channel: String,
    val logoPath: String
) : Parcelable