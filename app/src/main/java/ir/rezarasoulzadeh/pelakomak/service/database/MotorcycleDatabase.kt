package ir.rezarasoulzadeh.pelakomak.service.database

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import ir.rezarasoulzadeh.pelakomak.model.Motorcycle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MotorcycleDatabase(private val context: Context) {

    private val DATABASE_PATH =
        ("/data/data/" + context.applicationContext.packageName + "/databases/")
    private var database: SQLiteDatabase? = null

    companion object {
        private const val DATABASE_NAME = "motorcycle.db"
        private const val TABLE_NAME = "motorcycle"
        private const val NUMBER = "number"
        private const val STATE = "state"
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
            // database is exist :)
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
        //Open your local db as the input stream
        val databaseInput = context.assets.open("databases/$DATABASE_NAME")

        //Open the empty db as the output stream
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

        // transfer byte to input file to output file
        val buffer = ByteArray(1024)
        var length: Int
        while (true) {
            length = databaseInput.read(buffer)
            if (length <= 0) {
                break
            }
            databaseOutput.write(buffer, 0, length)
        }

        //Close the streams
        databaseOutput.flush()
        databaseOutput.close()
        databaseInput.close()
    }

    @Throws(SQLException::class)
    fun openDatabase() {
        //Open the database
        val databasePath = DATABASE_PATH + DATABASE_NAME
        database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    fun getCity(state: String): ArrayList<Motorcycle> {
        val response: ArrayList<Motorcycle> = ArrayList()
        val selectOneQuery = "SELECT * FROM $TABLE_NAME WHERE $STATE = \"$state\""

        val cursor = database?.rawQuery(selectOneQuery, null)

        if (cursor!!.moveToFirst()) {
            do {
                val states = cursor.getString(cursor.getColumnIndex(STATE))
                val number = cursor.getString(cursor.getColumnIndex(NUMBER))

                response.add(Motorcycle(states, number))
            } while (cursor .moveToNext())
        }

//        cursor?.close()
//        database?.close()
        return response
    }

}