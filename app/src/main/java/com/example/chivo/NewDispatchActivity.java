package com.example.chivo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDispatchActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference referencia = db.collection("Rutas");
    EditText txt_folio, txt_fecha, txt_hora, txt_placas, txt_litros;
    CheckBox checkStatus;
    Spinner txt_sucursal, txt_ruta, txt_dueño, txt_unidad;

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

        ArrayList Sucursales = new ArrayList();
        ArrayList Rutas = new ArrayList();
        ArrayList Dueños = new ArrayList();
        ArrayList Unidades = new ArrayList();

        Sucursales.add("Sucursales");
        Sucursales.add("Vicente Guerrero");
        Sucursales.add("Lopez Mateos");
        Rutas.add("Ruta");
        Dueños.add("Dueño");
        Unidades.add("Unidad");

        ArrayAdapter <String> sucursal = new ArrayAdapter<String>(this, R.layout.spinner_item_diesel, Sucursales);
        ArrayAdapter <String> dueños = new ArrayAdapter<String>(this, R.layout.spinner_item_diesel, Dueños);
        ArrayAdapter <String> rutas = new ArrayAdapter<String>(this, R.layout.spinner_item_diesel, Rutas);
        rutas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter <String> unidades = new ArrayAdapter<String>(this, R.layout.spinner_item_diesel, Unidades);

        txt_sucursal.setAdapter(sucursal);
        txt_dueño.setAdapter(dueños);
        txt_ruta.setAdapter(rutas);
        txt_unidad.setAdapter(unidades);

        referencia.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot d : task.getResult()){
                        String ruta = d.getId();
                        Rutas.add(ruta);
                    }
                    rutas.notifyDataSetChanged();
                }
            }
        });

        txt_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Dueños.clear();
                Dueños.add("Dueño");
                txt_dueño.setSelection(0);

                String rutaSeleccionada = adapterView.getItemAtPosition(i).toString();

                db.collection("Rutas")
                        .document(rutaSeleccionada)
                        .collection("Dueños")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot d : task.getResult()){
                                String dueño = d.getId();
                                Dueños.add(dueño);
                            }
                            dueños.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txt_dueño.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Unidades.clear();
                Unidades.add("Unidad");
                txt_unidad.setSelection(0);

                db.collection("Rutas")
                        .document(txt_ruta.getSelectedItem().toString())
                        .collection("Dueños")
                        .document(adapterView.getItemAtPosition(i).toString())
                        .collection("Unidades")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot d : task.getResult()){
                                        String unidad = d.getId();
                                        Unidades.add(unidad);
                                    }
                                    unidades.notifyDataSetChanged();
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void AgregarDespacho (View view){

        Map<String, Object> despacho = new HashMap<>();
        despacho.put("Sucursal", txt_sucursal.getSelectedItem().toString());
        despacho.put("Folio", Integer.parseInt(txt_folio.getText().toString()));
        despacho.put("Fecha", txt_fecha.getText().toString());
        despacho.put("Hora", txt_hora.getText().toString());
        despacho.put("Ruta", txt_ruta.getSelectedItem().toString());
        despacho.put("Dueño", txt_dueño.getSelectedItem().toString());
        despacho.put("Placas", txt_placas.getText().toString());
        despacho.put("Unidad", txt_unidad.getSelectedItem().toString());
        despacho.put("Litros", Float.parseFloat(txt_litros.getText().toString()));
        if (checkStatus.isChecked()){
            despacho.put("Estatus", "Pagado");
        }
        else {
            despacho.put("Estatus", "No pagado");
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

        txt_sucursal.setSelection(0);
        txt_folio.setText(null);
        txt_fecha.setText(null);
        txt_hora.setText(null);
        txt_ruta.setSelection(0);
        txt_dueño.setSelection(0);
        txt_placas.setText(null);
        txt_unidad.setSelection(0);
        txt_litros.setText(null);


        checkStatus.setChecked(false);
    }
}