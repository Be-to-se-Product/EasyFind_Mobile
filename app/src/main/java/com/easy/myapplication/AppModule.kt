package com.easy.myapplication
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.easy.myapplication.repositories.StorageRepository
import com.easy.myapplication.screens.Login.Model
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::StorageRepository)
    viewModelOf(::Model)
}

val storageModule = module {
    single {
        PreferenceDataStoreFactory.create{
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }
}