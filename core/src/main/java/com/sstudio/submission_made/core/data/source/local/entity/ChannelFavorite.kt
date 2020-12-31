package com.sstudio.submission_made.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Relation

data class ChannelFavorite (
    @Embedded
    var channelEntity: ChannelEntity,

    @Relation(parentColumn = "id", entityColumn = "channelId")
    @NonNull
    var favoriteEntity: FavoriteEntity
)