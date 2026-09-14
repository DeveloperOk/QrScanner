package com.enterprise.qrscanner


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.enterprise.qrscanner.presentation.screen.QRScannerScreen
import com.enterprise.qrscanner.ui.theme.QrScannerTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrScannerTheme {
                QrScannerApp()
            }
        }
    }
}

@Composable
fun QrScannerApp() {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(innerPadding)
        ){

            QRScannerScreen()

        }
    }

}

