package yoon.tutorials.shoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(){
    //JetpackCompose에서 ShoppingItem 타입의 list를 사용하는 객체상태를 관리하는 변수선언
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    //Add Item버튼을 눌렀을때 Dialog창의 표시상태여부를 가지는 변수 선언
    var showDialog by remember { mutableStateOf(false) }

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
        ){
            //LazyColumn 내부항목
            //위에서 선언한 sItems항목을 렌더링하는  LazyListScope.items함수
            items(sItems){

            }
        }
    }

    if (showDialog){
        //버튼말고 다른곳을 눌렀을때 showDialog를 flase로
        //AlertDialog는 기본적으로 나타날때 배경이 어두워짐
        AlertDialog(onDismissRequest = { showDialog = false }) {
            Text(text = "This is AlertDialog!")
        }
    }
}
