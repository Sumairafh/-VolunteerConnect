package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.VolunteerConnectRepository
import com.example.models.ApplicationStatus
import com.example.models.VolunteerEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Volunteer Connect", appName)
    }

    @Test
    fun `test strict active applications limit of 2`() {
        val repo = VolunteerConnectRepository.getInstance()
        val currentVolunteerId = repo.currentUser.value.userId

        // Initially seeded with 1 accepted application
        val initialCount = repo.getActiveApplicationsCount(currentVolunteerId)
        assertEquals(1, initialCount)

        // Find available event 2
        val event2 = repo.events.value.first { it.eventId == "ev_2" }
        val result1 = repo.applyForEvent(event2, "Applying for food distribution")
        assertTrue(result1 is VolunteerConnectRepository.ApplyResult.Success)

        // Now active count is 2 (Maximum limit reached!)
        val countAfterSecond = repo.getActiveApplicationsCount(currentVolunteerId)
        assertEquals(2, countAfterSecond)

        // Try applying for a 3rd event
        val event3 = repo.events.value.first { it.eventId == "ev_3" }
        val result2 = repo.applyForEvent(event3, "Applying for 3rd event")
        assertTrue("Expected LimitReached error", result2 is VolunteerConnectRepository.ApplyResult.LimitReached)

        // Cancel one application to free up a slot
        val secondApp = (result1 as VolunteerConnectRepository.ApplyResult.Success).application
        val cancelSuccess = repo.cancelApplication(secondApp.applicationId)
        assertTrue(cancelSuccess)

        // Active count drops back to 1
        val countAfterCancel = repo.getActiveApplicationsCount(currentVolunteerId)
        assertEquals(1, countAfterCancel)

        // Now applying for 3rd event should succeed!
        val result3 = repo.applyForEvent(event3, "Retrying 3rd event after slot freed")
        assertTrue(result3 is VolunteerConnectRepository.ApplyResult.Success)
    }
}
