package presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import presentation.theme.Gray700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    value: String,
    onVisibilityChanged: (Boolean) -> Unit,
    onSaveClicked: (String) -> Unit) {

    val keyVisibility = remember { mutableStateOf(false) }
    val textField = remember { mutableStateOf(value) }

    AlertDialog(
        onDismissRequest = { onVisibilityChanged(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Enter your personal API Key",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButton(
                        onClick = { onVisibilityChanged(false) },
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null,
                            )
                        },
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = textField.value,
                    onValueChange = { textField.value = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Your API Key...",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Gray700,
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    visualTransformation =
                        if (keyVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { keyVisibility.value = !keyVisibility.value },
                            content = {
                                Icon(
                                    imageVector =
                                        if (keyVisibility.value) Icons.Default.Visibility
                                        else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            },
                        )
                    },
                    shape = RoundedCornerShape(24),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    enabled = textField.value.isNotBlank() && textField.value != value,
                    onClick = {
                        onSaveClicked(textField.value)
                        onVisibilityChanged(false)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(text = "Save")
                }

            }
        }
    }
}