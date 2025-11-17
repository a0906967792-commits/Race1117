package tw.edu.pu.csim.tcyang.race

class Horse(n: Int) {
    // Horse 的 X 座標，初始為 0
    var horseX = 0
    // Horse 的 Y 座標，根據編號 n 設定不同高度
    var horseY = 100 + 320 * n

    // 動畫圖片編號 (0-3)
    var number = 0

    fun HorseRun(){
        // 切換動畫圖片
        number ++
        if (number>3) { number = 0}

        // 隨機增加 X 座標，模擬賽跑
        horseX += (10..30).random()
    }
}