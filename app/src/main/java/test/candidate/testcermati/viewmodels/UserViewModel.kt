package test.candidate.testcermati.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import test.candidate.testcermati.models.UsersModel
import test.candidate.testcermati.repositories.UserRepository

class UserViewModel constructor(private val userRepository: UserRepository) : ViewModel() {
    private val userResponse = MediatorLiveData<UsersModel>()

    fun getUsers(username: String, page: Int): MediatorLiveData<UsersModel> {
        val liveData: LiveData<UsersModel> = userRepository.getUsers(username, page)

        userResponse.addSource(liveData, userResponse::setValue)

        return userResponse
    }
}