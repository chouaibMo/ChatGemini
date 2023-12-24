package presentation.ui.extension

import androidx.compose.ui.graphics.Color
import domain.Resource
import presentation.theme.LightBlue
import presentation.theme.LightGreen
import presentation.theme.LightRed

fun Resource.snackBarColor(): Color {
    return when (this) {
        Resource.Success -> LightGreen
        Resource.Error -> LightRed
        else -> LightBlue
    }
}
