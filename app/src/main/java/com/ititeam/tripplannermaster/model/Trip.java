package com.ititeam.tripplannermaster.model;

import java.util.ArrayList;

import com.ititeam.tripplannermaster.model.Note;

/**
 * Created by MARK on 3/18/2018.
 */

public class Trip {

    int tripId;
    String tripName;
    String tripStartPoint;
    String tripEndPoint;
    String tripDate;
    String tripTime;
    String tripStatus;
    String tripDirection;
    String tripDescription;
    String tripRepetition;
    String tripCategory;
    int userId;
    ArrayList<Note> tripNodes;

    public Trip() {
    }

    public Trip(int tripId, String tripName, String tripStartPoint, String tripEndPoint, String tripDate, String tripTime, String tripStatus, String tripDirection, String tripDescription, String tripRepetition, String tripCategory, int userId, ArrayList<Note> tripNodes) {
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
        this.tripNodes = tripNodes;
    }
    public Trip(String tripName, String tripStartPoint, String tripEndPoint, String tripDate, String tripTime, String tripStatus, String tripDirection, String tripDescription, String tripRepetition, String tripCategory, int userId, ArrayList<Note> tripNodes) {
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
        this.tripNodes = tripNodes;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Note> getTripNodes() {
        return tripNodes;
    }

    public void setTripNodes(ArrayList<Note> tripNodes) {
        this.tripNodes = tripNodes;
    }

    public String getTripCategory() {
        return tripCategory;
    }

    public void setTripCategory(String tripCategory) {
        this.tripCategory = tripCategory;
    }
}
