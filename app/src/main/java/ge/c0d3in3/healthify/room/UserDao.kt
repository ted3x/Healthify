package ge.c0d3in3.healthify.room

import androidx.room.*
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM `user` WHERE uid LIKE :userId LIMIT 1")
    suspend fun getUser(userId: String): User

    @Query("UPDATE `user` SET `steps` = :steps WHERE uid LIKE :userId")
    suspend fun updateSteps(steps: List<StepData>, userId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)
}