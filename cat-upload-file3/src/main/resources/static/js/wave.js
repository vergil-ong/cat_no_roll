function Wave(canvasId, proNumId,
              canvasW, canvasH) {
    this.speed = 150 ;   //波浪横向流动速度
    this.offsetX = 0 ;   //波浪横向偏移量
    this.isDrawContainer = false;        //判断是否画了容器
    this.offsetYRange = 1.1 ;            //波浪垂直方向最大范围
    this.offsetY = 0;                    //波浪垂直方向位移
    this.offsetYSpeed = 0.03;            //波浪溢满
    this.progressNum = 0;                    //进度
    this.init = function(){
        var canvas = document.getElementById(canvasId);
        this.ctx = canvas.getContext('2d');
        canvas.width = canvasW;
        canvas.height = canvasH;

        this.canvasW = canvas.width;
        this.canvasH = canvas.height;

        this.draw(0)
    };

    //所有绘制
    this.draw = function(progressInt){
        var ctx = this.ctx;
        if (progressInt === undefined) {
            progressInt = 0
        }

        ctx.clearRect(0,0,this.canvasW,this.canvasW);
        this.offsetX += this.speed;

        let waveW = 0.04
        let waveH1 = 6
        let waveH2 = 8
        this.drawWave(ctx , this.offsetX , this.offsetY , waveW , waveH1 , '#a4def6');
        this.drawWave(ctx , this.offsetX + 5 , this.offsetY  - 0.02, waveW , waveH2, '#79d4f9');

        if(this.offsetY < this.offsetYRange){
            // this.offsetY += this.offsetYSpeed;
            this.offsetY = progressInt / 100 * this.offsetYRange

            // this.progressNum += 100/(this.offsetYRange/this.offsetYSpeed);
            this.progressNum = progressInt

            document.getElementById(proNumId).innerHTML = parseInt(this.progressNum) + '%';
        }

        // requestAnimationFrame(this.draw.bind(this));
    };
    //画波浪线
    this.drawWave = function(ctx , offsetX , offsetY , waveW , waveH , color){
        var canvasW = this.canvasW,
            canvasH = this.canvasH,
            startX = 0;     //波浪线初始x轴坐标

        ctx.beginPath();

        var startPos = [startX];

        for(var x = startX ; x < canvasW ; x += 20 / canvasW){
            //正弦曲线公式：y=Asin(ωx+φ)+k
            var y = (1 - offsetY) * canvasW + waveH * Math.sin((startX + x) * waveW + offsetX);

            if(startPos.length === 1){startPos.push(y)}

            ctx.lineTo(x , y);
        }
        //画出完整的波浪形状
        ctx.lineTo(canvasH , canvasH);
        ctx.lineTo(startX , canvasH);
        ctx.lineTo(startPos[0] , startPos[1]);
        ctx.fillStyle = color;
        ctx.fill();
    }
}

// wave.init()


// var progressInt = 0
// setInterval(function () {
//     progressInt += 4
//     wave.draw(progressInt)
// }, 200)