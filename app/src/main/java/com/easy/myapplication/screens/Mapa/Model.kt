import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.Estabelecimento
import com.easy.myapplication.dto.FilterDTO
import com.easy.myapplication.dto.Metodo
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.dto.RoutesMapper
import com.easy.myapplication.services.Service.MapBoxService
import com.easy.myapplication.services.Service
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
    val filterDTO = MutableLiveData(FilterDTO());
    val filterMapa = MutableLiveData(FilterDTO());
    val produtos = MutableLiveData(SnapshotStateList<Produto>())
    val destination = MutableLiveData(DestinationTarget());
    val erroApi = MutableLiveData("")
    val latLong = MutableLiveData(LatandLong())
    val routes = MutableLiveData(SnapshotStateList<RoutesMapper>())
    private val produtoService = Service.ProdutoService();
    private val mapaService = MapBoxService();


    fun applyFilters(){
        filterMapa.postValue(filterDTO.value)
        getProdutos( distancia = filterDTO?.value?.distancia, metodoPagamento = filterDTO.value?.metodoPagamento,
            nome = filterDTO.value?.nome)
    }

    fun clearFilter(){
        getProdutos()
        filterMapa.postValue(FilterDTO())
        filterDTO.postValue(FilterDTO())
    }


fun getRoute(destinationRoute:DestinationTarget, origin:LatandLong) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = destinationRoute.coordinates?.let {
                mapaService.getRoutesMap(
                    latitudeOrigin = it.latitude,
                    longitudeDestination = it.longitude,
                    longitudeOrigin = origin.longitude,
                    latitudeDestination = origin.latitude
                )
            };
            val directions = response?.body();
            if (directions!!.routes.isNotEmpty()) {
                val routesMapper = directions!!.routes[0].legs[0].steps.map {
                    RoutesMapper(
                        it.name,
                        it.duration,
                        it.distance,
                        it.maneuver.instruction,
                        it.maneuver.modifier
                    )
                }
                routes.value!!.addAll(routesMapper)
                destination.postValue(destinationRoute)
            }
        }
        catch (e:Exception ){
            Log.e("Erro",e.toString())
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
                    produtos.value!!.addAll(response.body() ?: listOf(Produto()) )
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }

        }

    }


}