package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Db_ChangePassword {

    @SerializedName("customMessages")
    @Expose
    private List<Object> customMessages = null;
    @SerializedName("fallbackMessages")
    @Expose
    private List<Object> fallbackMessages = null;
    @SerializedName("customAttributes")
    @Expose
    private List<Object> customAttributes = null;
    @SerializedName("customValues")
    @Expose
    private List<Object> customValues = null;
    @SerializedName("extensions")
    @Expose
    private List<Object> extensions = null;
    @SerializedName("replacers")
    @Expose
    private List<Object> replacers = null;

    public List<Object> getCustomMessages() {
        return customMessages;
    }

    public void setCustomMessages(List<Object> customMessages) {
        this.customMessages = customMessages;
    }

    public List<Object> getFallbackMessages() {
        return fallbackMessages;
    }

    public void setFallbackMessages(List<Object> fallbackMessages) {
        this.fallbackMessages = fallbackMessages;
    }

    public List<Object> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<Object> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public List<Object> getCustomValues() {
        return customValues;
    }

    public void setCustomValues(List<Object> customValues) {
        this.customValues = customValues;
    }

    public List<Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }

    public List<Object> getReplacers() {
        return replacers;
    }

    public void setReplacers(List<Object> replacers) {
        this.replacers = replacers;
    }

}