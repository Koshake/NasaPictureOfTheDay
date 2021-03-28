package com.koshake1.nasapictureoftheday.retrofit.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EarthServerResponseData(
    @Expose val identifier: String?,
    @Expose val caption: String?,
    @Expose val image: String?,
    @Expose val version: String?,
    @Expose val centroid_coordinates: CoordinatesData?,
    @Expose val dscovr_j2000_position: PositionData?,
    @Expose val lunar_j2000_position: PositionData?,
    @Expose val sun_j2000_position: PositionData?,
    @Expose val attitude_quaternions: AttitudeData?,
    @Expose val date: String?
) : Parcelable

@Parcelize
data class CoordinatesData(
    @Expose val lat: Double?,
    @Expose val lon: Double?
) : Parcelable

@Parcelize
data class PositionData(
    @Expose val x: Double?,
    @Expose val y: Double?,
    @Expose val z: Double?
) : Parcelable

@Parcelize
data class AttitudeData(
    @Expose val q0: Double?,
    @Expose val q1: Double?,
    @Expose val q2: Double?,
    @Expose val q3: Double?
) : Parcelable