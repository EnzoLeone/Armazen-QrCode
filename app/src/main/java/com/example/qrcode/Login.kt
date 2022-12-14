package com.example.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnConectar: Button = findViewById(R.id.btnConectar)
        btnConectar.setOnClickListener{

            val usuarios = HashMap<String, String>()
            usuarios["Enzo"] = "Enzo|Enzo Leone|1234"
            usuarios["Diego"] = "Diego|Diego Louzeiro|1234"
            usuarios["Victor"] = "Victor|Victor Sanches|1234"
            usuarios["Yago"] = "Yago|Yago Matos|1234"
            usuarios["Weverton"] = "Weverton|Weverton Viana|1234"
            usuarios["Vitor"] = "Vitor|Vitor Alexandre|1234"
            usuarios["Maycon"] = "Maycon|Maycon Calvacante|1234"
            usuarios["Iuri"] = "Iuri|Iuri Martins|1234"
            usuarios["Nahuan"] = "Nahuan|Nahuan Abreu|1234"

            val edtUsuario : EditText = findViewById(R.id.edtUsuario)
            edtUsuario.requestFocus()
            val edtSenha : EditText = findViewById(R.id.edtSenha)
            btnConectar.setOnClickListener{

                val strUsuario:String = edtUsuario.text.toString()
                val strSenha:String = edtSenha.text.toString()

                val usuario = usuarios[strUsuario]?.split("|")
                if (usuario?.get(0)?.compareTo(strUsuario) != 0) {
                    Toast.makeText(applicationContext, "Usuario Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                    edtUsuario.setText("")
                }else if (usuario?.get(2)?.compareTo(strSenha) != 0) {
                    Toast.makeText(
                        applicationContext,
                        "Senha Incorreta. Tente novamente.",
                        Toast.LENGTH_LONG
                    ).show()
                    edtSenha.setText("")
                }else {
                    Toast.makeText(
                        applicationContext,
                        "Seja bem vindo "+usuario.get(1).toString()+".",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent (this, QrCodeListar::class.java)
                    startActivity(intent)
                }
                val strConectar = "Usu??rio: "+edtUsuario.text+" Senha: "+edtSenha.text
                Toast.makeText(applicationContext, strConectar, Toast.LENGTH_LONG).show()
            }
        }

        val btnFechar: Button = findViewById(R.id.btnFechar)
        btnFechar.setOnClickListener {
            finish()
        }
    }
}