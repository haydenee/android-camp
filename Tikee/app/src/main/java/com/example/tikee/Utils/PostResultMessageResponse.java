package com.example.tikee.Utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResultMessageResponse {
    @SerializedName("feeds")
    public List<PostResultMessage> mItems;

    @SerializedName("success")
    public boolean success;

}
