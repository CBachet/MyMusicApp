package com.example.musicalear

enum class SoundType {
    PUR_SIN,
    HARMONIC,
    INSTRUMENTAL
}

enum class Instrument {
    PIANO,
    CELLO
}

object AudioConfig {

    const val LA4_MIDI = 69 //number given for LA 4 in midi. We will use it to calculate every note
    var referenceLA4: Double = 442.0
    var soundType = SoundType.HARMONIC
    var instrument = Instrument.CELLO
    var semiToneDisplay : Boolean = true
}
