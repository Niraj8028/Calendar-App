import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import java.time.format.DateTimeFormatter

data class TaskModel(
    @SerializedName("task_id") val taskId: Int? = null,
    @SerializedName("task_detail") val taskDetail: TaskDetail
)

data class TaskDetail(
    val title: String,
    val description: String? = null,
    val taskDate: LocalDate? = null
)

data class TaskRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("task") val task: TaskDetail
)

data class TaskListRequest(
    @SerializedName("user_id") val userId: Int
)

data class DeleteTaskRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("task_id") val taskId: Int
)

data class TaskResponse(
    val tasks: List<TaskModel>
)