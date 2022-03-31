package uz.cap.testmytaxi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    var lt: Double = 41.311800
    var ln: Double = 69.253950
    var markerOpt: MarkerOptions = MarkerOptions()
    var latLng: LatLng = LatLng(lt, ln)
//    private val camLatLngZoom: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        markerOpt.position(latLng)
        markerOpt.icon(BitmapFromVector(this,R.drawable.ic_map_pin))



        val fragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment

        fragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.clear()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
        googleMap.addMarker(markerOpt)


    }

    private fun BitmapFromVector(context: Context, resId: Int): BitmapDescriptor {
        var vecDrawable: Drawable = ContextCompat.getDrawable(context, resId)!!
        vecDrawable.setBounds(0, 0, vecDrawable.intrinsicWidth, vecDrawable.intrinsicHeight)

        var bitmap: Bitmap = Bitmap.createBitmap(
            vecDrawable.intrinsicWidth,
            vecDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        var canvas: Canvas = Canvas(bitmap)

        vecDrawable.draw(canvas)



        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}
