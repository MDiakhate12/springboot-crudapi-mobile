package com.crudapi.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.crudapi.mobile.client.Client;

public class NewClientActivity extends AppCompatActivity {

    public static final String PRENOM_EXTRA_REPLY = "com.crudapi.android.client.PRENOM_REPLY";
    public static final String NOM_EXTRA_REPLY = "com.crudapi.android.client.NOM_EXTRA_REPLY";
    public static final String TELEPHONE_EXTRA_REPLY = "com.crudapi.android.client.TELEPHONE_EXTRA_REPLY";

    public static final String ID_EXTRA_REQUEST = "com.crudapi.android.client.ID_REQUEST";
    public static final String PRENOM_EXTRA_REQUEST = "com.crudapi.android.client.PRENOM_REQUEST";
    public static final String NOM_EXTRA_REQUEST = "com.crudapi.android.client.NOM_EXTRA_REQUEST";
    public static final String TELEPHONE_EXTRA_REQUEST = "com.crudapi.android.client.TELEPHONE_EXTRA_REQUEST";

    private EditText editPrenomView;
    private EditText editNomView;
    private EditText editTelephoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        editPrenomView = findViewById(R.id.edit_prenom);
        editNomView = findViewById(R.id.edit_nom);
        editTelephoneView = findViewById(R.id.edit_telephone);

        final Button saveButton = findViewById(R.id.save_button);

        Intent sourceIntent = getIntent();

        if(sourceIntent != null) {
            editPrenomView.setText(sourceIntent.getStringExtra(PRENOM_EXTRA_REQUEST));
            editNomView.setText(sourceIntent.getStringExtra(NOM_EXTRA_REQUEST));
            editTelephoneView.setText(sourceIntent.getStringExtra(TELEPHONE_EXTRA_REQUEST));
        }

        saveButton.setOnClickListener(view -> {
            Intent replyItent = new Intent();
            Log.d("DIAF",
                    ""+(TextUtils.isEmpty(editPrenomView.getText()) ||
                            TextUtils.isEmpty(editNomView.getText()) ||
                            TextUtils.isEmpty(editTelephoneView.getText())));

            if(
                    TextUtils.isEmpty(editPrenomView.getText()) ||
                    TextUtils.isEmpty(editNomView.getText()) ||
                    TextUtils.isEmpty(editTelephoneView.getText())
            ) {
                setResult(RESULT_CANCELED, replyItent);
            } else {
                Log.d("DIAF ELSE", editPrenomView.getText().toString());
                replyItent.putExtra(PRENOM_EXTRA_REPLY, editPrenomView.getText().toString());
                replyItent.putExtra(NOM_EXTRA_REPLY, editNomView.getText().toString());
                replyItent.putExtra(TELEPHONE_EXTRA_REPLY, editTelephoneView.getText().toString());

                setResult(RESULT_OK, replyItent);
            }
            finish();
        });

    }

}