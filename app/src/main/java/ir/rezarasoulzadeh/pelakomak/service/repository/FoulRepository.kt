package ir.rezarasoulzadeh.pelakomak.service.repository

import ir.rezarasoulzadeh.pelakomak.inteface.FoulDao
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.config.FoulRetrofitConfig
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class FoulRepository {

    fun provideSlider(barcode: String): List<Foul>? {
        var response: Response<List<Foul>>? = null
        val hashmap = HashMap<String, String>(1)
        hashmap["barcode"] = barcode
        runBlocking {
            try {
                response = FoulRetrofitConfig.retrofit().create(FoulDao::class.java)
                    .getFoul(hashmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response!!.body()
    }

    companion object {
        @JvmStatic
        fun getInstance(): FoulRepository {
            return FoulRepository()
        }
    }

}