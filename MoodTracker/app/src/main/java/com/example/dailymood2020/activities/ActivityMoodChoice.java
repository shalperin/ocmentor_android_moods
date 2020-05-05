package com.example.dailymood2020.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dailymood2020.R;
import com.example.dailymood2020.models.Model;

public class ActivityMoodChoice extends AppCompatActivity implements IFlingHandler {

    private String TAG = "MoodChoice";
    private GestureDetectorCompat gestureDetector;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_choice);

        /* GestureDetectorCompat below wraps some version of a GestureDetector,
           in this case a MyGestureDetector,and gives it a consistent interface across
           all versions. Other examples of this approach are in classes like ContextCompat, and
           ResourcesCompat.
         */
        gestureDetector = new GestureDetectorCompat(
                this,
                new MyGestureDetector(this /* passing ourself as the FlingHandler */)
        );

        model = new Model (this);

        updateViews();

        bindClickListeners();

    }

    private void bindClickListeners() {
        findViewById(R.id.button_history).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityMoodChoice.this, ActivityMoodHistory.class);
                startActivity(i);
            }
        });

        findViewById(R.id.button_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showNoteEntryDialog(); }
        });
    }

    /* Implements a method in the FlingHandler Interface */
    @Override
    public void handleFlingDown() {
        selectWorseMood();
    }

    /* Implements a method in the FlingHandler Interface */
    @Override
    public void handleFlingUp() {
        selectBetterMood();
    }

    /* There are other approaches to this view switching that might
       be more amenable to a beginner, such as using a switch statement,
       or a group of if/else conditionals.

       In this approach, we walk up and down an Array of views, being careful
       not to go off the end of the Array.
     */
    private void selectWorseMood() {
        boolean didIncrementCurrentView = model.setWorseMood();
        if (didIncrementCurrentView) {
            updateViews();
        } else {
            Toast.makeText(this, R.string.no_worse_mood,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void selectBetterMood() {
        boolean didDecrementCurrentView = model.setBetterMood();
        if (didDecrementCurrentView) {
            updateViews();
        } else {
            Toast.makeText(this, R.string.no_better_mood,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void updateViews() {
        ViewGroup container = (ViewGroup) findViewById(R.id.mood_container);
        for (int i = 0; i< container.getChildCount(); i++) {
            container.getChildAt(i).setVisibility(View.GONE);
        }
        View activeMood = ((ViewGroup) findViewById(R.id.mood_container))
                .getChildAt(model.getTodaysMood());
        activeMood.setVisibility(View.VISIBLE);
    }


    @Override /* Overrides a method in android.app.Activity */
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void showNoteEntryDialog() {
        final EditText txtUrl = new EditText(this);
        txtUrl.setText(model.getTodaysMessage());
        new AlertDialog.Builder(this)
                .setTitle(R.string.enter_a_note)
                .setView(txtUrl)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String note = txtUrl.getText().toString();
                        model.setTodaysMessage(note);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
}
