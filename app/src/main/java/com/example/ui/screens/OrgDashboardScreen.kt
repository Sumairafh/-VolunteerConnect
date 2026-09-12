package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.UserRole
import com.example.models.VolunteerEvent
import com.example.ui.components.EmptyStateView
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSubtle
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.BlueVibrant
import com.example.ui.theme.CardBorder
import com.example.ui.theme.StatusAcceptedBg
import com.example.ui.theme.StatusAcceptedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrgDashboardScreen(
    viewModel: VolunteerConnectViewModel,
    onNavigatePostEvent: () -> Unit,
    onViewApplicants: (VolunteerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentOrg by viewModel.currentOrg.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle()
    val applications by viewModel.applications.collectAsStateWithLifecycle()

    val orgEvents = events.filter { it.organizationId == currentOrg.organizationId }
    val orgApplications = applications.filter { it.organizationId == currentOrg.organizationId }
    val totalPending = orgApplications.count { it.status == com.example.models.ApplicationStatus.PENDING }
    val totalAccepted = orgApplications.count { it.status == com.example.models.ApplicationStatus.ACCEPTED }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigatePostEvent,
                containerColor = BlueVibrant,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("post_event_fab")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Post Event")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Post Event", fontWeight = FontWeight.Bold)
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
        ) {
            // Header
            Surface(
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = BlueUltraLight,
                                shape = CircleShape,
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.CorporateFare,
                                        contentDescription = null,
                                        tint = BluePrimary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = currentOrg.name,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BlueNavyDark
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified",
                                        tint = BlueVibrant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = "Organization Management Portal",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        // Switch Back to Volunteer View with clear button affordance
                        Surface(
                            color = BlueUltraLight,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, BlueSubtle),
                            modifier = Modifier.clickable {
                                viewModel.switchRole(UserRole.VOLUNTEER)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = "Switch to Volunteer",
                                    tint = BluePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Volunteer View",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BluePrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Metric Cards
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OrgMetricCard(
                            title = "Active Events",
                            value = orgEvents.size.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OrgMetricCard(
                            title = "Pending Review",
                            value = totalPending.toString(),
                            valueColor = if (totalPending > 0) Color(0xFFD97706) else BlueNavyDark,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OrgMetricCard(
                            title = "Accepted",
                            value = totalAccepted.toString(),
                            valueColor = StatusAcceptedText,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Events List
            LazyColumn(
                contentPadding = PaddingValues(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Managed Volunteering Events",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Text(
                            text = "${orgEvents.size} events",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )
                    }
                }

                if (orgEvents.isEmpty()) {
                    item {
                        EmptyStateView(
                            title = "No Events Created Yet",
                            subtitle = "Post your first volunteering opportunity to start connecting with dedicated volunteers.",
                            buttonText = "Post New Event",
                            onButtonClick = onNavigatePostEvent,
                            icon = Icons.Default.Event
                        )
                    }
                } else {
                    items(orgEvents, key = { it.eventId }) { event ->
                        val eventApps = orgApplications.filter { it.eventId == event.eventId }
                        val pendingCount = eventApps.count { it.status == com.example.models.ApplicationStatus.PENDING }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(18.dp),
                            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Surface(
                                            color = BlueUltraLight,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = event.category,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = BluePrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = event.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BlueNavyDark
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "${event.dateText} • ${event.startTime} - ${event.endTime}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "Location: ${event.locationName}",
                                    fontSize = 12.sp,
                                    color = TextTertiary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

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
                                            text = "${event.volunteersAcceptedCount} / ${event.capacity} Filled",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextPrimary
                                        )
                                        if (pendingCount > 0) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = Color(0xFFFEF3C7),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "$pendingCount New",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFB45309),
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }

                                    Button(
                                        onClick = { onViewApplicants(event) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = BluePrimary,
                                            contentColor = Color.White
                                        ),
                                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                        modifier = Modifier.height(40.dp)
                                    ) {
                                        Text("View Applicants (${eventApps.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrgMetricCard(
    title: String,
    value: String,
    valueColor: Color = BlueNavyDark,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                color = TextTertiary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
