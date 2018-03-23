package com.ititeam.tripplannermaster.model;

/**
 * Created by MARK on 3/18/2018.
 */

public class Note {

    String noteId;
    String noteBody;
    String status;
    String tripIdFk;

    public Note() {
    }

    public Note(String noteId, String noteBody, String status, String tripIdFk) {
        this.noteId = noteId;
        this.noteBody = noteBody;
        this.status = status;
        this.tripIdFk = tripIdFk;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getTripIdFk() {
        return tripIdFk;
    }

    public void setTripIdFk(String tripIdFk) {
        this.tripIdFk = tripIdFk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
