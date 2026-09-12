package com.example.data

import com.example.models.CityWeather
import com.example.models.DailyWeather
import com.example.models.HourlyWeather
import com.example.models.RouteStep
import com.example.models.Application
import com.example.models.ApplicationStatus
import com.example.models.ChatMessage
import com.example.models.EventStatus
import com.example.models.NotificationItem
import com.example.models.NotificationType
import com.example.models.Organization
import com.example.models.User
import com.example.models.UserRole
import com.example.models.VerificationStatus
import com.example.models.VolunteerEvent
import com.example.models.VolunteerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class VolunteerConnectRepository private constructor() {

    // Default Demo Volunteer User
    private val defaultVolunteerUser = User(
        userId = "vol_101",
        role = UserRole.VOLUNTEER,
        fullName = "Ahmed Khan",
        phone = "+92 300 8214950",
        email = "ahmed.khan@connect.org",
        city = "Karachi",
        cnicMasked = "42101-*******-3",
        profileImage = ""
    )

    private val defaultVolunteerProfile = VolunteerProfile(
        userId = "vol_101",
        bio = "Dedicated social work student passionate about environmental sustainability, coastal conservation, and community health.",
        skills = listOf("First Aid", "Event Coordination", "Crowd Management", "Public Speaking"),
        interests = listOf("Environment", "Disaster Relief", "Education", "Healthcare"),
        experience = "2 years volunteer experience with Red Crescent and local beach cleanups.",
        availability = "Weekends & Friday afternoons",
        completedEventsCount = 6,
        hoursVolunteered = 38
    )

    // Default Demo Organization User
    private val defaultOrgUser = User(
        userId = "org_201",
        role = UserRole.ORGANIZATION,
        fullName = "Green Future Foundation",
        phone = "+92 321 4455667",
        email = "contact@greenfuture.org",
        city = "Karachi",
        cnicMasked = "42201-*******-9",
        profileImage = ""
    )

    private val defaultOrganization = Organization(
        organizationId = "org_201",
        name = "Green Future Foundation",
        description = "Registered non-profit empowering youth for environmental sustainability, tree plantation drives, and marine ecosystem cleanups across Pakistan.",
        location = "Clifton, Karachi",
        contactPhone = "+92 321 4455667",
        contactEmail = "contact@greenfuture.org",
        website = "https://greenfuture.foundation",
        verificationStatus = VerificationStatus.VERIFIED,
        totalEventsCount = 14
    )

    // Current State
    private val _currentUser = MutableStateFlow<User>(defaultVolunteerUser)
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _volunteerProfile = MutableStateFlow<VolunteerProfile>(defaultVolunteerProfile)
    val volunteerProfile: StateFlow<VolunteerProfile> = _volunteerProfile.asStateFlow()

    private val _currentOrg = MutableStateFlow<Organization>(defaultOrganization)
    val currentOrg: StateFlow<Organization> = _currentOrg.asStateFlow()

    private val _events = MutableStateFlow<List<VolunteerEvent>>(emptyList())
    val events: StateFlow<List<VolunteerEvent>> = _events.asStateFlow()

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    init {
        seedInitialData()
    }

    private fun seedInitialData() {
        val initialEvents = listOf(
            VolunteerEvent(
                eventId = "ev_1",
                organizationId = "org_201",
                organizationName = "Green Future Foundation",
                isOrgVerified = true,
                title = "Beach Cleanup & Mangrove Planting Drive",
                description = "Join over 40 eco-conscious volunteers for a high-impact coastal cleanup at Clifton Beach followed by sapling planting along the coastal mangrove zone. Gloves, trash bags, safety tools, and drinking water will be provided.",
                category = "Environment",
                dateText = "Saturday, 20 Sep 2026",
                day = "Saturday",
                startTime = "08:00 AM",
                endTime = "12:30 PM",
                deadline = "18 Sep 2026",
                locationName = "Clifton Beach, Marine Promenade",
                address = "Near Do Darya viewpoint, Block 4, Clifton",
                city = "Karachi",
                capacity = 40,
                volunteersAppliedCount = 26,
                volunteersAcceptedCount = 25,
                minAge = 16,
                maxAge = 60,
                requiredSkills = listOf("Waste Sorting", "Teamwork", "Physical Stamina"),
                whatToBring = "Sun hat, sunscreen, reusable water bottle, closed-toe sneakers",
                specialInstructions = "Safety briefing begins promptly at 8:00 AM. Free volunteer certificates and light snacks provided.",
                latitude = 24.8138,
                longitude = 67.0306,
                distanceKm = 4.2,
                etaMinutes = 14,
                routeSteps = listOf(
                    RouteStep("Head south on Khayaban-e-Iqbal toward Marine Promenade", "1.2 km", "straight"),
                    RouteStep("Turn left onto Sea View Road toward Do Darya", "2.0 km", "turn_left"),
                    RouteStep("Take the 2nd exit at roundabout into Coastal Park", "0.8 km", "turn_right"),
                    RouteStep("Arrive at Clifton Beach Eco Volunteer Point", "0.2 km", "destination")
                )
            ),
            VolunteerEvent(
                eventId = "ev_2",
                organizationId = "org_202",
                organizationName = "Hope Meal Initiative",
                isOrgVerified = true,
                title = "Community Kitchen Food Distribution",
                description = "Assisting chefs in packaging 1,000+ warm nutritious meals and distributing lunch packs to low-income families and hospital attendants. Volunteers also help organize queues and inventory.",
                category = "Food & Relief",
                dateText = "Sunday, 21 Sep 2026",
                day = "Sunday",
                startTime = "11:00 AM",
                endTime = "03:30 PM",
                deadline = "19 Sep 2026",
                locationName = "Civic Centre Food Hub",
                address = "Main University Road, Gulshan-e-Iqbal",
                city = "Karachi",
                capacity = 25,
                volunteersAppliedCount = 19,
                volunteersAcceptedCount = 18,
                minAge = 18,
                maxAge = 55,
                requiredSkills = listOf("Food Packaging", "Crowd Control", "Compassion"),
                whatToBring = "Comfortable apron if available, ID card",
                specialInstructions = "Hairnets and sanitizer are supplied at entry. Strict hygiene protocol observed.",
                latitude = 24.9180,
                longitude = 67.0971,
                distanceKm = 7.5,
                etaMinutes = 22,
                routeSteps = listOf(
                    RouteStep("Head northeast on Shahrah-e-Faisal", "3.1 km", "straight"),
                    RouteStep("Take flyover onto University Road toward Civic Centre", "3.2 km", "turn_left"),
                    RouteStep("Turn right into Civic Centre compound gate 2", "0.8 km", "turn_right"),
                    RouteStep("Arrive at Community Kitchen Logistics Hub", "0.4 km", "destination")
                )
            ),
            VolunteerEvent(
                eventId = "ev_3",
                organizationId = "org_203",
                organizationName = "Warm Hearts Pakistan",
                isOrgVerified = true,
                title = "Winter Relief & Blanket Packing Drive",
                description = "Preparing emergency winter care kits containing thermal blankets, jackets, socks, and emergency medical kits for vulnerable rural families ahead of cold waves.",
                category = "Community",
                dateText = "Friday, 26 Sep 2026",
                day = "Friday",
                startTime = "02:30 PM",
                endTime = "06:30 PM",
                deadline = "24 Sep 2026",
                locationName = "Expo Centre Hall 3",
                address = "University Road, Block 15",
                city = "Karachi",
                capacity = 50,
                volunteersAppliedCount = 48,
                volunteersAcceptedCount = 42,
                minAge = 16,
                maxAge = 65,
                requiredSkills = listOf("Packaging", "Inventory Check", "Labeling"),
                whatToBring = "Notepad, enthusiasm to serve",
                specialInstructions = "Warehouse environment; wear durable footwear.",
                latitude = 24.8934,
                longitude = 67.0782,
                distanceKm = 5.8,
                etaMinutes = 18,
                routeSteps = listOf(
                    RouteStep("Head east on Stadium Road toward Expo Centre", "2.5 km", "straight"),
                    RouteStep("Turn right at Expo Center Gate 3 main avenue", "2.8 km", "turn_right"),
                    RouteStep("Arrive at Hall 3 Relief Logistics Bay", "0.5 km", "destination")
                )
            ),
            VolunteerEvent(
                eventId = "ev_4",
                organizationId = "org_204",
                organizationName = "EduBridge Youth Alliance",
                isOrgVerified = true,
                title = "Weekend Children Book Reading & Tutoring",
                description = "Mentoring primary grade students at an underprivileged community school. Volunteers conduct interactive English and Urdu storytelling sessions, basic math games, and fun science demonstrations.",
                category = "Education",
                dateText = "Saturday, 27 Sep 2026",
                day = "Saturday",
                startTime = "09:30 AM",
                endTime = "01:00 PM",
                deadline = "25 Sep 2026",
                locationName = "Lyari Community Learning Centre",
                address = "Street 7, Kalakot, Lyari",
                city = "Karachi",
                capacity = 20,
                volunteersAppliedCount = 14,
                volunteersAcceptedCount = 12,
                minAge = 18,
                maxAge = 40,
                requiredSkills = listOf("Storytelling", "Patience", "Tutoring"),
                whatToBring = "Children storybooks if you'd like to donate",
                specialInstructions = "A 30-minute orientation on child safeguarding is provided prior to the session.",
                latitude = 24.8680,
                longitude = 66.9920,
                distanceKm = 3.9,
                etaMinutes = 15,
                routeSteps = listOf(
                    RouteStep("Head west via Napier Road toward Kalakot", "1.8 km", "straight"),
                    RouteStep("Turn left onto Street 7 by the Community Garden", "1.6 km", "turn_left"),
                    RouteStep("Arrive at Lyari Community Learning Centre Entrance", "0.5 km", "destination")
                )
            ),
            VolunteerEvent(
                eventId = "ev_5",
                organizationId = "org_205",
                organizationName = "Paw Rescue Society",
                isOrgVerified = false,
                title = "Animal Shelter Rescue Care & Feeding",
                description = "Helping feed, groom, and socialize 60+ rescued street dogs and cats at our temporary animal recovery shelter. Vet assistants will oversee all interactions.",
                category = "Animal Welfare",
                dateText = "Sunday, 28 Sep 2026",
                day = "Sunday",
                startTime = "09:00 AM",
                endTime = "01:30 PM",
                deadline = "26 Sep 2026",
                locationName = "Malir Rescue Sanctuary",
                address = "Korangi Creek Road, Sector 33",
                city = "Karachi",
                capacity = 15,
                volunteersAppliedCount = 15,
                volunteersAcceptedCount = 15,
                minAge = 18,
                maxAge = 50,
                requiredSkills = listOf("Animal Handling", "Sanitization", "Patience"),
                whatToBring = "Clothes you do not mind getting fur/dirt on",
                specialInstructions = "Volunteers must be comfortable around animals. Rabies vaccination verified.",
                latitude = 24.8510,
                longitude = 67.1420,
                distanceKm = 11.2,
                etaMinutes = 28,
                routeSteps = listOf(
                    RouteStep("Take Korangi Creek Expressway heading southeast", "7.0 km", "straight"),
                    RouteStep("Turn right into Sector 33 access road", "3.5 km", "turn_right"),
                    RouteStep("Arrive at Paw Rescue Sanctuary main gate", "0.7 km", "destination")
                )
            ),
            VolunteerEvent(
                eventId = "ev_6",
                organizationId = "org_201",
                organizationName = "Green Future Foundation",
                isOrgVerified = true,
                title = "Urban Tree Plantation: 500 Neem Trees",
                description = "Combatting urban heat islands by planting 500 indigenous drought-resistant Neem and Peepal saplings in public school grounds and parks.",
                category = "Environment",
                dateText = "Saturday, 4 Oct 2026",
                day = "Saturday",
                startTime = "07:30 AM",
                endTime = "11:30 AM",
                deadline = "2 Oct 2026",
                locationName = "Safari Park Community Grounds",
                address = "Main Gulshan Road",
                city = "Karachi",
                capacity = 35,
                volunteersAppliedCount = 20,
                volunteersAcceptedCount = 18,
                minAge = 16,
                maxAge = 65,
                requiredSkills = listOf("Digging & Planting", "Watering", "Teamwork"),
                whatToBring = "Gardening gloves, water bottle",
                specialInstructions = "Tools and saplings provided by Green Future Foundation.",
                latitude = 24.9220,
                longitude = 67.1120,
                distanceKm = 8.4,
                etaMinutes = 24,
                routeSteps = listOf(
                    RouteStep("Take University Road heading toward Safari Park", "5.0 km", "straight"),
                    RouteStep("Turn left at Safari Park main gate access lane", "2.8 km", "turn_left"),
                    RouteStep("Arrive at Community Tree Planting staging area", "0.6 km", "destination")
                )
            )
        )
        _events.value = initialEvents

        // Seed 1 active application for demo volunteer (Beach Cleanup)
        // Leaving 1 available slot out of 2!
        val initialApplications = listOf(
            Application(
                applicationId = "app_1",
                eventId = "ev_1",
                eventTitle = "Beach Cleanup & Mangrove Planting Drive",
                organizationId = "org_201",
                organizationName = "Green Future Foundation",
                volunteerId = "vol_101",
                volunteerName = "Ahmed Khan",
                volunteerAge = 22,
                volunteerSkills = listOf("First Aid", "Event Coordination"),
                volunteerPhone = "+92 300 8214950",
                status = ApplicationStatus.ACCEPTED,
                appliedAtText = "10 Sep 2026",
                eventDate = "Saturday, 20 Sep 2026",
                eventTime = "08:00 AM – 12:30 PM",
                eventLocation = "Clifton Beach, Marine Promenade",
                notes = "Excited to assist with first aid station and waste sorting!"
            ),
            // Another applicant for Org 201 to review:
            Application(
                applicationId = "app_2",
                eventId = "ev_1",
                eventTitle = "Beach Cleanup & Mangrove Planting Drive",
                organizationId = "org_201",
                organizationName = "Green Future Foundation",
                volunteerId = "vol_102",
                volunteerName = "Fatima Noor",
                volunteerAge = 20,
                volunteerSkills = listOf("Photography", "Social Media", "First Aid"),
                volunteerPhone = "+92 312 3456789",
                status = ApplicationStatus.PENDING,
                appliedAtText = "09 Sep 2026",
                eventDate = "Saturday, 20 Sep 2026",
                eventTime = "08:00 AM – 12:30 PM",
                eventLocation = "Clifton Beach, Marine Promenade",
                notes = "Can document the event and assist participants."
            ),
            Application(
                applicationId = "app_3",
                eventId = "ev_6",
                eventTitle = "Urban Tree Plantation: 500 Neem Trees",
                organizationId = "org_201",
                organizationName = "Green Future Foundation",
                volunteerId = "vol_103",
                volunteerName = "Bilal Siddiqui",
                volunteerAge = 24,
                volunteerSkills = listOf("Gardening", "Heavy Lifting"),
                volunteerPhone = "+92 333 9988776",
                status = ApplicationStatus.PENDING,
                appliedAtText = "10 Sep 2026",
                eventDate = "Saturday, 4 Oct 2026",
                eventTime = "07:30 AM – 11:30 AM",
                eventLocation = "Safari Park Community Grounds",
                notes = "Active community volunteer ready to dig and plant."
            )
        )
        _applications.value = initialApplications

        // Seed initial chat
        val initialMessages = listOf(
            ChatMessage(
                messageId = "msg_1",
                chatId = "chat_vol101_org201",
                senderId = "org_201",
                senderName = "Green Future Foundation",
                senderRole = UserRole.ORGANIZATION,
                message = "Welcome Ahmed! Your application for the Beach Cleanup is approved. Please bring comfortable shoes.",
                timestamp = System.currentTimeMillis() - 3600000 * 4,
                timeText = "09:15 AM",
                isRead = true
            ),
            ChatMessage(
                messageId = "msg_2",
                chatId = "chat_vol101_org201",
                senderId = "vol_101",
                senderName = "Ahmed Khan",
                senderRole = UserRole.VOLUNTEER,
                message = "Thank you! I will be there by 7:45 AM. Looking forward to helping.",
                timestamp = System.currentTimeMillis() - 3600000 * 3,
                timeText = "09:30 AM",
                isRead = true
            )
        )
        _messages.value = initialMessages

        // Seed notifications
        val initialNotifications = listOf(
            NotificationItem(
                notificationId = "notif_1",
                userId = "vol_101",
                title = "Application Accepted 🎉",
                message = "Green Future Foundation accepted your application for 'Beach Cleanup & Mangrove Planting Drive'.",
                type = NotificationType.APPLICATION_ACCEPTED,
                timeAgo = "3 hours ago",
                isRead = false
            ),
            NotificationItem(
                notificationId = "notif_2",
                userId = "vol_101",
                title = "Active Application Slots: 1 of 2",
                message = "You currently have 1 active application. You may apply for 1 more volunteering opportunity.",
                type = NotificationType.LIMIT_WARNING,
                timeAgo = "Yesterday",
                isRead = true
            ),
            NotificationItem(
                notificationId = "notif_3",
                userId = "org_201",
                title = "New Applicant: Fatima Noor",
                message = "Fatima Noor applied for Beach Cleanup & Mangrove Planting Drive.",
                type = NotificationType.APPLICATION_SUBMITTED,
                timeAgo = "1 day ago",
                isRead = false
            )
        )
        _notifications.value = initialNotifications
    }

    // Role switching for rapid testing
    fun switchUserRole(newRole: UserRole) {
        if (newRole == UserRole.VOLUNTEER) {
            _currentUser.value = defaultVolunteerUser
        } else {
            _currentUser.value = defaultOrgUser
        }
    }

    fun loginAsVolunteer(name: String, email: String, phone: String) {
        val user = User(
            userId = "vol_${System.currentTimeMillis() % 1000}",
            role = UserRole.VOLUNTEER,
            fullName = name.ifBlank { "Ahmed Khan" },
            phone = phone.ifBlank { "+92 300 8214950" },
            email = email.ifBlank { "volunteer@connect.org" },
            city = "Karachi"
        )
        _currentUser.value = user
    }

    fun loginAsOrganization(orgName: String, email: String, phone: String) {
        val user = User(
            userId = "org_${System.currentTimeMillis() % 1000}",
            role = UserRole.ORGANIZATION,
            fullName = orgName.ifBlank { "Green Future Foundation" },
            phone = phone.ifBlank { "+92 321 4455667" },
            email = email.ifBlank { "contact@greenfuture.org" },
            city = "Karachi"
        )
        val org = Organization(
            organizationId = user.userId,
            name = user.fullName,
            description = "Community volunteering & empowerment organization.",
            location = "Karachi, Pakistan",
            contactPhone = user.phone,
            contactEmail = user.email,
            verificationStatus = VerificationStatus.VERIFIED
        )
        _currentUser.value = user
        _currentOrg.value = org
    }

    // ----------------------------------------------------
    // STRICT 2-ACTIVE APPLICATION LIMIT ENFORCEMENT
    // ----------------------------------------------------
    fun getActiveApplicationsCount(volunteerId: String): Int {
        return _applications.value.count { it.volunteerId == volunteerId && it.isActive }
    }

    fun hasAppliedToEvent(eventId: String, volunteerId: String): Boolean {
        return _applications.value.any { it.eventId == eventId && it.volunteerId == volunteerId && it.status != ApplicationStatus.CANCELLED }
    }

    fun getApplicationForEvent(eventId: String, volunteerId: String): Application? {
        return _applications.value.firstOrNull { it.eventId == eventId && it.volunteerId == volunteerId }
    }

    sealed class ApplyResult {
        data class Success(val application: Application) : ApplyResult()
        data class LimitReached(val currentCount: Int, val message: String) : ApplyResult()
        data class AlreadyApplied(val message: String) : ApplyResult()
        data class EventFull(val message: String) : ApplyResult()
    }

    fun applyForEvent(event: VolunteerEvent, notes: String): ApplyResult {
        val user = _currentUser.value
        val volunteerId = user.userId

        // 1. Server-grade check: Event availability
        if (event.isFull) {
            return ApplyResult.EventFull("This volunteering event has reached maximum volunteer capacity.")
        }

        // 2. Duplicate check
        if (hasAppliedToEvent(event.eventId, volunteerId)) {
            return ApplyResult.AlreadyApplied("You have already submitted an application for this opportunity.")
        }

        // 3. STRICT 2-ACTIVE APPLICATION LIMIT CHECK
        val activeCount = getActiveApplicationsCount(volunteerId)
        if (activeCount >= 2) {
            return ApplyResult.LimitReached(
                currentCount = activeCount,
                message = "You can only have 2 active volunteering applications at a time. Please wait for an organization to review your existing applications or withdraw one before applying to new opportunities."
            )
        }

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val newApp = Application(
            applicationId = "app_${UUID.randomUUID().toString().take(8)}",
            eventId = event.eventId,
            eventTitle = event.title,
            organizationId = event.organizationId,
            organizationName = event.organizationName,
            volunteerId = volunteerId,
            volunteerName = user.fullName,
            volunteerAge = 22,
            volunteerSkills = _volunteerProfile.value.skills,
            volunteerPhone = user.phone,
            status = ApplicationStatus.PENDING,
            appliedAtText = dateFormat.format(Date()),
            eventDate = event.dateText,
            eventTime = "${event.startTime} – ${event.endTime}",
            eventLocation = event.locationName,
            notes = notes
        )

        // Update applications list
        _applications.value = listOf(newApp) + _applications.value

        // Increment event applicant count
        _events.value = _events.value.map {
            if (it.eventId == event.eventId) {
                it.copy(volunteersAppliedCount = it.volunteersAppliedCount + 1)
            } else it
        }

        // Add confirmation notification for volunteer
        val newCount = activeCount + 1
        val notif = NotificationItem(
            notificationId = "notif_${UUID.randomUUID().toString().take(6)}",
            userId = volunteerId,
            title = "Application Submitted 📋",
            message = "Your application for '${event.title}' was sent to ${event.organizationName}. Active applications: $newCount/2.",
            type = NotificationType.APPLICATION_SUBMITTED,
            timeAgo = "Just now"
        )
        _notifications.value = listOf(notif) + _notifications.value

        return ApplyResult.Success(newApp)
    }

    fun cancelApplication(applicationId: String): Boolean {
        val app = _applications.value.firstOrNull { it.applicationId == applicationId } ?: return false
        _applications.value = _applications.value.map {
            if (it.applicationId == applicationId) {
                it.copy(status = ApplicationStatus.CANCELLED)
            } else it
        }

        // Decrement event counts if needed
        _events.value = _events.value.map {
            if (it.eventId == app.eventId) {
                val newAccepted = if (app.status == ApplicationStatus.ACCEPTED) (it.volunteersAcceptedCount - 1).coerceAtLeast(0) else it.volunteersAcceptedCount
                it.copy(
                    volunteersAppliedCount = (it.volunteersAppliedCount - 1).coerceAtLeast(0),
                    volunteersAcceptedCount = newAccepted
                )
            } else it
        }

        val remainingActive = getActiveApplicationsCount(_currentUser.value.userId)
        val notif = NotificationItem(
            notificationId = "notif_${UUID.randomUUID().toString().take(6)}",
            userId = _currentUser.value.userId,
            title = "Application Withdrawn",
            message = "You cancelled your application for '${app.eventTitle}'. Active applications: $remainingActive/2.",
            type = NotificationType.LIMIT_WARNING,
            timeAgo = "Just now"
        )
        _notifications.value = listOf(notif) + _notifications.value
        return true
    }

    // Organization actions
    fun acceptApplication(applicationId: String) {
        val app = _applications.value.firstOrNull { it.applicationId == applicationId } ?: return
        _applications.value = _applications.value.map {
            if (it.applicationId == applicationId) {
                it.copy(status = ApplicationStatus.ACCEPTED)
            } else it
        }
        _events.value = _events.value.map {
            if (it.eventId == app.eventId) {
                it.copy(volunteersAcceptedCount = it.volunteersAcceptedCount + 1)
            } else it
        }

        val notif = NotificationItem(
            notificationId = "notif_${UUID.randomUUID().toString().take(6)}",
            userId = app.volunteerId,
            title = "Application Accepted! 🎉",
            message = "Congratulations! ${app.organizationName} accepted your application for '${app.eventTitle}'.",
            type = NotificationType.APPLICATION_ACCEPTED,
            timeAgo = "Just now"
        )
        _notifications.value = listOf(notif) + _notifications.value
    }

    fun rejectApplication(applicationId: String) {
        val app = _applications.value.firstOrNull { it.applicationId == applicationId } ?: return
        _applications.value = _applications.value.map {
            if (it.applicationId == applicationId) {
                it.copy(status = ApplicationStatus.REJECTED)
            } else it
        }
        val notif = NotificationItem(
            notificationId = "notif_${UUID.randomUUID().toString().take(6)}",
            userId = app.volunteerId,
            title = "Application Update",
            message = "${app.organizationName} was unable to accept your application for '${app.eventTitle}'. You now have a free application slot!",
            type = NotificationType.APPLICATION_REJECTED,
            timeAgo = "Just now"
        )
        _notifications.value = listOf(notif) + _notifications.value
    }

    // Post new event
    fun createEvent(
        title: String,
        description: String,
        category: String,
        dateText: String,
        day: String,
        startTime: String,
        endTime: String,
        deadline: String,
        locationName: String,
        address: String,
        city: String,
        capacity: Int,
        minAge: Int,
        skills: List<String>,
        whatToBring: String,
        specialInstructions: String
    ): VolunteerEvent {
        val org = _currentOrg.value
        val newEvent = VolunteerEvent(
            eventId = "ev_${UUID.randomUUID().toString().take(8)}",
            organizationId = org.organizationId,
            organizationName = org.name,
            isOrgVerified = org.verificationStatus == VerificationStatus.VERIFIED,
            title = title,
            description = description,
            category = category,
            dateText = dateText,
            day = day,
            startTime = startTime,
            endTime = endTime,
            deadline = deadline,
            locationName = locationName,
            address = address,
            city = city,
            capacity = capacity,
            volunteersAppliedCount = 0,
            volunteersAcceptedCount = 0,
            minAge = minAge,
            requiredSkills = skills,
            whatToBring = whatToBring,
            specialInstructions = specialInstructions,
            status = EventStatus.ACTIVE
        )
        _events.value = listOf(newEvent) + _events.value
        _currentOrg.value = org.copy(totalEventsCount = org.totalEventsCount + 1)
        return newEvent
    }

    // Chat
    fun sendMessage(text: String, chatId: String = "chat_vol101_org201") {
        if (text.isBlank()) return
        val user = _currentUser.value
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val msg = ChatMessage(
            messageId = "msg_${UUID.randomUUID().toString().take(8)}",
            chatId = chatId,
            senderId = user.userId,
            senderName = user.fullName,
            senderRole = user.role,
            message = text.trim(),
            timestamp = System.currentTimeMillis(),
            timeText = timeFormat.format(Date()),
            isRead = true
        )
        _messages.value = _messages.value + msg
    }

    // Volunteer Profile Update
    fun updateVolunteerProfile(
        bio: String,
        skills: List<String>,
        interests: List<String>,
        availability: String,
        city: String
    ) {
        _volunteerProfile.value = _volunteerProfile.value.copy(
            bio = bio,
            skills = skills,
            interests = interests,
            availability = availability
        )
        _currentUser.value = _currentUser.value.copy(city = city)
    }

    fun markAllNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    // Dynamic Weather Checker
    fun getWeatherForCity(cityName: String): CityWeather {
        val city = cityName.trim()
        return when {
            city.contains("Lahore", ignoreCase = true) -> CityWeather(
                cityName = "Lahore",
                temperatureC = 27,
                condition = "Clear Sky",
                conditionIcon = "sunny",
                highC = 31,
                lowC = 22,
                feelsLikeC = 29,
                humidityPercent = 58,
                windSpeedKmh = 12,
                uvIndex = 6,
                aqi = 142,
                aqiText = "Moderate",
                advisory = "Pleasant morning temperatures. Recommended for outdoor youth coaching and tree plantation drives.",
                hourlyForecast = listOf(
                    HourlyWeather("08:00 AM", 24, "Sunny", "sunny"),
                    HourlyWeather("10:00 AM", 27, "Sunny", "sunny"),
                    HourlyWeather("12:00 PM", 30, "Partly Cloudy", "cloudy"),
                    HourlyWeather("02:00 PM", 31, "Partly Cloudy", "cloudy"),
                    HourlyWeather("04:00 PM", 29, "Clear", "sunny"),
                    HourlyWeather("06:00 PM", 26, "Clear", "sunny")
                ),
                dailyForecast = listOf(
                    DailyWeather("Sat", 31, 22, "Sunny", "sunny"),
                    DailyWeather("Sun", 30, 21, "Sunny", "sunny"),
                    DailyWeather("Mon", 29, 20, "Partly Cloudy", "cloudy"),
                    DailyWeather("Tue", 28, 20, "Clear", "sunny"),
                    DailyWeather("Wed", 30, 21, "Sunny", "sunny")
                )
            )
            city.contains("Islamabad", ignoreCase = true) || city.contains("Rawalpindi", ignoreCase = true) -> CityWeather(
                cityName = "Islamabad",
                temperatureC = 23,
                condition = "Mild & Breezy",
                conditionIcon = "cloudy",
                highC = 26,
                lowC = 17,
                feelsLikeC = 23,
                humidityPercent = 52,
                windSpeedKmh = 14,
                uvIndex = 5,
                aqi = 48,
                aqiText = "Good",
                advisory = "Excellent fresh air quality and mild weather. Prime conditions for environmental drives and park cleanups.",
                hourlyForecast = listOf(
                    HourlyWeather("08:00 AM", 19, "Clear", "sunny"),
                    HourlyWeather("10:00 AM", 22, "Partly Cloudy", "cloudy"),
                    HourlyWeather("12:00 PM", 25, "Partly Cloudy", "cloudy"),
                    HourlyWeather("02:00 PM", 26, "Mild Breeze", "breezy"),
                    HourlyWeather("04:00 PM", 24, "Clear", "sunny"),
                    HourlyWeather("06:00 PM", 21, "Clear", "sunny")
                ),
                dailyForecast = listOf(
                    DailyWeather("Sat", 26, 17, "Clear", "sunny"),
                    DailyWeather("Sun", 25, 16, "Mild Breeze", "breezy"),
                    DailyWeather("Mon", 24, 15, "Partly Cloudy", "cloudy"),
                    DailyWeather("Tue", 25, 16, "Sunny", "sunny"),
                    DailyWeather("Wed", 26, 17, "Sunny", "sunny")
                )
            )
            else -> CityWeather(
                cityName = "Karachi",
                temperatureC = 29,
                condition = "Sunny & Coastal Breeze",
                conditionIcon = "sunny",
                highC = 33,
                lowC = 25,
                feelsLikeC = 32,
                humidityPercent = 64,
                windSpeedKmh = 19,
                uvIndex = 7,
                aqi = 85,
                aqiText = "Moderate",
                advisory = "Comfortable morning conditions with refreshing Arabian Sea breeze. Optimal for coastal cleanups and relief operations. Stay hydrated.",
                hourlyForecast = listOf(
                    HourlyWeather("08:00 AM", 26, "Sunny", "sunny"),
                    HourlyWeather("10:00 AM", 29, "Coastal Breeze", "breezy"),
                    HourlyWeather("12:00 PM", 32, "Sunny", "sunny"),
                    HourlyWeather("02:00 PM", 33, "Warm", "sunny"),
                    HourlyWeather("04:00 PM", 31, "Sea Breeze", "breezy"),
                    HourlyWeather("06:00 PM", 28, "Clear Evening", "sunny")
                ),
                dailyForecast = listOf(
                    DailyWeather("Sat", 33, 25, "Sunny & Breeze", "sunny"),
                    DailyWeather("Sun", 32, 25, "Coastal Breeze", "breezy"),
                    DailyWeather("Mon", 33, 26, "Sunny", "sunny"),
                    DailyWeather("Tue", 32, 25, "Partly Cloudy", "cloudy"),
                    DailyWeather("Wed", 31, 24, "Sunny", "sunny")
                )
            )
        }
    }

    companion object {
        @Volatile
        private var instance: VolunteerConnectRepository? = null

        fun getInstance(): VolunteerConnectRepository {
            return instance ?: synchronized(this) {
                instance ?: VolunteerConnectRepository().also { instance = it }
            }
        }
    }
}
