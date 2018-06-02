package com.piciu.bricklist

import android.os.Environment

object Globals {
    const val NEW_PROJECT_REQUEST_CODE = 100
    const val EDIT_PROJECT_REQUEST_CODE = 101
    const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 102
    const val PREFS_FILENAME = "com.piciu"
    const val PROJECTS_LIST = "projectsList"
    const val ADDRESS_URL = "addressUrl"
    var LEGOSETURL = ""

    val XMLPath = Environment.getExternalStorageDirectory().absolutePath + "/" + "BrickList"

}
