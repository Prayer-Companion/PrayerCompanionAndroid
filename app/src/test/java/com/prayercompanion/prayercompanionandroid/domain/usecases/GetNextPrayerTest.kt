package com.prayercompanion.prayercompanionandroid.domain.usecases

import android.location.Location
import com.google.common.truth.Truth
import com.prayercompanion.prayercompanionandroid.domain.Consts
import com.prayercompanion.prayercompanionandroid.domain.PrayersFakeRepository
import com.prayercompanion.prayercompanionandroid.domain.models.Prayer
import com.prayercompanion.prayercompanionandroid.domain.repositories.PrayersRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetNextPrayerTest {

    private lateinit var usecase: GetNextPrayer
    private val prayersRepository: PrayersRepository = PrayersFakeRepository()
    private val clock: Clock = mockk()

    @Before
    fun setup() {
        usecase = GetNextPrayer(
            prayersRepository = prayersRepository,
            clock
        )
    }

    @Test
    fun `should return the correct current prayer FAJR`() = runTest {
        val time = LocalTime.of(2, 0, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.FAJR)
        }
    }

    @Test
    fun `should return the correct current prayer DUHA`() = runTest {
        val time = LocalTime.of(5, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.DUHA)
        }

    }

    @Test
    fun `should return the correct current prayer DHUHR`() = runTest {
        val time = LocalTime.of(7, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.DHUHR)
        }

    }

    @Test
    fun `should return the correct current prayer ASR`() = runTest {
        val time = LocalTime.of(12, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.ASR)
        }
    }

    @Test
    fun `should return the correct current prayer MAGHRIB`() = runTest {
        val time = LocalTime.of(15, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.MAGHRIB)
        }

    }

    @Test
    fun `should return the correct current prayer ISHA`() = runTest {
        val time = LocalTime.of(18, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.ISHA)
        }

    }

    @Test
    fun `should return the correct current prayer Next FAJR`() = runTest {
        val time = LocalTime.of(20, 30, 0)
        stubClock(LocalDateTime.of(Consts.TODAY_DATE, time))
        val prayer = usecase.call(LOCATION)

        Truth.assertThat(prayer.isSuccess).isTrue()

        prayer.onSuccess {
            Truth.assertThat(it.prayer).isEqualTo(Prayer.FAJR)
        }
    }

    private fun stubClock(dateTime: LocalDateTime) {
        val newClock = Clock.fixed(
            dateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault()
        )
        every { clock.instant() } returns newClock.instant()
        every { clock.zone } returns newClock.zone
    }

    companion object {
        private val LOCATION = Location("").apply {
            latitude = 31.963158
            longitude = 35.930359
        }
    }
}