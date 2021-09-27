package com.rodrigues.githubrepositories.util

import androidx.test.espresso.idling.CountingIdlingResource
import com.rodrigues.githubrepositories.BuildConfig

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL_IDLING"

    val countingIdlingResource: CountingIdlingResource? = if (BuildConfig.DEBUG) {
        CountingIdlingResource(RESOURCE)
    } else {
        null
    }

    fun increment() {
        countingIdlingResource?.increment()
    }

    fun decrement() {
        if (countingIdlingResource?.isIdleNow == false) countingIdlingResource.decrement()
    }
}