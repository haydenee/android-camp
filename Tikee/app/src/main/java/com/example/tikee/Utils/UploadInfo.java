package com.example.tikee.Utils;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class UploadInfo {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("extra_value")
    private String extraValue;
    @SerializedName("cover_image")
    private MultipartBody.Part coverImage;
    @SerializedName("video")
    private MultipartBody.Part video;
    @SerializedName("token")
    private String token;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExtraValue()   {return extraValue; }

    public void setExtraValue(String extraValue)    {this.extraValue = extraValue;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MultipartBody.Part getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartBody.Part cover_image) {
        this.coverImage = cover_image;
    }

    public MultipartBody.Part getVideo() {
        return video;
    }

    public void setVideo(MultipartBody.Part video) {
        this.video = video;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


