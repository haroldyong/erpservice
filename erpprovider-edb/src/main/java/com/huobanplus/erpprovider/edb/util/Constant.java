package com.huobanplus.erpprovider.edb.util;

/**
 * 常量工具类
 */
public class Constant {

    /**
     * 请求地址
     */
    public final static String REQUEST_URI = "http://vip802.6x86.com/edb2/rest/index.aspx";

    //系统级参数
    public static final String DB_HOST = "edb_a88888";
    public static final String APP_KEY = "6f55e36b";
    public static final String APP_SECRET = "adeaac8b252e4ed6a564cdcb1a064082";
    public static final String TOKEN = "a266066b633c429890bf4df1690789a3";
    public static final String FORMAT = "XML";
    public static final String V = "2.0";
    public static final String SLENCRY = "0";
    public static final String IP = "117.79.148.228";
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmm";

    //请求方法
    /**
     * 写入订单
     */
    public static final String CREATE_ORDER = "edbTradeAdd";
    /**
     * 得到商品库存信息
     */
    public static final String GET_PRO_INFO = "edbProductGet";
    public static final String GET_ORDER_INFO = "edbTradeGet";
    public static final String ORDER_STATUS_UPDATE = "edbTradeImportStatusUpdate";

    //返回参数
    /**
     * 获取产品库存信息返回参数
     */
    public static final String GET_PRO_INFO_FIELD = "product_no,brand_no,brand_name,standard,sort_no,sort_name,bar_code,barCode_remark,proitem_no,product_name,verder_no,ground_date,bookBuild_date,supplier,product_state,is_suit,sale_price,cost,tax_cost,entity_stock,sell_stock,standbook_stock,use_num,总数量";
    /**
     * 获取订单详细信息返回参数
     */
    public static final String GET_ORDER_INFO_FIELD = "storage_id,tid,transaction_id,customer_id,distributor_id,shop_name,out_tid,out_pay_tid,voucher_id,shopid,serial_num,order_channel,order_from,buyer_id,buyer_name,type,status,abnormal_status,merge_status,receiver_name,receiver_mobile,phone,province,city,district,address,post,email,is_bill,invoice_name,invoice_situation,invoice_title,invoice_type,invoice_content,pro_totalfee,order_totalfee,reference_price_paid,invoice_fee,cod_fee,other_fee,refund_totalfee,discount_fee,discount,channel_disfee,merchant_disfee,order_disfee,commission_fee,is_cod,point_pay,cost_point,point,superior_point,royalty_fee,external_point,express_no,tradegifadd,express,express_coding,online_express,sending_type,real_income_freight,real_pay_freight,gross_weight,gross_weight_freight,net_weight_freight,freight_explain,total_weight,tid_net_weight,tid_time,pay_time,get_time,order_creater,business_man,payment_received_operator,payment_received_time,review_orders_operator,review_orders_time,finance_review_operator,finance_review_time,advance_printer,printer,print_time,is_print,adv_distributer,adv_distribut_time,distributer,distribut_time,is_inspection,inspecter,inspect_time,cancel_operator,cancel_time,revoke_cancel_er,revoke_cancel_time,packager,pack_time,weigh_operator,weigh_time,book_delivery_time,delivery_operator,delivery_time,locker,lock_time,book_file_time,file_operator,file_time,finish_time,modity_time,is_promotion,promotion_plan,out_promotion_detail,good_receive_time,receive_time,verificaty_time,enable_inte_sto_time,enable_inte_delivery_time,alipay_id,alipay_status,pay_mothed,pay_status,platform_status,rate,currency,delivery_status,buyer_message,service_remarks,inner_lable,distributor_mark,system_remarks,other_remarks,message,message_time,is_stock,related_orders,related_orders_type,import_mark,delivery_name,is_new_customer,distributor_level,cod_service_fee,express_col_fee,product_num,sku,item_num,single_num,flag_color,is_flag,taobao_delivery_order_status,taobao_delivery_status,taobao_delivery_method,order_process_time,is_break,breaker,break_time,break_explain,plat_send_status,plat_type,is_adv_sale,provinc_code,city_code,area_code,express_code,last_returned_time,last_refund_time,deliver_centre,deliver_station,is_pre_delivery_notice,jd_delivery_time,Sorting_code,cod_settlement_vouchernumber,total_num,child_storage_id,child_tid,child_pro_detail_code,child_pro_name,child_specification,child_barcode,child_combine_barcode,child_iscancel,child_isscheduled,child_stock_situation,child_isbook_pro,child_iscombination,child_isgifts,child_gift_num,child_book_storage,child_pro_num,child_send_num,child_refund_num,child_refund_renum,child_inspection_num,child_timeinventory,child_cost_price,child_sell_price,child_average_price,child_original_price,child_sys_price,child_ferght,child_item_discountfee,child_inspection_time,child_weight,child_shopid,child_out_tid,child_out_proid,child_out_prosku,child_proexplain,child_buyer_memo,child_seller_remark,child_distributer,child_distribut_time,child_second_barcode,child_product_no,child_brand_number,child_brand_name,child_book_inventory,child_product_specification,child_discount_amount,child_credit_amount,child_MD5_encryption";

    public static final String ORDER_STATUS_UPDATE_FIELDS = "result";
}
