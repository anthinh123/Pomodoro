package com.thinh.pomodoro.features.settings._di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.thinh.pomodoro.features.settings.ui.SettingViewModel
import com.thinh.pomodoro.features.settings.usecase.getsettings.GetSettingsUseCase
import com.thinh.pomodoro.features.settings.usecase.getsettings.impl.GetSettingsUseCaseImpl
import com.thinh.pomodoro.features.settings.usecase.savesettings.SaveSettingsUseCase
import com.thinh.pomodoro.features.settings.usecase.savesettings.impl.SaveSettingsUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pomodoro_settings")

val settingModule = module {
    viewModel { SettingViewModel(get(), get(), get()) }
    single<SaveSettingsUseCase> { SaveSettingsUseCaseImpl(androidContext().dataStore) }
    single<GetSettingsUseCase> { GetSettingsUseCaseImpl(androidContext().dataStore) }
}