package com.crudapi.mobile.client;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository clientRepository;
    private final LiveData<List<Client>> clients;


    public ClientViewModel(Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        clients = clientRepository.getClients();
    }

    public LiveData<List<Client>> getClients() {
        return clients;
    }

    public void insert(Client client) {
        clientRepository.insert(client);
    }

    public void update(Client client) {
        clientRepository.update(client);
    }

    public void delete(Client client) { clientRepository.delete(client); }
}
