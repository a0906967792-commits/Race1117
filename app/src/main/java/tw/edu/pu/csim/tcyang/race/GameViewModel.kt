package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    // 用於顯示獲勝資訊
    var winnerMessage by mutableStateOf("")

    val horses = mutableListOf<Horse>()

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        for (i in 0..2){
            horses.add(Horse(i))
        }

    }

    fun StartGame() {
        // 回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f
        // *** 僅在遊戲開始時清除獲勝訊息 ***
        winnerMessage = ""

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)

                // 圓形物件的移動邏輯 (保持不變)
                circleX += 10
                if (circleX >= screenWidthPx - 300){
                    circleX = 100f
                }

                // 檢查是否有馬獲勝
                var winnerIndex = -1
                for (i in 0..2){
                    horses[i].HorseRun()

                    // 終點判斷
                    if (horses[i].horseX >= screenWidthPx - 200){
                        winnerIndex = i + 1 // 記下獲勝馬的編號 (1, 2, 3)
                        break
                    }
                }

                // 處理獲勝情況
                if (winnerIndex != -1) {
                    // *** 設置獲勝訊息並讓其保留 ***
                    winnerMessage = "第${winnerIndex}馬獲勝"

                    // 所有馬匹回到起點 (X 軸為 0)
                    for (i in 0..2){
                        horses[i].horseX = 0
                    }
                    // *** 不再清除 winnerMessage，讓它持續顯示 ***
                }
                // 移除原有的 else { winnerMessage = "" } 邏輯

            }
        }
    }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}