package com.crudapi.mobile.client;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Client client);

    @Query("DELETE FROM client")
    void deleteAll();

    @Update
    void update(Client client);

    @Query("SELECT * FROM client ORDER BY id DESC")
    LiveData<List<Client>> getAll();

    @Delete
    void delete(Client client);

}
