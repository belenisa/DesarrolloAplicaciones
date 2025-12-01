import com.example.evaluacion2.Data.network.ArtistaService
import com.example.evaluacion2.Data.network.ArtistasService
import com.example.evaluacion2.Data.network.ComunaService
import com.example.evaluacion2.Data.network.DireccionService
import com.example.evaluacion2.Data.network.ImagenService
import com.example.evaluacion2.Data.network.MetodoEnvioService
import com.example.evaluacion2.Data.network.MetodoPagoService
import com.example.evaluacion2.Data.network.ProductoService
import com.example.evaluacion2.Data.network.RegionService
import com.example.evaluacion2.Data.network.Rol.EstadoVentaService
import com.example.evaluacion2.Data.network.Rol.RolService
import com.example.evaluacion2.Data.network.Rol.VentaServive
import com.example.evaluacion2.Data.network.TipoProductoService
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
    val regionService: RegionService = retrofit.create(RegionService::class.java)
    val metodoPagoService: MetodoPagoService = retrofit.create(MetodoPagoService::class.java)
    val metodoEnvioService: MetodoEnvioService = retrofit.create(MetodoEnvioService::class.java)
    val imagenService: ImagenService = retrofit.create(ImagenService::class.java)
    val estadoVentaService: EstadoVentaService = retrofit.create(EstadoVentaService::class.java)
    val direccionService: DireccionService = retrofit.create(DireccionService::class.java)
    val comunaService: ComunaService = retrofit.create(ComunaService::class.java)
    val artistasService: ArtistasService = retrofit.create(ArtistasService::class.java)
    val artistaService: ArtistaService = retrofit.create(ArtistaService::class.java)

    val tipoProductoService: TipoProductoService = retrofit.create(TipoProductoService::class.java)
}
