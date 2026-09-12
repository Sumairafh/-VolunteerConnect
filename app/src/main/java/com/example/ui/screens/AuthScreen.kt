package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.UserRole
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSubtle
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.BlueVibrant
import com.example.ui.theme.CardBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: VolunteerConnectViewModel,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRegisterMode by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(UserRole.VOLUNTEER) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var cnic by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // App Logo & Brand Header
        Surface(
            color = Color.White,
            shape = CircleShape,
            shadowElevation = 3.dp,
            border = BorderStroke(1.5.dp, BlueSubtle),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = com.example.R.drawable.ic_volunteer_connect_emblem),
                    contentDescription = "Volunteer Connect Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Volunteer",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BlueNavyDark
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Connect",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BlueVibrant
            )
        }

        Text(
            text = "Modern Volunteering & Community Action Marketplace",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Role Selector Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                RoleTabButton(
                    title = "I'm a Volunteer",
                    subtitle = "Find & join events",
                    icon = Icons.Default.Person,
                    isSelected = selectedRole == UserRole.VOLUNTEER,
                    onClick = { selectedRole = UserRole.VOLUNTEER },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                RoleTabButton(
                    title = "Organization",
                    subtitle = "Post events & recruit",
                    icon = Icons.Default.CorporateFare,
                    isSelected = selectedRole == UserRole.ORGANIZATION,
                    onClick = { selectedRole = UserRole.ORGANIZATION },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Main Auth Form Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (isRegisterMode) "Create Account" else "Welcome Back",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueNavyDark
                )
                Text(
                    text = if (isRegisterMode) {
                        "Register as a ${if (selectedRole == UserRole.VOLUNTEER) "Volunteer" else "Verified Organization"}"
                    } else {
                        "Sign in to access your volunteering dashboard"
                    },
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isRegisterMode) {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(if (selectedRole == UserRole.VOLUNTEER) "Full Name" else "Organization Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BluePrimary) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BluePrimary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_email_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (isRegisterMode) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BluePrimary) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (selectedRole == UserRole.VOLUNTEER) {
                        OutlinedTextField(
                            value = cnic,
                            onValueChange = { cnic = it },
                            label = { Text("National ID (CNIC)") },
                            placeholder = { Text("e.g. 42101-1234567-1") },
                            leadingIcon = { Icon(Icons.Default.Shield, contentDescription = null, tint = BluePrimary) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            color = BlueUltraLight,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🔒 CNIC is encrypted for verification only and is never publicly shown to event organizers.",
                                fontSize = 11.sp,
                                color = BluePrimary,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BluePrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_password_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (selectedRole == UserRole.VOLUNTEER) {
                            viewModel.switchRole(UserRole.VOLUNTEER)
                        } else {
                            viewModel.switchRole(UserRole.ORGANIZATION)
                        }
                        onAuthSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("auth_submit_btn")
                ) {
                    Text(
                        text = if (isRegisterMode) "Register Account" else "Sign In",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = { isRegisterMode = !isRegisterMode },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(38.dp)
                ) {
                    Text(
                        text = if (isRegisterMode) "Already have an account? Sign In" else "Don't have an account? Register",
                        color = BluePrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // One-Tap Quick Demo Access
        Text(
            text = "⚡ QUICK DEMO ACCESS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextTertiary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = {
                    viewModel.switchRole(UserRole.VOLUNTEER)
                    onAuthSuccess()
                },
                border = BorderStroke(1.5.dp, BluePrimary.copy(alpha = 0.35f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = BluePrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .testTag("demo_volunteer_btn")
            ) {
                Text("Demo Volunteer", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(10.dp))

            OutlinedButton(
                onClick = {
                    viewModel.switchRole(UserRole.ORGANIZATION)
                    onAuthSuccess()
                },
                border = BorderStroke(1.5.dp, BluePrimary.copy(alpha = 0.35f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = BluePrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .testTag("demo_org_btn")
            ) {
                Text("Demo Org", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun RoleTabButton(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isSelected) BluePrimary else Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .height(60.dp)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) BluePrimary else Color.Transparent,
                contentColor = if (isSelected) Color.White else TextPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else BluePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else TextPrimary
                    )
                    Text(
                        text = subtitle,
                        fontSize = 10.sp,
                        color = if (isSelected) BlueSubtle else TextTertiary
                    )
                }
            }
        }
    }
}
