<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src='/static/js/bootstrap.bundle.js'></script>
    <script src="/static/js/common.js?v=202401301725"></script>
    <script src="/static/js/aliyun-oss-sdk-6.18.1.min.js"></script>
    <script src="/static/js/wave.js"></script>
    <link rel="stylesheet" href='/static/css/common.css?v=202401221627'>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>
    <link rel="stylesheet" href='/static/css/bootstrap-icons.css'>
    <link rel="stylesheet" href='/static/css/wave.css'>

    <style>
        body {
            padding-bottom: 80px;
            height:100%
        }

        .row h1 {
            color: white;
            font-size: 35px;
            border-bottom: 6px solid var(--yellow-color);
            padding-bottom: 0;
            margin-bottom: 0;
        }
        .h-subtitle {
            color: var(--yellow-color);
            font-weight: bold;
            margin-bottom: 8px;
        }
        .img-title {
            color: white;
        }
        .container img {
            width: 100%;
            height: 130px;
        }
        .container .img-div {
            position:absolute;
            top:0;
            left:0;
            z-index:1000
        }
        .img-div i {
            margin-left: 5px;
            font-size: 15px;
        }
        .container .img-div-num {
            position:absolute;
            top:20px;
            left:0;
            z-index:1000
        }
        .container .img-play {
            opacity: 0.5;
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

        .row-pt-title {
            margin-top: 64px;
        }

        .btn-bottom {
            display: block;
            margin: 30px auto 0 auto;
            background-color: var(--yellow-color);
            font-weight: bold;
            font-size: 28px;
            border-radius: 24px;
            height: 48px;
            width: 14rem;
        }

        .subtitle-h {
            height: 20px;
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
                <h1 >1.原场景</h1>
            </div>
            <div class="d-flex justify-content-center subtitle-h">
                <span class="h-subtitle">图片2张 + 视频2个</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">图片参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.beforeImg1Url}">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.beforeImg2Url}">
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
            <#--<div class="text-center">
                <span class="img-title">图片预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg1" data-index="0" src="${userUploadInfo.beforeImg1Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg1-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg1-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg1-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg1-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg1" data-index="1" src="${userUploadInfo.beforeImg2Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg1-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg1-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg1-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg1-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadImg1" style="display: block;margin: 0">图片上传</label>
                            <input type="file" id="uploadImg1" accept="image/*" multiple class="no-display" onchange="uploadImg(this, 2)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
<#--                        <div class="main" style="position:relative;">-->
                            <div class="img">
                                <img src="${systemUploadInfo.beforeVideoImg1Url}"/>
                            </div>
                            <div class="img-div">
                                <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.beforeVideo1Url}', this)" />
                            </div>
<#--                        </div>-->
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.beforeVideoImg2Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.beforeVideo2Url}', this)" />
                        </div>
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
            <#--<div class="text-center">
                <span class="img-title">视频预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo1" data-index="0" src="${userUploadInfo.beforeVideoImg1Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.beforeVideo1Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo1-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo1-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo1-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo1-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo1" data-index="1" src="${userUploadInfo.beforeVideoImg2Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.beforeVideo2Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo1-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo1-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo1-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo1-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo1">视频上传</label>
                        <input type="file" id="uploadVideo1" accept="video/*" multiple class="no-display" onchange="uploadVideo(this, 2)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt-title">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >2.安装</h1>
            </div>
            <div class="d-flex justify-content-center subtitle-h">
                <span class="h-subtitle">视频4个</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.installVideoImg1Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.installVideo1Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.installVideoImg2Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.installVideo2Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">1. 拆箱</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">2. 入场</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo2" data-index="0" src="${userUploadInfo.installVideoImg1Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.installVideo1Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo2-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo2-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo2-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo2-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo2" data-index="1" src="${userUploadInfo.installVideoImg2Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.installVideo2Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo2-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo2-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo2-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo2-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
<#--                            <label class="upload-btn" for="uploadVideo2">视频上传</label>-->
<#--                            <input type="file" id="uploadVideo2" accept="video/*" multiple class="no-display" onchange="uploadVideo(this)">-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row ">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.installVideoImg3Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.installVideo3Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.installVideoImg4Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.installVideo4Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">3. 打孔</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">4. 上螺丝</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo2" data-index="2" src="${userUploadInfo.installVideoImg3Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.installVideo3Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo2-2" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo2-2"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo2-2" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo2-2">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo2" data-index="3" src="${userUploadInfo.installVideoImg4Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.installVideo4Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo2-3" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo2-3"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo2-3" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo2-3">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo2">视频上传</label>
                        <input type="file" id="uploadVideo2" accept="video/*" multiple class="no-display" onchange="uploadVideo(this, 4)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-pt-title">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >3.展示</h1>
            </div>
            <div class="d-flex justify-content-center subtitle-h">
                <span class="h-subtitle">图片6张 + 视频6个</span>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">图片参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg1Url}">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg2Url}">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">1.正视图</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">2.侧视图</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">图片预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="0" src="${userUploadInfo.displayImg1Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg3-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="1" src="${userUploadInfo.displayImg2Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg3-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg3Url}">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg4Url}">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">3.细节</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">4.细节</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="2" src="${userUploadInfo.displayImg3Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg3-2" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-2"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-2" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-2">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="3" src="${userUploadInfo.displayImg4Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg3-3" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-3"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-3" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-3">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg5Url}">
                    </div>
                    <div class="col-6 little-pd">
                        <img src="${systemUploadInfo.displayImg6Url}">
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">5.电表箱</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">6.镜子</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="4" src="${userUploadInfo.displayImg5Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadImg3-4" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-4"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-4" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-4">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadImg3" data-index="5" src="${userUploadInfo.displayImg6Url?default('/static/imgs/emptyImg.png')}"
                                 onclick="showImageModal(this)"/>
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 1)"></i>
                        </div>'<div class="img-div" id="canvasDiv-uploadImg3-5" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadImg3-5"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadImg3-5" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadImg3-5">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadImg3">图片上传</label>
                        <input type="file" id="uploadImg3" accept="image/*" multiple class="no-display" onchange="uploadImg(this, 6)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--展示视频-->
    <div class="row row-pt">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg1Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo1Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg2Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo2Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">1.正视平拍</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">2.侧视推拉</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="0" src="${userUploadInfo.displayVideoImg1Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo1Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="1" src="${userUploadInfo.displayVideoImg2Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo2Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg3Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo3Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg4Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo4Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">3.细节平拍</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">4.细节竖拍</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="2" src="${userUploadInfo.displayVideoImg3Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo3Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-2" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-2"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-2" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-2">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="3" src="${userUploadInfo.displayVideoImg4Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo4Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-3" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-3"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-3" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-3">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg5Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo5Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.displayVideoImg6Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.displayVideo6Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">5.电箱拉合</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">6.镜子开合</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="4" src="${userUploadInfo.displayVideoImg5Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo5Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-4" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-4"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-4" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-4">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo3" data-index="5" src="${userUploadInfo.displayVideoImg6Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.displayVideo6Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo3-5" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo3-5"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo3-5" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo3-5">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                            <label class="upload-btn" for="uploadVideo3">视频上传</label>
                            <input type="file" id="uploadVideo3" accept="video/*" multiple class="no-display" onchange="uploadVideo(this, 6)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row row-pt-title">
        <div class="col-12">
            <div class="d-flex justify-content-center">
                <h1 >4.布置</h1>
            </div>
            <div class="d-flex justify-content-center subtitle-h">
                <span class="h-subtitle">[固定镜头，轮流换摆件] 视频6个</span>
            </div>
        </div>
    </div>
    <div class="row row-pt">
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频参考</span>
            </div>-->
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg1Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo1Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg2Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo2Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">全景</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">特写1</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <#--<div class="text-center">
                <span class="img-title">视频预览</span>
            </div>-->
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="0" src="${userUploadInfo.disposeVideoImg1Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo1Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-0" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-0"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-0" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-0">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="1" src="${userUploadInfo.disposeVideoImg2Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo2Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-1" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-1"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-1" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-1">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <div class="div-btn-center">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg3Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo3Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg4Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo4Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">特写2</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">特写3</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="2" src="${userUploadInfo.disposeVideoImg3Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo3Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-2" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-2"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-2" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-2">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="3" src="${userUploadInfo.disposeVideoImg4Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo4Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-3" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-3"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-3" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-3">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="img-card no-pd">
                <#--demo-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg5Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo5Url}', this)" />
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img src="${systemUploadInfo.disposeVideoImg6Url}"/>
                        </div>
                        <div class="img-div">
                            <img src="/static/imgs/play.png" class="img-play" onclick="showVideoModal('${systemUploadInfo.disposeVideo6Url}', this)" />
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-6 little-pd">
                        <span class="img-card-text">特写4</span>
                    </div>
                    <div class="col-6 little-pd">
                        <span class="img-card-text">特写5</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="img-card no-pd">
                <#--用户上传-->
                <div class="row img-card-inner">
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="4" src="${userUploadInfo.disposeVideoImg5Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo5Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-4" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-4"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-4" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-4">0%</span>
                        </div>
                    </div>
                    <div class="col-6 little-pd">
                        <div class="img">
                            <img class="uploadVideo4" data-index="5" src="${userUploadInfo.disposeVideoImg6Url?default('/static/imgs/emptyVideo.png')}"
                                 data-video-url="${userUploadInfo.disposeVideo6Url?default('/static/imgs/emptyVideo.png')}"
                                 onclick="showUserVideoModal(this)">
                        </div>
                        <div class="img-div">
                            <i class="bi bi-x-circle-fill" onclick="delImg(this, 2)"></i>
                        </div>
                        <div class="img-div" id="canvasDiv-uploadVideo4-5" style="display: none">
                            <canvas class="wave-canvas" id="canvas-uploadVideo4-5"></canvas>
                        </div>
                        <div class="img-div-num" id="proNum-uploadVideo4-5" style="display: none">
                            <span class="wave-proNum" id="proNumSp-uploadVideo4-5">0%</span>
                        </div>
                    </div>
                </div>
                <div class="row img-card-inner-text">
                    <div class="col-12 little-pd">
                        <label class="upload-btn" for="uploadVideo4">视频上传</label>
                        <input type="file" id="uploadVideo4" accept="video/*" multiple class="no-display" onchange="uploadVideo(this, 6)">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 50px">
<#--        <div class="col-12">-->
<#--            <button type="button" class="btn-bottom">通知PR同学</button>-->
<#--        </div>-->
        <div class="col-12">
            <div class="text-center" style="margin-bottom: 20px">
                <span class="wechat-bottom">猫不卷微信号:mbj6699</span>
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
            <button type="button" class="footer-btn" onclick="jumpDownload()">下载视频</button>
        </div>
    </div>
</div>

<div style="position: fixed;bottom: -100px">
    <video controls="controls" id="fileVideo" style="opacity: 0"></video>
    <canvas id="videoCanvas" width="320" height="200" style="display:none"></canvas>
</div>

<div class="modal fade bd-example-modal-lg" id="videoModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <video controls id="modalVideo" width="300" playsinline="true"></video>
            </div>
            <#--<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>-->
        </div>
    </div>
</div>

<div class="modal fade bd-example-modal-lg" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" style="height: 80vh">
                <img src="" id="modalImage" style="object-fit: contain;width: 100%;height: 100%" />
            </div>
            <#--<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>-->
        </div>
    </div>
</div>

<script>
    var client
    $(function () {
        $.get('/api/sts/info', function(data, status) {
            client = new OSS({
                // yourRegion填写Bucket所在地域。以华东1（杭州）为例，Region填写为oss-cn-hangzhou。
                region: 'oss-cn-hangzhou',
                // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）。
                accessKeyId: data.accessKeyId,
                accessKeySecret: data.accessKeySecret,
                // 从STS服务获取的安全令牌（SecurityToken）。
                stsToken: data.stsToken,
                refreshSTSToken: async () => {
                    // 向您搭建的STS服务获取临时访问凭证。
                    const info = await fetch('/api/sts/info');
                    return {
                        accessKeyId: info.accessKeyId,
                        accessKeySecret: info.accessKeySecret,
                        stsToken: info.stsToken
                    }
                },
                // 刷新临时访问凭证的时间间隔，单位为毫秒。
                refreshSTSTokenInterval: 300000,
                // 填写Bucket名称。
                bucket: 'cat-no-roll'
            });
        })
    })

    const headers = {
        // 指定该Object被下载时的网页缓存行为。
        "Cache-Control": "no-cache",
        // 指定该Object被下载时的名称。
        "Content-Disposition": "example.txt",
        // 指定该Object被下载时的内容编码格式。
        "Content-Encoding": "utf-8",
        // 指定过期时间，单位为毫秒。
        Expires: "1000",
        // 指定Object的存储类型。
        "x-oss-storage-class": "Standard",
        // 指定Object标签，可同时设置多个标签。
        "x-oss-tagging": "Tag1=1&Tag2=2",
        // 指定初始化分片上传时是否覆盖同名Object。此处设置为true，表示禁止覆盖同名Object。
        "x-oss-forbid-overwrite": "true",
    };

    async function put(file, wechatCode, middleDir, fileName, fileId, fileIndex) {
        try {
            // object表示上传到OSS的文件名称。
            // file表示浏览器中需要上传的文件，支持HTML5 file和Blob类型。
            // const r1 = await client.put(wechatCode + "/" + fileName, file);
            // console.log('put success: %j', r1);
            // const r2 = await client.get(fileName);
            // console.log('get success: %j', r2);
            $("#canvasDiv-"+fileId+"-"+fileIndex).show()
            $("#proNum-"+fileId+"-"+fileIndex).show()
            let canvasVideo = $("#canvas-"+fileId+"-"+fileIndex)
            var waveVideo = new Wave("canvas-"+fileId+"-"+fileIndex, "proNumSp-"+fileId+"-"+fileIndex,
                canvasVideo.width(), canvasVideo.height())
            waveVideo.init()

            const name = wechatCode + "/" + middleDir + "/" + fileName;
            // let fileSuffix = getFileSuffix(fileName);
            // let fileMineType = getFileMineType(fileSuffix);
            // 获取DOM。
            const options = {
                // 获取分片上传进度、断点和返回值。
                progress: (p, cpt, res) => {
                    console.log(p);
                    waveVideo.draw(p*100)
                },
                // 设置并发上传的分片数量。
                parallel: 4,
                // 设置分片大小。默认值为1 MB，最小值为100 KB。
                partSize: 1024 * 1024,
                // headers,
                // 自定义元数据，通过HeadObject接口可以获取Object的元数据。
                // meta: { year: 2020, people: "test" },
                mime: "application/octet-stream",
                // mime: "text/plain",
            };
            // if (fileMineType && fileMineType !== '') {
            //     options.mime = fileMineType
            // }
            console.log('options', options)

            const res = await client.multipartUpload(name, file, {
                ...options,
            });
            console.log(res);
        } catch (e) {
            console.error('error: %j', e);
            // alert(e)
        }
    }

    async function uploadImg(inputFile, limit) {
        let fileId = $(inputFile).attr("id")
        var files = $(inputFile)[0].files;
        if (files.length === 0) {
            return;
        }
        let emptyImgArray = []
        $("."+fileId).each(function(index, e){
            let currentImg = $(e)
            let src = currentImg.attr('src')
            if (src === '/static/imgs/emptyImg.png'
                || src === '/static/imgs/emptyVideo.png') {
                emptyImgArray.push(currentImg.data('index'))
            }
        })
        console.log('emptyImgArray ', emptyImgArray)
        if (files.length > emptyImgArray.length) {
            alert('本次最多上传'+emptyImgArray.length+'张照片')
            return;
        }
        await uploadFile(files, fileId, 0, emptyImgArray)
    }

    async function uploadFile(files, fileId, index, emptyImgArray) {
        try {
            if (emptyImgArray.length <= index) {
                return
            }
            if (files.length <= index) {
                return
            }
            console.info(files);
            let wechatCode = '${code}'
            let fileName = files[index].name;
            let fileExt = fileName.substring(fileName.lastIndexOf("."));
            let newFileName = uuidC() + fileExt
            let middleDir = ''
            if (fileId === 'uploadImg1') {
                middleDir = '1before'
            } else if (fileId === 'uploadImg3') {
                middleDir = '3decorate'
            }
            await put(files[index], wechatCode, middleDir, newFileName, fileId, emptyImgArray[index])
            // console.log('put success')
            let newFileKey = wechatCode + "/" + middleDir + "/" + newFileName

            var formData = new FormData();
            formData.append("fileUrl", newFileKey);
            formData.append("fileName", newFileName);
            $.ajax({
                url: "/api/upload/img/upload?fileId="+fileId+"&fileIndex="+emptyImgArray[index]+"&wechatCode=${code}",
                type: 'post',
                data: formData,
                timeout:3*1000*1000,
                contentType: false,
                processData: false,
                error:function (res) {
                    index += 1
                    uploadFile(files, fileId, index, emptyImgArray)
                    $("#canvasDiv-"+fileId+"-"+emptyImgArray[index]).hide()
                    $("#proNum-"+fileId+"-"+emptyImgArray[index]).hide()
                },
                success: function (res) {
                    $("."+fileId+":eq("+emptyImgArray[index]+")").attr("src", res)
                    console.log('success res ', res)
                    $("#canvasDiv-"+fileId+"-"+emptyImgArray[index]).hide()
                    $("#proNum-"+fileId+"-"+emptyImgArray[index]).hide()

                    index += 1
                    uploadFile(files, fileId, index, emptyImgArray)
                }
            })
        } catch (e) {
            console.info(e)
        } finally {

        }
    }

    async function uploadVideo(inputFile, limit) {
        let fileId = $(inputFile).attr("id")
        var files = $(inputFile)[0].files;
        if (files.length === 0) {
            return;
        }
        let emptyVideoArray = []
        $("."+fileId).each(function(index, e){
            let currentImg = $(e)
            let src = currentImg.attr('src')
            if (src === '/static/imgs/emptyImg.png'
                || src === '/static/imgs/emptyVideo.png') {
                emptyVideoArray.push(currentImg.data('index'))
            }
        })
        console.log('emptyVideoArray ', emptyVideoArray)
        if (files.length > emptyVideoArray.length) {
            alert('本次最多上传'+emptyVideoArray.length+'个视频')
            return;
        }
        await uploadVideoFile(files, fileId, 0, emptyVideoArray)
    }

    async function uploadVideoFile(files, fileId, index, emptyVideoArray) {
        try {
            if (emptyVideoArray.length <= index) {
                return
            }
            if (files.length <= index) {
                return
            }
            console.info(files);
            let wechatCode = '${code}'
            let fileName = files[index].name;
            let fileExt = fileName.substring(fileName.lastIndexOf("."));
            let newFileName = uuidC() + fileExt
            let middleDir = ''
            if (fileId === 'uploadVideo1') {
                middleDir = '1before'
            } else if (fileId === 'uploadVideo2') {
                middleDir = '2install'
            } else if (fileId === 'uploadVideo3') {
                middleDir = '3decorate'
            } else if (fileId === 'uploadVideo4') {
                middleDir = '4show'
            }
            await put(files[index], wechatCode, middleDir, newFileName, fileId, emptyVideoArray[index])
            // console.log('put success')

            let newFileKey = wechatCode + "/" + middleDir + "/" + newFileName

            var formData = new FormData();
            formData.append("fileUrl", newFileKey);
            formData.append("fileName", newFileName);
            $.ajax({
                url: "/api/upload/video/upload?fileId="+fileId+"&fileIndex="+emptyVideoArray[index]+"&wechatCode=${code}",
                type: 'post',
                data: formData,
                timeout:3*1000*1000,
                contentType: false,
                processData: false,
                error:function (res) {
                    index += 1
                    uploadVideoFile(files, fileId, index, emptyVideoArray)
                    $("#canvasDiv-"+fileId+"-"+emptyVideoArray[index]).hide()
                    $("#proNum-"+fileId+"-"+emptyVideoArray[index]).hide()
                },
                success: function (res) {
                    console.log('success res ', res)
                    let imgEleJq = $("."+fileId+":eq("+emptyVideoArray[index]+")")
                    imgEleJq.attr("src", res.imageUrl)
                    imgEleJq.attr('data-video-url', res.videoUrl)
                    imgEleJq.data('video-url', res.videoUrl)
                    $("#canvasDiv-"+fileId+"-"+emptyVideoArray[index]).hide()
                    $("#proNum-"+fileId+"-"+emptyVideoArray[index]).hide()
                    index += 1
                    uploadVideoFile(files, fileId, index, emptyVideoArray)
                }
            })
        } catch (e) {
            console.info(e)
        } finally {

        }
    }

    /*function cutImage(url, imgJqObj, files, fileId, index) {
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
    }*/

    function showVideoModal(videoUrl, btn) {
        $("#modalVideo").attr('src', videoUrl)
        $("#videoModal").modal('show')
    }

    function showUserVideoModal(imgEle) {
        let imgEleJq = $(imgEle)
        let imgSrc = imgEleJq.attr('src')
        let videoUrl = imgEleJq.data('video-url')
        if (imgSrc === '/static/imgs/emptyVideo.png'
            || videoUrl === '/static/imgs/emptyVideo.png') {
            return false
        }
        $("#modalVideo").attr('src', videoUrl)
        $("#videoModal").modal('show')
    }

    function showImageModal(imgEle) {
        let imgSrc = $(imgEle).attr('src')
        if (imgSrc === '/static/imgs/emptyImg.png') {
            return false
        }
        $("#modalImage").attr('src', imgSrc)
        $("#imageModal").modal('show')
    }

    function delImg(iconEle, imgType) {
        let img = $(iconEle).parent().parent().find(".img>img")
        let fileId = img.attr('class')
        let fileIndex = img.data('index')
        let emptyImgPath = '/static/imgs/emptyImg.png'
        if (imgType === 2) {
            emptyImgPath = '/static/imgs/emptyVideo.png'
        }
        if (img.attr('src') === emptyImgPath) {
            return;
        }
        $.ajax({
            url: "/api/upload/img/delte?fileId="+fileId+"&fileIndex="+fileIndex+"&wechatCode=${code}",
            type: 'get',
            error:function (res) {
            },
            success: function (res) {
                img.attr('src', emptyImgPath)
            }
        })
    }

    function jumpDownload() {
        location.href='/view/download/file?wechatCode=${code}'
    }
</script>
</body>
</html>