package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/16.
 */
public class InventoryInfo extends BaseBean {

    //出库单号
    private String outStorageNo;
    //运费
    private String freight;
    //运费分摊方式1:按产品数量2：按产品重量默认为1
    private String freightAvgway;
    //入库单号
    private String inStorageNo;
    //条形码
    private String barCode;
    //入库数量
    private String instorageNum;
    //出库数量
    private String outstorageNum;
    //入库类型；全部:1/其他入库:10/产成品入库:11/原料入库:12/盘盈入库:13/维修入库:14/差错入库:15/退货入库:3/归还入库:4/调拨入库:6/正常入库:8/采购入库:9
    private int type;
    //日期类型--入库日期in_time,制单日期create_time,审核日期examine_time
    private String dateType;
    //开始时间;所查订单的开始时间,和日期类型配合使用.
    private String beginTime;
    //结束时间;所查订单的结束时间,和日期类型配合使用.如果没输入,则默认为此时此刻.
    private String endTime;
    //仓库名称，用于查询指定仓库的入库单
    private String storageName;
    //入库状态.0:未审核；1：已审核；2：已作废
    private int instorageStatus;
    //页大小，如果不填，则默认为200.每页最多返回记录数为200.
    private int pageSize;
    //当前页，如果不填，默认为1
    private int pageNo;
    //导入标记:不导入,未导入,已导入,已处理,已取消
    private String importMark;
    //入库类型编号
    private String typeNo;
    //供应商编码
    private String provider;
    //仓库
    private String storage;
    //制单员
    private String creater;
    //制单时间
    private String createTime;
    //入库时间
    private String inTime;
    //质检员
    private String qualityInspctor;
    //质检时间
    private String inspctTime;
    //质检结果
    private String inspctResult;
    //审核人
    private String examiner;
    //审核时间
    private String examineTime;
    //发货单号
    private String sendTid;
    //入库原因
    private String inReason;
    //成本价格
    private String cost;
    //来源单号
    private String SourceTid;
    //采购费用
    private String purchaseFee;
    //合同总额
    private String contractMoney;
    //相关单号
    private String relevantTid;
    //汇率
    private String rate;
    //币种
    private String currency;
    //外部合同号
    private String outContractTid;
    //物流公司
    private String logistics;
    //快递单号
    private String expressTid;
    //运费承担方
    private String freightPayer;
    //入库备注
    private String remarks;
    //运费均摊模式;按产品数量，按产品重量
    private String freightMode;
    //总记录数
    private String totalCount;
    //子节点
    private InventoryInfo[] instoreTid;
    //仓库编码
    private String storageNo;
    //来源：不对外提供，固定默认值
    private String listSource;
    //其他费用
    private String otherCost;
    //外部合同单号
    private String outPactNo;
    //产品明细编号
    private String productItemNo;
    //库位编号
    private String locationNo;
    //批次
    private String batch;
    //到期时间
    private String expireTime;
    //出库类型（可在档案管理-仓库档案-出库类型设置中查看）
    private String outstorageType;
    //出库时间
    private String outstorageTime;
    //返厂供应商编号：在采购管理—供应商档案里查看
    private String supplieNo;
    //原始入库单号
    private String YSInstorageNo;
    //出库备注
    private String outStorageRemark;
    //出库价
    private String outstoragePrice;
    //运费均摊
    private String freightAvg;
    //出库状态
    private String outstorageStatus;
    //出库类型名称
    private String outStoreTypeName;

}
