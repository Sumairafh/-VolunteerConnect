package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.UserRole
import com.example.models.VolunteerEvent
import com.example.ui.components.ActiveApplicationLimitBanner
import com.example.ui.components.ConfirmApplyDialog
import com.example.ui.components.EmptyStateView
import com.example.ui.components.EventCard
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
fun VolunteerHomeScreen(
    viewModel: VolunteerConnectViewModel,
    onEventClick: (VolunteerEvent) -> Unit,
    onNavigateNotifications: () -> Unit,
    onNavigateProfile: () -> Unit,
    onNavigateMapRoute: (VolunteerEvent?) -> Unit = {},
    onNavigateWeather: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val events by viewModel.filteredEvents.collectAsStateWithLifecycle()
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val activeCount by viewModel.activeApplicationsCount.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()
    val filterOnlySpaces by viewModel.filterOnlyWithSpaces.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val currentWeather by viewModel.currentWeather.collectAsStateWithLifecycle()

    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedEventForApply by remember { mutableStateOf<VolunteerEvent?>(null) }

    val unreadNotifs = notifications.count { !it.isRead }
    val categories = listOf("All", "Environment", "Food & Relief", "Education", "Community", "Animal Welfare")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // Top Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                // Main Header Row: Branding takes full priority with no truncation
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            color = Color.White,
                            shape = CircleShape,
                            shadowElevation = 2.dp,
                            border = BorderStroke(1.dp, BlueSubtle),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(id = com.example.R.drawable.ic_volunteer_connect_emblem),
                                    contentDescription = "Volunteer Connect Logo",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(3.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Volunteer",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BlueNavyDark
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Connect",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BlueVibrant
                                )
                            }
                            Text(
                                text = "Serve & empower your community",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                maxLines = 1
                            )
                        }
                    }

                    // Compact Right Actions: Notifications & Switch Role & Profile
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = BlueUltraLight,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BlueSubtle),
                            modifier = Modifier.clickable {
                                viewModel.switchRole(UserRole.ORGANIZATION)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = "Switch to Org View",
                                    tint = BluePrimary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Org",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BluePrimary
                                )
                            }
                        }

                        IconButton(
                            onClick = onNavigateNotifications,
                            modifier = Modifier.size(34.dp)
                        ) {
                            BadgedBox(
                                badge = {
                                    if (unreadNotifs > 0) {
                                        Badge(containerColor = Color(0xFFEF4444)) {
                                            Text(unreadNotifs.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = BlueNavyDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Surface(
                            color = BluePrimary,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onNavigateProfile() }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = currentUser.fullName.take(1),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Dynamic Live Context Bar: Quick In-App Weather & Route Navigation Checkers
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Live Weather Pill
                    Surface(
                        color = Color(0xFFF0F9FF),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFBAE6FD)),
                        modifier = Modifier
                            .clickable { onNavigateWeather(currentWeather.cityName) }
                            .testTag("home_weather_quick_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = null,
                                tint = Color(0xFF0284C7),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${currentWeather.cityName} • ${currentWeather.temperatureC}°C ${currentWeather.condition}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0369A1)
                            )
                        }
                    }

                    // Route Checker Pill
                    Surface(
                        color = BlueUltraLight,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, BlueSubtle),
                        modifier = Modifier
                            .clickable { onNavigateMapRoute(null) }
                            .testTag("home_route_quick_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Venue Route Guide",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BluePrimary
                            )
                        }
                    }

                    // Active Application Slot Pill
                    Surface(
                        color = if (activeCount >= 2) Color(0xFFFEF2F2) else Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, if (activeCount >= 2) Color(0xFFFECACA) else Color(0xFFBBF7D0))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = if (activeCount >= 2) Icons.Default.Lock else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (activeCount >= 2) Color(0xFFDC2626) else Color(0xFF16A34A),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Active Limit: $activeCount/2",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activeCount >= 2) Color(0xFFDC2626) else Color(0xFF15803D)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search and Filter Bar (Indeed style)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearch(it) },
                        placeholder = {
                            Text(
                                text = "Search event title, organization, location...",
                                fontSize = 13.sp,
                                color = TextTertiary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = BluePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearch("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = TextTertiary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BluePrimary,
                            unfocusedBorderColor = CardBorder,
                            focusedContainerColor = BackgroundLight,
                            unfocusedContainerColor = BackgroundLight
                        ),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("search_input_field")
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Surface(
                        color = if (selectedCategory != null || selectedCity != null || filterOnlySpaces) BluePrimary else BlueUltraLight,
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            1.dp,
                            if (selectedCategory != null || selectedCity != null || filterOnlySpaces) BluePrimary else BlueSubtle
                        ),
                        shadowElevation = if (selectedCategory != null || selectedCity != null || filterOnlySpaces) 2.dp else 0.dp,
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { showFilterSheet = true }
                            .testTag("open_filters_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filters",
                                tint = if (selectedCategory != null || selectedCity != null || filterOnlySpaces) Color.White else BluePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category Chips Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    categories.forEach { cat ->
                        val isSelected = (cat == "All" && selectedCategory == null) || (selectedCategory == cat)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.setCategory(if (cat == "All") null else cat)
                            },
                            label = {
                                Text(
                                    text = cat,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BluePrimary,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = TextSecondary
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }
        }

        // Main List Content
        LazyColumn(
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Strict 2-Application Limit Banner
            item {
                ActiveApplicationLimitBanner(activeCount = activeCount, maxLimit = 2)
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Recommended Opportunities",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    Text(
                        text = "${events.size} available",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BluePrimary
                    )
                }
            }

            if (events.isEmpty()) {
                item {
                    EmptyStateView(
                        title = "No Opportunities Found",
                        subtitle = "Try adjusting your search keywords or clear filters to see more volunteering events.",
                        buttonText = "Clear All Filters",
                        onButtonClick = { viewModel.clearFilters() }
                    )
                }
            } else {
                items(events, key = { it.eventId }) { event ->
                    val hasApplied = applications.any {
                        it.eventId == event.eventId && it.volunteerId == currentUser.userId && it.status != com.example.models.ApplicationStatus.CANCELLED
                    }
                    EventCard(
                        event = event,
                        hasApplied = hasApplied,
                        onClick = { onEventClick(event) },
                        onApplyClick = { selectedEventForApply = event }
                    )
                }
            }
        }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Filter Opportunities",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark
                    )
                    IconButton(onClick = { showFilterSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Category",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BlueNavyDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    categories.forEach { cat ->
                        val isSel = (cat == "All" && selectedCategory == null) || (selectedCategory == cat)
                        FilterChip(
                            selected = isSel,
                            onClick = { viewModel.setCategory(if (cat == "All") null else cat) },
                            label = { Text(cat, fontSize = 12.sp) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Availability",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BlueNavyDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.setOnlyWithSpaces(!filterOnlySpaces) }
                ) {
                    Checkbox(
                        checked = filterOnlySpaces,
                        onCheckedChange = { viewModel.setOnlyWithSpaces(it) },
                        colors = CheckboxDefaults.colors(checkedColor = BluePrimary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Show events with open volunteer spaces only",
                        fontSize = 13.sp,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            viewModel.clearFilters()
                            showFilterSheet = false
                        },
                        border = BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = TextPrimary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Clear Filters", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { showFilterSheet = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Apply Filters", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }

    // Apply Confirmation Dialog
    selectedEventForApply?.let { event ->
        ConfirmApplyDialog(
            event = event,
            activeCount = activeCount,
            maxLimit = 2,
            onDismiss = { selectedEventForApply = null },
            onConfirm = { notes ->
                viewModel.applyForEvent(event, notes) {
                    selectedEventForApply = null
                }
            }
        )
    }
}
