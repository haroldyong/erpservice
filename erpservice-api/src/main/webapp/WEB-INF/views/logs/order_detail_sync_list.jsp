<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2016. All rights reserved.
  --%>

<jsp:useBean id="orderDetailSyncSearch" scope="request"
             type="com.huobanplus.erpservice.datacenter.searchbean.OrderDetailSyncSearch"/>
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
        订单信息同步记录
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
        $(function () {
            var syncStatus = ${orderDetailSyncSearch.syncStatus};
            $("#syncStatus").val(syncStatus);
        })
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

                <h3>订单信息同步记录</h3>
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
                            <label class="first ">订单编号：</label>
                            <input name="orderId" type="text"
                                   class="input-normal" value="${orderDetailSyncSearch.orderId}"/>
                            <label class="first ">同步结果：</label>
                            <select name="syncStatus" id="syncStatus">
                                <option value="-1">全部</option>
                                <option value="0">同步失败</option>
                                <option value="1">同步成功</option>
                            </select>
                            <br/><br/>
                            <label class="first ">起始时间：</label>
                            <input name="beginTime" type="text" id="txtCreateBeginTime" placeholder=" [开始时间]"
                                   class="input-normal Wdate"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                                   style="margin-left: 8px;" value="${orderDetailSyncSearch.beginTime}"/>
                            <label class="first ">---</label>
                            <input name="endTime" type="text" id="txtCreateEndTime" placeholder=" [结束时间]"
                                   class="input-normal Wdate" value="${orderDetailSyncSearch.endTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'txtCreateBeginTime\')}'})"/>
                            <label>
                                <a class="btn-lit btn-middle" href="javascript:$('#searchForm').submit();"
                                   style="margin-bottom: 3px;">
                                    <span>查询</span>
                                </a>
                                <a class="btn-lit btn-middle"
                                   href="<c:url value="/erpService/platform/orderDetailSyncs?erpUserType=${erpUserType}" />"
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
                            <th scope="col">添加时间
                            </th>
                            <th scope="col">最后一次同步时间
                            </th>
                            <th scope="col">操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="log" items="${orderDetailSyncLogs.getContent()}">
                            <tr>
                                <td class="txt40 c">${log.orderId}</td>
                                <td class="txt40 c">${log.providerType.name}</td>
                                <td class="txt40 c">${log.detailSyncStatus.name}</td>
                                <td class="txt40 c"><fmt:formatDate value="${log.createTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt40 c"><fmt:formatDate value="${log.syncTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt80 c">
                                    <c:if test="${log.detailSyncStatus.code==0}">
                                        <a href="javascript:rePush(${log.id},${log.userType.code})">重新推送</a>
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
                    var pageCount = ${orderDetailSyncLogs.getTotalPages()};
                    var recordCount = ${orderDetailSyncLogs.getTotalElements()};
                    var formName = 'searchForm';
                    Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
                </script>

            </div>
        </div>
    </form>
</div>
</body>
</html>
