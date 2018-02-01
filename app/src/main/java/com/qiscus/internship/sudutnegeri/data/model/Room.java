package com.qiscus.internship.sudutnegeri.data.model;

/**
 * Created by Vizyan on 2/1/2018.
 */

public class Room {

    private final int id;
    private final String name;
    private String latestConversation="";
    private String onlineImage="";
    private int unreadCounter=0;
    private String lastMessageTime="";

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getLatestConversation() {
        return latestConversation;
    }

    public void setLatestConversation(String latestConversation) {
        this.latestConversation = latestConversation;
    }

    public void setOnlineImage(String image) {
        this.onlineImage = image;
    }

    public String getOnlineImage() {return onlineImage;}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnreadCounter() { return unreadCounter; }

    public void setUnreadCounter(int unreadCounter) { this.unreadCounter = unreadCounter;}

    public String getLastMessageTime() { return lastMessageTime;}

    public void setLastMessageTime(String lastMessageTime) { this.lastMessageTime = lastMessageTime;  }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id == room.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
