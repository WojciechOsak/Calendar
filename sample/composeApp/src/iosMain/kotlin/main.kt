import androidx.compose.ui.window.ComposeUIViewController
import io.wojciechosak.calendar.calendar.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
