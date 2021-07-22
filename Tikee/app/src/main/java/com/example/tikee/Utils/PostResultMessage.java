package com.example.tikee.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PostResultMessage implements Parcelable {

    @SerializedName("student_id")
    String studentId;
    @SerializedName("extra_value")
    String extraValue;
    @SerializedName("user_name")
    String userName;
    @SerializedName("video_url")
    String videoUrl;
    @SerializedName("image_url")
    String imageUrl;
    @SerializedName("image_w")
    int image_w;
    @SerializedName("image_h")
    int image_h;
    @SerializedName("_id")
    String id;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;

    protected PostResultMessage(Parcel in) {
        studentId = in.readString();
        extraValue = in.readString();
        userName = in.readString();
        videoUrl = in.readString();
        imageUrl = in.readString();
        image_w = in.readInt();
        image_h = in.readInt();
        id = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<PostResultMessage> CREATOR = new Creator<PostResultMessage>() {
        @Override
        public PostResultMessage createFromParcel(Parcel in) {
            return new PostResultMessage(in);
        }

        @Override
        public PostResultMessage[] newArray(int size) {
            return new PostResultMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.extraValue);
        dest.writeString(this.userName);
        dest.writeString(this.videoUrl);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.image_w);
        dest.writeInt(this.image_h);
        dest.writeString(this.id);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTime() {return createdAt;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getImage_w() {
        return image_w;
    }

    public void setImage_w(int image_w) {
        this.image_w = image_w;
    }

    public int getImage_h() {
        return image_h;
    }

    public void setImage_h(int image_h) {
        this.image_h = image_h;
    }

    public String getExtraValue(){return  extraValue;}

    public void setExtraValue(String extraValue) {this.extraValue = extraValue;}


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
