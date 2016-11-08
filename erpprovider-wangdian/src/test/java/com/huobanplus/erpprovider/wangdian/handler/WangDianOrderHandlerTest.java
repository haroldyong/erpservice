/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.wangdian.WangDianTestBase;
import com.huobanplus.erpprovider.wangdian.util.WangDianSignUtil;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
public class WangDianOrderHandlerTest extends WangDianTestBase {

    @Autowired
    private WangDianOrderHandler wangDianOrderHandler;

    @Test
    public void test() {
        Assert.assertNotNull(wangDianOrderHandler);
    }

    @Test
    public void testPushOrder() {
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);

        EventResult eventResult = wangDianOrderHandler.pushOrder(pushNewOrderEvent);

        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }

    @Test
    public void testRequestSign() throws UnsupportedEncodingException {
        String content = "{\"TradeList\":{\"Trade\":[{\"OrderCode\":\"OR2013010101\",\"TradeNO\":\"JY201301010001\",\"ErpLogisticCode\":\"SF\",\"LogisticName\":\"顺丰速运\",\"PostID\":\"3273832728\",\"SndTime\":\"2001-01-0110:00:00\"},{\"OrderCode\":\"OR2013010102\",\"TradeNO\":\"JY201301010002\",\"ErpLogisticCode\":\"EMS\",\"LogisticName\":\"EMS\",\"PostID\":\"3273832729\",\"SndTime\":\"2001-01-0110:00:00\"}]}}";
//        JSONObject jsonObject = JSON.parseObject(content);
        String sign = WangDianSignUtil.buildSign(content, "12345");
        System.out.println("\n*************");
        System.out.println(sign);
        System.out.println("\n*************");
    }

}
