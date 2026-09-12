package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.NotificationItem
import com.example.models.NotificationType
import com.example.ui.components.EmptyStateView
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.CardBorder
import com.example.ui.theme.StatusAcceptedBg
import com.example.ui.theme.StatusAcceptedText
import com.example.ui.theme.StatusPendingBg
import com.example.ui.theme.StatusPendingText
import com.example.ui.theme.StatusRejectedBg
import com.example.ui.theme.StatusRejectedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Notifications",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueNavyDark
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BlueNavyDark
                    )
                }
            },
            actions = {
                if (notifications.any { !it.isRead }) {
                    TextButton(
                        onClick = { viewModel.markNotificationsRead() },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = null,
                            tint = BluePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mark all read", color = BluePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        if (notifications.isEmpty()) {
            EmptyStateView(
                title = "No Notifications",
                subtitle = "You're all caught up! You'll receive real-time updates when organizations review your volunteering applications.",
                icon = Icons.Default.Notifications
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications, key = { it.notificationId }) { notif ->
                    NotificationCard(notif = notif)
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(notif: NotificationItem) {
    val (icon, iconBg, iconColor) = when (notif.type) {
        NotificationType.APPLICATION_ACCEPTED -> Triple(
            Icons.Default.CheckCircle,
            StatusAcceptedBg,
            StatusAcceptedText
        )
        NotificationType.APPLICATION_SUBMITTED -> Triple(
            Icons.Default.AssignmentTurnedIn,
            BlueUltraLight,
            BluePrimary
        )
        NotificationType.APPLICATION_REJECTED -> Triple(
            Icons.Default.Warning,
            StatusRejectedBg,
            StatusRejectedText
        )
        NotificationType.LIMIT_WARNING -> Triple(
            Icons.Default.Info,
            StatusPendingBg,
            StatusPendingText
        )
        NotificationType.NEW_MESSAGE -> Triple(
            Icons.Default.Message,
            BlueUltraLight,
            BluePrimary
        )
        NotificationType.EVENT_UPDATE -> Triple(
            Icons.Default.Info,
            BlueUltraLight,
            BluePrimary
        )
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (notif.isRead) Color.White else Color(0xFFF8FAFC)
        ),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(if (notif.isRead) CardBorder else Color(0xFFBFDBFE))
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                color = iconBg,
                shape = CircleShape,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = notif.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    if (!notif.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(BluePrimary, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notif.message,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = notif.timeAgo,
                    fontSize = 11.sp,
                    color = TextTertiary
                )
            }
        }
    }
}
