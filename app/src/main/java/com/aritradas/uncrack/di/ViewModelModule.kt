package com.aritradas.uncrack.di

import com.aritradas.uncrack.data.datastore.DataStoreUtil
import com.aritradas.uncrack.sharedViewModel.SharedViewModel
import com.aritradas.uncrack.util.AppBioMetricManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
        bioMetricManager: AppBioMetricManager,
        dataStoreUtil: DataStoreUtil,
    ): SharedViewModel {
        return SharedViewModel(bioMetricManager, dataStoreUtil)
    }
}