package com.example.chivo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private FirebaseAuth mAuth;

    private Button btn_registro, btn_ingreso;
    private ImageButton btn_view;
    private EditText edit_email, edit_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        btn_ingreso = findViewById(R.id.button_ingreso);
        btn_registro = findViewById(R.id.button_registro);
        btn_view = findViewById(R.id.imageButtonView);
        edit_email = findViewById(R.id.editTextTextEmailAddress);
        edit_contrasena = findViewById(R.id.editTextTextPassword);

        SharedPreferences emailPreferences = getSharedPreferences("correos", Context.MODE_PRIVATE);
        edit_email.setText(emailPreferences.getString("mail", ""));
    }

    public void Ingreso (View view){
        String correo = edit_email.getText().toString();
        String contrasena = edit_contrasena.getText().toString();

        SharedPreferences emailPreferences = getSharedPreferences("correos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = emailPreferences.edit();
        editor.putString("mail", correo);
        editor.commit();

        if (correo.length() == 0){
            if (contrasena.length() == 0){
                Toast.makeText(this,
                        "Ambos campos están vacíos, llénalos para continuar", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,
                        "El campo de correo está vacío, llénalo para continuar", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if (contrasena.length() == 0){
                Toast.makeText(this,
                        "El campo de contraseña está vacío, llénalo para continuar", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.signInWithEmailAndPassword(correo, contrasena)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Acceso concedido", Toast.LENGTH_SHORT).show();
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Acceso denegado", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        }
    }

    public void Registro (View view){
        Toast.makeText(this, "Registro concedido", Toast.LENGTH_SHORT).show();
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}