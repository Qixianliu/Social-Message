package com.example.kallz2u.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Group implements Parcelable {
    public String groupName,adminId,groupType;
    public List<GroupMemberModel> members;

    public Group(String groupName, String adminId, String groupType, List<GroupMemberModel> members) {
        this.groupName = groupName;
        this.adminId = adminId;
        this.groupType = groupType;
        this.members = members;
    }

    protected Group(Parcel in) {
        groupName = in.readString();
        adminId = in.readString();
        groupType = in.readString();
        members = in.createTypedArrayList(GroupMemberModel.CREATOR);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<GroupMemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberModel> members) {
        this.members = members;
    }

    public Group(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(groupName);
        parcel.writeString(adminId);
        parcel.writeString(groupType);
        parcel.writeTypedList(members);
    }
}
