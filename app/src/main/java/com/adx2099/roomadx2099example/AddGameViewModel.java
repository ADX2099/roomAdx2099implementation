package com.adx2099.roomadx2099example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.adx2099.roomadx2099example.database.AppDatabase;
import com.adx2099.roomadx2099example.database.GameEntry;

public class AddGameViewModel extends ViewModel {

    private LiveData<GameEntry> game;

    public AddGameViewModel(AppDatabase database, int gameId){
        game = database.gameDao().loadGameByuId(gameId);
    }
    public LiveData<GameEntry> getGame() {
        return game;
    }

}
