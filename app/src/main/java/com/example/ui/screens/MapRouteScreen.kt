package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.TurnLeft
import androidx.compose.material.icons.filled.TurnRight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.TransportMode
import com.example.models.VolunteerEvent
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
fun MapRouteScreen(
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    onNavigateWeather: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val events by viewModel.events.collectAsStateWithLifecycle()
    val selectedEventState by viewModel.selectedRouteEvent.collectAsStateWithLifecycle()
    val transportMode by viewModel.selectedTransportMode.collectAsStateWithLifecycle()

    val currentEvent = selectedEventState ?: events.firstOrNull()

    var zoomLevel by remember { mutableFloatStateOf(1.0f) }
    var showLiveTraffic by remember { mutableStateOf(true) }
    var showStepsList by remember { mutableStateOf(true) }

    val calculatedEta = if (currentEvent != null) {
        (currentEvent.etaMinutes * transportMode.speedFactor).toInt().coerceAtLeast(1)
    } else 15

    val distanceKm = currentEvent?.distanceKm ?: 4.2

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Map & Route Checker",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Text(
                            text = "In-app venue navigation & travel route",
                            fontSize = 11.sp,
                            color = TextSecondary
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
                actions = {
                    IconButton(onClick = {
                        if (currentEvent != null) {
                            onNavigateWeather(currentEvent.city)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Check Weather",
                            tint = BluePrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundLight
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Event Selector Carousel
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Select Event Venue to Preview Route:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        events.forEach { ev ->
                            val isSelected = currentEvent?.eventId == ev.eventId
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) BluePrimary else BlueUltraLight,
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, BlueSubtle),
                                modifier = Modifier
                                    .clickable { viewModel.selectRouteEvent(ev) }
                                    .testTag("route_event_${ev.eventId}")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else BluePrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = ev.title.take(22) + if (ev.title.length > 22) "..." else "",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else BlueNavyDark
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Transport Mode Selector
            item {
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TransportMode.values().forEach { mode ->
                            val isSelected = transportMode == mode
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) BluePrimary else Color.Transparent,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.setTransportMode(mode) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val icon = when (mode) {
                                        TransportMode.DRIVE -> Icons.Default.DirectionsCar
                                        TransportMode.TRANSIT -> Icons.Default.DirectionsTransit
                                        TransportMode.WALK -> Icons.Default.DirectionsWalk
                                    }
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = mode.label,
                                        tint = if (isSelected) Color.White else TextSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = mode.label,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Interactive Map Canvas
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE8ECEF))
                        .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                ) {
                    // Custom Canvas Map Renderer
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Draw background road grid lines
                        val roadColor = Color(0xFFD4DBE2)
                        val roadMajorColor = Color(0xFFC0CCD9)

                        // Coastline / Water feature (e.g. Clifton beach water zone)
                        val waterPath = Path().apply {
                            moveTo(0f, h * 0.82f)
                            cubicTo(w * 0.3f, h * 0.78f, w * 0.7f, h * 0.88f, w, h * 0.80f)
                            lineTo(w, h)
                            lineTo(0f, h)
                            close()
                        }
                        drawPath(waterPath, color = Color(0xFFBAE6FD))

                        // Secondary roads
                        for (i in 1..5) {
                            drawLine(
                                color = roadColor,
                                start = Offset(0f, h * (i * 0.16f)),
                                end = Offset(w, h * (i * 0.16f)),
                                strokeWidth = 5f * zoomLevel
                            )
                        }
                        for (j in 1..4) {
                            drawLine(
                                color = roadColor,
                                start = Offset(w * (j * 0.22f), 0f),
                                end = Offset(w * (j * 0.22f), h),
                                strokeWidth = 5f * zoomLevel
                            )
                        }

                        // Major Express Arteries
                        drawLine(
                            color = roadMajorColor,
                            start = Offset(0f, h * 0.35f),
                            end = Offset(w, h * 0.45f),
                            strokeWidth = 10f * zoomLevel
                        )
                        drawLine(
                            color = roadMajorColor,
                            start = Offset(w * 0.5f, 0f),
                            end = Offset(w * 0.45f, h * 0.8f),
                            strokeWidth = 10f * zoomLevel
                        )

                        // Live Traffic Glow overlay if enabled
                        if (showLiveTraffic) {
                            drawLine(
                                color = Color(0x7722C55E),
                                start = Offset(w * 0.15f, h * 0.25f),
                                end = Offset(w * 0.45f, h * 0.42f),
                                strokeWidth = 6f
                            )
                            drawLine(
                                color = Color(0x77EAB308),
                                start = Offset(w * 0.45f, h * 0.42f),
                                end = Offset(w * 0.65f, h * 0.50f),
                                strokeWidth = 6f
                            )
                        }

                        // Route Polyline (Origin to Destination)
                        val originX = w * 0.18f
                        val originY = h * 0.28f
                        val destX = w * 0.78f
                        val destY = h * 0.68f

                        val routePath = Path().apply {
                            moveTo(originX, originY)
                            lineTo(w * 0.45f, h * 0.32f)
                            lineTo(w * 0.50f, h * 0.55f)
                            lineTo(w * 0.70f, h * 0.58f)
                            lineTo(destX, destY)
                        }

                        // Outer glow
                        drawPath(
                            path = routePath,
                            color = Color(0x442563EB),
                            style = Stroke(width = 14f, cap = StrokeCap.Round)
                        )
                        // Main route stroke
                        drawPath(
                            path = routePath,
                            color = Color(0xFF2563EB),
                            style = Stroke(width = 6f, cap = StrokeCap.Round)
                        )

                        // Route dashed direction ticks
                        drawPath(
                            path = routePath,
                            color = Color.White,
                            style = Stroke(
                                width = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f),
                                cap = StrokeCap.Round
                            )
                        )

                        // Origin Point (Current Location Pulse)
                        drawCircle(
                            color = Color(0x440284C7),
                            radius = 16f,
                            center = Offset(originX, originY)
                        )
                        drawCircle(
                            color = Color(0xFF0284C7),
                            radius = 8f,
                            center = Offset(originX, originY)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 4f,
                            center = Offset(originX, originY)
                        )

                        // Destination Marker Pin
                        drawCircle(
                            color = Color(0x33EF4444),
                            radius = 18f,
                            center = Offset(destX, destY)
                        )
                        drawCircle(
                            color = Color(0xFFEF4444),
                            radius = 9f,
                            center = Offset(destX, destY)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 4f,
                            center = Offset(destX, destY)
                        )
                    }

                    // Map Overlay Badges (Traffic, Zoom Controls, Origin/Dest labels)
                    Surface(
                        color = Color.White.copy(alpha = 0.92f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (showLiveTraffic) Color(0xFF22C55E) else Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (showLiveTraffic) "Live Route Active" else "Traffic Off",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueNavyDark
                            )
                        }
                    }

                    // Zoom and Recenter Controls
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .size(34.dp)
                                .clickable { zoomLevel = (zoomLevel + 0.2f).coerceAtMost(2.0f) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Add, contentDescription = "Zoom In", tint = BlueNavyDark, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .size(34.dp)
                                .clickable { zoomLevel = (zoomLevel - 0.2f).coerceAtLeast(0.8f) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Remove, contentDescription = "Zoom Out", tint = BlueNavyDark, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = BluePrimary,
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .size(34.dp)
                                .clickable {
                                    zoomLevel = 1.0f
                                    showLiveTraffic = !showLiveTraffic
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.MyLocation, contentDescription = "Recenter", tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            // Route Overview Metrics Card
            item {
                if (currentEvent != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = currentEvent.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BlueNavyDark
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = BluePrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${currentEvent.locationName}, ${currentEvent.city}",
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }

                                Surface(
                                    color = BlueUltraLight,
                                    shape = RoundedCornerShape(10.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, BlueSubtle)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "$calculatedEta min",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            color = BluePrimary
                                        )
                                        Text(
                                            text = "$distanceKm km",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Traffic & Timing Advisory
                            Surface(
                                color = BackgroundLight,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Traffic,
                                        contentDescription = null,
                                        tint = Color(0xFF16A34A),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Smooth flow: Fast route via main expressway. Arrive 15m early for safety briefing.",
                                        fontSize = 11.sp,
                                        color = BlueNavyDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Action Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        // Launch navigation intent
                                        val gmmIntentUri = Uri.parse("geo:${currentEvent.latitude},${currentEvent.longitude}?q=${Uri.encode(currentEvent.address + ", " + currentEvent.city)}")
                                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                        mapIntent.setPackage("com.google.android.apps.maps")
                                        if (mapIntent.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(mapIntent)
                                        } else {
                                            // Fallback browser map
                                            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=${currentEvent.latitude},${currentEvent.longitude}"))
                                            context.startActivity(webIntent)
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Start Navigation", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { showStepsList = !showStepsList },
                                    modifier = Modifier.height(44.dp),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(if (showStepsList) "Hide Steps" else "Turn-by-Turn", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Turn-by-Turn Directions List
            if (showStepsList && currentEvent != null && currentEvent.routeSteps.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Turn-by-Turn Driving Steps (${currentEvent.routeSteps.size} legs):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                itemsIndexed(currentEvent.routeSteps) { index, step ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (step.directionType == "destination") Color(0xFFDCFCE7) else BlueUltraLight,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    val icon = when (step.directionType) {
                                        "turn_left" -> Icons.Default.TurnLeft
                                        "turn_right" -> Icons.Default.TurnRight
                                        "destination" -> Icons.Default.CheckCircle
                                        else -> Icons.Default.NearMe
                                    }
                                    val tint = if (step.directionType == "destination") Color(0xFF16A34A) else BluePrimary
                                    Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = step.instruction,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = BlueNavyDark
                                )
                                Text(
                                    text = "Continue for ${step.distanceText}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Text(
                                text = "${index + 1}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextTertiary
                            )
                        }
                    }
                }
            }
        }
    }
}
