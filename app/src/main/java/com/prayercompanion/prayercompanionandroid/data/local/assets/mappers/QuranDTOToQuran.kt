package com.prayercompanion.prayercompanionandroid.data.local.assets.mappers

import com.prayercompanion.prayercompanionandroid.data.local.assets.dto.QuranDTO
import com.prayercompanion.prayercompanionandroid.domain.models.Quran
import com.prayercompanion.prayercompanionandroid.domain.models.QuranChapter
import com.prayercompanion.prayercompanionandroid.domain.models.QuranVerse

fun QuranDTO.toQuran(): Quran {
    val chapters = this.chapters.map { chapterDTO ->
        val verses = chapterDTO.verses.map { verseDTO ->
            QuranVerse(
                index = verseDTO.index,
                text = verseDTO.text,
                hasBismillah = verseDTO.bismillah.isNotBlank()
            )
        }
        QuranChapter(
            index = chapterDTO.index,
            name = chapterDTO.name,
            verses = verses,
        )
    }

    return Quran(chapters = chapters)
}