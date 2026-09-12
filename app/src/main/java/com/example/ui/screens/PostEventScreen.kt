package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.CardBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.VolunteerConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEventScreen(
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    onEventCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Environment") }
    var description by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf("Saturday, 17 Oct 2026") }
    var day by remember { mutableStateOf("Saturday") }
    var startTime by remember { mutableStateOf("09:00 AM") }
    var endTime by remember { mutableStateOf("01:00 PM") }
    var deadline by remember { mutableStateOf("15 Oct 2026") }
    var locationName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("Karachi") }
    var capacityText by remember { mutableStateOf("30") }
    var minAgeText by remember { mutableStateOf("16") }
    var skillsText by remember { mutableStateOf("Teamwork, First Aid, Organization") }
    var whatToBring by remember { mutableStateOf("Comfortable shoes, reusable water bottle, national ID") }
    var specialInstructions by remember { mutableStateOf("Please arrive 15 minutes before the briefing.") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Environment", "Food & Relief", "Education", "Community", "Animal Welfare", "Healthcare")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Post Volunteering Event",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
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
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Event Overview",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    FormField(label = "Event Title *") {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = { Text("e.g. Coastal Mangrove Restoration Drive", fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("event_title_input"),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    FormField(label = "Category") {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            categories.take(3).forEach { cat ->
                                FilterChip(
                                    selected = category == cat,
                                    onClick = { category = cat },
                                    label = { Text(cat, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = BluePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            categories.drop(3).forEach { cat ->
                                FilterChip(
                                    selected = category == cat,
                                    onClick = { category = cat },
                                    label = { Text(cat, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = BluePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    FormField(label = "Full Event Description *") {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text("Describe event goals, volunteer responsibilities, and expected community impact...", fontSize = 13.sp) },
                            minLines = 3,
                            maxLines = 5,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("event_desc_input"),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Schedule & Location Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Date, Time & Location",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    FormField(label = "Event Date *") {
                        OutlinedTextField(
                            value = dateText,
                            onValueChange = { dateText = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            FormField(label = "Start Time") {
                                OutlinedTextField(
                                    value = startTime,
                                    onValueChange = { startTime = it },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            FormField(label = "End Time") {
                                OutlinedTextField(
                                    value = endTime,
                                    onValueChange = { endTime = it },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FormField(label = "Application Deadline *") {
                        OutlinedTextField(
                            value = deadline,
                            onValueChange = { deadline = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FormField(label = "Venue / Location Name *") {
                        OutlinedTextField(
                            value = locationName,
                            onValueChange = { locationName = it },
                            placeholder = { Text("e.g. Hill Park Community Grounds", fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FormField(label = "Address & City *") {
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            placeholder = { Text("Street address and sector", fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Volunteer Capacity Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Volunteer Requirements",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            FormField(label = "Capacity (Seats) *") {
                                OutlinedTextField(
                                    value = capacityText,
                                    onValueChange = { capacityText = it },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            FormField(label = "Minimum Age") {
                                OutlinedTextField(
                                    value = minAgeText,
                                    onValueChange = { minAgeText = it },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FormField(label = "Required Skills (Comma separated)") {
                        OutlinedTextField(
                            value = skillsText,
                            onValueChange = { skillsText = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FormField(label = "What to Bring") {
                        OutlinedTextField(
                            value = whatToBring,
                            onValueChange = { whatToBring = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage ?: "",
                    color = Color(0xFFDC2626),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        errorMessage = "Please enter an event title."
                        return@Button
                    }
                    if (description.isBlank()) {
                        errorMessage = "Please enter event description."
                        return@Button
                    }
                    if (locationName.isBlank()) {
                        errorMessage = "Please provide the venue or location name."
                        return@Button
                    }
                    val capacity = capacityText.toIntOrNull() ?: 25
                    val minAge = minAgeText.toIntOrNull() ?: 16
                    val skills = skillsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }

                    viewModel.createEvent(
                        title = title,
                        description = description,
                        category = category,
                        dateText = dateText,
                        day = day,
                        startTime = startTime,
                        endTime = endTime,
                        deadline = deadline,
                        locationName = locationName,
                        address = address.ifBlank { "Karachi" },
                        city = city,
                        capacity = capacity,
                        minAge = minAge,
                        skills = skills,
                        whatToBring = whatToBring,
                        specialInstructions = specialInstructions
                    )
                    onEventCreated()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("publish_event_submit_btn")
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Publish Volunteering Event", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun FormField(
    label: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        content()
    }
}
