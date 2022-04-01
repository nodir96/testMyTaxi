package uz.cap.testmytaxi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var lt = 41.311800
    private var ln = 69.253950
    private var markerOpt: MarkerOptions = MarkerOptions()
    private var latLng: LatLng = LatLng(lt, ln)

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()



        markerOpt.position(latLng)
        markerOpt.icon(bitmapFromVector(this, R.drawable.ic_map_pin))

        val fragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment

        fragment.getMapAsync(this)




        val bottomSheetLayout: LinearLayout = findViewById(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior?.isHideable = false


    }


    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.clear()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOpt)


    }

    private fun bitmapFromVector(context: Context, resId: Int): BitmapDescriptor {
        val vecDrawable: Drawable = ContextCompat.getDrawable(context, resId)!!
        vecDrawable.setBounds(0, 0, vecDrawable.intrinsicWidth, vecDrawable.intrinsicHeight)

        val bitmap: Bitmap = Bitmap.createBitmap(
            vecDrawable.intrinsicWidth,
            vecDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vecDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}
