package com.example.merit_match_delta_task_3

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.merit_match_delta_task_3.ui.theme.Merit_match_delta_task_3Theme
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Merit_match_delta_task_3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

//add a proper system for review ..

val userName = mutableStateOf("")
val karma_points = mutableStateOf("")
val rating = mutableIntStateOf(0)
val tokenForTheSession = mutableStateOf("")
val responseForTaskDisplayFromLogin = mutableStateOf<List<Task>>(emptyList())

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "introPage",
        enterTransition = {
            fadeIn(animationSpec = tween(1000))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(1000))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(1000))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(1000))
        }
    ) {
        composable("introPage") {
            IntroPage(navController = navController)
        }
        composable("loginPage") {
            LoginPage(navController = navController)
        }
        composable("signUpPage") {
            SignUpPage(navController)
        }
        composable("createTasks") {
            CreateTasks(navController)
        }
        composable("displayTasks") {
            DisplayTasks(navController = navController)
        }
        composable("approveTasks") {
            ApprovalPage(navController = navController)
        }
        composable("transactionHistory") {
            TransactionHistory(navController)
        }
        composable("myActiveTasks") {
            MyTasks(navController = navController)
        }
        composable("myReservedTasks") {
            MyReservedTasks(navController = navController)
        }
        composable("getKarmaPoints") {
            GetKarmaPoints(navController = navController)
        }
        composable("notifications") {
            Notifications(navController = navController)
        }
        composable(
            route = "transactionsBetweenTwoUsers/{otherId}/{otherUser}",
            arguments = listOf(
                navArgument("otherId") {type = NavType.IntType}
            )
        ) {backStackEntry ->
            val otherId = backStackEntry.arguments?.getInt("otherId") ?: 0
            val otherUser = backStackEntry.arguments?.getString("otherUser") ?: ""
            TransactionsBetweenTwoUsers(navController , otherId , otherUser)
        }
        composable(
            route = "taskDetails/{taskId}/{karmaPoints}/{description}/{title}/{location}" ,
            arguments = listOf(
                navArgument("title") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("karmaPoints") {type = NavType.IntType},
                navArgument("taskId") {type = NavType.IntType},
                navArgument("location") {type = NavType.StringType}
            )
        ) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val karmaPoints = backStackEntry.arguments?.getInt("karmaPoints") ?: 0
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val location = backStackEntry.arguments?.getString("location") ?: "na"
            val task = Task(title = title , description = description , karmaPoints = karmaPoints , taskId = taskId , location = location)
            TaskDetails(task = task , navController = navController)
        }
        composable(
            route = "taskDetailsSeekingApproval/{taskId}/{karmaPoints}/{description}/{title}/{location}/{helperId}" ,
            arguments = listOf(
                navArgument("title") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("karmaPoints") {type = NavType.IntType},
                navArgument("taskId") {type = NavType.IntType},
                navArgument("location") {type = NavType.StringType},
                navArgument("helperId") {type = NavType.IntType}
            )
        ) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val karmaPoints = backStackEntry.arguments?.getInt("karmaPoints") ?: 0
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val location = backStackEntry.arguments?.getString("location") ?: "na"
            val helperId = backStackEntry.arguments?.getInt("helperId") ?: 0
            val taskSeekingApproval = TaskSeekingApproval(title = title , description = description , karmaPoints = karmaPoints , taskId = taskId , location = location , helperId = helperId)
            TaskDetailsSeekingApproval(task = taskSeekingApproval , navController = navController)
        }
        composable(
            route = "editTasks/{taskId}/{karmaPoints}/{description}/{title}/{location}" ,
            arguments = listOf(
                navArgument("title") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("karmaPoints") {type = NavType.IntType},
                navArgument("taskId") {type = NavType.IntType},
                navArgument("location") {type = NavType.StringType}
            )
        ) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val karmaPoints = backStackEntry.arguments?.getInt("karmaPoints") ?: 0
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val location = backStackEntry.arguments?.getString("location") ?: "na"
            val task = Task(title = title , description = description , karmaPoints = karmaPoints , taskId = taskId , location = location)
            EditTasks(task = task , navController = navController)
        }
        composable(
            route = "taskDetailsForMyReservedTasks/{taskId}/{karmaPoints}/{description}/{title}/{location}" ,
            arguments = listOf(
                navArgument("title") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("karmaPoints") {type = NavType.IntType},
                navArgument("taskId") {type = NavType.IntType},
                navArgument("location") {type = NavType.StringType}
            )
        ) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val karmaPoints = backStackEntry.arguments?.getInt("karmaPoints") ?: 0
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val location = backStackEntry.arguments?.getString("location") ?: "na"
            val task = Task(title = title , description = description , karmaPoints = karmaPoints , taskId = taskId , location = location)
            TaskDetailsForMyReservedTasks(task = task , navController = navController)
        }
    }
}

@Composable
fun IntroPage(navController: NavController) {
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(3f)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "MeritMatch Digital",
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.padding(top = 70.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("loginPage")
                }
                .padding(horizontal = 20.dp)
                .height(60.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("signUpPage")
                }
                .padding(horizontal = 20.dp)
                .height(60.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun LoginPage(navController: NavController) {
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
    val passwordVisible = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val enteredDetails = remember { mutableStateOf(false) }
        Text(
            text = "Welcome,",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Glad to see you!",
            fontSize = 40.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(top = 80.dp))
        Column() {
            val username = remember { mutableStateOf("") }
            val pass = remember { mutableStateOf("") }
            Row() {
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {
                        username.value = it
                        Log.d("username", username.value)
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textIndent = TextIndent(5.sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Username",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                OutlinedTextField(
                    value = pass.value,
                    onValueChange = {
                        pass.value = it
                        Log.d("password", pass.value)
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textIndent = TextIndent(5.sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Password",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    visualTransformation = (
                        if(passwordVisible.value) {
                            PasswordVisualTransformation()
                        }
                        else {
                            VisualTransformation.None
                        }
                    )
                )
            }
            val context = LocalContext.current
            LaunchedEffect(enteredDetails.value) {
                if (enteredDetails.value) {
                    val loginBody = LoginBody(username = username.value, password = pass.value)
                    try {
                        val responseBodyForLogin = retrofitServiceForLogin.loginUser(loginBody)
                        val jsonStringForLogin = responseBodyForLogin.string()
                        val jsonObjectForLogin = JSONObject(jsonStringForLogin)
                        val token = jsonObjectForLogin.getString("token")
                        tokenForTheSession.value = token
                        Log.d("token", tokenForTheSession.value)
                        val responseBodyForUserData =
                            retrofitServiceForUserData.getUserData(token)
                        val jsonStringForUserData = responseBodyForUserData.string()
                        val jsonObjectForUserData = JSONObject(jsonStringForUserData)
                        userName.value = jsonObjectForUserData.getString("username")
                        karma_points.value = jsonObjectForUserData.getString("karma_points")
                        rating.value = jsonObjectForUserData.getInt("rating")
                        Log.d("username", username.value)
                        Log.d("karma_points", karma_points.value)
                        responseForTaskDisplayFromLogin.value = retrofitServiceForTaskDisplay.receiveTaskData(token = tokenForTheSession.value)
                        for(i in responseForTaskDisplayFromLogin.value) {
                            Log.d(
                                "task ${i.taskId}" ,
                                "description : ${i.description} , " +
                                        "title : ${i.title} , " +
                                        "karma_points : ${i.karmaPoints}"
                            )
                        }
                        navController.navigate("displayTasks")
                    } catch (e: Exception) {
                        Log.d("error in logging in", "${e.message}")
                        Toast.makeText(context ,e.message,Toast.LENGTH_LONG).show()
                        navController.navigate("loginPage")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 70.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    enteredDetails.value = true
                }
                .height(60.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 120.dp))
        Row() {
            Text(
                text = "Don't have an account ?",
                modifier = Modifier.padding(top = 10.dp , start = 15.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Sign Up Now",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable {
                        navController.navigate("signUpPage")
                    }
                    .padding(top = 10.dp, start = 15.dp)
            )
        }
    }
    Column(
        modifier = Modifier.padding(top = 392.dp , start = 340.dp)
    ) {
        if(passwordVisible.value){
            Image(
                painter = painterResource(R.drawable.sharp_visibility_off_24),
                contentDescription = "show password",
                modifier = Modifier
                    .scale(1.4f)
                    .clickable {
                        passwordVisible.value = false
                    }
            )
        }
        else {
            Image(
                painter = painterResource(R.drawable.sharp_visibility_24),
                contentDescription = "show password",
                modifier = Modifier
                    .scale(1.4f)
                    .clickable {
                        passwordVisible.value = true
                    }
            )
        }
    }
}

@Composable
fun LoginBox(navController: NavController) {
    Card(
        modifier = Modifier
            .size(400.dp, 350.dp)
            .padding(start = 15.dp),
        colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.2f))
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LOGIN",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color.White,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Column() {
            val username = remember { mutableStateOf("") }
            val pass = remember { mutableStateOf("") }
            val enteredDetails = remember { mutableStateOf(false) }
            Row() {
                Text(
                    text = "Username : ",
                    modifier = Modifier.padding(top = 10.dp , start = 15.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {
                        username.value = it
                        Log.d("username", username.value)
                    },
                    modifier = Modifier.size(230.dp , 50.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                Text(
                    text = "Password : ",
                    modifier = Modifier.padding(top = 10.dp , start = 15.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                OutlinedTextField(
                    value = pass.value,
                    onValueChange = {
                        pass.value = it
                        Log.d("password", pass.value)
                    },
                    modifier = Modifier.size(230.dp , 50.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 30.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        enteredDetails.value = true
                    },
                    modifier = Modifier.size(130.dp , 50.dp),
                    colors = ButtonDefaults.buttonColors(Color(100,150,255))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Login",
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                Text(
                    text = "Don't have an account ?",
                    modifier = Modifier.padding(top = 10.dp , start = 15.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
                Text(
                    text = "Sign up",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(10,150,255),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .clickable {
                            navController.navigate("signUpPage")
                        }
                        .padding(top = 10.dp, start = 15.dp)
                )
            }
            LaunchedEffect(enteredDetails.value) {
                if (enteredDetails.value) {
                    val loginBody = LoginBody(username = username.value, password = pass.value)
                    try {
                        val responseBodyForLogin = retrofitServiceForLogin.loginUser(loginBody)
                        val jsonStringForLogin = responseBodyForLogin.string()
                        val jsonObjectForLogin = JSONObject(jsonStringForLogin)
                        val token = jsonObjectForLogin.getString("token")
                        tokenForTheSession.value = token
                        Log.d("token", tokenForTheSession.value)
                        val responseBodyForUserData =
                            retrofitServiceForUserData.getUserData(token)
                        val jsonStringForUserData = responseBodyForUserData.string()
                        val jsonObjectForUserData = JSONObject(jsonStringForUserData)
                        userName.value = jsonObjectForUserData.getString("username")
                        karma_points.value = jsonObjectForUserData.getString("karma_points")
                        Log.d("username", username.value)
                        Log.d("karma_points", karma_points.value)
                        responseForTaskDisplayFromLogin.value = retrofitServiceForTaskDisplay.receiveTaskData(token = tokenForTheSession.value)
                        for(i in responseForTaskDisplayFromLogin.value) {
                            Log.d(
                                "task ${i.taskId}" ,
                                "description : ${i.description} , " +
                                        "title : ${i.title} , " +
                                        "karma_points : ${i.karmaPoints}"
                            )
                        }
                        navController.navigate("displayTasks")
                    } catch (e: Exception) {
                        Log.d("error in logging in", "${e.message}")
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpPage(navController: NavController) {
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
    val pass = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val enteredDetails = remember { mutableStateOf(false) }
        Text(
            text = "Create Account,",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "to get started now!",
            fontSize = 40.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(top = 80.dp))
        Column() {
            val username = remember { mutableStateOf("") }
            Row() {
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {
                        username.value = it
                        Log.d("username", username.value)
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textIndent = TextIndent(5.sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Username",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                OutlinedTextField(
                    value = pass.value,
                    onValueChange = {
                        pass.value = it
                        Log.d("password", pass.value)
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textIndent = TextIndent(5.sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Password",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                OutlinedTextField(
                    value = confirmPass.value,
                    onValueChange = {
                        confirmPass.value = it
                        Log.d("confirm password", pass.value)
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textIndent = TextIndent(5.sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Confirm Password",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            LaunchedEffect(enteredDetails.value) {
                if (enteredDetails.value) {
                    val signupBody = SignupBody(username.value, pass.value)
                    try {
                        val responseForSignUp =
                            retrofitServiceForSignup.signupNewUser(signupBody)
                        Log.d("message", responseForSignUp.message)
                        Toast.makeText(context ,responseForSignUp.message,Toast.LENGTH_LONG).show()
                        navController.navigate("loginPage")
                    } catch (e: Exception) {
                        Log.d("Error", "${e.message}")
                        Toast.makeText(context ,e.message,Toast.LENGTH_LONG).show()
                        navController.navigate("signUpPage")
                    }
                    enteredDetails.value = false
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 70.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    if (pass.value == confirmPass.value) {
                        enteredDetails.value = true
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Password and confirm password don't match",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                }
                .height(60.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 120.dp))
        Row() {
            Text(
                text = "Already have an account ?",
                modifier = Modifier.padding(top = 10.dp , start = 15.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Login Now",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable {
                        navController.navigate("loginPage")
                    }
                    .padding(top = 10.dp, start = 15.dp)
            )
        }
    }
}

@Composable
fun SignUpBox(navController: NavController) {
    Column() {
        Spacer(modifier = Modifier.padding(top = 200.dp))
        Card(
            modifier = Modifier
                .size(400.dp, 400.dp)
                .padding(start = 15.dp),
            colors = CardDefaults.cardColors(Color.LightGray)
        ) {
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SIGN UP",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.padding(top = 35.dp))
            Column() {
                val username = remember { mutableStateOf("") }
                val pass = remember { mutableStateOf("") }
                val confirmPass = remember { mutableStateOf("") }
                val enteredDetails = remember { mutableStateOf(false) }
                Row() {
                    Text(
                        text = "Username : ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 15.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = {
                            username.value = it
                        },
                        modifier = Modifier.size(250.dp, 50.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                val passBoxesColor = remember { mutableStateOf(Color.DarkGray) }
                if (pass.value != "" && confirmPass.value != "") {
                    if (pass.value == confirmPass.value) passBoxesColor.value = Color.Green
                    else passBoxesColor.value = Color.Red
                } else {
                    passBoxesColor.value = Color.DarkGray
                }
                Row() {
                    Text(
                        text = "Password : ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 15.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    OutlinedTextField(
                        value = pass.value,
                        onValueChange = {
                            pass.value = it
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = passBoxesColor.value,
                            unfocusedBorderColor = passBoxesColor.value
                        ),
                        modifier = Modifier.size(250.dp, 50.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Row() {
                    Column() {
                        Text(
                            text = "Confirm ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 15.dp)
                        )
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 15.dp)
                        )
                    }
                    Text(
                        text = " : ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    OutlinedTextField(
                        value = confirmPass.value,
                        onValueChange = {
                            confirmPass.value = it
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = passBoxesColor.value,
                            unfocusedBorderColor = passBoxesColor.value
                        ),
                        modifier = Modifier.size(250.dp, 50.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(top = 40.dp))
                val userCreated = remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (pass.value == confirmPass.value) {
                                enteredDetails.value = true
                            } else {
                                pass.value = ""
                                confirmPass.value = ""
                                //display message that the password and confirm password didn't match
                            }
                        },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sign up",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
                LaunchedEffect(enteredDetails.value) {
                    if (enteredDetails.value) {
                        val signupBody = SignupBody(username.value, pass.value)
                        try {
                            val responseForSignUp =
                                retrofitServiceForSignup.signupNewUser(signupBody)
                            Log.d("message", responseForSignUp.message)
                            navController.navigate("loginPage")
                        } catch (e: Exception) {
                            Log.d("Error", "${e.message}")
                        }
                        enteredDetails.value = false
                    }
                }
            }
        }
    }
}

