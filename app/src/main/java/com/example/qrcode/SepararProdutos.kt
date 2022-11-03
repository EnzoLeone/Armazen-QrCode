package com.example.qrcode

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.qrcode.Listar.Companion.result
import com.google.zxing.integration.android.IntentIntegrator

class SepararProdutos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_separar_produtos)




        fun ChamarQRcode (obterResultado: ActivityResultLauncher<Intent>, propriedade: String) {
            val integrator: IntentIntegrator = IntentIntegrator(this)
            integrator.setPrompt("Ler ${propriedade}")
            obterResultado.launch(integrator.createScanIntent())
        }

        fun Qrcodenovo(
            botao: Button? = null,
            produto:Listar.Produto,
            produtos:ArrayList<Listar.Produto>,
            propriedade: String
        ) {
            val obterResultado =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
                    if (intentResult.contents != null) {

                        startActivity(
                            Intent(this, SepararProdutos::class.java)
                                .putExtra("QRCode", intentResult.contents)
                                .putExtra("Produto", produto)
                                .putExtra("Produtos", produtos)
                        )
                    }
                }

            if (botao != null) {
                botao.setOnClickListener{
                    ChamarQRcode(obterResultado, propriedade)
                }
            }else {
                ChamarQRcode(obterResultado, propriedade)
            }

        }

        fun ProximoProduto (estoque: ArrayList<Listar.Produto>) {


            startActivity(
                Intent(this, SepararProdutos::class.java)
                    .putExtra("Produtos", estoque)
            )
        }


        val layout = findViewById<LinearLayout>(R.id.root02)

        val result = intent.getSerializableExtra("Produtos") as ArrayList<Listar.Produto>?
        val qrCodeResulte = intent.getStringExtra("QRCode") as String?
        val produtoScaneado = intent.getSerializableExtra("Produto") as Listar.Produto?


        val btnLerQrCode2: Button = findViewById(R.id.btnLerQrCode2)

        if (result != null) {
            if (produtoScaneado == null) {
                for (x in result){
                    if (!x.scaneado ) {
                        val textView = TextView(this)
                        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        textView.layoutParams = layoutParams
                        textView.text = "${x.nome} - RUA ${x.rua} - Nº ${x.numero} - ANDAR" +
                                " ${x.andar}" + " - QUANTIDADE ${x.quantidadePedido}"
                        layout.addView(textView)

                        x.quantidadePedido = 2
                        Qrcodenovo(botao = btnLerQrCode2,
                            produto = x,
                            produtos = result,
                            propriedade = "Rua ${x.rua}")

                        break
                    }
                }
            }


            if (qrCodeResulte != null && produtoScaneado != null) {

                println(qrCodeResulte)

                val rua = "R${produtoScaneado.rua[0]}"
                val numero = "${rua}-N${produtoScaneado.numero}"
                val andar = "${numero}-${produtoScaneado.andar}A"
                val scaneados: ArrayList<String> = ArrayList()
                scaneados.addAll(produtoScaneado.scans)
                var proximoProduto = false
                var propriedade = ""

                if (qrCodeResulte == rua) {
                    println("Rua")
                    propriedade = "Número ${produtoScaneado.numero}"
                    produtoScaneado.scans.add(rua)
                }else {
                    if (scaneados.size == 0) {
                        propriedade = "Rua ${produtoScaneado.rua}"
                        println("Rua errada")
                        Toast.makeText(applicationContext, "Rua Incorreta. Tente novamente.", Toast.LENGTH_LONG).show()
                    }
                }

                if (qrCodeResulte == numero) {
                    println("Numero")
                    propriedade = "Andar ${produtoScaneado.andar}"
                    produtoScaneado.scans.add(numero)
                }else {
                    if (scaneados.size != 0 && scaneados.last() == rua) {
                        propriedade = "Número ${produtoScaneado.numero}"
                        println("Numero errado")
                        Toast.makeText(applicationContext, "Número Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                    }
                }

                if (qrCodeResulte == andar) {
                    println("Andar")
                    propriedade = "Produto ${produtoScaneado.nome}"
                    produtoScaneado.scans.add(andar)
                }else {
                    if (scaneados.size != 0 && scaneados.last() == numero) {
                        propriedade = "Andar ${produtoScaneado.andar}"
                        println("Andar errado")
                        Toast.makeText(applicationContext, "Andar Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                    }
                }

                if (qrCodeResulte == produtoScaneado.id) {
                    println("Produto")
                    propriedade = "Produto ${produtoScaneado.nome}"
                    if (produtoScaneado.quantidade -1 >= 0) {
                        produtoScaneado.quantidadePedido--
                        produtoScaneado.quantidade--
                        println(produtoScaneado.quantidadePedido)
                    }else {
                        Toast.makeText(applicationContext, "Estoque insuficiente.", Toast.LENGTH_LONG).show()
                        proximoProduto = true
                    }
                    if (produtoScaneado.quantidadePedido == 0) {
                        produtoScaneado.scans.add(produtoScaneado.id)
                        proximoProduto = true
                        println(produtoScaneado.quantidade)
                    }

                }else {
                    if (scaneados.size != 0 && scaneados.last() == andar) {
                        println("Produto errado")
                        propriedade = "Produto ${produtoScaneado.nome}"
                        Toast.makeText(applicationContext, "Produto Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                    }
                }
                if (!proximoProduto) {
                    Qrcodenovo(produto = produtoScaneado, produtos = result, propriedade = propriedade)
                }else {
                    for (i in 0..result.size-1) {
                        if (produtoScaneado.id == result[i].id) {
                            if (i+1 != result.size) {
                                result[i].quantidade = produtoScaneado.quantidade
                                result[i].scaneado = true
                                ProximoProduto(estoque = result)
                            }
                        }
                    }
                }
            }
        }
    }
}