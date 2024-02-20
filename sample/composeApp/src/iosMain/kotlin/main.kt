import androidx.compose.ui.window.ComposeUIViewController
import com.voytec.calendar.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
