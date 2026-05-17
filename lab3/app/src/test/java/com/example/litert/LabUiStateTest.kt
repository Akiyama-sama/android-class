package com.example.litert

import org.junit.Assert.assertEquals
import org.junit.Test

class LabUiStateTest {
    @Test
    fun takePhoto_updatesRecognitionState() {
        val state = LabUiState()

        val updated = state.takePhoto()

        assertEquals("MobileNetV3", updated.modelName)
        assertEquals("Keyboard", updated.result)
        assertEquals("94.8%", updated.confidence)
        assertEquals("24 ms", updated.inferenceTime)
    }

    @Test
    fun switchModel_togglesBetweenModels() {
        val start = LabUiState(modelName = "MobileNetV3")

        val switched = start.switchModel()

        assertEquals("EfficientNet-Lite", switched.modelName)
    }

    @Test
    fun clearResults_keepsModelAndResetsMetrics() {
        val state = LabUiState(
            modelName = "EfficientNet-Lite",
            result = "Notebook",
            confidence = "89.6%",
            inferenceTime = "31 ms"
        )

        val cleared = state.clearResults()

        assertEquals("EfficientNet-Lite", cleared.modelName)
        assertEquals("", cleared.result)
        assertEquals("", cleared.confidence)
        assertEquals("", cleared.inferenceTime)
    }
}
