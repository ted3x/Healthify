package ge.c0d3in3.healthify.model

import androidx.room.*
import java.util.*

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    var uid: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var profilePicture: String = "",
    var weight: Double = 0.0,
    var targetWeight: Double = 0.0,
    var height: Int = 0,
    var age: Int = 0,
    var steps: MutableList<StepData> = mutableListOf(),
    var targetWater: Int = 8,
    var targetSleep: Int = 8,
    var targetStep: Int = 10000
)

@Entity
data class StepData (
    @PrimaryKey
    var timestamp: Long = 0,
    var steps: Int = 0,
    var water: Int = 0,
    var sleep: Int = 0,
    var weightGain: Double = 0.0,
    var targetWater: Int = 8,
    var targetSleep: Int = 8,
    var targetStep: Int = 10000
)