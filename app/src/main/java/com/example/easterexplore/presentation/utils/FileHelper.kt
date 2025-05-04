package com.example.easterexplore.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

/**
 * A utility object for file operations.
 */
object FileHelper {

    /**
     * Saves a bitmap image to the internal storage of the app.
     *
     * @param context The context of the application.
     * @param bitmap The bitmap image to be saved.
     * @return The absolute path of the saved image file.
     */
    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
        val filename = "poi_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file.absolutePath
    }

}