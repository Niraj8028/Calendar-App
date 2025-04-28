import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calender.ui.theme.CalenderTheme
import org.threeten.bp.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.calender.DI.DependacyProvider
import com.example.calender.DateHeader
import com.example.calender.TaskDialog
import com.example.calender.ui.theme.Components.CalendarGrid
import com.example.calender.ui.theme.Components.WeekdayHeader

@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val tasksForSelectedDate by viewModel.tasksForSelectedDate.collectAsState()
    val calendarGridInfo by viewModel.calendarGridInfo.collectAsState()

    var showTaskDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DateHeader(
            selectedDate = selectedDate,
            onPreviousMonth = { viewModel.navigateToPreviousMonth() },
            onNextMonth = { viewModel.navigateToNextMonth() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        WeekdayHeader()

        Spacer(modifier = Modifier.height(10.dp))

//        CalendarGrid(
//            calendarGridInfo = calendarGridInfo,
//            selectedDate = selectedDate,
//            tasks = viewModel.tasks,
//            onDateSelected = { viewModel.selectDate(it) }
//        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showTaskDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task for $selectedDate")
        }

//        TaskList(
//            selectedDate = selectedDate,
//            tasks = tasksForSelectedDate,
//            onDeleteTask = { viewModel.deleteTask(it) }
//        )
    }

    if (showTaskDialog) {
        TaskDialog(
            selectedDate = selectedDate,
            onDismiss = { showTaskDialog = false },
            onConfirm = { viewModel.addTask(it) }
        )
    }
}