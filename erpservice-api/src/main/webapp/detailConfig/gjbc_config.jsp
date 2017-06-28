<%--
  Created by IntelliJ IDEA.
  User: hzbc
  Date: 2017-06-26
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var gjbcConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#gjbc_requestUrl").val());
            var key = $.trim($("#gjbc_key").val());
            var name = $.trim($("#gjbc_name").val());
            var mark = $.trim($("#gjbc_mark").val());
            var confirm = $.trim($("#gjbc_confirm").val());
            var order = $.trim($("#gjbc_order").val());
            var requestCustomsUrl = $.trim($("#gjbc_requestCustomsUrl").val());
            var isBc = $.trim($("#is_bc").val());
            var senderName = $.trim($("#sender_name").val());
            var senderCity = $.trim($("#sender_city").val());
            var senderAddress = $.trim($("#sender_address").val());
            var senderPhone = $.trim($("#sender_phone").val());
            var senderCountryCode = $.trim($("#sender_country_code").val());
            var customsInsured = $.trim($("#customs_insured").val());
            var buyerIdcard = $.trim($("#buyer_idcard").val());
            var pName = $.trim($("#p_name").val());
            var pWeb = $.trim($("#pWeb").val());
            var webName = $.trim($("#web_name").val());
            var reNo = $.trim($("#re_no").val());
            var reNoQg = $.trim($("#re_no_qg").val());
            var reName = $.trim($("#re_name").val());
            if (requestUrl.length == 0) {
                $.jBox.tip("请输入高捷接口请求地址");
                return null;
            }
            if (requestCustomsUrl == 0) {
                $.jBox.tip("请输入海关接口请求地址");
                return null;
            }
            if (key.length == 0) {
                $.jBox.tip("请输入验证码");
                return null;
            }
            if (name.length == 0) {
                $.jBox.tip("请输入账号名称");
                return null;
            }

            if (confirm.length == 0) {
                $.jBox.tip("请输入订单状态");
                return null;
            }
            if (order.length == 0) {
                $.jBox.tip("请输入请求参数格式")
                return null;
            }
            if (mark.length == 0) {
                $.jBox.tip("请输入业务类型");
                return null;
            }
            if (isBc.length == 0) {
                $.jBox.tip("请输入是否走高捷接口");
                return null;
            }
            if (senderName.length == 0) {
                $.jBox.tip("请输入发件人姓名")
                return null;
            }
            if (senderCity.length == 0) {
                $.jBox.tip("请输入发件人城市");
                return null;
            }
            if (senderAddress.length == 0) {
                $.jBox.tip("请输入发件人地址")
                return null;
            }
            if (senderPhone.length == 0) {
                $.jBox.tip("请输入发件人电话");
                return null;
            }
            if (senderCountryCode.length == 0) {
                $.jBox.tip("请输入发件人国别")
                return null;
            }
            if (customsInsured.length == 0) {
                $.jBox.tip("请输入保价费");
                return null;
            }
            if (buyerIdcard.length == 0) {
                $.jBox.tip("请输入收件人身份证号")
                return null;
            }
            if (pName.length == 0) {
                $.jBox.tip("请输入支付企业名称");
                return null;
            }
            if (pWeb.length == 0) {
                $.jBox.tip("请输入订单网址");
                return null;
            }
            if (webName.length == 0) {
                $.jBox.tip("请输入网址名称");
                return null;
            }
            if (reNo.length == 0) {
                $.jBox.tip("请输入商家广州备案号")
                return null;
            }
            if (reNoQg.length == 0) {
                $.jBox.tip("请输入商家全国备案号");
                return null;
            }
            if (reName.length == 0) {
                $.jBox.tip("请输入商家备案名称")
                return null;
            }
            var senderAndAddresseeInfo = senderName + "," + senderCity + "," + senderAddress + "," + senderPhone + "," + senderCountryCode + "," + buyerIdcard;
            var reInfo = reNo + "," + reNoQg + "," + reName;
            var gjbcConfig = {
                requestUrl: requestUrl,
                requestCustomsUrl: requestCustomsUrl,
                key: key,
                name: name,
                mark: mark,
                confirm: confirm,
                order: order,
                isBc: isBc,
                senderAndAddresseeInfo: senderAndAddresseeInfo,
                customsInsured: customsInsured,
                pName: pName,
                pWeb: pWeb,
                webName: webName,
                reInfo: reInfo,
            };
            return JSON.stringify(gjbcConfig);
        },
        setValues: function (jsonData) {
            $("#gjbc_requestUrl").val(jsonData.requestUrl);
            $("#gjbc_key").val(jsonData.key);
            $("#gjbc_name").val(jsonData.name);
            $("#gjbc_order").val(jsonData.order);
            $("#gjbc_confirm").val(jsonData.confirm);
            $("#gjbc_mark").val(jsonData.mark);
            $("#gjbc_requestCustomsUrl").val(jsonData.requestCustomsUrl);
            $("#is_bc").val(jsonData.isBc);
            var senderAndAddresseeInfo = jsonData.senderAndAddresseeInfo;
            if (senderAndAddresseeInfo) {
                var senderAndAddresseeInfoArray = senderAndAddresseeInfo.split(",");
                $("#sender_name").val(senderAndAddresseeInfoArray[0]);
                $("#sender_city").val(senderAndAddresseeInfoArray[1]);
                $("#sender_address").val(senderAndAddresseeInfoArray[2]);
                $("#sender_phone").val(senderAndAddresseeInfoArray[3]);
                $("#sender_country_code").val(senderAndAddresseeInfoArray[4]);
                $("#buyer_idcard").val(senderAndAddresseeInfoArray[5]);
            }
            $("#customs_insured").val(jsonData.customsInsured);
            $("#p_name").val(jsonData.pName);
            $("#pWeb").val(jsonData.pWeb);
            $("#web_name").val(jsonData.webName);

            var reInfo = jsonData.reInfo;
            if (reInfo) {
                var reInfoArry = reInfo.split(",");
                $("#re_no").val(reInfoArry[0]);
                $("#re_no_qg").val(reInfoArry[1]);
                $("#re_name").val(reInfoArry[2]);
            }
        }
    };
</script>



<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">高捷接口请求地址</th>
        <td>
            <input name="gjbc_requestUrl" type="text" value="" id="gjbc_requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">海关接口请求地址</th>
        <td>
            <input name="gjbc_requestCustomsUrl" type="text" value="" id="gjbc_requestCustomsUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">账号名称：</th>
        <td>
            <input name="gjbc_name" type="text" value="" id="gjbc_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">验证码</th>
        <td>
            <input name="gjbc_key" type="text" value="" id="gjbc_key" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">业务参数类型：</th>
        <td>
            <input name="gjbc_mark" type="text" value="" id="gjbc_mark" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">下单状态：</th>
        <td>
            <input name="gjbc_confirm" type="text" value="" id="gjbc_confirm" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">请求参数（json）：</th>
        <td>
            <input name="gjbc_order" type="text" value="" id="gjbc_order" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">是否走高捷接口(是：1，否：0)：</th>
        <td>
            <input name="is_bc" type="text" value="" id="is_bc" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人：</th>
        <td>
            <input name="sender_name" type="text" value="" id="sender_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人城市：</th>
        <td>
            <input name="sender_city" type="text" value="" id="sender_city" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人地址：</th>
        <td>
            <input name="sender_address" type="text" value="" id="sender_address" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人电话：</th>
        <td>
            <input name="sender_phone" type="text" value="" id="sender_phone" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人国别：</th>
        <td>
            <input name="sender_country_code" type="text" value="" id="sender_country_code" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">保价费：</th>
        <td>
            <input name="customs_insured" type="text" value="" id="customs_insured" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">收件人身份证号：</th>
        <td>
            <input name="buyer_idcard" type="text" value="" id="buyer_idcard" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">支付企业名称：</th>
        <td>
            <input name="p_name" type="text" value="" id="p_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">订单网址：</th>
        <td>
            <input name="pWeb" type="text" value="" id="pWeb" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">网址名称：</th>
        <td>
            <input name="web_name" type="text" value="" id="web_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">商家广州备案号：</th>
        <td>
            <input name="re_no" type="text" value="" id="re_no" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">商家全国备案号：</th>
        <td>
            <input name="re_no_qg" type="text" value="" id="re_no_qg" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">商家备案名称：</th>
        <td>
            <input name="re_name" type="text" value="" id="re_name" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
