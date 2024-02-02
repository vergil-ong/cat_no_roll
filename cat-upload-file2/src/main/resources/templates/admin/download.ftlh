<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src='/static/js/fakeLoader.js'></script>
    <script src='/static/js/bootstrap.bundle.js'></script>
    <script src="/static/js/common.js?v=202401231214"></script>
    <script src="/static/js/download.js"></script>
    <script src="/static/js/aliyun-oss-sdk-6.18.1.min.js"></script>
    <script src="/static/js/bootstrap-table.min.js"></script>
    <script src="/static/js/bootstrap-table-zh-CN.js"></script>

    <link rel="stylesheet" href='/static/css/common.css?v=20240202'>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>
    <link rel="stylesheet" href='/static/css/fakeLoader.min.css'>
    <link rel="stylesheet" href='/static/css/bootstrap-table.min.css'>

    <style>
        /*body {*/
        /*    background-color: white !important;*/
        /*}*/
        .pagination-detail {
            color: white;
        }
        .row-border {
            border: 2px solid;
            border-left: 0;
            padding-left: 0;
            padding-right: 0;
        }
        .row-border-first {
            border-left: 2px solid;
        }
        .user-img {
            width: 100%;
            max-height: 3.9rem;
        }
        .table {
            background-color: white;
        }
        .table .little-pd {
            padding-right: 1px;
            padding-left: 1px;
        }
        .bootstrap-table .fixed-table-container .fixed-table-body {
            overflow: hidden;
        }
        .line {
            border-bottom: 1px solid #c8c8c8;
            margin-bottom: 20px;
        }
        input[type='checkbox'] {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            border-radius: 50%;
            width: 15px;
            height: 15px;
            border: 2px solid var(--yellow-color);
            outline: none;
            cursor: pointer;
        }
        input[type="checkbox"]:checked {
            background-color: var(--yellow-color);
        }
        input.admin-video-checkbox {
            border: 2px solid black;
        }
        input.admin-video-checkbox:checked {
            background-color: black;
        }

        .row-fixed-h {
            height: 8rem;
        }
        .row-fixed-sub-h {
            height: 4rem;
        }
        .row-fixed-h2 {
            height: 16.6rem;
            max-height: 16.6rem;
            overflow-y: auto
        }
        .row-no-margin {
            margin-left: 0;
            margin-right: 0;
        }
        .btn-yellow {
            background-color: var(--yellow-color);
            color: white;
        }
        .btn-red {
            background-color: red;
            color: white;
        }
        .btn-admin {
            border-radius: 0.7rem !important;
            font-size: 0.7rem !important;
        }
        .wechat-search {
            border: 1px solid white;
            padding: 5px 5px 5px 5px;
        }
        .wechat-search input {
            background-color: black;
            color: white;
            width: 250px;
            border: 0;
        }
        .search-input::-webkit-input-placeholder {
            color: white;
        }
        .search-btn {
            font-weight: bold;
            background-color: var(--yellow-color);
            border-radius: 15px;
        }
        .search-btn-red {
            background-color: red;
            color: white;
        }
        .tb-cus {
            border-collapse: separate;
            border-spacing: 0 30px;
            background-color: black;
        }
        .tb-row {
            background-color: white;
        }
    </style>
</head>
<body>
<div class="container fixed-top" style="background-color: black">
    <div class="admin-top-logo">
        <img src="/static/imgs/logo.jpg">
    </div>
    <div>
        <div class="row justify-content-center">
            <div class="col-auto">
                <div class="form-inline wechat-search">
                    <input class="search-input" id="wechat-code-input" type="text" placeholder="搜索微信号">
                    <button type="button" onclick="search()">搜索</button>
                </div>
            </div>
            <div class="col-auto">
                <button type="button" class="search-btn" onclick="batchDownload()">下载</button>
                <button type="button" class="search-btn search-btn-red" onclick="batchDelete()">删除</button>
            </div>
        </div>
        <div class="row" style="height: 10px;border-bottom: 2px solid var(--yellow-color);padding-bottom: 30px"></div>
    </div>
</div>

<#--<div class="container" style="margin-top: -35px">

</div>-->
<div class="container" style="margin-top: 180px">
    <table class="table" id="tb">
    </table>
</div>

<div class="fakeLoader"></div>

    <script>
        var client
        $(function () {
            $(".fakeLoader").hide()
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
            $("#tb").bootstrapTable({
                method:'get',
                url:'/api/upload/admin/user/video/list',
                pageNumber : 1, //初始化加载第一页
                pagination : true,//是否分页
                sidePagination : 'server',//server:服务器端分页|client：前端分页
                pageSize : 10,//单页记录数
                pageList: [10],
                showHeader: false,
                classes: 'tb-cus ',
                rowStyle: function(row, index) {
                  return {
                      classes: "tb-row"
                  }
                },
                // showRefresh : true,//刷新按钮
                queryParams : function(params) {//上传服务器的参数
                    var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
                        limit : params.limit, // 每页显示数量
                        offset : params.offset, // SQL语句起始索引
                        page : (params.offset / params.limit) , //当前页码
                        wechatCode : $("#wechat-code-input").val(),
                    };
                    return temp;
                },
                columns:[{
                    align: "center",
                    field: 'userUploadInfo',
                    formatter: function (value, row, index) {
                        // console.log('row data is ', row)
                        let beforeCheckId = 'before_'+value.wechatCode
                        let installCheckId = 'install_'+value.wechatCode
                        let displayCheckId = 'display_'+value.wechatCode
                        let disposeCheckId = 'dispose_'+value.wechatCode
                        let adminUploadCheckId = 'admin_upload'+value.wechatCode
                        let wechatCodeId = 'wechat_code_'+value.wechatCode
                        let html = '<div class="row">'
                        html += '<div class="col-10">'
                        // html += '<span>' + value.wechatCode + '</span>'
                        html += '<input type="checkbox" id="'+wechatCodeId+'" data-wechat-code="'+value.wechatCode+'" onclick="wechatCodeCheck(this)"><label style="margin-left: 10px" for="'+wechatCodeId+'">'+value.wechatCode+'</label>'
                        html += '</div>'
                        html += '<div class="col-2">'
                        html += '</div>'
                        html += '</div>'
                        html += '<div class="row">'
                        html += '<div class="col-10" style="padding-right: 1px">'
                        html += '   <div class="row">'
                        html += '   <div class="col-3">'
                        html += '       <div class="row row-border row-border-first" style="margin-left: 5px">'
                        html += '       <div class="col-12">'
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '           <input type="checkbox" id="'+beforeCheckId+'"><label for="'+beforeCheckId+'">1.原场景</label>'
                        html += '           </div>'
                        html += '           </div>'
                        //安装前图片
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        let beforeImg1Url = row.beforeImg1Url==null?'':row.beforeImg1Url
                        html += '                   <img class="user-img '+beforeCheckId+'_data" ' +
                                                    'data-img-download="'+beforeImg1Url+'" data-name="1_img_1" src="'+beforeImg1Url+'" >'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        let beforeImg2Url = row.beforeImg2Url==null?'':row.beforeImg2Url
                        html += '                   <img class="user-img '+beforeCheckId+'_data"' +
                                                    'data-img-download="'+beforeImg2Url+'" data-name="1_img_2" src="'+beforeImg2Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'
                        //分割线
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '               <div class="line">&nbsp</div>'
                        html += '           </div>'
                        html += '           </div>'
                        //安装前视频
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        let beforeVideoImg1Url = row.beforeVideoImg1Url==null?'':row.beforeVideoImg1Url
                        let beforeVideo1Url = row.beforeVideo1Url==null?'':row.beforeVideo1Url
                        html += '                   <img class="user-img '+beforeCheckId+'_data" ' +
                                                    'data-video-download="'+beforeVideo1Url+'" data-name="1_video_1" src="'+beforeVideoImg1Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        let beforeVideoImg2Url = row.beforeVideoImg2Url==null?'':row.beforeVideoImg2Url
                        let beforeVideo2Url = row.beforeVideo2Url==null?'':row.beforeVideo2Url
                        html += '                   <img class="user-img '+beforeCheckId+'_data" ' +
                                                    'data-video-download="'+beforeVideo2Url+'" data-name="1_video_2" src="'+beforeVideoImg2Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'

                        html += '       </div>'
                        html += '       </div>'
                        html += '   </div>'
                        html += '   <div class="col-3">'
                        html += '       <div class="row row-border">'
                        html += '       <div class="col-12">'
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '           <input type="checkbox" id="'+installCheckId+'"><label for="'+installCheckId+'">2.安装</label>'
                        html += '           </div>'
                        html += '           </div>'
                        //安装图片
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'
                        //分割线
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '               <div class="line">&nbsp</div>'
                        html += '           </div>'
                        html += '           </div>'
                        //安装视频
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        let installVideoImg1Url = row.installVideoImg1Url==null?'':row.installVideoImg1Url
                        let installVideo1Url = row.installVideo1Url==null?'':row.installVideo1Url
                        html += '                   <img class="user-img '+installCheckId+'_data" ' +
                                                    'data-video-download="'+installVideo1Url+'" data-name="2_video_1" src="'+installVideoImg1Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        let installVideoImg2Url = row.installVideoImg2Url==null?'':row.installVideoImg2Url
                        let installVideo2Url = row.installVideo2Url==null?'':row.installVideo2Url
                        html += '                   <img class="user-img '+installCheckId+'_data" ' +
                                                    'data-video-download="'+installVideo2Url+'" data-name="2_video_2" src="'+installVideoImg2Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        let installVideoImg3Url = row.installVideoImg3Url==null?'':row.installVideoImg3Url
                        let installVideo3Url = row.installVideo3Url==null?'':row.installVideo3Url
                        html += '                   <img class="user-img '+installCheckId+'_data" ' +
                                                    'data-video-download="'+installVideo3Url+'" data-name="2_video_3" src="'+installVideoImg3Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        let installVideoImg4Url = row.installVideoImg4Url==null?'':row.installVideoImg4Url
                        let installVideo4Url = row.installVideo4Url==null?'':row.installVideo4Url
                        html += '                   <img class="user-img '+installCheckId+'_data"' +
                                                    'data-video-download="'+installVideo4Url+'" data-name="2_video_4" src="'+installVideoImg4Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'

                        html += '       </div>'
                        html += '       </div>'
                        html += '   </div>'
                        html += '   <div class="col-3">'
                        html += '       <div class="row row-border">'
                        html += '       <div class="col-12">'
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '           <input type="checkbox" id="'+displayCheckId+'"><label for="'+displayCheckId+'">3.展示</label>'
                        html += '           </div>'
                        html += '           </div>'
                        //展示图片
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg1Url = row.displayImg1Url==null?'':row.displayImg1Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg1Url+'" data-name="3_img_1"  src="'+displayImg1Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg2Url = row.displayImg2Url==null?'':row.displayImg2Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg2Url+'" data-name="3_img_2" src="'+displayImg2Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg3Url = row.displayImg3Url==null?'':row.displayImg3Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg3Url+'" data-name="3_img_3" src="'+displayImg3Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg4Url = row.displayImg4Url==null?'':row.displayImg4Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg4Url+'" data-name="3_img_4" src="'+displayImg4Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg5Url = row.displayImg5Url==null?'':row.displayImg5Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg5Url+'" data-name="3_img_5" src="'+displayImg5Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayImg6Url = row.displayImg6Url==null?'':row.displayImg6Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-img-download="'+displayImg6Url+'" data-name="3_img_6" src="'+displayImg6Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'
                        //分割线
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '               <div class="line">&nbsp</div>'
                        html += '           </div>'
                        html += '           </div>'
                        //展示视频
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg1Url = row.displayVideoImg1Url==null?'':row.displayVideoImg1Url
                        let displayVideo1Url = row.displayVideo1Url==null?'':row.displayVideo1Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-video-download="'+displayVideo1Url+'" data-name="3_video_1" src="'+displayVideoImg1Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg2Url = row.displayVideoImg2Url==null?'':row.displayVideoImg2Url
                        let displayVideo2Url = row.displayVideo2Url==null?'':row.displayVideo2Url
                        html += '                   <img class="user-img '+displayCheckId+'_data" ' +
                                                    'data-video-download="'+displayVideo2Url+'" data-name="3_video_2" src="'+displayVideoImg2Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg3Url = row.displayVideoImg3Url==null?'':row.displayVideoImg3Url
                        let displayVideo3Url = row.displayVideo3Url==null?'':row.displayVideo3Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-video-download="'+displayVideo3Url+'" data-name="3_video_3" src="'+displayVideoImg3Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg4Url = row.displayVideoImg4Url==null?'':row.displayVideoImg4Url
                        let displayVideo4Url = row.displayVideo4Url==null?'':row.displayVideo4Url
                        html += '                   <img class="user-img '+displayCheckId+'_data"' +
                                                    'data-video-download="'+displayVideo4Url+'" data-name="3_video_4" src="'+displayVideoImg4Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg5Url = row.displayVideoImg5Url==null?'':row.displayVideoImg5Url
                        let displayVideo5Url = row.displayVideo5Url==null?'':row.displayVideo5Url
                        html += '                   <img class="user-img '+displayCheckId+'_data" ' +
                                                    'data-video-download="'+displayVideo5Url+'" data-name="3_video_5" src="'+displayVideoImg5Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let displayVideoImg6Url = row.displayVideoImg6Url==null?'':row.displayVideoImg6Url
                        let displayVideo6Url = row.displayVideo6Url==null?'':row.displayVideo6Url
                        html += '                   <img class="user-img '+displayCheckId+'_data" ' +
                                                    'data-video-download="'+displayVideo6Url+'" data-name="3_video_6" src="'+displayVideoImg6Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'

                        html += '       </div>'
                        html += '       </div>'
                        html += '   </div>'
                        html += '   <div class="col-3">'
                        html += '       <div class="row row-border" style="margin-right: 5px">'
                        html += '       <div class="col-12">'
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '           <input type="checkbox" id="'+disposeCheckId+'"><label for="'+disposeCheckId+'">4.布置</label>'
                        html += '           </div>'
                        html += '           </div>'
                        //布置图片
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-6 little-pd">'
                        html += '                   </div>'
                        html += '                   <div class="col-6 little-pd">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'
                        //分割线
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '               <div class="line">&nbsp</div>'
                        html += '           </div>'
                        html += '           </div>'
                        //布置视频
                        html += '           <div class="row row-fixed-h">'
                        html += '               <div class="col-12 little-pd">'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg1Url = row.disposeVideoImg1Url==null?'':row.disposeVideoImg1Url
                        let disposeVideo1Url = row.disposeVideo1Url==null?'':row.disposeVideo1Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo1Url+'" data-name="4_video_1" src="'+disposeVideoImg1Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg2Url = row.disposeVideoImg2Url==null?'':row.disposeVideoImg2Url
                        let disposeVideo2Url = row.disposeVideo2Url==null?'':row.disposeVideo2Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo2Url+'" data-name="4_video_2" src="'+disposeVideoImg2Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg3Url = row.disposeVideoImg3Url==null?'':row.disposeVideoImg3Url
                        let disposeVideo3Url = row.disposeVideo3Url==null?'':row.disposeVideo3Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo3Url+'" data-name="4_video_3" src="'+disposeVideoImg3Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '                   <div class="row row-no-margin row-fixed-sub-h">'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg4Url = row.disposeVideoImg4Url==null?'':row.disposeVideoImg4Url
                        let disposeVideo4Url = row.disposeVideo4Url==null?'':row.disposeVideo4Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo4Url+'" data-name="4_video_4" src="'+disposeVideoImg4Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg5Url = row.disposeVideoImg5Url==null?'':row.disposeVideoImg5Url
                        let disposeVideo5Url = row.disposeVideo5Url==null?'':row.disposeVideo5Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo5Url+'" data-name="4_video_5" src="'+disposeVideoImg5Url+'">'
                        html += '                   </div>'
                        html += '                   <div class="col-4 little-pd">'
                        let disposeVideoImg6Url = row.disposeVideoImg6Url==null?'':row.disposeVideoImg6Url
                        let disposeVideo6Url = row.disposeVideo6Url==null?'':row.disposeVideo6Url
                        html += '                   <img class="user-img '+disposeCheckId+'_data" ' +
                                                    'data-video-download="'+disposeVideo6Url+'" data-name="4_video_6" src="'+disposeVideoImg6Url+'">'
                        html += '                   </div>'
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'


                        html += '       </div>'
                        html += '       </div>'
                        html += '   </div>'
                        html += '   </div>'
                        html += '</div>'
                        html += '<div class="col-2" style="padding-left: 1px;padding-right: 1px">'
                        html += '       <div class="row row-border row-border-first" style="margin-right: 5px;margin-left: 5px">'
                        html += '       <div class="col-12">'
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        html += '           <input type="checkbox" class="admin-video-checkbox" id="'+adminUploadCheckId+'"><label for="'+adminUploadCheckId+'">爆款视频</label>'
                        html += '           </div>'
                        html += '           </div>'
                        //爆款视频
                        html += '           <div class="row">'
                        html += '               <div class="col-12 little-pd row-fixed-h2">'
                        html += '                   <div class="row row-no-margin">'
                        if (row.adminUploadVideoVoList == undefined) {
                            row.adminUploadVideoVoList = []
                        }
                        let adminUploadIndex = 0
                        for (let adminUploadVideoVo of row.adminUploadVideoVoList) {
                            adminUploadIndex++
                            html += '                   <div class="col-4 little-pd row-fixed-sub-h">'
                            let imgUrl = adminUploadVideoVo.imgUrl==null?'':adminUploadVideoVo.imgUrl
                            let videoUrl = adminUploadVideoVo.videoUrl==null?'':adminUploadVideoVo.videoUrl
                            html += '                   <img class="user-img '+adminUploadCheckId+'_data"' +
                                                        'data-video-download="'+videoUrl+'" data-name="5_video_'+adminUploadIndex+'" src="'+imgUrl+'">'
                            html += '                   </div>'
                        }
                        html += '                   </div>'
                        html += '               </div>'
                        html += '           </div>'
                        //底部按钮
                        html += '           <div class="row">'
                        html += '           <div class="col-12">'
                        // html += '           <div class="" style="position: absolute; bottom:1rem">'
                        html += '           <div class="btn-group-sm" style="height: 2rem">'
                        // html += '           <button type="button" class="btn btn-admin btn-yellow" onclick="uploadAdmin(this)">上传</button>'
                        html += '           <label class="btn btn-admin btn-yellow" for="uploadFile_'+value.wechatCode+'" style="margin: 0" >上传</label>'
                                +'          <input type="file" id="uploadFile_'+value.wechatCode+'" data-wechat="'+value.wechatCode+'" accept="video/*" multiple class="no-display" onchange="uploadAdmin(this)">'
                        html += '           <button type="button" class="btn btn-admin btn-red" data-wechat="'+value.wechatCode+'" onclick="deleteAdmin(this)">全部删除</button>'
                        html += '           </div>'
                        html += '           </div>'
                        html += '           </div>'

                        html += '       </div>'
                        html += '       </div>'

                        html += '</div>'
                        html += '</div>'
                        return html
                    }
                }]
            });
        })

        function search() {
            $("#tb").bootstrapTable('refresh', {pageNumber: 1})
        }

        function wechatCodeCheck(checkBoxEle) {
            let checkBoxJq = $(checkBoxEle)
            let wechatCode = checkBoxJq.data('wechat-code')
            let beforeCheckId = '#before_'+wechatCode
            let installCheckId = '#install_'+wechatCode
            let displayCheckId = '#display_'+wechatCode
            let disposeCheckId = '#dispose_'+wechatCode
            let checked = checkBoxJq.prop('checked')
            if (checked) {
                $(beforeCheckId).prop('checked', true)
                $(installCheckId).prop('checked', true)
                $(displayCheckId).prop('checked', true)
                $(disposeCheckId).prop('checked', true)
            } else {
                $(beforeCheckId).prop('checked', false)
                $(installCheckId).prop('checked', false)
                $(displayCheckId).prop('checked', false)
                $(disposeCheckId).prop('checked', false)
            }
        }

        function batchDownload() {
            $(".row-border input[type='checkbox']:checked").each(function (index, e) {
                let eJq = $(e)
                let checkId = eJq.prop("id")
                $("."+checkId+"_data").each(function(imgIndex, imgEle){
                    let imgJq = $(imgEle)
                    let videoDownload = imgJq.data("video-download")
                    let imgDownload = imgJq.data("img-download")
                    let fileName = imgJq.data("name")
                    let downloadUrl = videoDownload
                    if (downloadUrl === undefined || downloadUrl === '') {
                        downloadUrl = imgDownload
                    }
                    if (downloadUrl === undefined || downloadUrl === '') {
                        return
                    }
                    let fileExt = downloadUrl.substring(downloadUrl.lastIndexOf("."));
                    // console.log("fileExt", fileExt, "downloadUrl", downloadUrl)
                    /*const response = {
                        "content-disposition": "attachment; filename="+(fileName + fileExt),
                    };*/
                    /*downloadUrl = downloadUrl.replaceAll('https://cat-no-roll.oss-cn-hangzhou.aliyuncs.com','')
                    console.log('downloadUrl', downloadUrl)
                    const url = client.signatureUrl(downloadUrl, { response });*/
                    // console.log(url);
                    download(downloadUrl, fileName + fileExt, 'application/octet-stream')
                    // downloadFileReq(downloadUrl, fileName + fileExt)
                })
            })
        }

        function batchDelete() {
            let checkIdArray = []
            $(".row-border input[type='checkbox']:checked").each(function (index, e) {
                let eJq = $(e)
                let checkId = eJq.prop("id")
                console.log('checkId', checkId)
                checkIdArray.push(checkId)
            })
            if (!confirm('确认删除吗？')) {
                return false;
            }
            loading()
            var formData = new FormData();
            formData.append("checkIdList", checkIdArray);
            $.ajax({
                url: "/api/upload/admin/video/batch/delete",
                type: 'post',
                timeout:3*1000*1000,
                data:formData,
                contentType: false,
                processData: false,
                error:function (res) {
                    console.log("batch delete video fail", res)
                    $("#tb").bootstrapTable('refresh', {pageNumber: 1})
                    unloading()
                },
                success: function (res) {
                    console.log("batch delete video success", res)
                    $("#tb").bootstrapTable('refresh', {pageNumber: 1})
                    unloading()
                }
            })
        }

        function loading() {
            console.log('loading')
            $.fakeLoader({
                timeToHide:15*60*1000,
                bgColor:"#f89a10",
                spinner:"spinner3"
            });
            $(".fakeLoader").show();
        }

        function unloading() {
            console.log('unloading')
            $(".fakeLoader").fadeOut();
        }

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

        async function put(file, wechatCode, fileName, fileIndex, fileLength) {
            try {
                const name = wechatCode + "/" + fileName;
                // 获取DOM。
                const options = {
                    // 获取分片上传进度、断点和返回值。
                    progress: (p, cpt, res) => {
                        console.log(p);
                        let percentStr = Number(p * 100).toFixed(1) + "%"
                        $(".fl-text").text('正在上传'+(fileIndex+1)+'/'+fileLength+' 进度 '+percentStr)
                    },
                    // 设置并发上传的分片数量。
                    parallel: 4,
                    // 设置分片大小。默认值为1 MB，最小值为100 KB。
                    partSize: 1024 * 1024,
                    // headers,
                    // 自定义元数据，通过HeadObject接口可以获取Object的元数据。
                    // meta: { year: 2020, people: "test" },
                    // mime: "text/plain",
                };

                const res = await client.multipartUpload(name, file, {
                    ...options,
                });
                console.log(res);


            } catch (e) {
                console.error('error: %j', e);
            }
        }

        async function uploadAdmin(inputFile) {
            let inputFileJq = $(inputFile)
            let wechatCode = inputFileJq.data('wechat')
            var files = inputFileJq[0].files;
            if (files.length === 0) {
                return;
            }
            loading()
            let fileUrlArray = []
            let fileNameArray = []
            for (let i = 0; i < files.length; i++) {
                let fileName = files[i].name;
                let fileExt = fileName.substring(fileName.lastIndexOf("."));
                let newFileName = uuidC() + fileExt
                await put(files[i], wechatCode, newFileName, i, files.length)
                let newFileKey = wechatCode + "/" + newFileName
                fileUrlArray.push(newFileKey)
                fileNameArray.push(newFileName)
            }
            var formData = new FormData();
            formData.append("fileUrlList", fileUrlArray);
            formData.append("fileNameList", fileNameArray);
            $.ajax({
                url: "/api/upload/admin/sso/video?wechatCode="+wechatCode,
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
                    $("#tb").bootstrapTable('refresh')
                }
            })
        }

        function deleteAdmin(btn) {
            let btnJq = $(btn)
            let wechatCode = btnJq.data('wechat');
            if (!confirm('确认删除吗？')) {
                return false;
            }
            loading()
            $.ajax({
                url: "/api/upload/admin/video/delete?wechatCode="+wechatCode,
                type: 'post',
                timeout:3*1000*1000,
                contentType: false,
                processData: false,
                error:function (res) {
                    unloading()
                    console.log("deleteAdmin video fail", res)
                },
                success: function (res) {
                    unloading()
                    console.log("deleteAdmin video success", res)
                    $("#tb").bootstrapTable('refresh')
                }
            })
        }
    </script>
</body>
</html>