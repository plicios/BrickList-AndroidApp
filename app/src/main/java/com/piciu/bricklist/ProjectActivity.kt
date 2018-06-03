package com.piciu.bricklist

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_project.*

class ProjectActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val dbHelper = DataBaseHelper(this)

        if(project!= null) {
            dbHelper.changeProjectValues(project!!)
        }

        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            Globals.WRITE_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if(!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val path: String? = project!!.writeXML()
                    if(path != null){
                        Toast.makeText(this, "Utworzono nowy plik w folderze: path", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Błąd generowania pliku", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this,"Nie mogę zapisać pliku bez pozwolenia",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private var adapter: BrickAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        try {
            projectName.text = project!!.name
            XMLGenerateButton.setOnClickListener{
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),Globals.WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
                }
                else {
                    val path: String? = project!!.writeXML()
                    if(path != null){
                        Toast.makeText(this, "Utworzono nowy plik w folderze: $path", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Błąd generowania pliku", Toast.LENGTH_LONG).show()
                    }

                }
            }
            adapter = BrickAdapter(this, project!!.brickList)
            val colorDictionary = HashMap<String, Int>()
            val itemIdList = ArrayList<String>()
            colorDictionary["Wszystkie"] = -1
            itemIdList.add("Wszystkie")
            for (brick: Brick in project!!.brickList){
                if(!colorDictionary.containsKey(brick.colorName)) {
                    colorDictionary[brick.colorName] = brick.colorId
                }
                if(!itemIdList.contains(brick.itemId)){
                    itemIdList.add(brick.itemId)
                }
            }
            val colorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colorDictionary.keys.toMutableList())
            val itemIdAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemIdList)

            colorFilter.adapter = colorAdapter
            colorFilter.setSelection(colorDictionary.keys.indexOf("Wszystkie"))
            colorFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    adapter!!.setColorFilter(0)
                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    val item = parent.getItemAtPosition(pos).toString()
                    val colorId = colorDictionary[item]
                    adapter!!.setColorFilter(colorId!!)
                }
            }

            itemIdFilter.adapter = itemIdAdapter
            itemIdFilter.setSelection(itemIdList.indexOf("Wszystkie"))
            itemIdFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    adapter!!.setItemIdFilter(null)
                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    val item = parent.getItemAtPosition(pos).toString()
                    if (item == "Wszystkie"){
                        adapter!!.setItemIdFilter(null)
                    }
                    else {
                        adapter!!.setItemIdFilter(item)
                    }
                }
            }


            brickList.adapter = adapter
        }catch (e: Exception){
            finish()
        }
    }

    companion object {
        var project: Project? = null
    }
}
