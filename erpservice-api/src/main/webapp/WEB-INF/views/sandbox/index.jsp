<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2015. All rights reserved.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: liuqucheng
  Date: 12/10/15
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>伙伴商城erp数据服务中心接口测试（沙盒)</title>
    <link href="<c:url value="/resource/css/admin.global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.content.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.mall.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resource/scripts/lib/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resource/scripts/lib/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/scripts/lib/jBox/Skins/Green/jbox.css"/>">
    <script src="<c:url value="/resource/scripts/lib/jquery.utils.js" />" type="text/javascript"></script>
    <style type="text/css">
        .spModuleTitle {
            padding: 3px 10px 0px 10px;
            font-size: 16px;
            font-weight: bold;
            font-family: 微软雅黑;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $("#apiType").change(function () {
                $("#eventType").html('<option value="-1">请选择</option>');
                $("#param_panel").empty();
                if ($(this).val() == 0) {
                    $("#eventType").append($("#order_op_template").html());
                }
            });
            $("#eventType").change(function () {
                $("#param_panel").empty();
                var eventType = $(this).val();
                switch (eventType) {
                    case "hbpOrderList":
                        $("#param_panel").html($("#orderList_param_template").html());
                        break;
                }
            });
        });
    </script>
    <script type="text/html" id="order_op_template">
        <option value="hbpOrderList">获取订单详情</option>
    </script>
</head>
<body>
<div class="container">
    <div class="blank10">
    </div>
    <div class="block">
        <div class="h" style="text-align: center;">

            <h3>伙伴商城ERP数据服务中心接口测试（沙盒)</h3>
        </div>
        <div class="cnt-wp" style="height: 600px;">
            <div class="cnt form" style="height: 600px;">
                <div style="float: left; width: 50%;">
                    <span class="spModuleTitle">系统参数</span>

                    <div class="division" style="width: 100%">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <th style="vertical-align: middle;">接入码（appKey）：</th>
                                <td>
                                    <input name="appKey" type="text" id="appKey" class="input-normal">
                                </td>
                            </tr>
                            <tr>
                                <th style="vertical-align: middle;">Token：</th>
                                <td>
                                    <input name="token" type="text" id="token" class="input-big">
                                </td>
                            </tr>
                            <tr>
                                <th style="vertical-align: middle;">接口类型：</th>
                                <td>
                                    <select id="apiType">
                                        <option value="-1">请选择</option>
                                        <option value="0">订单接口</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th style="vertical-align: middle;">接口类型：</th>
                                <td>
                                    <select id="eventType">
                                        <option value="none">请选择</option>
                                    </select>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <span class="spModuleTitle">接口参数</span>

                    <div id="param_panel">

                    </div>

                    <div style="width: 100%; text-align: center;">
                        <input type="button" value="提交"/>
                    </div>
                </div>

                <div style="float: right;width: 48%;">
                    <span class="spModuleTitle">请求参数:</span>

                    <div class="division" style="width: 95%">
                        <textarea style="width: 99%;height: 120px;" readonly="readonly">

                        </textarea>
                    </div>

                    <span class="spModuleTitle">返回:</span>
                    <div class="division" style="width: 95%">
                        <textarea style="width: 99%;height: 200px;" readonly="readonly">

                        </textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@include file="/apiParam/order_list_param.jsp" %>
