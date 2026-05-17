package com.example.litert

data class CameraUiState(
    val hasPermission: Boolean = false,
    val recordingStatus: RecordingStatus = RecordingStatus.Idle,
    val lastPhotoName: String = "",
    val lastVideoName: String = "",
    val message: String = "等待相机权限"
) {
    fun permissionGranted(): CameraUiState = copy(
        hasPermission = true,
        message = "相机已就绪"
    )

    fun permissionDenied(): CameraUiState = copy(
        hasPermission = false,
        recordingStatus = RecordingStatus.Idle,
        message = "没有相机或录音权限"
    )

    fun photoSaved(fileName: String): CameraUiState = copy(
        lastPhotoName = fileName,
        message = "拍照完成：$fileName"
    )

    fun recordingStarted(): CameraUiState = copy(
        recordingStatus = RecordingStatus.Recording,
        message = "正在录像"
    )

    fun recordingPaused(): CameraUiState = copy(
        recordingStatus = RecordingStatus.Paused,
        message = "录像已暂停"
    )

    fun recordingResumed(): CameraUiState = copy(
        recordingStatus = RecordingStatus.Recording,
        message = "继续录像"
    )

    fun recordingStopped(fileName: String): CameraUiState = copy(
        recordingStatus = RecordingStatus.Idle,
        lastVideoName = fileName,
        message = "录像完成：$fileName"
    )

    fun cameraError(message: String): CameraUiState = copy(
        recordingStatus = RecordingStatus.Idle,
        message = message
    )
}

enum class RecordingStatus {
    Idle,
    Recording,
    Paused
}
