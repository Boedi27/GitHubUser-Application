package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.room.FavoriteDao
import com.example.githubuser.data.room.FavoriteEntity
import com.example.githubuser.data.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insertFavorite(favoriteEntity) }
    }

    fun delete(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoriteDao.delete(favoriteEntity) }
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllFavorite()

    fun getFavorite(username: String) = mFavoriteDao.getFavorite(username)
}