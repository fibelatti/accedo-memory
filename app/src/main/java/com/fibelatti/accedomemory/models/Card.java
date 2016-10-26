package com.fibelatti.accedomemory.models;

public class Card {
    private final static int STATUS_FACE_DOWN = 0;
    private final static int STATUS_FACE_UP = 1;
    private final static int STATUS_MATCHED = 2;

    private int drawableId;
    private int status;

    public Card(int drawableId) {
        this.drawableId = drawableId;
        this.status = STATUS_FACE_DOWN;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public boolean isFaceDown() {
        return status == STATUS_FACE_DOWN;
    }

    public boolean isFaceUp() {
        return status == STATUS_FACE_UP;
    }

    public boolean isFaceMatched() {
        return status == STATUS_MATCHED;
    }

    public void setStatusFaceDown() {
        this.status = STATUS_FACE_DOWN;
    }

    public void setStatusFaceUp() {
        this.status = STATUS_FACE_UP;
    }

    public void setStatusMatched() {
        this.status = STATUS_MATCHED;
    }
}
