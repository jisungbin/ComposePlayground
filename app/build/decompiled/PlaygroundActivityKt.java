package land.sungbin.androidplayground;

import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonElevation;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.ComposableTarget;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableLongState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 9, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"Content", "", "ms", "Landroidx/compose/runtime/MutableLongState;", "(Landroidx/compose/runtime/MutableLongState;Landroidx/compose/runtime/Composer;I)V", "app_debug"}
)
@SourceDebugExtension({"SMAP\nPlaygroundActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlaygroundActivity.kt\nland/sungbin/androidplayground/PlaygroundActivityKt\n+ 2 Composer.kt\nandroidx/compose/runtime/ComposerKt\n*L\n1#1,188:1\n1097#2,6:189\n*S KotlinDebug\n*F\n+ 1 PlaygroundActivity.kt\nland/sungbin/androidplayground/PlaygroundActivityKt\n*L\n184#1:189,6\n*E\n"})
public final class PlaygroundActivityKt {
   @Composable
   @ComposableTarget(
      applier = "androidx.compose.ui.UiComposable"
   )
   public static final void Content(@NotNull final MutableLongState ms, @Nullable Composer $composer, final int $changed) {
      Intrinsics.checkNotNullParameter(ms, "ms");
      $composer = $composer.startRestartGroup(1419088695);
      ComposerKt.sourceInformation($composer, "C(Content)183@7088L110:PlaygroundActivity.kt#qshby3");
      int $dirty = $changed;
      if (($changed & 14) == 0) {
         $dirty = $changed | ($composer.changed(ms) ? 4 : 2);
      }

      if (($dirty & 11) == 2 && $composer.getSkipping()) {
         $composer.skipToGroupEnd();
      } else {
         if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(1419088695, $dirty, -1, "land.sungbin.androidplayground.Content (PlaygroundActivity.kt:182)");
         }

         $composer.startReplaceableGroup(-1968075294);
         boolean invalid$iv = $composer.changed(ms);
         int $i$f$cache = false;
         Object it$iv = $composer.rememberedValue();
         int var9 = false;
         Object var10000;
         if (!invalid$iv && it$iv != Composer.Companion.getEmpty()) {
            var10000 = it$iv;
         } else {
            int var10 = false;
            Object value$iv = (Function0)(new Function0() {
               public final void invoke() {
                  ms.setLongValue(System.currentTimeMillis());
               }
            });
            $composer.updateRememberedValue(value$iv);
            var10000 = value$iv;
         }

         Function0 var4 = (Function0)var10000;
         $composer.endReplaceableGroup();
         ButtonKt.Button(var4, (Modifier)null, false, (Shape)null, (ButtonColors)null, (ButtonElevation)null, (BorderStroke)null, (PaddingValues)null, (MutableInteractionSource)null, (Function3)ComposableLambdaKt.composableLambda($composer, 1039689511, true, new Function3() {
            @Composable
            @ComposableTarget(
               applier = "androidx.compose.ui.UiComposable"
            )
            public final void invoke(@NotNull RowScope $this$Button, @Nullable Composer $composer, int $changed) {
               Intrinsics.checkNotNullParameter($this$Button, "$this$Button");
               ComposerKt.sourceInformation($composer, "C184@7158L36:PlaygroundActivity.kt#qshby3");
               if (($changed & 81) == 16 && $composer.getSkipping()) {
                  $composer.skipToGroupEnd();
               } else {
                  if (ComposerKt.isTraceInProgress()) {
                     ComposerKt.traceEventStart(1039689511, $changed, -1, "land.sungbin.androidplayground.Content.<anonymous> (PlaygroundActivity.kt:184)");
                  }

                  TextKt.Text--4IGK_g(String.valueOf(ms.getLongValue()), (Modifier)null, 0L, 0L, (FontStyle)null, (FontWeight)null, (FontFamily)null, 0L, (TextDecoration)null, (TextAlign)null, 0L, 0, false, 0, 0, (Function1)null, (TextStyle)null, $composer, 0, 0, 131070);
                  if (ComposerKt.isTraceInProgress()) {
                     ComposerKt.traceEventEnd();
                  }
               }

            }
         }), $composer, 805306368, 510);
         if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
         }
      }

      ScopeUpdateScope var12 = $composer.endRestartGroup();
      if (var12 != null) {
         var12.updateScope((Function2)(new Function2() {
            public final void invoke(@Nullable Composer $composer, int $force) {
               PlaygroundActivityKt.Content(ms, $composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
            }
         }));
      }

   }
}
