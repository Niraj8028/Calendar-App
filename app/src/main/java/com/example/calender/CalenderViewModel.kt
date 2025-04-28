
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calender.API.CalendarRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class CalendarViewModel(private val repository: CalendarRepository) : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasks: StateFlow<List<TaskModel>> = _tasks.asStateFlow()

    private val _calendarTasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val calendarTasks: StateFlow<List<TaskModel>> = _calendarTasks.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _showAllTasks = MutableStateFlow(true)
    val showAllTasks: StateFlow<Boolean> = _showAllTasks

//    val isConnectedToNetwork: StateFlow<Boolean> = connectivityMonitor.isConnected

    init {
        loadTasks()
    }

    fun loadTasks(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
               repository.getTasks().collect { taskList ->
                   _calendarTasks.value = taskList
               }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleTaskDisplay() {
        _showAllTasks.value = !_showAllTasks.value
    }

    fun addTasks(title: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val task = TaskDetail(title = title, description = description);
                repository.addTask(task);
                _tasks.update { currentTasks ->
                    currentTasks + TaskModel(
                        taskId = null,
                        TaskDetail(
                            title = title,
                            description = description,
                            taskDate = selectedDate.value
                        )
                    )
                }
                loadTasks()
            } catch (e: Exception){
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    val tasksForSelectedDate: StateFlow<List<TaskModel>> =
        combine(_selectedDate, _tasks) { date, tasks ->
            tasks.filter { it.taskDetail.taskDate == date }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
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
        Log.d("Month", "Previos")
        _selectedDate.update { it.minusMonths(1) }
    }

    fun navigateToNextMonth() {
        Log.d("Month", "Next Previos")
        _selectedDate.update { it.plusMonths(1) }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {
            try {
                task.taskId?.let { repository.deleteTask(it) }
                _tasks.update { currentTasks ->
                    currentTasks.filterNot { it.taskDetail.title == task.taskDetail.title }
                }
                loadTasks()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

data class CalendarGridInfo(
    val daysInMonth: Int = 0,
    val firstDayOfWeek: Int = 1, // Monday
    val totalCells: Int = 0
)