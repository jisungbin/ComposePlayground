@file:Suppress("MayBeConstant")
@file:NoLiveLiterals

package land.sungbin.androidplayground.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

class NumberHolder : ViewModel() {
    var number by mutableStateOf(1)
    override fun onCleared() {
        super.onCleared()
    }
}

class PlaygroundActivity : ComponentActivity() {
    private var numberHolder = NumberHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

            }
        }
    }

    override fun onStop() {
        println("!!! STOP !!!")
        super.onStop()
    }

    override fun onDestroy() {
        println("!!! DESTROY !!!")
        super.onDestroy()
    }
}
