interface Platform {
    val name: String
    val platformType: PlatformType
}

enum class PlatformType  {
    ANDROID, IOS
}

expect fun getPlatform(): Platform
