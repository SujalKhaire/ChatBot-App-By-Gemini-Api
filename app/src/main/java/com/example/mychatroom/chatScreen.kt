package com.example.mychatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField



import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun chatScreen(modifier: Modifier = Modifier,navController: NavController,chatViewModel: ChatViewModel){
    Column(
        modifier = modifier.background(colorResource(id = R.color.backg))
    ) {
        AppHeader()
        ListM(
            modifier = Modifier.weight(1F),
            messageList = chatViewModel.messageList
        )
        messageInput(onMessageSend= { chatViewModel.sendMessage(it)})
        
    }
}


@Composable
fun ListM(modifier: Modifier = Modifier,messageList: List<MessageList>){
    LazyColumn(modifier = modifier,reverseLayout = true){
        items(messageList.reversed()) {
            messageItem(messageList = it)
        }
    }
}


@Composable
fun messageItem(messageList: MessageList){
    val isModel = messageList.role == "model"

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.fillMaxWidth() ){
            Box(modifier = Modifier
                .align(
                    if (isModel) {
                        Alignment.BottomStart
                    } else {
                        Alignment.BottomEnd
                    }
                )

                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(48f))
                .background(
                    if (isModel) {
                        colorResource(id = R.color.chatModel)
                    } else {
                        colorResource(id = R.color.chatUser)
                    }
                )
                .padding(16.dp)){
                Text(text = messageList.message,
                    fontWeight = FontWeight.W500,
                    color = Color.Black)
            }
        }
    }

}

@Composable
fun messageInput(onMessageSend:(String)->Unit){

    var message by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        OutlinedTextField(modifier = Modifier.weight(1f),textStyle = TextStyle(color = Color.White, fontSize = 18.sp),value = message, onValueChange = {message = it}, label = { Text(text = "Message", color = Color.White)})
        Spacer(modifier = Modifier.width(24.dp))
        IconButton(onClick = {  if(message.isNotEmpty()){
            onMessageSend(message)
            message = ""
        } }, modifier = Modifier.padding(top = 12.dp)) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(35.dp), tint = Color.White)
        }
    }

}
@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Chat Session",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}


