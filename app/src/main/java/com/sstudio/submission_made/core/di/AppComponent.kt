package com.sstudio.submission_made.core.di

import com.sstudio.submission_made.di.AppScope
import com.sstudio.submission_made.ui.channel.ChannelFragment
import com.sstudio.submission_made.ui.favorite.FavoriteFragment
import com.sstudio.submission_made.ui.schedule.ScheduleActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: ChannelFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(activity: ScheduleActivity)
}