package presentation.ui.extension

import androidx.compose.material3.SnackbarHostState
import domain.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showSnackBar(scope: CoroutineScope, status: Status) {
    if (status is Status.ERROR) {
        scope.launch {
            showSnackbar(
                message = "An error occurred, please retry.",
                withDismissAction = true
            )
        }
    } else {
        this.currentSnackbarData?.dismiss()
    }
}