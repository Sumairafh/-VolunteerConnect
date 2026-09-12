package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.models.CityWeather
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
fun WeatherCheckerScreen(
    viewModel: VolunteerConnectViewModel,
    onBack: () -> Unit,
    onNavigateExplore: (String) -> Unit,
    onNavigateMapRoute: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weather by viewModel.currentWeather.collectAsStateWithLifecycle()
    val currentCity by viewModel.weatherCity.collectAsStateWithLifecycle()

    val availableCities = listOf("Karachi", "Lahore", "Islamabad", "Rawalpindi", "Peshawar", "Quetta", "Multan")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Weather Checker",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueNavyDark
                        )
                        Text(
                            text = "Event conditions & outdoor safety advisory",
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
                    IconButton(onClick = onNavigateMapRoute) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "Map & Route",
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
            contentPadding = PaddingValues(bottom = 28.dp)
        ) {
            // City Selector Strip
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Select Event Location City:",
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
                        availableCities.forEach { city ->
                            val isSelected = currentCity.equals(city, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) BluePrimary else BlueUltraLight,
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, BlueSubtle),
                                modifier = Modifier
                                    .clickable { viewModel.setWeatherCity(city) }
                                    .testTag("weather_city_$city")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else BluePrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = city,
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

            // Hero Weather Card with dynamic gradient
            item {
                val heroGradient = when {
                    weather.condition.contains("Rain", ignoreCase = true) -> Brush.verticalGradient(
                        listOf(Color(0xFF3B82F6), Color(0xFF1E3A8A))
                    )
                    weather.condition.contains("Breeze", ignoreCase = true) || weather.condition.contains("Cloud", ignoreCase = true) -> Brush.verticalGradient(
                        listOf(Color(0xFF0284C7), Color(0xFF075985))
                    )
                    else -> Brush.verticalGradient(
                        listOf(Color(0xFF1E40AF), Color(0xFF172554))
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(heroGradient)
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.85f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = weather.cityName,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    }
                                    Text(
                                        text = "Today's Live Volunteer Outlook",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f)
                                    )
                                }

                                // Condition Badge
                                Surface(
                                    color = Color.White.copy(alpha = 0.20f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.WbSunny,
                                            contentDescription = null,
                                            tint = Color(0xFFFDE047),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = weather.condition,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(
                                        text = "${weather.temperatureC}",
                                        fontSize = 56.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "°C",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White.copy(alpha = 0.85f),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "Feels like ${weather.feelsLikeC}°C",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                    Text(
                                        text = "High ${weather.highC}° • Low ${weather.lowC}°",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Outdoor Volunteer Advisory Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Volunteer Safety & Suitability: Optimal",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = weather.advisory,
                                fontSize = 12.sp,
                                color = Color(0xFF15803D),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // Atmospheric Details Grid
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Field Conditions & Health Metrics:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Humidity
                        WeatherMetricCard(
                            modifier = Modifier.weight(1f),
                            label = "Humidity",
                            value = "${weather.humidityPercent}%",
                            subtitle = "Comfortable",
                            icon = Icons.Default.WaterDrop,
                            iconTint = Color(0xFF0284C7)
                        )
                        // Wind Speed
                        WeatherMetricCard(
                            modifier = Modifier.weight(1f),
                            label = "Wind Speed",
                            value = "${weather.windSpeedKmh} km/h",
                            subtitle = "Moderate breeze",
                            icon = Icons.Default.Air,
                            iconTint = Color(0xFF0D9488)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // UV Index
                        WeatherMetricCard(
                            modifier = Modifier.weight(1f),
                            label = "UV Index",
                            value = "${weather.uvIndex} / 10",
                            subtitle = "Sunscreen advised",
                            icon = Icons.Default.WbSunny,
                            iconTint = Color(0xFFF59E0B)
                        )
                        // Air Quality (AQI)
                        WeatherMetricCard(
                            modifier = Modifier.weight(1f),
                            label = "Air Quality (AQI)",
                            value = "${weather.aqi}",
                            subtitle = weather.aqiText,
                            icon = Icons.Default.DeviceThermostat,
                            iconTint = if (weather.aqi < 100) Color(0xFF16A34A) else Color(0xFFF97316)
                        )
                    }
                }
            }

            // Hourly Forecast
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Hourly Event Schedule Outlook:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        weather.hourlyForecast.forEach { hourly ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                                modifier = Modifier.width(88.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = hourly.time,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Icon(
                                        imageVector = if (hourly.condition.contains("Cloud", ignoreCase = true)) Icons.Default.Cloud else Icons.Default.WbSunny,
                                        contentDescription = null,
                                        tint = if (hourly.condition.contains("Cloud", ignoreCase = true)) Color(0xFF64748B) else Color(0xFFF59E0B),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "${hourly.tempC}°C",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BlueNavyDark
                                    )
                                    Text(
                                        text = hourly.condition.take(9),
                                        fontSize = 10.sp,
                                        color = TextTertiary,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5-Day Event Planning Forecast
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "5-Day Drive Planning Forecast:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueNavyDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            weather.dailyForecast.forEachIndexed { idx, daily ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = daily.day,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BlueNavyDark,
                                        modifier = Modifier.width(50.dp)
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (daily.condition.contains("Cloud", ignoreCase = true)) Icons.Default.Cloud else Icons.Default.WbSunny,
                                            contentDescription = null,
                                            tint = if (daily.condition.contains("Cloud", ignoreCase = true)) Color(0xFF64748B) else Color(0xFFF59E0B),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = daily.condition,
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }

                                    Text(
                                        text = "${daily.highC}° / ${daily.lowC}°",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlueNavyDark
                                    )
                                }
                                if (idx < weather.dailyForecast.size - 1) {
                                    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(CardBorder))
                                }
                            }
                        }
                    }
                }
            }

            // Quick Exploration Action
            item {
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Ready to serve in ${weather.cityName}?",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueNavyDark
                            )
                            Text(
                                text = "Filter events and view active volunteer drives here",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Button(
                            onClick = { onNavigateExplore(weather.cityName) },
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Find Events", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherMetricCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = BlueNavyDark
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextTertiary
            )
        }
    }
}
