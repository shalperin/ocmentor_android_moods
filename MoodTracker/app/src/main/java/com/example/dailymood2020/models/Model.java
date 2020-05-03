package com.example.dailymood2020.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.dailymood2020.Config;
import com.example.dailymood2020.models.IModel;

import java.util.Calendar;

public class Model implements IModel {
    public static int N_DAYS = 8;
    private String[] messages = new String[N_DAYS];
    private int[] moods = new int[N_DAYS];
    public static int TODAY_IDX = 0;
    public static int N_MOODS = 5;
    private Activity context;
    private SharedPreferences prefsCache;

    public Model(Activity context) {
        this.context = context;
        readFromStorage();
    }

    private SharedPreferences prefs() {
        if (prefsCache == null) {
            prefsCache = context.getSharedPreferences(Config.prefsFile, Context.MODE_PRIVATE);
        }
        return prefsCache;
    }

    private int[] readFromStorage() {
        Calendar calendar = Calendar.getInstance();
        for (int i = TODAY_IDX; i< N_DAYS; i++) {
            int dotw = calendar.get(Calendar.DAY_OF_WEEK);
            int week = i / 7; //integer division
            moods[i] = prefs().getInt(moodSharedPrefKey(dotw, week), 4);
            messages[i] = prefs().getString(msgSharedPrefKey(dotw, week), null);
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        return moods;
    }

    private static String moodSharedPrefKey(int dayOfWeek, int week) {
        return "mood_day:" + dayOfWeek + "_week:" + week;
    }
    private static String msgSharedPrefKey(int dayOfWeek, int week) {
        return "msg_day:" + dayOfWeek + "_week:" + week;
    }

    private void setTodaysMood(int mood) {
        String prefKey = moodSharedPrefKey(Calendar.getInstance().get(Calendar.DAY_OF_WEEK), 0);
        prefs().edit()
                .putInt(prefKey, mood)
                .commit();
        readFromStorage();
    }

    public void setTodaysMessage(String message) {
        String prefKey = msgSharedPrefKey(Calendar.getInstance().get(Calendar.DAY_OF_WEEK), 0);
        prefs().edit()
                .putString(prefKey, message)
                .commit();
        readFromStorage();
    }

    public int getTodaysMood() { return moods[TODAY_IDX]; }
    public String getTodaysMessage() { return messages[TODAY_IDX]; }
    public int[] getMoods() { return moods; }
    /*May contain nulls.: String?[] in Kotlin */
    public String[]  getMessages() { return messages; }

    public boolean setBetterMood() {
        if (getTodaysMood() < N_MOODS - 1) {
            setTodaysMood(getTodaysMood()+ 1);
            return true;
        } else {
            return false;
        }
    }
    public boolean setWorseMood() {
        if (getTodaysMood() > 0) {
            setTodaysMood(getTodaysMood() - 1);
            return true;
        } else {
            return false;
        }
    }
}
