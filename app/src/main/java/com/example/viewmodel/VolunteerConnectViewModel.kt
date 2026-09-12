package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.CityWeather
import com.example.models.TransportMode
import com.example.data.VolunteerConnectRepository
import com.example.models.Application
import com.example.models.ChatMessage
import com.example.models.NotificationItem
import com.example.models.Organization
import com.example.models.User
import com.example.models.UserRole
import com.example.models.VolunteerEvent
import com.example.models.VolunteerProfile
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VolunteerConnectViewModel(
    private val repository: VolunteerConnectRepository = VolunteerConnectRepository.getInstance()
) : ViewModel() {

    val currentUser: StateFlow<User> = repository.currentUser
    val volunteerProfile: StateFlow<VolunteerProfile> = repository.volunteerProfile
    val currentOrg: StateFlow<Organization> = repository.currentOrg
    val events: StateFlow<List<VolunteerEvent>> = repository.events
    val applications: StateFlow<List<Application>> = repository.applications
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val messages: StateFlow<List<ChatMessage>> = repository.messages

    // Search and Filter State
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<String?>(null)
    val selectedCity = MutableStateFlow<String?>(null)
    val filterOnlyWithSpaces = MutableStateFlow(false)

    // Filtered Events
    val filteredEvents: StateFlow<List<VolunteerEvent>> = combine(
        events,
        searchQuery,
        selectedCategory,
        selectedCity,
        filterOnlyWithSpaces
    ) { allEvents, query, category, city, onlySpaces ->
        allEvents.filter { event ->
            val matchesQuery = query.isBlank() ||
                event.title.contains(query, ignoreCase = true) ||
                event.organizationName.contains(query, ignoreCase = true) ||
                event.category.contains(query, ignoreCase = true) ||
                event.locationName.contains(query, ignoreCase = true) ||
                event.description.contains(query, ignoreCase = true)

            val matchesCategory = category == null || event.category.equals(category, ignoreCase = true)
            val matchesCity = city == null || event.city.equals(city, ignoreCase = true)
            val matchesSpaces = !onlySpaces || event.availableSpaces > 0

            matchesQuery && matchesCategory && matchesCity && matchesSpaces
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active applications count for current volunteer
    val activeApplicationsCount: StateFlow<Int> = combine(applications, currentUser) { apps, user ->
        apps.count { it.volunteerId == user.userId && it.isActive }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Map & Route Navigation State
    val selectedRouteEvent = MutableStateFlow<VolunteerEvent?>(null)
    val selectedTransportMode = MutableStateFlow(TransportMode.DRIVE)

    // In-App Weather State
    val weatherCity = MutableStateFlow("Karachi")
    val currentWeather: StateFlow<CityWeather> = combine(weatherCity, currentUser) { city, user ->
        val queryCity = if (city.isNotBlank()) city else user.city
        repository.getWeatherForCity(queryCity)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getWeatherForCity("Karachi"))

    fun selectRouteEvent(event: VolunteerEvent?) {
        selectedRouteEvent.value = event
    }

    fun setTransportMode(mode: TransportMode) {
        selectedTransportMode.value = mode
    }

    fun setWeatherCity(city: String) {
        weatherCity.value = city
    }

    // One-time UI events (Snackbars / Dialog feedback)
    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent.asSharedFlow()

    fun setSearch(query: String) {
        searchQuery.value = query
    }

    fun setCategory(category: String?) {
        selectedCategory.value = if (selectedCategory.value == category) null else category
    }

    fun setCity(city: String?) {
        selectedCity.value = if (selectedCity.value == city) null else city
    }

    fun setOnlyWithSpaces(only: Boolean) {
        filterOnlyWithSpaces.value = only
    }

    fun clearFilters() {
        searchQuery.value = ""
        selectedCategory.value = null
        selectedCity.value = null
        filterOnlyWithSpaces.value = false
    }

    // Application actions
    fun applyForEvent(event: VolunteerEvent, notes: String, onResult: (VolunteerConnectRepository.ApplyResult) -> Unit) {
        val result = repository.applyForEvent(event, notes)
        viewModelScope.launch {
            when (result) {
                is VolunteerConnectRepository.ApplyResult.Success -> {
                    _uiEvent.emit("Application submitted successfully for ${event.title}!")
                }
                is VolunteerConnectRepository.ApplyResult.LimitReached -> {
                    _uiEvent.emit(result.message)
                }
                is VolunteerConnectRepository.ApplyResult.AlreadyApplied -> {
                    _uiEvent.emit(result.message)
                }
                is VolunteerConnectRepository.ApplyResult.EventFull -> {
                    _uiEvent.emit(result.message)
                }
            }
        }
        onResult(result)
    }

    fun cancelApplication(applicationId: String) {
        val success = repository.cancelApplication(applicationId)
        if (success) {
            viewModelScope.launch {
                _uiEvent.emit("Application successfully withdrawn. Your active slot is now freed.")
            }
        }
    }

    // Organization actions
    fun acceptApplicant(applicationId: String) {
        repository.acceptApplication(applicationId)
        viewModelScope.launch {
            _uiEvent.emit("Applicant accepted! Notification sent to volunteer.")
        }
    }

    fun rejectApplicant(applicationId: String) {
        repository.rejectApplication(applicationId)
        viewModelScope.launch {
            _uiEvent.emit("Applicant status updated to Rejected.")
        }
    }

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
        val newEvent = repository.createEvent(
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
            minAge = minAge,
            skills = skills,
            whatToBring = whatToBring,
            specialInstructions = specialInstructions
        )
        viewModelScope.launch {
            _uiEvent.emit("Event '${newEvent.title}' published successfully!")
        }
        return newEvent
    }

    fun sendMessage(text: String) {
        repository.sendMessage(text)
    }

    fun switchRole(role: UserRole) {
        repository.switchUserRole(role)
        viewModelScope.launch {
            val roleName = if (role == UserRole.VOLUNTEER) "Volunteer" else "Organization"
            _uiEvent.emit("Switched viewport to $roleName account.")
        }
    }

    fun updateVolunteerProfile(
        bio: String,
        skills: List<String>,
        interests: List<String>,
        availability: String,
        city: String
    ) {
        repository.updateVolunteerProfile(bio, skills, interests, availability, city)
        viewModelScope.launch {
            _uiEvent.emit("Profile updated successfully!")
        }
    }

    fun markNotificationsRead() {
        repository.markAllNotificationsRead()
    }

    fun reportItem(type: String, id: String, reason: String) {
        viewModelScope.launch {
            _uiEvent.emit("Thank you. Your report for $type has been submitted to system administrators for review.")
        }
    }
}
