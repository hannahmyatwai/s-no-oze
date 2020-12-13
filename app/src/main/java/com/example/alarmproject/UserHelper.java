package com.example.alarmproject;

public class UserHelper {
    public UserHelper(String username, String password, String user1, int score) {
        this.username = username;
        this.password = password;
        this.user1 = user1;
        this.score = score;
    }

    String username;
    String password;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    int score;


    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    String user1;

    public UserHelper(){

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
