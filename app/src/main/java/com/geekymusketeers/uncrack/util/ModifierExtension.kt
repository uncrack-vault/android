package com.geekymusketeers.uncrack.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

/**
 * A custom [Modifier.clickable] with no ripple effect.
 *
 * Configure component to receive clicks via input or accessibility "click" event.
 *
 * Add this modifier to the element to make it clickable within its bounds and show an indication
 * as specified in [indication] parameter.
 *
 * If you need to support double click or long click alongside the single click, consider
 * using [combinedClickable].
 *
 * @sample androidx.compose.foundation.samples.ClickableSample
 *
 * @param indication indication to be shown when modified element is pressed. By default,
 * indication from [LocalIndication] will be used. Pass `null` to show no indication, or
 * current value from [LocalIndication] to show theme default
 * @param enabled Controls the enabled state. When `false`, [onClick], and this modifier will
 * appear disabled for accessibility services
 * @param onClickLabel semantic / accessibility label for the [onClick] action
 * @param role the type of user interface element. Accessibility services might use this
 * to describe the element or do customizations
 * @param onClick will be called when user clicks on the element
 */
fun Modifier.onClick(
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.composed(inspectorInfo = debugInspectorInfo {
    name = "onClickModifier"
    value = enabled
}) {
    val interactionSource = remember { MutableInteractionSource() }
    clickable(
        indication = indication,
        interactionSource = interactionSource,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role
    ) {
        onClick.invoke()
    }
}