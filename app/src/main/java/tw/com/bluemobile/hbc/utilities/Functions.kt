package tw.com.bluemobile.hbc.utilities

infix fun <T: Any?> Boolean?.then(block: () -> T): T? = if (this == true) block() else null
