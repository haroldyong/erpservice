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
  User: allan
  Date: 4/13/16
  Time: 10:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        订单同步记录
    </title>
    <link href="<c:url value="/resource/css/admin.global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.content.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.mall.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resource/scripts/lib/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resource/scripts/lib/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/scripts/lib/jBox/Skins/Green/jbox.css"/>">
    <script src="<c:url value="/resource/scripts/lib/jquery.utils.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resource/scripts/admin.js" />"></script>
    <script src="<c:url value="/resource/scripts/lib/My97DatePicker/WdatePicker.js" />" type="text/javascript"></script>
    <style type="text/css">
        .spModuleTitle {
            padding: 3px 10px 0px 10px;
            font-size: 16px;
            font-weight: bold;
            font-family: 微软雅黑;
        }
    </style>
    <script type="text/javascript">
        var ajaxUrl = "<c:url value="/erpService/platform/rePushOrder" />";
        function rePush(id, userType) {
            J.jboxConfirm("确定要推送吗?", function () {
                $.jBox.tip("正在推送", "loading");
                J.GetJsonRespons(ajaxUrl, {
                    id: id,
                    erpUserType: userType
                }, function (json) {
                    if (json.resultCode == 2000) {
                        $.jBox.tip("推送成功", "success");
                    } else {
                        $.jBox.tip("推送失败", "error");
                        window.location.reload();
                    }
                }, function () {
                }, J.PostMethod)
            });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="blank10">
    </div>

    <form method="get" id="searchForm">
        <div class="search block" style="display: block;">
            <div class="h">
                <span class="icon-sprite icon-magnifier"></span>

                <h3>订单同步记录</h3>
            </div>
            <div class="tl corner">
            </div>
            <div class="tr corner">
            </div>
            <div class="bl corner">
            </div>
            <div class="br corner">
            </div>
            <div class="cnt-wp" style="padding: 35px 10px 10px;">
                <div class="cnt">
                    <div class="search-bar">
                        <div>
                            <input type="hidden" name="erpUserType" value="${orderSyncSearch.erpUserType}"/>
                            <label class="first ">起始时间：</label>
                            <input name="beginTime" type="text" id="txtCreateBeginTime" placeholder=" [开始时间]"
                                   class="input-normal Wdate"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"
                                   style="margin-left: 8px;" value="${orderSyncSearch.beginTime}"/>
                            <label class="first ">---</label>
                            <input name="endTime" type="text" id="txtCreateEndTime" placeholder=" [结束时间]"
                                   class="input-normal Wdate" value="${orderSyncSearch.endTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'txtCreateBeginTime\')}'})"/>
                            <label>
                                <a class="btn-lit btn-middle" href="javascript:$('#searchForm').submit();"
                                   style="margin-bottom: 3px;">
                                    <span>查询</span>
                                </a>
                                <a class="btn-lit btn-middle"
                                   href="<c:url value="/erpService/platform/orderPushLogs?erpUserType=${orderPushSearch.erpUserType}" />"
                                   style="margin-bottom: 3px;">
                                    <span>显示全部</span>
                                </a>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="blank10">
        </div>
        <div class="block">
            <div class="tl corner">
            </div>
            <div class="tr corner">
            </div>
            <div class="bl corner">
            </div>
            <div class="br corner">
            </div>
            <div class="cnt-wp" style="padding: 10px 10px 10px;display: block;">

                <div class="cnt">
                    <table class="data-table even1" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <thead>
                        <tr class="even">
                            <th scope="col">订单号
                            </th>
                            <th scope="col">ERP
                            </th>
                            <th scope="col">同步状态
                            </th>
                            <th scope="col">订单状态
                            </th>
                            <th scope="col">支付状态
                            </th>
                            <th scope="col">发货状态
                            </th>
                            <th scope="col">ERP平台订单状态
                            </th>
                            <th scope="col">添加时间
                            </th>
                            <th scope="col">最后一次同步时间
                            </th>
                            <th scope="col">备注
                            </th>
                            <th scope="col">操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="orderSync" items="${orderSyncs.getContent()}">
                            <tr>
                                <td class="txt40 c">${orderSync.orderId}</td>
                                <td class="txt40 c">${orderSync.providerType.name}</td>
                                <td class="txt40 c">${orderSync.orderSyncStatus.name}</td>
                                <td class="txt40 c">${orderSync.orderStatus.name}</td>
                                <td class="txt40 c">${orderSync.payStatus.name}</td>
                                <td class="txt40 c">${orderSync.shipStatus.name}</td>
                                <td class="txt40 c">${orderSync.outPayStatus}${orderSync.outShipStatus}</td>
                                <td class="txt40 c"><fmt:formatDate value="${orderSync.createTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt40 c"><fmt:formatDate value="${orderSync.lastSyncDate}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt40 c">${orderSync.remark}</td>
                                <td class="txt80 c">
                                    <c:if test="${log.resultStatus==0}">
                                        <a href="javascript:rePush(${log.id},${log.userType.code})">重新推送</a> |
                                    </c:if>
                                    <a href="<c:url value="/erpService/platform/orderOperatorLogs?erpUserType=${orderSyncSearch.erpUserType}&orderId=${orderSync.orderId}" />">查看日志</a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>
                </div>

                <script type="text/javascript">
                    var pageSize = ${pageSize};
                    var pageIndex = ${pageIndex};
                    var pageCount = ${orderSyncs.getTotalPages()};
                    var recordCount = ${orderSyncs.getTotalElements()};
                    var formName = 'searchForm';
                    Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
                </script>

            </div>
        </div>
    </form>
</div>
</body>
</html>
