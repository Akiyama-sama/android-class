package com.example.litert

import org.junit.Assert.assertEquals
import org.junit.Test

class CameraUiStateTest {
    @Test
    fun permissionGranted_marksCameraReady() {
        val state = CameraUiState()

        val updated = state.permissionGranted()

        assertEquals(true, updated.hasPermission)
        assertEquals("相机已就绪", updated.message)
    }

    @Test
    fun recordingFlow_updatesStatusText() {
        val state = CameraUiState(hasPermission = true)

        val recording = state.recordingStarted()
        val paused = recording.recordingPaused()
        val resumed = paused.recordingResumed()
        val stopped = resumed.recordingStopped("video.mp4")

        assertEquals(RecordingStatus.Recording, recording.recordingStatus)
        assertEquals(RecordingStatus.Paused, paused.recordingStatus)
        assertEquals(RecordingStatus.Recording, resumed.recordingStatus)
        assertEquals(RecordingStatus.Idle, stopped.recordingStatus)
        assertEquals("video.mp4", stopped.lastVideoName)
    }

    @Test
    fun photoSaved_recordsFileName() {
        val state = CameraUiState(hasPermission = true)

        val updated = state.photoSaved("photo.jpg")

        assertEquals("photo.jpg", updated.lastPhotoName)
        assertEquals("拍照完成：photo.jpg", updated.message)
    }
}
