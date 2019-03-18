package com.example.android.background.sync;

// completed(1) Create a class called ReminderTasks

import android.content.Context;
import android.preference.Preference;

import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {

    // completed(2) Create a public static constant String called ACTION_INCREMENT_WATER_COUNT
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    // completed(3) Create a private static void method called incrementWaterCount
    // completed(4) Add a Context called context to the argument list
    public static void incrementWaterCount(Context context) {
// completed(5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count
        PreferenceUtilities.incrementWaterCount(context);
    }

    // completed(6) Create a public static void method called executeTask
    // completed(7) Add a Context called context and String parameter called action to the parameter list
    public static void executeTask(Context context, String action) {
        // completed(8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount
        if (action.equals(ACTION_INCREMENT_WATER_COUNT)) {
            incrementWaterCount(context);
        }
    }
}
