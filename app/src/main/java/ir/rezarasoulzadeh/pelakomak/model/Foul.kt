package ir.rezarasoulzadeh.pelakomak.model
import com.google.gson.annotations.SerializedName

data class Foul(
    @SerializedName("amount")
    val amount: Int = 0, // 600000
    @SerializedName("barcode")
    val barcode: String = "", // 89692969
    @SerializedName("bill_id")
    val billId: String = "", // 0708419000291
    @SerializedName("city")
    val city: String = "", // تهران
    @SerializedName("code")
    val code: String = "", // 2002
    @SerializedName("created_at")
    val createdAt: String = "", // 1396/6/19 23:00:00
    @SerializedName("description")
    val description: String = "", // تجاوز از سرعت مجاز (تا سي كيلومتر در ساع
    @SerializedName("id")
    val id: Int = 0, // 26
    @SerializedName("location")
    val location: String = "", // ستاري حكيم شمال به ج
    @SerializedName("paid")
    val paid: String = "", // false
    @SerializedName("payment_id")
    val paymentId: String = "", // 0000060018238
    @SerializedName("plaque")
    val plaque: String = "", // 381ص7110
    @SerializedName("serial")
    val serial: String = "",
    @SerializedName("type")
    val type: String = "", // دوربين
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("user_id")
    val userId: String = "" // 0
)