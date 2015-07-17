package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 商品信息实体
 */
public class ProductInfo implements Serializable {

    //商品编号
    private String productNo;
    //商品明细编号
    private String productDetailNo;
    //条形码
    private String barCode;
    //规格
    private String specification;
    //颜色
    private String color;
    //尺码
    private String size;
    //单位
    private String unit;
    //重量
    private String weight;
    //产品状态
    private String productStatus;
    //销售价格
    private String sellPrice;
    //参考进货价
    private String contrastPurchasePrice;
    //最低进货价
    private String miniPurchasePrice;
    //成本价
    private String cost;
    //含税成本价
    private String costTax;
    //进货价说明
    private String purchasePriceEx;
    //装箱数
    private String boxNum;
    //有效期
    private String periodValidity;
    //产品明细图片
    private String picture;
    //是否消耗品
    private String isConsump;
    //消耗周期
    private String consumpCycle;
    //不单发产品
    private String isSingleSend;
    //物料属性
    private String attribute;
    //采购类型
    private String purchaseType;
    //是否航空
    private String isFlight;
    //产品名称ID
    private String productNameId;
    //产品名称
    private String productName;
    //产品URL
    private String productUrl;
    //采购周期
    private String purchaseCycle;
    //采购批量
    private String purchaseBatch;
    //销量计算方式
    private String saleCalculation;
    //是否包材
    private String isPackMaterials;
    //备注
    private String remark;
    //操作人IP
    private String userIP;
    //品牌名称
    private String brandName;
    //分类名称
    private String sortName;
    //供应商
    private String supplier;
    //市场价
    private String marketPrice;
    //产品介绍
    private String productintro;
    //厂家货号
    private String factoryItem;
    //是否包装
    private String isPack;
    //包装费
    private String packingCharges;
    //毛重比例
    private String roughWeightRatio;
    //长
    private String length;
    //宽
    private String wide;
    //高
    private String high;
    //成品属性
    private String productAttribute;
    //生产级别
    private String createLevel;
    //物流级别
    private String logisticLevel;
    //存储级别
    private String storageLevel;
    //包装级别
    private String packLevel;
    //操作人ip,用于记录日志
    private String wfpid;
    //新品到期时间
    private String newExpireDate;
    //单品数量
    private String suitItemCount;
    //打包编号
    private String packCode;
    //打包规格
    private String packStandard;
    //产品积分
    private String integral;
    //是否积分换购
    private String isUseIntegral;
    //换购积分
    private String buyIntegral;
    //最近进货价
    private String minCost;
    //最高进货价
    private String highestCost;
    //平均价
    private String avgCost;
    //预计到货日期
    private String avgcostExplain;
    //是否删除
    private String isDeleted;
    //是否停用
    private String isStop;
    //积分系数
    private String integralScale;
    //存货属性
    private String stockAttribute;
    //需要发货数量
    private String lowestStock;
    //尺码名称
    private String sizeName;
    //颜色名称
    private String colorName;
    //自定义属性
    private String customAtt;
    

}
