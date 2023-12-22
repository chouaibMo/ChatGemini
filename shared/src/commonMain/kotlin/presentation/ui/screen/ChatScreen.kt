package presentation.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import domain.Message
import domain.Resource
import kotlinx.coroutines.launch
import presentation.ui.component.BottomBar
import presentation.ui.component.ErrorSnackBar
import presentation.ui.component.MessageBubble
import presentation.ui.component.TopBar

@Composable
fun ChatScreen() {
    val focusManager = LocalFocusManager.current
    val viewModel = remember { ChatViewModel() }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 30.dp, top = 5.dp),
                status = viewModel.status.value,
                onSendClick = { text, images ->
                    coroutineScope.launch {
                        viewModel.generateContent(text, images)
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data -> ErrorSnackBar(data) }
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
    ) {
        ChatList(
            modifier = Modifier.padding(it),
            messages = viewModel.messages.value
        )

        if (viewModel.status.value is Resource.Error) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "An error occurred, please retry.",
                    withDismissAction = true
                )
            }
        }
    }
}


@Composable
fun ChatList(modifier: Modifier, messages: List<Message>) {
    val listState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(messages) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages.size) {
            val message = messages[it]
            MessageBubble(message = message)
        }
    }
}