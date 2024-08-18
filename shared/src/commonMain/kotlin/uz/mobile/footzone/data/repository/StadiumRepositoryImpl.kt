package uz.mobile.footzone.data.repository

import uz.mobile.footzone.domain.model.Stadiums
import uz.mobile.footzone.data.remote.RemoteDataSource
import uz.mobile.footzone.domain.repository.StadiumRepository


class StadiumRepositoryImpl(private val remoteDataSource: RemoteDataSource) : StadiumRepository {

    override suspend fun fetchStadiums(): Result<Stadiums> =
        remoteDataSource.fetchStadiums().map {
            it.stadiums.map {
                it.toUiModel()
            }
        }
}