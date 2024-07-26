package com.example.merit_match_delta_task_3

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewBox(task: TaskSeekingApproval) {
    val showDialog = remember { mutableStateOf(true) }
    val ratingGiven = remember { mutableIntStateOf(0) }
    if(showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                //do nothing
            },
            modifier = Modifier.size(320.dp, 200.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(Color(120,20,220))
            ) {
                Column() {
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Please Rate Your Experience",
                            fontSize = 20.sp,
                            color = Color.Green,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val starSelectedone = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                starSelectedone.value = !starSelectedone.value
                                if(starSelectedone.value) {
                                    ratingGiven.intValue++
                                }
                                else {
                                    ratingGiven.intValue--
                                }
                            }
                        ) {
                            val starColor = when (starSelectedone.value) {
                                true -> Color.Yellow
                                else -> Color.White
                            }
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = starColor,
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        val starSelectedtwo = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                starSelectedtwo.value = !starSelectedtwo.value
                                if(starSelectedtwo.value) {
                                    ratingGiven.intValue++
                                }
                                else {
                                    ratingGiven.intValue--
                                }
                            }
                        ) {
                            val starColor = when (starSelectedtwo.value) {
                                true -> Color.Yellow
                                else -> Color.White
                            }
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = starColor,
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        val starSelectedthree = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                starSelectedthree.value = !starSelectedthree.value
                                if(starSelectedthree.value) {
                                    ratingGiven.intValue++
                                }
                                else {
                                    ratingGiven.intValue--
                                }
                            }
                        ) {
                            val starColor = when (starSelectedthree.value) {
                                true -> Color.Yellow
                                else -> Color.White
                            }
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = starColor,
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        val starSelectedfour = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                starSelectedfour.value = !starSelectedfour.value
                                if(starSelectedfour.value) {
                                    ratingGiven.intValue++
                                }
                                else {
                                    ratingGiven.intValue--
                                }
                            }
                        ) {
                            val starColor = when (starSelectedfour.value) {
                                true -> Color.Yellow
                                else -> Color.White
                            }
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = starColor,
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        val starSelectedfive = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                starSelectedfive.value = !starSelectedfive.value
                                if(starSelectedfive.value) {
                                    ratingGiven.intValue++
                                }
                                else {
                                    ratingGiven.intValue--
                                }
                            }
                        ) {
                            val starColor = when (starSelectedfive.value) {
                                true -> Color.Yellow
                                else -> Color.White
                            }
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = starColor,
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    val triggerReview = remember { mutableStateOf(false) }
                    Row() {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    triggerReview.value = true
                                },
                                colors = ButtonDefaults.buttonColors(Color.Green)
                            ) {
                                Text(
                                    text = "Submit",
                                    fontSize = 18.sp,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    val context = LocalContext.current
                    LaunchedEffect(triggerReview.value) {
                        if(triggerReview.value) {
                            try{
                                val responseForRatingUpdate =
                                    retrofitServiceForHandlingRating.updateRating(
                                        tokenForTheSession.value,
                                        RatingBody(task.taskId, ratingGiven.intValue)
                                    )
                                Toast.makeText(context , responseForRatingUpdate.message , Toast.LENGTH_LONG).show()
                                showDialog.value = false
                            }
                            catch(e : Exception) {
                                Log.d("error" , "${e.message}")
                                Toast.makeText(context , e.message , Toast.LENGTH_LONG).show()
                                showDialog.value = false
                            }
                            triggerReview.value = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetKarmaPoints(navController: NavController) {
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
                text = "Get Karma Points",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                textDecoration = TextDecoration.Underline,
                color = Color.White
            )
        }
    }
    val availability = remember { mutableStateOf(DailyBonusAvailability(false)) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            availability.value = retrofitServiceForDailyBonusAvailability.getDailyBonusAvailability(token = tokenForTheSession.value)
            Log.d("availability" , availability.value.available.toString())
        }
        catch(e : Exception) {
            Log.d("error in displaying availability of bonus" , "${e.message}")
        }
    }
    val collected = remember { mutableStateOf(false) }
    val triggerCollection = remember { mutableStateOf(false) }
    if(availability.value.available && !collected.value) {
        Column {
            Spacer(modifier = Modifier.padding(top = 80.dp))
            Row() {
                Spacer(modifier = Modifier.padding(start = 15.dp))
                Text(
                    text = "** Daily Bonus : ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                Spacer(modifier = Modifier.padding(start = 15.dp))
                Card(
                    colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .size(370.dp, 60.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = "Collect your daily login bonus here : ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.padding(start = 10.dp))
                            Image(
                                painter = painterResource(id = R.drawable.baseline_monetization_on_24),
                                contentDescription = "Collect",
                                modifier = Modifier
                                    .clickable {
                                        triggerCollection.value = true
                                    }
                                    .scale(1.5f)
                            )
                        }
                    }
                }
            }
        }
    }
    val karmaPointsAfterChange = remember { mutableStateOf(DailyBonusAdded(karma_points.value.toInt())) }
    LaunchedEffect(triggerCollection.value) {
        if(triggerCollection.value) {
            Log.d("token", tokenForTheSession.value)
            try {
                karmaPointsAfterChange.value =
                    retrofitServiceForKarmaPointsAfterChange.getDailyBonusAvailability(token = tokenForTheSession.value)
                Log.d("points after change", karmaPointsAfterChange.value.karmaPoints.toString())
                collected.value = true
            } catch (e: Exception) {
                Log.d("error in displaying tasks updating daily bonus", "${e.message}")
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
fun MyReservedTasks(navController: NavController) {
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
    val responseForMyReservedTasks = remember { mutableStateOf<List<Task>>(emptyList()) }
    LaunchedEffect(Unit) {
        Log.d("token" , tokenForTheSession.value)
        try {
            responseForMyReservedTasks.value = retrofitServiceForMyReservedTask.getMyReservedTasks(token = tokenForTheSession.value)
            delay(5000L)
            for(i in responseForMyReservedTasks.value) {
                Log.d(
                    "task ${i.taskId}" ,
                    "description : ${i.description} , " +
                            "title : ${i.title} , " +
                            "karma_points : ${i.karmaPoints}" +
                    "location : ${i.location}"
                )
            }
        }
        catch(e : Exception) {
            Log.d("error in displaying tasks needing approval" , "${e.message}")
        }
    }
    val showFilters = remember { mutableStateOf(false) }
    val minKarmaPoints = remember { mutableIntStateOf(0) }
    val location = remember { mutableStateOf("") }
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
                    text = "My Reserved Tasks",
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
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column() {
            if (responseForMyReservedTasks.value.isEmpty()) {
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
                    items(responseForMyReservedTasks.value.filter {
                        it.title.contains(textFieldValue.value , ignoreCase = true) || it.description.contains(textFieldValue.value , ignoreCase = true)
                    }) { task ->
                        val taskApproved = remember { mutableStateOf(false) }
                        val taskRejected = remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("taskDetailsForMyReservedTasks/${task.taskId}/${task.karmaPoints}/${task.description}/${task.title}/${task.location}")
                                }
                                .size(400.dp, 120.dp)
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
                        Spacer(modifier = Modifier.padding(top = 20.dp))
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
    if(showFilters.value){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Filters(showFilters , minKarmaPoints , location)
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
                    Spacer(modifier = Modifier.padding(top = 10.dp))
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

data class UserDeets(
    val username : String,
    val rating : Int,
    val noOfPeopleHelped : Int
)

@Composable
fun ShowUserDetails(userId : Int , navController: NavController) {
    val userDeets = UserDeets("Batman", 3 , 5)
    val otherUserData = remember { mutableStateOf(userDeets) }
    val context = LocalContext.current
    val ready = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        try {
            val responseForOtherUserData = retrofitServiceForGettingOtherUserData.getOtherUserData(tokenForTheSession.value , OtherUserData(userId))
            otherUserData.value = UserDeets(responseForOtherUserData.username , responseForOtherUserData.rating , 5)
            ready.value = true
        }
        catch(e : Exception) {
            Log.d("error" , "${e.message}")
            Toast.makeText(context , e.message , Toast.LENGTH_LONG)
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
    if(ready.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .size(300.dp, 400.dp)
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "user image",
                        modifier = Modifier.scale(5f)
                    )
                    Spacer(modifier = Modifier.padding(top = 90.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Username        : ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(start = 10.dp))
                        Text(
                            text = otherUserData.value.username,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Rating               : ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(
                            text = otherUserData.value.rating.toString(),
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column() {
                            Text(
                                text = "Number of ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "people helped ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = " : ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(
                            text = otherUserData.value.noOfPeopleHelped.toString(),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
    else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .size(300.dp, 400.dp)
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
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
        ){
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
}