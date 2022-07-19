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

data class MarkerStatus(var type: MarkerType, var count: Int?, var max: Double?, var isConnected: Boolean?)

class MainFragment: Fragment(), OnMapReadyCallback {
    private val viewModel: MainViewModel by inject()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var gpsTracker: GpsTracker? = null

    private lateinit var newMarkerMap                   : ArrayList<Pair<MarkerStatus, Marker>>
    private lateinit var locationSource                 : FusedLocationSource
    private lateinit var nMap                           : NaverMap

    private lateinit var defaultDealershipMarker : ArrayList<Marker>
    private lateinit var reduction20DealershipMarker : ArrayList<Marker>
    private lateinit var reduction30DealershipMarker : ArrayList<Marker>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gpsTracker = GpsTracker(context = requireActivity())
        newMarkerMap = ArrayList()
        defaultDealershipMarker = ArrayList()
        reduction20DealershipMarker = ArrayList()
        reduction30DealershipMarker = ArrayList()

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
//            markerReset()
            val defaultMarkerLatLng = listOf(LatLng(38.13791380837467, 128.32567305621163), LatLng(38.06635581984284, 128.41125912070427), LatLng(38.102143574199246, 128.23473786268823), LatLng(38.003158236183204, 128.44602845940443), LatLng(37.93568739393951, 128.74825424964402))
            val reduction20MarkerLatLng = listOf(LatLng(36.839477226445965, 129.08647881154113), LatLng(36.85596150116896, 129.2286144174804), LatLng(36.75810280434425, 129.36045035042386), LatLng(36.416976080199746, 129.14626395400418), LatLng(36.36961171758885, 129.30138869903993))
            val reduction30MarkerLatLng = listOf(LatLng(35.58860512712293, 129.17368276997303), LatLng(35.361577515752224, 129.15308340545064), LatLng(35.466783295116734, 128.93885001441757), LatLng(35.294131480311634, 128.60332735497553), LatLng(35.357412874464664, 128.77449948396077))
            defaultMarkerLatLng.forEach { drawDefaultMarker(nMap, it) }
            reduction20MarkerLatLng.forEach { draw20ReductionMarker(nMap, it) }
            reduction30MarkerLatLng.forEach { draw30ReductionMarker(nMap, it) }
        }

        binding.btnMarkerRemove.setOnClickListener {
//            removeMarker()
            defaultDealershipMarker.forEach { it.map = null }
            reduction20DealershipMarker.forEach { it.map = null }
            reduction30DealershipMarker.forEach { it.map = null }
        }

    }

    private fun subscribeObservers() {
        viewModel.stationData.observe(requireActivity()) { it ->
            newMarkerMap.clear()
            it.embedded.stationList.forEachIndexed { index, station ->
                Logger.d("index :: $index")
                drawMarker(idx = index, nMap = nMap, latlng = LatLng(station.lat, station.lon), socCount = station.socCount, socMax = station.maxSoc, isConnection = station.connYn)
            }
        }
    }

    private fun onMapClickEvent() {
        nMap.setOnMapClickListener { _, _ ->
            newMarkerMap.forEachIndexed { index, mutableMap ->
                    if(mutableMap.first.type == MarkerType.Large) {
                        mutableMap.second.icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, mutableMap.first.count, mutableMap.first.max))
                    }
            }
        }
    }

    private fun drawMarker(idx: Int, nMap: NaverMap, latlng: LatLng, socCount: Int?, socMax: Double?, isConnection: Boolean) {
        newMarkerMap.add(
            MarkerStatus(type = MarkerType.Small, count = socCount, max = socMax, isConnected = isConnection) to Marker().apply {
            position = latlng
            map = nMap
            width = Marker.SIZE_AUTO
            height = Marker.SIZE_AUTO
            icon = when(isConnection) {
                true    -> OverlayImage.fromView(CustomMarker(context = requireContext(), type = MarkerType.Small, countSoc = socCount, maxSoc = socMax))
                false   -> OverlayImage.fromView(CustomMarker(context = requireContext(), type = MarkerType.Error, countSoc = socCount, maxSoc = socMax))
            }
            setOnClickListener {
                if(isConnection) {
                    newMarkerMap.forEach { mutableMap ->
                        if (mutableMap.first.type == MarkerType.Large) {
                            mutableMap.second.icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Small, mutableMap.first.count, mutableMap.first.max))
                        }
                    }
                    newMarkerMap[idx].first.type = MarkerType.Large
                    icon = OverlayImage.fromView(CustomMarker(requireContext(), MarkerType.Large, socCount, socMax))
                }
                true
            }
        })
    }

    private fun drawDefaultMarker(nMap: NaverMap, latlng: LatLng) {
        defaultDealershipMarker.add(
            Marker().apply {
                position = latlng
                map = nMap
                width = Marker.SIZE_AUTO
                height = Marker.SIZE_AUTO
                icon = OverlayImage.fromResource(R.drawable.s_dealership_pin_icon)

                setOnClickListener {
                    icon = OverlayImage.fromResource(R.drawable.l_dealership_pin_icon)
                    true
                }

            }
        )
    }

    private fun draw20ReductionMarker(nMap: NaverMap, latlng: LatLng) {
        reduction20DealershipMarker.add(
            Marker().apply {
                position = latlng
                map = nMap
                width = Marker.SIZE_AUTO
                height = Marker.SIZE_AUTO
                icon = OverlayImage.fromResource(R.drawable.s_80_dealership_pin_icon)

                setOnClickListener {
                    icon = OverlayImage.fromResource(R.drawable.l_80_dealership_pin_icon)
                    true
                }
            }
        )
    }

    private fun draw30ReductionMarker(nMap: NaverMap, latlng: LatLng) {
        reduction30DealershipMarker.add(
            Marker().apply {
                position = latlng
                map = nMap
                width = Marker.SIZE_AUTO
                height = Marker.SIZE_AUTO
                icon = OverlayImage.fromResource(R.drawable.s_70_dealership_pin_icon)

                setOnClickListener {
                    icon = OverlayImage.fromResource(R.drawable.l_70_dealership_pin_icon)
                    true
                }
            }
        )
    }


    private fun markerReset() {
        newMarkerMap.forEach { (_, b) -> b.map = null }
        viewModel.getMockStationList()
    }

    private fun removeMarker() {
        newMarkerMap.forEach { (_, b) -> b.map = null }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(naverMap: NaverMap) {
        nMap = naverMap
        nMap.locationSource = locationSource

        nMap.apply {
            cameraPosition = CameraPosition(LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude()), 8.0, 0.0, 0.0)
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
            locationTrackingMode = LocationTrackingMode.Follow
        }

        onMapClickEvent()


        val defaultMarkerLatLng = listOf(LatLng(38.13791380837467, 128.32567305621163), LatLng(38.06635581984284, 128.41125912070427), LatLng(38.102143574199246, 128.23473786268823), LatLng(38.003158236183204, 128.44602845940443), LatLng(37.93568739393951, 128.74825424964402))
        val reduction20MarkerLatLng = listOf(LatLng(36.839477226445965, 129.08647881154113), LatLng(36.85596150116896, 129.2286144174804), LatLng(36.75810280434425, 129.36045035042386), LatLng(36.416976080199746, 129.14626395400418), LatLng(36.36961171758885, 129.30138869903993))
        val reduction30MarkerLatLng = listOf(LatLng(35.58860512712293, 129.17368276997303), LatLng(35.361577515752224, 129.15308340545064), LatLng(35.466783295116734, 128.93885001441757), LatLng(35.294131480311634, 128.60332735497553), LatLng(35.357412874464664, 128.77449948396077))


        defaultMarkerLatLng.forEach { drawDefaultMarker(nMap, it) }
        reduction20MarkerLatLng.forEach { draw20ReductionMarker(nMap, it) }
        reduction30MarkerLatLng.forEach { draw30ReductionMarker(nMap, it) }
    }
}