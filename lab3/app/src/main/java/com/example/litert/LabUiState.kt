package com.example.litert

data class LabUiState(
    val modelName: String = MobileNetV3,
    val result: String = "",
    val confidence: String = "",
    val inferenceTime: String = ""
) {
    fun takePhoto(): LabUiState = copy(
        result = "Keyboard",
        confidence = "94.8%",
        inferenceTime = "24 ms"
    )

    fun importGallery(): LabUiState = copy(
        result = "Notebook",
        confidence = "89.6%",
        inferenceTime = "31 ms"
    )

    fun switchModel(): LabUiState = copy(
        modelName = if (modelName == MobileNetV3) EfficientNetLite else MobileNetV3
    )

    fun clearResults(): LabUiState = copy(
        result = "",
        confidence = "",
        inferenceTime = ""
    )
}

private const val MobileNetV3 = "MobileNetV3"
private const val EfficientNetLite = "EfficientNet-Lite"
