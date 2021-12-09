package com.example.chivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewChargeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText txt_sucursal, txt_proveedor, txt_fecha, txt_hora, txt_litros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_charge);

        txt_sucursal = findViewById(R.id.edit_SucursalC);
        txt_proveedor = findViewById(R.id.edit_ProveedorC);
        txt_fecha = findViewById(R.id.edit_FechaC);
        txt_hora = findViewById(R.id.edit_HoraC);
        txt_litros = findViewById(R.id.edit_LitrosC);
        
    }
    
    public void AgregarCarga (View view){

        Map<String, Object> carga = new HashMap<>();
        carga.put("Sucursal", txt_sucursal.getText().toString());
        carga.put("Proveedor", txt_proveedor.getText().toString());
        carga.put("Fecha", txt_fecha.getText().toString());
        carga.put("Hora", txt_hora.getText().toString());
        carga.put("Litros", txt_litros.getText().toString());

        db.collection("Cargas")
                .add(carga)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NewChargeActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewChargeActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}