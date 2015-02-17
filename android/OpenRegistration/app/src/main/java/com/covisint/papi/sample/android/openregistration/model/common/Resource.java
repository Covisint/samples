package com.covisint.papi.sample.android.openregistration.model.common;

/**
 * Created by Nitin.Khobragade on 2/12/2015.
 */
public class Resource {
    private String id;
    private Long version;
    private String creator;
    private String creatorAppId;
    private Long creation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorAppId() {
        return creatorAppId;
    }

    public void setCreatorAppId(String creatorAppId) {
        this.creatorAppId = creatorAppId;
    }

    public Long getCreation() {
        return creation;
    }

    public void setCreation(Long creation) {
        this.creation = creation;
    }
}
