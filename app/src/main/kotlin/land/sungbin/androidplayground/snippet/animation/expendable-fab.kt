@file:OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)

package land.sungbin.androidplayground.snippet.animation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import land.sungbin.androidplayground.annotation.BackgroundPreview
import land.sungbin.androidplayground.extension.DefaultMeasurePolicy
import land.sungbin.androidplayground.extension.layoutTransitionAnimation
import land.sungbin.androidplayground.extension.noRippleClickable
import land.sungbin.androidplayground.theme.Pink

private object FabDefaults {
    val elevation = 10.dp
    val color = Color.Pink

    fun shape(isExpanded: Boolean) = when (isExpanded) {
        true -> RoundedCornerShape(percent = 10)
        else -> RoundedCornerShape(percent = 30)
    }

    @Stable
    fun size(
        isExpanded: Boolean,
        maxWidthDp: Dp
    ) = when (isExpanded) {
        true -> DpSize(
            width = maxWidthDp,
            height = 300.dp
        )
        else -> DpSize(
            width = 75.dp,
            height = 75.dp
        )
    }
}

@BackgroundPreview
@Composable
fun ExpandableFabBasic(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(
            modifier = Modifier
                .size(
                    size = FabDefaults.size(
                        isExpanded = isExpanded,
                        maxWidthDp = maxWidth
                    )
                )
                .noRippleClickable { isExpanded = !isExpanded },
            elevation = FabDefaults.elevation,
            color = FabDefaults.color,
            shape = FabDefaults.shape(isExpanded = isExpanded),
            content = {}
        )
    }
}

@BackgroundPreview
@Composable
fun ExpandableFabAnimation(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    val screenMaxWidth = LocalConfiguration.current.screenWidthDp

    LookaheadLayout(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(16.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Surface(
                    modifier = Modifier
                        .size(
                            size = FabDefaults.size(
                                isExpanded = isExpanded,
                                maxWidthDp = screenMaxWidth.dp
                            )
                        )
                        .layoutTransitionAnimation(
                            lookaheadScope = this@LookaheadLayout,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessVeryLow,
                            )
                        )
                        .noRippleClickable { isExpanded = !isExpanded },
                    elevation = FabDefaults.elevation,
                    color = FabDefaults.color,
                    shape = FabDefaults.shape(isExpanded = isExpanded),
                    content = {}
                )
            }
        },
        measurePolicy = DefaultMeasurePolicy
    )
}