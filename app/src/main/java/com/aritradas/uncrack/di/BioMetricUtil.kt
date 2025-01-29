package com.aritradas.uncrack.di

import android.content.Context
import com.aritradas.uncrack.util.AppBioMetricManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class BioMetricUtil {

    @Provides
    fun provideAppBioMetricManager(context: Context): AppBioMetricManager {
        return AppBioMetricManager(context)
    }
}