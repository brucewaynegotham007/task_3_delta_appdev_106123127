package com.example.merit_match_delta_task_3

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filters(showFilters : MutableState<Boolean> , minKarmaPoints : MutableState<Int> , location : MutableState<String>) {
    val showAlertDialog = remember { mutableStateOf(true) }
    if(showAlertDialog.value){
        AlertDialog(
            onDismissRequest = {
                showAlertDialog.value = false
                showFilters.value = false
            },
            modifier = Modifier.size(220.dp , 200.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Choose Filters",
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            Text(
                                text = "Min karma points : "
                            )
                        }
                        OutlinedTextField(
                            value = minKarmaPoints.value.toString(),
                            onValueChange = {
                                val intermediate = it.toIntOrNull()
                                minKarmaPoints.value = when (intermediate) {
                                    null -> 0
                                    else -> intermediate
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Text(
                                        text = "",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            modifier = Modifier.size(50.dp, 50.dp),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            Text(
                                text = "Location : "
                            )
                        }
                        OutlinedTextField(
                            value = location.value,
                            onValueChange = {
                                location.value = it
                            },
                            placeholder = {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Text(
                                        text = "",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            modifier = Modifier.size(100.dp, 50.dp),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ApprovalPage(navController : NavController) {
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
    val context = LocalContext.current
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
    val responseForTasksNeedingApproval = remember { mutableStateOf<List<TaskSeekingApproval>>(emptyList()) }
    LaunchedEffect(Unit) {
        isLoading.value = true
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForTasksNeedingApproval.value = retrofitServiceForTasksThatNeedApproval.getTasksNeedingApproval(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForTasksNeedingApproval.value) {
                Log.d(
                "task ${i.taskId}" ,
                "description : ${i.description} , " +
                    "title : ${i.title} , " +
                    "karma_points : ${i.karmaPoints}" +
                    "helper_id : ${i.helperId}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks needing approval" , "${e.message}")
        }
        finally {
            isLoading.value = false
        }
    }
    val showFilters = remember { mutableStateOf(false) }
    val minKarmaPoints = remember { mutableIntStateOf(0) }
    val location = remember { mutableStateOf("") }
    Column() {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Approve Tasks",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        val textFieldValue = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row() {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(350.dp)
                            .border(
                                width = 5.dp,
                                color = Color.White
                            ),
                        value = textFieldValue.value,
                        onValueChange = {
                            textFieldValue.value = it
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Row() {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search icon"
                                )
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = "Search here",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "Filter icon ?!",
                            modifier = Modifier
                                .scale(1.5f)
                                .clickable {
                                    showFilters.value = true
                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        if (responseForTasksNeedingApproval.value.isEmpty() && isLoading.value) {
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
        else if(responseForTasksNeedingApproval.value.isEmpty() && !isLoading.value) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(responseForTasksNeedingApproval.value.filter {
                    it.title.contains(textFieldValue.value , ignoreCase = true) || it.description.contains(textFieldValue.value , ignoreCase = true)
                }) { task ->
                    val taskApproved = remember { mutableStateOf(false) }
                    val taskRejected = remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .clickable {
                                if (!taskApproved.value && !taskRejected.value) {
                                    navController.navigate("taskDetailsSeekingApproval/${task.taskId}/${task.karmaPoints}/${task.description}/${task.title}/${task.location}/${task.helperId}")
                                }
                            }
                            .size(420.dp, 150.dp)
                            .border(
                                color = Color.White,
                                width = 3.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.padding(top = 15.dp))
                            Row() {
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = task.taskId.toString() + ". ",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = task.title.uppercase(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.padding(start = 20.dp))
                                val triggerApproveTask = remember { mutableStateOf(false) }
                                if(!taskApproved.value){
                                    Card(
                                        modifier = Modifier
                                            .clickable {
                                                triggerApproveTask.value = true
                                            }
                                            .border(
                                                width = 3.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .size(170.dp, 60.dp),
                                        colors = CardDefaults.cardColors(Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row() {
                                                Image(
                                                    painter = painterResource(id = R.drawable.tick),
                                                    contentDescription = "tick mark"
                                                )
                                                Text(
                                                    text = "Approve Task"
                                                )
                                            }
                                        }
                                    }
                                }
                                else {
                                    Card(
                                        modifier = Modifier
                                            .clickable {
                                                triggerApproveTask.value = true
                                            }
                                            .border(
                                                width = 3.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .size(170.dp, 60.dp),
                                        colors = CardDefaults.cardColors(Color.Green.copy(alpha = 0.5f))
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row() {
                                                Image(
                                                    painter = painterResource(id = R.drawable.tick),
                                                    contentDescription = "tick mark"
                                                )
                                                Text(
                                                    text = "Approved Task"
                                                )
                                            }
                                        }
                                    }
                                }
                                LaunchedEffect(triggerApproveTask.value) {
                                    if (triggerApproveTask.value && !taskRejected.value) {
                                        approvingTasks(task, true , context)
                                        taskApproved.value = true
                                    }
                                    triggerApproveTask.value = false
                                }
                                Spacer(modifier = Modifier.padding(start = 20.dp))
                                val triggerRejectTask = remember { mutableStateOf(false) }
                                if(!taskRejected.value){
                                    Card(
                                        modifier = Modifier
                                            .clickable {
                                                triggerRejectTask.value = true
                                            }
                                            .border(
                                                width = 3.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .size(170.dp, 60.dp),
                                        colors = CardDefaults.cardColors(Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row() {
                                                Image(
                                                    painter = painterResource(id = R.drawable.outline_dangerous_24),
                                                    contentDescription = "wrong mark"
                                                )
                                                Text(
                                                    text = "Reject Task"
                                                )
                                            }
                                        }
                                    }
                                }
                                else {
                                    Card(
                                        modifier = Modifier
                                            .clickable {
                                                triggerRejectTask.value = true
                                            }
                                            .border(
                                                width = 3.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .size(170.dp, 60.dp),
                                        colors = CardDefaults.cardColors(Color.Red.copy(alpha = 0.5f))
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row() {
                                                Image(
                                                    painter = painterResource(id = R.drawable.outline_dangerous_24),
                                                    contentDescription = "wrong mark"
                                                )
                                                Text(
                                                    text = "Rejected Task"
                                                )
                                            }
                                        }
                                    }
                                }
                                LaunchedEffect(triggerRejectTask.value) {
                                    if (triggerRejectTask.value && !taskApproved.value) {
                                        approvingTasks(task, false,context)
                                        taskRejected.value = true
                                    }
                                    triggerRejectTask.value = false
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 600.dp , start = 300.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate("createTasks")
            }
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = "Star",
                tint = Color.Blue,
                modifier = Modifier.scale(2.5f)
            )
        }
    }
    if(showFilters.value){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Filters(showFilters , minKarmaPoints , location)
        }
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun NotificationOnTop(navController: NavController) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You have a new notification !",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.padding(start = 20.dp))
                    Text(
                        text = "Click to view",
                        modifier = Modifier.clickable {
                            navController.navigate("notifications")
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayTasks(navController: NavController) {
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
    val context = LocalContext.current
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
    val responseForTaskDisplay = remember { mutableStateOf<List<Task>>(emptyList()) }
    LaunchedEffect(Unit) {
        isLoading.value = true
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForTaskDisplay.value = retrofitServiceForTaskDisplay.receiveTaskData(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForTaskDisplay.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "description : ${i.description} , " +
                        "title : ${i.title} , " +
                        "karma_points : ${i.karmaPoints}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks" , "${e.message}")
        }
        finally {
            isLoading.value = false
        }
    }
    val showFilters = remember { mutableStateOf(false) }
    val minKarmaPoints = remember { mutableIntStateOf(0) }
    val location = remember { mutableStateOf("") }
    Column() {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Available Tasks",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        val textFieldValue = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Row() {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(350.dp)
                            .border(BorderStroke(5.dp, Color.White)),
                        value = textFieldValue.value,
                        onValueChange = {
                            textFieldValue.value = it
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Row() {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search icon"
                                )
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = "Search here",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "Filter icon ?!",
                            modifier = Modifier
                                .scale(1.5f)
                                .clickable {
                                    showFilters.value = true
                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        if (responseForTaskDisplay.value.isEmpty() && isLoading.value) {
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
        else if(responseForTaskDisplay.value.isEmpty() && !isLoading.value) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(responseForTaskDisplay.value.filter {
                    (it.title.contains(textFieldValue.value , ignoreCase = true) || it.description.contains(textFieldValue.value , ignoreCase = true))
                            && it.karmaPoints>=minKarmaPoints.value && it.location.contains(location.value , ignoreCase = true)
                }) { task ->
                    val taskReserved = remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .clickable {
                                if (!taskReserved.value) {
                                    navController.navigate("taskDetails/${task.taskId}/${task.karmaPoints}/${task.description}/${task.title}/${task.location}")
                                }
                            }
                            .size(400.dp, 150.dp)
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
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = task.taskId.toString() + ". ",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Text(
                                    text = task.title.uppercase(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.padding(start = 20.dp))
                                val triggerReserveTask = remember { mutableStateOf(false) }
                                val color = remember { mutableStateOf(Color(9, 174, 196, 255)) }
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            triggerReserveTask.value = true
                                        }
                                        .border(BorderStroke(5.dp, Color.LightGray))
                                        .size(170.dp, 60.dp),
                                    colors = CardDefaults.cardColors(
                                        color.value
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if(!taskReserved.value){
                                            Row() {
                                                Text(
                                                    text = "Reserve Task"
                                                )
                                            }
                                        }
                                        else {
                                            Row() {
                                                Image(
                                                    painter = painterResource(id = R.drawable.tick),
                                                    contentDescription = "tick mark"
                                                )
                                                Text(
                                                    text = "Task Reserved"
                                                )
                                            }
                                        }
                                    }
                                }
                                LaunchedEffect(triggerReserveTask.value) {
                                    if (triggerReserveTask.value) {
                                        reservingTasks(task,context)
                                        taskReserved.value = true
                                        color.value = Color(36, 140, 245, 255)
                                        triggerReserveTask.value = false
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 10.dp , start = 350.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate("notifications")
            }
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.scale(1.5f),
                tint = Color.Cyan
            )
            if(newNotification.value){
                Column(modifier = Modifier.padding(bottom = 20.dp, start = 25.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = "Notifications alert",
                        modifier = Modifier.scale(0.7f)
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 600.dp , start = 300.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate("createTasks")
            }
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = "Star",
                tint = Color.Blue,
                modifier = Modifier.scale(2.5f)
            )
        }
    }
    if(showFilters.value){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Filters(showFilters , minKarmaPoints , location)
        }
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun SideBar(navController: NavController) {
    val showSideBar = remember { mutableStateOf(false) }
    if(!showSideBar.value){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            IconButton(
                onClick = {
                    showSideBar.value = !showSideBar.value
                },
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu bar icon",
                    modifier = Modifier.scale(2f),
                    tint = Color.Black
                )
            }
        }
    }
    if(showSideBar.value) {
        Column(
            modifier = Modifier.size(100.dp , 1000.dp)
        ) {
            Card(
                modifier = Modifier.size(250.dp , 1000.dp),
                colors = CardDefaults.cardColors(Color(100, 129, 200)),
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    IconButton(
                        onClick = {
                            showSideBar.value = !showSideBar.value
                        },
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu bar icon",
                            modifier = Modifier.scale(2f)
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Box(
                        modifier = Modifier
                            .size(250.dp, 140.dp)
                            .clickable {
                                //do nothing
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Account icon",
                                modifier = Modifier.scale(2f)
                            )
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Text(
                                text = "Username : ${userName.value}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            Text(
                                text = "Karma Points : ${karma_points.value}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            Text(
                                text = "Rating : ${rating.value}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("displayTasks")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Show Available Tasks"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("approveTasks")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Approve Tasks"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("createTasks")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Create Tasks"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("transactionHistory")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Transaction history"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("myActiveTasks")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "My Active Tasks"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("getKarmaPoints")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Get karma points"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                navController.navigate("myReservedTasks")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "My Reserved Tasks"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(250.dp, 60.dp)
                            .clickable {
                                tokenForTheSession.value = ""
                                navController.navigate("loginPage")
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Logout"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskDetails(task: Task , navController : NavController) {
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    val executed = remember { mutableStateOf(false) }
    val originalCount = remember { mutableIntStateOf(0) }
    val newCount = remember { mutableIntStateOf(0) }
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
    val context = LocalContext.current
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
    val taskReserved = remember { mutableStateOf(false) }
    val taskCompleted = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Task Details",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Column(
            modifier = Modifier.padding(top = 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Title : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 35.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 55.dp))
                OutlinedTextField(
                    value = task.title.uppercase(),
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = task.description,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 250.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Location : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 35.dp))
                OutlinedTextField(
                    value = task.location,
                    enabled = false,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Karma points offered : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 12.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 20.dp))
                OutlinedTextField(
                    value = task.karmaPoints.toString(),
                    onValueChange = {
                        //do nothing
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .size(80.dp, 50.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        val triggerReserveTask = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(start = 20.dp))
            if (!taskReserved.value) {
                Card(
                    modifier = Modifier
                        .clickable {
                            triggerReserveTask.value = true
                        }
                        .size(170.dp, 60.dp)
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Reserve Task",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(170.dp, 60.dp),
                    colors = CardDefaults.cardColors(Color.Green.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row() {
                            Text(
                                text = "Task Reserved",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            LaunchedEffect(triggerReserveTask.value) {
                if (triggerReserveTask.value) {
                    taskReserved.value = reservingTasks(task,context)
                    taskReserved.value = true
                }
                triggerReserveTask.value = false
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        text = "Share to social media : ",
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.padding(start = 0.dp))
                Column() {
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    val shareText = buildString {
                        append("Task Title: ${task.title}\n")
                        append("Task Karma Points: ${task.karmaPoints}\n")
                        append("Task ID: ${task.taskId}\n")
                        append("Task Description: ${task.description}")
                    }
                    Log.d("shareText" , shareText)
                    ShareButton(
                        text = shareText , context = context
                    )
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
fun TaskDetailsSeekingApproval(task: TaskSeekingApproval , navController: NavController) {
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    val executed = remember { mutableStateOf(false) }
    val originalCount = remember { mutableIntStateOf(0) }
    val newCount = remember { mutableIntStateOf(0) }
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
    val context = LocalContext.current
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
    val taskApproved = remember { mutableStateOf(false) }
    val taskRejected = remember { mutableStateOf(false) }
    val triggerReviewBox = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Task Details",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Column(
            modifier = Modifier.padding(top = 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Title : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 35.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 55.dp))
                OutlinedTextField(
                    value = task.title.uppercase(),
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = task.description,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 250.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Location : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 35.dp))
                OutlinedTextField(
                    value = task.location,
                    enabled = false,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Karma points offered : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 12.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 20.dp))
                OutlinedTextField(
                    value = task.karmaPoints.toString(),
                    onValueChange = {
                        //do nothing
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .size(80.dp, 50.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        val triggerApproveTask = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 20.dp))
            if (!taskApproved.value) {
                Card(
                    modifier = Modifier
                        .clickable {
                            triggerApproveTask.value = true
                            triggerReviewBox.value = true
                        }
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(170.dp, 60.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Approve Task",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(170.dp, 60.dp),
                    colors = CardDefaults.cardColors(Color.Green.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row() {
                            Text(
                                text = "Task Approved",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            LaunchedEffect(triggerApproveTask.value) {
                if (triggerApproveTask.value && !taskRejected.value) {
                    taskApproved.value = approvingTasks(task,true,context)
                }
                triggerApproveTask.value = false
            }
            Spacer(modifier = Modifier.padding(start = 30.dp))
            val triggerRejectTask = remember { mutableStateOf(false) }
            if (!taskRejected.value) {
                Card(
                    modifier = Modifier
                        .clickable {
                            triggerRejectTask.value = true
                        }
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(170.dp, 60.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Reject Task",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(170.dp, 60.dp),
                    colors = CardDefaults.cardColors(Color.Red.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row() {
                            Text(
                                text = "Task Rejected",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            LaunchedEffect(triggerRejectTask.value) {
                if (triggerRejectTask.value && !taskApproved.value) {
                    taskRejected.value = approvingTasks(task,false,context)
                }
                triggerRejectTask.value = false
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        text = "Share to social media : ",
                        fontSize = 22.sp,
                        color = Color.LightGray
                    )
                }
                val shareText = buildString {
                    append("Task Title: ${task.title}\n")
                    append("Task Karma Points: ${task.karmaPoints}\n")
                    append("Task ID: ${task.taskId}\n")
                    append("Task Description: ${task.description}")
                }
                Spacer(modifier = Modifier.padding(start = 0.dp))
                Column() {
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    ShareButton(
                        text = shareText , context = context
                    )
                }
            }
        }
    }
    if(triggerReviewBox.value) {
        ReviewBox(task)
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun TaskDetailsForMyReservedTasks(navController: NavController , task: Task) {
    val responseForNotifications = remember { mutableStateOf<List<Notifications>>(emptyList()) }
    val executed = remember { mutableStateOf(false) }
    val originalCount = remember { mutableIntStateOf(0) }
    val newCount = remember { mutableIntStateOf(0) }
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
    val context = LocalContext.current
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
    val taskReserved = remember { mutableStateOf(false) }
    val taskCompleted = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Task Details",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Column(
            modifier = Modifier.padding(top = 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Title : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 35.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 55.dp))
                OutlinedTextField(
                    value = task.title.uppercase(),
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = task.description,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 250.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Location : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 35.dp))
                OutlinedTextField(
                    value = task.location,
                    enabled = false,
                    onValueChange = {
                        //do nothing
                    },
                    modifier = Modifier
                        .size(250.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Karma points offered : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 12.dp, start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 20.dp))
                OutlinedTextField(
                    value = task.karmaPoints.toString(),
                    onValueChange = {
                        //do nothing
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .size(80.dp, 50.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val triggerCompleteTask = remember { mutableStateOf(false) }
            if(!taskCompleted.value){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clickable {
                                triggerCompleteTask.value = true
                            }
                            .size(180.dp, 60.dp)
                            .border(
                                width = 3.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Complete Task",
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clickable {
                                triggerCompleteTask.value = true
                            }
                            .size(180.dp, 60.dp)
                            .border(
                                width = 3.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Green.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Submitted",
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            LaunchedEffect(triggerCompleteTask.value) {
                if (triggerCompleteTask.value) {
                    taskCompleted.value = completingTasks(task,context)
                }
                triggerCompleteTask.value = false
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        text = "Share to social media : ",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                }
                val shareText = buildString {
                    append("Task Title: ${task.title}\n")
                    append("Task Karma Points: ${task.karmaPoints}\n")
                    append("Task ID: ${task.taskId}\n")
                    append("Task Description: ${task.description}")
                }
                Spacer(modifier = Modifier.padding(start = 0.dp))
                Column() {
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    ShareButton(
                        text = shareText , context = context
                    )
                }
            }
        }
    }
    SideBar(navController = navController)
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

suspend fun reservingTasks(task: Task , context : Context) : Boolean {
    try {
        val responseForReservingTask = retrofitServiceForReservingTask.reserveTask(taskId = task.taskId , token = tokenForTheSession.value)
        Log.d("message" , responseForReservingTask.message)
        return true
    }
    catch(e : Exception) {
        Log.d("error in reserving tasks" , "${e.message}")
        Toast.makeText(context ,e.message, Toast.LENGTH_LONG).show()
        return false
    }
}

suspend fun completingTasks(task : Task , context: Context) : Boolean {
    try {
        val responseForCompletingTask = retrofitServiceForCompletingTask.completeTask(taskId = task.taskId , token = tokenForTheSession.value)
        Log.d("message" , responseForCompletingTask.message)
        return true
    }
    catch(e : Exception) {
        Log.d("error in completing tasks" , "${e.message}")
        Toast.makeText(context ,e.message,Toast.LENGTH_LONG).show()
        return false
    }
}

suspend fun approvingTasks(task: TaskSeekingApproval, approved: Boolean , context: Context) : Boolean {
    try {
        val responseForApproving = retrofitServiceForApprovingTasks.approveTask(task.taskId,
            tokenForTheSession.value,ApprovalBody(approved))
        Log.d("message" , responseForApproving.message)
        return true
    }
    catch(e : Exception) {
        Log.d("error in approving tasks" , "${e.message}")
        Toast.makeText(context ,e.message,Toast.LENGTH_LONG).show()
        return false
    }
}