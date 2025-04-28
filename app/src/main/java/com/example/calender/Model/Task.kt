import com.example.calender.Task
import com.google.gson.annotations.SerializedName
import java.time.format.DateTimeFormatter

data class TaskModel(
    @SerializedName("task_id") val id: Int = 0,
    @SerializedName("title") val title: String,
    @SerializedName("date") private val date: String,
)

data class TaskRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("task") val task: Task
)

data class TaskListRequest(
    @SerializedName("user_id") val userId: Int
)

data class DeleteTaskRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("task_id") val taskId: Int
)

data class TaskListResponse(
    @SerializedName("task_details") val tasks: List<Task>
)