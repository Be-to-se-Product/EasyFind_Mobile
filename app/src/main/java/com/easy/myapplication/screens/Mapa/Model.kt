import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.services.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MapaViewModel : ViewModel() {

    val produtos = MutableLiveData(SnapshotStateList<Produto>())

    val erroApi = MutableLiveData("");

    private val produtoService = Service.ProdutoService();



     fun getProdutos(latitude: Double?=null, longitude: Double?=null) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.e("Executou","Executou duas vezes")
                val response = produtoService.getMapaProdutos(latitude,longitude);
                if (response.isSuccessful) {
                    produtos.value!!.clear()
                    produtos.value!!.addAll(response.body() ?: listOf(Produto()) )
                } else {
                    erroApi.value = response.errorBody()?.string() ?: ""
                }
            } catch (e: Exception) {
                Log.e("API", e.message.toString())
                erroApi.value=e.message
            }

        }

    }


}