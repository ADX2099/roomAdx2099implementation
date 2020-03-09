package com.adx2099.roomadx2099example;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adx2099.roomadx2099example.database.AppDatabase;
import com.adx2099.roomadx2099example.database.GameEntry;

import java.util.Date;

public class AddGameActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_ID = "extraGameId";
    public static final String INSTANCE_GAME_ID = "instanceGameId";
    public static final int OWNED = 1;
    public static final int NOT_OWNED = 2;

    private static final int DEFAULT_GAME_ID = -1;

    private static final String TAG = AddGameActivity.class.getSimpleName();

    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;

    private int mGameId = DEFAULT_GAME_ID;
    //Agregamos la variable para  la instancia de la base de datos
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        initViews();
        //agregamos al instancia de la base
        mDb = AppDatabase.getInstance(getApplicationContext());

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_GAME_ID)){
            mGameId = savedInstanceState.getInt(INSTANCE_GAME_ID, DEFAULT_GAME_ID);
        }
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_GAME_ID)){
            mButton.setText(R.string.update_button);
            if(mGameId == DEFAULT_GAME_ID){
                mGameId = intent.getIntExtra(EXTRA_GAME_ID, DEFAULT_GAME_ID);
                AppExecutors.getInstance().DiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final GameEntry game = mDb.gameDao().loadGameByuId(mGameId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI(game);
                            }
                        });

                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_GAME_ID, mGameId);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        mEditText = findViewById(R.id.editTextGameDescription);
        mRadioGroup = findViewById(R.id.radioGroup);

        mButton = findViewById(R.id.addButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(GameEntry game){
        if (game == null) {
            return;
        }
        mEditText.setText(game.getDescription());
        setOwnedInView(game.getOwned());
    }



    private void onSaveButtonClicked() {

        String description = mEditText.getText().toString();
        int owned = getOwnedFromViews();

        Date date = new Date();

        final GameEntry gameEntry = new GameEntry(description, owned, date);
        AppExecutors.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mGameId == DEFAULT_GAME_ID){
                    mDb.gameDao().insertGame(gameEntry);
                }else{
                    gameEntry.setId(mGameId);
                    mDb.gameDao().updateGame(gameEntry);
                }

                finish();
            }
        });

    }

    private int getOwnedFromViews() {
        int owned = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

        switch (checkedId){
            case R.id.radButton1:
                owned = OWNED;
                break;
            case R.id.radButton2:
                owned = NOT_OWNED;
                break;
        }
        return owned;
    }

    public void setOwnedInView(int owned){
        switch(owned){
            case OWNED:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case NOT_OWNED:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
        }
    }

    public void onClickAddTask(View view){
        String input = ((EditText) findViewById(R.id.editTextGameDescription)).getText().toString();
        if(input.length() == 0){
            return;
        }

    }




}
