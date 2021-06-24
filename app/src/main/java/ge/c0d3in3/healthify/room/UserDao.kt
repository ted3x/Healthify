package ge.c0d3in3.healthify.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.c0d3in3.healthify.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM `user` WHERE uid LIKE :userId LIMIT 1")
    suspend fun getUser(userId: Int): User

    @Insert
    suspend fun saveUser(user: User)
}