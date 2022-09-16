package com.example.ewalle.data.receiver

import com.example.ewalle.data.model.login.DateTime
import io.reactivex.Observable

interface DateTimeReceiver {
    fun getDateTime(): Observable<DateTime>
}