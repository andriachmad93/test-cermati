package test.candidate.testcermati.models

import android.os.Parcel
import android.os.Parcelable

data class UsersModel(
    var total_count: Int = 0
    , var incomplete_results: Boolean = false
    , var items: List<UserModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()
        , parcel.readByte() != 0.toByte()
        , parcel.createTypedArrayList(UserModel)
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(total_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersModel> {
        override fun createFromParcel(parcel: Parcel): UsersModel {
            return UsersModel(parcel)
        }

        override fun newArray(size: Int): Array<UsersModel?> {
            return arrayOfNulls(size)
        }
    }
}