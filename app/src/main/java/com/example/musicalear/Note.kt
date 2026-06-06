package com.example.musicalear

import kotlin.math.pow

/**
 * Class to use each note from DO to SI.
 * @property semitoneFromDO give the gap between 2 notes to calculate frequency.
 * Can be used directly as 'Note.DO'.
 */
enum class Note(val semitoneFromDO: Int, val displayName: String) {
    DO(0, "Do"),
    DO_DIESE(1, "Do#"),
    RE(2, "Ré"),
    RE_DIESE(3, "Ré#"),
    MI(4, "Mi"),
    FA(5, "Fa"),
    FA_DIESE(6, "Fa#"),
    SOL(7, "Sol"),
    SOL_DIESE(8, "Sol#"),
    LA(9, "La"),
    LA_DIESE(10, "La#"),
    SI(11, "Si")
}

/**
 * Class that contain the name of the note and the octave desired.
 */
data class MusicalNote(
    val note: Note,
    val octave: Int
)

/**
 * Create the complete note combining note (DO, RE...) with its octave
 * Example: 'Note.DO.octave(4)'
 *
 * @param octave : the octave chosen
 * @return the complete musical note
 */
fun Note.octave(octave: Int): MusicalNote {
    return MusicalNote(this, octave)
}

/**
 * Calculate the frequency of the note depending on its octave
 * The calculus is done with a referenced value of LA4 and
 * each gap between two semitones is made with a factor 2^(1/2)
 *
 * @return the note frequency in hertz
 */
fun MusicalNote.frequency(): Double {
    val midiValue = (octave + 1) * 12 + note.semitoneFromDO
    return AudioConfig.referenceLA4 * 2.0.pow((midiValue - AudioConfig.LA4_MIDI) / 12.0)
}