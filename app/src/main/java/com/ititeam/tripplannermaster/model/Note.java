package com.ititeam.tripplannermaster.model;

/**
 * Created by MARK on 3/18/2018.
 */

public class Note {

    int noteId;
    String noteBody;



    String status;
    int tripIdFk;

    public Note() {
    }

    public Note(String noteBody, String status) {
        this.noteBody = noteBody;
        this.status = status;
    }
    public Note(int noteId, String noteBody, String status, int tripIdFk) {
        this.noteId = noteId;
        this.noteBody = noteBody;
        this.status = status;
        this.tripIdFk = tripIdFk;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public int getTripIdFk() {
        return tripIdFk;
    }

    public void setTripIdFk(int tripIdFk) {
        this.tripIdFk = tripIdFk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
