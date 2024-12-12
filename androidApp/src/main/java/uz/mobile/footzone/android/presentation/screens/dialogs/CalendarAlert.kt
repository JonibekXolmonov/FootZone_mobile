package uz.mobile.footzone.android.presentation.screens.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.presentation.components.AppPrimaryBorderedButton
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral20
import uz.mobile.footzone.android.theme.neutral60
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.shadow10
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarAlert(
    onDismissRequest: () -> Unit,
    onSelectDate: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    AlertDialog(
        modifier = Modifier
            .blurredShadow(
                color = shadow10,
                shadowBlurRadius = 12.dp,
                offsetY = 6.dp
            ),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppPrimaryBorderedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    text = stringResource(R.string.cancel),
                    onClick = onDismissRequest
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppPrimaryButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    text = stringResource(R.string.select),
                    onClick = {
                        onSelectDate(selectedDate)
                        onDismissRequest()
                    }
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CalendarHeader(selectedDate) { newDate ->
                    selectedDate = newDate
                }
                CalendarBody(selectedDate) { date ->
                    selectedDate = date
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = neutral10,
        textContentColor = neutral90,
        titleContentColor = neutral100,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(currentDate: LocalDate, onMonthChange: (LocalDate) -> Unit) {
    val monthName = currentDate.month.getDisplayName(TextStyle.FULL, Locale("uz"))
    val year = currentDate.year

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppPrimaryBorderedButton(
            leadingIcon = R.drawable.chevron_left,
            modifier = Modifier.size(40.dp),
            onClick = {
                onMonthChange(currentDate.minusMonths(1))
            }
        )

        Text(
            text = "$monthName, $year",
            style = MaterialTheme.typography.titleMedium.copy(color = neutral100)
        )

        AppPrimaryBorderedButton(
            leadingIcon = R.drawable.chevron_right,
            modifier = Modifier.size(40.dp),
            onClick = {
                onMonthChange(currentDate.plusMonths(1))
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarBody(currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today = LocalDate.now()
    val daysOfWeek = listOf("Du", "Se", "Ch", "Pa", "Ju", "Sh", "Ya")

    val firstDayOfMonth = currentDate.withDayOfMonth(1)
    val lastDayOfMonth = YearMonth.of(currentDate.year, currentDate.month).lengthOfMonth()

    val previousMonthDays =
        YearMonth.of(currentDate.year, currentDate.month.minus(1)).lengthOfMonth()
    val startOffset = (firstDayOfMonth.dayOfWeek.value - 1) % 7
    val leadingDates = (previousMonthDays - startOffset + 1..previousMonthDays).toList()

    val endOffset = (7 - (startOffset + lastDayOfMonth) % 7) % 7
    val trailingDates = (1..endOffset).toList()

    val currentMonthDays = (1..lastDayOfMonth).toList()

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.titleMedium.copy(color = neutral60),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        val dates = leadingDates + currentMonthDays + trailingDates
        val rows = dates.size / 7

        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                for (col in 0 until 7) {
                    val dayIndex = row * 7 + col
                    if (dayIndex in dates.indices) {
                        val day = dates[dayIndex]
                        val date = when {
                            dayIndex < leadingDates.size -> currentDate.minusMonths(1)
                                .withDayOfMonth(day)

                            dayIndex >= leadingDates.size + currentMonthDays.size -> currentDate.plusMonths(
                                1
                            ).withDayOfMonth(day)

                            else -> currentDate.withDayOfMonth(day)
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = when {
                                        date == currentDate -> blue600
                                        date == today -> neutral20
                                        else -> Color.Transparent
                                    },
                                    shape = RoundedCornerShape(50)
                                )
                                .clickable(
                                    onClick = { onDateSelected(date) },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${date.dayOfMonth}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = when {
                                        date.month != currentDate.month -> neutral60
                                        date == currentDate -> neutral10
                                        date == today -> neutral90
                                        else -> neutral90
                                    },
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun CalendarAlertPr() {
    MyApplicationTheme {
        CalendarAlert(
            {}) {

        }
    }
}