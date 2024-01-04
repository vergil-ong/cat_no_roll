<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src='/static/js/bootstrap.bundle.js'></script>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>

    <style type="text/css">
        html {
            font-size: 62.5%;
        }

        body {
            font-size: 14px;
            font-size: 2rem;
        }

        .top-box {
            background-image: linear-gradient(#ed8c07, #eed3ae);
            color: white;
            height: 5.5rem;
            font-weight: bold;
        }

    </style>
</head>
<body>
<div class="fixed-top">
    <div class="container-fluid top-box" id="topDiv">
        <div  style="display: block">
            <div style="font-size:1.5rem;padding-top:1rem;margin-left:0.5rem;float: left; ">
                订单号：<span>${code}</span> <br/>

                <span style="display: none" id ="finishSpan">
                    状态：<span  style="color: #e83e8c;font-weight: bold" /> 已提交</span>
                </span>
            </div>
            <div style="float: right; margin-right:0.5rem;font-size: 1.5rem;margin-top:2.8rem; ">
                已上传 <span style="color: #e83e8c;font-weight: bold" id="img_count">0</span> 张图片
            </div>
        </div>

    </div>
</div>



<!--图片信息-->

<div class="container" id="imgDiv" style="margin-top: 6rem;padding: 1rem;margin-bottom: 7rem">
    <div class="alert alert-warning" role="alert" style="font-size: 14px;">
        这个位置也要留给卓总讲话 <br/>
        来呱唧呱唧<br/>
        呱唧呱唧～
    </div>
    <div class="row" style="padding: 5px;">

    </div>
</div>
<div>
    <input type="file" id="photo" accept="image/*" multiple onChange="uploadImg()" hidden/>
</div>

<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true" style="margin-top: 30%">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="staticBackdropLabel">上传进度</h4>
            </div>
            <div class="modal-body">
                <div>
                    本次共上传<span id="imgSum" style="color:#ffc107;font-weight: bold">0</span>张，
                    已成功 <span id="imgSuccSum" style="color:#218838;font-weight: bold ">0</span>张。
                </div>
                <div class="progress" id="progressDiv">
                    <div id="pro" class="progress-bar progress-bar-striped" role="progressbar" style="width:0"
                         aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="upbut btn btn-primary" onclick="closeModal()"
                        style="font-size:18px;border-radius: 0.5rem;margin-right: 10px;">查看图片
                </button>
                <button type="button" class="upbut btn btn-primary " onclick="contineUpload()"
                        style="font-size:18px;background-color: #00BCD2; border-radius: 0.5rem;">继续上传
                </button>
            </div>
        </div>
    </div>
</div>


<div class="fixed-bottom" style="text-align: center;padding: 0.5rem">
    <div>
        <button onclick="upload()" type="submit" class="btn-info"
                style="border:0; background-color: #00BCD2;width: 45%; font-size:16px;border-radius: 0.5rem;margin-bottom:3px; float: left ">
            <div style="padding: 10px">点此上传图片</div>
        </button>


        <button onclick="finish()" type="submit" class="btn-info"
                style="border:0; background-color: #d2af00;width: 45%; font-size:16px;border-radius: 0.5rem;margin-bottom:3px;float: right;  ">
            <div style="padding: 10px">上传完成</div>
        </button>
    </div>


</div>

<div class="modal " id="wiexinModal" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="false" style="margin-top: 30%">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-body">
                <div style="text-align: center;font-weight: bold" class="text-info">**温馨提醒**</div>

                <div style="font-size:16px;line-height: 200% ">
                    1、安卓系统，微信内只支持<span class="text-danger">单张上传</span>！
                    为了上传方便，请点击<span class="text-danger">屏幕右上角</span> ... 使用其他浏览器打开。
                    <a href="#" style="font-size: 1.6rem;float: right">不知道怎么使用其他浏览器？</a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="upbut btn btn-primary" onclick="closeWiexinModal()"
                        style="font-size:18px;border-radius: 0.5rem;margin-right: 10px;">
                    继续在微信内上传
                </button>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var u = navigator.userAgent
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        if (isAndroid) {
            u = u.toLowerCase();
            if (u.match(/MicroMessenger/i) == 'micromessenger') {
                $("#wiexinModal").modal("show");
                $("#photo").removeAttr("multiple");
            }
        }

        if (0 >= 10) {
            $("#finishSpan").show(500);
        }
    })

    function delImg(path) {
        // var id = $(obj).attr("data-id");//获得参数的值
        $.post("/api/upload/delete?fileId=" + path + "&code=${code}", function (res) {
            loadImgs();
        })
    }

    function finish() {
        if (confirm("您确定上传完毕？")) {
            /*$.post("/mgp/photo/finish?id=" + 853, function (res) {
                alert("图片已经上传完成，建议截图发给客服！");
                $("#topDiv").hide(100);
                $("#topDiv").show(300);
                $("#finishSpan").hide(100);
                $("#finishSpan").show(500);
            })*/
            alert("图片已经上传完成，建议截图发给客服！");
        }
    }


    function upload() {
        $('#staticBackdrop').modal("hide");
        $("#photo").trigger('click');
        console.info($("#photo"));
    }

    function closeWiexinModal() {
        $('#wiexinModal').modal("hide");
    }

    function closeModal() {
        $('#staticBackdrop').modal("hide");
        $("#waitProDiv").css("display", "");
        $("#proDiv").css("display", "none");
        $("#pro").css("width", "0%");
    }

    function contineUpload() {
        $("#waitProDiv").css("display", "");
        $("#proDiv").css("display", "none");
        $("#pro").css("width", "0%");
        upload();
    }

    function uploadImg() {
        //alert(853);
        $("#imgSuccSum").text(0);
        var files = $("#photo")[0].files;
        if (files.length === 0) {
            return;
        }

        $('#staticBackdrop').modal("show");
        $(".upbut").attr("disabled", true);
        $("#proDiv").css("display", "");
        $("#waitProDiv").css("display", "none");
        $("#imgSum").text(files.length);

        console.info(files);
        $("#pro").css("width", "15%");
        uploadImgSubmit(files, 0, 0);
    }

    function uploadImgSubmit(files, index, sucCount) {
        try {
            console.info(files);
            var formData = new FormData();
            formData.append("file", files[index]);
            $.ajax({
                url: "/api/upload/upload?code=${code}", /*接口域名地址 mgp/photo/upload.vapi*/
                type: 'post',
                data: formData,
                timeout:30000,
                contentType: false,
                processData: false,
                error:function (res) {
                    uploadOneComplete(sucCount, index, files);
                },
                success: function (res) {
                    sucCount++;
                    uploadOneComplete(sucCount, index, files);
                }
            })
        } catch (e) {
            console.info(e)
            $(".upbut").removeAttr("disabled");
        } finally {

        }
    }

    function uploadOneComplete(sucCount, index, files) {
        index++
        $("#imgSuccSum").text(sucCount);
        if (index === files.length) {
            // $('#staticBackdrop').modal("show");
            $("#pro").css("width", "100%");
            $(".upbut").removeAttr("disabled");
            loadImgs();
            return;
        }
        $("#pro").css("width", index / files.length * 100 + "%");
        uploadImgSubmit(files, index, sucCount);
    }

    function loadImgs() {
        $.post("/api/upload/orderImgs?code=${code}", function (res) {
            //alert(33333);
            $("#imgDiv").html(res);
            $("#img_count").html($("#imgDiv").find("img").length);
        })
    }
</script>
</body>
</html>