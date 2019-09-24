package com.res_system.commons.mvc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * MvcUtil テストクラス.
 *
 * @author res.
 *
 */
public class MvcUtilTest {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ @AfterClass ]");
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
    // Test.
    /** getName テスト */
    @Test
    public void getNameOKTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] getName() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        {
            message = "直";
            expected = "name";
            actual = MvcUtil.getName("name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data.name";
            actual = MvcUtil.getName("data", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list[0].name";
            actual = MvcUtil.getName("list", "0", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void getNameNGTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] getName() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        {
            message = "直";
            expected = "";
            actual = MvcUtil.getName(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = ".";
            actual = MvcUtil.getName(null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "[].";
            actual = MvcUtil.getName(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** getId テスト */
    @Test
    public void getIdOKTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] getId() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        // getId
        {
            message = "直";
            expected = "name";
            actual = MvcUtil.getId("name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data_name";
            actual = MvcUtil.getId("data", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list_0_name";
            actual = MvcUtil.getId("list", "0", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // getIdWithValue
        {
            message = "直";
            expected = "name_value";
            actual = MvcUtil.getIdWithValue("name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data_name_value";
            actual = MvcUtil.getIdWithValue("data", "name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list_0_name_value";
            actual = MvcUtil.getIdWithValue("list", "0", "name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void getIdNGTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] getId() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        // getId
        {
            message = "直";
            expected = "";
            actual = MvcUtil.getId(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "_";
            actual = MvcUtil.getId(null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "__";
            actual = MvcUtil.getId(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // getIdWithValue
        {
            message = "直";
            expected = "_";
            actual = MvcUtil.getIdWithValue(null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "__";
            actual = MvcUtil.getIdWithValue(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "___";
            actual = MvcUtil.getIdWithValue(null, null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** editAttrValues テスト */
    @Test
    public void editAttrValuesOKTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] editAttrValues() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        {
            message = "なにもなし";
            expected = "value";
            actual = MvcUtil.editAttrValues("value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "変数式.";
            expected = "value";
            actual = MvcUtil.editAttrValues("*{value}");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "選択変数式.";
            expected = "value";
            actual = MvcUtil.editAttrValues("#{value}");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "メッセージ式.";
            expected = "value";
            actual = MvcUtil.editAttrValues("#{value}");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リンクURL式.";
            expected = "value";
            actual = MvcUtil.editAttrValues("@{value}");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リテラル.";
            expected = "value";
            actual = MvcUtil.editAttrValues("'value'");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void editAttrValuesNGTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] editAttrValues() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        // getId
        {
            message = "null";
            expected = "";
            actual = MvcUtil.editAttrValues(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** toJson テスト */
    @Test
    public void toJsonOKTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] toJson() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        {
            Message msgData = new Message("E", "メッセージ");
            message = "オブジェクト";
            expected = "{\"code\":\"\",\"kind\":\"E\",\"text\":\"メッセージ\",\"button\":null,\"selector\":\"\"}";
            actual = MvcUtil.toJson(msgData);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<Message> msgDataList = new ArrayList<>();
            msgDataList.add(new Message("E", "メッセージ1"));
            msgDataList.add(new Message("I", "メッセージ2"));
            message = "リスト";
            expected = "[{\"code\":\"\",\"kind\":\"E\",\"text\":\"メッセージ1\",\"button\":null,\"selector\":\"\"}"
                    + ",{\"code\":\"\",\"kind\":\"I\",\"text\":\"メッセージ2\",\"button\":null,\"selector\":\"\"}]";
            actual = MvcUtil.toJson(msgDataList);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void toJsonNGTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] toJson() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        {
            message = "null";
            expected = "";
            actual = MvcUtil.toJson(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** escapeHtml テスト */
    @Test
    public void escapeHtmlTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] escapeHtml():";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String param1;
        String expected;
        String actual;

        // OK.
        // escapeHtml.
        {
            param1 = "&<>\"'";
            message = "escapeHtml[" + param1 + "]";
            expected = "&amp;&lt;&gt;&quot;&#39;";
            actual = MvcUtil.escapeHtml(param1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // unsanitize.
        {
            param1 = "&amp;&lt;&gt;&quot;&#39;";
            message = "escapeHtml[" + param1 + "]";
            expected = "&<>\"'";
            actual = MvcUtil.unsanitize(param1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        // NG.
        // escapeHtml.
        {
            message = "escapeHtml[null]";
            expected = null;
            actual = MvcUtil.escapeHtml(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "escapeHtml[空]";
            expected = "";
            actual = MvcUtil.escapeHtml("");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // unsanitize.
        {
            message = "unsanitize[null]";
            expected = null;
            actual = MvcUtil.unsanitize(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "unsanitize[空]";
            expected = "";
            actual = MvcUtil.unsanitize("");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }

        System.out.println(testinfo + "**   End ****************");
    }

    /** replaceCLTag テスト */
    @Test
    public void replaceCLTagTest() throws Exception {
        String testinfo = "[ MvcUtil Test ] replaceCLTag():";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String param1;
        String expected;
        String actual;

        // OK.
        {
            param1 = "あいうえお\nかきくけこ\rさしすせそ\r\nたちつてと";
            message = "[" + param1 + "]";
            expected = "あいうえお<br />かきくけこ<br />さしすせそ<br />たちつてと";
            actual = MvcUtil.replaceCLTag(param1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        // NG.
        {
            message = "[null]";
            expected = null;
            actual = MvcUtil.replaceCLTag(null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "[空]";
            expected = "";
            actual = MvcUtil.replaceCLTag("");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }

        System.out.println(testinfo + "**   End ****************");
    }

}
