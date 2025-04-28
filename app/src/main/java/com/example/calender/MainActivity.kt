package com.example.calender

import CalendarScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.calender.ui.theme.CalenderTheme
import com.example.calender.DI.DependacyProvider

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        DependacyProvider.provideCalendarViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalenderTheme {
                CalendarScreen(viewModel)
            }
        }
    }
}
