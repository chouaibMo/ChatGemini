package presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.markdownColor
import domain.model.Message
import presentation.theme.Gray700

@Composable
inline fun MessageBubble(message: Message, modifier: Modifier = Modifier) {
    val bubbleColor =
        if (message.isBotMessage) MaterialTheme.colorScheme.surface
        else MaterialTheme.colorScheme.secondaryContainer

    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        visibility = true
    }
    AnimatedVisibility(
        visible = visibility,
        enter = slideInHorizontally()
                + expandHorizontally(expandFrom = Alignment.Start)
                + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f))
                + fadeIn(initialAlpha = 0.3f),
    ) {
        Box(
            contentAlignment = if (!message.isBotMessage) Alignment.CenterEnd else Alignment.CenterStart,
            modifier = modifier
                .padding(
                    start = if (message.isBotMessage) 0.dp else 50.dp,
                    end = if (message.isBotMessage) 50.dp else 0.dp,
                )
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp,
                                    topEnd = if (message.isBotMessage) 20.dp else 2.dp,
                                    topStart = if (message.isBotMessage) 2.dp else 20.dp
                                )
                            )
                            .background(color = bubbleColor)
                            .padding(vertical = 5.dp, horizontal = 16.dp),
                    ) {
                        Column {
                            Text(
                                text = message.sender.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Left,
                                color = if (message.isBotMessage) MaterialTheme.colorScheme.secondary
                                else MaterialTheme.colorScheme.primary,
                            )
                            if (message.isBotMessage && message.isLoading) {
                                LoadingAnimation(
                                    circleSize = 8.dp,
                                    spaceBetween = 5.dp,
                                    travelDistance = 10.dp,
                                    circleColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(top = 14.dp)
                                )
                            } else {
                                Markdown(
                                    content = message.text,
                                    colors = markdownColor(
                                        text = LocalContentColor.current,
                                        codeText = MaterialTheme.colorScheme.tertiaryContainer
                                    ),
                                    modifier = Modifier.wrapContentWidth()
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    text = message.time,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp,
                                    color = Gray700
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
