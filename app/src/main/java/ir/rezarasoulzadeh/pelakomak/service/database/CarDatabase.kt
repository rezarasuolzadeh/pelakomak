package ir.rezarasoulzadeh.pelakomak.service.database

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import ir.rezarasoulzadeh.pelakomak.model.Car
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CarDatabase(private val context: Context) {

    private val DATABASE_PATH =
        ("/data/data/" + context.applicationContext.packageName + "/databases/")
    private var database: SQLiteDatabase? = null

    companion object {
        private const val DATABASE_NAME = "iranVehicleNumberPlates.db"
        private const val TABLE_NAME = "IranNumberPlate"
        private const val NUMBER = "list_number"
        private const val CHARACTER = "list_characterCounty_char"
        private const val STATE = "list_state"
        private const val COUNTY = "list_characterCounty_county"
    }

    init {
        val databaseIsExist = checkDatabase()
        if (databaseIsExist) {
            openDatabase()
        } else {
            println("Database doesn't exist")
            createDatabase()
            openDatabase()
        }
    }

    @Throws(IOException::class)
    fun createDatabase() {
        val databaseIsExist = checkDatabase()
        if (databaseIsExist) {
        } else {
            try {
                copyDatabase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDatabase(): Boolean {
        var databaseCheck = false
        try {
            val myPath = DATABASE_PATH + DATABASE_NAME
            val databaseFile = File(myPath)
            databaseCheck = databaseFile.exists()
        } catch (e: SQLiteException) {
            println("Database doesn't exist")
        }

        return databaseCheck
    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val databaseInput = context.assets.open("databases/$DATABASE_NAME")

        val databaseDirectory = File(DATABASE_PATH)
        if (!databaseDirectory.exists()) {
            databaseDirectory.mkdirs()
        }

        val databaseFile = File(
            databaseDirectory,
            DATABASE_NAME
        )
        databaseFile.createNewFile()

        val databaseOutput = FileOutputStream(databaseFile)

        val buffer = ByteArray(1024)
        var length: Int
        while (true) {
            length = databaseInput.read(buffer)
            if (length <= 0) {
                break
            }
            databaseOutput.write(buffer, 0, length)
        }

        databaseOutput.flush()
        databaseOutput.close()
        databaseInput.close()
    }

    @Throws(SQLException::class)
    fun openDatabase() {
        val databasePath = DATABASE_PATH + DATABASE_NAME
        database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    fun get(number: String, character: String): String {
        var response = ""
        val selectOneQuery =
            "SELECT * FROM $TABLE_NAME WHERE $NUMBER = \"$number\" AND $CHARACTER = \"$character\""

        val cursor = database?.rawQuery(selectOneQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val state = cursor.getString(cursor.getColumnIndex(STATE))
                val county = cursor.getString(cursor.getColumnIndex(COUNTY))

                response = "$state-$county"
            }
        }

        if (response.isEmpty()) {
            var secondResponse = ""
            val secondSelectOneQuery = "SELECT * FROM $TABLE_NAME WHERE $NUMBER = \"$number\""

            val secondCursor = database?.rawQuery(secondSelectOneQuery, null)
            if (secondCursor != null) {
                if (secondCursor.moveToFirst()) {
                    val state = secondCursor.getString(
                        secondCursor.getColumnIndex(
                            STATE
                        )
                    )
                    val county = secondCursor.getString(
                        secondCursor.getColumnIndex(
                            COUNTY
                        )
                    )

                    secondResponse = "$state-$county"
                }
            }

            if (secondResponse.isNotEmpty()) {
                cursor?.close()
                database?.close()
                return secondResponse
            }
        }

        cursor?.close()
        database?.close()
        return response
    }

    fun getCity(city: String): ArrayList<Car> {
        val response: ArrayList<Car> = ArrayList()
        val selectOneQuery = "SELECT * FROM $TABLE_NAME WHERE $STATE = \"$city\""

        val cursor = database?.rawQuery(selectOneQuery, null)
        if (cursor!!.moveToFirst()) {
            do {
                val character = cursor.getString(cursor.getColumnIndex(CHARACTER))
                val number = cursor.getString(cursor.getColumnIndex(NUMBER))
                val county = cursor.getString(cursor.getColumnIndex(COUNTY))

                response.add(Car(number, character, "", county))
            } while (cursor.moveToNext())
        }

//        cursor?.close()
//        database?.close()
        return response
    }

}