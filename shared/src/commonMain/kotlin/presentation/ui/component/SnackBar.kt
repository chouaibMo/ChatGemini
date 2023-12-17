package presentation.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorSnackBar(snackBarData: SnackbarData, ) {
    Snackbar(
        containerColor = Color(0xFFf44949),
        contentColor = Color.White,
        dismissActionContentColor = Color.White,
        shape = RoundedCornerShape(20),
        snackbarData = snackBarData
    )
}
