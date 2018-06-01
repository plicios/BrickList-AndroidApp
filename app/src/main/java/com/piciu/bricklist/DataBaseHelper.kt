package com.piciu.bricklist

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.FileOutputStream
import java.io.IOException


class DataBaseHelper (private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_PATH = "/data/data/com.piciu.bricklist/databases/"//${context.filesDir.path}
        private const val DB_VERSION = 1
        private const val DB_NAME = "BrickList.db"
        private const val TABLE_PROJECTS = "Projects"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "Name"
        private const val COLUMN_BRICKLIST = "BrickList"
        private const val COLUMN_ARCHIVED = "Archived"
    }

    fun getProjects(): ArrayList<Project>{
        val query = "SELECT * FROM $TABLE_PROJECTS"

        val dataBase: SQLiteDatabase = openDataBase()

        val cursor = dataBase.rawQuery(query, null)


        val projectList = ArrayList<Project>()

        while (cursor.moveToNext()){
            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val brickListString = cursor.getString(2)
            val archived = Integer.parseInt(cursor.getString(3)) == 1

            val mapper = jacksonObjectMapper()

            val brickList: ArrayList<Brick> = mapper.readValue(brickListString)

            val project = Project(name, id, brickList, archived)
            projectList.add(project)
        }

        cursor.close()
        dataBase.close()

        return projectList
    }

    fun addProject(project: Project){
        val values = ContentValues()

        val mapper = jacksonObjectMapper()

        val brickListString: String = mapper.writeValueAsString(project.brickList)

        values.put(COLUMN_ID, project.legoProjectId)
        values.put(COLUMN_NAME, project.name)
        values.put(COLUMN_BRICKLIST, brickListString)
        values.put(COLUMN_ARCHIVED, if(project.archived) 1 else 0)

        val dataBase: SQLiteDatabase = openDataBase()

        dataBase.insert(TABLE_PROJECTS, null,values)

        dataBase.close()
    }

    fun changeProjectValues(project: Project){
        deleteProject(project)
        addProject(project)
    }

    fun deleteProject(project: Project){
        val dataBase: SQLiteDatabase = openDataBase()

        dataBase.delete(TABLE_PROJECTS, "$COLUMN_ID = ?", arrayOf(project.legoProjectId.toString()))

        dataBase.close()
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()

        if (!dbExist) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.readableDatabase

            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null

        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            //database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close()
        }

        return checkDB != null
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    @Throws(IOException::class)
    private fun copyDataBase() {

        //Open your local db as the input stream
        val myInput = context.assets.open(DB_NAME)

        // Path to the just created empty db
        val outFileName = DB_PATH + DB_NAME

        //Open the empty db as the output stream
        val myOutput = FileOutputStream(outFileName)

        //transfer bytes from the inputfile to the outputfile
        val buffer = ByteArray(1024)
        var length: Int = myInput.read(buffer)
        while (length > 0) {
            myOutput.write(buffer, 0, length)
            length = myInput.read(buffer)
        }

        //Close the streams
        myOutput.flush()
        myOutput.close()
        myInput.close()

    }

    @Throws(SQLException::class)
    fun openDataBase() : SQLiteDatabase{
        createDataBase()
        //Open the database
        val myPath = DB_PATH + DB_NAME
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}