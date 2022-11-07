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
import java.io.Serializable

class Listar : AppCompatActivity() {

    companion object {
        const val result = "result"
    }

    var separarProdutos: ArrayList<Produto> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        val btnIniciar: Button = findViewById(R.id.btnIniciar)


        val btnSair: Button = findViewById(R.id.btnSair)
        btnSair.setOnClickListener{
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
        }

        val layout = findViewById<LinearLayout>(R.id.root)


        val result = intent.getStringExtra(result)

        val strResult = result?.split("|")

        val estoque = getEstoque()


        val produtosScaneados: ArrayList<Produto> = ArrayList()



        strResult?.forEach {
            val resultProduto = it.split(":")[0]
            val quantidade = it.split(":")[1].toInt()

            println(resultProduto)

            estoque.forEachIndexed {index, element ->


                if (resultProduto == element.id) {
                    element.quantidadePedido = quantidade
                    produtosScaneados.add(element)
                }
            }
        }


        btnIniciar.setOnClickListener{
            val intent = Intent (this, SepararProdutos::class.java)
            intent.putExtra("Produtos", produtosScaneados)
            startActivity(intent)
        }
        produtosScaneados.forEach { it ->


            val textView = TextView(this)
            val layoutParams:LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(20,0,0,0)
            textView.layoutParams = layoutParams
            textView.setText(
                Html.fromHtml("<p>Produto: ${it.nome}</p>" +
                        "<p>Quantidade: ${it.quantidadePedido}</p>" +
                        "<hr>", Html.FROM_HTML_MODE_COMPACT))
            layout.addView(textView)
            val view = View(this)
            val viewLP:LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                5)
            view.layoutParams = viewLP
            view.setBackgroundColor(Color.parseColor("#000000"))
            layout.addView(view)
        }

    }

    class Produto: Serializable {
        var id = ""
        var nome = ""
        var rua = ""
        var numero = ""
        var andar = ""
        var quantidade = 0
        var quantidadePedido = 0
        var scaneado = false
        var scans: ArrayList<String> = ArrayList()
    }

    fun getEstoque (): ArrayList<Produto> {


        val dados = arrayOf("REFRIGERANTE COCA-COLA LATA 350ML | 7894900010015 | A | 1 | 1 | 1000",
            "REFRIGERANTE COCA-COLA GARRAFA 2L | 7894900011517 | A | 1 | 2 | 1000",
            "REFRIGERANTE SODA LIMONADA ANTARTIC LATA 350ML | 7891991000833 | A | 1 | 3 | 1000",
            "REFRIGERANTE GUARANA ANTARCTICA LATA 350ML | 7891991011020 | A | 2 | 1 | 1000",
            "REFRIGERANTE GUARANA ANTARCTICA 2L | 7898712836870 | A | 2 | 2 | 1000",
            "REFRIGERANTE FANTA LARANJA 2L | 7894900039924 | A | 2 | 3 | 1000",
            "REFRIGERANTE FANTA LARANJA LATA 350ML | 7894900031201 | A | 2 | 4 | 1000",
            "REFRIGERANTE PEPSI LATA 350ML | 7892840800079 | A | 3 | 1 | 1000",
            "REFRIGERANTE PEPSI 2L | 7892840813017 | A | 3 | 2 | 1000",
            "SUCRILHOS KELLOGG'S ORIGINAL 250G | 7896004000855 | B | 1 | 1 | 1000",
            "SUCRILHOS KELLOGG'S CHOCOLATE 320G | 7896004003979 | B | 1 | 2 | 1000",
            "PAPEL HIGIÊNICO PERSONAL FOLHA SIMPLES NEUTRO 60 METROS 4 UNIDADES | 7896110005140 | B | 2 | 1 | 1000",
            "PAPEL HIGIÊNICO MILI 4R | 7896104998953 | B | 2 | 2 | 1000",
            "PAPEL HIGIENICO DAMA 60MTR | 7896076002146 | B | 2 | 3 | 1000",
            "ARROZ AGULHINHA ARROZAL T1 5KG | 7896276060021 | C | 1 | 1 | 1000",
            "ARROZ SABOROSO 5KG | 7898295150189 | C | 1 | 2 | 1000",
            "ARROZ TRIMAIS 5KG | 7896086423030 | C | 1 | 3 | 1000",
            "FEIJAO PICININ 1KG | 7896864400192 | C | 2 | 1 | 1000",
            "FEIJAO PRETO VENEZA 1KG | 7897924800877 | C | 2 | 2 | 1000",
            "FEIJÃO PEREIRA CARIOQUINHA 1KG | 7898084090030 | C | 2 | 3 | 1000",
            "AÇUCAR REFINADO DOÇULA 1KG | 7891959004415 | D | 1 | 1 | 1000",
            "AÇÚCAR REFINADO DA BARRA 1KG | 7896032501010 | D | 1 | 2 | 1000",
            "AÇÚCAR REFINADO ESPECIAL GUARANI 1KG | 7896109801005 | D | 1 | 3 | 1000",
            "ACUCAR REFINADO CLARION 1KG | 7896319420546 | D | 2 | 1 | 1000",
            "CAFÉ TORRADO MOÍDO POUCHE CAFÉ DO PONTO 500G | 7896089028935 | D | 2 | 2 | 1000",
            "CAFE MARATA 500G | 7898286200077 | D | 2 | 3 | 1000",
            "CAFE CABOCLO 500G | 7891910010905 | D | 3 | 1 | 1000",
            "CAFE FIORENZA 500G | 7898079250012 | D | 3 | 2 | 1000",
            "OLEO DE SOJA SOYA 1L | 7891107000504 | E | 1 | 1 | 1000",
            "OLEO DE SOJA GRANOL 1L | 7896334200550 | E | 2 | 1 | 1000",
            "OLEO DE SOJA VELEIRO 1L | 7896036090107 | E | 3 |1 | 0")

        val estoque: ArrayList<Produto> = ArrayList()
        dados.forEachIndexed {index, element ->
            val dadosArr = element.split("|")

            val produto = Produto()

            produto.nome = dadosArr[0]
            produto.id = dadosArr[1].replace(" ", "")
            produto.rua = dadosArr[2].replace(" ", "")
            produto.numero = dadosArr[3].replace(" ", "")
            produto.andar = dadosArr[4].replace(" ", "")
            produto.quantidade = dadosArr[5].replace(" ", "").toInt()

                estoque.add(produto)
        }

        return estoque

    }

}