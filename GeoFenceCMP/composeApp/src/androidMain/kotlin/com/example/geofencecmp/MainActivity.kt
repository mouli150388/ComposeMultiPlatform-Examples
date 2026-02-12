package com.example.geofencecmp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.geofencecmp.geofence.GeofenceManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    private lateinit var geofenceManager: GeofenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        
        geofenceManager = GeofenceManager(this)
        
        checkPermissionsAndAddGeofence()

        setContent {
            App()
        }
    }

    private fun checkPermissionsAndAddGeofence() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isEmpty()) {
          //  setupGeofence()
        } else {
            requestPermissionLauncher.launch(missingPermissions.toTypedArray())
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            //setupGeofence()
            Log.e("Permissions ","Permissions Granted")

        }else
        {
            Log.e("Permissions ","Permissions Not access")
        }
    }

    private fun setupGeofence() {
        // Example: Site A coordinates (Replace with actual site location)
        geofenceManager.addGeofence(
            id = "SITE_A",
            lat = 12.9716, // Replace with your lat
            lng = 77.5946, // Replace with your lng
            radius = 100f  // 100 meters
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}