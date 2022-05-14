@file:Suppress("SetTextI18n", "unused", "OPT_IN_IS_NOT_ENABLED")

package land.sungbin.androidplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

class A(var b: A?)

class MainActivity : ComponentActivity() {

    /*private lateinit var binding: ActivityMainBinding
     private val vm: MainViewModel by viewModels()*/

    private val someValue = A(null)

    // @OptIn(InternalComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableLiveLiterals()
        setContent {
            LambdaOptimizedTest(
                nonComposableLambdaExpression = {
                    println(someValue)
                },
                composableLambdaExpression = {
                    Text(text = someValue.toString())
                }
            )

            /*Test(
                nonComposableLambdaExpression = {
                    println("nonComposableLambdaExpression")
                },
                composableLambdaExpression = {
                    Text(text = "composableLambdaExpression")
                }
            )*/
        }

        /*binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        vm.printCurrentThreadName()
        binding.btnTest.setOnClickListener {
            measureNanoTime {
                lifecycleScope.launchWhenCreated {
                    withContext(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            binding.tvLabel.text = "Bye, world!"
                        }
                    }
                }
            }.also(::println)
        }

        binding.btnTestImmediate.setOnClickListener {
            measureNanoTime {
                lifecycleScope.launchWhenCreated {
                    withContext(Dispatchers.IO) {
                        withContext(Dispatchers.Main.immediate) {
                            binding.tvLabel.text = "Bye, world! with immediate"
                        }
                    }
                }
            }.also(::println)
        }*/
    }

    /*// @Preview
    @Composable
    fun Test(
        nonComposableLambdaExpression: () -> Unit,
        composableLambdaExpression: @Composable () -> Unit,
    ) {
        SideEffect {
            nonComposableLambdaExpression()
        }
        composableLambdaExpression()
        // println(string(R.string.app_name) @Composable { stringResource(it) })
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(color = Color.Cyan)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Test enableLiveLiterals() enabled")
        }
    }*/
}

/*
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FunAnn

inline fun string(@StringRes resId: Int, builder: (resId: Int) -> String) = builder(resId)

@Composable
fun Test(content: @Composable (int: Int, argument: Any) -> Unit) {
    content(argument = "path", int = 1) // named-arguments in Lambda expression.
}
*/
