package com.example.kallz2u.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Parcelable {
    public String RequestId,isUrgent,sender,event,time,GroupNam,location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Request(String requestId, String isUrgent, String sender, String event, String time, String groupNam, String location) {
        RequestId = requestId;
        this.isUrgent = isUrgent;
        this.sender = sender;
        this.event = event;
        this.time = time;
        this.GroupNam = groupNam;
        this.location = location;
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroupNam() {
        return GroupNam;
    }

    public void setGroupNam(String group) {
        GroupNam = getGroupNam();
    }

    protected Request(Parcel in) {
        RequestId = in.readString();
        isUrgent = in.readString();
        sender = in.readString();
        event = in.readString();
        time = in.readString();
        GroupNam = in.readString();
        location = in.readString();
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(RequestId);
        parcel.writeString(isUrgent);
        parcel.writeString(sender);
        parcel.writeString(event);
        parcel.writeString(time);
        parcel.writeString(GroupNam);
        parcel.writeString(location);
    }
}
