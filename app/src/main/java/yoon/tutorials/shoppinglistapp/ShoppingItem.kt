package yoon.tutorials.shoppinglistapp

//메인 액티비티에서 사용될 domain class
data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    //쇼핑목록의 수정여부를 관리할 property
    var isEditing: Boolean = false
) {

}