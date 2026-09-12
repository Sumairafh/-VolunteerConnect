package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.UserRole
import com.example.ui.components.ActiveApplicationLimitBanner
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VolunteerProfileScreen(
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val profile by viewModel.volunteerProfile.collectAsStateWithLifecycle()
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val activeCount by viewModel.activeApplicationsCount.collectAsStateWithLifecycle()

    var showEditDialog by remember { mutableStateOf(false) }

    val myApps = applications.filter { it.volunteerId == currentUser.userId }
    val acceptedCount = myApps.count { it.status == com.example.models.ApplicationStatus.ACCEPTED }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Volunteer Profile",
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
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = BluePrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Profile Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Surface(
                        color = BluePrimary,
                        shape = CircleShape,
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = currentUser.fullName.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentUser.fullName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = BlueVibrant,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${currentUser.city}, Pakistan",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // CNIC Security Shield Badge
                    Surface(
                        color = Color(0xFFEFF6FF),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Protected",
                                tint = BluePrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CNIC: ${currentUser.cnicMasked} • Protected",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BluePrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Application Limit Banner
            ActiveApplicationLimitBanner(activeCount = activeCount, maxLimit = 2)

            Spacer(modifier = Modifier.height(16.dp))

            // Impact Stats Grid
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    title = "Active Slots",
                    value = "$activeCount / 2",
                    icon = Icons.Default.Assignment,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                StatCard(
                    title = "Accepted",
                    value = acceptedCount.toString(),
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                StatCard(
                    title = "Hours",
                    value = "${profile.hoursVolunteered}h",
                    icon = Icons.Default.Timer,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bio & Details
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "About Me",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = profile.bio,
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Skills & Expertise",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        profile.skills.forEach { skill ->
                            Surface(
                                color = BlueUltraLight,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = skill,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BluePrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Availability",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = profile.availability,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switch to Organization View
            Card(
                colors = CardDefaults.cardColors(containerColor = BlueUltraLight),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BlueSubtle)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Organization Dashboard",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Text(
                            text = "Post events and manage applicants as Green Future Foundation",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    Button(
                        onClick = { viewModel.switchRole(UserRole.ORGANIZATION) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Switch", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App Brand & Logo Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BlueSubtle),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Image(
                            painter = painterResource(id = com.example.R.drawable.ic_volunteer_connect_emblem),
                            contentDescription = "Volunteer Connect Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Volunteer Connect",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Linking People • Making Impact",
                            fontSize = 12.sp,
                            color = BlueVibrant,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Version 1.0.0 • Verified Community Platform",
                            fontSize = 11.sp,
                            color = TextTertiary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            bio = profile.bio,
            availability = profile.availability,
            city = currentUser.city,
            onDismiss = { showEditDialog = false },
            onSave = { newBio, newAvail, newCity ->
                viewModel.updateVolunteerProfile(
                    bio = newBio,
                    skills = profile.skills,
                    interests = profile.interests,
                    availability = newAvail,
                    city = newCity
                )
                showEditDialog = false
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BluePrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BlueNavyDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                color = TextTertiary
            )
        }
    }
}

@Composable
private fun EditProfileDialog(
    bio: String,
    availability: String,
    city: String,
    onDismiss: () -> Unit,
    onSave: (bio: String, availability: String, city: String) -> Unit
) {
    var editBio by remember { mutableStateOf(bio) }
    var editAvail by remember { mutableStateOf(availability) }
    var editCity by remember { mutableStateOf(city) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Volunteer Profile",
                fontWeight = FontWeight.Bold,
                color = BlueNavyDark,
                fontSize = 18.sp
            )
        },
        text = {
            Column {
                Text(text = "City", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = editCity,
                    onValueChange = { editCity = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Bio & Motivation", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = editBio,
                    onValueChange = { editBio = it },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Availability", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = editAvail,
                    onValueChange = { editAvail = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(editBio, editAvail, editCity) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(42.dp)
            ) {
                Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    )
}
