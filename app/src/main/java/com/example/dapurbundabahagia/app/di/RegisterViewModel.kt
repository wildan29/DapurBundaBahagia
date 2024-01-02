package com.example.dapurbundabahagia.app.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dapurbundabahagia.data.models.RegisterModel
import com.example.dapurbundabahagia.data.repo.MainRepo
import com.example.dapurbundabahagia.domain.models.RegisterState
import com.example.dapurbundabahagia.domain.models.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(private val mainRepo: MainRepo)  : ViewModel(){
    private val _res = MutableLiveData<Resource<JsonObject>>()

    val res: LiveData<Resource<JsonObject>>
        get() = _res

    private val _state = MutableStateFlow(RegisterState())

    val state: StateFlow<RegisterState>
        get() = _state

    fun register(register : RegisterModel) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepo.register(register).let{
            if(it.isSuccessful){
                navigate()
                _res.postValue(Resource.success(it.body()))
            }else{
                val jsonObj =
                    JSONObject(it.errorBody()!!.charStream().readText()).getString("errors")
                showError(jsonObj)
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

   private fun navigate() {
        _state.update { change ->
            change.copy(isNavigate = true)
        }
    }

    fun navigates() {
        _state.update { change ->
            change.copy(isNavigate = null)
        }
    }

    private fun showError(message: String) {
        _state.update { error ->
            error.copy(error = "Register Failed, $message!")
        }
    }

    fun showErrors() {
        _state.update { error ->
            error.copy(error = null)
        }
    }
}