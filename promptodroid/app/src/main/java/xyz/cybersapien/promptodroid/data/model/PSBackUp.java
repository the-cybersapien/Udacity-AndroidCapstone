package xyz.cybersapien.promptodroid.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ogcybersapien on 1/1/18.
 */

public class PSBackUp implements Parcelable {

    private String storyTitle;
    private String storyDetail;
    private String id;
    private long date;
    private String userId;

    public PSBackUp() {
    }

    public PSBackUp(String storyTitle, String storyDetail) {
        this.storyTitle = storyTitle;
        this.storyDetail = storyDetail;
        date = new Date().getTime();
    }

    public PSBackUp(String storyTitle, String storyDetail, String userId) {
        this.storyTitle = storyTitle;
        this.storyDetail = storyDetail;
        this.userId = userId;
        date = new Date().getTime();
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryDetail() {
        return storyDetail;
    }

    public void setStoryDetail(String storyDetail) {
        this.storyDetail = storyDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.storyTitle);
        dest.writeString(this.storyDetail);
        dest.writeString(this.id);
        dest.writeLong(this.date);
        dest.writeString(this.userId);
    }

    protected PSBackUp(Parcel in) {
        this.storyTitle = in.readString();
        this.storyDetail = in.readString();
        this.id = in.readString();
        this.date = in.readLong();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<PSBackUp> CREATOR = new Parcelable.Creator<PSBackUp>() {
        @Override
        public PSBackUp createFromParcel(Parcel source) {
            return new PSBackUp(source);
        }

        @Override
        public PSBackUp[] newArray(int size) {
            return new PSBackUp[size];
        }
    };

    @Override
    public String toString() {
        String sb = "PSBackUp{" + "storyTitle='" + storyTitle + '\'' +
                ", storyDetail='" + storyDetail + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                '}';
        return sb;
    }
}
