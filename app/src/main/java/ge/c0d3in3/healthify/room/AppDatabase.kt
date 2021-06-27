package ge.c0d3in3.healthify.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.model.User

@TypeConverters(StepConverter::class)
@Database(entities = [User::class, StepData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "healthify-db"
        fun getInstance(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}