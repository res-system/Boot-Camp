package com.res_system.commons.mvc.view.thexpressionobjects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.res_system.commons.mvc.model.IListItem;

/**
 *
 * ReThHelper テストクラス.
 *
 * @author res.
 *
 */
public class ReThHelperTest {

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

    class ListData implements IListItem {
        private String value;
        private String text;
        @Override
        public String getValue() { return value; }
        @Override
        public String getText() { return text; }
        @Override
        public boolean getDisabled() { return false; }

        public ListData(String value, String text) {
            this.value = value;
            this.text = text;
        }
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** name テスト */
    @Test
    public void nameOKTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] name() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            message = "直";
            expected = "name";
            actual = target.name("form", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data.name";
            actual = target.name("form", "data", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list[0].name";
            actual = target.name("form", "list", 0, "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void nameNGTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] name() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            message = "直";
            expected = "";
            actual = target.name(null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = ".";
            actual = target.name(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "[0].";
            actual = target.name(null, null, 0, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** id テスト */
    @Test
    public void idOKTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] id() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        // id
        {
            message = "直";
            expected = "name";
            actual = target.id("form", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data_name";
            actual = target.id("form", "data", "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list_0_name";
            actual = target.id("form", "list", 0, "name");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // idWithValue
        {
            message = "直";
            expected = "name_value";
            actual = target.idWithValue("form", "name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "data_name_value";
            actual = target.idWithValue("form", "data", "name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "list_0_name_value";
            actual = target.idWithValue("form", "list", 0, "name", "value");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void getIdNGTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] id() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        // id
        {
            message = "直";
            expected = "";
            actual = target.id(null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "_";
            actual = target.id(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "_0_";
            actual = target.id(null, null, 0, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // idWithValue
        {
            message = "直";
            expected = "_";
            actual = target.idWithValue(null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "データ";
            expected = "__";
            actual = target.idWithValue(null, null, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            message = "リスト";
            expected = "_0__";
            actual = target.idWithValue(null, null, 0, null, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** list テスト */
    @Test
    public void listOKTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] list() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<String> value = new ArrayList<>();
            value.add("listValue");
            message = "通常";
            expected = "listValue";
            actual = target.list(value, 0);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void listNGTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] list() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<String> value = null;
            message = "null";
            expected = "";
            actual = target.list(value, 0);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            value.add("listValue");
            message = "Index不正";
            expected = "";
            actual = target.list(value, 1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
            actual = target.list(value, -1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            value.add(null);
            message = "値がNULL";
            expected = "";
            actual = target.list(value, 0);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** listValue テスト */
    @Test
    public void listValueOKTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] listValue() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "通常";
            expected = "value3";
            actual = target.listValue(value, 2);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void listValueNGTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] listValue() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<IListItem> value = null;
            message = "null";
            expected = "";
            actual = target.listValue(value, 0);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "Index不正";
            expected = "";
            actual = target.listValue(value, 3);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
            actual = target.listValue(value, -1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(null);
            value.add(new ListData(null, null));
            message = "値がNULL";
            expected = "";
            actual = target.listValue(value, 0);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
            actual = target.listValue(value, 1);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** listText テスト */
    @Test
    public void listTextOKTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] listText() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "通常";
            expected = "text2";
            actual = target.listText(value, "value2");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    @Test
    public void listTextNGTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] listText() [ NG ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        String expected;
        String actual;
        ReThHelper target = new ReThHelper();
        {
            List<IListItem> value = null;
            message = "null";
            expected = "";
            actual = target.listText(value, "value2");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "value不正";
            expected = "";
            actual = target.listText(value, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
            actual = target.listText(value, "value4");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(null);
            value.add(new ListData("value1", null));
            message = "値がNULL";
            expected = "";
            actual = target.listText(value, "value1");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }


        System.out.println(testinfo + "**   End ****************");
    }

    /** isExists テスト */
    @Test
    public void isExistsTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] isExists() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        boolean expected;
        boolean actual;
        ReThHelper target = new ReThHelper();
        {
            List<String> value = new ArrayList<>();
            value.add("value1");
            value.add("value2");
            value.add("value3");
            message = "あり";
            expected = true;
            actual = target.isExists(value, "value2");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            value.add("value1");
            value.add("value2");
            value.add("value3");
            message = "なし";
            expected = false;
            actual = target.isExists(value, "value4");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = null;
            message = "リストNULL";
            expected = false;
            actual = target.isExists(value, "value4");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            value.add(null);
            value.add(null);
            value.add(null);
            message = "値NULL";
            expected = false;
            actual = target.isExists(value, "value1");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            value.add("value1");
            value.add("value2");
            value.add("value3");
            message = "対象値NULL";
            expected = false;
            actual = target.isExists(value, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    /** isExistsItem テスト */
    @Test
    public void isExistsItemTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] isExistsItem() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        boolean expected;
        boolean actual;
        ReThHelper target = new ReThHelper();
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "あり";
            expected = true;
            actual = target.isExistsItem(value, "value2");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "なし";
            expected = false;
            actual = target.isExistsItem(value, "value4");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = null;
            message = "リストNULL";
            expected = false;
            actual = target.isExistsItem(value, "value1");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData(null, null));
            value.add(new ListData(null, null));
            value.add(new ListData(null, null));
            message = "値NULL";
            expected = false;
            actual = target.isExistsItem(value, "value1");
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "対象値NULL";
            expected = false;
            actual = target.isExistsItem(value, null);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** isEmpty テスト */
    @Test
    public void isEmptyTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] isEmpty() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        boolean expected;
        boolean actual;
        ReThHelper target = new ReThHelper();
        // String
        {
            String value = "aaa";
            message = "文字あり";
            expected = false;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            String value = "";
            message = "文字なし";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            String value = null;
            message = "文字NULL";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // List<String>
        {
            List<String> value = new ArrayList<>();
            value.add("value1");
            value.add("value2");
            value.add("value3");
            message = "リストあり";
            expected = false;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            message = "リストなし";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = null;
            message = "リストNULL";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // List<ListItem>
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "リストあり";
            expected = false;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            message = "リストなし";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = null;
            message = "リストNULL";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // Map<>
        {
            Map<String, String> value = new HashMap<>();
            value.put("value1", "text1");
            value.put("value2", "text2");
            value.put("value3", "text3");
            message = "Mapあり";
            expected = false;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            Map<String, String> value = new HashMap<>();
            message = "Mapなし";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            Map<String, String> value = null;
            message = "MapNULL";
            expected = true;
            actual = target.isEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }
    /** isNotEmpty テスト */
    @Test
    public void isNotEmptyTest() throws Exception {
        String testinfo = "[ ReThHelper Test ] isNotEmpty() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");
        String message;
        boolean expected;
        boolean actual;
        ReThHelper target = new ReThHelper();
        // String
        {
            String value = "aaa";
            message = "文字あり";
            expected = true;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            String value = "";
            message = "文字なし";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            String value = null;
            message = "文字NULL";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // List<String>
        {
            List<String> value = new ArrayList<>();
            value.add("value1");
            value.add("value2");
            value.add("value3");
            message = "リストあり";
            expected = true;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = new ArrayList<>();
            message = "リストなし";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<String> value = null;
            message = "リストNULL";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // List<ListItem>
        {
            List<IListItem> value = new ArrayList<>();
            value.add(new ListData("value1", "text1"));
            value.add(new ListData("value2", "text2"));
            value.add(new ListData("value3", "text3"));
            message = "リストあり";
            expected = true;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = new ArrayList<>();
            message = "リストなし";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            List<IListItem> value = null;
            message = "リストNULL";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        // Map<>
        {
            Map<String, String> value = new HashMap<>();
            value.put("value1", "text1");
            value.put("value2", "text2");
            value.put("value3", "text3");
            message = "Mapあり";
            expected = true;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            Map<String, String> value = new HashMap<>();
            message = "Mapなし";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        {
            Map<String, String> value = null;
            message = "MapNULL";
            expected = false;
            actual = target.isNotEmpty(value);
            assertEquals(message, expected, actual);
            System.out.println(message + "[OK]:" + actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
