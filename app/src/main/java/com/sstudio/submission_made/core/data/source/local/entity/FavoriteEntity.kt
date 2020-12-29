package com.sstudio.submission_made.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var channelId: Int
)