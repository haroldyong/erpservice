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
        订单发货同步日志
    </title>
    <link href="<c:url value="/resource/css/admin.global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.content.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.mall.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resource/scripts/lib/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resource/scripts/lib/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/scripts/lib/jBox/Skins/Green/jbox.css"/>">
    <script src="<c:url value="/resource/scripts/lib/jquery.utils.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resource/scripts/admin.js" />"></script>

    <script type="text/javascript">
        var erpUserType = ${erpUserType};
        var ajaxUrl = "<c:url value="/erpService/platform/reSyncChannelOrder" />";
        function reSyncShip(id) {
            J.jboxConfirm("确定要同步吗?", function () {
                $.jBox.tip("正在同步", "loading");
                J.GetJsonRespons(ajaxUrl, {
                    id: id,
                    erpUserType: erpUserType
                }, function (json) {
                    if (json.resultCode == 2000) {
                        $.jBox.tip("推送成功", "success");
                        window.location.reload();
                    } else {
                        $.jBox.tip("推送失败", "error");
                    }
                }, function () {
                }, J.PostMethod)
            });
        }

        var syncUrl = "<c:url value="/erpService/platform/resyncAllChannelOrder" />"
        function syncAll(logSyncId) {
            J.jboxConfirm("确定一键同步所有订单吗?", function () {
                $.jBox.tip("正在同步", "loading");
                J.GetJsonRespons(syncUrl, {
                    logSyncId: logSyncId,
                    erpUserType: erpUserType
                }, function (json) {
                    if (json.resultCode == 2000) {
                        $.jBox.tip("同步成功", json.resultMsg);
                        window.location.reload();
                    } else {
                        $.jBox.tip("同步失败", "error");
                    }
                })
            })
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

                <h3>渠道订单信息同步记录</h3>

                <p style="float: right;margin-top: -26px;margin-right: 10px;">
                    <a class="btn-lit btn-middle"
                       href="<c:url value="/erpService/platform/channelOrderSyncs?erpUserType=0" />"
                       style="margin-bottom: 3px;">
                        <span>返回</span>
                    </a>
                </p>

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
                            <input type="hidden" name="erpUserType" value="${erpUserType}"/>
                            <input type="hidden" name="logSyncId" value="${logSyncId}"/>

                            <label class="first ">订单编号：</label>
                            <input name="orderId" type="text"
                                   class="input-normal" value="${orderId}"/>

                            <label>
                                <a class="btn-lit btn-middle" href="javascript:$('#searchForm').submit();"
                                   style="margin-bottom: 3px;">
                                    <span>查询</span>
                                </a>
                                <a class="btn-lit btn-middle"
                                   href="<c:url value="/erpService/platform/channelOrderSyncList?erpUserType=${erpUserType}&logSyncId=${logSyncId}" />"
                                   style="margin-bottom: 3px;">
                                    <span>显示全部</span>
                                </a>
                                <a class="btn-middle"
                                   href="javascript:syncAll(${logSyncId})"
                                   style="margin-bottom: 3px; background-color:#00B738; color:#fff; font-size:14px; border-radius:4px; padding:4px 8px;">
                                    <span>一键同步</span>
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
                            <th scope="col">订单编号
                            </th>

                            <th scope="col">同步状态
                            </th>
                            <th scope="col">备注
                            </th>
                            <th scope="col">操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="log" items="${channelOrderSyncInfoList.getContent()}">
                            <tr>
                                <td class="txt40 c">${log.orderId}</td>
                                <td class="txt40 c">${log.channelOrderSyncStatus.name}</td>
                                <td class="txt40 c">${log.remark}</td>
                                <td class="txt80 c">
                                    <c:if test="${log.channelOrderSyncStatus.code!=0}">
                                        <a href="javascript:reSyncShip(${log.id})">重新同步</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>
                </div>

                <script type="text/javascript">
                    var pageSize = ${pageSize};
                    var pageIndex = ${pageIndex};
                    var pageCount = ${channelOrderSyncInfoList.getTotalPages()};
                    var recordCount = ${channelOrderSyncInfoList.getTotalElements()};
                    var formName = 'searchForm';
                    Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
                </script>

            </div>
        </div>
    </form>
</div>
<script>

</script>
</body>
</html>
