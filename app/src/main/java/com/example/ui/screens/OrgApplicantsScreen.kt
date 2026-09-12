package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.models.Application
import com.example.models.ApplicationStatus
import com.example.models.VolunteerEvent
import com.example.ui.components.EmptyStateView
import com.example.ui.components.StatusBadge
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.CardBorder
import com.example.ui.theme.StatusAcceptedBg
import com.example.ui.theme.StatusAcceptedText
import com.example.ui.theme.StatusRejectedBg
import com.example.ui.theme.StatusRejectedText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OrgApplicantsScreen(
    event: VolunteerEvent,
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    onNavigateChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val eventApplicants = applications.filter { it.eventId == event.eventId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Volunteer Applicants",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Text(
                        text = event.title,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        maxLines = 1
                    )
                }
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
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        // Capacity and Privacy bar
        Surface(
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Capacity: ${event.volunteersAcceptedCount} / ${event.capacity} accepted",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BlueNavyDark
                    )
                    Text(
                        text = "${eventApplicants.size} Total Applied",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BluePrimary
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = BluePrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Volunteer privacy is protected. Government ID (CNIC) is strictly secured and hidden.",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        if (eventApplicants.isEmpty()) {
            EmptyStateView(
                title = "No Applicants Yet",
                subtitle = "New volunteer applications for this event will appear here once candidates submit their applications.",
                icon = Icons.Default.Group
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(eventApplicants, key = { it.applicationId }) { app ->
                    ApplicantCard(
                        application = app,
                        onAccept = { viewModel.acceptApplicant(app.applicationId) },
                        onReject = { viewModel.rejectApplicant(app.applicationId) },
                        onChat = onNavigateChat
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ApplicantCard(
    application: Application,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onChat: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("applicant_card_${application.applicationId}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        color = BlueUltraLight,
                        shape = CircleShape,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = application.volunteerName.take(1),
                                color = BluePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = application.volunteerName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Text(
                            text = "Age: ${application.volunteerAge} • Applied ${application.appliedAtText}",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                StatusBadge(status = application.status)
            }

            if (application.volunteerSkills.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    application.volunteerSkills.forEach { skill ->
                        Surface(
                            color = BlueUltraLight,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = skill,
                                fontSize = 11.sp,
                                color = BluePrimary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            if (application.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "\"${application.notes}\"",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Row with clear visual hierarchy, bold labels, and distinct primary/danger/secondary affordances
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onChat,
                    border = BorderStroke(1.dp, CardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = BlueNavyDark
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp), tint = BlueNavyDark)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Chat", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                if (application.status == ApplicationStatus.PENDING) {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onReject,
                        border = BorderStroke(1.dp, StatusRejectedText.copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = StatusRejectedBg.copy(alpha = 0.3f),
                            contentColor = StatusRejectedText
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(15.dp), tint = StatusRejectedText)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reject", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onAccept,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .testTag("accept_volunteer_btn_${application.applicationId}")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Accept", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
