package com.easy.myapplication.services.endpoints
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.dto.MapDirections
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface IMapBox  {
    @GET("cycling/{longitudeOrigin},{latitudeOrigin};{longitudeDestination},{latitudeDestination}?steps=true&language=pt-BR&access_token=${BuildConfig.TOKEN_MAPBOX}")
    suspend fun getRoutesMap(@Path(value="longitudeOrigin") longitudeOrigin:Double, @Path(value="latitudeOrigin")latitudeOrigin:Double,@Path(value="longitudeDestination") longitudeDestination:Double,@Path(value="latitudeDestination") latitudeDestination:Double): Response<MapDirections>
}