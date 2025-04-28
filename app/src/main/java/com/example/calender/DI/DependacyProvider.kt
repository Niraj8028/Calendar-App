package com.example.calender.DI

import CalendarViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.calender.API.CalendarRepository
import com.example.calender.API.CalenderService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependacyProvider {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://dev.frndapp.in:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val calendarApiService by lazy {
        retrofit.create(CalenderService::class.java)
    }

    fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository(calendarApiService)
    }

    fun provideCalendarViewModel(owner: ViewModelStoreOwner): CalendarViewModel {
        return ViewModelProvider(owner, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CalendarViewModel(provideCalendarRepository()) as T
            }
        }).get(CalendarViewModel::class.java)
    }
}