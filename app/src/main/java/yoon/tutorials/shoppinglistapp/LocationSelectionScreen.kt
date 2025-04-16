package yoon.tutorials.shoppinglistapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// 구글 map이 나오며 위치 선택하는 화면을 보여주는 컴포저블 함수입니다.
// LocationSelectionScreen은 사용자가 위치를 선택할 수 있는 화면을 나타내는 컴포저블 함수입니다.
// location은 사용자가 선택한 위치를 나타내는 LocationData 객체입니다.
// LocationData는 위도와 경도를 포함하는 데이터 클래스로, 사용자가 선택한 위치를 나타냅니다.
// onLocationSelected는 사용자가 위치를 선택했을 때 호출되는 콜백 함수입니다.
@Composable
fun LocationSelectionScreen(
    location: LocationData,
    onLocationSelected: (LocationData) -> Unit
) {

    // userLocation은 사용자가 선택한 위치를 저장하는 상태입니다.
    // location은 LocationData 객체로, 위도와 경도를 포함합니다.
    // mutableStateOf는 Compose에서 상태를 관리하기 위한 함수로, 상태가 변경될 때 UI를 자동으로 업데이트합니다.
    // remember는 Compose에서 상태를 기억하기 위한 함수로, 컴포저블이 재구성될 때 상태를 유지합니다.
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))
    }

    // cameraPostionState는 카메라의 위치를 기억하는 상태를 나타내는 변수
    // rememberCameraPositionState는 카메라의 위치를 기억하기 위한 함수로, 카메라의 위치를 설정합니다.
    val cameraPostionState = rememberCameraPositionState {

        // CameraPosition은 카메라의 위치를 나타내는 객체로, 위도, 경도, 줌 레벨을 포함합니다.
        // fromLatLngZoom은 LatLng 객체와 줌 레벨을 사용하여 CameraPosition 객체를 생성합니다.
        // LatLng는 위도와 경도를 나타내는 객체입니다.
        // location.latitude와 location.longitude는 사용자가 선택한 위치의 위도와 경도를 나타냅니다.
        // 10f는 줌 레벨을 나타내며, 10f는 중간 정도의 줌 레벨입니다.
        position = CameraPosition.fromLatLngZoom(
            LatLng(location.latitude, location.longitude), 10f
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // GoogleMap은 구글 지도를 표시하는 컴포저블 함수입니다.
        // modifier는 컴포저블의 크기와 위치를 지정하는 속성입니다.
        // cameraPositionState는 카메라의 위치를 기억하는 상태를 나타내는 변수입니다.
        // onMapClick은 사용자가 지도를 클릭했을 때 호출되는 콜백 함수입니다.
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPostionState,
            onMapClick = { latLng ->
                // 사용자가 지도를 클릭했을 때 호출되는 콜백 함수입니다.
                // latLng는 사용자가 클릭한 위치의 위도와 경도를 나타내는 LatLng 객체입니다.
                userLocation.value = latLng
            }
        ) {
            // Marker는 지도에 마커를 표시하는 컴포저블 함수입니다.
            // state는 마커의 상태를 나타내는 MarkerState 객체입니다.
            // MarkerState는 마커의 위치를 기억하는 상태를 나타내는 객체입니다.
            // position은 마커의 위치를 나타내는 LatLng 객체입니다.
            // userLocation.value는 사용자가 선택한 위치의 위도와 경도를 나타내는 LatLng 객체입니다.
            Marker(state = MarkerState(position = userLocation.value))
        }


//        var newLocation = LocationData

        // 버튼을 누르면 사용자가 선택한 위치를 onLocationSelected 콜백 함수에 전달합니다.
        Button(onClick = {
            // 필요한 시점에 직접 생성하고 바로 사용
            onLocationSelected(
                LocationData(
                    latitude = userLocation.value.latitude,
                    longitude = userLocation.value.longitude
                )
            )
        }) {
            Text(text = "Select Location")
        }

    }
}
