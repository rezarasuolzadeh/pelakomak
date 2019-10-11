package ir.rezarasoulzadeh.pelakomak.database

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CarNumberplateDB(val mycontext: Context) {

    companion object {
        private val DB_NAME = "iranVehicleNumberPlates.db"
        private val TABLE_NAME = "IranNumberPlate"
        private val NUMBER = "list_number"
        private val CHARACTER = "list_characterCounty_char"
        private val STATE = "list_state"
        private val COUNTY = "list_characterCounty_county"
    }

    private val DB_PATH = ("/data/data/"
            + mycontext.applicationContext.packageName
            + "/databases/")

    var myDataBase: SQLiteDatabase? = null

    init {
        val dbexist = checkdatabase()
        if (dbexist) {
            opendatabase()
        } else {
            println("Database doesn't exist")
            createdatabase()
            opendatabase()
        }
    }

    @Throws(IOException::class)
    fun createdatabase() {
        val dbexist = checkdatabase()
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            try {
                copydatabase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }

        }
    }

    private fun checkdatabase(): Boolean {
        var checkdb = false
        try {
            val myPath = DB_PATH + DB_NAME
            val dbfile = File(myPath)
            checkdb = dbfile.exists()
        } catch (e: SQLiteException) {
            println("Database doesn't exist")
        }

        return checkdb
    }

    @Throws(IOException::class)
    private fun copydatabase() {
        //Open your local db as the input stream
        val myinput = mycontext.assets.open("databases/" + DB_NAME)

        //Open the empty db as the output stream
        val directory = File(DB_PATH)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, DB_NAME)
        file.createNewFile()


        val myoutput = FileOutputStream(file)

        // transfer byte to inputfile to outputfile
        val buffer = ByteArray(1024)
        var length: Int
        while (true) {
            length = myinput.read(buffer)
            if (length <= 0)
                break
            myoutput.write(buffer, 0, length)
        }

        //Close the streams
        myoutput.flush()
        myoutput.close()
        myinput.close()
    }

    @Throws(SQLException::class)
    fun opendatabase() {
        //Open the database
        val mypath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    fun getOneSchedule(number: String, character: String): String {
        var response = ""
        val selectOneQuery = "SELECT * FROM $TABLE_NAME WHERE $NUMBER = \"$number\" AND $CHARACTER = \"$character\""

        val cursor = myDataBase?.rawQuery(selectOneQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val state = cursor.getString(cursor.getColumnIndex(STATE))
                val county = cursor.getString(cursor.getColumnIndex(COUNTY))

                response = "$state-$county"
            }
        }

        if(response.isEmpty()){
            var secondResponse = ""
            val secondSelectOneQuery = "SELECT * FROM $TABLE_NAME WHERE $NUMBER = \"$number\""

            val secondCursor = myDataBase?.rawQuery(secondSelectOneQuery, null)
            if (secondCursor != null) {
                if (secondCursor.moveToFirst()) {
                    val state = secondCursor.getString(secondCursor.getColumnIndex(STATE))
                    val county = secondCursor.getString(secondCursor.getColumnIndex(COUNTY))

                    secondResponse = "$state-$county"
                }
            }

            if(secondResponse.isNotEmpty()) {
                cursor?.close()
                myDataBase?.close()
                return secondResponse
            }
        }

        cursor?.close()
        myDataBase?.close()
        return response
    }

}