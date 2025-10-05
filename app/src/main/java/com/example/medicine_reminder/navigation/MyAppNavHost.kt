import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.medicine_reminder.navigation.SetupNavGraph

@Composable
fun NavAppHost() {
    val navController = rememberNavController()
    val activity = LocalActivity.current

    // Only call SetupNavGraph if activity is not null
    activity?.let { nonNullActivity ->
        SetupNavGraph(
            navController = navController,
            activity = nonNullActivity
        )
    }
}
