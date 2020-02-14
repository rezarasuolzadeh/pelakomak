package ir.rezarasoulzadeh.pelakomak.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.repository.FoulRepository
import ir.rezarasoulzadeh.pelakomak.service.utils.Enums
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoulViewModel(application: Application) : AndroidViewModel(application) {

    var foulLiveData = MutableLiveData<MutableList<Foul>>()
    var foulState = MutableLiveData<Enums.DataState>()

    fun provideFoul(barcode: String) {
        foulState.postValue(Enums.DataState.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            foulLiveData.postValue(FoulRepository.getInstance().provideFoul(barcode))
            foulState.postValue(Enums.DataState.DONE)
        }
    }

}