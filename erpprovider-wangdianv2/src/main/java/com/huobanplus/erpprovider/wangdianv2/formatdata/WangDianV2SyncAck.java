/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 旺店通V2物流同步状态回写
 * Created by allan on 22/03/2017.
 */
@Setter
@Getter
public class WangDianV2SyncAck {
    @JSONField(name = "rec_id")
    private int recId;
    private int status;
    private String message;
}
