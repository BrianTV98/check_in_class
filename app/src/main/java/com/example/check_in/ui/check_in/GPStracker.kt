package com.example.check_in.ui.check_in

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.check_in.R
import kotlinx.android.synthetic.main.layout_turn_on_gps_dialog.*

class GPStracker(val context: Context) : LocationListener {

    override fun onLocationChanged(p0: Location?) {}

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    override fun onProviderEnabled(p0: String?) {
        Toast.makeText(context, "Da kich hoat hoat GPS", Toast.LENGTH_LONG).show()
    }

    override fun onProviderDisabled(p0: String?) {
        Toast.makeText(context, "Chua kich hoat GPS", Toast.LENGTH_LONG).show()
    }

    fun getLocation(): Location? {
        /*
         * check permission
         * check gps enable
         * if( permisstion and gps enable is getLocation)
         */
        val mLocationManager =
            context.getSystemService(LOCATION_SERVICE) as LocationManager
        val providers: List<String> = mLocationManager.getProviders(true)
        var bestLocation: Location? = null
        if (ContextCompat.checkSelfPermission( // kiem tra quyen duoc cap
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //neu ko dc cap thi log ra
            Log.d("gps", "permisstion no grandted")
            return null
        } else {

            val  manager: LocationManager =
                context.getSystemService( Context.LOCATION_SERVICE ) as LocationManager;
            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps()
            }
            else{

                for (provider in providers) {
                    val l: Location = mLocationManager.getLastKnownLocation(provider) ?: continue
                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) { // Found best last known location: %s", l);
                        bestLocation = l
                    }
                }
            }

        }
        return bestLocation
    }
    fun  buildAlertMessageNoGps() {
        val gpsDialogView = LayoutInflater.from(context).inflate(R.layout.layout_turn_on_gps_dialog,null)
        val gpsBuilder = AlertDialog.Builder(context).setView(gpsDialogView)
        val gpsDialog = gpsBuilder.create()
        gpsDialog.show()
        gpsDialog.dialog_btn_turn_on_gps.setOnClickListener {
            val intent =  Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }

        /* val builder: AlertDialog.Builder = AlertDialog.Builder(context);
         builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
             .setCancelable(false)
             .setPositiveButton("Yes") { dialog, which ->
                 //startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS));
                 val intent =  Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                 context.startActivity(intent)
             }
             .setNegativeButton("No") { dialog, which ->
                 Toast.makeText(context, " chua Kich hoat", Toast.LENGTH_LONG).show()
             }

         val alert: AlertDialog = builder.create()
         alert.show()*/
    }

    fun removeGps() {

    }
}
