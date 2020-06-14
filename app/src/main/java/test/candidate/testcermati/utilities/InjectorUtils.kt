package test.candidate.testcermati.utilities

import test.candidate.testcermati.repositories.UserRepository
import test.candidate.testcermati.viewmodels.UserViewModelFactory

object InjectorUtils {
    fun provideUserViewModelFactory(): UserViewModelFactory {
        val repository = UserRepository()

        return UserViewModelFactory(repository)
    }
}