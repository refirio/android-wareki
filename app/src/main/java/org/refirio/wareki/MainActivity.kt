package org.refirio.wareki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.refirio.wareki.ui.theme.WarekiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarekiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen() {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("和暦変換", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("年月日を入力すると和暦で表示します。")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (inputText.text.length == 8 && inputText.text.toIntOrNull() != null) {
            val year  = inputText.text.substring(0, 4).toInt()
            val month = inputText.text.substring(4, 6).toInt()
            val day   = inputText.text.substring(6, 8).toInt()

            val (wareki_label, wareki_year) = getWareki(year, month, day)

            Text("和暦は${wareki_label}${wareki_year}年${month}月${day}日です。", fontSize = 20.sp)
        } else {
            Text("8桁の数字で入力してください。", fontSize = 20.sp)
        }
    }
}

fun getWareki(year: Int, month: Int, day: Int): Pair<String, Int> {
    val date = String.format("%04d%02d%02d", year, month, day).toInt()
    return when {
        date >= 20190501 -> Pair("令和", year - 2018)
        date >= 19890108 -> Pair("平成", year - 1988)
        date >= 19261225 -> Pair("昭和", year - 1925)
        date >= 19120730 -> Pair("大正", year - 1911)
        date >= 18680125 -> Pair("明治", year - 1867)
        else -> Pair("", year)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    WarekiTheme {
        AppScreen()
    }
}
