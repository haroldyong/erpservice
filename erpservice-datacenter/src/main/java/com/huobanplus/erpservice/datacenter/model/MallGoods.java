package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-05.
 */

@Data
public class MallGoods {

    private String bn;

    private String goodName;

    private int num;

    private double price;

    private int isSku;

    private String remark;

    List<MallGoodsItem> productBeans;

}
