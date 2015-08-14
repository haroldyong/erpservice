import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpservice.transit.utils.DesUtil;
import com.huobanplus.erpservice.transit.utils.DxDESCipher;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by allan on 2015/8/11.
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        String successStr = "{\"Success\":{\"total_results\":\"2\",\"items\":{\"item\":[{\"resultNum\":\"1\",\"storage_id\":\"3\",\"tid\":\"S1412110000004\",\"transaction_id\":\"s14121100002\",\"customer_id\":\"\",\"distributor_id\":\"\",\"shop_name\":\"倩楠の店\",\"out_tid\":\"\",\"out_pay_tid\":\"\",\"voucher_id\":\"\",\"shopid\":\"1\",\"serial_num\":\"\",\"order_channel\":\"手工下单\",\"order_from\":\"\",\"buyer_id\":\"双子星座8304\",\"buyer_name\":\"\",\"type\":\"正常订单\",\"status\":\"已确认\",\"abnormal_status\":\"\",\"merge_status\":\"\",\"receiver_name\":\"张三edb_a88888\",\"receiver_mobile\":\"15901425729\",\"phone\":\"84992371\",\"province\":\"北京\",\"city\":\"北京市\",\"district\":\"朝阳区\",\"address\":\"北京北京市朝阳区 红军营南路瑞普大厦\",\"post\":\"100000\",\"email\":\"\",\"is_bill\":\"0\",\"invoice_name\":\"\",\"invoice_situation\":\"\",\"invoice_title\":\"\",\"invoice_type\":\"\",\"invoice_content\":\"\",\"pro_totalfee\":\"158.00\",\"order_totalfee\":\"158.00\",\"reference_price_paid\":\"158.00\",\"invoice_fee\":\"158.00\",\"cod_fee\":\"\",\"other_fee\":\"\",\"refund_totalfee\":\"\",\"discount_fee\":\"0.00\",\"discount\":\"\",\"channel_disfee\":\"\",\"merchant_disfee\":\"\",\"order_disfee\":\"0.00\",\"commission_fee\":\"\",\"is_cod\":\"0\",\"point_pay\":\"\",\"cost_point\":\"\",\"point\":\"\",\"superior_point\":\"\",\"royalty_fee\":\"\",\"external_point\":\"\",\"express_no\":\"0011001071\",\"express\":\"申通快运\",\"express_coding\":\"STO\",\"online_express\":\"\",\"sending_type\":\"\",\"real_income_freight\":\"0.00\",\"real_pay_freight\":\"\",\"gross_weight\":\"0.000\",\"gross_weight_freight\":\"\",\"net_weight_freight\":\"0.00\",\"freight_explain\":\"\",\"total_weight\":\"\",\"tid_net_weight\":\"0.00\",\"tid_time\":\"2014-12-11 18:13:44\",\"pay_time\":\"2014-12-11 18:17:43\",\"get_time\":\"2015-08-07 17:51:25\",\"order_creater\":\"edb_a88888\",\"business_man\":\"edb\",\"payment_received_operator\":\"edb_a88888                                        \",\"payment_received_time\":\"2014-12-11 18:18:26\",\"review_orders_operator\":\"edb_a88888                                        \",\"review_orders_time\":\"2014-12-15 14:58:37\",\"finance_review_operator\":\"\",\"finance_review_time\":\"\",\"advance_printer\":\"\",\"printer\":\"\",\"print_time\":\"\",\"is_print\":\"\",\"adv_distributer\":\"\",\"adv_distribut_time\":\"\",\"distributer\":\"\",\"distribut_time\":\"\",\"is_inspection\":\"\",\"inspecter\":\"\",\"inspect_time\":\"\",\"cancel_operator\":\"\",\"cancel_time\":\"\",\"revoke_cancel_er\":\"\",\"revoke_cancel_time\":\"\",\"packager\":\"\",\"pack_time\":\"\",\"weigh_operator\":\"\",\"weigh_time\":\"\",\"book_delivery_time\":\"\",\"delivery_operator\":\"edb_a88888\",\"delivery_time\":\"2014-12-15 15:03:14\",\"locker\":\"\",\"lock_time\":\"\",\"book_file_time\":\"\",\"file_operator\":\"\",\"file_time\":\"\",\"finish_time\":\"\",\"modity_time\":\"\",\"is_promotion\":\"0\",\"promotion_plan\":\"\",\"out_promotion_detail\":\"\",\"good_receive_time\":\"\",\"receive_time\":\"\",\"verificaty_time\":\"\",\"enable_inte_sto_time\":\"2014-12-15 14:01:25\",\"enable_inte_delivery_time\":\"2014-12-11 18:13:53\",\"alipay_id\":\"\",\"alipay_status\":\"\",\"pay_mothed\":\"xianjin\",\"pay_status\":\"已付款\",\"platform_status\":\"未付款\",\"rate\":\"\",\"currency\":\"\",\"delivery_status\":\"已发货\",\"buyer_message\":\"\",\"service_remarks\":\"订单已导入\",\"inner_lable\":\"\",\"distributor_mark\":\"\",\"system_remarks\":\"\",\"other_remarks\":\"\",\"message\":\"\",\"message_time\":\"\",\"is_stock\":\"0\",\"related_orders\":\"\",\"related_orders_type\":\"\",\"import_mark\":\"已导入\",\"delivery_name\":\"\",\"is_new_customer\":\"\",\"distributor_level\":\"\",\"cod_service_fee\":\"\",\"express_col_fee\":\"0.00\",\"product_num\":\"2\",\"sku\":\"1\",\"item_num\":\"1\",\"single_num\":\"2\",\"flag_color\":\"\",\"is_flag\":\"\",\"taobao_delivery_order_status\":\"未付款\",\"taobao_delivery_status\":\"\",\"taobao_delivery_method\":\"\",\"order_process_time\":\"\",\"is_break\":\"0\",\"breaker\":\"\",\"break_time\":\"\",\"break_explain\":\"\",\"plat_send_status\":\"0\",\"plat_type\":\"\",\"is_adv_sale\":\"0\",\"provinc_code\":\"110000\",\"city_code\":\"110100\",\"area_code\":\"110105\",\"express_code\":\"12\",\"last_returned_time\":\"\",\"last_refund_time\":\"\",\"deliver_centre\":\"\",\"deliver_station\":\"\",\"is_pre_delivery_notice\":\"0\",\"jd_delivery_time\":\"\",\"Sorting_code\":\"\",\"cod_settlement_vouchernumber\":\"\",\"total_num\":\"2305\",\"originCode\":\"\",\"destCode\":\"\",\"big_marker\":\"\",\"tradegifadd\":\"否\",\"总数量\":\"2305\",\"tid_item\":[{\"storage_id\":\"3\",\"tid\":\"S1412110000004\",\"pro_detail_code\":\"1\",\"pro_name\":\"测试产品1\",\"specification\":\"米黄xl\",\"barcode\":\"QI14121100001\",\"combine_barcode\":\"\",\"iscancel\":\"\",\"isscheduled\":\"\",\"stock_situation\":\"\",\"isbook_pro\":\"\",\"iscombination\":\"0\",\"isgifts\":\"\",\"gift_num\":\"\",\"book_storage\":\"\",\"pro_num\":\"2\",\"send_num\":\"2\",\"refund_num\":\"\",\"refund_renum\":\"\",\"inspection_num\":\"\",\"timeinventory\":\"\",\"cost_price\":\"0.00\",\"sell_price\":\"79.00\",\"average_price\":\"\",\"original_price\":\"0.00\",\"sys_price\":\"0.00\",\"ferght\":\"\",\"item_discountfee\":\"0.00\",\"inspection_time\":\"\",\"weight\":\"0.000\",\"shopid\":\"\",\"out_tid\":\"\",\"out_proid\":\"\",\"out_prosku\":\"\",\"proexplain\":\"\",\"buyer_memo\":\"\",\"seller_remark\":\"\",\"distributer\":\"\",\"distribut_time\":\"\",\"second_barcode\":\"aaaaaaaaaaaaa\",\"product_no\":\"QI141211000011\",\"brand_number\":\"1\",\"brand_name\":\"米格达思\",\"book_inventory\":\"103.000\",\"product_specification\":\"米黄xl\",\"discount_amount\":\"0.00\",\"credit_amount\":\"0.00\",\"MD5_encryption\":\"c71dd3f0ba085972479167556f8b92b1\"}]},{\"resultNum\":\"2\",\"storage_id\":\"2\",\"tid\":\"S1412160000001\",\"transaction_id\":\"\",\"customer_id\":\"\",\"distributor_id\":\"\",\"shop_name\":\"倩楠の店\",\"out_tid\":\"DD141216100158\",\"out_pay_tid\":\"\",\"voucher_id\":\"\",\"shopid\":\"1\",\"serial_num\":\"\",\"order_channel\":\"直营网店\",\"order_from\":\"\",\"buyer_id\":\"MI141120100022\",\"buyer_name\":\"\",\"type\":\"正常订单\",\"status\":\"已确认\",\"abnormal_status\":\"\",\"merge_status\":\"\",\"receiver_name\":\"吴明春土中午休一天edb_a88888\",\"receiver_mobile\":\"15901551935\",\"phone\":\"\",\"province\":\"\",\"city\":\"0\",\"district\":\"\",\"address\":\"北京市市辖区朝阳区all路PK了的啊call里hi去www哦哦的吧他们某经历过家里去他了了监控\",\"post\":\"\",\"email\":\"\",\"is_bill\":\"0\",\"invoice_name\":\"\",\"invoice_situation\":\"0\",\"invoice_title\":\"\",\"invoice_type\":\"\",\"invoice_content\":\"\",\"pro_totalfee\":\"0.00\",\"order_totalfee\":\"0.00\",\"reference_price_paid\":\"0.00\",\"invoice_fee\":\"0.00\",\"cod_fee\":\"\",\"other_fee\":\"\",\"refund_totalfee\":\"\",\"discount_fee\":\"0.00\",\"discount\":\"\",\"channel_disfee\":\"\",\"merchant_disfee\":\"\",\"order_disfee\":\"0.00\",\"commission_fee\":\"0.00\",\"is_cod\":\"0\",\"point_pay\":\"\",\"cost_point\":\"0.00\",\"point\":\"\",\"superior_point\":\"\",\"royalty_fee\":\"\",\"external_point\":\"\",\"express_no\":\"kd000001\",\"express\":\"圆通\",\"express_coding\":\"YTO\",\"online_express\":\"\",\"sending_type\":\"\",\"real_income_freight\":\"0.00\",\"real_pay_freight\":\"\",\"gross_weight\":\"0.000\",\"gross_weight_freight\":\"\",\"net_weight_freight\":\"0.00\",\"freight_explain\":\"\",\"total_weight\":\"\",\"tid_net_weight\":\"100.00\",\"tid_time\":\"2014-12-16 00:00:00\",\"pay_time\":\"\",\"get_time\":\"2014-12-16 15:40:25\",\"order_creater\":\"edb_a88888\",\"business_man\":\"\",\"payment_received_operator\":\"\",\"payment_received_time\":\"\",\"review_orders_operator\":\"edb_b00001                                        \",\"review_orders_time\":\"2014-12-16 15:41:44\",\"finance_review_operator\":\"\",\"finance_review_time\":\"\",\"advance_printer\":\"\",\"printer\":\"edb_a88888                                        \",\"print_time\":\"2014-12-16 17:00:00\",\"is_print\":\"1\",\"adv_distributer\":\"\",\"adv_distribut_time\":\"\",\"distributer\":\"\",\"distribut_time\":\"\",\"is_inspection\":\"\",\"inspecter\":\"\",\"inspect_time\":\"\",\"cancel_operator\":\"\",\"cancel_time\":\"\",\"revoke_cancel_er\":\"\",\"revoke_cancel_time\":\"\",\"packager\":\"\",\"pack_time\":\"\",\"weigh_operator\":\"\",\"weigh_time\":\"\",\"book_delivery_time\":\"\",\"delivery_operator\":\"edb_b00001\",\"delivery_time\":\"2014-12-16 17:00:00\",\"locker\":\"\",\"lock_time\":\"\",\"book_file_time\":\"\",\"file_operator\":\"\",\"file_time\":\"\",\"finish_time\":\"\",\"modity_time\":\"\",\"is_promotion\":\"0\",\"promotion_plan\":\"\",\"out_promotion_detail\":\"\",\"good_receive_time\":\"\",\"receive_time\":\"\",\"verificaty_time\":\"\",\"enable_inte_sto_time\":\"2014-12-16 15:40:30\",\"enable_inte_delivery_time\":\"2014-12-16 15:40:31\",\"alipay_id\":\"                                                  \",\"alipay_status\":\"\",\"pay_mothed\":\"449716200001\",\"pay_status\":\"已付款\",\"platform_status\":\"已付款\",\"rate\":\"\",\"currency\":\"\",\"delivery_status\":\"已发货\",\"buyer_message\":\"\",\"service_remarks\":\"\",\"inner_lable\":\"\",\"distributor_mark\":\"\",\"system_remarks\":\"\",\"other_remarks\":\"\",\"message\":\"\",\"message_time\":\"\",\"is_stock\":\"0\",\"related_orders\":\"\",\"related_orders_type\":\"\",\"import_mark\":\"未导入\",\"delivery_name\":\"\",\"is_new_customer\":\"\",\"distributor_level\":\"\",\"cod_service_fee\":\"0.00\",\"express_col_fee\":\"0.00\",\"product_num\":\"1\",\"sku\":\"1\",\"item_num\":\"1\",\"single_num\":\"1\",\"flag_color\":\"\",\"is_flag\":\"\",\"taobao_delivery_order_status\":\"已付款\",\"taobao_delivery_status\":\"\",\"taobao_delivery_method\":\"\",\"order_process_time\":\"\",\"is_break\":\"0\",\"breaker\":\"\",\"break_time\":\"\",\"break_explain\":\"\",\"plat_send_status\":\"0\",\"plat_type\":\"\",\"is_adv_sale\":\"0\",\"provinc_code\":\"\",\"city_code\":\"\",\"area_code\":\"\",\"express_code\":\"4\",\"last_returned_time\":\"\",\"last_refund_time\":\"\",\"deliver_centre\":\"\",\"deliver_station\":\"\",\"is_pre_delivery_notice\":\"0\",\"jd_delivery_time\":\"\",\"Sorting_code\":\"\",\"cod_settlement_vouchernumber\":\"\",\"total_num\":\"2305\",\"originCode\":\"\",\"destCode\":\"\",\"big_marker\":\"\",\"tradegifadd\":\"否\",\"总数量\":\"2305\",\"tid_item\":[{\"storage_id\":\"2\",\"tid\":\"S1412160000001\",\"pro_detail_code\":\"7\",\"pro_name\":\"韩束 墨菊深度补水精华素 30ml\",\"specification\":\"*\",\"barcode\":\"SA001\",\"combine_barcode\":\"\",\"iscancel\":\"\",\"isscheduled\":\"\",\"stock_situation\":\"\",\"isbook_pro\":\"\",\"iscombination\":\"0\",\"isgifts\":\"\",\"gift_num\":\"\",\"book_storage\":\"\",\"pro_num\":\"1\",\"send_num\":\"1\",\"refund_num\":\"\",\"refund_renum\":\"\",\"inspection_num\":\"\",\"timeinventory\":\"\",\"cost_price\":\"0.00\",\"sell_price\":\"0.00\",\"average_price\":\"\",\"original_price\":\"0.00\",\"sys_price\":\"100.00\",\"ferght\":\"\",\"item_discountfee\":\"0.00\",\"inspection_time\":\"\",\"weight\":\"100.000\",\"shopid\":\"\",\"out_tid\":\"DD141216100158\",\"out_proid\":\"\",\"out_prosku\":\"\",\"proexplain\":\"\",\"buyer_memo\":\"\",\"seller_remark\":\"\",\"distributer\":\"\",\"distribut_time\":\"\",\"second_barcode\":\"\",\"product_no\":\"SA001\",\"brand_number\":\"1\",\"brand_name\":\"米格达思\",\"book_inventory\":\"229.000\",\"product_specification\":\"50*50\",\"discount_amount\":\"0.00\",\"credit_amount\":\"0.00\",\"MD5_encryption\":\"3f6813ba5d779e4dd9a27fdeb99adf8b\"}]}]}}}";

        String errorStr = "{\"error_code\":100,\"error_msg\":\"PARAM_100:输入的参数begin_time不存在或不是有效参数\",\"request_args\":[{\"key\":\"slencry\",\"value\":\"0\"},{\"key\":\"method\",\"value\":\"edbTradeGet\"},{\"key\":\"v\",\"value\":\"2.0\"},{\"key\":\"ip\",\"value\":\"117.79.148.228\"},{\"key\":\"format\",\"value\":\"json\"},{\"key\":\"sign\",\"value\":\"F7BD86B8935A6774B979F58BDA68AC2E\"},{\"key\":\"appkey\",\"value\":\"6f55e36b\"},{\"key\":\"page_no\",\"value\":\"1\"},{\"key\":\"dbhost\",\"value\":\"edb_a88888\"},{\"key\":\"timestamp\",\"value\":\"201508141025\"},{\"key\":\"page_size\",\"value\":\"2\"}]}";

        ObjectMapper objectMapper = new ObjectMapper();
        Map successMap = objectMapper.readValue(successStr, Map.class);
        Map errorMap = objectMapper.readValue(errorStr, Map.class);

        System.out.println(successMap.keySet().iterator().next());
        System.out.println(errorMap.keySet().iterator().next());
        List<Map> list = (List<Map>) ((Map) ((Map) successMap.get("Success")).get("items")).get("item");

        /*JSONObject jsonObject = new JSONObject(successStr);
        JSONArray jsonArray = jsonObject.getJSONObject("Success").getJSONObject("items").getJSONArray("item");
        JSONObject jsonObject1 = new JSONObject(errorStr);

        System.out.println(jsonObject1.keys().next());*/

    }
}
