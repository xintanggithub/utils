package com.tson.utils.entity;


import java.io.Serializable;

/**
 * The type Package entity.
 */
public class PackageEntity implements Serializable {

    /**
     * The Api level.
     */
    public int apiLevel;
    /**
     * The Version id.
     */
    public int versionId;
    /**
     * The Id.
     */
    public String id;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Name en.
     */
    public String name_en;
    /**
     * The Package name.
     */
    public String packageName;
    /**
     * The Version name.
     */
    public String versionName;
    /**
     * The Version code.
     */
    public String versionCode;
    /**
     * The Url.
     */
    public String url;
    /**
     * The Checksum.
     */
    public String checksum;
    /**
     * The Size.
     */
    public long size;
    /**
     * The Icon.
     */
    public String icon;

    //push
    private String nickName;
    private String appInfo;

    /**
     * PackageEntity
     * The Download status.
     */
// added property
    public int downloadStatus;
    private long downloadSize;
    private long totalSize;

    private int needUpdate;
    private int installed;

    //local
    private String diffUrl;

    /**
     * The File downloader id.
     */
//fileDownloader ID
    public int fileDownloaderId = -1;

    /**
     * Gets app info.
     *
     * @return the app info
     */
    public String getAppInfo() {
        return appInfo;
    }

    /**
     * Sets app info.
     *
     * @param appInfo the app info
     */
    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Gets diff url.
     *
     * @return the diff url
     */
    public String getDiffUrl() {
        return diffUrl;
    }

    /**
     * Sets diff url.
     *
     * @param diffUrl the diff url
     */
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    /**
     * Gets download size.
     *
     * @return the download size
     */
    public long getDownloadSize() {
        return downloadSize;
    }

    /**
     * Gets total size.
     *
     * @return the total size
     */
    public long getTotalSize() {
        return totalSize;
    }

    /**
     * Sets download size.
     *
     * @param downloadSize the download size
     */
    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    /**
     * Sets total size.
     *
     * @param totalSize the total size
     */
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Gets need update.
     *
     * @return the need update
     */
    public int getNeedUpdate() {
        return needUpdate;
    }

    /**
     * Sets need update.
     *
     * @param needUpdate the need update
     */
    public void setNeedUpdate(int needUpdate) {
        this.needUpdate = needUpdate;
    }

    /**
     * Gets installed.
     *
     * @return the installed
     */
    public int getInstalled() {
        return installed;
    }

    /**
     * Sets installed.
     *
     * @param installed the installed
     */
    public void setInstalled(int installed) {
        this.installed = installed;
    }

    /**
     * Percent int.
     *
     * @return the int
     */
    public int percent() {
        long total = getTotalSize();
        long download = getDownloadSize();
        int percent = (int) (100.0 * getDownloadSize() / getTotalSize());
        if (percent > 100) {   // 临时规避
            return 100;
        } else {
            return percent;
        }
    }

    /**
     * Format percent string.
     *
     * @return the string
     */
    public String formatPercent() {
        if (totalSize == 0) {
            return "0 %";
        } else {
            return percent() + " %";
        }
    }
}
