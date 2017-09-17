package ru.xmn.common.extensions

import android.location.Location
import com.google.android.gms.maps.model.LatLng


fun LatLng.distanceTo(anotherLocation: LatLng): Float {
    val l1 = Location("1").also { location -> location.latitude = this.latitude; location.longitude = this.longitude }
    val l2 = Location("2").also { location -> location.latitude = anotherLocation.latitude; location.longitude = anotherLocation.longitude }
    return l1.distanceTo(l2)
}