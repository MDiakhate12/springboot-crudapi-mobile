package com.crudapi.mobile.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.crudapi.mobile.ConfirmationDialogFragment;
import com.crudapi.mobile.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class ClientViewHolder extends RecyclerView.ViewHolder {

    private final TextView clientPrenomView;
    private final TextView clientNomView;
    private final TextView clientTelephoneView;

    private ClientViewHolder(View clientView) {
        super(clientView);
        clientPrenomView = clientView.findViewById(R.id.prenom);
        clientNomView = clientView.findViewById(R.id.nom);
        clientTelephoneView = clientView.findViewById(R.id.telephone);
    }

    public void bind(String prenom, String nom, String telephone) {
        clientPrenomView.setText(prenom);
        clientNomView.setText(nom);
        clientTelephoneView.setText(telephone);
    }

    public static ClientViewHolder create(ViewGroup parent) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ClientViewHolder(view);
    }
}
