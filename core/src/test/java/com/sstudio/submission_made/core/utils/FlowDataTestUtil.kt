package com.sstudio.submission_made.core.utils

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object FlowDataTestUtil {
    fun <T> getValue(liveData: Flow<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)

        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data[0] = o
                latch.countDown()
                liveData.asLiveData().removeObserver(this)
            }
        }

        liveData.asLiveData().observeForever(observer)

        try {
            latch.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return data[0] as T

    }
}