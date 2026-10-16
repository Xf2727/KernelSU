package me.weishu.kernelsu.ui.screen.diagnostics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.weishu.kernelsu.BuildConfig
import me.weishu.kernelsu.Natives
import me.weishu.kernelsu.getKernelVersion
import me.weishu.kernelsu.ksuApp
import me.weishu.kernelsu.ui.screen.home.getManagerVersion
import me.weishu.kernelsu.ui.util.getSELinuxStatusRaw
import me.weishu.kernelsu.ui.util.resolveDeviceName
import me.weishu.kernelsu.ui.util.rootAvailable

@Composable
fun DiagnosticsScreen(onBack: () -> Unit) {
    val manager = getManagerVersion(ksuApp)
    val active = Natives.isManager && Natives.version != 0
    val rows = listOf(
        "CodexSU 管理器" to "${manager.versionName} (${manager.versionCode})",
        "内核接口" to if (active) "已连接 · API ${Natives.kernelUAPIVersion}" else "未连接",
        "运行模式" to if (active) if (Natives.isLkmMode) "LKM" else "GKI" else "未知",
        "内嵌 LKM" to if (active && Natives.isLkmBundled) "已加载 · ${BuildConfig.BUNDLED_LKM_VERSION_CODE}" else "未确认",
        "Root shell" to if (rootAvailable()) "可用" else "不可用",
        "SELinux" to getSELinuxStatusRaw(),
        "内核" to getKernelVersion().toString(),
        "设备" to resolveDeviceName(),
        "构建标识" to "CodexSU ${BuildConfig.VERSION_CODE} · ${BuildConfig.VERSION_NAME}",
    )
    Scaffold(topBar = {
        TopAppBar(title = { Text("诊断与构建信息") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") }
        })
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("仅显示当前设备已检测到的状态", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            rows.forEach { (label, value) ->
                val known = value != "未知" && value != "未连接" && value != "未确认" && value != "不可用"
                Surface(Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceContainerHigh) {
                    ListItem(headlineContent = { Text(label) }, supportingContent = { Text(value) }, leadingContent = {
                        Icon(if (known) Icons.Filled.CheckCircle else if (value == "未确认" || value == "未知") Icons.Filled.HelpOutline else Icons.Filled.Warning, null, tint = if (known) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                    })
                }
            }
        }
    }
}
