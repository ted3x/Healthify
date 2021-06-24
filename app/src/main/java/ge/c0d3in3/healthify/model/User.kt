package ge.c0d3in3.healthify.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var uid: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var profilePicture: String = "",
    var weight: Double = 0.0,
    var height: Double = 0.0,
    var age: Int = 0
)