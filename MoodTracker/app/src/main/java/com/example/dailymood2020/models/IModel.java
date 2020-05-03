package com.example.dailymood2020.models;

public interface IModel {
    public void setTodaysMessage(String message);
    public int getTodaysMood();
    public String getTodaysMessage();
    public int[] getMoods();
    public String[]  getMessages();
    public boolean setBetterMood();
    public boolean setWorseMood();
}
