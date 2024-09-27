package yoon.tutorials.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
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
        {
            Text(text = "Add Item")
        }
        //스크롤에 따라 눈에 보이는것만 렌더링하는 LazyColumn
        LazyColumn(
            //LazyColumn 내부 항목이 Add Item 버튼을 위로 밀어내도록 modifier 설정
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //LazyColumn 내부항목
            //위에서 선언한 sItems항목을 렌더링하는  LazyListScope.items함수
            items(sItems) {
                //밑에 만들어둔 컴포저블 함수를 LazyColumn 내부의 내용으로 사용
                ShoppingItemList(item = it, onEditClick = {}, onDeleteClick = {})

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
                                id = sItems.size + 1,
                                //name은 텍스트필드에 입력한 텍스트
                                name = itemName,
                                //텍스트필드에 입력한 String type 숫자를 int로 변환
                                quantity = itemQuantity.toInt()
                                //idEditing프로퍼티는 수정할때 ture가 되기때문에 생성시에는 false 기본값 으로 생성
                            )
                            //Add 버튼을 클릭했을때 itemName이 비어있지않다면
                            //쇼핑목록 상태에 새로 생성한 아이템을추가
                            //TODO
                            sItems = sItems + newItem
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

//쇼핑 목록을 나타내는 컴포저블 함수
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
        //텍스트필드에 입력한 itemName, padding 설정
        Text(text = item.name, modifier = Modifier.padding(8.dp))
    }
}
