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
        var configHandler = {
            setOpenStatus: function () {
                
            }
        }
    </script>
</head>
<body>
<form method="post" id="form1">
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

                        <div class="division">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <th>是否开通ERP数据服务：</th>
                                    <td>
                                        <c:if test="${baseConfig==null || baseConfig.isOpen==0}">
                                            <input type="button" value="开启" style="cursor: pointer;"/>
                                        </c:if>
                                        <c:if test="${baseConfig!=null || baseConfig.isOpen==1}">
                                            <input type="button" value="关闭" style="cursor: pointer;"/>
                                        </c:if>

                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <c:if test="${baseConfig!=null || baseConfig.isOpen==1}">
                            <div class="division">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tbody>
                                    <tr>
                                        <th style="vertical-align: middle;">接入码（appKey）：</th>
                                        <td>
                                            <input name="txtMallName" type="text" value="" id="appKey" class="input-normal" readonly="readonly">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle;">Token：</th>
                                        <td>
                                            <input name="txtMallName" type="text" value="" id="token" class="input-normal" readonly="readonly">
                                            <input type="button" value="重新生成" readonly="readonly"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle;">签名密钥（secretKey）：</th>
                                        <td>
                                            <input name="txtMallName" type="text" value="" id="secretKey" class="input-normal">（接口调用时用于安全校验，只允许数字跟字母的组合）
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                    </div>
                    <div>
                        <span class="spModuleTitle">ERP系统详细配置</span>

                        <div class="division">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <th>选择您使用的ERP平台：</th>
                                    <td>
                                        <select>
                                            <option value="-1">请选择</option>
                                            <option value="0">E店宝</option>
                                            <option value="1">网店管家</option>
                                        </select>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="division">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <th>选择您使用的ERP平台：</th>
                                    <td>
                                        <select>
                                            <option value="-1">请选择</option>
                                            <option value="0">E店宝</option>
                                            <option value="1">网店管家</option>
                                        </select>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="division">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <th style="vertical-align: middle;">请求地址：</th>
                                    <td>
                                        http:// <input name="txtMallName" type="text" value="" id="requestUrl" class="input-normal" readonly="readonly">
                                    </td>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle;">dbhost：</th>
                                    <td>
                                        <input name="txtMallName" type="text" value="" id="dbhost" class="input-normal" readonly="readonly">
                                        （软件注册用户，比如edb_aXXXXX（接口调用的唯一标识），用户的主帐号）
                                    </td>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle;">appKey：</th>
                                    <td>
                                        <input name="txtMallName" type="text" value="" id="edbAppKey" class="input-normal" readonly="readonly">
                                        （公钥，你申请的appkey， 以标识来源）
                                    </td>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle;">appSecret：</th>
                                    <td>
                                        <input name="txtMallName" type="text" value="" id="appSecret" class="input-normal" readonly="readonly">
                                        （可在edb开发者后台查看）
                                    </td>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle;">token：</th>
                                    <td>
                                        <input name="txtMallName" type="text" value="" id="edbToken" class="input-normal" readonly="readonly">
                                        （可在edb开发者后台查看）
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div style="text-align: center;">
                        <div style="display: none;">
                            <input type="submit" name="btnSave" value="" id="btnSave">
                        </div>
                        <a class="btn-lit" href="javascript:submitForm();"><span>保存</span></a>
                    </div>

                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
