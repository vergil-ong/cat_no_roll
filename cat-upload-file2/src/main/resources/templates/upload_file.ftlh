<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
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
        .container img {
            width: 100%;
            height: 130px;
        }

        .col-6 {
            padding-left: 5px;
            padding-right: 5px;
        }

        .img-card .upload-btn {
            background-color: var(--yellow-color);
            color: var(--background-black);
            border-radius: 10px;
            font-weight: bold;
            width: 8rem;
        }

        .row-pt {
            margin-top: 8px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-12" style="height: 30px"></div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >1.改造前</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视图</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视图</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="uploadImg1" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="uploadImg1" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadImg1" style="display: block;margin: 0">图片上传</label>
                            <input type="file" id="uploadImg1" accept="image/*" multiple class="no-display" onchange="uploadImg(this)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视视频</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视视频</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="uploadVideo1" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="uploadVideo1" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo1">视频上传</label>
                        <input type="file" id="uploadVideo1" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >2.安装</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视图</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视图</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadImg1" style="display: block;margin: 0">图片上传</label>
                            <input type="file"  accept="image/*" multiple class="no-display" onchange="uploadImg(this)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视视频</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视视频</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo1">视频上传</label>
                        <input type="file" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >3.展示</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视图</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视图</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadImg1" style="display: block;margin: 0">图片上传</label>
                            <input type="file"  accept="image/*" multiple class="no-display" onchange="uploadImg(this)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视视频</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视视频</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo1">视频上传</label>
                        <input type="file" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >4.布置</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视图</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视图</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadImg1" style="display: block;margin: 0">图片上传</label>
                            <input type="file"  accept="image/*" multiple class="no-display" onchange="uploadImg(this)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="/static/imgs/before2.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">正视视频</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">侧视视频</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                    <div class="col-6 little-pd">
                        <img class="" src="/static/imgs/empty1.png">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo1">视频上传</label>
                        <input type="file" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">
                    </div>
                </div>
            </div>
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

<div style="position: fixed;bottom: -100px">
    <video controls="controls" id="fileVideo" style="opacity: 0"></video>
    <canvas id="videoCanvas" width="320" height="200" style="display:none"></canvas>
</div>

<script>
    $(function () {

    })

    function uploadImg(inputFile) {
        let fileId = $(inputFile).attr("id")
        var files = $(inputFile)[0].files;
        if (files.length === 0) {
            return;
        }
        if (files.length > 2) {
            alert('一次性最多上传2张照片')
        }
        uploadFile(files, fileId, 0)
    }

    function uploadFile(files, fileId, index) {
        try {
            if (files.length <= index) {
                return
            }
            console.info(files);
            var formData = new FormData();
            formData.append("file", files[index]);
            $.ajax({
                url: "/api/upload/upload?code=${code}",
                type: 'post',
                data: formData,
                timeout:3*1000*1000,
                contentType: false,
                processData: false,
                error:function (res) {
                    index += 1
                    uploadFile(files, fileId, index)
                },
                success: function (res) {
                    $("."+fileId+":eq("+index+")").attr("src", res)
                    console.log('success res ', res)

                    index += 1
                    uploadFile(files, fileId, index)
                }
            })
        } catch (e) {
            console.info(e)
        } finally {

        }
    }

    function uploadVideo(inputFile) {
        let fileId = $(inputFile).attr("id")
        var files = $(inputFile)[0].files;
        if (files.length === 0) {
            return;
        }
        if (files.length > 2) {
            alert('一次性最多上传2个视频')
        }
        uploadVideoFile(files, fileId, 0)
    }

    function uploadVideoFile(files, fileId, index) {
        try {
            if (files.length <= index) {
                return
            }
            console.info(files);
            var formData = new FormData();
            formData.append("file", files[index]);
            $.ajax({
                url: "/api/upload/upload?code=${code}",
                type: 'post',
                data: formData,
                timeout:3*1000*1000,
                contentType: false,
                processData: false,
                error:function (res) {
                    index += 1
                    uploadVideoFile(files, fileId, index)
                },
                success: function (res) {
                    // $("."+fileId+":eq("+index+")").attr("src", res)
                    console.log('success res ', res)
                    index += 1

                    cutImage(res,
                        $("."+fileId+":eq("+(index-1)+")"),
                        files,
                        fileId,
                        index)
                }
            })
        } catch (e) {
            console.info(e)
        } finally {

        }
    }

    function cutImage(url, imgJqObj, files, fileId, index) {
        $("#fileVideo").attr("src", url)
        let video = $("#fileVideo")[0]
        let canvas = $("#videoCanvas")[0]
        const ctx = canvas.getContext('2d') // 绘制2d
        video.crossOrigin = 'anonymous' // 解决跨域问题，也就是提示污染资源无法转换视频
        video.currentTime = 1 // 第一帧
        video.oncanplay = () => {
            console.log('video', video)
            canvas.width = video.clientWidth // 获取视频宽度
            canvas.height = video.clientHeight // 获取视频高度
            // 利用canvas对象方法绘图
            ctx.drawImage(video, 0, 0, video.clientWidth, video.clientHeight)
            // 转换成base64形式
            const imgUrl = canvas.toDataURL('image/png') // 截取后的视频封面
            imgJqObj.attr("src", imgUrl)
            $("#fileVideo").attr("src", "")
            uploadVideoFile(files, fileId, index)
        }
    }
</script>
</body>
</html>