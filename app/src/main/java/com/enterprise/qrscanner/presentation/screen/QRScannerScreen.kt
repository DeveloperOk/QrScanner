package com.enterprise.qrscanner.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.enterprise.qrscanner.presentation.component.CameraPreview
import com.enterprise.qrscanner.presentation.component.QRResult
import com.enterprise.qrscanner.presentation.component.ScannerOverlay


@Composable
fun QRScannerScreen() {

    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            hasCameraPermission = isGranted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }


    var scannedValue by remember {
        mutableStateOf<String?>(null)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (hasCameraPermission) {

            CameraPreview(
                onQrCodeScanned = { value ->
                    if (scannedValue == null) {
                        scannedValue = value
                    }
                }
            )

            // Scanner overlay
            ScannerOverlay()

            // Result
            scannedValue?.let { value ->

                QRResult(
                    value = value,
                    onDismiss = {
                        scannedValue = null
                    }
                )
            }

        } else {

            Column(modifier = Modifier.fillMaxSize().align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                Text(text = "Camera permission is required")

                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    onClick = {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                ) {
                    Text("Grant Permission")
                }

            }


        }
    }
}