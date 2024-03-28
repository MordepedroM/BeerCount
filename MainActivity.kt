package com.example.beercount 

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beercount.ui.theme.BeerCountTheme 
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeerCountTheme { 
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White 
                ) {
                    BeerCount() 
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeerCount() {
    var lastDrankDate by remember { mutableStateOf(loadLastDrankDate()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "pedroloureiro.me \nDays Without Drinking:",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val daysWithoutDrinking = calculateDaysWithoutDrinking(lastDrankDate)
        Text(
            "$daysWithoutDrinking",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Last Drank: $lastDrankDate",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Button(
            onClick = {
                lastDrankDate = updateLastDrankDate()
                keyboardController?.hide()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("I drank today")
        }
    }
}

fun calculateDaysWithoutDrinking(lastDrankDate: String): Int {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Calendar.getInstance().time
        val lastDrank = dateFormat.parse(lastDrankDate) ?: return 0
        val difference = today.time - lastDrank.time
        (difference / (1000 * 60 * 60 * 24)).toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun loadLastDrankDate(): String {
    return "2024-03-25"
}

fun updateLastDrankDate(): String {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(Calendar.getInstance().time)
        today
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
