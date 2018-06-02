package com.piciu.bricklist

import android.app.Application
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_new_project.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.concurrent.thread


class Project(var id: Int = 0, val name: String, var brickList: ArrayList<Brick>, var archived: Boolean = false, val lastModified: Date){
    constructor(name: String, legoId: Int) : this(0, name, bricksFromXML(legoId), false, Date())

    companion object {
        fun bricksFromXML(legoId: Int): ArrayList<Brick> {
            val brickList = ArrayList<Brick>()
            val x = thread {
                val urlPath = "${Globals.LEGOSETURL}$legoId.xml"

                val url = URL(urlPath)

                val urlConnection = url.openConnection() as HttpURLConnection

                urlConnection.connect()

                val path = File(Globals.XMLPath)
                if(!path.exists()){
                    path.mkdir()
                }
                val outDir = File(path, "Input")
                if(!outDir.exists()){
                    outDir.mkdir()
                }


                val file = File(outDir, "$legoId.xml")
                if(!file.exists()){
                    file.createNewFile()
                }

                val fileOutput = FileOutputStream(file)
                val inputStream = urlConnection.inputStream

                val buffer = ByteArray(1024)
                var bufferLength = inputStream.read(buffer) //used to store a temporary size of the buffer

                //now, read through the input buffer and write the contents to the file
                while (bufferLength > 0) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength)
                    //add up the size so we know how much is downloaded
                    bufferLength = inputStream.read(buffer)
                }
                //close the output stream when done
                fileOutput.close()

                val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                xmlDoc.documentElement.normalize()

                val itemList: NodeList = xmlDoc.getElementsByTagName("ITEM")

                val dataBaseHelper = DataBaseHelper(CustomApplication.context!!)

                for(i in 0 until itemList.length)
                {
                    var itemNode = itemList.item(i)

                    if (itemNode.getNodeType() === Node.ELEMENT_NODE) {

                        val elem = itemNode as Element

                        val mMap = mutableMapOf<String, String>()


                        for(j in 0 until elem.attributes.length)
                        {
                            if(!mMap.containsKey(elem.attributes.item(j).nodeName)) {
                                mMap[elem.attributes.item(j).nodeName] = elem.attributes.item(j).nodeValue
                            }
                        }

                        val typeId = dataBaseHelper.getItemTypeId(elem.getElementsByTagName("ITEMTYPE").item(0).textContent)
                        val itemID = elem.getElementsByTagName("ITEMID").item(0).textContent
                        val qty = elem.getElementsByTagName("QTY").item(0).textContent.toInt()
                        val color = elem.getElementsByTagName("COLOR").item(0).textContent.toInt()
                        val extra = elem.getElementsByTagName("EXTRA").item(0).textContent
                        val brick = Brick(typeId, itemID, qty, 0, color, extra)
                        brickList.add(brick)
                    }
                }
            }

            x.join()

            return brickList
        }
    }



    fun writeXML(): String? {
        val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc: Document = docBuilder.newDocument()

        val rootElement: Element = doc.createElement("INVENTORY")

        for (b: Brick in brickList){
            if(b.quantityInStore < b.quantityInSet) {
                val itemElement: Element = doc.createElement("ITEM")
                val itemTypeElement: Element = doc.createElement("ITEMTYPE")
                itemTypeElement.appendChild(doc.createTextNode(b.typeId.toString()))
                val itemIdElement: Element = doc.createElement("ITEMID")
                itemIdElement.appendChild(doc.createTextNode(b.itemId.toString()))
                val colorElement: Element = doc.createElement("COLOR")
                colorElement.appendChild(doc.createTextNode(b.colorId.toString()))
                val qTyFilledElement: Element = doc.createElement("QTYFILLED")
                qTyFilledElement.appendChild(doc.createTextNode("${b.quantityInSet - b.quantityInStore}"))
                itemElement.appendChild(itemTypeElement)
                itemElement.appendChild(itemIdElement)
                itemElement.appendChild(colorElement)
                itemElement.appendChild(qTyFilledElement)
                rootElement.appendChild(itemElement)
            }
        }
        doc.appendChild(rootElement)

        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2")
        try {
            val path = File(Globals.XMLPath)
            if(!path.exists()){
                path.mkdir()
            }
            val outDir = File(path, "Output")
            if(!outDir.exists()){
                outDir.mkdir()
            }


            val file = File(outDir, "$name.xml")
            if(!file.exists()){
                file.createNewFile()
            }
            transformer.transform(DOMSource(doc), StreamResult(file))
            return file.absolutePath
        }catch (e:Exception){
            return null
        }
    }
}
