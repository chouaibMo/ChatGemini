package presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.Gray700


@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CustomAppBar(onActionClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            title = {
                Column {
                    Text(
                        text = "ChatGemini",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                    Text(
                        text = "Powered by Gemini Pro",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = Gray700,
                        ),
                    )
                }
            },
            navigationIcon = {
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource("logo.png"),
                        contentDescription = "Compose Multiplatform icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .clickable {}
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        onActionClick()
                    },
                    content = {
                        Icon(
                            Icons.Filled.Key,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    },
                )
            },

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )
    }
}