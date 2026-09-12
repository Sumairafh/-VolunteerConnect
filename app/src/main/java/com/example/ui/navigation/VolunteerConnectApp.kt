package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.models.UserRole
import com.example.models.VolunteerEvent
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.EventDetailsScreen
import com.example.ui.screens.MapRouteScreen
import com.example.ui.screens.MyApplicationsScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OrgApplicantsScreen
import com.example.ui.screens.OrgDashboardScreen
import com.example.ui.screens.PostEventScreen
import com.example.ui.screens.VolunteerHomeScreen
import com.example.ui.screens.VolunteerProfileScreen
import com.example.ui.screens.WeatherCheckerScreen
import com.example.ui.theme.BlueNavyDark
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSubtle
import com.example.ui.theme.BlueUltraLight
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.VolunteerConnectViewModel

enum class Screen {
    EXPLORE,
    MAP_ROUTE,
    WEATHER,
    MY_APPLICATIONS,
    CHAT,
    PROFILE,
    EVENT_DETAILS,
    NOTIFICATIONS,
    ORG_DASHBOARD,
    POST_EVENT,
    ORG_APPLICANTS,
    AUTH
}

@Composable
fun VolunteerConnectApp(
    viewModel: VolunteerConnectViewModel = viewModel()
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val activeApplicationsCount by viewModel.activeApplicationsCount.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    var currentScreen by remember { mutableStateOf(Screen.EXPLORE) }
    var selectedEvent by remember { mutableStateOf<VolunteerEvent?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    val unreadNotifs = notifications.count { !it.isRead }

    // Listen to UI events
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Automatically navigate to Org Dashboard when role switches to ORGANIZATION
    LaunchedEffect(currentUser.role) {
        if (currentUser.role == UserRole.ORGANIZATION && currentScreen == Screen.EXPLORE) {
            currentScreen = Screen.ORG_DASHBOARD
        } else if (currentUser.role == UserRole.VOLUNTEER && currentScreen == Screen.ORG_DASHBOARD) {
            currentScreen = Screen.EXPLORE
        }
    }

    val isVolunteer = currentUser.role == UserRole.VOLUNTEER
    val showBottomBar = currentScreen in listOf(
        Screen.EXPLORE,
        Screen.MAP_ROUTE,
        Screen.WEATHER,
        Screen.MY_APPLICATIONS,
        Screen.CHAT,
        Screen.PROFILE,
        Screen.ORG_DASHBOARD
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                Surface(
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isVolunteer) {
                            ScrollableNavItem(
                                selected = currentScreen == Screen.EXPLORE,
                                onClick = { currentScreen = Screen.EXPLORE },
                                icon = Icons.Default.Explore,
                                label = "Explore",
                                testTag = "nav_explore"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.MAP_ROUTE,
                                onClick = { currentScreen = Screen.MAP_ROUTE },
                                icon = Icons.Default.Directions,
                                label = "Map & Route",
                                testTag = "nav_map_route"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.WEATHER,
                                onClick = { currentScreen = Screen.WEATHER },
                                icon = Icons.Default.WbSunny,
                                label = "Weather",
                                testTag = "nav_weather"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.MY_APPLICATIONS,
                                onClick = { currentScreen = Screen.MY_APPLICATIONS },
                                icon = Icons.Default.Assignment,
                                label = "Applications",
                                badgeText = if (activeApplicationsCount > 0) "$activeApplicationsCount/2" else null,
                                testTag = "nav_applications"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.CHAT,
                                onClick = { currentScreen = Screen.CHAT },
                                icon = Icons.Default.Chat,
                                label = "Messages",
                                testTag = "nav_messages"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.PROFILE,
                                onClick = { currentScreen = Screen.PROFILE },
                                icon = Icons.Default.Person,
                                label = "Profile",
                                testTag = "nav_profile"
                            )
                        } else {
                            // Organization Navigation Bar
                            ScrollableNavItem(
                                selected = currentScreen == Screen.ORG_DASHBOARD,
                                onClick = { currentScreen = Screen.ORG_DASHBOARD },
                                icon = Icons.Default.CorporateFare,
                                label = "Dashboard",
                                testTag = "nav_org_dashboard"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.MAP_ROUTE,
                                onClick = { currentScreen = Screen.MAP_ROUTE },
                                icon = Icons.Default.Directions,
                                label = "Map & Route",
                                testTag = "nav_map_route"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.WEATHER,
                                onClick = { currentScreen = Screen.WEATHER },
                                icon = Icons.Default.WbSunny,
                                label = "Weather",
                                testTag = "nav_weather"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.CHAT,
                                onClick = { currentScreen = Screen.CHAT },
                                icon = Icons.Default.Chat,
                                label = "Messages",
                                testTag = "nav_messages"
                            )

                            ScrollableNavItem(
                                selected = currentScreen == Screen.NOTIFICATIONS,
                                onClick = { currentScreen = Screen.NOTIFICATIONS },
                                icon = Icons.Default.Notifications,
                                label = "Updates",
                                badgeText = if (unreadNotifs > 0) unreadNotifs.toString() else null,
                                testTag = "nav_updates"
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when (currentScreen) {
            Screen.EXPLORE -> {
                VolunteerHomeScreen(
                    viewModel = viewModel,
                    onEventClick = { event ->
                        selectedEvent = event
                        currentScreen = Screen.EVENT_DETAILS
                    },
                    onNavigateNotifications = { currentScreen = Screen.NOTIFICATIONS },
                    onNavigateProfile = { currentScreen = Screen.PROFILE },
                    onNavigateMapRoute = { event ->
                        event?.let { viewModel.selectRouteEvent(it) }
                        currentScreen = Screen.MAP_ROUTE
                    },
                    onNavigateWeather = { city ->
                        viewModel.setWeatherCity(city)
                        currentScreen = Screen.WEATHER
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.MAP_ROUTE -> {
                MapRouteScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.EXPLORE },
                    onNavigateWeather = { city ->
                        viewModel.setWeatherCity(city)
                        currentScreen = Screen.WEATHER
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.WEATHER -> {
                WeatherCheckerScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.EXPLORE },
                    onNavigateExplore = { city ->
                        viewModel.setCity(city)
                        currentScreen = Screen.EXPLORE
                    },
                    onNavigateMapRoute = {
                        currentScreen = Screen.MAP_ROUTE
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.EVENT_DETAILS -> {
                selectedEvent?.let { event ->
                    EventDetailsScreen(
                        event = event,
                        viewModel = viewModel,
                        onBack = { currentScreen = Screen.EXPLORE },
                        onNavigateChat = { currentScreen = Screen.CHAT },
                        onNavigateMapRoute = { ev ->
                            viewModel.selectRouteEvent(ev)
                            currentScreen = Screen.MAP_ROUTE
                        },
                        onNavigateWeather = { city ->
                            viewModel.setWeatherCity(city)
                            currentScreen = Screen.WEATHER
                        }
                    )
                } ?: run {
                    currentScreen = Screen.EXPLORE
                }
            }

            Screen.MY_APPLICATIONS -> {
                MyApplicationsScreen(
                    viewModel = viewModel,
                    onNavigateExplore = { currentScreen = Screen.EXPLORE },
                    onNavigateChat = { currentScreen = Screen.CHAT },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.CHAT -> {
                ChatScreen(
                    viewModel = viewModel,
                    onBack = {
                        currentScreen = if (isVolunteer) Screen.EXPLORE else Screen.ORG_DASHBOARD
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.PROFILE -> {
                VolunteerProfileScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.EXPLORE },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.NOTIFICATIONS -> {
                NotificationsScreen(
                    viewModel = viewModel,
                    onBack = {
                        currentScreen = if (isVolunteer) Screen.EXPLORE else Screen.ORG_DASHBOARD
                    }
                )
            }

            Screen.ORG_DASHBOARD -> {
                OrgDashboardScreen(
                    viewModel = viewModel,
                    onNavigatePostEvent = { currentScreen = Screen.POST_EVENT },
                    onViewApplicants = { event ->
                        selectedEvent = event
                        currentScreen = Screen.ORG_APPLICANTS
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            Screen.POST_EVENT -> {
                PostEventScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.ORG_DASHBOARD },
                    onEventCreated = { currentScreen = Screen.ORG_DASHBOARD }
                )
            }

            Screen.ORG_APPLICANTS -> {
                selectedEvent?.let { event ->
                    OrgApplicantsScreen(
                        event = event,
                        viewModel = viewModel,
                        onBack = { currentScreen = Screen.ORG_DASHBOARD },
                        onNavigateChat = { currentScreen = Screen.CHAT }
                    )
                } ?: run {
                    currentScreen = Screen.ORG_DASHBOARD
                }
            }

            Screen.AUTH -> {
                AuthScreen(
                    viewModel = viewModel,
                    onAuthSuccess = {
                        currentScreen = if (currentUser.role == UserRole.VOLUNTEER) Screen.EXPLORE else Screen.ORG_DASHBOARD
                    }
                )
            }
        }
    }
}

@Composable
fun ScrollableNavItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    badgeText: String? = null,
    testTag: String = ""
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selected) BlueUltraLight else Color.Transparent,
        border = if (selected) BorderStroke(1.dp, BlueSubtle) else null,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (selected) BluePrimary else TextSecondary,
                    modifier = Modifier.size(22.dp)
                )
                if (badgeText != null) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFEF4444),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(start = 14.dp, top = 0.dp)
                    ) {
                        Text(
                            text = badgeText,
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) BluePrimary else TextSecondary,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}
