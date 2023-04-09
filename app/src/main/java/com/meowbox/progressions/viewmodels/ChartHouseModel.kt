package com.meowbox.progressions.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.House
import com.meowbox.fourpillars.Palace
import com.meowbox.fourpillars.Star
import kotlinx.serialization.internal.EnumSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

data class ChartHouseModel(val palace: Palace, val branch: Branch, val stars: List<Star>) : Parcelable {

    constructor(parcel: Parcel) : this(
        palace = Palace.valueOf(parcel.readString()),
        branch = Branch.valueOf(parcel.readString()),
        stars = Json.parse(serializer, parcel.readString())
    )

    fun toHouse() = House(palace, branch)

    constructor(branch: Branch, mingLocation: Branch, stars: List<Star>) :
            this(Palace.num(mingLocation - branch), branch, stars)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(palace.name)
        dest?.writeString(branch.name)
        dest?.writeString(Json.stringify(serializer, stars))
    }

    override fun describeContents(): Int = 0

    companion object {
        private val serializer = EnumSerializer(Star::class).list
        @JvmField
        @Suppress("unused")
        val CREATOR = createParcel { ChartHouseModel(it) }
    }
}
