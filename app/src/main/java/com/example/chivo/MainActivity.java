package com.example.chivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView NombreUsuario, Tiempo;
    LinearLayout LinearVicente, LinearLopez, LinearTime;
    ListView listDispach, listCharge;
    Time today = new Time(Time.getCurrentTimezone());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Chivo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String uid = usuario.getUid();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        NombreUsuario = findViewById(R.id.txt_username);
        LinearVicente = findViewById(R.id.linearLayoutV);
        LinearLopez = findViewById(R.id.linearLayoutL);
        LinearTime = findViewById(R.id.linearLayout2);
        listDispach = findViewById(R.id.listDespacho);
        listCharge = findViewById(R.id.listCarga);
        Tiempo = findViewById(R.id.txt_bienvenida);
        mAuth = FirebaseAuth.getInstance();
        String nombre = usuario.getDisplayName();
        today.setToNow();

        NombreUsuario.setText(today.format("%k:%M:%S"));

        if (today.hour >= 6){
            LinearTime.setBackgroundColor(getResources().getColor(R.color.día));
            Tiempo.setText(R.string.string_dia);
        }

        if (today.hour >= 12){
            LinearTime.setBackgroundColor(getResources().getColor(R.color.tarde));
            Tiempo.setText(R.string.string_tarde);
        }

        if (today.hour >= 19){
            LinearTime.setBackgroundColor(getResources().getColor(R.color.noche));
            Tiempo.setText(R.string.string_noche);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<DBdata> historialD = new ArrayList<>();
        ArrayList<DBdata2> historialC = new ArrayList<>();

        db.collection("Despachos")
                .orderBy("Folio", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list){
                                DBdata dBdata = d.toObject(DBdata.class);
                                historialD.add(dBdata);
                            }
                            LVAdapter adapter = new LVAdapter(MainActivity.this, historialD);
                            listDispach.setAdapter(adapter);
                        }
                    }
                });

        db.collection("Cargas")
                .limit(10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list){
                                DBdata2 dBdata = d.toObject(DBdata2.class);
                                historialC.add(dBdata);
                            }
                            LVAdapter2 adapter = new LVAdapter2(MainActivity.this, historialC);
                            listCharge.setAdapter(adapter);
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.item_cobranza){
            Intent cobranza = new Intent(this, CollectionActivity.class);
            startActivity(cobranza);
        }
        if (id == R.id.item_nuevoCliente){

        }
        if (id == R.id.item_cerrarSesion){
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void Vicente (View view){
        Intent vicente = new Intent(this, HistoryVicenteActivity.class);
        startActivity(vicente);
    }

    public void Lopez (View view){
        Intent lopez = new Intent(this, HistoryLopezActivity.class);
        startActivity(lopez);
    }

    public void General (View view){
        Intent general = new Intent(this, HistoryGeneralActivity.class);
        startActivity(general);
    }

    public void Despacho (View view){
        Intent despacho = new Intent(this, NewDispatchActivity.class);
        startActivity(despacho);
    }

    public void Carga (View view){
        Intent carga = new Intent(this, NewChargeActivity.class);
        startActivity(carga);
    }

    public void CRUD (View view){
        Intent crud = new Intent(this, CrudActivity.class);
        startActivity(crud);
    }

    public void CRUD2 (View view){
        Intent crud = new Intent(this, CrudActivity2.class);
        startActivity(crud);
    }
}