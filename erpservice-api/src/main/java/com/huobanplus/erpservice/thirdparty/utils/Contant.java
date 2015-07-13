package com.huobanplus.erpservice.thirdparty.utils;

/**
 * 常量定义
 */
public class Contant {
    //EDB接口访问ip
    private final static String HOST_IP = "http://vip802.6x86.com/edb2/rest/index.aspx";
    //是否开启日志模式
    private final static boolean IS_LOG = true;
    //是否生产环境
    private final static boolean IS_PRODUCTION = false;

    //E店宝接口名称
    /**
     * 产品信息
     */
    //增加产品明细信息
    private final static String EDB_PRODUCT_DETAIL_ADD = "edbProductDetailAdd";
    //更新产品明细信息
    private final static String EDB_PRODUCT_DETAIL_INFO_UPDATE = "edbProductDetailInfoUpdate";
    //获取产品分类信息
    private final static String EDB_PRODUCT_DETAIL_CLASS_GET = "edbProductClassGet";
    //增加产品品牌
    private final static String EDB_PRODUCT_BRAND_ADD = "edbProductBrandAdd";
    //获取产品基本产品信息
    private final static String EDB_PRODUCT_BASE_INFO_GET = "edbProductBaseInfoGet";
    //获取产品库存信息
    private final static String EDB_PRODUCT_GET = "edbProductGet";
    /**
     * 出入库信息
     */
    //确认出库单
    private final static String EDB_OUT_STORE_CONFIRM = "edbOutStoreConfirm";
    //确认入库单
    private final static String EDB_IN_STORE_CONFIRM = "edbInStoreConfirm";
    //入库单回写信息
    private final static String EDB_IN_STORE_WRITE_BACK = "edbInStoreWriteback";
    //出库单回写
    private final static String EDB_OUT_STORE_WRITE_BACK = "edbOutStoreWriteback";
    //获取入库单信息
    private final static String EDB_IN_STORE_GET = "edbInStoreGet";
    //获取出库单信息
    private final static String EDB_OUT_STORE_GET = "edbOutStoreGet";
    //增加入库单信息
    private final static String EDB_IN_STORE_ADD = "edbInStoreAdd";
    //退货单导入接口
    private final static String EDB_RETURN_STORE_ADD = "edbReturnStoreAdd";
    //增加出库单信息
    private final static String EDB_OUT_STORE_ADD = "edbOutStoreAdd";
    /**
     * 订单信息
     */
    //更新订单状态
    private final static String EDB_TRADE_IMPORT_STATUS_UPDATE = "edbTradeImportStatusUpdate";
    //订单作废
    private final static String EDB_TRADE_CANCEL = "edbTradeCancel";
    //根据条件得到外部订单信息
    private final static String EDB_OUT_TRADE_GET = "edbOutTradeGet";
    //订单批量发货
    private final static String EDB_TRADE_DELIVERY_BATCH = "edbTradeDeliveryBatch";
    //更新发票信息
    private final static String EDB_INVOICE_UPDATE = "edbInvoiceUpdate";
    //获取发票信息
    private final static String EDB_INVOICE_GET = "edbInvoiceGet";
    //订单业务状态更新
    private final static String EDB_TRADE_UPDATE = "edbTradeUpdate";
    //获取订单信息
    private final static String EDB_TRADE_GET = "edbTradeGet";
    //写入订单
    private final static String EDB_TRADE_ADD = "edbTradeAdd";
    /**
     * 售后信息
     */
    //获取退货订单信息
    private final static String EDB_TRADE_RETURN_GET = "edbTradReturnGet";

    //公共参数
    //e店宝主账号
    private final static String DB_HOST = "dbhost";
    //公钥
    private final static String APP_KEY = "appkey";
    //返回格式
    private final static String FORMAT = "format";

}
