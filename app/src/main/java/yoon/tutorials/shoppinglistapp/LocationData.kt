package yoon.tutorials.shoppinglistapp

// 이 파일은 Google Maps API를 사용하여 위치 정보를 가져오는 데 필요한 데이터 클래스를 정의합니다.
// LocationData는 위도와 경도를 나타내는 데이터 클래스로, Google Maps API를 사용하여 위치 정보를 가져오는 데 사용됩니다.
// latitude는 위도를 나타내는 Double 타입의 값이며, longitude는 경도를 나타내는 Double 타입의 값입니다.
data class LocationData(
    val latitude: Double,
    val longitude: Double
)

// GeocodingResponse는 Google Maps Geocoding API의 응답을 나타내는 데이터 클래스입니다.
// results는 GeocodingResult 객체의 리스트로, 각 결과에 대한 정보를 포함합니다.
// status는 요청의 상태를 나타내는 문자열입니다.
data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

//GeocodingResult는 Google Maps Geocoding API의 결과를 나타내는 데이터 클래스입니다.
// formatted_address는 주소를 나타내는 문자열입니다.
data class GeocodingResult(
    val formatted_address: String
)