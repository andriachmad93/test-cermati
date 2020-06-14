package test.candidate.testcermati.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import test.candidate.testcermati.models.UsersModel

interface APIList {

    @GET
    fun getUsers(@Url url: String, @Query("q") query: String, @Query("page") page: Int): Call<UsersModel>
}
