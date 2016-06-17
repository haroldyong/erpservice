package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYDeliveryOrderSearch {

    /**
     * 页码
     */
    @JSONField(name = "")
    private int pageNo;

    /**
     * 每页大小
     */
    @JSONField(name = "")
    private int pageSize;

    /**
     * 单据编号
     */
    @JSONField(name = "")
    private String code;

    /**
     * 开始_创建时间
     */
    @JSONField(name = "")
    private Date beginCreateTime;

    /**
     * 结束_创建时间
     */
    @JSONField(name = "")
    private Date endCreateTime;

    /**
     * 开始-发货时间
     */
    @JSONField(name = "")
    private Date beginDelveryTime;

    /**
     * 结束-发货时间
     */
    @JSONField(name = "")
    private Date endDelveryTime;

    /**
     * 仓库代码
     */
    @JSONField(name = "")
    private String wareHouseCode;

    /**
     * 店铺代码
     */
    @JSONField(name = "")
    private String shopCode;

    /**
     * 平台单号
     */
    @JSONField(name = "")
    private String outerCode;

    /**
     * 查询物流单的打印状态 0-未打印，1-已打印
     */
    @JSONField(name = "")
    private int print;

    /**
     * 扫描状态 0-未扫描，1-已扫描
     */
    @JSONField(name = "")
    private int scan;

    /**
     * 是否货到付款 	0-否，1-是
     */
    @JSONField(name = "")
    private int cod;

    /**
     * 会员名称
     */
    @JSONField(name = "")
    private String vipName;

    /**
     * 发货状态 0-未发货,发货失败,发货中；1-发货完成
     */
    @JSONField(name = "")
    private int delivery;

    /**
     * 物流单号
     */
    @JSONField(name = "")
    private String mailNo;
}
