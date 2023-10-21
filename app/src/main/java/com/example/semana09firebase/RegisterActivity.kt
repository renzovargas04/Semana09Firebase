package com.example.semana09firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.semana09firebase.model.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtFullName: EditText = findViewById(R.id.edtFullName)
        val edtCountry: EditText = findViewById(R.id.edtCountry)
        val edtEmailRegister: EditText = findViewById(R.id.edtEmailRegister)
        val edtPassRegister: EditText = findViewById(R.id.edtPassRegister)
        val btnSaveRegister: Button = findViewById(R.id.btnSaveRegister)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")

        btnSaveRegister.setOnClickListener {
            val correo = edtEmailRegister.text.toString()
            val clave = edtPassRegister.text.toString()
            val nombreCompleto = edtFullName.text.toString()
            val pais = edtCountry.text.toString()

            auth.createUserWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        //Se registroó en Firebase Auth y deberá registrarse en Firestore
                        val user: FirebaseUser? = auth.currentUser
                        val uid = user?.uid

                        val userModel = UserModel(correo, clave, nombreCompleto, pais, uid.toString())
                        collectionRef.add(userModel)
                            .addOnCompleteListener{

                            }.addOnFailureListener{error ->
                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content)
                                        ,"Ocurrió un error al registrar el modelo"
                                        ,Snackbar.LENGTH_LONG
                                    ).show()
                            }

                    }else{
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Ocurrió un error al registrarse"
                                ,Snackbar.LENGTH_LONG
                            ).show()
                    }

                    Snackbar
                        .make(
                            findViewById(android.R.id.content)
                            ,"Registro exitoso del usuario"
                            ,Snackbar.LENGTH_LONG
                        ).show()
                }

        }

    }
}