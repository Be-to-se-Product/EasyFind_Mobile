import android.content.Context
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.Estabelecimento
import com.easy.myapplication.dto.FilterDTO
import com.easy.myapplication.dto.Metodo
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.dto.RoutesMapper
import com.easy.myapplication.dto.TargetRoutes
import com.easy.myapplication.services.Service.MapBoxService
import com.easy.myapplication.services.Service
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.getLatLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class MetodoPagamentoCheckbox(
    val nome: String,
    val key: Metodo,
    var checked: Boolean? = false,
)


data class LatandLong(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class DestinationTarget(
    val coordinates: LatandLong?=null,
    val estabelecimento:Estabelecimento?=null
)



class MapaViewModel : ViewModel() {
    val filterMapa = MutableLiveData(FilterDTO());
    val produtos = MutableLiveData(SnapshotStateList<Produto>())
    val destination = MutableLiveData(DestinationTarget());
    val erroApi = MutableLiveData("")
    val latLong = MutableLiveData(LatandLong())
    val infoRoutes = MutableLiveData(TargetRoutes())
    private val produtoService = Service.ProdutoService();
    private val mapaService = MapBoxService();

    val locationCallback = object : LocationCallback {
        override fun onSuccess(latitude: Double, longitude: Double) {
            latLong.postValue(latLong.value?.copy(latitude = latitude, longitude = longitude))
        }

        override fun onError(message: String?) {
            print(message)
        }
    }

   fun getLocations(context: Context){
       getLatLong(context, locationCallback)
   }



    fun applyFilters(filterDTO:FilterDTO){
        filterMapa.postValue(filterDTO)
        getProdutos( distancia = filterDTO.distancia, metodoPagamento = filterDTO.metodoPagamento,
            nome = filterDTO.nome
        )
    }

    fun clearFilter(setFilterDTO: (FilterDTO)->Unit){
        getProdutos()
        setFilterDTO(FilterDTO())
    }


fun getRoute(destinationRoute:DestinationTarget, origin:LatandLong) {
    CoroutineScope(Dispatchers.IO).launch {

        try {
            val response = destinationRoute.coordinates?.let {
                mapaService.getRoutesMap(
                    latitudeOrigin = origin.latitude,
                    longitudeDestination = it.longitude,
                    longitudeOrigin = origin.longitude,
                    latitudeDestination = it.latitude
                )
            };

            val directions = response?.body();

            if (directions!!.routes.isNotEmpty()) {

                val routesMapper = directions.routes[0].legs[0].steps.map {
                    RoutesMapper(
                        it.name,
                        it.duration,
                        it.distance,
                        it.maneuver.instruction,
                        it.maneuver.modifier
                    )
                }


                val routesDestination=TargetRoutes(
                    distance = directions.routes[0].distance,
                    duration = directions.routes[0].duration,
                )

                routesDestination.routes.addAll(routesMapper)
                infoRoutes.postValue(routesDestination)
                destination.postValue(destinationRoute)
            }
        }
        catch (e:Exception ){
            if(e.message!=null){
                erroApi.postValue(e.message)
            }
        }
    }
}

     fun getProdutos(metodoPagamento:String?=null,distancia:Float?=null,nome:String?=null) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = produtoService.getMapaProdutos(latitude = latLong.value?.latitude,longitude= latLong.value?.longitude, nome = nome, metodoPagamento =metodoPagamento, distancia = distancia);
                if (response.isSuccessful) {
                    produtos.value!!.clear()
                    produtos.value!!.addAll(response.body() ?: listOf() )
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }

        }

    }




}