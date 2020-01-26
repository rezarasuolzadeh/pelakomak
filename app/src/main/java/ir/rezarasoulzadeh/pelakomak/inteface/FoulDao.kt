package ir.rezarasoulzadeh.pelakomak.inteface

import ir.rezarasoulzadeh.pelakomak.model.Foul
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FoulDao {

    @Headers(
        "Content-Type:application/json"
    )
    @POST("Penalties/refreshUserPenalties")
    suspend fun getFoul(@Body barcode: HashMap<String, String>): Response<List<Foul>>

}