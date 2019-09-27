package com.res_system.mvc_skeleton;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.res_system.commons.util.ReUtil;

/**
 *
 * SandBox.
 *
 * @author res.
 *
 */
public class SandBox {

    // ------------------------------------------------------------------------------------------------------------------------
    // setting.
    /**
     * オブジェクトの内容を表示します.
     * @param hd ヘッダ.
     * @param data 対象オブジェクト.
     */
    public static void showObject(String hd, Object data) {
        if (data != null) {
            LinkedHashMap<String, Field> fields = new LinkedHashMap<>();
            ReUtil.getAllFields(data.getClass(), fields);
            System.out.print(hd + "  {");
            for (Field field : fields.values()) {
                Object value;
                try {
                    value = field.get(data);
                    String valueStr = (value != null) ? value.toString() : "";
                    System.out.print(field.getName() + ":\"" + valueStr + "\",");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("}");
        } else {
            System.out.println(hd + "  null");
        }
    }



    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    @Test
    public void SandBoxTest() throws Exception {
        String testinfo = "[ SandBox ]";
        System.out.println(testinfo + "** Start ****************");

        System.out.println(testinfo + "**   End ****************");
    }



}
