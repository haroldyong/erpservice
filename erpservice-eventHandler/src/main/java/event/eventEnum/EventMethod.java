package event.eventEnum;

/**
 * 订单事件方法枚举
 * Created by allan on 2015/7/14.
 */
public interface EventMethod {
    enum OrderEventMethod {
        CREATE_ORDER(1, "创建订单"),
        GET_ORDER_DETAIL(2, "得到订单详情");
        //todo

        private int value;
        private String name;

        OrderEventMethod(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum GoodEventMethod {
        CREATE_GOOD(1, "创建商品"),
        GET_GOOD_DETAIL(2, "得到商品详情");
        //todo

        private int value;
        private String name;

        GoodEventMethod(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum DeliveryEventMethod {
        DELIVER(1, "发货");
        //todo

        private int value;
        private String name;

        DeliveryEventMethod(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
