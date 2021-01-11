package com.crudapi.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crudapi.mobile.client.Client;
import com.crudapi.mobile.client.ClientListAdapter;
import com.crudapi.mobile.client.ClientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ConfirmationDialogFragment.ConfirmationDialogListener {

    private ClientViewModel clientViewModel;
    public static final int NEW_CLIENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CLIENT_ACTIVITY_REQUEST_CODE = 2;

    public int ID_EXTRA_REQUEST = 0;
    public static final String PRENOM_EXTRA_REQUEST = "com.crudapi.android.client.PRENOM_REQUEST";
    public static final String NOM_EXTRA_REQUEST = "com.crudapi.android.client.NOM_EXTRA_REQUEST";
    public static final String TELEPHONE_EXTRA_REQUEST = "com.crudapi.android.client.TELEPHONE_EXTRA_REQUEST";
    private boolean DELETE_CONFIRMATION = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ClientListAdapter clientListAdapter = new ClientListAdapter(new ClientListAdapter.ClientDiff());
        recyclerView.setAdapter(clientListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ClientViewModel.class);

        clientViewModel.getClients().observe(this, clients -> {
            clientListAdapter.submitList(clients);
        });

        FloatingActionButton addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewClientActivity.class);
            startActivityForResult(intent, NEW_CLIENT_ACTIVITY_REQUEST_CODE);
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Client client = clientListAdapter.getClientAtPosition(position);
                        Toast.makeText(MainActivity.this, "Suppression du client " +
                                client.getPrenom() + " " + client.getNom(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        clientViewModel.delete(client);
                        //showConfirmationDialog();

                        /*if(DELETE_CONFIRMATION == true) {
                            clientViewModel.delete(client);
                            DELETE_CONFIRMATION = false;
                        }*/
                    }

                });

        helper.attachToRecyclerView(recyclerView);
        configureOnClickRecyclerView(recyclerView, clientListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.println(Log.DEBUG, "DIAF", data.getStringExtra(NewClientActivity.PRENOM_EXTRA_REPLY));
            Client client = new Client(
                    data.getStringExtra(NewClientActivity.PRENOM_EXTRA_REPLY),
                    data.getStringExtra(NewClientActivity.NOM_EXTRA_REPLY),
                    data.getStringExtra(NewClientActivity.TELEPHONE_EXTRA_REPLY)
            );

            if (requestCode == NEW_CLIENT_ACTIVITY_REQUEST_CODE) {
                clientViewModel.insert(client);
            } else if (requestCode == UPDATE_CLIENT_ACTIVITY_REQUEST_CODE) {
                client.setId(ID_EXTRA_REQUEST);
                clientViewModel.update(client);
                Log.d("DIAF UPDATE", "Client " + client);
                Toast.makeText(MainActivity.this, "Client Modifié avec succés: " +
                        client.getPrenom() + " " + client.getNom(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DELETE_CONFIRMATION = true;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        DELETE_CONFIRMATION = false;
        dialog.getDialog().cancel();
    }

    private void configureOnClickRecyclerView(RecyclerView recyclerView, ClientListAdapter clientListAdapter){
        ItemClickSupport.addTo(recyclerView, R.layout.recyclerview_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Client client = clientListAdapter.getClientAtPosition(position);
                        Toast.makeText(MainActivity.this, "Modification du client " +
                                client.getPrenom() + " " + client.getNom(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, NewClientActivity.class);

                        ID_EXTRA_REQUEST = client.getId();
                        intent.putExtra(PRENOM_EXTRA_REQUEST, client.getPrenom());
                        intent.putExtra(NOM_EXTRA_REQUEST, client.getNom());
                        intent.putExtra(TELEPHONE_EXTRA_REQUEST, client.getTelephone());
                        startActivityForResult(intent, UPDATE_CLIENT_ACTIVITY_REQUEST_CODE);
                    }
                });

    }

    public void showConfirmationDialog() {
        DialogFragment confirmationDialog = new ConfirmationDialogFragment();
        confirmationDialog.show(getSupportFragmentManager(), "delete_client_dialog");
    }

}