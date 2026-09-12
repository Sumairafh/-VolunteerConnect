package com.example.models

enum class UserRole {
    VOLUNTEER,
    ORGANIZATION,
    ADMIN
}

enum class VerificationStatus {
    PENDING,
    VERIFIED,
    REJECTED
}

enum class EventStatus {
    ACTIVE,
    FULL,
    COMPLETED,
    CANCELLED
}

enum class ApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED,
    COMPLETED
}

enum class NotificationType {
    APPLICATION_SUBMITTED,
    APPLICATION_ACCEPTED,
    APPLICATION_REJECTED,
    EVENT_UPDATE,
    NEW_MESSAGE,
    LIMIT_WARNING
}

data class User(
    val userId: String,
    val role: UserRole,
    val fullName: String,
    val phone: String,
    val email: String,
    val city: String,
    val cnicMasked: String = "42101-*******-3", // Protected & masked - never raw
    val profileImage: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class VolunteerProfile(
    val userId: String,
    val bio: String,
    val skills: List<String>,
    val interests: List<String>,
    val experience: String,
    val availability: String,
    val completedEventsCount: Int = 0,
    val hoursVolunteered: Int = 0
)

data class Organization(
    val organizationId: String,
    val name: String,
    val description: String,
    val logoUrl: String = "",
    val location: String,
    val contactPhone: String,
    val contactEmail: String,
    val website: String = "",
    val verificationStatus: VerificationStatus = VerificationStatus.VERIFIED,
    val totalEventsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

data class VolunteerEvent(
    val eventId: String,
    val organizationId: String,
    val organizationName: String,
    val organizationLogo: String = "",
    val isOrgVerified: Boolean = true,
    val title: String,
    val description: String,
    val category: String,
    val dateText: String,
    val day: String,
    val startTime: String,
    val endTime: String,
    val deadline: String,
    val locationName: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val volunteersAppliedCount: Int,
    val volunteersAcceptedCount: Int,
    val minAge: Int = 16,
    val maxAge: Int = 65,
    val requiredSkills: List<String> = emptyList(),
    val whatToBring: String = "Comfortable clothes, water bottle, government ID",
    val specialInstructions: String = "Please arrive 15 minutes before the briefing.",
    val status: EventStatus = EventStatus.ACTIVE,
    val latitude: Double = 24.8607,
    val longitude: Double = 67.0011,
    val distanceKm: Double = 4.5,
    val etaMinutes: Int = 16,
    val routeSteps: List<RouteStep> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) {
    val availableSpaces: Int
        get() = (capacity - volunteersAcceptedCount).coerceAtLeast(0)

    val isFull: Boolean
        get() = availableSpaces <= 0 || status == EventStatus.FULL
}

data class RouteStep(
    val instruction: String,
    val distanceText: String,
    val directionType: String = "straight" // straight, turn_left, turn_right, destination
)

enum class TransportMode(val label: String, val speedFactor: Double) {
    DRIVE("Driving", 1.0),
    TRANSIT("Transit", 1.8),
    WALK("Walking", 3.5)
}

data class CityWeather(
    val cityName: String,
    val temperatureC: Int,
    val condition: String,
    val conditionIcon: String,
    val highC: Int,
    val lowC: Int,
    val feelsLikeC: Int,
    val humidityPercent: Int,
    val windSpeedKmh: Int,
    val uvIndex: Int,
    val aqi: Int,
    val aqiText: String,
    val advisory: String,
    val hourlyForecast: List<HourlyWeather>,
    val dailyForecast: List<DailyWeather>
)

data class HourlyWeather(
    val time: String,
    val tempC: Int,
    val condition: String,
    val iconType: String = "sunny"
)

data class DailyWeather(
    val day: String,
    val highC: Int,
    val lowC: Int,
    val condition: String,
    val iconType: String = "sunny"
)

data class Application(
    val applicationId: String,
    val eventId: String,
    val eventTitle: String,
    val organizationId: String,
    val organizationName: String,
    val volunteerId: String,
    val volunteerName: String,
    val volunteerAge: Int,
    val volunteerSkills: List<String>,
    val volunteerPhone: String,
    val status: ApplicationStatus = ApplicationStatus.PENDING,
    val appliedAtText: String,
    val eventDate: String,
    val eventTime: String,
    val eventLocation: String,
    val notes: String = ""
) {
    val isActive: Boolean
        get() = status == ApplicationStatus.PENDING || status == ApplicationStatus.ACCEPTED
}

data class ChatMessage(
    val messageId: String,
    val chatId: String,
    val senderId: String,
    val senderName: String,
    val senderRole: UserRole,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val timeText: String,
    val isRead: Boolean = true
)

data class NotificationItem(
    val notificationId: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timeAgo: String,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
