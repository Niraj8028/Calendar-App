package com.example.calender.ui.theme.Components

import CalendarGridInfo
import TaskModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.threeten.bp.LocalDate
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CalendarGrid(
    calendarGridInfo: CalendarGridInfo,
    selectedDate: LocalDate,
    tasks: StateFlow<List<TaskModel>>,
    onDateSelected: (LocalDate) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(calendarGridInfo.totalCells) { index ->
            if (index < calendarGridInfo.firstDayOfWeek - 1) {
                Spacer(modifier = Modifier.size(40.dp))
            } else {
                val day = index - calendarGridInfo.firstDayOfWeek + 2
                val date = selectedDate.withDayOfMonth(day)
                val isSelected = date == selectedDate
                val taskCount = tasks.value.count { it.taskDetail.taskDate == date }

                CalendarDay(
                    day = day,
                    isSelected = isSelected,
                    taskCount = taskCount,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    day: Int,
    isSelected: Boolean,
    taskCount: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                if (isSelected) Color.Blue.copy(alpha = 0.2f) else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.toString(),
                fontWeight = FontWeight.SemiBold
            )
            if (taskCount > 0) {
                Text(
                    text = "$taskCount",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun WeekdayHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<String> = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
) {
    Row(modifier = modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}