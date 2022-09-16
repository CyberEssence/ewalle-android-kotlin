package com.example.ewalle.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ewalle.data.model.menu.User
import com.example.ewalle.data.net.NetReqState
import com.example.ewalle.domain.usecases.menu.GetUserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val menuUseCase: GetUserUseCase) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _networkState: MutableLiveData<NetReqState> = MutableLiveData()
    val networkState: LiveData<NetReqState> = _networkState

    private val _toggleVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val toggleVisibility: LiveData<Boolean> = _toggleVisibility

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    init {
        _toggleVisibility.value = false
    }

    fun getCurrentData() {
        if (_networkState.value != NetReqState.LOADING) {
            _networkState.value = NetReqState.LOADING
            _toggleVisibility.value = true
            compositeDisposable.add(
                menuUseCase.getUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        _user.value = it
                        _networkState.value = NetReqState.SUCCESS
                    }, {
                        _networkState.value = NetReqState.ERROR
                    })
            )
        }
    }

    fun deleteData() {
        _toggleVisibility.value = false
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}