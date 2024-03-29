package com.haydhook.golfapp.utils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Outlined.Home, "Home")
    object NewGame : NavigationItem("new", Icons.Outlined.AddCircle, "New")
    object Current : NavigationItem("current", Icons.Outlined.PlayArrow, "Resume")
    object History : NavigationItem("history", Icons.Outlined.MailOutline, "History")
    object Settings : NavigationItem("settings", Icons.Outlined.Settings, "Settings")
}
