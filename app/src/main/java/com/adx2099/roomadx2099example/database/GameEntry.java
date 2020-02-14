package com.adx2099.roomadx2099example.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
//Este POJO lo convertiremos en una entidad
//Con el simple hecho de agregar la anotacion Entity a nuestro POJO Room creara una tabla con el nombre Entity
//pero para mantener un manejo corecto de nuestra base de datos renombraremos la tabla como game
@Entity (tableName = "game")
public class GameEntry {
    //Agregamos la anotacion Primary key, podriamos generarla nosotros pero Room puede hacerlo por nosotros
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int owned;
    private Date updatedAt;

    //Utilizamos dos constructores dedibo a que para agregar un nuevo juego a la base de datos necesitamos crear un ojeto game entry
    //Pero debido a que el id sera autogenerado no tenemos manera de saber el Id que tendra ese juego en la base de datos, easi que para esta
    //insercion utilizaremos el primer constructor pero al momento de leer de la base de datos debemos de saber el id que vamos a leer.
    @Ignore
    public GameEntry(String description, int owned, Date updatedAt) {
        this.description = description;
        this.owned = owned;
        this.updatedAt = updatedAt;
    }

    public GameEntry(int id, String description, int owned, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.owned = owned;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
