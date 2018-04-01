package com.ititeam.tripplannermaster.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import com.ititeam.tripplannermaster.model.Note;

/**
 * Created by MARK on 3/18/2018.
 */

public class Trip implements Parcelable {

    int tripId;
    String tripName;
    String tripStartPoint;
    String tripEndPoint;
    String tripDate;
    String tripTime;

    public Trip(String tripName, String tripStartPoint, String tripEndPoint, String tripDate, String tripTime, String tripStatus, String tripDirection, String tripDescription, String tripRepetition, String tripCategory, String userId, ArrayList<Note> tripNodes) {
        this.tripName = tripName;
        this.tripStartPoint = tripStartPoint;
        this.tripEndPoint = tripEndPoint;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.tripStatus = tripStatus;
        this.tripDirection = tripDirection;
        this.tripDescription = tripDescription;
        this.tripRepetition = tripRepetition;
        this.tripCategory = tripCategory;
        this.userId = userId;
        this.tripNotes = tripNodes;
    }

    String tripStatus;
    String tripDirection;
    String tripDescription;
    String tripRepetition;
    String tripCategory;
    String userId;

    public void setTripNotes(ArrayList<Note> tripNotes) {
        this.tripNotes = tripNotes;
    }

    ArrayList<Note> tripNotes;

    public Trip() {
    }

    public Trip(int tripId, String tripName, String tripStartPoint, String tripEndPoint, String tripDate, String tripTime, String tripStatus, String tripDirection, String tripDescription, String tripRepetition, String tripCategory, String userId, ArrayList<Note> tripNotes) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripStartPoint = tripStartPoint;
        this.tripEndPoint = tripEndPoint;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.tripStatus = tripStatus;
        this.tripDirection = tripDirection;
        this.tripDescription = tripDescription;
        this.tripRepetition = tripRepetition;
        this.tripCategory = tripCategory;
        this.userId = userId;
        this.tripNotes = tripNotes;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripStartPoint() {
        return tripStartPoint;
    }

    public void setTripStartPoint(String tripStartPoint) {
        this.tripStartPoint = tripStartPoint;
    }

    public String getTripEndPoint() {
        return tripEndPoint;
    }

    public void setTripEndPoint(String tripEndPoint) {
        this.tripEndPoint = tripEndPoint;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getTripDirection() {
        return tripDirection;
    }

    public void setTripDirection(String tripDirection) {
        this.tripDirection = tripDirection;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripRepetition() {
        return tripRepetition;
    }

    public void setTripRepetition(String tripRepetition) {
        this.tripRepetition = tripRepetition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {this.userId = userId;}

    public ArrayList<Note> getTripNotes() {
        if (tripNotes == null)
            tripNotes = new ArrayList<Note>();
        return tripNotes;
    }

    public String getTripCategory() {
        return tripCategory;
    }

    public void setTripCategory(String tripCategory) {
        this.tripCategory = tripCategory;
    }

    protected Trip(Parcel in) {
        tripId = in.readInt();
        tripName = in.readString();
        tripStartPoint = in.readString();
        tripEndPoint = in.readString();
        tripDate = in.readString();
        tripTime = in.readString();
        tripStatus = in.readString();
        tripDirection = in.readString();
        tripDescription = in.readString();
        tripRepetition = in.readString();
        tripCategory = in.readString();
        userId = in.readString();
        if (in.readByte() == 0x01) {
            tripNotes = new ArrayList<Note>();
            in.readList(tripNotes, Note.class.getClassLoader());
        } else {
            tripNotes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tripId);
        dest.writeString(tripName);
        dest.writeString(tripStartPoint);
        dest.writeString(tripEndPoint);
        dest.writeString(tripDate);
        dest.writeString(tripTime);
        dest.writeString(tripStatus);
        dest.writeString(tripDirection);
        dest.writeString(tripDescription);
        dest.writeString(tripRepetition);
        dest.writeString(tripCategory);
        dest.writeString(userId);
        if (tripNotes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tripNotes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}