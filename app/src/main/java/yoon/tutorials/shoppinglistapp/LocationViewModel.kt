package yoon.tutorials.shoppinglistapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    // _location은 사용자가 선택한 위치를 저장하는 상태입니다.
    // mutableStateOf는 Compose에서 상태를 관리하기 위한 함수로, 상태가 변경될 때 UI를 자동으로 업데이트합니다.
    private val _location = mutableStateOf<LocationData?>(null)

    // location은 _location의 상태를 외부에서 읽을 수 있도록 하는 속성입니다.
    // State<LocationData?>는 _location의 상태를 나타내는 타입으로, LocationData 객체 또는 null을 포함할 수 있습니다.
    // State는 Compose에서 상태를 읽기 위한 타입으로, 상태가 변경될 때 UI를 자동으로 업데이트합니다.
    // _location은 private으로 선언되어 외부에서 직접 접근할 수 없으며, location은 public으로 선언되어 외부에서 읽을 수 있습니다.
    val location: State<LocationData?> = _location


}