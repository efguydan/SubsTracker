package me.efedaniel.substracker.ui.subscriptions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.datetime.LocalDate
import me.efedaniel.substracker.domain.Currency
import me.efedaniel.substracker.domain.Frequency
import me.efedaniel.substracker.ui.proton.components.button.ProtonButton
import me.efedaniel.substracker.ui.proton.components.button.ProtonButtonSize
import me.efedaniel.substracker.ui.proton.components.button.ProtonButtonType
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension

/** Billing frequency option exposed by the add-subscription form. */
enum class FrequencyChoice(
    val label: String,
) {
    Monthly("Monthly"),
    Annually("Annually"),
    OneTime("One-time"),
}

/** Maps a form choice to a domain [Frequency], anchoring recurring billing on [today]. */
internal fun FrequencyChoice.toDomain(today: LocalDate): Frequency =
    when (this) {
        FrequencyChoice.Monthly -> Frequency.Monthly(anchorDay = today.day)
        FrequencyChoice.Annually -> Frequency.Annually(anchorMonth = today.month, anchorDay = today.day)
        FrequencyChoice.OneTime -> Frequency.OneTime
    }

/**
 * Simple v1 add-subscription form. Collects raw values and forwards them via [onConfirm];
 * validation and domain construction live in [SubscriptionsViewModel].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: (name: String, priceText: String, currency: Currency, frequency: FrequencyChoice) -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf(Currency.EUR) }
    var frequency by remember { mutableStateOf(FrequencyChoice.Monthly) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ProtonTheme.colors.surface,
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ProtonDimension.Spacing16)
                    .navigationBarsPadding()
                    .imePadding(),
        ) {
            ProtonText(
                text = "Add subscription",
                style = ProtonTheme.typography.titleMedium,
                color = ProtonTheme.colors.onSurface,
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(ProtonDimension.Spacing12))

            OutlinedTextField(
                value = priceText,
                onValueChange = { priceText = it },
                label = { Text("Price") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                Currency.entries.forEachIndexed { index, entry ->
                    SegmentedButton(
                        selected = currency == entry,
                        onClick = { currency = entry },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = Currency.entries.size),
                    ) {
                        Text(entry.isoCode)
                    }
                }
            }
            Spacer(Modifier.height(ProtonDimension.Spacing12))

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                FrequencyChoice.entries.forEachIndexed { index, entry ->
                    SegmentedButton(
                        selected = frequency == entry,
                        onClick = { frequency = entry },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = FrequencyChoice.entries.size),
                    ) {
                        Text(entry.label)
                    }
                }
            }
            Spacer(Modifier.height(ProtonDimension.Spacing24))

            ProtonButton(
                text = "Add subscription",
                type = ProtonButtonType.PRIMARY,
                size = ProtonButtonSize.LARGE,
                fillMaxWidth = true,
                enabled = name.isNotBlank() && parsePriceToMinorUnits(priceText, currency) != null,
                onClick = { onConfirm(name, priceText, currency, frequency) },
            )
            Spacer(Modifier.height(ProtonDimension.Spacing16))
        }
    }
}
