package com.adx2099.roomadx2099example;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.adx2099.roomadx2099example.database.AppDatabase;
import com.adx2099.roomadx2099example.database.GameEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private LiveData<List<GameEntry>> games;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        games = database.gameDao().loadAllGames();
    }

    public LiveData<List<GameEntry>> getGames() {
        return games;
    }
}
