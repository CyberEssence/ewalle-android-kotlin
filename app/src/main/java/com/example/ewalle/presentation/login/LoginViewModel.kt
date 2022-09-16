package com.example.ewalle.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ewalle.data.model.login.DateTime
import com.example.ewalle.data.model.login.LoginData
import com.example.ewalle.data.model.login.Weather
import com.example.ewalle.data.net.NetReqState
import com.example.ewalle.domain.usecases.login.GetLoginUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginUseCase: GetLoginUseCase) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _dateTime: MutableLiveData<DateTime> = MutableLiveData()
    val dateTime: LiveData<DateTime> = _dateTime

    private val _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: LiveData<Weather> = _weather

    private val _networkState: MutableLiveData<NetReqState> = MutableLiveData()
    val networkState: LiveData<NetReqState> = _networkState

    fun getCurrentData() {
        if (_networkState.value != NetReqState.LOADING) {
            val dateTime =
                loginUseCase.getDateTime()
            val weather =
                loginUseCase.getWeather().toObservable()
            compositeDisposable.add(
                Observable.combineLatest(dateTime, weather) { dateTime, weather ->
                    LoginData(dateTime, weather)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        _weather.value = it.weather
                        _dateTime.value = it.dateTime
                        _networkState.value = NetReqState.SUCCESS
                    }, {
                        _networkState.value = NetReqState.ERROR
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}