package com.huobanplus.erpprovider.gy.formatgy;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2016/5/31.
 */
@Getter
@Setter
public class GetDeliveryBean {

//    page_no 	Number 			1 	页码
//    page_size 	Number 			10 	每页大小
//    code 	String 				单据编号
//    start_create 	DateTime 				开始_创建时间
//    end_create 	DateTime 				结束_创建时间
//    start_delivery_date 	DateTime 				开始-发货时间
//    end_delivery_date 	DateTime 				结束-发货时间
//    warehouse_code 	String 				仓库代码
//    shop_code 	String 				店铺代码
//    outer_code 	String 				平台单号
//    print 	Number 		0-未打印，1-已打印 		查询物流单的打印状态
//    scan 	Number 		0-未扫描，1-已扫描 		扫描状态
//    cod 	Number 		0-否，1-是 		是否货到付款
//    vip_name 	String 				会员名称
//    delivery 	Number 		0-未发货,发货失败,发货中；1-发货完成 		发货状态
//    mail_no 	String 				物流单号

    /**
     * page_no 	Number 			1 	页码
     */
    @JSONField(name="page_no")
    private String pageNo;
    /**
     * page_size 	Number 			10 	每页大小
     */
    @JSONField(name="page_size")
    private String pageSize;
    /**
     * code 	String 				单据编号
     */
    private String code;
    /**
     * start_create 	DateTime 				开始_创建时间
     */
    @JSONField(name="start_create")
    private String startCreate;
    /**
     * end_create 	DateTime 				结束_创建时间
     */
    @JSONField(name="end_create")
    private String endCreate;
    /**
     * start_delivery_date 	DateTime 				开始-发货时间
     */
    @JSONField(name="start_delivery_date")
    private String startDeliveryDate;
    /**
     * end_delivery_date 	DateTime 				结束-发货时间
     */
    @JSONField(name="end_delivery_date")
    private String endDeliveryDate;
    /**
     * warehouse_code 	String 				仓库代码
     */
    @JSONField(name="warehouse_code")
    private String warehouseCode;
    /**
     * shop_code 	String 				店铺代码
     */
    @JSONField(name="shop_code")
    private String shopCode;
    /**
     * outer_code 	String 				平台单号
     */
    @JSONField(name="outer_code")
    private String outerCode;
    /**
     * print 	Number 		0-未打印，1-已打印 		查询物流单的打印状态
     */
    private String print;
    /**
     * scan 	Number 		0-未扫描，1-已扫描 		扫描状态
     */
    private String scan;
    /**
     * cod 	Number 		0-否，1-是 		是否货到付款
     */
    private String cod;
    /**
     * vip_name 	String 				会员名称
     */
    @JSONField(name="vip_name")
    private String vipName;
    /**
     * delivery 	Number 		0-未发货,发货失败,发货中；1-发货完成 		发货状态
     */
    private String delivery;
    /**
     *  mail_no 	String 				物流单号
     */
    @JSONField(name="mail_no")
    private String mailNo;


}
