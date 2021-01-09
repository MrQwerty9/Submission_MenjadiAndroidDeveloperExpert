package com.sstudio.submission_made.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class ChannelEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val channel: String,
    val logoPath: String
)