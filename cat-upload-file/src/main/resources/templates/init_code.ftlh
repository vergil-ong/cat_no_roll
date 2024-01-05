<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>猫不卷</title>
    <script src='/static/js/jquery.js'></script>
    <script src='/static/js/bootstrap.bundle.js'></script>
    <link rel="stylesheet" href='/static/css/bootstrap.css'>

    <script src="/static/js/driver.min.js"></script>
    <link rel="stylesheet" href="/static/css/driver.min.css"/>

    <style>
        html {
            font-size: 62.5%;
        }

        body {
            font-size: 14px;
            font-size: 2rem;
        }

        input.form-control {
            height: 4rem;
            font-size: 1.3rem;
        }

        .top-box {
            background-image: linear-gradient(#ed8c07, #eed3ae);
            color: white;
            height: 26rem;
            font-weight: bold;
        }

        .center-box {
            padding: 1rem;
            border-radius: 1rem;
            position: relative;
            top: -18rem;
            -webkit-filter: drop-shadow(0 0 0.4rem #f1ad51);
            filter: drop-shadow(0 0 0.4rem #fbb75a);
        }

        .shadow {
            box-shadow: 0 0 10px 0 #fbb75a;
            border-radius: 25px;
            background-color: white;
            position: relative;
            top: -130px;
        }

        [class*="driver-close-btn"] {
            visibility: hidden;
        }


    </style>
</head>
<body>
<div class="container-fluid top-box">
    <div style="padding-top:1.2rem "> Hello</div>
    <div>猫不卷 不卷不卷</div>
</div>

<div class="container center-box">
    <div class="card" style="height: 26rem;border-radius: 1rem;">
        <div class="card-body" style=" border-radius: 1rem;">
            <h3 class="card-title" style="font-size:1.5rem"></h3>
            <form>
                <div class="form-group">
                    <label for="orderCode" style="font-size: 1.6rem;margin-left: 1rem;">输入猫不卷订单号</label>
                    <input onchange="valOrder(this)" id="orderCode" type="text" placeholder="推荐进入猫不卷订单页复制"
                           class="form-control">
                    <span id="orderErr" class="text-danger"
                          style="display: none; font-size: 1.6rem;margin-top:1rem; float: left;margin-left: 1rem">
                       请输入订单号
                    </span>

                    <!--<small id="emailHelp" class="form-text text-muted" style="margin-top:1rem; ">
                        <a href="#" style="font-size: 1.6rem;float: right">不知道怎么复制订单号？</a>
                    </small>-->
                </div>
                <div style="text-align: center" id="uploadBtnDiv">
                    <a onclick="submitOrder(this)" class="btn-lg btn-info btn-block"
                       style="background-color: #9a5c07; font-size:2rem;border-radius: 0.8rem;margin-top: 10rem ">
                        点击开始上传
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal " id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="false" >
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 15px;">
            <div class="modal-body">
                <div style="text-align: center;font-weight: bold" class="text-info">**温馨提醒**</div>
                <div style="font-size:16px;line-height: 200% ">
                    请卓总讲话
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="upbut btn btn-primary" onclick="closeModal()"
                        style="font-size:18px;border-radius: 0.5rem;margin-right: 10px;">
                    好的，我已了解
                </button>
            </div>
        </div>
    </div>
</div>

<script>

    function centerModals() {
        $('#staticBackdrop').each(function(i) {
            var $clone = $(this).clone().css('display','block').appendTo('body');
            var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
            top = top > 0 ? top : 0;
            $clone.remove();
            $(this).find('.modal-content').css("margin-top", top-50);
        });
    };

    $('#staticBackdrop').on('show.bs.modal', function(e){
        centerModals();
    });


    // $('#staticBackdrop').modal("show");
    function closeModal() {
        $('#staticBackdrop').modal("hide");
    }

    function valOrder(obj) {
        let orderCode = $("#orderCode").val();
        if (orderCode) {
            $("#orderErr").hide();
        } else {
            $("#orderErr").show();
        }
    }

    function submitOrder(obj) {
        let orderCode = $("#orderCode").val();
        if (!orderCode) {
            $("#orderErr").show();
            return;
        }

        $(obj).attr("disabled", true);
        /*$.post("/mgp/photo/userGenerateOrder?code=" + orderCode, function (res) {
            console.info(res);
            if (res.success) {
                window.location.href = "/view/upload/file?code=" + res.code;
            }
        })*/
        window.location.href = "/view/upload/file?code=" + orderCode;
    }
    $(function () {
        /*const driver = new Driver({
            showProgress: true,
            steps: [
                { element: '.top-box', popover: { title: 'Animated Tour Example', description: 'Here is the code example showing animated tour. Let\'s walk you through it.', side: "left", align: 'start' }},
                // { element: 'code .line:nth-child(1)', popover: { title: 'Import the Library', description: 'It works the same in vanilla JavaScript as well as frameworks.', side: "bottom", align: 'start' }},
                // { element: 'code .line:nth-child(2)', popover: { title: 'Importing CSS', description: 'Import the CSS which gives you the default styling for popover and overlay.', side: "bottom", align: 'start' }},
                // { element: 'code .line:nth-child(4) span:nth-child(7)', popover: { title: 'Create Driver', description: 'Simply call the driver function to create a driver.js instance', side: "left", align: 'start' }},
                // { element: 'code .line:nth-child(18)', popover: { title: 'Start Tour', description: 'Call the drive method to start the tour and your tour will be started.', side: "top", align: 'start' }},
                // { element: 'a[href="/docs/configuration"]', popover: { title: 'More Configuration', description: 'Look at this page for all the configuration options you can pass.', side: "right", align: 'start' }},
                { popover: { title: 'Happy Coding', description: 'And that is all, go ahead and start adding tours to your applications.' } }
            ]
        });*/
        const driver = new Driver({
            allowClose: false,
            nextBtnText: '下一步',
            prevBtnText: '上一步',
            closeBtnText: '',
            doneBtnText: '好了,干吧'
        });
        driver.defineSteps([
            {
                element: '.top-box',
                popover: {
                    title: '卓总引导着你',
                    description: '这里是品牌',
                }
            },
            {
                element: '#orderCode',
                popover: {
                    title: '卓总引导着你',
                    description: '在这里输入订单',
                    position: 'top',
                }
            },
            {
                element: '#uploadBtnDiv',
                popover: {
                    title: '卓总引导着你',
                    description: '点这里上传图片吧',
                    position: 'bottom',
                    // allowClose: true,
                }
            },
        ]);
        driver.start()
    })
</script>
</body>
</html>