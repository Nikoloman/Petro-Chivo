package com.example.chivo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HistoryLopezActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edit_fecha, edit_dueño, edit_ruta;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_lopez);
        setTitle("Lopez Mateos");

        edit_fecha = findViewById(R.id.edit_fechaL);
        edit_dueño = findViewById(R.id.edit_dueñoL);
        edit_ruta = findViewById(R.id.edit_rutaL);
        listView = findViewById(R.id.List_HistoryL);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, año);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        String fechaActual = DateFormat.getDateInstance().format(c.getTime());
        edit_fecha.setText(fechaActual);
    }

    public void Calendario (View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void ConsultaHL (View view) {

        ArrayList<DBdata> historial = new ArrayList<>();

        String fecha = edit_fecha.getText().toString();
        String ruta = edit_ruta.getText().toString();
        String dueño = edit_dueño.getText().toString();

        if (fecha.isEmpty() && ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && ruta.isEmpty() && !dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Dueño", dueño)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && !ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Ruta", ruta)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && !ruta.isEmpty() && !dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Ruta", ruta)
                    .whereEqualTo("Dueño", dueño)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Fecha", fecha)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && ruta.isEmpty() && !dueño.isEmpty()){
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Dueño", dueño)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && !ruta.isEmpty() && dueño.isEmpty()){
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Ruta", ruta)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && !ruta.isEmpty() && !dueño.isEmpty()){
            db.collection("Despachos")
                    .whereIn("Sucursal", Arrays.asList("Lopez Mateos", "López Mateos"))
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Ruta", ruta)
                    .whereEqualTo("Dueño", dueño)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    DBdata dBdata = d.toObject(DBdata.class);
                                    historial.add(dBdata);
                                }
                                LVAdapter adapter = new LVAdapter(HistoryLopezActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        }

    }
}