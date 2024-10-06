package yoon.tutorials.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListApp() {
    //JetpackCompose에서 ShoppingItem 타입의 list를 사용하는 객체상태를 관리하는 변수선언
    // Kotlin listOf는 기본적으로 불변이다 리스트를 변경하고싶다면 MutableList로 선언해야한다

    //ShoppingItem객체타입의 리스트의 상태를 관리하는 변수
    var shoppingItemList by remember { mutableStateOf(listOf<ShoppingItem>()) }
    //Add Item버튼을 눌렀을때 Dialog창의 표시상태여부를 가지는 변수 선언
    var showDialog by remember { mutableStateOf(false) }
    //itemName 변수 초기값 빈문자열
    var itemName by remember { mutableStateOf("") }
    //itemQuantity변수 숫자형태의 String이 텍스트박스에 들어가기 때문에 초기값 String으로 설정
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        //컬럼이 화면을 가득채우도록
        modifier = Modifier.fillMaxSize(),
        //수직 정렬
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            //Add Item버튼을 클릭하면 showDialog상태를 true로
            onClick = { showDialog = true },
            //버튼 수평가운데 정렬
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        //상단에 위치한 버튼
        {
            Text(text = "Add Item")
        }
        //스크롤에 따라 눈에 보이는것만 렌더링하는 LazyColumn은 무한한 Column이 있다고 가정
        LazyColumn(
            //LazyColumn 내부 항목이 Add Item 버튼을 위로 밀어내도록 modifier 설정
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //LazyColumn 내부항목
            //위에서 선언한 shoppingItemList 항목을 렌더링하는  LazyListScope.items함수
            items(shoppingItemList) {
                //밑에 만들어둔 컴포저블 함수를 LazyColumn 내부의 내용으로 사용
                //ShoppingItem객체의 isEditing이 true냐 false냐에 따라서 보여줄 UI를 다르게한다
                //isEditing이 true라면 현재 편집모드상태인것, isEditing이 false라면 편집모드가 아닌것
                //ShoppingItem의 상태가 (람다함수)
                    item ->
                    //ShoppingItem객체가 현재 편집모드(true)라면
                if (item.isEditing) {
                    //ShoppingItemEditor 함수를 보여준다
                    ShoppingItemEditor(item = item, onEditComplete = {
                        //아이템 이름과 수량을 전달받아 편집을 완료하는 람다 함수
                            editedName, editedQuantiy ->
                        //TODO
                        //수정이 완료되면 편집모드는 종료되어야 하기 때문에
                        // 쇼핑목록리스트의 항목객체(ShoppingItem)들을 복사해서 편집상태가 false인 리스트로 반환
                        shoppingItemList = shoppingItemList.map { it.copy(isEditing = false) }
                        //TODO
                        //목록중에서 현재 보이는 항목(it)과 편집중인 항목(item)의 id가 일치하는지 확인해서 편집중인 항목찾기
                        val editedItem = shoppingItemList.find { it.id == item.id }
                        //찾은 ShoppingItem이 null이 아닐경우 let함수 실행
                        editedItem?.let {
                            //기존 항목의 이름과 수량을 편집창에서 수정된 이름과 수량으로 교체
                            it.name = editedName
                            it.quantity = editedQuantiy
                        }

                    })
                } else {
                    //ShoppingItem객체가 현재 편집모드가 아니라면
                    //ShoppingItemList 함수를 보여준다
                    ShoppingItemList(item = item,
                        onEditClick = {
                            //TODO
                            //1.편집버튼을 눌렀을때 어떤 항목의 편집버튼을 눌렀는지 찾는다
                            //2.isEditing을 true로 설정해서 편집모드(ShoppingItemEditor)를 시작
                            shoppingItemList =
                                shoppingItemList.map { it.copy(isEditing = it.id == item.id) }
                        },
                        onDeleteClick = {
                            //목록화면에서 삭제 버튼을 눌렀을때 해당 항목을 리스트에서 제거후 상태반환
                            shoppingItemList = shoppingItemList - item
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        //버튼말고 다른곳을 눌렀을때 showDialog를 flase로
        //AlertDialog는 기본적으로 나타날때 배경이 어두워짐
        AlertDialog(
            onDismissRequest = { showDialog = false },
            //대화상자 확인버튼
            confirmButton = {
                //수평 영역
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    //Row에 위치하는 수평요소가 서로 간격을 두도록
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //추가버튼
                    Button(onClick = {
                        //Add 버튼을 클릭했을때 itemName이 비어있지않다면
                        if (itemName.isNotBlank()) {
                            //새로운 아이템 생성하고
                            val newItem = ShoppingItem(
                                //id는 현재 아이템목록크기에서 +1
                                id = shoppingItemList.size + 1,
                                //name은 텍스트필드에 입력한 텍스트
                                name = itemName,
                                //텍스트필드에 입력한 String type 숫자를 int로 변환
                                quantity = itemQuantity.toInt()
                                //idEditing프로퍼티는 수정할때 ture가 되기때문에 생성시에는 false 기본값 으로 생성
                            )
                            //Add 버튼을 클릭했을때 itemName이 비어있지않다면
                            //쇼핑목록 상태에 새로 생성한 아이템을추가
                            //1. 기존의 `sItems`는 그대로 유지됩니다. (불변이므로 변경되지 않음)
                            //2. 새로운 `newItem`을 기존의 `sItems`에 **추가한 새로운 리스트**를 생성합니다.
                            //3. 그 새 리스트를 `sItems`에 다시 할당하여 상태를 업데이트합니다.
                            shoppingItemList = shoppingItemList + newItem
                            //대화상자를 닫기
                            showDialog = false
                            //새로운 아이템이 추가후 다시 대화상자가 열렸을때 텍스트필드 비우기
                            itemName = ""

                        }
                    }) {
                        Text(text = "Add")
                    }
                    //취소버튼 -> 대화상자 닫기
                    Button(onClick = { showDialog = false }) {
                        Text(text = "Cancel")
                    }
                }
            },
            //alert창의 제목
            title = { Text(text = "Add Shopping Item") },
            //alert창의 내용
            text = {
                Column {
                    //itemName텍스트 필드
                    OutlinedTextField(
                        value = itemName,
                        //textfield에 입력되는값(it)이 변할때마다 value속성의 값으로 설정
                        onValueChange = { itemName = it },
                        //textfield에 한줄로만 입력가능
                        singleLine = true,
                        //OutlinedTextField의 좌우넓이를 최대로하고 padding설정ㄴㅇ
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        //placeHolder
                        label = { Text(text = "Enter ItemName!") }
                    )
                    //itemQuantity텍스트 필드
                    OutlinedTextField(
                        value = itemQuantity,
                        //textfield에 입력되는값(it)이 변할때마다 value속성의 값으로 설정
                        onValueChange = { itemQuantity = it },
                        //textfield에 한줄로만 입력가능
                        singleLine = true,
                        //OutlinedTextField의 좌우넓이를 최대로하고 padding설정
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        //placeHolder
                        label = { Text(text = "Enter ItemQuantity!") }
                    )
                }
            }

        )
    }
}

/**
 * 쇼핑 목록 편집을 위한 연필 아이콘을 클릭했을 경우의 항목이름과 수량을 입력하는 컴포저블 함수.
 *
 * @param item 편집할 ShoppingItem 객체.
 * @param onEditComplete 아이템 이름과 수량을 전달받아 편집을 완료하는 람다 함수.
 */
@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
    //ShoppingItem객체의 name속성 편집을 위한 상태관찰 변수
    var editedName by remember { mutableStateOf(item.name) }
    //ShoppingItem객체의 quantity속성 편집을 위한 상태관찰 변수
    //숫자형태의 문자열이 들어가야하기때문에 toString을 전환
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    //ShoppingItem객체의 편집여부 상태관찰 변수
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier
            .fillMaxWidth() // Row가 가로로 전체 너비를 차지하도록 설정
            .background(Color.White) // 배경색을 흰색으로 설정
            .padding(8.dp), // Row의 내부 여백을 8dp로 설정
        horizontalArrangement = Arrangement.SpaceEvenly // Row 내의 요소들을 균등하게 배치
    ) {
        Column {
            BasicTextField(
                value = editedName, // 텍스트 필드의 현재 값
                onValueChange = { editedName = it }, // 값이 변경될 때 호출되는 콜백
                singleLine = true, // 한 줄로 입력
                modifier = Modifier
                    .wrapContentSize() // 텍스트 필드의 크기를 내용에 맞게 조정
                    .padding(8.dp) // 텍스트 필드의 내부 여백을 8dp로 설정
            )
            BasicTextField(
                value = editedQuantity, // 텍스트 필드의 현재 값
                onValueChange = { editedQuantity = it }, // 값이 변경될 때 호출되는 콜백
                singleLine = true, // 한 줄로 입력
                modifier = Modifier
                    .wrapContentSize() // 텍스트 필드의 크기를 내용에 맞게 조정
                    .padding(8.dp) // 텍스트 필드의 내부 여백을 8dp로 설정
            )
        }
        // 저장 버튼
        Button(
            // 저장 버튼을 클릭하면
            onClick = {
                isEditing = false // 편집 모드를 종료
                // 수정한 이름과 수량을 전달받음
                // 수량을 Int로 변환할 수 있으면 변환하고, 변환이 불가할 경우 null로 변환
                // null일 경우 엘비스 연산자를 사용하여 1로 설정
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
            }
        ) {
            Text(text = "Save") // 버튼의 텍스트를 "Save"로 설정
        }
    }

}

/**
 * 쇼핑 목록을 나타내는 컴포저블 함수
 * @param item
 * @param onEditClick
 * @param onDeleteClick
 */
@Composable
fun ShoppingItemList(
    item: ShoppingItem,
    //Unit은 void와 비슷한 개념이고 실제객체로 존재하기 때문에 변수나 함수의 타입으로 지정할수있다
    //하지만 기본적으로 Kotlin은 명시적이거나 유추할수있는 경우가 아니면 Unit타입으로 간주한다
    //이하의 경우는 함수의 ShoppingItemList의 파라미터로 함수를 받을수 있게 하는 코드
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    //가로로 나열될 itemName과 quantity
    Row(
        //패딩을 주고 가로넓이 전체차지하도록설정 Row요소의 테두리 굵기, 색상 , 모양지정
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color.DarkGray),
                //20퍼센트 만큼 둥글게 설정
                shape = RoundedCornerShape(percent = 20)
            )
    ) {
        //텍스트필드에 입력한 itemName의 상태를 감지하여 표시, padding 설정
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        //텍스트필드에 입력한 itemQuantity의 상태를 감지하여 표시
        Text(text = "Qty:${item.quantity}", modifier = Modifier.padding(8.dp))
        //Row 영어안에 Row영역넣기
        Row(modifier = Modifier.padding(8.dp)) {
            //아이콘 모양의 버튼 선언
            //버튼 클릭시 일회성 편집Unit함수를 실행하도록 설정
            IconButton(onClick = onEditClick ) {
                //아이콘 모양설정 -> 연필모양 아이콘, 시각장애인용접근성 설명 null
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            //버튼 클릭시 일회성 편집Unit함수를 실행하도록 설정
            IconButton(onClick =  onDeleteClick ) {
                //아이콘 모양설정 -> 휴지통모양 아이콘, 시각장애인용접근성 설명 null
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}
