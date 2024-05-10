package data.mapper

import domain.model.GeoLocation

fun String?.toGeoLocationOrNull(): GeoLocation? {
    if (this == null) return null
    return this.toGeoLocation()
}

fun String.toGeoLocation(): GeoLocation {
    val (latitude, longitude) = this.split(",").map { it.dropLast(1).toDouble() }
    return GeoLocation(
        latitude = latitude,
        longitude = longitude,
    )
}