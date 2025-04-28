//import com.example.calender.API.CalenderService
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class MyModule {
//
//    @Provides
//    @Singleton
//    fun providesRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("http://dev.frndapp.in:8085")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun providesCalendarService(retrofit: Retrofit): CalenderService {
//        return retrofit.create(CalenderService::class.java)
//    }
//}