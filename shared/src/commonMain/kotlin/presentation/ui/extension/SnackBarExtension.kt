package presentation.ui.extension

import androidx.compose.material3.SnackbarHostState
import domain.model.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showSnackBar(scope: CoroutineScope, status: Status) {
    if (status is Status.Error) {
        scope.launch {
            showSnackbar(
                message = status.message,
                withDismissAction = true
            )
        }
    } else {
        this.currentSnackbarData?.dismiss()
    }
}