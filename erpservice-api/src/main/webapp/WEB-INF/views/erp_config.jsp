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
  User: liual
  Date: 2015-10-28
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        商城基本设置
    </title>
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

        .tip {
            padding-left: 3px;
            color: #aeacac;
        }
    </style>
    <script type="text/javascript">
        var ajaxUrl = "/erpService/";
        var erpType = ${erpType};
        var configHandler = {
            setOpenStatus: function (isOpen) {
                var msg = isOpen == 1 ? "确定要打开erp数据服务？请仔细填写相关设置" : "确定要关闭？您将失去相关erp数据服务";
                J.jboxConfirm(msg, function () {
                    var tip = isOpen == 1 ? "打开" : "关闭"
                    $.jBox.tip("正在" + tip + "...", "loading");
                    J.GetJsonRespons(ajaxUrl + "setOpenStatus", {erpUserType:${erpUserType}}, function (json) {
                        if (json.resultCode == 2000) {
                            $.jBox.tip("erp数据服务已" + tip, "success");
                            window.location.reload();
                        } else {
                            $.jBox.tip("操作失败，请重试");
                        }
                    }, function () {
                    }, J.PostMethod);
                })
            },
            rebuildToken: function () {
                J.GetJsonRespons(ajaxUrl + "getToken", null, function (json) {
                    if (json.resultCode == 2000) {
                        $("#token").val(json.data);
                    } else {
                        $.jBox.tip("重新生成失败，请重试");
                    }
                }, function () {
                }, J.PostMethod);
            },
            changeErpType: function () {
                var erpType = $("#erpType").val();
                switch (erpType) {
                    case "-1":
                        $("#erpDetailConfigDiv").hide();
                        break;
                    case "0":
                        $("#erpDetailConfigDiv").show();
                        $("#edbConfig").show();
                        $("#nsConfig").hide();
                        break;
                    case "1":
                        $("#erpDetailConfigDiv").show();
                        $("#nsConfig").show();
                        $("#edbConfig").hide();
                        break;
                }
            },
            getErpConfig: function (erpType) {
                switch (erpType) {
                    case "-1":
                        return "";
                    case "0":
                        return edbConfigHandler.getEdbConfig();
                    case "1":
                        return nsConfigHandler.getNSConfig();
                }
            },
            submitForm: function () {
                var secretKey = $.trim($("#secretKey").val());
                if (secretKey.length == 0) {
                    $.jBox.tip("请输入签名密钥");
                    return;
                }

                var erpType = $("#erpType").val();
                var sysDataJson = this.getErpConfig(erpType);
                if (sysDataJson == null) {
                    return;
                }
                $("#sysDataJson").val(sysDataJson);
                $.jBox.tip("正在保存...", "loading");
                $("#submitForm").submit();
            }
        };

        $(function () {
            var result = "${result}";
            if (result == "success") {
                $.jBox.tip("保存成功", "success");
            }
            if (result == "error") {
                $.jBox.tip("保存失败", "error");
            }
            <c:forEach items="${lstDetailConfig}" var="item">
            var erpSysData = ${item.erpSysData};
            switch (${item.erpType.getCode()}) {
                case 0:
                    edbConfigHandler.setEdbValues(erpSysData);
                    break;
                case 1:
                    nsConfigHandler.setNSValue(erpSysData);
            }
            </c:forEach>

            $("#erpType").val(erpType);
            configHandler.changeErpType();
            $("#erpType").change(function () {
                configHandler.changeErpType();
            });
            if (erpType == -1) {
                $("#currentErp").html("您还为选择任何erp系统");
            } else {
                $("#currentErp").html($("#erpType").find("option:selected").text());
            }
        })
    </script>
</head>
<body>
<form method="post" id="submitForm" action="<c:url value="/erpService/saveConfig" />">
    <div class="container">
        <div class="blank10">
        </div>
        <div class="block">
            <div class="h">
                <span class="icon-sprite icon-list"></span>

                <h3>ERP数据服务设置</h3>
            </div>
            <div class="cnt-wp">
                <div class="cnt form">

                    <div>
                        <span class="spModuleTitle">基本设置</span>
                        <input type="hidden" name="erpUserType" value="${erpUserType}"/>

                        <div class="division">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <th>是否开通ERP数据服务：</th>
                                    <td>
                                        <c:if test="${baseConfig==null || baseConfig.isOpen==0}">
                                            <input type="button" onclick="configHandler.setOpenStatus(1)" value="开启" style="cursor: pointer;"/>
                                        </c:if>
                                        <c:if test="${baseConfig!=null && baseConfig.isOpen==1}">
                                            <input type="button" value="关闭" onclick="configHandler.setOpenStatus(0)" style="cursor: pointer;"/>
                                        </c:if>

                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <c:if test="${baseConfig!=null && baseConfig.isOpen==1}">
                            <div class="division">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tbody>
                                    <tr>
                                        <th style="vertical-align: middle;">接入码（appKey）：</th>
                                        <td>
                                            <input name="appKey" type="text" value="${baseConfig.appKey}" id="appKey" class="input-normal" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle;">Token：</th>
                                        <td>
                                            <input name="token" type="text" value="${baseConfig.token}" id="token" class="input-big" readonly="readonly">
                                            <input type="button" style="cursor: pointer;" onclick="configHandler.rebuildToken()" value="重新生成"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle;">签名密钥（secretKey）：</th>
                                        <td>
                                            <input name="secretKey" type="text" value="${baseConfig.secretKey}" id="secretKey" class="input-normal">（接口调用时用于安全校验，只允许数字跟字母的组合）
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle;">正在使用：</th>
                                        <td id="currentErp">

                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                    </div>
                    <c:if test="${baseConfig!=null && baseConfig.isOpen == 1}">
                        <div>
                            <span class="spModuleTitle">ERP系统详细配置</span>

                            <div class="division">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tbody>
                                    <tr>
                                        <th>选择您使用的ERP平台：</th>
                                        <td>
                                            <select id="erpType" name="erpType">
                                                <option value="-1">请选择</option>
                                                <option value="0">E店宝</option>
                                                <option value="1">网店管家</option>
                                            </select>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="division" id="erpDetailConfigDiv">
                                <input name="sysDataJson" id="sysDataJson" type="hidden"/>

                                <div id="edbConfig">
                                    <%@include file="/detailConfig/edb_config.jsp" %>
                                </div>

                                <div id="nsConfig">
                                    <%@include file="/detailConfig/ns_config.jsp" %>
                                </div>
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <div style="display: none;">
                                <input type="submit" name="btnSave" value="" id="btnSave">
                            </div>
                            <a class="btn-lit" href="javascript:configHandler.submitForm();"><span>保存</span></a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>