package com.example.calender.DI

import CalendarViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.calender.API.CalendarRepository
import com.example.calender.API.CalenderService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependacyProvider {

    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://dev.frndapp.in:8085/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
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