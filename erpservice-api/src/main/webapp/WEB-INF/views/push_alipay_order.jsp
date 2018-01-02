<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2016. All rights reserved.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: liual
  Date: 2015-11-03
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        支付宝推送订单页面
    </title>
    <link href="http://resali.huobanplus.com/cdn/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/animate.min.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css"
          rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="http://resali.huobanplus.com/cdn/hotui/css/style.min-1.0.8.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/summernote/0.8.8/summernote.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>订单信息</h5>
        </div>
        <div class="ibox-content">
            <form class="form-horizontal" name="orderInfo" id="orderInfo" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">订单号</label>
                    <div class="col-sm-6">
                        <input type="text" id="orderId" name="orderId" class="form-control" value="" required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">支付成功后伙伴商城平台返回的支付单号</label>
                    <div class="col-sm-6">
                        <input type="text" id="payNumber" name="payNumber" class="form-control" value="" required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">线上支付金额</label>
                    <div class="col-sm-6">
                        <input type="text" id="onlinePayAmount" name="onlinePayAmount" class="form-control" value=""
                               required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">支付宝商户号</label>
                    <div class="col-sm-6">
                        <input type="text" id="aliPartner" name="aliPartner" class="form-control" value="" required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电商企业编码(电商企业在跨境平台备案编码)</label>
                    <div class="col-sm-6">
                        <input type="text" id="eCommerceCode" name="eCommerceCode" class="form-control" value=""
                               required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电商企业名称</label>
                    <div class="col-sm-6">
                        <input type="text" id="eCommerceName" name="eCommerceName" class="form-control" value=""
                               required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">支付宝key</label>
                    <div class="col-sm-6">
                        <input type="text" id="aliKey" name="aliKey" class="form-control" value="" required>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-5">
                        <button class="btn btn-success btn-submit" type="button" id="infoSubmit"
                                onclick="orderHandler.submit()">提交
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="http://resali.huobanplus.com/cdn/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resource/scripts/lib/bootstrap.hot.extra-utils.js" />" type="text/javascript"></script>
<script src="http://resali.huobanplus.com/cdn/bootstrap/3.3.7/bootstrap.min.js"></script>
<script src="http://resali.huobanplus.com/cdn/hotui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
<script src="http://resali.huobanplus.com/cdn/hotui/js/plugins/switchery/switchery.js"></script>
<script src="http://resali.huobanplus.com/cdn/layer/3.0.3/layer.js"></script>
<script src="http://resali.huobanplus.com/cdn/hotui/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="http://resali.huobanplus.com/cdn/jquery-validation/1.16.0/jquery.validate.min.js"></script>
<script src="http://resali.huobanplus.com/cdn/jquery-validation/1.15.0/additional-methods.min.js"></script>
<script src="http://resali.huobanplus.com/cdn/jquery-validation/1.15.0/localization/messages_zh.min.js"></script>
<script type="text/javascript">
    $(function () {
    });
    var orderHandler = {
        submit: function () {
            if(!$('#orderInfo').valid()){
                return;
            }
            var orderId = $("#orderId").val();
            var payNumber = $("#payNumber").val();
            var onlinePayAmount = $("#onlinePayAmount").val();
            var aliPartner = $("#aliPartner").val();
            var eCommerceCode = $("#eCommerceCode").val();
            var eCommerceName = $("#eCommerceName").val();
            var aliKey = $("#aliKey").val();
            var requestData = {
                orderId: orderId,
                payNumber: payNumber,
                onlinePayAmount: onlinePayAmount,
                aliPartner: aliPartner,
                eCommerceCode: eCommerceCode,
                eCommerceName: eCommerceName,
                aliKey: aliKey
            }
            console.log(requestData)
            hot.ajax("/erpService/platform/pushAlipayOrder", requestData, function (res) {
                if (res.resultCode == 2000) {
                    hot.tip.success("操作成功");
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }else{
                    hot.tip.error(res.resultMsg)
                }
            });
        }
    }
</script>
</body>
</html>
