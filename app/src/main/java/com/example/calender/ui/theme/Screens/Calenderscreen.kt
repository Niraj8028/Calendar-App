import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.calender.DateHeader
import com.example.calender.TaskDialog
import com.example.calender.ui.theme.Components.CalendarGrid
import com.example.calender.ui.theme.Components.TaskItem
import com.example.calender.ui.theme.Components.WeekdayHeader

@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val tasksForSelectedDate by viewModel.tasksForSelectedDate.collectAsState()
    val allTasks by viewModel.calendarTasks.collectAsState()
    val calendarGridInfo by viewModel.calendarGridInfo.collectAsState()
    val showAllTasks by viewModel.showAllTasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showTaskDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        DateHeader(
            selectedDate = selectedDate,
            onPreviousMonth = { viewModel.navigateToPreviousMonth() },
            onNextMonth = { viewModel.navigateToNextMonth() }
        )

        Spacer(modifier = Modifier.height(14.dp))

        WeekdayHeader()

        Spacer(modifier = Modifier.height(10.dp))

        CalendarGrid(
            calendarGridInfo = calendarGridInfo,
            selectedDate = selectedDate,
            tasks = viewModel.tasks,
            onDateSelected = { viewModel.selectDate(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showTaskDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task for $selectedDate")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.toggleTaskDisplay() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (showAllTasks) Color.LightGray else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (showAllTasks) "Tasks for ${selectedDate}" else "Show All")
        }

        Text(
            text = if (showAllTasks) "All Tasks" else "Tasks for ${selectedDate}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            val currentTasks = if (showAllTasks) allTasks else tasksForSelectedDate
            if (currentTasks.isEmpty()) {
                Text(
                    text = if (showAllTasks) "No Taks" else "No tasks available for selected date",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            } else {
                LazyColumn {
                    items(items = if (showAllTasks) allTasks else tasksForSelectedDate,
                        key = { it.hashCode() }) { task ->
                        TaskItem(task = task, onDelete = { viewModel.deleteTask(task) })
                    }
                }
            }
        }
    }

    if (showTaskDialog) {
        TaskDialog(
            selectedDate = selectedDate,
            onDismiss = { showTaskDialog = false },
            onConfirm = { title, description ->
                viewModel.addTasks(title, description)
            }
        )
    }
}