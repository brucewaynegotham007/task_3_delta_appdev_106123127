package com.example.merit_match_delta_task_3

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://brucewaynegotham007.pythonanywhere.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val retrofitServiceForSignup : ApiServiceForSignup = retrofit.create(ApiServiceForSignup::class.java)
val retrofitServiceForLogin : ApiServiceForLogin = retrofit.create(ApiServiceForLogin::class.java)
val retrofitServiceForUserData : ApiServiceForUserData = retrofit.create(ApiServiceForUserData::class.java)
val retrofitServiceForTaskCreation : ApiServiceForTaskCreation = retrofit.create(ApiServiceForTaskCreation::class.java)
val retrofitServiceForTaskDisplay : ApiServiceForTaskDisplay = retrofit.create(ApiServiceForTaskDisplay::class.java)
val retrofitServiceForReservingTask : ApiServiceForReservingTask = retrofit.create(ApiServiceForReservingTask::class.java)
val retrofitServiceForCompletingTask : ApiServiceForCompletingTask = retrofit.create(ApiServiceForCompletingTask::class.java)
val retrofitServiceForTasksThatNeedApproval : ApiServiceForTasksThatNeedApproval = retrofit.create(ApiServiceForTasksThatNeedApproval::class.java)
val retrofitServiceForApprovingTasks : ApiServiceForApproving = retrofit.create(ApiServiceForApproving::class.java)
val retrofitServiceForTransactionHistory : ApiServiceForTransactionsHistoryOfUser = retrofit.create(ApiServiceForTransactionsHistoryOfUser::class.java)
val retrofitServiceForMyTask : ApiServiceForMyTasks = retrofit.create(ApiServiceForMyTasks::class.java)
val retrofitServiceForEditingTasks : ApiServiceForEditingTasks = retrofit.create(ApiServiceForEditingTasks::class.java)
val retrofitServiceForMyReservedTask : ApiServiceForMyReservedTasks = retrofit.create(ApiServiceForMyReservedTasks::class.java)
val retrofitServiceForGettingTransactionsWithOtherUser : ApiServiceForGettingTransactionsWithOtherUser = retrofit.create(ApiServiceForGettingTransactionsWithOtherUser::class.java)
val retrofitServiceForNotifications : ApiServiceForNotifications = retrofit.create(ApiServiceForNotifications::class.java)
val retrofitServiceForDailyBonusAvailability : ApiServiceForDailyBonus = retrofit.create(ApiServiceForDailyBonus::class.java)
val retrofitServiceForKarmaPointsAfterChange : ApiServiceForKarmaPointsAfterChange = retrofit.create(ApiServiceForKarmaPointsAfterChange::class.java)
val retrofitServiceForHandlingRating : ApiServiceForHandlingRating = retrofit.create(ApiServiceForHandlingRating::class.java)
val retrofitServiceForGettingOtherUserData : ApiServiceForGettingOtherUserData = retrofit.create(ApiServiceForGettingOtherUserData::class.java)

data class SignupBody(
    @SerializedName("username") val username : String,
    @SerializedName("password") val password : String
)

data class SignupResponse(
    @SerializedName("message") val message : String
)

interface ApiServiceForSignup {
    @POST("signup")
    suspend fun signupNewUser(
        @Body signupbody : SignupBody
    ) : SignupResponse
}

data class LoginBody(
    @SerializedName("username") val username : String,
    @SerializedName("password") val password: String
)

interface ApiServiceForLogin {
    @POST("login")
    suspend fun loginUser(
        @Body loginBody: LoginBody
    ) : ResponseBody
}

interface ApiServiceForUserData {
    @GET("user")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ) : ResponseBody
}

data class TaskCreationBody(
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("karma_points") val karmaPoints : Int,
    @SerializedName("location") val location: String
)

data class TaskCreationResponse(
    @SerializedName("message") val message : String,
    @SerializedName("task_id") val taskId : Int
)

interface ApiServiceForTaskCreation {
    @POST("tasks")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body newTask : TaskCreationBody
    ) : TaskCreationResponse
}

data class Task(
    @SerializedName("description") val description: String,
    @SerializedName("id") val taskId : Int,
    @SerializedName("karma_points") val karmaPoints: Int,
    @SerializedName("title") val title: String,
    @SerializedName("location") val location : String
)

interface ApiServiceForTaskDisplay {
    @GET("tasks")
    suspend fun receiveTaskData(
        @Header("Authorization") token : String
    ) : List<Task>
}

data class ReserveTaskResponse(
    @SerializedName("message") val message: String
)

interface ApiServiceForReservingTask {
    @POST("tasks/{taskId}/reserve")
    suspend fun reserveTask(
        @Path("taskId") taskId: Int,
        @Header("Authorization") token: String
    ) : ReserveTaskResponse
}

data class CompleteTaskResponse(
    @SerializedName("message") val message: String
)

interface ApiServiceForCompletingTask {
    @POST("tasks/{taskId}/complete")
    suspend fun completeTask(
        @Path("taskId") taskId: Int,
        @Header("Authorization") token: String
    ) : CompleteTaskResponse
}

data class TaskSeekingApproval(
    @SerializedName("description") val description: String,
    @SerializedName("id") val taskId : Int,
    @SerializedName("karma_points") val karmaPoints: Int,
    @SerializedName("title") val title: String,
    @SerializedName("helper_id") val helperId : Int,
    @SerializedName("location") val location: String
)

interface ApiServiceForTasksThatNeedApproval {
    @GET("tasks/pending_approval")
    suspend fun getTasksNeedingApproval(
        @Header("Authorization") token : String
    ) : List<TaskSeekingApproval>
}

data class ApprovalBody(
    @SerializedName("approved") val approved : Boolean
)

data class ApprovalResponse(
    @SerializedName("message") val message: String
)

interface ApiServiceForApproving {
    @POST("tasks/{taskId}/approve")
    suspend fun approveTask(
        @Path("taskId") taskId: Int,
        @Header("Authorization") token: String,
        @Body approvalBody: ApprovalBody
    ) : ApprovalResponse
}

data class TransactionHistoryResponse(
    @SerializedName("id") val transactionId : Int,
    @SerializedName("other_id") val otherId : Int,
    @SerializedName("other_user") val otherUser : String,
    @SerializedName("task_id") val taskId: Int,
    @SerializedName("amount") val amount : Int,
    @SerializedName("transaction_type") val transactionType : String,
    @SerializedName("timestamp") val dateTime : String
)

interface ApiServiceForTransactionsHistoryOfUser {
    @GET("transactions")
    suspend fun getTransactionHistory(
        @Header("Authorization") token: String
    ) : List<TransactionHistoryResponse>
}

interface ApiServiceForMyTasks {
    @GET("mytasks")
    suspend fun getMyTasks(
        @Header("Authorization") token: String
    ) : List<Task>
}

data class EditTasks(
    @SerializedName("title") val title: String,
    @SerializedName("karma_points") val karmaPoints: Int,
    @SerializedName("description") val description: String
)

data class EditResponse(
    @SerializedName("message") val message: String
)

interface ApiServiceForEditingTasks {
    @PUT("tasks/{taskId}/edit")
    suspend fun editTasks(
        @Path("taskId") taskId: Int,
        @Header("Authorization") token: String,
        @Body editBody : EditTasks
    ) : EditResponse
}

interface ApiServiceForMyReservedTasks {
    @GET("myreservedtasks")
    suspend fun getMyReservedTasks(
        @Header("Authorization") token: String
    ) : List<Task>
}

data class TransactionsWithOtherUser(
    @SerializedName("id") val transactionId : Int,
    @SerializedName("task_id") val taskId: Int,
    @SerializedName("amount") val amount : Int,
    @SerializedName("transaction_type") val transactionType : String,
    @SerializedName("timestamp") val dateTime : String,
    @SerializedName("is_current_user_creator") val isCurrentUserCreator : Boolean
)

interface ApiServiceForGettingTransactionsWithOtherUser {
    @GET("transactions/with/{other_id}")
    suspend fun getTransactionsWithOtherUser(
        @Path("other_id") otherId: Int,
        @Header("Authorization") token: String
    ) : List<TransactionsWithOtherUser>
}

data class Notifications(
    @SerializedName("timestamp") val timestamp  : String,
    @SerializedName("id") val taskId : Int,
    @SerializedName("karma_points") val karmaPoints: Int,
    @SerializedName("title") val title : String,
    @SerializedName("status") val status : String,
    @SerializedName("helper_id") val helperId : Int,
)

interface ApiServiceForNotifications {
    @GET("notifications")
    suspend fun getNotifications(
        @Header("Authorization") token : String
    ) : List<Notifications>
}

data class DailyBonusAvailability(
    @SerializedName("message") val available : Boolean
)

interface ApiServiceForDailyBonus {
    @GET("daily_bonus_availability")
    suspend fun getDailyBonusAvailability(
        @Header("Authorization") token: String
    ) : DailyBonusAvailability
}

data class DailyBonusAdded(
    @SerializedName("karma_points") val karmaPoints: Int
)

interface ApiServiceForKarmaPointsAfterChange {
    @GET("handle_bonus")
    suspend fun getDailyBonusAvailability(
        @Header("Authorization") token: String
    ) : DailyBonusAdded
}

data class RatingBody(
    @SerializedName("task_id") val taskId: Int,
    @SerializedName("rating") val rating : Int
)

interface ApiServiceForHandlingRating {
    @POST("handle_rating")
    suspend fun updateRating(
        @Header("Authorization") token: String,
        @Body ratingBody: RatingBody
    ) : EditResponse
}

data class OtherUserDataResponse(
    @SerializedName("username") val username: String,
    @SerializedName("rating") val rating: Int
)

data class OtherUserData(
    @SerializedName("user_id") val userId : Int
)

interface ApiServiceForGettingOtherUserData {
    @POST("otheruserdetails")
    suspend fun getOtherUserData(
        @Header("Authorization") token : String,
        @Body otherUserData : OtherUserData
    ) : OtherUserDataResponse
}

