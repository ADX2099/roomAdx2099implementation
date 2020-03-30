package com.adx2099.roomadx2099example;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.adx2099.roomadx2099example.AddGameViewModel;
import com.adx2099.roomadx2099example.database.AppDatabase;

public class AddGameViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDB;
    private final int mGameId;

    public AddGameViewModelFactory(AppDatabase mDB, int mGameId) {
        this.mDB = mDB;
        this.mGameId = mGameId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddGameViewModel(mDB,mGameId);
    }
}
