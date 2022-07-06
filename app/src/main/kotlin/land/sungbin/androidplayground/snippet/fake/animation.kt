@file:Suppress(
    "KDocUnresolvedReference",
    "UNUSED_PARAMETER",
    "MemberVisibilityCanBePrivate"
)

package land.sungbin.androidplayground.snippet.fake

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.RepeatableSpec
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

private val colorDefaultSpring = spring<Color>()

/**
 * [Color] 에 대한 Fire-and-Forget 애니메이션 기능을 제공합니다.
 * animate*AsState 는 [Float], [Color], [Dp], [Size], [Offset], [Rect], [Int], [IntOffset] 그리고 [IntSize] 에 사전 정의돼 있습니다.
 * 제공된 [targetValue] 가 변경되면 애니메이션이 자동으로 실행됩니다.
 * [targetValue] 가 변경될 때 진행 중인 애니메이션이 이미 있는 경우 진행 중인 애니메이션은 새 대상 값을 향해 애니메이션되도록 코스를 조정합니다.
 *
 * animate*AsState 는 [State] 객체를 반환합니다. 상태 개체의 값은 애니메이션이 끝날 때까지 애니메이션에 의해 계속 업데이트됩니다.
 * 이 컴포저블 함수를 제거하지 않고는 애니메이션을 취소할 수 없습니다. 취소 가능한 애니메이션은 [Animatable] 을 사용하세요.
 *
 * @param targetValue 애니메이션의 타겟
 * @param animationSpec 시간에 따라 값을 변경하는 데 사용할 애니메이션
 * @param finishedListener 애니메이션이 완료될 때 알림을 받는 선택적 리스너
 *
 * @return 애니메이션이 적용되는 값을 담은 [State]
 */
@Composable
fun animateColorAsState(
    targetValue: Color,
    animationSpec: AnimationSpec<Color> = colorDefaultSpring,
    finishedListener: ((Color) -> Unit)? = null,
): State<Color> {
    val converter = remember(targetValue.colorSpace) {
        // Color.VectorConverter 는 아래와 같은 ColorToVector 를 반환합니다.
        // private val ColorToVector: (colorSpace: ColorSpace) -> TwoWayConverter<Color, AnimationVector4D>
        //
        // 그래서 (Color.VectorConverter)(targetValue.colorSpace) 이 코드는
        // ColorToVector(targetValue.colorSpace) 와 같습니다.
        (Color.VectorConverter)(targetValue.colorSpace)
    }
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = converter,
        animationSpec = animationSpec,
        finishedListener = finishedListener
    )
}

/**
 * 시작 값과 끝 값 사이에 물리학 기반 애니메이션을 적용합니다.
 *
 * @param DampingRatio 스프링의 감쇠비 (탄성)
 * @param stiffness 스프링의 강성 (종료 값으로 이동하는 속도)
 * @param visibleThreshold 가시성 임계값
 * 애니메이션이 대상에 반올림하기에 충분히 시각적으로 가까운 것으로 간주되어야 하는 시기를 정의합니다.
 *
 * @return 주어진 옵션을 사용하는 [SpringSpec]
 */
@Stable
fun <T> spring(
    dampingRatio: Float = Spring.DampingRatioNoBouncy,
    stiffness: Float = Spring.StiffnessMedium,
    visibilityThreshold: T? = null,
): SpringSpec<T> = SpringSpec(
    dampingRatio = dampingRatio,
    stiffness = stiffness,
    visibilityThreshold = visibilityThreshold
)

/**
 * 이징 곡선을 사용하여 지정된 [durationMillis] 동안 시작 값과 끝 값 간에 애니메이션을 적용합니다.
 *
 * @param durationMillis 애니메이션 사양의 지속 시간 (밀리초)
 * @param delayMillis 애니메이션이 시작되기 전에 대기하는 시간 (밀리초)
 * @param easing 시작과 끝 사이를 보간하는 데 사용되는 이징 곡선
 *
 * @return 주어진 옵션을 사용하는 [TweenSpec]
 */
@Stable
fun <T> tween(
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
): TweenSpec<T> = TweenSpec(
    durationMillis = durationMillis,
    delay = delayMillis,
    easing = easing
)

/**
 * 전환의 끝을 강조하기 위해 빠르게 속도를 높이고 점차적으로 느려집니다.
 */
val FastOutSlowInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

/**
 * 들어오는 요소는 최고 속도(요소 이동의 가장 빠른 지점)에서 전환을 시작하고 정지 상태에서 끝나는 감속 완화를 사용하여 애니메이션됩니다.
 */
val LinearOutSlowInEasing: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

/**
 * 화면을 종료하는 요소는 정지 상태에서 시작하여 최고 속도로 끝나는 가속 완화를 사용합니다.
 */
val FastOutLinearInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)

/**
 * 수정되지 않은 분수를 반환합니다. [Easing]이 필요하지만 실제 easing 이 필요하지 않은 경우 기본값으로 유용합니다.
 */
val LinearEasing: Easing = Easing { fraction -> fraction }


/**
 * [CubicBezierEasing] 클래스는 3차 베지어 곡선을 구현합니다.
 */
@Immutable
class CubicBezierEasing(
    private val a: Float,
    private val b: Float,
    private val c: Float,
    private val d: Float,
) : Easing {
    override fun transform(fraction: Float): Float {
        TODO("Not yet implemented")
    }
}

/**
 * 애니메이션 기간에 여러 타임스탬프에서 지정된 스냅샷 값을 기반으로 애니메이션을 처리합니다.
 * 언제나 애니메이션 값은 두 키프레임 값 사이에 보간됩니다.
 * 키프레임마다 [Easing] 을 지정하여 보간 유형 곡선을 결정할 수 있습니다.
 */
@Stable
fun <T> keyframes(
    init: KeyframesSpec.KeyframesSpecConfig<T>.() -> Unit,
): KeyframesSpec<T> = KeyframesSpec(
    config = KeyframesSpec.KeyframesSpecConfig<T>().apply(init)
)

/**
 * 지정된 반복 횟수에 도달할 때까지 기간 기반 애니메이션(예: [tween] 또는 [keyframes])을 반복적으로 실행합니다.
 *
 * @param iterations 반복할 횟수
 * @param animation 반복할 애니메이션
 * @param repeatMode 반복할 모드 - [RepeatMode.Restart] 또는 [RepeatMode.Reverse]
 * @param initialStartOffset 애니메이션을 시작할 오프셋
 */
@Stable
fun <T> repeatable(
    iterations: Int,
    animation: DurationBasedAnimationSpec<T>,
    repeatMode: RepeatMode = RepeatMode.Restart,
    initialStartOffset: StartOffset = StartOffset(0),
): RepeatableSpec<T> = RepeatableSpec(
    iterations = iterations,
    animation = animation,
    repeatMode = repeatMode,
    initialStartOffset = initialStartOffset
)

/**
 * [repeatable] 과 동일하고, 유일한 차이점은 무한 반복이라 iterations 인자가 없습니다.
 */
@Stable
fun <T> infiniteRepeatable(
    animation: DurationBasedAnimationSpec<T>,
    repeatMode: RepeatMode = RepeatMode.Restart,
    initialStartOffset: StartOffset = StartOffset(0),
): InfiniteRepeatableSpec<T> = InfiniteRepeatableSpec(
    animation = animation,
    repeatMode = repeatMode,
    initialStartOffset = initialStartOffset
)

/**
 * snap 은 값을 즉시 종료 값으로 변환하는 특수 [AnimationSpec] 입니다.
 *
 * @param delayMillis 애니메이션 시작을 지연할 시간 (밀리초)
 */
@Stable
fun <T> snap(delayMillis: Int = 0): SnapSpec<T> = SnapSpec(
    delay = delayMillis
)

/**
 * [targetState] 가 변경될 때 [content] 에 자동으로 애니메이션을 적용하는 컨테이너입니다.
 *
 * @param targetState 변경될 상태
 * @param modifier 적용할 [Modifier]
 * @param transitionSpec 적용할 애니메이션
 * @param contentAlignment [content] 가 배치될 [Alignment]
 * @param content 배치할 컴포저블 함수
 */
@ExperimentalAnimationApi
@Composable
fun <S> AnimatedContent(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform = {
        fadeIn(
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = 90
            )
        ) + scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = 90
            )
        ) with fadeOut(
            animationSpec = tween(durationMillis = 90)
        )
    },
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit,
) {
}

/**
 * 콘텐츠가 [AnimatedContent] 에 들어가고 나가는 방법을 정의합니다.
 *
 * @param targetContentEnter 새로운 콘텐츠가 들어오는 애니메이션
 * @param initialContentExit 기존 콘텐츠가 나가는 애니메이션
 * @param targetContentZIndex 콘텐츠가 들어오고 나갈 때 사용할 zIndex
 * @param sizeTransform 콘텐츠가 들어오고 나갈 때 사이즈가 변할 경우 이를 관리하기 위한 옵션
 */
@ExperimentalAnimationApi
class ContentTransform(
    val targetContentEnter: EnterTransition,
    val initialContentExit: ExitTransition,
    targetContentZIndex: Float = 0f,
    sizeTransform: SizeTransform? = SizeTransform(),
)

/**
 * [AnimatedVisibility] 컴포저블이 표시될 때 화면에 표시되는 방식을 정의합니다.
 * 사용할 수 있는 [EnterTransition] 의 4가지 카테고리는 다음과 같습니다.
 *
 * 1. fade: [fadeIn]
 * 2. scale: [scaleIn]
 * 3. slide: [slideIn], [slideInHorizontally], [slideInVertically]
 * 4. expand: [expandIn], [expandHorizontally], [expandVertically]
 */
@Immutable
sealed class EnterTransition {
    internal abstract val data: TransitionData // 내부에서 사용하는 정보를 보관함

    /**
     * 다양한 [EnterTransition] 을 결합합니다. [EnterTransition] 는 동시에 시작되므로 결합되는 [EnterTransition] 의 순서는 중요하지 않습니다.
     * 이러한 [EnterTransition] 에서 변형을 적용하는 순서는 알파 및 배율을 먼저 조정하고 축소 또는 확대한 다음 슬라이드합니다.
     *
     * @param enter 결합할 다른 [EnterTransition]
     *
     * @return 2개의 [EnterTransition] 이 결합된 새로운 [EnterTransition]
     */
    @Stable
    operator fun plus(enter: EnterTransition): EnterTransition {
        return EnterTransitionImpl(
            TransitionData(
                fade = data.fade ?: enter.data.fade,
                slide = data.slide ?: enter.data.slide,
                changeSize = data.changeSize ?: enter.data.changeSize,
                scale = data.scale ?: enter.data.scale
            )
        )
    }
}

/**
 * [AnimatedVisibility] 컴포저블이 사라질 때 화면에 표시되는 방식을 정의합니다.
 * 사용할 수 있는 [ExitTransition] 의 4가지 카테고리는 다음과 같습니다.
 *
 * 1. fade: [fadeOut]
 * 2. scale: [scaleOut]
 * 3. slide: [slideOut], [slideOutHorizontally], [slideOutVertically]
 * 4. shrink: [shrinkOut], [shrinkHorizontally], [shrinkVertically]
 */
@Immutable
sealed class ExitTransition {
    internal abstract val data: TransitionData // 내부에서 사용하는 정보를 보관함

    /**
     * 다양한 [ExitTransition] 을 결합합니다. [ExitTransition] 는 동시에 시작되므로 결합되는 [ExitTransition] 의 순서는 중요하지 않습니다.
     * 이러한 [ExitTransition] 에서 변형을 적용하는 순서는 알파 및 배율을 먼저 조정하고 축소 또는 확대한 다음 슬라이드합니다.
     *
     * @param exit 결합할 다른 [ExitTransition]
     *
     * @return 2개의 [ExitTransition] 이 결합된 새로운 [ExitTransition]
     */
    @Stable
    operator fun plus(exit: ExitTransition): ExitTransition {
        return ExitTransitionImpl(
            TransitionData(
                fade = data.fade ?: exit.data.fade,
                slide = data.slide ?: exit.data.slide,
                changeSize = data.changeSize ?: exit.data.changeSize,
                scale = data.scale ?: exit.data.scale
            )
        )
    }
}

/**
 * 콘텐츠의 크기가 변경될 때 한 크기에서 다른 크기로 변환하는 방법을 정의합니다.
 */
@ExperimentalAnimationApi
interface SizeTransform {
    /**
     * 사이즈 조절 애니메이션에서 콘텐츠를 clip 하는지 여부입니다.
     */
    val clip: Boolean

    /**
     * 애니메이션 적용 전인 [initialSize] 와 애니메이션의 [targetSize] 를 기반으로 [FiniteAnimationSpec] 을 정의할 수 있습니다.
     */
    fun createAnimationSpec(initialSize: IntSize, targetSize: IntSize): FiniteAnimationSpec<IntSize>
}

/**
 * 제공된 [this][EnterTransition] 및 [exit] 를 사용하여 [ContentTransform] 을 생성하며,
 * Enter 및 Exit 애니메이션이 동시에 실행됩니다.
 *
 * @param exit [this][EnterTransition] 와 합칠 [ExitTransition]
 *
 * @return [this][EnterTransition] 와 [ExitTransition] 이 합쳐진 [ContentTransform]
 */
@ExperimentalAnimationApi
infix fun EnterTransition.with(exit: ExitTransition) = ContentTransform(
    targetContentEnter = this,
    initialContentExit = exit
)

/* ==================================== */
/* ===== internal implementations ===== */
/* ==================================== */

interface Fade
interface Slide
interface ChangeSize
interface Scale

@Immutable
data class TransitionData(
    val fade: Fade? = null,
    val slide: Slide? = null,
    val changeSize: ChangeSize? = null,
    val scale: Scale? = null,
)

@Immutable
private class EnterTransitionImpl(override val data: TransitionData) : EnterTransition()

@Immutable
private class ExitTransitionImpl(override val data: TransitionData) : ExitTransition()