package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.Application
import com.example.models.ApplicationStatus
import com.example.ui.components.ActiveApplicationLimitBanner
import com.example.ui.components.ApplicationCard
import com.example.ui.components.EmptyStateView
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApplicationsScreen(
    viewModel: VolunteerConnectViewModel,
    onNavigateExplore: () -> Unit,
    onNavigateChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val activeCount by viewModel.activeApplicationsCount.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var applicationToCancel by remember { mutableStateOf<Application?>(null) }

    val myApplications = applications.filter { it.volunteerId == currentUser.userId }

    val tabTitles = listOf("All", "Pending", "Accepted", "Rejected", "Completed")

    val filteredApps = when (selectedTabIndex) {
        1 -> myApplications.filter { it.status == ApplicationStatus.PENDING }
        2 -> myApplications.filter { it.status == ApplicationStatus.ACCEPTED }
        3 -> myApplications.filter { it.status == ApplicationStatus.REJECTED }
        4 -> myApplications.filter { it.status == ApplicationStatus.COMPLETED }
        else -> myApplications
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = "My Applications",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BlueNavyDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Track your volunteering application reviews and active event assignments",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // Tabs
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = BluePrimary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                ActiveApplicationLimitBanner(
                    activeCount = activeCount,
                    maxLimit = 2
                )
            }

            if (filteredApps.isEmpty()) {
                item {
                    val emptySubtitle = when (selectedTabIndex) {
                        1 -> "You don't have any pending applications waiting for organization approval."
                        2 -> "You haven't been accepted to any upcoming volunteering events yet."
                        3 -> "No rejected applications. Great job!"
                        4 -> "No completed volunteering records yet."
                        else -> "You haven't applied to any volunteering opportunities yet."
                    }
                    EmptyStateView(
                        title = "No Applications Here",
                        subtitle = emptySubtitle,
                        buttonText = "Explore Opportunities",
                        onButtonClick = onNavigateExplore,
                        icon = Icons.Default.Assignment
                    )
                }
            } else {
                items(filteredApps, key = { it.applicationId }) { app ->
                    ApplicationCard(
                        application = app,
                        onCancelClick = { applicationToCancel = app },
                        onChatClick = onNavigateChat
                    )
                }
            }
        }
    }

    // Cancel Application Confirmation Dialog
    applicationToCancel?.let { app ->
        AlertDialog(
            onDismissRequest = { applicationToCancel = null },
            title = {
                Text(
                    text = "Withdraw Application?",
                    fontWeight = FontWeight.Bold,
                    color = BlueNavyDark,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to withdraw your application for '${app.eventTitle}'? This will free up 1 active application slot immediately.",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cancelApplication(app.applicationId)
                        applicationToCancel = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Withdraw", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { applicationToCancel = null }) {
                    Text("Keep Application", color = TextSecondary)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}
