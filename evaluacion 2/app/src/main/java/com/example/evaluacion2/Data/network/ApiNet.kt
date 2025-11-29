import com.example.evaluacion2.Data.network.ProductoService
import com.example.evaluacion2.Data.network.Rol.RolService
import com.example.evaluacion2.Data.network.Rol.VentaServive
import com.example.evaluacion2.Data.network.UsuariosService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNet {
    private const val BASE_URL = "https://ragemusicbackend.onrender.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Interfaces globales
    val usuarioService: UsuariosService = retrofit.create(UsuariosService::class.java)
    val productoService: ProductoService = retrofit.create(ProductoService::class.java)
    val ventaService: VentaServive = retrofit.create(VentaServive::class.java)
    val rolService: RolService = retrofit.create(RolService::class.java)
}
