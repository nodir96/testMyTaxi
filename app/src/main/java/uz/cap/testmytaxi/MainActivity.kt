package uz.cap.testmytaxi

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uz.cap.testmytaxi.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private var lt = 41.311800
    private var ln = 69.253950
    private var markerOpt: MarkerOptions = MarkerOptions()
    private var latLng: LatLng = LatLng(lt, ln)
    private var googleMap: GoogleMap? = null
    private var context = this

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        actionBar?.hide()



        setDefaultLongLat()

        val fragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment

        fragment.getMapAsync(this)


        val bottomSheetLayout: LinearLayout = findViewById(binding.orderDetail.bottomSheet.id)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior?.isHideable = false

        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.orderDetail.btmSheetCollapsed.visibility = View.GONE
                    binding.orderDetail.btmSheetExpend.visibility = View.VISIBLE

                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.orderDetail.btmSheetCollapsed.visibility = View.VISIBLE
                    binding.orderDetail.btmSheetExpend.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

    }

    private fun setDefaultLongLat() {
        markerOpt.position(latLng)
//        markerOpt.icon(bitmapFromVector(this, R.drawable.ic_map_pin))

    }


    override fun onMapReady(googleMap: GoogleMap) {

        this.googleMap = googleMap
        this.googleMap!!.setOnCameraIdleListener(this)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.clear()
//        googleMap.addMarker(markerOpt)
    }

    override fun onCameraIdle() {
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(
                    this.googleMap!!.cameraPosition.target.latitude,
                    this.googleMap!!.cameraPosition.target.longitude, 1
                )

            if (!addresses.isNullOrEmpty()) {
                val selectedAdress = addresses[0]

                Log.i("MY_CURRENT_ADDRESS", selectedAdress.thoroughfare)
                binding.orderDetail.currentPositionTitle.text = selectedAdress.thoroughfare
                binding.orderDetail.currentPositionTitleExpand.text = selectedAdress.thoroughfare
            } else {
                Log.i("MY_CURRENT_ADDRESS", "No Address returned!")
            }

        } catch (e: Exception) {
            Log.i("MY_CURRENT_ADDRESS", "on Exception!")
            Log.i("MY_CURRENT_ADDRESS", "" + e.message)
        }

    }

//    private fun bitmapFromVector(context: Context, resId: Int): BitmapDescriptor {
//        val vecDrawable: Drawable = ContextCompat.getDrawable(context, resId)!!
//        vecDrawable.setBounds(0, 0, vecDrawable.intrinsicWidth, vecDrawable.intrinsicHeight)
//
//        val bitmap: Bitmap = Bitmap.createBitmap(
//            vecDrawable.intrinsicWidth,
//            vecDrawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        vecDrawable.draw(canvas)
//
//        return BitmapDescriptorFactory.fromBitmap(bitmap)
//    }


}
