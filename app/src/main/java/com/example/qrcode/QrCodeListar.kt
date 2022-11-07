package com.example.qrcode

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class QrCodeListar : AppCompatActivity() {

    private val obterResultado = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
        if (intentResult.contents != null) {

            startActivity(Intent(this, Listar::class.java)
                .putExtra(Listar.result, intentResult.contents))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_listar)

        val layout = findViewById<LinearLayout>(R.id.root04)
        val textView = findViewById<TextView>(R.id.listaDeProdutos)
        textView.setText(
            Html.fromHtml("<h1>Escaneie a lista de produtos</h1>" +
                "<br><br>"
                + "<h1>Clique no botão para começar </h1>", Html.FROM_HTML_MODE_COMPACT))

        val btnLerQrCode: Button = findViewById(R.id.btnLerQrCode)
        btnLerQrCode.setOnClickListener{
            val integrator: IntentIntegrator = IntentIntegrator(this)
            integrator.setPrompt("Leitura de QRCode")
            obterResultado.launch(integrator.createScanIntent())
        }

    }
}


