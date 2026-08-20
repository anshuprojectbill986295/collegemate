package com.anshu.collegemate.ui.ViewModel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.AndroidViewModel
import com.anshu.collegemate.Data.Injections.FireStorageInjection
import com.anshu.collegemate.Data.Model.AssignmentTest.UploadResult
import com.anshu.collegemate.ui.View.Others.CustomizedButtons.activeSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.io.FileOutputStream

class UploadImgPDFVM(application: Application): AndroidViewModel(application) {
    private val _result = MutableStateFlow<UploadResult?>(null)
    val result: StateFlow<UploadResult?> = _result
    var progress= 0.0
    private val _uploadButtonClicked = MutableStateFlow<Boolean>(false)
    //val uploadButtonClicked: StateFlow<Boolean> = _uploadButtonClicked

    fun upload(uri: Uri, type: activeSource) {
        // Intercept URI for optimization if it's an image
        val finalUri = getOptimizedUri(uri)
        
        val fileRef = FireStorageInjection.getStorageRef().child("files/${System.currentTimeMillis()}")

        _result.value = UploadResult.Uploading(0.0f)
        Log.d("Set to Uploading", "${_result.value.toString()}")

        val uploadTask = fileRef.putFile(finalUri)

        uploadTask.addOnProgressListener { snapshot ->
            Log.d("Progress", "")
            progress = (100.0 * snapshot.bytesTransferred / snapshot.totalByteCount)
            _result.value = UploadResult.Uploading(progress.toFloat())
        }
        uploadTask.addOnFailureListener { error ->
            Log.d("ErrorUpload", "${error.toString()}")
            _result.value = UploadResult.Error(error.toString())
            _uploadButtonClicked.value = false
        }
        uploadTask.addOnSuccessListener {
            Log.d("SuccessUpload", "")
            fileRef.downloadUrl.addOnSuccessListener { downloadURL ->
                val downloadLink = downloadURL.toString()
                _result.value = UploadResult.Success(downloadLink, type)
            }
        }
    }

    /**
     * Optimizes images (resizing, orientation correction, compression).
     * Returns a new URI pointing to a temporary optimized file, or the original URI if optimization is not applicable/fails.
     */
    private fun getOptimizedUri(uri: Uri): Uri {
        val context = getApplication<Application>()
        val contentResolver = context.contentResolver
        
        // 1. MIME detection to determine if it's an image
        val mimeType = contentResolver.getType(uri)
        val isImage = mimeType?.startsWith("image/") == true || 
                      (mimeType == null && isImageExtension(uri))
        
        if (!isImage) return uri

        return try {
            // 2. Memory safety: Get original dimensions without loading full bitmap
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            }

            if (options.outWidth <= 0 || options.outHeight <= 0) return uri

            // 3. Calculate inSampleSize to downsample large images during decode
            options.inSampleSize = calculateInSampleSize(options, 2048, 2048)
            options.inJustDecodeBounds = false

            // 4. Decode bitmap with memory-safe sample size
            var bitmap = contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            } ?: return uri

            // 5. Handle EXIF Orientation
            bitmap = rotateImageIfRequired(bitmap, uri)

            // 6. Max dimension enforcement (2048px) and Aspect Ratio preservation
            val scale = Math.min(2048f / bitmap.width, 2048f / bitmap.height)
            if (scale < 1f) {
                val scaledBitmap = Bitmap.createScaledBitmap(
                    bitmap, 
                    (bitmap.width * scale).toInt(), 
                    (bitmap.height * scale).toInt(), 
                    true
                )
                if (scaledBitmap != bitmap) {
                    bitmap.recycle()
                    bitmap = scaledBitmap
                }
            }

            // 7. Compression: JPEG quality 80 and save to cacheDir
            val tempFile = File(context.cacheDir, "optimized_${System.currentTimeMillis()}.jpg")
            FileOutputStream(tempFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }
            bitmap.recycle()
            
            Uri.fromFile(tempFile)
        } catch (e: Exception) {
            Log.e("UploadImgPDFVM", "Image optimization failed: ${e.message}", e)
            uri // Fallback to original URI on failure
        }
    }

    private fun isImageExtension(uri: Uri): Boolean {
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        return extension?.lowercase() in listOf("jpg", "jpeg", "png", "webp", "bmp")
    }

    /**
     * Standard implementation for calculating inSampleSize for memory-efficient bitmap decoding.
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /**
     * Reads EXIF data and rotates the bitmap if necessary.
     */
    private fun rotateImageIfRequired(bitmap: Bitmap, uri: Uri): Bitmap {
        val contentResolver = getApplication<Application>().contentResolver
        return try {
            val orientation = contentResolver.openInputStream(uri)?.use { inputStream ->
                val ei = ExifInterface(inputStream)
                ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            } ?: ExifInterface.ORIENTATION_NORMAL
            
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                else -> bitmap
            }
        } catch (e: Exception) {
            Log.e("UploadImgPDFVM", "Rotation handling failed: ${e.message}")
            bitmap
        }
    }

    private fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        if (rotatedImg != img) img.recycle()
        return rotatedImg
    }

    fun bitmapToFile(context:Context,bitmap: Bitmap): File{
        val file = File(context.cacheDir,"temp_file_${System.currentTimeMillis()}")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream)
        outputStream.close()
        return file
    }

    fun resetUploadState(){
        _result.value= UploadResult.Idle
    }
}
