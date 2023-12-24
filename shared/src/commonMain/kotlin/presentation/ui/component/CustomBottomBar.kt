package presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import domain.model.Status
import presentation.theme.Gray700

/**
 * TODO : allow sending attachments without text
 */
@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    status: Status,
    onSendClick: (String, List<ByteArray>) -> Unit
) {
    val textState = remember { mutableStateOf("") }
    val images = remember { mutableStateOf(listOf<ByteArray>()) }

    val scope = rememberCoroutineScope()
    val multipleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Multiple(),
        scope = scope,
        onResult = { images.value = it }
    )
    Column {
        LazyRow {
            items(images.value.size) { index ->
                val bitmap = images.value[index].toImageBitmap()
                ImageAttachment(
                    bitmap = bitmap,
                    onCloseClick = {
                        val mutableImages = images.value.toMutableList()
                        mutableImages.removeAt(index)
                        images.value = mutableImages
                    }
                )

            }
        }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            maxLines = 3,
            placeholder = {
                Text(
                    text = "Type a message...",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Gray700,
                    ),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            trailingIcon = {
                Button(
                    onClick = {
                        onSendClick(textState.value, images.value)
                        images.value = emptyList()
                        textState.value = ""
                    },
                    enabled = textState.value.isNotBlank() && status != Status.Loading,
                    content = {
                        if (status is Status.Loading)
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        else {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.rotate(-90.0F).size(20.dp),
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    shape = RoundedCornerShape(30),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        multipleImagePicker.launch()
                    },
                    content = {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null,
                            modifier = Modifier,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                )
            },
            modifier = modifier,
            shape = RoundedCornerShape(24),
        )
    }
}

@Composable
private fun ImageAttachment(bitmap: ImageBitmap, onCloseClick: () -> Unit = {}) {

    val iconSize = 20.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    Box(modifier = Modifier.padding((iconSize / 3))) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(80.dp)
                .wrapContentWidth()
                .shadow(1.dp, RoundedCornerShape(12.dp)),
        )

        IconButton(
            onClick = {
                onCloseClick()
            },
            modifier = Modifier
                .offset {
                    IntOffset(x = +offsetInPx, y = -offsetInPx)
                }
                .padding(5.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .size(iconSize)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
