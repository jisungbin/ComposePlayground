@file:NoLiveLiterals

package land.sungbin.androidplayground.snippet.animation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.tabs.TabItem
import land.sungbin.androidplayground.snippet.animation.component.MovieContainer
import land.sungbin.androidplayground.snippet.animation.component.MovieName
import land.sungbin.androidplayground.snippet.animation.component.MoviePoster
import land.sungbin.androidplayground.snippet.animation.component.TabContainer
import land.sungbin.androidplayground.snippet.animation.component.TabItem
import land.sungbin.androidplayground.theme.BackgroundWhite
import land.sungbin.androidplayground.theme.NanumGothicTextStyle

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun DefaultMovieSelector() {
    var selectedTabIndexState by remember { mutableStateOf(0) }
    val (selectedTabType, selectedTabPosterDrawable, selectedTabFullname) = remember(TabDefaults.Items) {
        derivedStateOf {
            TabDefaults.Items[selectedTabIndexState]
        }
    }.value

    ProvideTextStyle(NanumGothicTextStyle) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.BackgroundWhite),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TabContainer {
                TabDefaults.Items.forEachIndexed { index, (type, _, _) ->
                    TabItem(
                        title = type.string,
                        backgroundColor = tabBackgroundColor(
                            selectedIndex = selectedTabIndexState,
                            nowTabIndex = index
                        ),
                        textColor = tabTextColor(
                            selectedIndex = selectedTabIndexState,
                            nowTabIndex = index
                        ),
                        onTabClick = {
                            selectedTabIndexState = index
                        }
                    )
                }
            }
            MovieContainer {
                MovieName(selectedTabFullname = selectedTabFullname)
                MoviePoster(
                    selectedTabPosterDrawable = selectedTabPosterDrawable,
                    posterDescription = selectedTabType.string
                )
            }
        }
    }
}

@Stable
private fun tabBackgroundColor(
    selectedIndex: Int,
    nowTabIndex: Int
) = when (selectedIndex == nowTabIndex) {
    true -> TabDefaults.Color.selectedBackground
    false -> TabDefaults.Color.defaultBackground
}

@Stable
private fun tabTextColor(
    selectedIndex: Int,
    nowTabIndex: Int
) = when (selectedIndex == nowTabIndex) {
    true -> TabDefaults.Color.selectedText
    false -> TabDefaults.Color.defaultText
}
