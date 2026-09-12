package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.Application
import com.example.models.ApplicationStatus
import com.example.models.EventStatus
import com.example.models.VolunteerEvent
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSubtle
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.BlueVibrant
import com.example.ui.theme.CardBorder
import com.example.ui.theme.StatusAcceptedBg
import com.example.ui.theme.StatusAcceptedText
import com.example.ui.theme.StatusCompletedBg
import com.example.ui.theme.StatusCompletedText
import com.example.ui.theme.StatusPendingBg
import com.example.ui.theme.StatusPendingText
import com.example.ui.theme.StatusRejectedBg
import com.example.ui.theme.StatusRejectedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun StatusBadge(
    status: ApplicationStatus,
    modifier: Modifier = Modifier
) {
    val (bg, fg, label) = when (status) {
        ApplicationStatus.PENDING -> Triple(StatusPendingBg, StatusPendingText, "Pending Review")
        ApplicationStatus.ACCEPTED -> Triple(StatusAcceptedBg, StatusAcceptedText, "Accepted")
        ApplicationStatus.REJECTED -> Triple(StatusRejectedBg, StatusRejectedText, "Rejected")
        ApplicationStatus.CANCELLED -> Triple(Color(0xFFF1F5F9), Color(0xFF64748B), "Cancelled")
        ApplicationStatus.COMPLETED -> Triple(StatusCompletedBg, StatusCompletedText, "Completed")
    }

    Surface(
        color = bg,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            color = fg,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun EventStatusBadge(
    status: EventStatus,
    modifier: Modifier = Modifier
) {
    val (bg, fg, label) = when (status) {
        EventStatus.ACTIVE -> Triple(BlueUltraLight, BluePrimary, "Active")
        EventStatus.FULL -> Triple(StatusPendingBg, StatusPendingText, "Event Full")
        EventStatus.COMPLETED -> Triple(StatusCompletedBg, StatusCompletedText, "Completed")
        EventStatus.CANCELLED -> Triple(StatusRejectedBg, StatusRejectedText, "Cancelled")
    }

    Surface(
        color = bg,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            color = fg,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ActiveApplicationLimitBanner(
    activeCount: Int,
    maxLimit: Int = 2,
    modifier: Modifier = Modifier
) {
    val isMaxed = activeCount >= maxLimit
    val bannerBg = if (isMaxed) Color(0xFFFFFBEB) else BlueUltraLight
    val borderColor = if (isMaxed) Color(0xFFFDE68A) else BlueSubtle
    val titleColor = if (isMaxed) Color(0xFFB45309) else BlueNavyDark

    Card(
        colors = CardDefaults.cardColors(containerColor = bannerBg),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(borderColor)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("active_limit_banner")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Limit Info",
                        tint = titleColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Active Applications Limit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = titleColor
                    )
                }
                Surface(
                    color = if (isMaxed) Color(0xFFFEF3C7) else Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "$activeCount / $maxLimit Active",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isMaxed) Color(0xFFB45309) else BluePrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { (activeCount.toFloat() / maxLimit.toFloat()).coerceIn(0f, 1f) },
                color = if (isMaxed) Color(0xFFF59E0B) else BlueVibrant,
                trackColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isMaxed) {
                    "You have reached your 2 active applications limit. A slot will open once an organization reviews or you withdraw an application."
                } else {
                    "You can have a maximum of 2 active volunteering applications simultaneously. You have ${maxLimit - activeCount} slot available."
                },
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventCard(
    event: VolunteerEvent,
    onClick: () -> Unit,
    onApplyClick: () -> Unit,
    hasApplied: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("event_card_${event.eventId}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Top Org Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        color = BlueUltraLight,
                        shape = CircleShape,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CorporateFare,
                                contentDescription = "Org Logo",
                                tint = BluePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = event.organizationName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (event.isOrgVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Organization",
                                    tint = BlueVibrant,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = event.city,
                            fontSize = 12.sp,
                            color = TextTertiary
                        )
                    }
                }

                Surface(
                    color = BlueUltraLight,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = event.category,
                        color = BluePrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Event Title
            Text(
                text = event.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BlueNavyDark,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Short Description
            Text(
                text = event.description,
                fontSize = 13.sp,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Meta Details: Date, Time, Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = event.dateText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(14.dp))
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${event.startTime} - ${event.endTime}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = event.locationName,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Volunteers Required / Available
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = BluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Volunteers: ${event.volunteersAcceptedCount} / ${event.capacity}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (event.isFull) StatusRejectedText else TextPrimary
                    )
                    if (event.isFull) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(Full)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = StatusRejectedText
                        )
                    }
                }

                // Action button with clear visual hierarchy, iconography, and touch target
                if (hasApplied) {
                    OutlinedButton(
                        onClick = onClick,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, StatusAcceptedText),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = StatusAcceptedBg.copy(alpha = 0.5f),
                            contentColor = StatusAcceptedText
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                        modifier = Modifier.height(42.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = StatusAcceptedText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Applied", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = StatusAcceptedText)
                    }
                } else if (event.isFull) {
                    OutlinedButton(
                        onClick = onClick,
                        enabled = false,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, CardBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            disabledContainerColor = Color(0xFFF1F5F9),
                            disabledContentColor = TextTertiary
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                        modifier = Modifier.height(42.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TextTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Capacity Full", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    Button(
                        onClick = onApplyClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueVibrant,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier
                            .height(42.dp)
                            .testTag("apply_now_btn_${event.eventId}")
                    ) {
                        Text("Apply Now", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(
    application: Application,
    onCancelClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("application_card_${application.applicationId}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = application.eventTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = application.organizationName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = BluePrimary
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                StatusBadge(status = application.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${application.eventDate} • ${application.eventTime}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = application.eventLocation,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Applied on: ${application.appliedAtText}",
                fontSize = 11.sp,
                color = TextTertiary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action row with clear contrast, iconography and distinct secondary vs primary weights
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (application.status == ApplicationStatus.PENDING) {
                    OutlinedButton(
                        onClick = onCancelClick,
                        border = BorderStroke(1.dp, StatusRejectedText.copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = StatusRejectedBg.copy(alpha = 0.3f),
                            contentColor = StatusRejectedText
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = StatusRejectedText,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Withdraw", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                if (application.status == ApplicationStatus.ACCEPTED || application.status == ApplicationStatus.PENDING) {
                    Button(
                        onClick = onChatClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Chat with Org", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(
    title: String,
    subtitle: String,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.CorporateFare,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Surface(
            color = BlueUltraLight,
            shape = CircleShape,
            modifier = Modifier.size(72.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = BlueNavyDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 18.sp
        )

        if (buttonText != null && onButtonClick != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                modifier = Modifier.height(46.dp)
            ) {
                Text(text = buttonText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}
