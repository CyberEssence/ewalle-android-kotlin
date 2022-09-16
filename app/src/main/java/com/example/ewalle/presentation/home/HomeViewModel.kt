package com.example.ewalle.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ewalle.data.model.home.Services
import com.example.ewalle.data.model.home.Users
import com.example.ewalle.data.net.NetReqState
import com.example.ewalle.domain.usecases.home.GetHomeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeUserCase: GetHomeUseCase,
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _networkState: MutableLiveData<NetReqState> = MutableLiveData()
    val networkState: LiveData<NetReqState> = _networkState

    private val _balance: MutableLiveData<Int> = MutableLiveData()
    val balance: LiveData<Int> = _balance

    private val _listUsers: MutableLiveData<List<Users>> = MutableLiveData()
    val listUsers: LiveData<List<Users>> = _listUsers

    private val _listServices: MutableLiveData<List<Services>> = MutableLiveData()
    val listServices: LiveData<List<Services>> = _listServices

    fun getCurrentData() {
        if (_networkState.value != NetReqState.LOADING) {
            _networkState.value = NetReqState.LOADING
            compositeDisposable.add(
                homeUserCase.getHomeData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ homeData ->
                        _balance.value = homeData.balance
                        _listServices.value = homeData.listServices
                        _listUsers.value = homeData.listUsers
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