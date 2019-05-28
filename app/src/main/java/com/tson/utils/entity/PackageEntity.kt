package com.tson.utils.entity


import java.io.Serializable

/**
 * The type Package entity.
 */
class PackageEntity : Serializable {

    /**
     * The Api level.
     */
    var apiLevel: Int = 0
    /**
     * The Version id.
     */
    var versionId: Int = 0
    /**
     * The Id.
     */
    var id: String? = null
    /**
     * The Name.
     */
    var name: String? = null
    /**
     * The Name en.
     */
    var name_en: String? = null
    /**
     * The Package name.
     */
    var packageName: String? = null
    /**
     * The Version name.
     */
    var versionName: String? = null
    /**
     * The Version code.
     */
    var versionCode: String? = null
    /**
     * The Url.
     */
    var url: String? = null
    /**
     * The Checksum.
     */
    var checksum: String? = null
    /**
     * The Size.
     */
    var size: Long = 0
    /**
     * The Icon.
     */
    var icon: String? = null

    //push
    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    var nickName: String? = null
    /**
     * Gets app info.
     *
     * @return the app info
     */
    /**
     * Sets app info.
     *
     * @param appInfo the app info
     */
    var appInfo: String? = null

    /**
     * PackageEntity
     * The Download status.
     */
    // added property
    var downloadStatus: Int = 0
    /**
     * Gets download size.
     *
     * @return the download size
     */
    /**
     * Sets download size.
     *
     * @param downloadSize the download size
     */
    var downloadSize: Long = 0
    /**
     * Gets total size.
     *
     * @return the total size
     */
    /**
     * Sets total size.
     *
     * @param totalSize the total size
     */
    var totalSize: Long = 0

    /**
     * Gets need update.
     *
     * @return the need update
     */
    /**
     * Sets need update.
     *
     * @param needUpdate the need update
     */
    var needUpdate: Int = 0
    /**
     * Gets installed.
     *
     * @return the installed
     */
    /**
     * Sets installed.
     *
     * @param installed the installed
     */
    var installed: Int = 0

    //local
    /**
     * Gets diff url.
     *
     * @return the diff url
     */
    /**
     * Sets diff url.
     *
     * @param diffUrl the diff url
     */
    var diffUrl: String? = null

    /**
     * The File downloader id.
     */
    //fileDownloader ID
    var fileDownloaderId = -1

    /**
     * Percent int.
     *
     * @return the int
     */
    fun percent(): Int {
        val total = totalSize
        val download = downloadSize
        val percent = (100.0 * downloadSize / totalSize).toInt()
        return if (percent > 100) {   // 临时规避
            100
        } else {
            percent
        }
    }

    /**
     * Format percent string.
     *
     * @return the string
     */
    fun formatPercent(): String {
        return if (totalSize == 0L) {
            "0 %"
        } else {
            percent().toString() + " %"
        }
    }
}
