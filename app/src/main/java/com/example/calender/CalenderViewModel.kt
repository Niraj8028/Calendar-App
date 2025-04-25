import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calender.Task
import kotlinx.coroutines.flow.*
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    val tasksForSelectedDate: StateFlow<List<Task>> =
        combine(_selectedDate, _tasks) { date, tasks ->
            tasks.filter { it.date == date }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val calendarGridInfo: StateFlow<CalendarGridInfo> =
        _selectedDate.map { date ->
            val daysInMonth = date.lengthOfMonth()
            val firstDayOfWeek = date.withDayOfMonth(1).dayOfWeek.value
            val totalCells = if (firstDayOfWeek == 7) daysInMonth else daysInMonth + firstDayOfWeek - 1

            CalendarGridInfo(
                daysInMonth = daysInMonth,
                firstDayOfWeek = firstDayOfWeek,
                totalCells = totalCells
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CalendarGridInfo()
        )

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun navigateToPreviousMonth() {
        _selectedDate.update { it.minusMonths(1) }
    }

    fun navigateToNextMonth() {
        _selectedDate.update { it.plusMonths(1) }
    }

    fun addTask(title: String) {
        _tasks.update { currentTasks ->
            currentTasks + Task(
                title = title,
                date = _selectedDate.value
            )
        }
    }

    fun deleteTask(task: Task) {
        _tasks.update { currentTasks ->
            currentTasks.filterNot { it == task }
        }
    }
}

data class CalendarGridInfo(
    val daysInMonth: Int = 0,
    val firstDayOfWeek: Int = 1, // Monday
    val totalCells: Int = 0
)