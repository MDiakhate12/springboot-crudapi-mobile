package com.crudapi.mobile.client;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.crudapi.mobile.R;

public class ClientListAdapter extends ListAdapter<Client, ClientViewHolder> {

    public ClientListAdapter(@NonNull DiffUtil.ItemCallback<Client> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ClientViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client currentClient = getItem(position);
        Log.println(Log.DEBUG, "DIAF", currentClient.getPrenom());
        holder.bind(
                currentClient.getPrenom(),
                currentClient.getNom(),
                currentClient.getTelephone());
    }

    public Client getClientAtPosition(int position){
        return getItem(position);
    }
    public static class ClientDiff extends DiffUtil.ItemCallback<Client> {

        @Override
        public boolean areItemsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return
                    oldItem.getPrenom().equals(newItem.getPrenom()) &&
                    oldItem.getNom().equals(newItem.getNom()) &&
                    oldItem.getTelephone().equals(newItem.getTelephone());
        }


    }
}
