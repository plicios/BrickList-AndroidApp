import pl.piotrgorny.bricklist.data.api.ApiResult
import pl.piotrgorny.bricklist.data.api.BrickResult
import pl.piotrgorny.bricklist.data.api.MinifigResult
import pl.piotrgorny.bricklist.data.api.SetResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RebrickableApi {
    @GET("sets/{set_number}/?key=335f3a9e0e587ba2a092da8a062be715")
    suspend fun getSet(@Path("set_number") setId: String) : SetResult
    @GET("sets/{set_number}/parts/?key=335f3a9e0e587ba2a092da8a062be715")
    suspend fun getParts(@Path("set_number") setId: String, @Query("page") page: Int, @Query("page_size") pageSize: Int = 1000) : ApiResult<BrickResult>

    @GET("sets/{set_number}/minifigs/?key=335f3a9e0e587ba2a092da8a062be715")
    suspend fun getMinifigs(@Path("set_number") setId: String, @Query("page_size") pageSize: Int = 1000) : ApiResult<MinifigResult>

    @GET("minifigs/{minifig_id}/parts/?key=335f3a9e0e587ba2a092da8a062be715")
    suspend fun getMinifigParts(@Path("minifig_id") minifigId: String, @Query("page_size") pageSize: Int = 1000) : ApiResult<BrickResult>
}







