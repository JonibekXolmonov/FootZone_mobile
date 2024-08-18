package uz.mobile.footzone.data.repository

import uz.mobile.footzone.data.remote.network.request.Requests
import uz.mobile.footzone.data.remote.network.response.Responses
import uz.mobile.footzone.data.settings.SettingsSource
import uz.mobile.footzone.data.remote.RemoteDataSource
import uz.mobile.footzone.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val settingsSource: SettingsSource
) : AuthRepository {

    override suspend fun signUp(user: Requests.User): Result<Responses.AccessToken> {
        return remoteDataSource.signUp(user).map {
            settingsSource.saveToken(it.token)
            it
        }
    }
}