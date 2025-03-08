
data class ApiResponse(
    val message: String,
    val error: Int,
    val data: List<Info>
)

data class Info(
    val id: Int,
    val nama_provinsi: String,
    val nama_kabupaten_kota: String,
    val tingkat_pengangguran_terbuka: Float,
    val satuan: String,
    val tahun: Int
)