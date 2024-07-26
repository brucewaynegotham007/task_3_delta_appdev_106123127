package com.example.merit_match_delta_task_3

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

@Composable
fun CreateTasks(navController: NavController) {
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
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val karmaPointsOffered = remember { mutableIntStateOf(0) }
    val location = remember { mutableStateOf("") }
    val taskCreated = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Task",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 70.dp))
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
                    modifier = Modifier.padding(top = 8.dp , start = 35.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 55.dp))
                OutlinedTextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
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
                    text = "Description : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = {
                        description.value = it
                    },
                    modifier = Modifier
                        .size(250.dp, 250.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
            ) {
                Text(
                    text = "Location : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp , start = 25.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 30.dp))
                OutlinedTextField(
                    value = location.value,
                    onValueChange = {
                        location.value = it
                    },
                    modifier = Modifier
                        .size(260.dp, 60.dp)
                        .border(BorderStroke(2.dp, Color.Black)),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 30.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Karma points offered : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 12.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 20.dp))
                OutlinedTextField(
                    value = karmaPointsOffered.value.toString(),
                    onValueChange = {
                        val check = it.toIntOrNull()
                        if (check != null) {
                            karmaPointsOffered.value = check
                        } else {
                            //no change
                        }
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
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 50.dp))
            val createTasks = remember { mutableStateOf(false) }
            if(!createTasks.value){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clickable {
                                createTasks.value = true
                            }
                            .size(120.dp, 60.dp)
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
                                text = "Create",
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            else if(!taskCreated.value) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clickable {
                                createTasks.value = true
                            }
                            .size(120.dp, 60.dp)
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
                                text = "Create",
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
                                createTasks.value = true
                            }
                            .size(120.dp, 60.dp)
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
                                text = "Created",
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            LaunchedEffect(createTasks.value) {
                if (createTasks.value) {
                    try {
                        val responseForTaskCreation = retrofitServiceForTaskCreation.createTask(
                            tokenForTheSession.value,
                            TaskCreationBody(
                                title = title.value,
                                description = description.value,
                                karmaPoints = karmaPointsOffered.value,
                                location = location.value
                            )
                        )
                        Log.d("message", responseForTaskCreation.message)
                        Log.d("task_id", responseForTaskCreation.taskId.toString())
                        taskCreated.value = true
                    } catch (e: Exception) {
                        Log.d("error in creating tasks", "${e.message}")
                    }
                }
            }
            if (taskCreated.value) {
                navController.navigate("displayTasks")
                taskCreated.value = false
            }
        }
    }
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
            modifier = Modifier.size(200.dp , 1000.dp)
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
                    Box(
                        modifier = Modifier
                            .size(200.dp, 140.dp)
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
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun MyTasks(navController: NavController) {
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
    val responseForMyTasks = remember { mutableStateOf<List<Task>>(emptyList()) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForMyTasks.value = retrofitServiceForMyTask.getMyTasks(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForMyTasks.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "description : ${i.description} , " +
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
                    text = "My Active Tasks",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.White
                )
            }
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

                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Column() {
            if (responseForMyTasks.value.isEmpty()) {
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
                    items(responseForMyTasks.value.filter {
                        it.title.contains(textFieldValue.value , ignoreCase = true) || it.description.contains(textFieldValue.value , ignoreCase = true)
                    }) { task ->
                        val taskApproved = remember { mutableStateOf(false) }
                        val taskRejected = remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("editTasks/${task.taskId}/${task.karmaPoints}/${task.description}/${task.title}/${task.location}")
                                }
                                .size(400.dp, 120.dp)
                                .padding(start = 10.dp)
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.2f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.padding(top = 15.dp))
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
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
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 20.dp))
                                    Text(
                                        text = "Karma Points :  " + task.karmaPoints.toString(),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
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
            modifier = Modifier.size(200.dp , 1000.dp)
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
                    Box(
                        modifier = Modifier
                            .size(200.dp, 140.dp)
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
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}

@Composable
fun EditTasks(task: Task , navController: NavController) {
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
    val title = remember { mutableStateOf(task.title) }
    val description = remember { mutableStateOf(task.description) }
    val karmaPointsOffered = remember { mutableIntStateOf(task.karmaPoints) }
    val location = remember { mutableStateOf(task.location) }
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
                text = "Edit Task",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
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
                    modifier = Modifier.padding(top = 8.dp , start = 35.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 55.dp))
                OutlinedTextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
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
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = {
                        description.value = it
                    },
                    modifier = Modifier
                        .size(250.dp, 250.dp)
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
                    text = "Location : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 35.dp))
                OutlinedTextField(
                    value = location.value,
                    enabled = false,
                    onValueChange = {
                        location.value = it
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
                    modifier = Modifier.padding(top = 12.dp , start = 15.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 20.dp))
                OutlinedTextField(
                    value = karmaPointsOffered.value.toString(),
                    onValueChange = {
                        val check = it.toIntOrNull()
                        if (check != null) {
                            karmaPointsOffered.value = check
                        } else {
                            //no change
                        }
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
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 80.dp))
            val editTasks = remember { mutableStateOf(false) }
            if(!editTasks.value){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clickable {
                                editTasks.value = true
                            }
                            .size(120.dp, 60.dp)
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
                                text = "Edit",
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
                                editTasks.value = true
                            }
                            .size(120.dp, 60.dp)
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
                                text = "Edited",
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            val taskEdited = remember { mutableStateOf(false) }
            LaunchedEffect(editTasks.value) {
                if (editTasks.value) {
                    try {
                        val responseForTaskEditing = retrofitServiceForEditingTasks.editTasks(
                            task.taskId,
                            tokenForTheSession.value,
                            EditTasks(
                                title = title.value,
                                description = description.value,
                                karmaPoints = karmaPointsOffered.value
                            )
                        )
                        Log.d("message", responseForTaskEditing.message)
                        taskEdited.value = true
                    } catch (e: Exception) {
                        Log.d("error in editing tasks", "${e.message}")
                    }
                }
            }
            if (taskEdited.value) {
                navController.navigate("myActiveTasks")
                taskEdited.value = false
            }
        }
    }
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
            modifier = Modifier.size(200.dp , 1000.dp)
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
                    Box(
                        modifier = Modifier
                            .size(200.dp, 140.dp)
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
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
                            .size(200.dp, 60.dp)
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
    if(newNotification.value) {
        NotificationOnTop(navController)
    }
}