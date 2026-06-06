package com.example.musicalear

import kotlin.math.exp
import kotlin.math.sin

const val SAMPLE_RATE = 44100

/**
 * Generate note wave and play it
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun playTone(frequency: Double, durationMs: Int = 200){
    val audio = when(AudioConfig.soundType) {
        SoundType.PUR_SIN -> generateSinWave(frequency, durationMs)
        SoundType.HARMONIC -> generateHarmonicWave(frequency, durationMs)
        SoundType.INSTRUMENTAL -> generateInstrumentWave(frequency, durationMs)
    }
    playAudio(audio)
}

/**
 * Generate sinusoidal wave
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun generateSinWave(frequency: Double, durationMs: Int = 500): ShortArray {
    val count = (SAMPLE_RATE * durationMs / 1000.0).toInt()
    val audio = ShortArray(count)

    for (i in 0 until count) {
        val angle = 2.0 * Math.PI * i * frequency / SAMPLE_RATE
        audio[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
    }
    return audio
}

/**
 * Generate harmonic wave
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun generateHarmonicWave(frequency: Double, durationMs: Int = 500): ShortArray{
    val count = (SAMPLE_RATE * durationMs / 1000.0).toInt()
    val audio = ShortArray(count)

    for (i in 0 until count) {
        val t = i.toDouble() / SAMPLE_RATE
        val value = sin(2 * Math.PI * frequency * t) +
                    0.5 * sin(2 * Math.PI * frequency * 2 * t) +
                    0.25 * sin(2 * Math.PI * frequency * 3 * t)
        audio[i] = (value * Short.MAX_VALUE / 1.75).toInt().toShort()
    }
    return audio
}

/**
 * Generate harmonic wave more instrument like
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun generateInstrumentWave(frequency: Double, durationMs: Int = 500): ShortArray{
    val audio = when(AudioConfig.instrument) {
        Instrument.CELLO -> generateCelloWave(frequency, durationMs)
        Instrument.PIANO -> generatePianoWave(frequency, durationMs)
    }
    return audio
}

/**
 * Generate harmonic wave more cello like
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun generateCelloWave(frequency: Double, durationMs: Int = 500): ShortArray{
    val count = (SAMPLE_RATE * durationMs / 1000.0).toInt()
    val audio = ShortArray(count)

    for (i in 0 until count) {
        val t = i.toDouble() / SAMPLE_RATE
        val progress = i.toDouble()/count
        val envelope = when {
            progress < 0.05 -> progress / 0.05              // attack
            progress > 0.85 -> (1.0 - progress) / 0.15     // release
            else -> 1.0                                    // sustain
        }
        val vibrato = 1.0 + 0.003 * sin(2 * Math.PI * 5 * t)
        val f = frequency * vibrato

        val value =
            1.0 * sin(2 * Math.PI * f * t) +
            0.5 * sin(2 * Math.PI * 2 * f * t) +
            0.25 * sin(2 * Math.PI * 3 * f * t) +
            0.1 * sin(2 * Math.PI * 4 * f * t) +
            0.05 * sin(2 * Math.PI * 5 * f * t)
        val finalValue = value*envelope
        audio[i] = (finalValue * Short.MAX_VALUE / 1.5).toInt().toShort()
    }
    return audio
}

/**
 * Generate harmonic wave more piano like
 * @param frequency : the frequency of the note
 * @param durationMs : the duration of the note
 * @return a short array containing the wave sample
 */
fun generatePianoWave(frequency: Double, durationMs: Int = 500): ShortArray{
    val count = (SAMPLE_RATE * durationMs / 1000.0).toInt()
    val audio = ShortArray(count)

    for (i in 0 until count) {
        val t = i.toDouble() / SAMPLE_RATE
        val progress = i.toDouble()/count
        val envelope = exp(-3.0 * progress)

        val value =
            1.0 * sin(2 * Math.PI * frequency * t) +
            0.7 * sin(2 * Math.PI * 2 * frequency * t) +
            0.4 * sin(2 * Math.PI * 3 * frequency * t) +
            0.2 * sin(2 * Math.PI * 4 * frequency * t) +
            0.1 * sin(2 * Math.PI * 5 * frequency * t) +
            0.05 * sin(2 * Math.PI * 6 * frequency * t)
        val finalValue = value*envelope
        audio[i] = (finalValue * Short.MAX_VALUE / 1.5).toInt().toShort()
    }
    return audio
}

/**
 * Use Android function to play the note
 * @param audio : a short array containing the wave sample
 */
fun playAudio(audio: ShortArray){
    val audioTrack = android.media.AudioTrack.Builder()
        .setAudioFormat(
            android.media.AudioFormat.Builder()
                .setSampleRate(SAMPLE_RATE)
                .setEncoding(android.media.AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(android.media.AudioFormat.CHANNEL_OUT_MONO)
                .build()
        )
        .setBufferSizeInBytes(audio.size * 2)
        .setTransferMode(android.media.AudioTrack.MODE_STATIC)
        .build()

    audioTrack.write(audio, 0, audio.size)
    audioTrack.play()
}