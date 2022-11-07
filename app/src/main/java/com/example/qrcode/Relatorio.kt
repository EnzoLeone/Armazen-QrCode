package com.example.qrcode

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Relatorio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)

        val layout = findViewById<LinearLayout>(R.id.root03)

        val retornoEstoque = intent.getSerializableExtra("Produtos") as ArrayList<Listar.Produto>

        for (x in retornoEstoque) {
            val textView = TextView(this)
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.layoutParams = layoutParams
            layoutParams.setMargins(20,0,0,0)
            textView.setText(Html.fromHtml("<p>Cod EAN13: ${x.id}</p>" +
                    "<p>Produto: ${x.nome}</p>" +
                    "<p>Quantidade retirada: ${x.quantidadePedido}</p>" +
                    "<p>Saldo estoque: ${x.quantidade}</p><hl>", Html.FROM_HTML_MODE_COMPACT))
            layout.addView(textView)
            val view = View(this)
            val viewLP:LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                5)
            view.layoutParams = viewLP
            view.setBackgroundColor(Color.parseColor("#000000"))
            layout.addView(view)
        }

        val btnNovaSeparacao: Button = findViewById(R.id.btnNovaSeparacao)
        btnNovaSeparacao.setOnClickListener{
            val intent = Intent (this, QrCodeListar::class.java)
            startActivity(intent)
        }

        val btnVoltar: Button = findViewById(R.id.btnVoltar)
        btnVoltar.setOnClickListener{
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
        }

    }
}