package com.example.dailymood2020.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailymood2020.Config;
import com.example.dailymood2020.R;
import com.example.dailymood2020.models.IModel;
import com.example.dailymood2020.models.MockModel;
import com.example.dailymood2020.models.Model;

public class ActivityMoodHistory extends AppCompatActivity {
    private IModel model;
    private String TAG = "ActivityMoodHistory";
    private ViewGroup rootContainer;
    private View[] messageButtons;
    int label_idx = 6;
    public static final String DEBUG_EXTRA_KEY = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        if (startedWithDebugIntent() || Config.useMockModel) {
            model = new MockModel(this);
        } else {
            model = new Model(this);
        }

        rootContainer = (ViewGroup) findViewById(R.id.root_container);

        messageButtons = new View[] {
                findViewById(R.id.button_notes_1),
                findViewById(R.id.button_notes_2),
                findViewById(R.id.button_notes_3),
                findViewById(R.id.button_notes_4),
                findViewById(R.id.button_notes_5),
                findViewById(R.id.button_notes_6),
                findViewById(R.id.button_notes_7)
        };

        // Self defense, assert some things about our data.
        if (preflightCheck()) {
            updateLayout();
        } else {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean preflightCheck() {
        int today = 1;
        return (model.getMoods().length - today == rootContainer.getChildCount() &&
                model.getMessages().length - today == rootContainer.getChildCount());

    }


    private void displayMood(int index, int mood) {
        if (index > rootContainer.getChildCount() - 1) {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
        } else {
            ViewGroup subcontainer = (ViewGroup) rootContainer.getChildAt(index);
            int label = 1;
            if (mood < subcontainer.getChildCount() - label ) {
                //first hide all of the progress bars
                for (int i = 0; i< subcontainer.getChildCount(); i++) {
                    subcontainer.getChildAt(i).setVisibility(View.GONE);
                }
                //Then show the active one.
                subcontainer.getChildAt(mood).setVisibility(View.VISIBLE);
                //We also "accidentally" hid the label, so re-show it.
                subcontainer.getChildAt(label_idx).setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayMessageButtons(int index, final String message) {
        if (message != null) {
            messageButtons[index].setVisibility(View.VISIBLE);
            messageButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ActivityMoodHistory.this, message, Toast.LENGTH_SHORT )
                            .show();
                }
            });
        }
    }

    private void updateLayout() {
        int[] moods = model.getMoods();
        String[] messages = model.getMessages();
        for (int day = 1; day<moods.length; day++) {
            int mood = moods[day];
            String message = messages[day];
            int today = 1;
            displayMood(day - today, mood);
            displayMessageButtons(day - today, message );
        }
    }

    boolean startedWithDebugIntent() {
        Intent intent = getIntent();
        return intent.getBooleanExtra(DEBUG_EXTRA_KEY, false);
    }
}
