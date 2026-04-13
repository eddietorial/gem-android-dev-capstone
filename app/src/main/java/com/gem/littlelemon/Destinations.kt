package com.gem.littlelemon

// Each destination is an object implementing this interface.
// Using the class name as the route keeps things readable in logs
// and easy to extend.
interface Destinations {
    val route: String
}

object Onboarding : Destinations {
    override val route = "Onboarding"
}

object Home : Destinations {
    override val route = "Home"
}

object Profile : Destinations {
    override val route = "Profile"
}
