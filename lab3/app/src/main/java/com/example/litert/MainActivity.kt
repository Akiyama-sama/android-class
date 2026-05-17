package com.example.litert

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.litert.ui.theme.LiteRtTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiteRtTheme {
                LabApp()
            }
        }
    }
}

@Composable
private fun LabApp() {
    var currentTask by remember { mutableIntStateOf(0) }
    var labState by remember { mutableStateOf(LabUiState()) }
    var rowClicked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomMenu(
                currentTask = currentTask,
                onTaskChange = { currentTask = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PageTitle(
                title = when (currentTask) {
                    0 -> "任务一"
                    1 -> "任务二"
                    else -> "任务三"
                },
                subtitle = when (currentTask) {
                    0 -> "第一个 Kotlin 页面"
                    1 -> "Compose 基础布局"
                    else -> "CameraX 相机"
                }
            )

            when (currentTask) {
                0 -> TaskOneScreen()
                1 -> TaskTwoScreen(
                    rowClicked = rowClicked,
                    onRowClick = { rowClicked = !rowClicked }
                )
                2 -> TaskThreeScreen(
                    state = labState,
                    onPhotoRecognized = { labState = labState.takePhoto() },
                    onImportGallery = { labState = labState.importGallery() },
                    onSwitchModel = { labState = labState.switchModel() },
                    onClearResults = { labState = labState.clearResults() }
                )
            }
        }
    }
}

@Composable
private fun BottomMenu(currentTask: Int, onTaskChange: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        listOf("任务一", "任务二", "任务三").forEachIndexed { index, title ->
            NavigationBarItem(
                selected = currentTask == index,
                onClick = { onTaskChange(index) },
                icon = { Text("${index + 1}", fontWeight = FontWeight.Bold) },
                label = { Text(title) }
            )
        }
    }
}

@Composable
private fun PageTitle(title: String, subtitle: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("实验三", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(subtitle, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TaskOneScreen() {
    TaskCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                modifier = Modifier.size(92.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Kt",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "Hello Android!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "用 Empty Activity 新建项目，页面能正常显示文字。",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TaskTwoScreen(rowClicked: Boolean, onRowClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel("Row")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LayoutBlock("左", MaterialTheme.colorScheme.primary.copy(alpha = 0.14f), Modifier.weight(1f))
                    LayoutBlock("中", MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f), Modifier.weight(1f))
                    LayoutBlock("右", MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f), Modifier.weight(1f))
                }

                Button(
                    onClick = onRowClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (rowClicked) "已点击" else "点一下")
                }
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel("Column")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DemoStrip("第一行")
                    DemoStrip("第二行")
                    DemoStrip("第三行")
                }
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel("Box")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(14.dp)
                ) {
                    Text("左上", modifier = Modifier.align(Alignment.TopStart), fontSize = 14.sp)
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(86.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Box",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Text("右下", modifier = Modifier.align(Alignment.BottomEnd), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun TaskThreeScreen(
    state: LabUiState,
    onPhotoRecognized: () -> Unit,
    onImportGallery: () -> Unit,
    onSwitchModel: () -> Unit,
    onClearResults: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    var cameraState by remember {
        mutableStateOf(
            if (hasCameraPermissions(context)) CameraUiState().permissionGranted() else CameraUiState()
        )
    }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var recording by remember { mutableStateOf<Recording?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        cameraState = if (grants.values.all { it }) {
            cameraState.permissionGranted()
        } else {
            cameraState.permissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermissions(context)) {
            permissionLauncher.launch(REQUIRED_PERMISSIONS)
        } else {
            cameraState = cameraState.permissionGranted()
        }
    }

    LaunchedEffect(cameraState.hasPermission, previewView) {
        if (cameraState.hasPermission) {
            bindCameraUseCases(
                context = context,
                lifecycleOwner = lifecycleOwner,
                previewView = previewView,
                onUseCasesReady = { image, video ->
                    imageCapture = image
                    videoCapture = video
                    cameraState = cameraState.permissionGranted()
                },
                onError = { cameraState = cameraState.cameraError(it) }
            )
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        TaskCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black)
            ) {
                if (cameraState.hasPermission) {
                    AndroidView(
                        factory = { previewView },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("需要相机权限", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Button(onClick = { permissionLauncher.launch(REQUIRED_PERMISSIONS) }) {
                            Text("授予权限")
                        }
                    }
                }
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ResultRow("模型", state.modelName)
                ResultRow("结果", state.result.ifBlank { "-" })
                ResultRow("置信度", state.confidence.ifBlank { "-" })
                ResultRow("耗时", state.inferenceTime.ifBlank { "-" })
                ResultRow("拍照文件", cameraState.lastPhotoName.ifBlank { "-" })
                ResultRow("录像文件", cameraState.lastVideoName.ifBlank { "-" })
                ResultRow("状态", cameraState.message)
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            takePhoto(
                                context = context,
                                imageCapture = imageCapture,
                                onSaved = { name ->
                                    cameraState = cameraState.photoSaved(name)
                                    onPhotoRecognized()
                                },
                                onError = { cameraState = cameraState.cameraError(it) }
                            )
                        },
                        enabled = cameraState.hasPermission && imageCapture != null && recording == null,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("拍照")
                    }
                    Button(
                        onClick = onImportGallery,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("相册导入")
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = {
                            if (recording == null) {
                                startRecording(
                                    context = context,
                                    videoCapture = videoCapture,
                                    onRecordingCreated = { recording = it },
                                    onStarted = { cameraState = cameraState.recordingStarted() },
                                    onFinalized = { name ->
                                        recording = null
                                        cameraState = cameraState.recordingStopped(name)
                                    },
                                    onError = {
                                        recording = null
                                        cameraState = cameraState.cameraError(it)
                                    }
                                )
                            } else {
                                recording?.stop()
                            }
                        },
                        enabled = cameraState.hasPermission && videoCapture != null,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (recording == null) "开始录像" else "停止录像")
                    }
                    OutlinedButton(
                        onClick = {
                            if (cameraState.recordingStatus == RecordingStatus.Paused) {
                                recording?.resume()
                                cameraState = cameraState.recordingResumed()
                            } else {
                                recording?.pause()
                                cameraState = cameraState.recordingPaused()
                            }
                        },
                        enabled = recording != null,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (cameraState.recordingStatus == RecordingStatus.Paused) "继续" else "暂停")
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = onSwitchModel,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("切换模型")
                    }
                    OutlinedButton(
                        onClick = onClearResults,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("清空结果")
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskCard(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun LayoutBlock(text: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(14.dp),
        color = color
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun DemoStrip(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun hasCameraPermissions(context: Context): Boolean =
    REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

private fun bindCameraUseCases(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onUseCasesReady: (ImageCapture, VideoCapture<Recorder>) -> Unit,
    onError: (String) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener(
        {
            try {
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }
                val imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()
                val recorder = Recorder.Builder().build()
                val videoCapture = VideoCapture.withOutput(recorder)

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture,
                    videoCapture
                )
                onUseCasesReady(imageCapture, videoCapture)
            } catch (exception: Exception) {
                onError("相机启动失败：${exception.message ?: "未知错误"}")
            }
        },
        ContextCompat.getMainExecutor(context)
    )
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    onSaved: (String) -> Unit,
    onError: (String) -> Unit
) {
    val capture = imageCapture ?: run {
        onError("相机还没准备好")
        return
    }
    val photoFile = createMediaFile(context, "jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    capture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onSaved(photoFile.name)
            }

            override fun onError(exception: ImageCaptureException) {
                onError("拍照失败：${exception.message ?: "未知错误"}")
            }
        }
    )
}

@SuppressLint("MissingPermission")
private fun startRecording(
    context: Context,
    videoCapture: VideoCapture<Recorder>?,
    onRecordingCreated: (Recording) -> Unit,
    onStarted: () -> Unit,
    onFinalized: (String) -> Unit,
    onError: (String) -> Unit
) {
    val capture = videoCapture ?: run {
        onError("录像还没准备好")
        return
    }
    val videoFile = createMediaFile(context, "mp4")
    val outputOptions = FileOutputOptions.Builder(videoFile).build()

    val recording = capture.output
        .prepareRecording(context, outputOptions)
        .withAudioEnabled()
        .start(ContextCompat.getMainExecutor(context)) { event ->
            when (event) {
                is VideoRecordEvent.Start -> onStarted()
                is VideoRecordEvent.Finalize -> {
                    if (event.hasError()) {
                        onError("录像失败：${event.error}")
                    } else {
                        onFinalized(videoFile.name)
                    }
                }
            }
        }
    onRecordingCreated(recording)
}

private fun createMediaFile(context: Context, extension: String): File {
    val directory = context.getExternalFilesDir(null) ?: context.filesDir
    val timeStamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA)
        .format(System.currentTimeMillis())
    return File(directory, "$timeStamp.$extension")
}

private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO
)
