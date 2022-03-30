package uz.cap.testmytaxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment as SupportMapFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment

        fragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

    }


}
