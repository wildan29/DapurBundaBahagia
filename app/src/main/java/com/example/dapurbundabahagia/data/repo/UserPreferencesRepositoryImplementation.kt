package com.example.dapurbundabahagia.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.dapurbundabahagia.domain.usecase.UserPreferencesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImplementation @Inject constructor(private val userDataStorePreferences: DataStore<Preferences>) :
    UserPreferencesRepository {

}