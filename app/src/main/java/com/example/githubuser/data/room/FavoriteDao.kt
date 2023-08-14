package com.example.githubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoriteTable WHERE username = :username")
    fun getFavorite(username: String): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteTable ORDER by username ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>
}