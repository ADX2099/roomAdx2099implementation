package com.adx2099.roomadx2099example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adx2099.roomadx2099example.database.AppDatabase;
import com.adx2099.roomadx2099example.database.GameEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    private GameAdapter mAdapter;
    RecyclerView mRecyclerView;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new GameAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().DiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int thePosition = viewHolder.getAdapterPosition();
                        List<GameEntry> games = mAdapter.getGames();
                        mDb.gameDao().deleteGame(games.get(thePosition));

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivity(addTaskIntent);
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        retrieveGames();
    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    private void retrieveGames() {
        final LiveData<List<GameEntry>> games = mDb.gameDao().loadAllGames();
        games.observe(this, new Observer<List<GameEntry>>() {
            @Override
            public void onChanged(List<GameEntry> gameEntries) {
                mAdapter.setGames(gameEntries);
            }
        });
    }


    @Override
    public void onItemClickListener(int itemId) {
        Intent updateIntent = new Intent(MainActivity.this, AddGameActivity.class);
        updateIntent.putExtra(AddGameActivity.EXTRA_GAME_ID, itemId);
        startActivity(updateIntent);
    }
}
