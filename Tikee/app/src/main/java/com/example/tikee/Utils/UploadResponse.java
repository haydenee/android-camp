package com.example.tikee.Utils;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    @SerializedName("result")
    PostResultMessage postResultMessage;
    @SerializedName("url")
    String url;
    @SerializedName("success")
    public boolean success;
    @SerializedName("error")
    public String error;
}

