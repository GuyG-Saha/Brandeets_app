package com.yessumtorah.brandeetsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Brand {
    @SerializedName("_id")
    private String id;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("buyLink")
    @Expose
    private String buyLink;
    @SerializedName("logoSrc")
    @Expose
    private String logoSrc;
    @SerializedName("ideas")
    @Expose
    private ArrayList<String> ideas;
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;
    @SerializedName("infos")
    @Expose
    private ArrayList<String> infos;

    public Brand(String name, String ext) {
        this.name = name;
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public void setLogoSrc(String logoSrc) {
        this.logoSrc = logoSrc;
    }

    public ArrayList<String> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<String> ideas) {
        this.ideas = ideas;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<String> infos) {
        this.infos = infos;
    }
}