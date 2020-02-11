package ir.rezarasoulzadeh.pelakomak.service.repository

import ir.rezarasoulzadeh.pelakomak.inteface.FoulDao
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.config.FoulRetrofitConfig
import kotlinx.coroutines.runBlocking

class FoulRepository {

    fun provideFoul(barcode: String): List<Foul>? {
        var result: List<Foul>? = null
        val hashmap = HashMap<String, String>(1)
        hashmap["barcode"] = barcode
        runBlocking {
            try {
                val response = FoulRetrofitConfig.retrofit().create(FoulDao::class.java)
                    .getFoul(hashmap)
                result = response.body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    companion object {
        @JvmStatic
        fun getInstance(): FoulRepository {
            return FoulRepository()
        }
    }

}