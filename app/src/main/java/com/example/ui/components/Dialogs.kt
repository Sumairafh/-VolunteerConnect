package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.VolunteerEvent
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.BlueVibrant
import com.example.ui.theme.CardBorder
import com.example.ui.theme.StatusAcceptedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ConfirmApplyDialog(
    event: VolunteerEvent,
    activeCount: Int,
    maxLimit: Int = 2,
    onDismiss: () -> Unit,
    onConfirm: (notes: String) -> Unit
) {
    var notes by remember { mutableStateOf("") }
    val isMaxed = activeCount >= maxLimit

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isMaxed) Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (isMaxed) Color(0xFFD97706) else BluePrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isMaxed) "Application Limit Reached" else "Confirm Application",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueNavyDark
                )
            }
        },
        text = {
            Column {
                if (isMaxed) {
                    Surface(
                        color = Color(0xFFFFFBEB),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Strict Limit: 2 Active Applications",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309),
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "You already have 2 active applications ($activeCount/$maxLimit). You cannot apply for another opportunity until an existing application is reviewed or withdrawn.",
                                color = Color(0xFF92400E),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                } else {
                    Surface(
                        color = BlueUltraLight,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = event.title,
                                fontWeight = FontWeight.Bold,
                                color = BlueNavyDark,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Org: ${event.organizationName}",
                                fontSize = 12.sp,
                                color = BluePrimary,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Date: ${event.dateText} • ${event.startTime}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "Location: ${event.locationName}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Active slots after applying: ${activeCount + 1}/$maxLimit",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BlueNavyDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Brief message or relevant skills (optional):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("e.g. Passionate about coastal cleaning, first-aid certified", fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("apply_notes_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        },
        confirmButton = {
            if (!isMaxed) {
                Button(
                    onClick = { onConfirm(notes) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(42.dp)
                        .testTag("confirm_application_btn")
                ) {
                    Text("Confirm Application", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF64748B),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text("Understood", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        },
        dismissButton = {
            if (!isMaxed) {
                TextButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text("Cancel", color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White
    )
}

@Composable
fun ReportDialog(
    targetName: String,
    onDismiss: () -> Unit,
    onSubmit: (reason: String) -> Unit
) {
    val reasons = listOf(
        "Misleading or inaccurate details",
        "Commercial / paid work falsely listed as volunteer",
        "Inappropriate communication or conduct",
        "Safety or security concern",
        "Other policy violation"
    )
    var selectedReason by remember { mutableStateOf(reasons.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color(0xFFDC2626),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Report Opportunity",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueNavyDark
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "Reporting '$targetName'. Select a violation reason for administrative review:",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                reasons.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason },
                            colors = RadioButtonDefaults.colors(selectedColor = BluePrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = reason, fontSize = 12.sp, color = TextPrimary)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(selectedReason) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC2626),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(42.dp)
            ) {
                Text("Submit Report", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(42.dp)
            ) {
                Text("Cancel", color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White
    )
}
