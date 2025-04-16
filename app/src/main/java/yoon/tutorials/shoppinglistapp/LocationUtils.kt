package yoon.tutorials.shoppinglistapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


// LocationUtils는 위치 권한을 확인하는 유틸리티 클래스입니다.
// 이 클래스는 위치 권한이 있는지 확인하는 메서드를 제공합니다.
// LocationUtils는 Context를 매개변수로 받아서 위치 권한을 확인합니다.
// Context는 Android 애플리케이션의 현재 상태를 나타내는 객체입니다.
class LocationUtils(val context: Context) {

    // FusedLocationProviderClient는 위치 서비스를 제공하는 클라이언트입니다.
    // FusedLocationProviderClient는 Google Play Services의 위치 API를 사용하여 위치 정보를 가져오는 데 사용됩니다.
    // FusedLocationProviderClient는 위치 정보를 가져오는 데 필요한 다양한 메서드를 제공합니다.
    // LocationServices는 Google Play Services의 위치 API를 사용하여 위치 정보를 가져오는 데 필요한 서비스를 제공합니다.
    // getFusedLocationProviderClient()는 FusedLocationProviderClient 객체를 반환하는 메서드입니다.
    // FusedLocationProviderClient는 위치 정보를 가져오는 데 필요한 다양한 메서드를 제공합니다.
    // context는 Android 애플리케이션의 현재 상태를 나타내는 객체입니다.
    private val _fusedLocationClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)


    // requestLocationUpdates()는 위치 업데이트를 요청하는 메서드입니다.
    // 이 메서드는 LocationViewModel을 매개변수로 받아서 위치 업데이트를 요청합니다.
    // LocationCallback은 위치 업데이트를 받을 때 호출되는 콜백 클래스입니다.
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel){
        val locationCallback = object : LocationCallback(){
    // onLocationResult()는 위치 업데이트를 받을 때 호출되는 메서드입니다.
    // locationResult는 위치 업데이트 결과를 나타내는 객체입니다.
    // locationResult.lastLocation은 마지막 위치를 나타내는 객체입니다.
    // lastLocation은 사용자의 마지막 위치를 나타내는 객체입니다.
    // viewModel.updateLocation()은 위치 정보를 업데이트하는 메서드입니다.
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(latitude = it.latitude, longitude =  it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        // LocationRequest는 위치 요청을 나타내는 객체입니다.
        // LocationRequest는 위치 요청의 우선 순위와 업데이트 간격을 설정하는 데 사용됩니다.
        // Builder는 LocationRequest 객체를 생성하는 데 사용되는 클래스입니다.
        // Priority는 위치 요청의 우선 순위를 나타내는 상수입니다.
        // PRIORITY_HIGH_ACCURACY는 높은 정확도의 위치 요청을 나타내는 상수입니다.
        // 1000은 위치 업데이트 간격을 나타내는 값입니다. 1000ms(1초)마다 위치 업데이트를 요청합니다.
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        // requestLocationUpdates()는 위치 업데이트를 요청하는 메서드입니다.
        // locationRequest는 위치 요청을 나타내는 객체입니다.
        // locationCallback은 위치 업데이트를 받을 때 호출되는 콜백 클래스입니다.
        // Looper.getMainLooper()는 메인 스레드의 Looper 객체를 반환하는 메서드입니다.
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }


    // hasLocationPermission()는 ACCESS_FINE_LOCATION과 ACCESS_COARSE_LOCATION 권한을 확인합니다.
    // 두 권한 모두 부여되어야 true를 반환합니다.
    // 만약 두 권한 중 하나라도 부여되지 않았다면 false를 반환합니다.
    // ContextComapt는 AndroidX 라이브러리의 일부로, Android의 다양한 기능을 사용할 수 있도록 도와주는 유틸리티 클래스
    // checkSelfPermission()은 정수(Int) 값을 반환합니다.
    //PackageManager에는 두 가지 주요 상수가 정의되어 있습니다:
    //PERMISSION_GRANTED = 0
    //PERMISSION_DENIED = -1
    // 0 == 0 -> true
    // -1 == 0 -> false
    /**
     * Checks if the app has location permissions.
     *
     * @param context The context of the current activity.
     * @return True if the app has location permissions, false otherwise.
     */
    fun hasLocationPermission(context: Context):Boolean{

        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }

}