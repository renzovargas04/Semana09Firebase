package com.example.semana09firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()

        val edtEmail: EditText = findViewById(R.id.edtEmail)
        val edtPass: EditText = findViewById(R.id.edtPass)
        val btnLog: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        btnLog.setOnClickListener {
            val correo = edtEmail.text.toString()
            val clave = edtPass.text.toString()

            auth.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Inicio de sesión exitoso"
                                ,Snackbar.LENGTH_LONG
                            ).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Credenciales invalidos"
                                ,Snackbar.LENGTH_LONG
                            ).show()
                    }
                }
        }
    }
}