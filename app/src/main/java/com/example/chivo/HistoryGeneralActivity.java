package com.example.chivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HistoryGeneralActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edit_fecha, edit_dueño, edit_ruta;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_general);
        setTitle("Vicente Guerrero");

        edit_fecha = findViewById(R.id.edit_FechaV);
        edit_ruta = findViewById(R.id.edit_RutaV);
        edit_dueño = findViewById(R.id.edit_DueñoV);
        listView = findViewById(R.id.List_HistoryV);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, año);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        String fechaActual = dia + "/" + mes + "/" + año;
        edit_fecha.setText(fechaActual);
    }

    public void Calendario (View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void ConsultaHV (View view){
        ArrayList<DBdata> historial = new ArrayList<>();

        String fecha = edit_fecha.getText().toString();
        String ruta = edit_ruta.getText().toString();
        String dueño = edit_dueño.getText().toString();

        if (fecha.isEmpty() && ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && ruta.isEmpty() && !dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereEqualTo("Dueño", dueño)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && !ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereEqualTo("Ruta", ruta)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (fecha.isEmpty() && !ruta.isEmpty() && !dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereEqualTo("Ruta", ruta)
                    .whereEqualTo("Dueño", dueño)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && ruta.isEmpty() && dueño.isEmpty()) {
            db.collection("Despachos")
                    .whereEqualTo("Fecha", fecha)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && ruta.isEmpty() && !dueño.isEmpty()){
            db.collection("Despachos")
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Dueño", dueño)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && !ruta.isEmpty() && dueño.isEmpty()){
            db.collection("Despachos")
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Ruta", ruta)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        } else if (!fecha.isEmpty() && !ruta.isEmpty() && !dueño.isEmpty()){
            db.collection("Despachos")
                    .whereEqualTo("Fecha", fecha)
                    .whereEqualTo("Ruta", ruta)
                    .whereEqualTo("Dueño", dueño)
                    .orderBy("Folio", Query.Direction.DESCENDING)
                    .limit(50)
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
                                LVAdapter adapter = new LVAdapter(HistoryGeneralActivity.this, historial);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
        }
    }
}
