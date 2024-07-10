package uz.mobile.footzone.repository.impl

import uz.mobile.footzone.model.Stadiums
import uz.mobile.footzone.remote.RemoteDataSource
import uz.mobile.footzone.repository.StadiumRepository


class StadiumRepositoryImpl(private val remoteDataSource: RemoteDataSource) : StadiumRepository {

    override suspend fun fetchStadiums(): Result<Stadiums> =
        remoteDataSource.fetchStadiums().map {
            it.stadiums.map {
                it.toUiModel()
            }
        }
}