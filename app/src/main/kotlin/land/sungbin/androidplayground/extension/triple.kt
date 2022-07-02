@file:NoLiveLiterals

package land.sungbin.androidplayground.extension

import androidx.compose.runtime.NoLiveLiterals

infix fun <A, B, C> Pair<A, B>.and(third: C) = Triple(first, second, third)
