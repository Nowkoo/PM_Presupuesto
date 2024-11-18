package com.example.pm_presupuesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pm_presupuesto.ui.theme.PM_PresupuestoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PM_PresupuestoTheme {
                var presupuesto by remember { mutableStateOf(0.0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { crearTopAppBar(presupuesto.toString()) }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)


                    Column (modifier
                        .fillMaxSize()
                        .background(Color.Black)
                    ) {


                        LazyColumn() {
                            items(getItems()) { producto ->
                                ItemProducto(producto, actualizarPresupuesto = {
                                    presupuesto += it
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearTopAppBar(presupuesto: String) {
    TopAppBar(
        title = {
            Row {
                Text(
                    text = "Presupuesto actual: "
                )

                Text(
                    text = "$presupuesto€",
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.LightGray
        )
    )
}

@Composable
fun ItemProducto(producto: Producto, actualizarPresupuesto: (Double) -> Unit) {
    var seleccionado by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Checkbox(
            checked = seleccionado,
            onCheckedChange = {
                seleccionado = !seleccionado
                if (seleccionado) actualizarPresupuesto(producto.precio)
                else actualizarPresupuesto(-producto.precio)
            }
        )

        Image(
            painter = painterResource(producto.foto),
            contentDescription = producto.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
        )

        Column (
            modifier = Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = producto.nombre
            )

            Text(
                text = "${producto.precio}€",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

fun getItems(): List<Producto> {
    return listOf(
        Producto(
            "Portátil ASUS TUF Gaming A15 FA506NCR-HN006 AMD Ryzen 7 7435HS/16GB/512GB SSD/RTX 3050/15.6",
            R.drawable.asus,
            669.0
        ),
        Producto(
            "Monitor AOC 24B3HA2 23.8\" LED IPS FullHD 100Hz",
            R.drawable.monitor,
            74.90
        ),
        Producto(
            "PcCom Essential Combo MK20 Teclado + Ratón Negro ",
            R.drawable.tecladoraton,
            13.0
        ),
        Producto(
            "Disco Duro Toshiba Canvio Basics 2TB Disco Externo Portátil USB 3.2 Negro",
            R.drawable.discoduro,
            66.99
        ),
        Producto(
            "Logitech G435 LIGHTSPEED Auriculares Gaming Inalámbricos Negros ",
            R.drawable.cascos,
            57.64
        )
    )
}

data class Producto(
    var nombre: String,
    var foto: Int,
    var precio: Double
)