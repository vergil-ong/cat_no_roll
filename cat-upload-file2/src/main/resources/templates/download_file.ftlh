<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no, user-scalable=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src="/static/js/download.js"></script>
    <link rel="stylesheet" href='/static/css/common.css'>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>

    <style>
        body {
            padding-bottom: 45px;
            height:100%
        }
        .row h1 {
            color: white;
            font-size: 26px;
            border-bottom: 2px solid var(--yellow-color);
            padding-bottom: 5px;
        }
        .img>img{
            border-radius: 15px;
        }
        .down-num-font-hit{
            color: var(--yellow-color);
            font-size: 1.5rem;
        }
        .img-hit {
            border: 2px solid var(--yellow-color);
        }
        .down-btn-div {
            text-align: center;
        }
        .down-btn {
            font-weight: bold;
            font-size: 1.5rem;
            background-color: var(--yellow-color);
            border-radius: 20px;
            width: 100px;
        }
    </style>
</head>
<body>
<!-- 顶部logo -->
<div class="top-logo">
    <img src="/static/imgs/logo1.png">
</div>
<div class="container">
    <div class="row">
        <div class="col-12" style="height: 70px"></div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >1.沉浸式</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <div>
                <span class="down-num-font">1</span>
            </div>
            <div class="main" style="position:relative;">
                <div class="img">
                    <img alt="图片名称" src="/static/imgs/before1.png" onclick="imgClick(this)" style="width:100px;height:100px">
                </div>
                <div class="checkbox" style="position:absolute;top:0;left:0;z-index:1000">
                    <input type="radio" >
                </div>
            </div>
        </div>
        <div class="col-3">
            <div>
                <span class="down-num-font">2</span>
            </div>
            <div class="main" style="position:relative;">
                <div class="img">
                    <img alt="图片名称" src="/static/imgs/before1.png" onclick="imgClick(this)" style="width:100px;height:100px">
                </div>
                <div class="checkbox" style="position:absolute;top:0;left:0;z-index:1000">
                    <input type="radio" >
                </div>
            </div>
        </div>
        <div class="col-3">
            <div>
                <span class="down-num-font">3</span>
            </div>
            <div class="main" style="position:relative;">
                <div class="img">
                    <img alt="图片名称" src="/static/imgs/before1.png" onclick="imgClick(this)" style="width:100px;height:100px">
                </div>
                <div class="checkbox" style="position:absolute;top:0;left:0;z-index:1000">
                    <input type="radio" >
                </div>
            </div>
        </div>
        <div class="col-3">
            <div>
                <span class="down-num-font">4</span>
            </div>
            <div class="main" style="position:relative;">
                <div class="img">
                    <img alt="图片名称" src="/static/imgs/before1.png" onclick="imgClick(this)" style="width:100px;height:100px">
                </div>
                <div class="checkbox" style="position:absolute;top:0;left:0;z-index:1000">
                    <input type="radio" >
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-6 down-btn-div">
            <button type="button" class="down-btn" onclick="allImgClick()">全选</button>
        </div>
        <div class="col-6 down-btn-div">
            <button type="button" id="downNumBtn" data-num="0" class="down-btn" onclick="downloadVideo()">下载(0)</button>
        </div>
    </div>
</div>

<div class="footer">
    <div class="row footer-row">
        <div class="col-6 no-pd">
            <button type="button" class="footer-btn">上传素材</button>
        </div>
        <div class="col-6 no-pd">
            <button type="button" class="footer-btn">下载爆款视频</button>
        </div>
    </div>
</div>

<script>
    
    function imgClick(img) {
        let spanNum = $(img).parent().parent().parent().find(".down-num-font")
        let downNumBtn = $("#downNumBtn")
        let downNum = downNumBtn.data('num')

        let radio = $(img).parent().parent().find("input[type=radio]")
        let radioVal = radio.attr('checked')
        if (radioVal === true || radioVal === 'checked') {
            radio.removeAttr('checked')
            spanNum.removeClass('down-num-font-hit')
            $(img).removeClass('img-hit')
            downNum--
        } else {
            radio.attr('checked', 'checked')
            spanNum.addClass('down-num-font-hit')
            $(img).addClass('img-hit')
            downNum++
        }
        downNumBtn.data('num', downNum)
        downNumBtn.text('下载('+downNum+')')
    }

    function allImgClick() {
        $(".img>img").each(function (index, e) {
            let radio = $(e).parent().parent().find("input[type=radio]")
            let radioVal = radio.attr('checked')
            if (radioVal !== true && radioVal !== 'checked') {
                imgClick(e)
            }
        })
    }

    function downloadVideo() {
        let ua = window.navigator.userAgent.toLowerCase()
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            // alert('这个是微信')
            window.location.href = '/view/download/forward?forwardUrl=' + window.location.href
            return false;
        }
        $(".img>img").each(function (index, e) {
            let radio = $(e).parent().parent().find("input[type=radio]")
            let radioVal = radio.attr('checked')
            if (radioVal === true || radioVal === 'checked') {
                download("/api/upload/files?code=1&fileName=1.mp4", index+'.mp4', 'application/octet-stream')
            }
        })
    }
    
    $(function () {

    })

</script>
</body>
</html>