package de.dilello.www.snappypark.services

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by timothy on 8/22/17.
 */
class FileService {
    companion object {
        fun readHtmlFile(filename: String, context: Context): String {
            val assets = context.assets
            val inputFile = assets.open(filename)
            val fullText = inputFile.reader().readText()

            Log.d("FILE SERVICE", fullText)
            return fullText
        }
    }
}