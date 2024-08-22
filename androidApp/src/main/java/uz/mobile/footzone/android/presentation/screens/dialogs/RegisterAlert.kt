package uz.mobile.footzone.android.presentation.screens.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.shadow10

@Composable
fun RegisterAlert(
    onDismissRequest: () -> Unit,
    onEnterAccountClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .blurredShadow(
                color = shadow10,
                shadowBlurRadius = 12.dp,
                offsetY = 6.dp
            ),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onEnterAccountClick,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = neutral10, contentColor = blue600),
                elevation = ButtonDefaults.elevatedButtonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.enter),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.enter_account_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = stringResource(R.string.enter_account_to_book),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = neutral10,
        textContentColor = neutral90,
        titleContentColor = neutral100,
    )
}

@Preview
@Composable
private fun RegisterAlertPr() {
    MyApplicationTheme {
        RegisterAlert({}) {

        }
    }
}