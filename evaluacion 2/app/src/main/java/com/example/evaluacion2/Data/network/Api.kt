
object Api {
    private const val BASE_URL = "https://ragemusicbackend.onrender.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Interfaces globales
    val usuarioService: UsuarioService = retrofit.create(UsuarioService::class.java)
    val productoService: ProductoService = retrofit.create(ProductoService::class.java)
    val ventaService: VentaService = retrofit.create(VentaService::class.java)
}
