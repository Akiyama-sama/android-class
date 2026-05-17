package com.example.litert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.litert.ui.theme.LiteRtTheme

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
                    else -> "AI 识别界面"
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
                    onTakePhoto = { labState = labState.takePhoto() },
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
        val items = listOf("任务一", "任务二", "任务三")
        items.forEachIndexed { index, title ->
            NavigationBarItem(
                selected = currentTask == index,
                onClick = { onTaskChange(index) },
                icon = {
                    Text(
                        text = "${index + 1}",
                        fontWeight = FontWeight.Bold
                    )
                },
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
            Text(
                text = "实验三",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
                    Text(
                        text = "左上",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontSize = 14.sp
                    )
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
                    Text(
                        text = "右下",
                        modifier = Modifier.align(Alignment.BottomEnd),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskThreeScreen(
    state: LabUiState,
    onTakePhoto: () -> Unit,
    onImportGallery: () -> Unit,
    onSwitchModel: () -> Unit,
    onClearResults: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        TaskCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "相机预览",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "这里只做占位",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ResultRow("模型", state.modelName)
                ResultRow("结果", state.result.ifBlank { "-" })
                ResultRow("置信度", state.confidence.ifBlank { "-" })
                ResultRow("耗时", state.inferenceTime.ifBlank { "-" })
            }
        }

        TaskCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onTakePhoto,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("拍照识别")
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
