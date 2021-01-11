package com.crudapi.mobile.client;

import android.app.Application;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.crudapi.mobile.ConfirmationDialogFragment;
import com.crudapi.mobile.RoomDatabase;

import java.util.List;

public class ClientRepository {

    private ClientDao clientDao;
    private LiveData<List<Client>> clients;

    public ClientRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        clientDao = db.clientDao();
        clients = clientDao.getAll();
    }

    LiveData<List<Client>> getClients() {
        return clients;
    }

    void insert(Client client) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            clientDao.insert(client);
        });
    }

    void update(Client client) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            clientDao.update(client);
        });
    }

    void delete(Client client) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            clientDao.delete(client);
        });
    }



}
