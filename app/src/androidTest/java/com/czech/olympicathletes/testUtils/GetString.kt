package com.czech.olympicathletes.testUtils

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import kotlin.properties.ReadOnlyProperty

/**
 * extension function to extract string resource
 */

fun AndroidComposeTestRule<*, *>.getString(@StringRes resId: Int) =
    ReadOnlyProperty<Any?, String> { _, _ -> activity.getString(resId) }