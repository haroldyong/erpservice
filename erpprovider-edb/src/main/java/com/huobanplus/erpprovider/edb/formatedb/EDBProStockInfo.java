/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.formatedb;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by allan on 8/4/16.
 */
@Data
public class EDBProStockInfo {
    @JSONField(name = "product_no")
    private String productBn;
    @JSONField(name = "product_name")
    private String productName;
    /**
     * 可销库存
     */
    @JSONField(name = "sell_stock")
    private int salableStock;
    /**
     * 实物库存
     */
    @JSONField(name = "entity_stock")
    private int entityStock;
    /**
     * 台账库存
     */
    @JSONField(name = "standbook_stock")
    private int standBookStock;
    /**
     * 消耗数量
     */
    @JSONField(name = "use_num")
    private int useNum;
    /**
     * 总数量
     */
    @JSONField(name = "总数量")
    private int totalRecord;
}
