package com.example.chivo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewDispatchActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText txt_sucursal, txt_folio, txt_fecha, txt_hora, txt_ruta, txt_dueño, txt_placas, txt_unidad, txt_litros;
    CheckBox checkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dispatch);

        txt_sucursal = findViewById(R.id.edit_Sucursal);
        txt_folio = findViewById(R.id.edit_Folio);
        txt_fecha = findViewById(R.id.edit_Fecha);
        txt_hora = findViewById(R.id.edit_Hora);
        txt_ruta = findViewById(R.id.edit_Ruta);
        txt_dueño = findViewById(R.id.edit_Dueño);
        txt_placas = findViewById(R.id.edit_Placas);
        txt_unidad = findViewById(R.id.edit_Unidad);
        txt_litros = findViewById(R.id.edit_Litros);

        checkStatus = findViewById(R.id.checkBox_Status);
    }

    public void AgregarDespacho (View view){

        Map<String, Object> despacho = new HashMap<>();
        despacho.put("Sucursal", txt_sucursal.getText().toString());
        despacho.put("Folio", txt_folio.getText().toString());
        despacho.put("Fecha", txt_fecha.getText().toString());
        despacho.put("Hora", txt_hora.getText().toString());
        despacho.put("Ruta", txt_ruta.getText().toString());
        despacho.put("Dueño", txt_dueño.getText().toString());
        despacho.put("Placas", txt_placas.getText().toString());
        despacho.put("Unidad", txt_unidad.getText().toString());
        despacho.put("Litros", txt_litros.getText().toString());
        if (checkStatus.isChecked()){
            despacho.put("Estatus de pago", "Pagado");
        }
        else {
            despacho.put("Estatus de pago", "No pagado");
        }

        db.collection("Despachos")
                .add(despacho)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NewDispatchActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewDispatchActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });

        txt_sucursal.setText(null);
        txt_folio.setText(null);
        txt_fecha.setText(null);
        txt_hora.setText(null);
        txt_ruta.setText(null);
        txt_dueño.setText(null);
        txt_placas.setText(null);
        txt_unidad.setText(null);
        txt_litros.setText(null);

        checkStatus.setChecked(false);
    }
}