package com.percolate.codetest;

import android.content.ContentValues;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Coffee Object
 */
public class Coffee {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("last_updated_at")
    private String lastUpdatedAt;

    private static String sKeyId = "id";
    private static String sKeyName = "name";
    private static String sKeyDesc = "desc";
    private static String sKeyImageUrl = "image_url";
    private static String skeyLastUpdatedAt = "last_updated_at";

    public Coffee(String _id, String _name, String _desc, String _imageUrl, String _lastUpdatedAt) {
        this.id = _id;
        this.name = _name;
        this.desc = _desc;
        this.imageUrl = _imageUrl;
        this.lastUpdatedAt = _lastUpdatedAt;

    }

    /**
     * Coffee Empty Constructor
     */
    public Coffee() {

    }

    /**
     * Get and Set Methods
     */

    public void setLastUpdatedAt(String _lastUpdatedAt) {
        this.lastUpdatedAt = _lastUpdatedAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setImageUrl(String _imageUrl) {
        this.imageUrl = _imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDesc(String _desc) {
        this.desc = _desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public String getId() {
        return id;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return name;
    }

    /**
     * Method to fill Coffee Object with Content Values
     *
     * @param coffee
     * @return Content Values
     */
    public static ContentValues marshall(Coffee coffee) {
        ContentValues values = new ContentValues();
        values.put(sKeyId, coffee.getId());
        values.put(sKeyName, coffee.getName());
        values.put(sKeyDesc, coffee.getDesc());
        values.put(sKeyImageUrl, coffee.getImageUrl());
        values.put(skeyLastUpdatedAt, coffee.getLastUpdatedAt());
        return values;
    }

    /**
     * Method to get Coffee Object from Content Values
     *
     * @param values
     * @return Coffee
     */
    public static Coffee unmarshall(ContentValues values) {
        return new Coffee(
                values.getAsString(sKeyId),
                values.getAsString(sKeyName),
                values.getAsString(sKeyDesc),
                values.getAsString(sKeyImageUrl),
                values.getAsString(skeyLastUpdatedAt));
    }


}
