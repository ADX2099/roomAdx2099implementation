package com.adx2099.roomadx2099example.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<GameEntry> loadAllGames();

    @Insert
    void insertGame(GameEntry gameEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGame(GameEntry gameEntry);

    @Delete
    void deleteGame(GameEntry gameEntry);

}
