package data.local.converter

import androidx.room.TypeConverter
import data.mapper.toGeoLocation
import domain.model.GeoLocation

class Converters {
    @TypeConverter
    fun fromStringToGeoLocation(string: String): GeoLocation {
        return string.toGeoLocation()
    }

    @TypeConverter
    fun fromGeoLocationToString(geoLocation: GeoLocation): String {
        return "${geoLocation.latitude},${geoLocation.longitude}"
    }
}