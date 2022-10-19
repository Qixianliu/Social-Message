package com.example.kallz2u.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.List;

public class Groups implements Parcelable {
    public String groupName,adminId;
    public List<GroupMemberModel> members;

    public Groups(){

    }

    public Groups(String groupName,String adminId) {
        this.groupName = groupName;
        this.adminId = adminId;
    }


    protected Groups(Parcel in) {
        groupName = in.readString();
        adminId = in.readString();
        members = in.createTypedArrayList(GroupMemberModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(adminId);
        dest.writeTypedList(members);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Groups> CREATOR = new Creator<Groups>() {
        @Override
        public Groups createFromParcel(Parcel in) {
            return new Groups(in);
        }

        @Override
        public Groups[] newArray(int size) {
            return new Groups[size];
        }
    };

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
