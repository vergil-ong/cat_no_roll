<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <link rel="stylesheet" href='/static/css/common.css?v=20240122'>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>

    <style>
        .font-label {
            font-weight: bold;
            font-size: 28px;
            width: 11rem;
            display: block;
            margin: 0 auto;
        }
        .wechat-input {
            border-radius: 15px;
            height: 40px;
            padding-left: 5px;
            padding-right: 5px;
            width: 16rem;
        }
        .upload-btn {
            display: block;
            margin: 30px auto 0 auto;
            background-color: var(--yellow-color);
            font-weight: bold;
            font-size: 17px;
            border-radius: 24px;
            height: 48px;
            width: 7.5rem;
        }
    </style>
</head>
<body>
<!-- 顶部logo -->
<div class="top-logo">
    <img src="/static/imgs/logo.jpg">
</div>
<div class="container">
    <div class="row" style="height: 100vh">
        <div class="col-1"></div>
        <div class="col-10">
            <div class="d-flex align-items-center justify-content-center" style="height: 100%">
                <div style="display: block">
                    <div>
                        <span class="font-yellow font-label">请输入微信号</span>
                    </div>
                    <div>
                        <input type="text" class="wechat-input" id="wechatCode">
                    </div>
                    <div>
                        <div class="btn-group">
                            <button type="button" style="margin-left: 5px" class="upload-btn" onclick="uploadClick()">上传素材</button>
                            <button type="button" style="margin-left: 10px" class="upload-btn" onclick="downloadClick()">下载视频</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-1"></div>
    </div>
</div>

<script>
    $(function () {

    })

    function uploadClick() {
        let wechatCode = $("#wechatCode").val()
        if (!wechatCode) {
            alert('请输入微信号')
            return false
        }
        window.location.href = "/view/upload/file?wechatCode=" + wechatCode;
    }

    function downloadClick() {
        let wechatCode = $("#wechatCode").val()
        if (!wechatCode) {
            alert('请输入微信号')
            return false
        }
        window.location.href = "/view/download/file?wechatCode=" + wechatCode;
    }
</script>
</body>
</html>