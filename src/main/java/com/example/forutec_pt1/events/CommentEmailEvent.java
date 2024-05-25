package com.example.forutec_pt1.events;

public class CommentEmailEvent {
    private final String email;
    private final String comment;

    public CommentEmailEvent(String email, String comment) {
        this.email = email;
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }
}
