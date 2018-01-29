package com.res_system.re_employee_manager.commons.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 *
 * CheckerModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class CheckerModelTest {

    @Inject
    private CheckerModel target;

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
    /** checkSmallerNum() テスト */
    @Test
    public void checkSmallerNumTest() throws Exception {
        String testinfo = "[ CheckerModel Test ] checkSmallerNum() テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "数値の大小チェックを行います.（小さい数値）";
        {
            String msg = title + "[ 10:11 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkSmallerNum("10", "11", "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ 11:10 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkSmallerNum("11", "10", "数値1", "数値2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00035", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 10:10 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkSmallerNum("10", "10", "数値1", "数値2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00035", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ null:null ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkSmallerNum(null, null, "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ abc:dfg ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkSmallerNum("abc", "dfg", "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkGreaterNum() テスト */
    @Test
    public void checkGreaterNumTest() throws Exception {
        String testinfo = "[ CheckerModel Test ] checkGreaterNum() テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "数値の大小チェックを行います.（大きい数値）";
        {
            String msg = title + "[ 11:10 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkGreaterNum("11", "10", "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ 10:11 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkGreaterNum("10", "11", "数値1", "数値2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00036", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 10:10 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkGreaterNum("10", "10", "数値1", "数値2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00036", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ null:null ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkGreaterNum(null, null, "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ abc:dfg ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkGreaterNum("abc", "dfg", "数値1", "数値2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkPastDate() テスト */
    @Test
    public void checkPastDateTest() throws Exception {
        String testinfo = "[ CheckerModel Test ] checkPastDate() テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "過去日チェックを行います.";
        {
            String msg = title + "[ 2017/10/31:2017/11/01 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate("2017/10/31", "2017/11/01", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ 2017/11/01:2017/10/31 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate("2017/11/01", "2017/10/31", "日付1", "日付2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00051", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 2017/10/31:2017/10/31 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate("2017/10/31", "2017/10/31", "日付1", "日付2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00051", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 2017/10/31 09:30:30:2017/10/31 12:30:30 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate("2017/10/31 09:30:30", "2017/10/31 12:30:30", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ null:null ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate(null, null, "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ abc:dfg ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkPastDate("abc", "dfg", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkFutureDate() テスト */
    @Test
    public void checkFutureDateTest() throws Exception {
        String testinfo = "[ CheckerModel Test ] checkFutureDate() テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "未来日チェックを行います.";
        {
            String msg = title + "[ 2017/11/01:2017/10/31 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate("2017/11/01", "2017/10/31", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ 2017/10/31:2017/11/01 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate("2017/10/31", "2017/11/01", "日付1", "日付2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00052", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 2017/10/31:2017/10/31 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate("2017/10/31", "2017/10/31", "日付1", "日付2");
            assertEquals(msg, false, result);
            assertEquals(msg, 1, messageList.size());
            assertEquals(msg, "E00052", messageList.get(0).getCode());
            System.out.println(msg + " -OK- :" + messageList.get(0).getText());
        }
        {
            String msg = title + "[ 2017/10/31 12:30:30:2017/10/31 09:30:30 ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate("2017/10/31 12:30:30", "2017/10/31 09:30:30", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ null:null ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate(null, null, "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = title + "[ abc:dfg ]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            boolean result = target.checkFutureDate("abc", "dfg", "日付1", "日付2");
            assertEquals(msg, true, result);
            assertEquals(msg, 0, messageList.size());
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
