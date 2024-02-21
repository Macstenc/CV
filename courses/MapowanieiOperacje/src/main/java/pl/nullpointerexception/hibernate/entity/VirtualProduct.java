package pl.nullpointerexception.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VIRTUAL")
public class VirtualProduct extends BaseProduct{
    private boolean downloadable;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_name")
    private String fileName;

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "VirtualProduct{" +
                "downloadable=" + downloadable +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
