package com.example.merit_match_delta_task_3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TransactionHistory(navController: NavController) {
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    val executed = remember { mutableStateOf(false) }
    val originalCount = remember { mutableIntStateOf(0) }
    val newCount = remember { mutableIntStateOf(0) }
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForNotifications.value = retrofitServiceForNotifications.getNotifications(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForNotifications.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "timestamp : ${i.timestamp} , " +
                            "title : ${i.title} , " +
                            "karma_points : ${i.karmaPoints}"
                )
            }
            executed.value = true
            originalCount.value = responseForNotifications.value.size
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks needing approval" , "${e.message}")
            executed.value = true
        }
    }
    val newNotification = remember { mutableStateOf(false) }
    LaunchedEffect(executed.value) {
        Log.d("token" , tokenForTheSession.value)
        while(executed.value){
            newCount.value = originalCount.value
            try {
                responseForNotifications.value =
                    retrofitServiceForNotifications.getNotifications(token = tokenForTheSession.value)
                newCount.value = responseForNotifications.value.size
            } catch (e: Exception) {
                Log.d("error in displaying tasks needing approval", "${e.message}")
            }
            delay(5000)
            if(newCount.value != originalCount.value) {
                newNotification.value = true
                delay(3000)
                newNotification.value = false
            }
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageResource(R.drawable.gradient)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { /* No update needed */ }
    )
    val responseForTransactionHistory = remember { mutableStateOf<List<TransactionHistoryResponse>>(emptyList()) }
    LaunchedEffect(Unit) {
        isLoading.value = true
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForTransactionHistory.value = retrofitServiceForTransactionHistory.getTransactionHistory(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForTransactionHistory.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "description : ${i.transactionId} , " +
                            "timestamp : ${i.dateTime} , " +
                            "amount : ${i.amount}" +
                            "transaction_type : ${i.transactionType}" +
                    "otherId : ${i.otherId}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in getting transaction history" , "${e.message}")
        }
        finally {
            isLoading.value = false
        }
    }
    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Transaction History",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Column() {
            Spacer(modifier = Modifier.padding(top = 20.dp))
            if (responseForTransactionHistory.value.isEmpty() && isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Loading...",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                }
            }
            else if(responseForTransactionHistory.value.isEmpty() && !isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nothing to show here :(",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White
                    )
                }
            }
            else {
                LazyColumn() {
                    items(responseForTransactionHistory.value) { transaction ->
                        Card(
                            modifier = Modifier
                                .size(400.dp, 140.dp)
                                .padding(start = 10.dp)
                                .clickable {
                                    navController.navigate("transactionsBetweenTwoUsers/${transaction.otherId}/${transaction.otherUser}")
                                }
                                .border(
                                    color = Color.White,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = transaction.otherUser,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Blue,
                                        modifier = Modifier.clickable {
                                            navController.navigate("showUserDetails/${transaction.otherId}")
                                        },
                                        textDecoration = TextDecoration.Underline
                                    )
                                    Spacer(modifier = Modifier.padding(start = 10.dp))
                                    Column(
                                        modifier = Modifier.padding(top = 10.dp)
                                    ) {
                                        Row() {
                                            Text(
                                                text = "(User Id : ${transaction.otherId})",
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.padding(top = 20.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = " Karma Points Transferred :   ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    val color : Color
                                    if(transaction.amount<0) color = Color.Red
                                    else color = Color.Green
                                    Text(
                                        text = transaction.amount.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = color
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    val date = transaction.dateTime.substring(0,10)
                                    Spacer(modifier = Modifier.padding(start = 30.dp))
                                    Text(
                                        text = "Date :    $date",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                    }
                }
            }
        }
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun TransactionsBetweenTwoUsers(navController: NavController , otherId : Int , otherUser : String) {
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    val executed = remember { mutableStateOf(false) }
    val originalCount = remember { mutableIntStateOf(0) }
    val newCount = remember { mutableIntStateOf(0) }
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForNotifications.value = retrofitServiceForNotifications.getNotifications(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForNotifications.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "timestamp : ${i.timestamp} , " +
                            "title : ${i.title} , " +
                            "karma_points : ${i.karmaPoints}"
                )
            }
            executed.value = true
            originalCount.value = responseForNotifications.value.size
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks needing approval" , "${e.message}")
            executed.value = true
        }
    }
    val newNotification = remember { mutableStateOf(false) }
    LaunchedEffect(executed.value) {
        Log.d("token" , tokenForTheSession.value)
        while(executed.value){
            newCount.value = originalCount.value
            try {
                responseForNotifications.value =
                    retrofitServiceForNotifications.getNotifications(token = tokenForTheSession.value)
                newCount.value = responseForNotifications.value.size
            } catch (e: Exception) {
                Log.d("error in displaying tasks needing approval", "${e.message}")
            }
            delay(5000)
            if(newCount.value != originalCount.value) {
                newNotification.value = true
                delay(3000)
                newNotification.value = false
            }
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageResource(R.drawable.gradient)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { /* No update needed */ }
    )
    val responseForTransactionHistoryWithOtherUser = remember { mutableStateOf<List<TransactionsWithOtherUser>>(emptyList()) }
    LaunchedEffect(Unit) {
        isLoading.value = true
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForTransactionHistoryWithOtherUser.value = retrofitServiceForGettingTransactionsWithOtherUser.getTransactionsWithOtherUser(otherId = otherId , token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForTransactionHistoryWithOtherUser.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "description : ${i.transactionId} , " +
                            "timestamp : ${i.dateTime} , " +
                            "amount : ${i.amount}" +
                            "transaction_type : ${i.transactionType}" +
                            "otherId : ${i.isCurrentUserCreator}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in getting transaction history" , "${e.message}")
        }
        finally {
            isLoading.value = false
        }
    }
    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Transaction History ",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Row() {
                Text(
                    text = "With \"${otherUser}\"",
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Column() {
            Spacer(modifier = Modifier.padding(top = 20.dp))
            if (responseForTransactionHistoryWithOtherUser.value.isEmpty() && isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading...",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White
                    )
                }
            }
            else if(responseForTransactionHistoryWithOtherUser.value.isEmpty() && !isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nothing to show here :(",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White
                    )
                }
            }
            else {
                LazyColumn() {
                    items(responseForTransactionHistoryWithOtherUser.value) { transaction ->
                        Card(
                            modifier = Modifier
                                .size(400.dp, 210.dp)
                                .padding(start = 10.dp)
                                .clickable {
                                    //do nothing
                                }
                                .border(
                                    color = Color.White,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {

                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = transaction.transactionType.uppercase(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 20.dp))
                                val color : Color
                                if(transaction.amount<0) color = Color.Red
                                else color = Color.Green
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = " Karma Points Transferred :   ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = transaction.amount.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = color
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Purpose of transaction : task " + transaction.taskId.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    val date = transaction.dateTime.substring(0,10)
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Date :    $date",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    val time = transaction.dateTime.substring(12)
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Time :    $time",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                    }
                }
            }
        }
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

fun shareToSocialMedia(text : String , context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT,text)
    }
    val chooserIntent = Intent.createChooser(intent , "Share to")
    context.startActivity(chooserIntent)
}

@Composable
fun ShareButton(text: String , context: Context) {
    IconButton(
        onClick = {
            shareToSocialMedia(text, context)
        }
    ) {
        Icon(
            Icons.Default.Share,
            contentDescription = "Share to social media",
            tint = Color.Black,
            modifier = Modifier.scale(1.5f)
        )
    }
}

@Composable
fun Notifications(navController: NavController) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageResource(R.drawable.gradient)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { /* No update needed */ }
    )
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForNotifications.value = retrofitServiceForNotifications.getNotifications(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForNotifications.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "timestamp : ${i.timestamp} , " +
                            "title : ${i.title} , " +
                            "karma_points : ${i.karmaPoints}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks needing approval" , "${e.message}")
        }
    }
    Column() {
        Column(
            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Notifications",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column() {
            if (responseForNotifications.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading...",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                }
            } else {
                Spacer(modifier = Modifier.padding(top = 30.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(responseForNotifications.value) { notifications ->
                        val taskApproved = remember { mutableStateOf(false) }
                        val taskRejected = remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .size(400.dp, 235.dp)
                                .padding(start = 10.dp)
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.padding(top = 15.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Date : " + notifications.timestamp.substring(0,10),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.padding(start = 20.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Time :" + notifications.timestamp.substring(11),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 20.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Task " + notifications.taskId.toString() + " "+ notifications.status + " by ",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "user - " + notifications.helperId.toString(),
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Blue,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = Modifier.clickable {
                                            navController.navigate("showUserDetails/${notifications.helperId}")
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 30.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Task title :  " + notifications.title,
                                        fontSize = 20.sp
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Karma Points :  " + notifications.karmaPoints.toString(),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                    }
                }
            }
        }
    }
    SideBar(navController = navController)
}

