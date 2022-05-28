package land.sungbin.androidplayground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.sp

private object TopWithFooter : Arrangement.Vertical {
    /**
     * @param totalSize 자식이 차지할 수 있는 사용 가능한 픽셀 공간
     * @param sizes 모든 자식의 픽셀 단위 크기 배열
     * -> Vertical 이니 크기는 높이가 됨
     * @param outPositions 위쪽을 기준으로 계산된 위치를
     * 픽셀 단위로 반환하는 크기 크기의 배열
     */
    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray
    ) {
        var y = 0 // 아이템 y 위치
        sizes.forEachIndexed { index, size ->
            outPositions[index] = y // 아이템 배치
            y += size // y 늘리기 (기존 y 에 배치된 아이템 높이 만큼 추가)
        }
        if (y < totalSize) {
            // 만약 배치 가능한 공간이 남았다면
            // 전체 공간에서 마지막 아이템의 높이 만큼 빼서
            // 마지막 아이템을 배치
            outPositions[outPositions.lastIndex] = totalSize - sizes.last()
        }
    }
}

@Composable
fun TopWithFooterTest() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = TopWithFooter,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = 5) { index ->
            Text(
                text = "Index: $index",
                style = LocalTextStyle.current.copy(fontSize = 20.sp)
            )
        }
    }
}