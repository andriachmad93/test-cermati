package test.candidate.testcermati.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.candidate.testcermati.core.APIList
import test.candidate.testcermati.core.restfull.RetrofitManager
import test.candidate.testcermati.models.UsersModel

class UserRepository {
    private val mRetrofit = RetrofitManager()
    private var service: APIList = mRetrofit.service

    init {
        mRetrofit.initRetrofit()
    }

    fun getUsers(username: String, page: Int): LiveData<UsersModel> {
        val userResponse: MutableLiveData<UsersModel> = MutableLiveData()

        service.getUsers("/search/users", username, page).enqueue(
            object : Callback<UsersModel> {
                override fun onResponse(call: Call<UsersModel>, response: Response<UsersModel>) {
                    userResponse.value = response.body()
                }

                override fun onFailure(call: Call<UsersModel>, t: Throwable) {}
            }
        )

        return userResponse
    }
}