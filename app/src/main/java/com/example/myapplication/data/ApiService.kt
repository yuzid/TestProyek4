import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("bigdata/bps/od_17044_tingkat_pengangguran_terbuka__kabupatenkota")
    suspend fun getDataAPI(
        ): ApiResponse
}