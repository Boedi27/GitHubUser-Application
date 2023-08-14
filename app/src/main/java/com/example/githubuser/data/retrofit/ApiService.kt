package com.example.githubuser.data.retrofit

import com.example.githubuser.GithubResponse
import com.example.githubuser.GithubUser
import com.example.githubuser.data.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{login}")
    fun getDetailUser(@Path("login") login: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubUser>>
}