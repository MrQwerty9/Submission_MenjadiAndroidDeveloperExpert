package com.sstudio.submission_made.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule(
    val channelId: Int,
    val date: String,
    val time: String,
    val title: String
): Parcelable