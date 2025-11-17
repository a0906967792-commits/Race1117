package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    // 假設您在 drawable 中有 horse0, horse1, horse2, horse3 的圖檔
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 繪製終點線
            val finishLineX = gameViewModel.screenWidthPx - 200
            drawLine(
                color = Color.Black,
                start = Offset(finishLineX, 0f),
                end = Offset(finishLineX, gameViewModel.screenHeightPx),
                strokeWidth = 10f
            )

            // 繪製圓形 (使用者可拖曳物件)
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )

            // 繪製三匹馬
            for(i in 0..2){
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].number],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY),
                    dstSize = IntSize(200, 200) // 馬匹圖片大小
                )
            }
        }

        // 1. 顯示應用程式標題和螢幕尺寸 (位於左上角/TopStart)
        Text(
            text = message + gameViewModel.screenWidthPx.toString() + "*"
                    + gameViewModel.screenHeightPx.toString(),
            modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
        )

        // 2. 顯示獲勝訊息 (位於標題下方，左對齊)
        if (gameViewModel.winnerMessage.isNotEmpty()) {
            Text(
                text = gameViewModel.winnerMessage,
                color = Color.Black, // *** 修改為黑色字體 ***
                fontSize = 20.sp, // 字體調整較小，配合標題
                // 左上對齊 TopStart，向下 padding 讓它顯示在標題文字下方
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 30.dp) // 30.dp 是讓它位於標題下方
            )
        }

        // 3. 遊戲開始按鈕 (位於左下角/BottomStart)
        Button(
            onClick = {
                gameViewModel.gameRunning = true
                gameViewModel.StartGame()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp) // 增加一些邊距
        ){
            Text("遊戲開始")
        }
    }
}


