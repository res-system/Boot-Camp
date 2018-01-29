package com.res_system.re_employee_manager.commons.model;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.util.ReUtil;

/**
 *
 * SandBox テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class SandBox {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ CommonModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ CommonModel Test @AfterClass ]");
   }

    @Before
    public void before() throws Exception {
        System.out.println("[ @Before ]");
    }

    @After
    public void after() throws Exception {
        System.out.println("[ @After ]");
    }


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
            for (Field field : fields.values()) {
                Object value;
                try {
                    value = field.get(data);
                    String valueStr = (value != null) ? value.toString() : "";
                    System.out.println(hd + "  " + field.getName() + ":" + valueStr);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(hd + "  null");
        }
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** SandBox テスト */
    @Test
    public void sandBoxTest() throws Exception {
        String testinfo = "SandBox :";
        System.out.println(testinfo + "** Start ****************");
        //String msg = "";
        showObject("", "");


        System.out.println(testinfo + ReUtil.toHashValueSha2("a8a0e10379" + "test" + "a2d6bae2c8"));
        //System.out.println(testinfo + ReUtil.makeToken(10));
        System.out.println(testinfo + "**   End ****************");
    }

}
