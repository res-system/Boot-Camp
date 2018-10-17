package com.res_system.re_emp_manager;


import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.view.thexpressionobjects.ExThHelper;

/**
 *
 * SandBox テストクラス.
 *
 * @author res.
 *
 */
//@RunWith(CdiTestRunner.class)
public class SandBox {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ SandBox @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception {  System.out.println("[ SandBox @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception {  System.out.println("[ @After ]"); }


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

    public static Object actual(final String msg, final Object value) {
        System.out.println(msg + value);
        return value;
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** SandBox テスト */
    @Test
    public void sandBoxTest() throws Exception {
        String testinfo = "SandBox :";
        System.out.println(testinfo + "** Start ****************");
        //String msg = "";
//        showObject("", "");
        
        ExThHelper target = new ExThHelper();
        System.out.println(target.commonMessages());

        //System.out.println(testinfo + ReUtil.makeToken(10));
        System.out.println(testinfo + "**   End ****************");
    }

}
