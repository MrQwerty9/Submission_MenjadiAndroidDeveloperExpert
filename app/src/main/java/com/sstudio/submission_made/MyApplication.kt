package com.sstudio.submission_made

import android.app.Application
import com.sstudio.submission_made.core.di.AppComponent
import com.sstudio.submission_made.core.di.CoreComponent
import com.sstudio.submission_made.core.di.DaggerAppComponent
import com.sstudio.submission_made.core.di.DaggerCoreComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}