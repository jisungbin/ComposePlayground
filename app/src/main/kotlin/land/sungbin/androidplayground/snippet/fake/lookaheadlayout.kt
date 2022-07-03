@file:Suppress("UNUSED_PARAMETER", "KDocUnresolvedReference")

package land.sungbin.androidplayground.snippet.fake

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.LookaheadLayoutCoordinates
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

/**
 * LookaheadLayout 은 레이아웃을 결정하기 위해
 * measure 를 미리 하고 추후 placement 단계를 실행하는 레이아웃입니다. => measure 를 미리 하는 단계: lookahead 단계
 * lookahead 단계가 끝나면 추후 [LookaheadLayoutScope.intermediateLayout] 을 통해
 * lookahead 결과를 기반으로 레이아웃의 measurement 및 placement 를
 * 조정할 수 있는 또 다른 measure 및 placement 단계가 시작됩니다.
 *
 * 이를 이용하여 레이아웃이 미리 measure 된 대상 레이아웃을 향해
 * 크기와 위치를 점차적으로 변경할 수 있습니다.
 *
 * @param content 표시할 composable content
 * @param modifier 레이아웃에 적용할 [Modifier]
 * @param measurePolicy 레이아웃의 measurement 와 positioning 에서 사용할 정책
 */
@ExperimentalComposeUiApi
@UiComposable
@Composable
fun LookaheadLayout(
    content: @Composable @UiComposable LookaheadLayoutScope.() -> Unit,
    modifier: Modifier = Modifier,
    measurePolicy: MeasurePolicy
) {
}

/**
 * LookaheadLayoutScope 는 [LookaheadLayout] 의 모든(직접 및 간접)
 * 자식 레이아웃에 대한 receiver 범위를 제공합니다.
 * lookahead 단계에서 계산된 레이아웃의 measurement 및 placement 는
 * LookaheadLayoutScope 에서 각각 [Modifier.intermediateLayout] 및 [Modifier.onPlaced] 를 통해 observe 할 수 있습니다.
 */
@ExperimentalComposeUiApi
interface LookaheadLayoutScope {
    /**
     * onPlaced 는 부모 [LayoutModifier] 가 배치된 후 자식 [LayoutModifier] 가
     * 배치되기 전에 호출됩니다. 이를 통해 자식 [LayoutModifier] 는
     * 부모를 기반으로 자체 placement 를 조정할 수 있습니다.
     *
     * onPlaced 콜백은 [LookaheadLayout] 에서 방출한 [LayoutNode] 의 [LookaheadLayoutCoordinates] 를
     * 첫 번째 매개변수로 사용하고 이 modifier 의 [LookaheadLayoutCoordinates] 를
     * 두 번째 매개변수로 사용하여 호출됩니다.
     *
     * [LookaheadLayoutCoordinates] 가 주어지면 [LookaheadLayout] 의 좌표 시스템에서
     * 이 modifier 의 lookahead 위치와 현재 위치는 각각
     * [LookaheadLayoutCoordinates.localLookaheadPositionOf] 및 [LookaheadLayoutCoordinates.localPositionOf] 를
     * 사용하여 계산할 수 있습니다.
     */
    fun Modifier.onPlaced(
        onPlaced: (
            lookaheadScopeCoordinates: LookaheadLayoutCoordinates,
            layoutCoordinates: LookaheadLayoutCoordinates
        ) -> Unit
    ): Modifier

    /**
     * lookahead 단계에서 계산된 자식 레이아웃의 대상 크기를 기반으로
     * 중간 레이아웃을 만듭니다. 이렇게 하면 lookahead 에서
     * 계산된 자식 레이아웃의 크기가 제공되는 [measure] 인자를 통해
     * 중간 레이아웃이 lookahead 단계 후 자식 레이아웃을 morph 할 수 있습니다.
     *
     * morph 는 한 모양을 다른 모양으로 바꾸는 행위입니다.
     */
    fun Modifier.intermediateLayout(
        measure: MeasureScope.(
            measurable: Measurable,
            constraints: Constraints,
            lookaheadSize: IntSize
        ) -> MeasureResult
    ): Modifier
}

/**
 * LookaheadLayoutCoordinates 인터페이스는
 * lookhead 단계 진행 전과 후의 레이아웃 모두의 좌표를 보유합니다.
 */
@ExperimentalComposeUiApi
sealed interface LookaheadLayoutCoordinates : LayoutCoordinates {
    /**
     * [sourceCoordinates] 공간의 [relativeToSource] 를 로컬 좌표로 변환합니다.
     * [sourceCoordinates] 는 동일한 compose 레이아웃 계층에 속하는
     * 모든 [LookaheadLayoutCoordinates] 일 수 있습니다.
     *
     * [localPositionOf] 와 달리 [localLookaheadPositionOf] 는
     * 좌표 계산을 위해 lookahead 위치를 사용합니다.
     */
    fun localLookaheadPositionOf(
        sourceCoordinates: LookaheadLayoutCoordinates,
        relativeToSource: Offset = Offset.Zero
    ): Offset
}

/**
 * 레이아웃에 대해 measure 된 bounds 의 홀더입니다.
 */
@JvmDefaultWithCompatibility
interface LayoutCoordinates {
    val size: IntSize
    val providedAlignmentLines: Set<AlignmentLine>
    val parentLayoutCoordinates: LayoutCoordinates?
    val parentCoordinates: LayoutCoordinates?
    val isAttached: Boolean
    fun windowToLocal(relativeToWindow: Offset): Offset
    fun localToWindow(relativeToLocal: Offset): Offset
    fun localToRoot(relativeToLocal: Offset): Offset

    /**
     * [sourceCoordinates] 공간의 [relativeToSource] 를 로컬 좌표로 변환합니다.
     * [sourceCoordinates] 는 동일한 compose 레이아웃 계층에 속하는
     * 모든 [LookaheadLayoutCoordinates] 일 수 있습니다.
     */
    fun localPositionOf(sourceCoordinates: LayoutCoordinates, relativeToSource: Offset): Offset
    fun localBoundingBoxOf(sourceCoordinates: LayoutCoordinates, clipBounds: Boolean = true): Rect
    operator fun get(alignmentLine: AlignmentLine): Int
}
