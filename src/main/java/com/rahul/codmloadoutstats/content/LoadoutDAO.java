package com.rahul.codmloadoutstats.content;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;

@Entity(name = "Loadout")
public class LoadoutDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loadoutId;

    private long votes;

    private byte[] blobData;

    private String fileName;

    private String contentType;

    private long size;

    public LoadoutDAO() {
    }

    public LoadoutDAO(long votes, byte[] blobData, String fileName, String contentType, long size) {
        this.votes = votes;
        this.blobData = blobData;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
    }

    public long getLoadoutId() {
        return loadoutId;
    }

    public void setLoadoutId(long loadoutId) {
        this.loadoutId = loadoutId;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "LoadoutDAO{" +
                "loadoutId=" + loadoutId +
                ", votes=" + votes +
                ", blobData=" + Arrays.toString(blobData) +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                '}';
    }
}
