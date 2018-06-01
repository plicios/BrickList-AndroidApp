package com.piciu.bricklist

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class Project(val name: String, val legoProjectId: Int, var brickList: ArrayList<Brick>, var archived: Boolean = false){
    fun writeXML(): String? {
        val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc: Document = docBuilder.newDocument()

        val rootElement: Element = doc.createElement("INVENTORY")

        for (b: Brick in brickList){
            val itemElement: Element = doc.createElement("ITEM")
            val itemTypeElement: Element = doc.createElement("ITEMTYPE")
            itemTypeElement.appendChild(doc.createTextNode(b.name))
            val itemIdElement: Element = doc.createElement("ITEMID")
            itemIdElement.appendChild(doc.createTextNode("${b.id}"))
            val colorElement: Element = doc.createElement("COLOR")
            colorElement.appendChild(doc.createTextNode(b.color))
            val qTyFilledElement: Element = doc.createElement("QTYFILLED")
            qTyFilledElement.appendChild(doc.createTextNode("${b.amountNeeded - b.currentAmount}"))
            itemElement.appendChild(itemTypeElement)
            itemElement.appendChild(itemIdElement)
            itemElement.appendChild(colorElement)
            itemElement.appendChild(qTyFilledElement)
            rootElement.appendChild(itemElement)
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
