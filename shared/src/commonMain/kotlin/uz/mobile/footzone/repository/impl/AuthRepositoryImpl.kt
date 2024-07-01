package uz.mobile.footzone.repository.impl

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses
import uz.mobile.footzone.data.settings.SettingsSource
import uz.mobile.footzone.remote.RemoteDataSource
import uz.mobile.footzone.repository.AuthRepository

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