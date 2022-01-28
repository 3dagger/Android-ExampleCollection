package com.dagger.custommarkerview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dagger.custommarkerview.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import kotlin.math.max

data class AA(var type: MarkerType, var count: Int?, var max: Double?)

class MainFragment: Fragment(), OnMapReadyCallback {
    private val viewModel: MainViewModel by inject()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var gpsTracker: GpsTracker? = null

    private lateinit var markerArray                    : ArrayList<Marker>
    private lateinit var locationSource                 : FusedLocationSource
    private lateinit var nMap                           : NaverMap

//    private lateinit var newMarkerMap                   : MutableMap<String, ArrayList<Marker>>
    private lateinit var newMarkerMap                   : ArrayList<MutableMap<AA, Marker>>
    private lateinit var newMarkerMap2                   : MutableMap<AA, Marker>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gpsTracker = GpsTracker(context = requireActivity())
        markerArray = ArrayList()

        newMarkerMap = ArrayList()
        newMarkerMap2 = mutableMapOf()

        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        viewModel.getMockStationList()

        val mapFragment = childFragmentManager.findFragmentById(R.id.naverMapFragment) as MapFragment?
            ?: MapFragment.newInstance().also { childFragmentManager.beginTransaction().add(R.id.naverMapFragment, it).commit() }

        mapFragment?.getMapAsync(this@MainFragment)
        locationSource = FusedLocationSource(this, 100)

        initView()
        subscribeObservers()

        return binding.root
    }

    private fun initView() {
        binding.btnReset.setOnClickListener {
            markerReset()
            viewModel.getMockStationList()
        }

        binding.btnMarkerRemove.setOnClickListener {
            removeMarer()
        }
    }

    private fun subscribeObservers() {
        viewModel.stationData.observe(requireActivity()) { it ->
            markerReset()
            markerArray.clear()

            it.embedded.stationList.forEachIndexed { index, entiretyStationList ->
//                newMarkerMap2.put(AA(type = MarkerType.Small, count = entiretyStationList.socCount, max = entiretyStationList.maxSoc), Marker().apply {
//                    position = LatLng(entiretyStationList.lat, entiretyStationList.lon)
//                    map = nMap
//                    width = Marker.SIZE_AUTO
//                    height = Marker.SIZE_AUTO
//                    icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))
//
//                    setOnClickListener {
//                        icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Large, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))
//
////                        newMarkerMap[index].keys.map {
////                            it.type = MarkerType.Large
////                        }
//                        true
//                    }
//                }

                newMarkerMap.add(mutableMapOf(AA(type = MarkerType.Small, count = entiretyStationList.socCount, max = entiretyStationList.maxSoc) to Marker().apply {
                    position = LatLng(entiretyStationList.lat, entiretyStationList.lon)
                    map = nMap
                    width = Marker.SIZE_AUTO
                    height = Marker.SIZE_AUTO
                    icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))

                    setOnClickListener {
                        icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Large, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))
                        newMarkerMap[index].keys.map {
                            it.type = MarkerType.Large
                        }
                        true
                    }
                }))





//                newMarkerMap.put(key = AA(type = MarkerType.Small, count = entiretyStationList.socCount, max = entiretyStationList.maxSoc), value = Marker().apply {
//                    position = LatLng(entiretyStationList.lat, entiretyStationList.lon)
//                    map = nMap
//                    width = Marker.SIZE_AUTO
//                    height = Marker.SIZE_AUTO
//                    icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))
//
//                    setOnClickListener {
//                        icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Large, defineMarker(socMax = entiretyStationList.maxSoc), entiretyStationList.socCount))
//                        newMarkerMap.keys[index]
////                                newMarkerMap.keys.map { it.type == MarkerType.Large }
//                        true
//                    }
//                })
            }

//            it.embedded.stationList.forEach {
//                drawMarker(nMap = nMap, latlng = LatLng(it.lat, it.lon), socCount = it.socCount, socMax = it.maxSoc, isConnection = it.connYn)
//            }

            Logger.d("newMarkermap2 :: ${newMarkerMap2}" )
        }
    }

    private fun onMapClickEvent() {
        nMap.setOnMapClickListener { _, _ ->
            Logger.d("newMarkerMap mapClickEvent :: ${newMarkerMap}")
//            Logger.d("res :: ${newMarkerMap2.filter { it.key.type == MarkerType.Large }}")
            newMarkerMap.forEachIndexed { index, mutableMap ->
                val b = mutableMap.keys.filterIndexed { index, aa ->  aa.type == MarkerType.Large }

//                mutableMap.values.

            }
        }
    }

    // 마커가 확대되어있는 상황에 맵 클릭시 작은 마커로 변환 되어야함
    // icon만 갈아끼워주면 됨
    private fun drawMarker(nMap: NaverMap, latlng: LatLng, socCount: Int?, socMax: Double?, isConnection: Boolean) {
//        newMarkerMap.put(key = AA(type = MarkerType.Small, count = socCount, max = socMax), value = Marker().apply {
//                            position = latlng
//                            map = nMap
//                            width = Marker.SIZE_AUTO
//                            height = Marker.SIZE_AUTO
//                            icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, defineMarker(socMax = socMax), socCount))
//
//                            setOnClickListener {
//                                icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Large, defineMarker(socMax = socMax), socCount))
//                                newMarkerMap.keys.map { Logger.d("click it :: $it") }
//                                true
//                            }
//                        })
    }

    private fun defineMarker(socMax: Double?) : Int {
        return when {
            socMax!! >= 80.0    -> R.drawable.s_station_pin_icon_g
            socMax   >= 60.0    -> R.drawable.s_station_pin_icon_y
            else                -> R.drawable.s_station_pin_icon_r
        }
    }


    private fun markerReset() {
        markerArray.forEach { marker ->  marker.map = null }
    }

    private fun removeMarer() {
        markerArray.forEach { marker ->  marker.map = null }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(naverMap: NaverMap) {
        nMap = naverMap
        nMap.locationSource = locationSource

        nMap.apply {
            cameraPosition = CameraPosition(LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude()), 14.0, 0.0, 0.0)
            uiSettings.apply {
                isLocationButtonEnabled = false
                isCompassEnabled = false
                isZoomControlEnabled = true
                isTiltGesturesEnabled = false
                isRotateGesturesEnabled = false
                isScaleBarEnabled = false
                locationOverlay.apply {
                    icon = OverlayImage.fromResource(R.drawable.sample)
                    iconHeight = LocationOverlay.SIZE_AUTO
                    iconWidth = LocationOverlay.SIZE_AUTO
                }
                setLogoMargin(20, 100, 200, 20)
            }
            mapType = NaverMap.MapType.Navi
            locationTrackingMode = LocationTrackingMode.NoFollow
        }

        onMapClickEvent()
    }
}