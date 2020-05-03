package com.example.dailymood2020.models;

import android.app.Activity;

import com.example.dailymood2020.models.IModel;

public class MockModel implements IModel {
    private static String[] MESSAGES = new String[] {
            "This is the message for _today_.",
            null,
            null,
            null,
            "Message, four days ago.",
            null,
            null,
            "Message, 7 days ago."
    };
    private static String TODAYS_MESSAGE =  MESSAGES[0];
    private static int[] MOODS = new int[] {0,0,1,2,3,4,0,1};
    private static int TODAYS_MOOD = MOODS[0];

    public MockModel(Activity context) {
    }

    public void setTodaysMessage(String message) { /* no op*/ }
    public int getTodaysMood() {return TODAYS_MOOD; }
    public String getTodaysMessage() { return TODAYS_MESSAGE ; }
    public int[] getMoods() {return MOODS; }
    public String[]  getMessages() { return MESSAGES; }
    public boolean setBetterMood() { /* no op */ return true; }
    public boolean setWorseMood() { /* no op */ return true; }
}
