package com.example.workermanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable

@Dao
interface OnlineDao {

    @Insert(onConflict = REPLACE)
    fun addUser(moneyInsert: MoneyInsert)

    @Query("select * from money")
    fun getAllUsers(): Flowable<List<MoneyInsert>>

    @Update
    fun like(moneyInsert: MoneyInsert)

    @Query("select *from money where isliked=:like")
    fun isliked(like: Boolean): Flowable<List<MoneyInsert>>

}