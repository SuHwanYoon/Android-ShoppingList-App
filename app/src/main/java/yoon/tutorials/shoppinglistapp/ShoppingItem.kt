package yoon.tutorials.shoppinglistapp

/**
 * 메인 액티비티에서 사용될 domain class
 * @param id 쇼핑항목을 구분하는 고유 id
 * @param name 쇼핑항목의 이름
 * @param quantity 쇼핑항목의 갯수
 * @param isEditing 쇼핑항목이 현재 편집모드인지 아닌지 나타내는 상태
 */
data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    //쇼핑목록의 수정여부를 관리할 property
    var isEditing: Boolean = false
) {

}



