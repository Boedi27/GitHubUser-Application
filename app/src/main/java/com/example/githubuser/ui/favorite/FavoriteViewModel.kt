package com.example.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.room.FavoriteEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteRepository =
        FavoriteRepository(application)

    fun insert(favoriteEntity: FavoriteEntity) {
        mFavoriteUserRepository.insertFavorite(favoriteEntity)
    }

    fun delete(favoriteEntity: FavoriteEntity) {
        mFavoriteUserRepository.delete(favoriteEntity)
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return mFavoriteUserRepository.getAllFavoriteUser()
    }

    fun getFavorite(username: String): LiveData<List<FavoriteEntity>> =
        mFavoriteUserRepository.getFavorite(username)
}