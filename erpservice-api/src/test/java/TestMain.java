import com.huobanplus.erpservice.transit.utils.DesUtil;
import com.huobanplus.erpservice.transit.utils.DxDESCipher;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by allan on 2015/8/11.
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        String mockStr = "123456";
        String enStr = DxDESCipher.encrypt(mockStr, "11112222");
        System.out.println(enStr);

        String key = "erpdeskey";
        System.out.println(DigestUtils.md5Hex(key));

    }
}
