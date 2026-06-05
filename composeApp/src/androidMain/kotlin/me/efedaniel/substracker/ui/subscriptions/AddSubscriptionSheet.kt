package me.efedaniel.substracker.ui.subscriptions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.ui.proton.components.bottomsheet.ProtonBottomSheet
import me.efedaniel.substracker.ui.proton.components.button.ProtonButton
import me.efedaniel.substracker.ui.proton.components.button.ProtonButtonSize
import me.efedaniel.substracker.ui.proton.components.button.ProtonButtonType
import me.efedaniel.substracker.ui.proton.components.segmentedcontrol.ProtonSegmentedControl
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.components.textfield.ProtonTextField
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension
import me.efedaniel.substracker.ui.subscriptions.models.FrequencyChoice
import me.efedaniel.substracker.ui.subscriptions.models.toDomainOrNull
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Add-subscription form following the compact "Editorial Ledger" design. Collects raw
 * values and forwards them via [onConfirm]; validation and domain construction live in
 * [SubscriptionsViewModel] (the button-enabled check here mirrors it so invalid anchors
 * can never reach the domain constructors).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: (
        name: String,
        priceText: String,
        currency: Currency,
        frequency: FrequencyChoice,
        billingDay: Int?,
        anchorMonth: Month?,
        startDate: LocalDate?,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }
    var name by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf(Currency.EUR) }
    var frequency by remember { mutableStateOf(FrequencyChoice.Monthly) }
    var billingDayText by remember { mutableStateOf(today.day.toString()) }
    var anchorMonth by remember { mutableStateOf(today.month) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    val billingDay = billingDayText.toIntOrNull()
    val isValid =
        name.isNotBlank() &&
            parsePriceToMinorUnits(priceText, currency) != null &&
            frequency.toDomainOrNull(billingDay = billingDay, anchorMonth = anchorMonth) != null

    ProtonBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ProtonDimension.Spacing24)
                    .navigationBarsPadding()
                    .imePadding(),
        ) {
            ProtonText(
                text = "Add subscription",
                style = ProtonTheme.typography.titleLarge,
                color = ProtonTheme.colors.primary,
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))

            ProtonTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                placeholder = "e.g. Netflix",
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))

            Row(modifier = Modifier.fillMaxWidth()) {
                CurrencyDropdownField(
                    selected = currency,
                    onSelected = { currency = it },
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(ProtonDimension.Spacing12))
                ProtonTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = "Price",
                    placeholder = "0.00",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(2f),
                )
            }
            FieldHelperText(text = "Currencies like JPY accept no decimals.")
            Spacer(Modifier.height(ProtonDimension.Spacing24))

            SectionLabel(text = "BILLING CYCLE")
            Spacer(Modifier.height(ProtonDimension.Spacing8))
            ProtonSegmentedControl(
                items = FrequencyChoice.entries,
                selectedIndex = FrequencyChoice.entries.indexOf(frequency),
                onSelected = { frequency = FrequencyChoice.entries[it] },
                labelText = { it.label },
            )

            when (frequency) {
                FrequencyChoice.Monthly -> {
                    Spacer(Modifier.height(ProtonDimension.Spacing16))
                    ProtonTextField(
                        value = billingDayText,
                        onValueChange = { billingDayText = it },
                        label = "Billing day",
                        helperText = "On short months we'll bill on the last day",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = { CalendarIcon() },
                    )
                }

                FrequencyChoice.Annually -> {
                    Spacer(Modifier.height(ProtonDimension.Spacing16))
                    FieldLabel(text = "Renewal date")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        MonthDropdownField(
                            selected = anchorMonth,
                            onSelected = { anchorMonth = it },
                            modifier = Modifier.weight(2f),
                        )
                        Spacer(Modifier.width(ProtonDimension.Spacing12))
                        ProtonTextField(
                            value = billingDayText,
                            onValueChange = { billingDayText = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                    }
                    FieldHelperText(text = "Renews every year on this date")
                }

                FrequencyChoice.OneTime -> Unit
            }
            Spacer(Modifier.height(ProtonDimension.Spacing24))

            SectionLabel(text = "OPTIONAL DETAILS")
            Spacer(Modifier.height(ProtonDimension.Spacing8))
            ProtonTextField(
                value = startDate?.formatAsDisplayDate().orEmpty(),
                onValueChange = {},
                label = "Start date",
                placeholder = "dd/mm/yyyy",
                helperText = "Already subscribed? Pick the first billing date",
                helperTextStyle = ProtonTheme.typography.labelMedium.copy(fontStyle = FontStyle.Italic),
                trailingIcon = { CalendarIcon() },
                onClick = { showDatePicker = true },
            )
            Spacer(Modifier.height(ProtonDimension.Spacing24))

            ProtonButton(
                text = "Add subscription",
                type = ProtonButtonType.ROUNDED,
                size = ProtonButtonSize.LARGE,
                fillMaxWidth = true,
                enabled = isValid,
                onClick = {
                    onConfirm(name, priceText, currency, frequency, billingDay, anchorMonth, startDate)
                },
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))
        }
    }

    if (showDatePicker) {
        StartDatePickerDialog(
            initialDate = startDate,
            onConfirm = {
                startDate = it
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
        )
    }
}

@Composable
private fun CurrencyDropdownField(
    selected: Currency,
    onSelected: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownField(
        label = "Currency",
        value = selected.isoCode,
        items = Currency.entries,
        itemText = { it.isoCode },
        onSelected = onSelected,
        modifier = modifier,
    )
}

@Composable
private fun MonthDropdownField(
    selected: Month,
    onSelected: (Month) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownField(
        label = null,
        value = selected.displayName(),
        items = Month.entries,
        itemText = { it.displayName() },
        onSelected = onSelected,
        modifier = modifier,
    )
}

@Composable
private fun <T> DropdownField(
    label: String?,
    value: String,
    items: List<T>,
    itemText: (T) -> String,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        ProtonTextField(
            value = value,
            onValueChange = {},
            label = label,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    tint = ProtonTheme.colors.onSurfaceVariant,
                )
            },
            onClick = { expanded = true },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = ProtonTheme.colors.surfaceContainerLowest,
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        ProtonText(
                            text = itemText(item),
                            style = ProtonTheme.typography.bodyMedium,
                            color = ProtonTheme.colors.onSurface,
                        )
                    },
                    onClick = {
                        onSelected(item)
                        expanded = false
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartDatePickerDialog(
    initialDate: LocalDate?,
    onConfirm: (LocalDate?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = initialDate?.toEpochMillisUtc(),
        )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(datePickerState.selectedDateMillis?.toLocalDateUtc()) },
            ) {
                ProtonText(text = "OK", color = ProtonTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                ProtonText(text = "Cancel", color = ProtonTheme.colors.onSurfaceVariant)
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun SectionLabel(text: String) {
    ProtonText(
        text = text,
        style = ProtonTheme.typography.labelSmall,
        color = ProtonTheme.colors.onSurfaceVariant.copy(alpha = 0.6f),
        maxLines = 1,
    )
}

@Composable
private fun FieldLabel(text: String) {
    ProtonText(
        text = text,
        style = ProtonTheme.typography.labelMedium,
        color = ProtonTheme.colors.onSurfaceVariant,
        modifier =
            Modifier.padding(
                start = ProtonDimension.Spacing4,
                bottom = ProtonDimension.Spacing8,
            ),
    )
}

@Composable
private fun FieldHelperText(text: String) {
    ProtonText(
        text = text,
        style = ProtonTheme.typography.labelMedium,
        color = ProtonTheme.colors.onSurfaceVariant.copy(alpha = 0.7f),
        modifier =
            Modifier.padding(
                start = ProtonDimension.Spacing4,
                top = ProtonDimension.Spacing8,
            ),
    )
}

@Composable
private fun CalendarIcon() {
    Icon(
        imageVector = Icons.Outlined.CalendarMonth,
        contentDescription = null,
        tint = ProtonTheme.colors.outline,
    )
}

private fun Month.displayName(): String = name.lowercase().replaceFirstChar { it.uppercase() }

private fun LocalDate.formatAsDisplayDate(): String {
    val dd = day.toString().padStart(2, '0')
    val mm = month.number.toString().padStart(2, '0')
    return "$dd/$mm/$year"
}

/** DatePicker works in UTC-midnight millis; convert both ways in UTC to avoid off-by-one days. */
private fun Long.toLocalDateUtc(): LocalDate = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC).date

private fun LocalDate.toEpochMillisUtc(): Long = atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
