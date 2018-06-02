package com.piciu.bricklist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class DataBaseHelper (private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        @SuppressLint("SdCardPath")
        private const val DB_PATH = "/data/data/com.piciu.bricklist/databases/"
        private const val DB_VERSION = 1
        private const val DB_NAME = "BrickList.db"
        private const val TABLE_PROJECTS = "Inventories"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "Name"
        private const val COLUMN_ARCHIVED = "Active"
        private const val COLUMN_LAST_ACCESSED = "LastAccessed"

        private const val TABLE_BRICKS = "InventoriesParts"
        private const val COLUMN_INVENTORY_ID = "InventoryID"
        private const val COLUMN_TYPE_ID = "TypeID"
        private const val COLUMN_ITEM_ID = "ItemID"
        private const val COLUMN_QUANTITY_IN_SET = "QuantityInSet"
        private const val COLUMN_QUANTITY_IN_STORE = "QuantityInStore"
        private const val COLUMN_COLOR_ID = "ColorID"
        private const val COLUMN_EXTRA = "Extra"

        private const val TABLE_ITEM_TYPES = "ItemTypes"
        private const val COLUMN_CODE = "Code"

        private const val TABLE_CODES = "Codes"
        private const val COLUMN_IMAGE = "Image"

        private const val TABLE_OLD_IMAGES = "OldImages"
        private const val TABLE_COLORS = "Colors"
    }

    fun getItemTypeId(type: String): Int{
        val dataBase = openDataBase()

        val query = "SELECT $COLUMN_ID FROM $TABLE_ITEM_TYPES WHERE $COLUMN_CODE = '$type'"

        val cursor = dataBase.rawQuery(query, null)

        var typeId = 1
        if(cursor.moveToFirst()) {
            typeId = cursor.getString(0).toInt()
        }

        cursor.close()
        dataBase.close()
        return typeId
    }

    fun getColorName(color: Int): String{
        val dataBase = openDataBase()

        val query = "SELECT $COLUMN_NAME FROM $TABLE_COLORS WHERE $COLUMN_CODE = $color"

        val cursor = dataBase.rawQuery(query, null)

        var colorName = ""
        if(cursor.moveToFirst()) {
            colorName = cursor.getString(0)
        }

        cursor.close()
        dataBase.close()
        return colorName
    }

    fun insertImageOld(ItemID: String, color: Int, img: ByteArray) {
        val dataBase = openDataBase()
        val values = ContentValues()
        values.put(COLUMN_IMAGE, img)
        values.put(COLUMN_COLOR_ID, color)
        values.put(COLUMN_ITEM_ID, ItemID)
        try {
            dataBase.insertOrThrow(TABLE_OLD_IMAGES, null, values)
        }catch (e: Exception){
            val ex = e.toString()
        }
        dataBase.close()
    }

    fun updateImage(code: Int, img: ByteArray) {
        val dataBase = openDataBase()
        val values = ContentValues()
        values.put(COLUMN_IMAGE, img)
        dataBase.update(TABLE_CODES, values, "$COLUMN_CODE = ?", arrayOf(code.toString()))
        dataBase.close()
    }

    fun getImageOld(ItemID: String, color: Int): ByteArray? {
        val dataBase = openDataBase()
        val cursor = dataBase.rawQuery("SELECT $COLUMN_IMAGE FROM $TABLE_OLD_IMAGES WHERE $COLUMN_ITEM_ID = '$ItemID' AND $COLUMN_COLOR_ID = $color", null)

        if (cursor.moveToFirst()) {
            val data = cursor.getBlob(0)

            cursor.close()
            return data
        }

        cursor.close()
        return null
    }

    fun getImage(ItemID: Int, color: Int): Image? {
        val dataBase = openDataBase()
        val cursor = dataBase.rawQuery("SELECT $COLUMN_CODE, $COLUMN_IMAGE FROM $TABLE_CODES WHERE $COLUMN_ITEM_ID = $ItemID AND $COLUMN_COLOR_ID = $color", null)

        if (cursor.moveToFirst()) {
            val image = Image(cursor.getInt(0), cursor.getBlob(1))

            cursor.close()
            return image
        }

        cursor.close()
        return null
    }

    fun getProjects(): ArrayList<Project>{
        val query = "SELECT * FROM $TABLE_PROJECTS"

        val dataBase: SQLiteDatabase = openDataBase()

        val cursor = dataBase.rawQuery(query, null)


        val projectList = ArrayList<Project>()

        while (cursor.moveToNext()){
            val id = cursor.getString(0).toInt()
            val name = cursor.getString(1)
            val archived = cursor.getString(2).toInt() == 0
            val lastModifiedInt: Long = cursor.getString(3).toLong()
            val lastModified = Date(lastModifiedInt)

            val brickQuery = "SELECT $COLUMN_TYPE_ID, $COLUMN_ITEM_ID, $COLUMN_QUANTITY_IN_SET, $COLUMN_QUANTITY_IN_STORE, $COLUMN_COLOR_ID, $COLUMN_EXTRA FROM $TABLE_BRICKS WHERE $COLUMN_INVENTORY_ID = $id"
            val brickCursor = dataBase.rawQuery(brickQuery, null)

            val brickList = ArrayList<Brick>()

            while (brickCursor.moveToNext()) {
                val typeId = brickCursor.getString(0).toInt()
                val itemId = brickCursor.getString(1)
                val quantityInSet = brickCursor.getString(2).toInt()
                val quantityInStore = brickCursor.getString(3).toInt()
                val colorId = brickCursor.getString(4).toInt()
                val extra = brickCursor.getString(5)

                val brick = Brick(typeId, itemId, quantityInSet, quantityInStore, colorId, extra)
                brickList.add(brick)
            }
            brickCursor.close()

            val project = Project(id, name, brickList, archived, lastModified)
            projectList.add(project)
        }

        cursor.close()
        dataBase.close()

        return projectList
    }

    fun addProject(project: Project){
        val values = ContentValues()

        val dateInTime = Date().time

        values.put(COLUMN_NAME, project.name)
        values.put(COLUMN_LAST_ACCESSED, dateInTime)
        values.put(COLUMN_ARCHIVED, if(project.archived) 0 else 1)
        val dataBase: SQLiteDatabase = openDataBase()

        dataBase.insert(TABLE_PROJECTS, null,values)

        val query = "SELECT $COLUMN_ID FROM $TABLE_PROJECTS WHERE $COLUMN_NAME = '${project.name}' AND $COLUMN_LAST_ACCESSED = $dateInTime AND $COLUMN_ARCHIVED = ${if(project.archived) 0 else 1}"

        val cursor = dataBase.rawQuery(query, null)

        if(cursor.moveToFirst()){
            project.id = Integer.parseInt(cursor.getString(0))
            for (brick: Brick in project.brickList){
                val brickValues = ContentValues()
                brickValues.put(COLUMN_INVENTORY_ID, project.id)
                brickValues.put(COLUMN_TYPE_ID, brick.typeId)
                brickValues.put(COLUMN_ITEM_ID, brick.itemId)
                brickValues.put(COLUMN_QUANTITY_IN_SET, brick.quantityInSet)
                brickValues.put(COLUMN_QUANTITY_IN_STORE, brick.quantityInStore)
                brickValues.put(COLUMN_COLOR_ID, brick.colorId)
                brickValues.put(COLUMN_EXTRA, brick.extra)

                dataBase.insert(TABLE_BRICKS, null, brickValues)
            }
        }
        else{
            Toast.makeText(context, "Nie udało się wstawić klocków do tabeli - projekt będzie pusty", Toast.LENGTH_LONG).show()
        }


        cursor.close()
        dataBase.close()
    }

    fun changeProjectValues(project: Project){
        deleteProject(project)
        addProject(project)
    }

    fun deleteProject(project: Project){
        val dataBase: SQLiteDatabase = openDataBase()

        dataBase.delete(TABLE_PROJECTS, "$COLUMN_ID = ?", arrayOf(project.id.toString()))
        dataBase.delete(TABLE_BRICKS,"$COLUMN_INVENTORY_ID = ?", arrayOf(project.id.toString()))

        dataBase.close()
    }

    @Throws(IOException::class)
    private fun createDataBase() {
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
    private fun openDataBase() : SQLiteDatabase{
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