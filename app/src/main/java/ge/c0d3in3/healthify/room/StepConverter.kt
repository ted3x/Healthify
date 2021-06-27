package ge.c0d3in3.healthify.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import ge.c0d3in3.healthify.model.StepData

class StepConverter {

    @TypeConverter
    fun listToJson(value: List<StepData>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<StepData>::class.java).toList()

}