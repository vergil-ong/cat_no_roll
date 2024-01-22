<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src='/static/js/fakeLoader.min.js'></script>
    <script src='/static/js/bootstrap.bundle.js'></script>
    <script src="/static/js/download.js"></script>
    <link rel="stylesheet" href='/static/css/common.css?v=20240122'>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>
    <link rel="stylesheet" href='/static/css/fakeLoader.min.css'>

    <style>
        body {
            background-color: white !important;
        }
        .video-screen {
            width: 200px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col text-center">
            <h1>爆款视频上传</h1>
        </div>
    </div>
    <div class="row" style="margin-top: 10px">
        <div class="col">
            <input type="text" class="form-control" id="wechatCode" placeholder="微信号码"/>
        </div>
        <div class="col">
            <div class="btn-group">
                <button type="button" class="btn btn-primary" onclick="refreshTable()">查询</button>
            </div>
            <div class="btn-group">
                <label class="btn btn-info" for="uploadFile" style="display: block;margin: 0" >视频上传</label>
                <input type="file" id="uploadFile" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 10px">
        <div class="col-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>视频ID</th>
                    <th>视频截图</th>
                    <th>用户下载次数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="videoTb">
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="fakeLoader"></div>

<script>
    $(function () {
        $(".fakeLoader").hide()
    })

    function checkWechatCode() {
        let wechatCode = $("#wechatCode").val()
        if (!wechatCode) {
            alert('请输入微信号')
            return false;
        }
        return true;
    }

    function uploadVideo() {
        let checkWechatRes = checkWechatCode();
        if (checkWechatRes === false) {
            return false
        }
        var files = $("#uploadFile")[0].files;
        if (files.length === 0) {
            return;
        }
        loading()
        var formData = new FormData();
        formData.append("file", files[0]);
        $.ajax({
            url: "/api/upload/admin/video?wechatCode="+$("#wechatCode").val(),
            type: 'post',
            data: formData,
            timeout:3*1000*1000,
            contentType: false,
            processData: false,
            error:function (res) {
                console.log("upload admin video fail", res)
                unloading()
            },
            success: function (res) {
                console.log("upload admin video success", res)
                unloading()
                refreshTable()
            }
        })

    }

    function loading() {
        console.log('loading')
        $.fakeLoader({
            timeToHide:15*1000,
            bgColor:"#f89a10",
            spinner:"spinner3"
        });
        $(".fakeLoader").show();
    }

    function unloading() {
        console.log('unloading')
        $(".fakeLoader").fadeOut();
    }

    function refreshTable() {
        $("#videoTb").empty()
        $.ajax({
            url: "/api/upload/admin/video/list?wechatCode="+$("#wechatCode").val(),
            type: 'get',
            timeout:30*1000,
            contentType: false,
            processData: false,
            error:function (res) {
                console.log("get table fail", res)
            },
            success: function (res) {
                console.log("get table success", res)
                if (res.status !== 200) {
                    alert(res.message)
                    return false;
                }
                let videoTbHtml = ""
                for (let adminUploadVideoVo of res.data) {
                    videoTbHtml += "<tr>"
                    videoTbHtml += "    <td>"+adminUploadVideoVo.adminUploadVideo.id+"</td>"
                    videoTbHtml += "    <td><img class=\"video-screen\" src=\""+adminUploadVideoVo.imgUrl+"\"></td>"
                    videoTbHtml += "    <td>"+adminUploadVideoVo.adminUploadVideo.userDownCount+"</td>"
                    let videoUrl = adminUploadVideoVo.videoUrl
                    videoTbHtml += "    <td>" +
                                            "<button type=\"button\" class=\"btn btn-info\" onclick='downloadVideo(\""+videoUrl+"\", \""+adminUploadVideoVo.videoFileName+"\")'>下载</button>" +
                                            "<button type=\"button\" class=\"btn btn-danger\" onclick='deleteVideo(\""+adminUploadVideoVo.adminUploadVideo.id+"\")'>删除</button>" +
                                    "</td>"
                    videoTbHtml += "</tr>"
                }
                $("#videoTb").html(videoTbHtml)
            }
        })
    }

    function deleteVideo(videoId) {
        $.ajax({
            url: "/api/upload/admin/video/delete?adminVideoId="+videoId,
            type: 'post',
            timeout:3*1000*1000,
            contentType: false,
            processData: false,
            error:function (res) {
                console.log("upload admin video fail", res)
            },
            success: function (res) {
                console.log("upload admin video success", res)
                refreshTable()
            }
        })
    }

    function downloadVideo(videoUrl, videoName) {
        download(videoUrl, videoName, 'application/octet-stream')
    }

</script>
</body>
</html>