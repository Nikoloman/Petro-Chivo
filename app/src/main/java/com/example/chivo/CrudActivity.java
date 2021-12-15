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

public class CrudActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView txt_sucursal, txt_folio, txt_fecha, txt_hora, txt_ruta, txt_dueño, txt_placas,
                     txt_unidad, txt_litros, txt_estatus;
    private RadioButton radio_eliminar, radio_modificar;
    private EditText edit_sucursal, edit_folio, edit_fecha, edit_hora, edit_ruta, edit_dueño,
                     edit_placas, edit_unidad, edit_litros;
    private CheckBox check_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        setTitle("Detalles de venta");

        String sucursal = getIntent().getStringExtra("Sucursal");
        int folio = getIntent().getIntExtra("Folio", 0);
        String fecha = getIntent().getStringExtra("Fecha");
        String hora = getIntent().getStringExtra("Hora");
        String ruta = getIntent().getStringExtra("Ruta");
        String dueño = getIntent().getStringExtra("Dueño");
        String placas = getIntent().getStringExtra("Placas");
        String unidad = getIntent().getStringExtra("Unidad");
        float litros = getIntent().getFloatExtra("Litros", 0);
        String estatus = getIntent().getStringExtra("Estatus");

        txt_sucursal = (TextView)findViewById(R.id.txt_SucursalT);
        txt_folio = (TextView)findViewById(R.id.txt_FolioT);
        txt_fecha = (TextView)findViewById(R.id.txt_FechaT);
        txt_hora = (TextView)findViewById(R.id.txt_HoraT);
        txt_ruta = (TextView)findViewById(R.id.txt_RutaT);
        txt_dueño = (TextView)findViewById(R.id.txt_DueñoT);
        txt_placas = (TextView)findViewById(R.id.txt_PlacasT);
        txt_unidad = (TextView)findViewById(R.id.txt_UnidadT);
        txt_litros = (TextView)findViewById(R.id.txt_LitrosT);
        txt_estatus = (TextView)findViewById(R.id.txt_StatusT);

        radio_eliminar = (RadioButton)findViewById(R.id.radio_Eliminar);
        radio_modificar = (RadioButton)findViewById(R.id.radio_Modificar);
        check_status = (CheckBox)findViewById(R.id.checkBox_Status);

        edit_sucursal = (EditText)findViewById(R.id.edit_Sucursal);
        edit_folio = (EditText)findViewById(R.id.edit_Folio);
        edit_fecha = (EditText)findViewById(R.id.edit_Fecha);
        edit_hora = (EditText)findViewById(R.id.edit_Hora);
        edit_ruta= (EditText)findViewById(R.id.edit_Ruta);
        edit_dueño = (EditText)findViewById(R.id.edit_Dueño);
        edit_placas = (EditText)findViewById(R.id.edit_Placas);
        edit_unidad = (EditText)findViewById(R.id.edit_Unidad);
        edit_litros = (EditText)findViewById(R.id.edit_Litros);

        txt_sucursal.setText("Sucursal: " + sucursal);
        txt_folio.setText("Folio de Ticket: " + folio);
        txt_fecha.setText("Fecha de despacho: " + fecha);
        txt_hora.setText("Hora de despacho: " + hora);
        txt_ruta.setText("Ruta de camión: " + ruta);
        txt_dueño.setText("Dueño: " + dueño);
        txt_placas.setText("Placas: " + placas);
        txt_unidad.setText("Unidad: " + unidad);
        txt_litros.setText("Litros despachados: " + litros);
        txt_estatus.setText("Estatus de pago: " + estatus);

        edit_sucursal.setText(sucursal);
        edit_folio.setText(""+folio);
        edit_fecha.setText(fecha);
        edit_hora.setText(hora);
        edit_ruta.setText(ruta);
        edit_dueño.setText(dueño);
        edit_placas.setText(placas);
        edit_unidad.setText(unidad);
        edit_litros.setText(""+litros);

        if (estatus.matches("Pagado")){
            check_status.setChecked(true);
        }
        else{
            check_status.setChecked(false);
        }

        radio_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_sucursal.setEnabled(true);
                edit_folio.setEnabled(true);
                edit_fecha.setEnabled(true);
                edit_hora.setEnabled(true);
                edit_ruta.setEnabled(true);
                edit_dueño.setEnabled(true);
                edit_placas.setEnabled(true);
                edit_unidad.setEnabled(true);
                edit_litros.setEnabled(true);
                check_status.setEnabled(true);
            }
        });

        radio_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_sucursal.setEnabled(false);
                edit_folio.setEnabled(false);
                edit_fecha.setEnabled(false);
                edit_hora.setEnabled(false);
                edit_ruta.setEnabled(false);
                edit_dueño.setEnabled(false);
                edit_placas.setEnabled(false);
                edit_litros.setEnabled(false);
                edit_unidad.setEnabled(false);
                check_status.setEnabled(false);
            }
        });
    }

    public void DialogConfirmation (View view){

        int folio = getIntent().getIntExtra("Folio", 0);
        float litros = getIntent().getFloatExtra("Litros", 0);
        String checked_status;

        if (check_status.isChecked()){
            checked_status = "Pagado";
        }
        else{
            checked_status = "No pagado";
        }

        if (radio_modificar.isChecked()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Modificación");
            alertDialogBuilder
                    .setMessage("¿Estás seguro de aplicar los cambios correspondientes?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            db.collection("Despachos")
                                    .whereEqualTo("Folio", folio)
                                    .whereEqualTo("Litros", litros)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            DocumentSnapshot d = task.getResult().getDocuments().get(0);
                                            String documentID = d.getId();

                                            db.collection("Despachos")
                                                    .document(documentID)
                                                    .update("Sucursal", edit_sucursal.getText().toString(),
                                                            "Folio", Integer.parseInt(edit_folio.getText().toString()),
                                                            "Fecha", edit_fecha.getText().toString(),
                                                            "Hora", edit_hora.getText().toString(),
                                                            "Ruta", edit_ruta.getText().toString(),
                                                            "Dueño", edit_dueño.getText().toString(),
                                                            "Placas", edit_placas.getText().toString(),
                                                            "Unidad", edit_unidad.getText().toString(),
                                                            "Litros", Float.parseFloat(edit_litros.getText().toString()),
                                                            "Estatus", checked_status
                                                    )
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(CrudActivity.this, "Actualización exitosamente realizada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(CrudActivity.this, "Actualización no realizada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CrudActivity.this, "Cambios cancelados", Toast.LENGTH_SHORT).show();
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
                            db.collection("Despachos")
                                    .whereEqualTo("Folio", folio)
                                    .whereEqualTo("Litros", litros)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            DocumentSnapshot d = task.getResult().getDocuments().get(0);
                                            String documentID = d.getId();

                                            db.collection("Despachos")
                                                    .document(documentID)
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(CrudActivity.this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(CrudActivity.this, "Registro no pudo ser eliminado", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CrudActivity.this, "Eliminación cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
        }
    }
}