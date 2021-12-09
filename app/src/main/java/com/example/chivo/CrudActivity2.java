package com.example.chivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CrudActivity2 extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView txt_sucursal, txt_proveedor, txt_fecha, txt_hora, txt_litros;
    private RadioButton radio_eliminar, radio_modificar;
    private EditText edit_sucursal, edit_proveedor, edit_fecha, edit_hora, edit_litros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud2);
        setTitle("Detalles de carga");

        String sucursal = getIntent().getStringExtra("Sucursal");
        String proveedor = getIntent().getStringExtra("Proveedor");
        String fecha = getIntent().getStringExtra("Fecha");
        String hora = getIntent().getStringExtra("Hora");
        String litros = getIntent().getStringExtra("Litros");

        txt_sucursal = (TextView) findViewById(R.id.txt_SucursalC);
        txt_proveedor = (TextView)findViewById(R.id.txt_ProveedorC);
        txt_fecha = (TextView)findViewById(R.id.txt_FechaC);
        txt_hora = (TextView)findViewById(R.id.txt_HoraC);
        txt_litros = (TextView)findViewById(R.id.txt_LitrosC);

        txt_sucursal.setText("Sucursal: " + sucursal);
        txt_proveedor.setText("Proveedor: " + proveedor);
        txt_fecha.setText("Fecha de carga: " + fecha);
        txt_hora.setText("Hora de carga: " + hora);
        txt_litros.setText("Litros cargados: " + litros);

        radio_eliminar = (RadioButton)findViewById(R.id.radio_EliminarC);
        radio_modificar = (RadioButton)findViewById(R.id.radio_ModificarC);

        edit_sucursal = (EditText)findViewById(R.id.edit_SucursalC);
        edit_proveedor = (EditText)findViewById(R.id.edit_ProveedorC);
        edit_fecha = (EditText)findViewById(R.id.edit_FechaC);
        edit_hora = (EditText)findViewById(R.id.edit_HoraC);
        edit_litros = (EditText)findViewById(R.id.edit_LitrosC);

        edit_sucursal.setText(sucursal);
        edit_proveedor.setText(proveedor);
        edit_fecha.setText(fecha);
        edit_hora.setText(hora);
        edit_litros.setText(litros);

        radio_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_sucursal.setEnabled(true);
                edit_proveedor.setEnabled(true);
                edit_fecha.setEnabled(true);
                edit_hora.setEnabled(true);
                edit_litros.setEnabled(true);
            }
        });

        radio_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_sucursal.setEnabled(false);
                edit_proveedor.setEnabled(false);
                edit_fecha.setEnabled(false);
                edit_hora.setEnabled(false);
                edit_litros.setEnabled(false);
            }
        });
    }

    public void EfectuarCambios (View view){

        String fecha = getIntent().getStringExtra("Fecha");
        String litros = getIntent().getStringExtra("Litros");

        if (radio_modificar.isChecked()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Modificación");
            alertDialogBuilder
                    .setMessage("¿Estás seguro de aplicar los cambios correspondientes?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            db.collection("Cargas")
                                    .whereEqualTo("Fecha", fecha)
                                    .whereEqualTo("Litros", litros)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            DocumentSnapshot d = task.getResult().getDocuments().get(0);
                                            String documentID = d.getId();

                                            db.collection("Cargas")
                                                    .document(documentID)
                                                    .update("Sucursal", edit_sucursal.getText().toString(),
                                                            "Proveedor", edit_proveedor.getText().toString(),
                                                            "Fecha", edit_fecha.getText().toString(),
                                                            "Hora", edit_hora.getText().toString(),
                                                            "Litros", edit_litros.getText().toString()
                                                    )
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(CrudActivity2.this, "Actualización exitosamente realizada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(CrudActivity2.this, "Actualización no realizada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CrudActivity2.this, "Cambios cancelados", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
        }

        if (radio_eliminar.isChecked()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Eliminación");
            alertDialogBuilder
                    .setMessage("¿Estás seguro de que quieres eliminar el registro?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.collection("Cargas")
                                    .whereEqualTo("Fecha", fecha)
                                    .whereEqualTo("Litros", litros)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            DocumentSnapshot d = task.getResult().getDocuments().get(0);
                                            String documentID = d.getId();

                                            db.collection("Cargas")
                                                    .document(documentID)
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(CrudActivity2.this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(CrudActivity2.this, "Registro no pudo ser eliminado", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CrudActivity2.this, "Eliminación cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
        }
    }
}