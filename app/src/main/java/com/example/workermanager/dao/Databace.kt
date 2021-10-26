package com.example.workermanager.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoneyInsert::class], version = 1)
abstract class Databace : RoomDatabase() {

    abstract fun onlineDao(): OnlineDao

    companion object {
        private var instance: Databace? = null

        @Synchronized
        fun getInstance(context: Context): Databace {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, Databace::class.java, "PdpAppRoomVersion")
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }
    }
}