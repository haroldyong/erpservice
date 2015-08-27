package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;

/**
 * 商品sku货品列表
 * Created by liual on 2015-08-26.
 */
@Entity
@Table(name = "Mall_Product")
public class MallProductBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 商品规格名称
     */
    private String skuName;
    /**
     * sku编号
     */
    private String skuId;
    private int num;

    @ManyToOne
    @JoinColumn(name = "goodId")
    private MallGoodBean goodBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public MallGoodBean getGoodBean() {
        return goodBean;
    }

    public void setGoodBean(MallGoodBean goodBean) {
        this.goodBean = goodBean;
    }
}
