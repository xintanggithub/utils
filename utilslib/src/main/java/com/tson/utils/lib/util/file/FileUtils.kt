package com.tson.utils.lib.util.file

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import com.tson.utils.lib.util.UtilsConfig
import com.tson.utils.lib.util.string.StringUtils
import java.io.*
import kotlin.experimental.and

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 读取文件工具类
 */
class FileUtils {

    companion object {

        private val TAG = "FileUtils"

        /**
         * The constant SDCard.
         */
        private var SDCard = Environment.getExternalStorageDirectory().absolutePath

        /**
         * 返回数据目录
         *
         * @return data path
         */
        val dataPath: String
            @SuppressLint("SdCardPath")
            get() = if (checkSDCard()) {
                "$SDCard/Android/data/$packageName/"
            } else {
                "/data/data/$packageName/"
            }

        /**
         * Gets package name.
         *
         * @return the package name
         */
        private val packageName: String
            get() = UtilsConfig.sContext.packageName ?: "com.tson.utils.lib.util"

        /**
         * Convert byte[] to hex string.将byte转换成int，
         * 然后利用Integer.toHexString(int)来转换成16进制字符串。
         *
         * @param src byte[] data
         * @return hex string
         */
        fun bytesToHexString(src: ByteArray?): String? {
            val stringBuilder = StringBuilder("")
            if (src == null || src.isEmpty()) {
                return null
            }
            for (i in src.indices) {
                val v = src[i] and 0xFF.toByte()
                val hv = Integer.toHexString(v.toInt())
                if (hv.length < 2) {
                    stringBuilder.append(0)
                }
                stringBuilder.append(hv)
            }
            return stringBuilder.toString()
        }

        /**
         * 根据文件名称和路径，获取sd卡中的文件，以File形式返回byte
         *
         * @param fileName the file name
         * @param folder   the folder
         * @return the file
         * @throws IOException the io exception
         */
        @Throws(IOException::class)
        fun getFile(fileName: String, folder: String): File? {
            val state = Environment.getExternalStorageState()
            if (state == Environment.MEDIA_MOUNTED) {
                val pathFile = File(Environment.getExternalStorageDirectory().toString() + folder)
                // && !pathFile .isDirectory()
                if (!pathFile.exists()) {
                    pathFile.mkdirs()
                }
                return File(pathFile, fileName)
            }
            return null
        }

        /**
         * 根据文件名称和路径，获取sd卡中的文件，判断文件是否存在，存在返回true
         *
         * @param fileName the file name
         * @param folder   the folder
         * @return the boolean
         */
        fun checkFile(fileName: String, folder: String): Boolean {
            try {
                val targetFile = getFile(fileName, folder)

                return targetFile!!.exists()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * 根据Uri返回文件绝对路径
         * 兼容了file:///开头的 和 content://开头的情况
         *
         * @param context the context
         * @param uri     the uri
         * @return the real file path from uri
         */
        fun getRealFilePathFromUri(context: Context, uri: Uri): String {
            val scheme = uri.scheme
            var data: String = ""
            if (scheme == null) {
                data = uri.path ?: ""
            } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
                data = uri.path ?: ""
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
                val cursor = context.contentResolver.query(
                    uri, arrayOf(
                        MediaStore
                            .Images.ImageColumns.DATA
                    ), null, null, null
                )
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        if (index > -1) {
                            data = cursor.getString(index)
                        }
                    }
                    cursor.close()
                }
            }
            return data
        }

        /**
         * 检查文件是否存在
         *
         * @param dirPath the dir path
         * @return the string
         */
        fun checkDirPath(dirPath: String): String {
            if (TextUtils.isEmpty(dirPath)) {
                return ""
            }
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dirPath
        }

        /**
         * Copy file.
         *
         * @param sourcefile the sourcefile
         * @param targetFile the target file
         */
        fun copyFile(sourcefile: File, targetFile: File) {
            var input: FileInputStream? = null
            var inbuff: BufferedInputStream? = null
            var out: FileOutputStream? = null
            var outbuff: BufferedOutputStream? = null

            try {

                input = FileInputStream(sourcefile)
                inbuff = BufferedInputStream(input)

                out = FileOutputStream(targetFile)
                outbuff = BufferedOutputStream(out)

                val b = ByteArray(1024 * 5)
                var len: Int
                while ((inbuff.read(b).also { len = it }) != -1) {
                    outbuff.write(b, 0, len)
                }
                outbuff.flush()
            } catch (ex: Exception) {
            } finally {
                try {
                    inbuff?.close()
                    outbuff?.close()
                    out?.close()
                    input?.close()
                } catch (ex: Exception) {

                }

            }
        }

        /**
         * 保存图片到本机
         *
         * @param context            context
         * @param fileName           文件名
         * @param file               file
         * @param saveResultCallback 保存结果callback
         */
        fun saveImage(
            context: Context, fileName: String, file: File,
            saveResultCallback: SaveResultCallback
        ) {
            Thread(Runnable {
                val appDir = File(Environment.getExternalStorageDirectory(), "qiming")
                if (!appDir.exists()) {
                    appDir.mkdir()
                }
                var saveFileName = "qiming_pic"
                if (fileName.contains(".png") || fileName.contains(".gif")) {
                    val fileFormat = fileName.substring(fileName.lastIndexOf("."))
                    saveFileName = StringUtils.md5Upper("qiming_pic$fileName") + fileFormat
                } else {
                    saveFileName = StringUtils.md5Upper("qiming_pic$fileName") + ".png"
                }
                saveFileName = saveFileName.substring(20)//取前20位作为SaveName
                val savefile = File(appDir, saveFileName)
                try {
                    val `is` = FileInputStream(file)
                    val fos = FileOutputStream(savefile)
                    val buffer = ByteArray(1024 * 1024)//1M缓冲区
                    var count: Int
                    while ((`is`.read(buffer).also { count = it }) > 0) {
                        fos.write(buffer, 0, count)
                    }
                    fos.close()
                    `is`.close()
                    saveResultCallback.onSavedSuccess()
                } catch (e: FileNotFoundException) {
                    saveResultCallback.onSavedFailed()
                    e.printStackTrace()
                } catch (e: IOException) {
                    saveResultCallback.onSavedFailed()
                    e.printStackTrace()
                }

                //保存图片后发送广播通知更新数据库
                val uri = Uri.fromFile(savefile)
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            }).start()
        }

        /**
         * 保存Bitmap到本机
         *
         * @param context            context
         * @param fileName           bitmap文件名
         * @param bmp                bitmap
         * @param saveResultCallback 保存结果callback
         */
        fun saveBitmap(
            context: Context, fileName: String, bmp: Bitmap,
            saveResultCallback: SaveResultCallback
        ) {
            Thread(Runnable {
                val appDir = File(Environment.getExternalStorageDirectory(), "utils")
                if (!appDir.exists()) {
                    appDir.mkdir()
                }
                //                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置以当前时间格式为图片名称
                var saveFileName = StringUtils.md5Upper("utils_pic$fileName") + ".png"
                saveFileName = saveFileName.substring(20)//取前20位作为SaveName
                val file = File(appDir, saveFileName)
                try {
                    val fos = FileOutputStream(file)
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    fos.flush()
                    fos.close()
                    saveResultCallback.onSavedSuccess()
                } catch (e: FileNotFoundException) {
                    saveResultCallback.onSavedFailed()
                    e.printStackTrace()
                } catch (e: IOException) {
                    saveResultCallback.onSavedFailed()
                    e.printStackTrace()
                }

                //保存图片后发送广播通知更新数据库
                val uri = Uri.fromFile(file)
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            }).start()
        }

        /**
         * The interface Save result callback.
         */
        interface SaveResultCallback {
            /**
             * On saved success.
             */
            fun onSavedSuccess()

            /**
             * On saved failed.
             */
            fun onSavedFailed()
        }

        /**
         * 检测SDCard是否可用
         *
         * @return boolean
         */
        fun checkSDCard(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 返回File 如果没有就创建
         *
         * @param path the path
         * @return directory directory
         */
        fun getDirectory(path: String): File {
            val appDir = File(path)
            if (!appDir.exists()) {
                appDir.mkdirs()
            }
            return appDir
        }

        /**
         * 删除文件夹
         *
         * @param sPath 路径
         * @return boolean
         */
        fun deleteDirectory(sPath: String): Boolean {
            var sPath = sPath
            var flag = false
            // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
            if (!sPath.endsWith(File.separator)) {
                sPath += File.separator
            }
            val dirFile = File(sPath)
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if (!dirFile.exists() || !dirFile.isDirectory) {
                return false
            }
            flag = true
            // 删除文件夹下的所有文件(包括子目录)
            val files = dirFile.listFiles()
            for (i in files.indices) {
                // 删除子文件
                if (files[i].isFile) {
                    flag = deleteFile(files[i].absolutePath)
                    if (!flag)
                        break
                } // 删除子目录
                else {
                    flag = deleteDirectory(files[i].absolutePath)
                    if (!flag)
                        break
                }
            }
            if (!flag)
                return false
            // 删除当前目录
            return dirFile.delete()
        }

        /**
         * Create folder.
         *
         * @param path the path
         */
        fun createFolder(path: String) {
            var path = path
            path = separatorReplace(path)
            val folder = File(path)
            if (folder.isDirectory) {
                return
            } else if (folder.isFile) {
                deleteFile(path)
            }
            folder.mkdirs()
        }

        /**
         * Create file file.
         *
         * @param path the path
         * @return the file
         * @throws Exception the exception
         */
        @Throws(Exception::class)
        fun createFile(path: String): File {
            var path = path
            path = separatorReplace(path)
            val file = File(path)
            if (file.isFile) {
                return file
            } else if (file.isDirectory) {
                deleteFolder(path)
            }
            return createFile(file)
        }

        /**
         * Create file file.
         *
         * @param file the file
         * @return the file
         * @throws Exception the exception
         */
        @Throws(Exception::class)
        fun createFile(file: File): File {
            createParentFolder(file)
            if (!file.createNewFile()) {
                throw Exception("create file failure!")
            }
            return file
        }

        /**
         * Delete folder.
         *
         * @param path the path
         * @throws Exception the exception
         */
        @Throws(Exception::class)
        fun deleteFolder(path: String) {
            var path = path
            path = separatorReplace(path)
            val folder = getFolder(path)
            val files = folder.listFiles()
            for (file in files) {
                if (file.isDirectory) {
                    deleteFolder(file.absolutePath)
                } else if (file.isFile) {
                    deleteFile(file.absolutePath)
                }
            }
            folder.delete()
        }

        /**
         * Gets folder.
         *
         * @param path the path
         * @return the folder
         * @throws FileNotFoundException the file not found exception
         */
        @Throws(FileNotFoundException::class)
        fun getFolder(path: String): File {
            var path = path
            path = separatorReplace(path)
            val folder = File(path)
            if (!folder.isDirectory) {
                throw FileNotFoundException("folder not found!")
            }
            return folder
        }

        /**
         * Gets file.
         *
         * @param path the path
         * @return the file
         */
        fun getFile(path: String): File? {
            var path = path
            path = separatorReplace(path)
            val file = File(path)
            return if (!file.isFile) {
                null
            } else file
        }

        /**
         * Separator replace string.
         *
         * @param path the path
         * @return the string
         */
        fun separatorReplace(path: String): String {
            return path.replace("\\", "/")
        }

        /**
         * 删除文件
         *
         * @param sPath 路径
         * @return boolean
         */
        fun deleteFile(sPath: String): Boolean {
            var flag = false
            val file = File(sPath)
            // 路径为文件且不为空则进行删除
            if (file.isFile && file.exists()) {
                file.delete()
                flag = true
            }
            return flag
        }

        /**
         * Create parent folder.
         *
         * @param file the file
         * @throws Exception the exception
         */
        @Throws(Exception::class)
        fun createParentFolder(file: File) {
            if (!file.parentFile.exists()) {
                if (!file.parentFile.mkdirs()) {
                    throw Exception("create parent directory failure!")
                }
            }
        }


        /**
         * 写入文件
         *
         * @param path    the path
         * @param content the content
         * @return 1 : 写入成功 0: 写入失败
         */
        fun writeFile(path: String, content: String): Int {
            try {
                val f = File(path)
                if (f.exists()) {
                    f.delete()
                } else {
                    createFolder(path.substring(0, path.lastIndexOf("/")))
                }
                if (f.createNewFile()) {
                    val utput = FileOutputStream(f)
                    utput.write(content.toByteArray())
                    utput.close()
                } else {
                    return 0
                }
            } catch (e: Exception) {
                return 0
            }

            return 1
        }

        /**
         * 写入文件
         *
         * @param path the path
         * @param in   the in
         * @return 1 : 写入成功 0: 写入失败
         */
        fun writeFile(path: String, `in`: InputStream?): Int {
            try {
                if (`in` == null)
                    return 0
                val f = File(path)
                if (f.exists()) {
                    f.delete()
                }
                if (f.createNewFile()) {
                    val utput = FileOutputStream(f)
                    val buffer = ByteArray(1024)
                    var count: Int
                    while ((`in`.read(buffer).also { count = it }) != -1) {
                        utput.write(buffer, 0, count)
                        utput.flush()
                    }
                    utput.close()
                    `in`.close()
                } else {
                    return 0
                }
            } catch (e: Exception) {
                return 0
            }

            return 1
        }

        /**
         * 复制文件
         *
         * @param is the is
         * @param os the os
         * @return 1 : 写入成功 0: 写入失败
         * @throws IOException
         */
        fun copyStream(`is`: InputStream, os: OutputStream): Int {
            try {
                val buffer_size = 1024
                val bytes = ByteArray(buffer_size)
                while (true) {
                    val count = `is`.read(bytes, 0, buffer_size)
                    if (count == -1) {
                        break
                    }
                    os.write(bytes, 0, count)
                }
                return 1
            } catch (e: IOException) {
                return 0
            } finally {
                try {
                    `is`.close()
                    os.close()
                } catch (e: IOException) {
                }

            }
        }

        /**
         * 读取序列化对象
         *
         * @param filePath the file path
         * @return object
         */
        fun readerObject(filePath: String): Any? {
            val oo: Any
            try {
                val fis = FileInputStream(filePath)
                val objectIn = ObjectInputStream(fis)
                oo = objectIn.readObject()
                objectIn.close()
                fis.close()
            } catch (e: Exception) {
                return null
            }

            return oo
        }

        /**
         * 写入序列化对象
         *
         * @param path   the path
         * @param object the object
         * @return int
         */
        fun writeObject(path: String, `object`: Any): Int {
            try {
                val f = File(path)
                if (f.exists()) {
                    f.delete()
                }
                if (f.createNewFile()) {
                    val utput = FileOutputStream(f)
                    val objOut = ObjectOutputStream(utput)
                    objOut.writeObject(`object`)
                    objOut.close()
                    utput.close()
                } else {
                    return 0
                }
            } catch (e: Exception) {
                return 0
            }

            return 1
        }


        /**
         * Bitmap 转换成 byte[]
         *
         * @param bm the bm
         * @return byte [ ]
         */
        fun Bitmap2Bytes(bm: Bitmap): ByteArray {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }

        /**
         * 保存图片
         *
         * @param path   the path
         * @param bitmap the bitmap
         */
        fun saveBitmap(path: String, bitmap: Bitmap) {
            try {
                val f = File(path)
                if (f.exists())
                    f.delete()
                f.createNewFile()
                var fOut: FileOutputStream? = null
                fOut = FileOutputStream(f)
                val bos = BufferedOutputStream(fOut)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * Gets file name.
         *
         * @param filePath the file path
         * @return the file name
         */
        fun getFileName(filePath: String): String {
            return filePath.substring(0, filePath.lastIndexOf("."))
        }

        /**
         * Gets folder size.
         *
         * @param file the file
         * @return the folder size
         */
        fun getFolderSize(file: File): Long {
            var size: Long = 0
            try {
                val fileList = file.listFiles()
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory) {
                        size += getFolderSize(fileList[i])
                    } else {
                        size += fileList[i].length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return size
        }

        /**
         * 复制文件
         *
         * @param oldPath 需要被复制文件的路径  如   D:/a/b/c.txt
         * @param newPath 需要将文件复制过来的目标路径 如  C:/e/f/c.txt
         */
        fun copyFile(oldPath: String, newPath: String) {
            try {
                var byteSum = 0
                var byteRead = 0
                val oldFile = File(oldPath)
                //文件存在时
                if (oldFile.exists()) {
                    //读入原文件
                    val inStream = FileInputStream(oldPath)
                    val fs = FileOutputStream(newPath)
                    val buffer = ByteArray(1444)
                    val length: Int
                    while ((inStream.read(buffer).also { byteRead = it }) != -1) {
                        //字节数 文件大小
                        byteSum += byteRead
                        println(byteSum)
                        fs.write(buffer, 0, byteRead)
                    }
                    inStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}