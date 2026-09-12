package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.ApplicationStatus
import com.example.models.VolunteerEvent
import com.example.ui.components.ConfirmApplyDialog
import com.example.ui.components.ReportDialog
import com.example.ui.components.StatusBadge
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSubtle
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.BlueVibrant
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EventDetailsScreen(
    event: VolunteerEvent,
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    onNavigateChat: () -> Unit,
    onNavigateMapRoute: (VolunteerEvent) -> Unit = {},
    onNavigateWeather: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val activeCount by viewModel.activeApplicationsCount.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    var showApplyDialog by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    val myApp = applications.firstOrNull {
        it.eventId == event.eventId && it.volunteerId == currentUser.userId && it.status != ApplicationStatus.CANCELLED
    }
    val hasApplied = myApp != null
    val isLimitMaxed = activeCount >= 2

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Event Details",
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
                    IconButton(onClick = { showReportDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Report",
                            tint = TextTertiary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                    if (hasApplied && myApp != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Application Status:",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                StatusBadge(status = myApp.status)
                            }
                            if (myApp.status == ApplicationStatus.ACCEPTED || myApp.status == ApplicationStatus.PENDING) {
                                Button(
                                    onClick = onNavigateChat,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BluePrimary,
                                        contentColor = Color.White
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                                    modifier = Modifier.height(44.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Chat,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Chat with Org", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                            }
                        }
                    } else if (event.isFull) {
                        Button(
                            onClick = {},
                            enabled = false,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = Color(0xFFE2E8F0),
                                disabledContentColor = Color(0xFF64748B)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Event Full (Maximum Capacity Reached)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    } else {
                        Button(
                            onClick = { showApplyDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isLimitMaxed) Color(0xFFD97706) else BlueVibrant,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("detail_apply_now_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isLimitMaxed) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Application Limit Reached (2/2 Active)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                } else {
                                    Text("Apply Now ($activeCount/2 Active)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Organization Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Surface(
                        color = BlueUltraLight,
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CorporateFare,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = event.organizationName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            if (event.isOrgVerified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Organization",
                                    tint = BlueVibrant,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }
                        Text(
                            text = "Verified Non-Profit Organizer",
                            fontSize = 12.sp,
                            color = BluePrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Event Title & Category
            Surface(
                color = BlueUltraLight,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = event.category,
                    color = BluePrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = event.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BlueNavyDark,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Info Grid
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(
                        icon = Icons.Default.CalendarToday,
                        title = "Date & Day",
                        value = "${event.dateText} (${event.day})"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(
                        icon = Icons.Default.AccessTime,
                        title = "Time",
                        value = "${event.startTime} – ${event.endTime}"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(
                        icon = Icons.Default.LocationOn,
                        title = "Location",
                        value = "${event.locationName}\n${event.address}, ${event.city}"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(
                        icon = Icons.Default.Group,
                        title = "Volunteers",
                        value = "${event.volunteersAcceptedCount} accepted of ${event.capacity} needed (${event.availableSpaces} spaces remaining)"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(
                        icon = Icons.Default.HourglassBottom,
                        title = "Application Deadline",
                        value = event.deadline
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Venue Navigation & Weather Outlook Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Venue Navigation & Weather",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueNavyDark
                            )
                            Text(
                                text = "Real-time travel route & field conditions",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Surface(
                            color = BlueUltraLight,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${event.distanceKm} km • ~${event.etaMinutes} min",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BluePrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigateMapRoute(event) },
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("details_map_route_btn")
                        ) {
                            Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Map & Route", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onNavigateWeather(event.city) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("details_weather_btn")
                        ) {
                            Icon(Icons.Default.WbSunny, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Weather", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description Section
            SectionCard(title = "About This Volunteering Opportunity") {
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Requirements Section
            SectionCard(title = "Volunteer Requirements") {
                Text(
                    text = "Age Requirement: ${event.minAge} to ${event.maxAge} years",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                if (event.requiredSkills.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Preferred Skills:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        event.requiredSkills.forEach { skill ->
                            Surface(
                                color = BlueUltraLight,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = skill,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BluePrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "What to Bring:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
                Text(
                    text = event.whatToBring,
                    fontSize = 13.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Special Instructions:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
                Text(
                    text = event.specialInstructions,
                    fontSize = 13.sp,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showApplyDialog) {
        ConfirmApplyDialog(
            event = event,
            activeCount = activeCount,
            maxLimit = 2,
            onDismiss = { showApplyDialog = false },
            onConfirm = { notes ->
                viewModel.applyForEvent(event, notes) {
                    showApplyDialog = false
                }
            }
        )
    }

    if (showReportDialog) {
        ReportDialog(
            targetName = event.title,
            onDismiss = { showReportDialog = false },
            onSubmit = { reason ->
                viewModel.reportItem("Event", event.eventId, reason)
                showReportDialog = false
            }
        )
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(
            color = BlueUltraLight,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextTertiary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BlueNavyDark
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}
