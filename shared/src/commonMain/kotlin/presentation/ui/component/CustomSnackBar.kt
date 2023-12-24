package presentation.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSnackBar(
    data: SnackbarData,
    containerColor: Color,
    contentColor: Color = Color.White,
) {
    Snackbar(
        containerColor = containerColor,
        contentColor = contentColor,
        dismissActionContentColor = Color.White,
        shape = RoundedCornerShape(20),
        snackbarData = data
    )
}
