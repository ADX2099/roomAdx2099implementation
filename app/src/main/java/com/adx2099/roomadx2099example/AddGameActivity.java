package com.adx2099.roomadx2099example;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adx2099.roomadx2099example.data.GameContract;

public class AddGameActivity extends AppCompatActivity {
    private int mOwned;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mOwned = 1;
    }

    public void onClickAddTask(View view){
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        if(input.length() == 0){
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(GameContract.GameEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(GameContract.GameEntry.COLUMN_OWNED, mOwned);

        Uri uri = getContentResolver().insert(GameContract.GameEntry.CONTENT_URI, contentValues);

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        finish();

    }




}
