package com.res_system.re_emp_manager.commons;

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

import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.kind.Stat;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;

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
    public static void beforeClass() throws Exception { System.out.println("[ CheckerModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ CheckerModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.
    /**
     * メッセージ確認.
     * @param msg
     * @param checkCode
     * @param targetMessages
     */
    private void checkMessage(String msg, String checkCode, List<Message> targetMessages) {
        if (!ReUtil.isEmpty(checkCode)) {
            assertEquals(msg, true, targetMessages.size() > 0);
            assertEquals(msg, checkCode, targetMessages.get(0).getCode());
            System.out.println(msg + " -OK- msg:" + targetMessages.get(0).getText());
        } else {
            assertEquals(msg, 0, targetMessages.size());
            System.out.println(msg + " -OK- ");
        }
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** checkRequired() テスト */
    @Test
    public void checkRequiredTest() throws Exception {
        String testinfo = "checkRequired():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "a";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkRequired(value, "必須"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkRequired(value, "必須"));
            checkMessage(msg, "E00020", messageList);
        }
        {
            value = null;
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkRequired(value, "必須"));
            checkMessage(msg, "E00020", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkSelected() テスト */
    @Test
    public void checkSelectedTest() throws Exception {
        String testinfo = "checkSelected():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "a";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSelected(value, "必須"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSelected(value, "必須"));
            checkMessage(msg, "E00021", messageList);
        }
        {
            value = null;
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSelected(value, "必須"));
            checkMessage(msg, "E00021", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkMatch() テスト */
    @Test
    public void checkMatchTest() throws Exception {
        String testinfo = "checkMatch():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "090-1234-5678";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkMatch(value, "^0\\d+-\\d+-\\d{4}$", "パターンチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkMatch(value, "^0\\d+-\\d+-\\d{4}$", "パターンチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkMatch(value, "^0\\d+-\\d+-\\d{4}$", "パターンチェック"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "abcd";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkMatch(value, "^0\\d+-\\d+-\\d{4}$", "パターンチェック"));
            checkMessage(msg, "E00031", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkLength() テスト */
    @Test
    public void checkLengthTest() throws Exception {
        String testinfo = "checkLength():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "1234567890";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "abcd";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 4, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "abcde67890";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 10, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "abcdefghij";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 4, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkLength(value, 10, "サイズチェック"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "12345678901";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkLength(value, 10, "サイズチェック"));
            checkMessage(msg, "E00035", messageList);
        }
        {
            value = "abcde6789";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkLength(value, 10, 10, "サイズチェック"));
            checkMessage(msg, "E00036", messageList);
        }
        {
            value = "abcde678901";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkLength(value, 10, 10, "サイズチェック"));
            checkMessage(msg, "E00036", messageList);
        }
        {
            value = "abc";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkLength(value, 4, 10, "サイズチェック"));
            checkMessage(msg, "E00037", messageList);
        }
        {
            value = "abcdefghijk";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkLength(value, 4, 10, "サイズチェック"));
            checkMessage(msg, "E00037", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkAscii() テスト */
    @Test
    public void checkAsciiTest() throws Exception {
        String testinfo = "checkAscii():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "1234567890";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAscii(value, "半角チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAscii(value, "半角チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAscii(value, "半角チェック"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "１２３４５６７８９０";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAscii(value, "半角チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkNumeric() テスト */
    @Test
    public void checkNumericTest() throws Exception {
        String testinfo = "checkNumeric():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "12341235";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "-1234.1235";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "１２３４５";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "abc";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumeric(value, "数値チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkNumber() テスト */
    @Test
    public void checkNumberTest() throws Exception {
        String testinfo = "checkNumber():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "12341235";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "-1,234.1235";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumber(value, "\\.,-", "数字チェック", "(ドット(.)とカンマ(,)とハイフォン(-)を含む)"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "abc";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "１２３４５";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "-1,234.1235";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumber(value, "数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "@-1,234.1235";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumber(value, "\\.,-", "数字チェック", "(ドット(.)とカンマ(,)とハイフォン(-)を含む)"));
            checkMessage(msg, "E00033", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkAlpha() テスト */
    @Test
    public void checkAlphaTest() throws Exception {
        String testinfo = "checkAlpha():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "abcdefghij";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "ab-cd@ef_gh";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlpha(value, "@\\-_", "英字チェック", "(アットマーク(@)とハイフォン(-)とアンダーバー(_)を含む)"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "123";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ＡＢＣＤＥ";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ab-cd@ef_gh";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlpha(value, "英字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ab-cd@ef_gh!";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlpha(value, "@\\-_", "英字チェック", "(アットマーク(@)とハイフォン(-)とアンダーバー(_)を含む)"));
            checkMessage(msg, "E00033", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkAlphaNumber() テスト */
    @Test
    public void checkAlphaNumberTest() throws Exception {
        String testinfo = "checkAlphaNumber():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "abcdefghij1234567890";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "ab-cdef_gh@com1.123.jp";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkAlphaNumber(value, "@\\-_\\.", "英数字チェック", "(アットマーク(@)とハイフォン(-)とアンダーバー(_)を含む)"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "１２３";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ＡＢＣＤＥ";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ab-cdef_gh@com1.123.jp";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlphaNumber(value, "英数字チェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "ab-cdef_gh@com1.123.jp!";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkAlphaNumber(value, "@\\-_\\.", "英数字チェック", "(アットマーク(@)とハイフォン(-)とアンダーバー(_)を含む)"));
            checkMessage(msg, "E00033", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkKana() テスト */
    @Test
    public void checkKanaTest() throws Exception {
        String testinfo = "checkKana():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "コンピューター";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "トンカツ (ニクアツ)";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkKana(value, "() ", "カナチェック", "(「(」「)」と半角スペースを含む)"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "ｱｲｳｴｵ";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "あいうえお";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "トンカツ (ニクアツ)";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkKana(value, "カナチェック"));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "トンカツ　(ニクアツ)";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkKana(value, "() ", "カナチェック", "(「(」「)」と半角スペースを含む)"));
            checkMessage(msg, "E00033", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkNumericSize() テスト */
    @Test
    public void checkNumericSizeTest() throws Exception {
        String testinfo = "checkNumericSize():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "123.123";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumericSize(value, "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "0.1";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumericSize(value, "0.1", "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "abc";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumericSize(value, "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumericSize(value, "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNumericSize(value, "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "123.124";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumericSize(value, "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "E00050", messageList);
        }
        {
            value = "0.099";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNumericSize(value, "0.1", "123.123", "数値サイズチェック", selector));
            checkMessage(msg, "E00051", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkNotZero() テスト */
    @Test
    public void checkNotZeroTest() throws Exception {
        String testinfo = "checkNotZero():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "1";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "0.01";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "0.0";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "E00052", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkNotMinus() テスト */
    @Test
    public void checkNotMinusTest() throws Exception {
        String testinfo = "checkNotMinus():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "1";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "0.01";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "0.0";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkNotZero(value, "0チェック", selector));
            checkMessage(msg, "E00052", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkDecimalSize() テスト */
    @Test
    public void checkDecimalSizeTest() throws Exception {
        String testinfo = "checkDecimalSize():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "123.12";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "123";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "123.123";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "E00054", messageList);
        }
        {
            value = "1234.12";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkDecimalSize(value, 3, 2, "小数点桁数チェック", selector));
            checkMessage(msg, "E00054", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkSmallerNum() テスト */
    @Test
    public void checkSmallerNumTest() throws Exception {
        String testinfo = "checkSmallerNum():";
        System.out.println(testinfo + "** Start ****************");
        String value1 = "";
        String value2 = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value1 = "123.123";
            value2 = "123.124";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSmallerNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = "";
            value2 = "";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSmallerNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = null;
            value2 = null;
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSmallerNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value1 = "123.124";
            value2 = "123.123";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSmallerNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "E00055", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkGreaterNum() テスト */
    @Test
    public void checkGreaterNum() throws Exception {
        String testinfo = "checkGreaterNum():";
        System.out.println(testinfo + "** Start ****************");
        String value1 = "";
        String value2 = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value1 = "123";
            value2 = "124";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkGreaterNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = "123.123";
            value2 = "123.124";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkGreaterNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = "";
            value2 = "";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkGreaterNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = null;
            value2 = null;
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkGreaterNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value1 = "123.124";
            value2 = "123.123";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkGreaterNum(value1, value2, "数値大小チェック1", "数値大小チェック2", selector));
            checkMessage(msg, "E00056", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkDateFormat() テスト */
    @Test
    public void checkDateFormatTest() throws Exception {
        String testinfo = "checkDateFormat():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "2018/03/29";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "03/29";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateFormat(value, "MM/dd", "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "03/29";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkDateFormat(value, "日付チェック", selector));
            checkMessage(msg, "E00032", messageList);
        }
        {
            value = "4/3/29";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkDateFormat(value, "MM/dd", "日付チェック", selector));
            checkMessage(msg, "E00032", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkDateTimeFormat() テスト */
    @Test
    public void checkDateTimeFormatTest() throws Exception {
        String testinfo = "checkDateTimeFormat():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "2018/03/29 17:21:09";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateTimeFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateTimeFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkDateTimeFormat(value, "日付チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "2018/03/29";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkDateTimeFormat(value, "日付チェック", selector));
            checkMessage(msg, "E00032", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkTimeFormat() テスト */
    @Test
    public void checkTimeFormatTest() throws Exception {
        String testinfo = "checkTimeFormat():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value = "12:45:12";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkTimeFormat(value, "時刻チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkTimeFormat(value, "時刻チェック", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkTimeFormat(value, "時刻チェック", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "25:45:12";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkTimeFormat(value, "時刻チェック", selector));
            checkMessage(msg, "E00032", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkPastDate() テスト */
    @Test
    public void checkPastDateTest() throws Exception {
        String testinfo = "checkPastDate():";
        System.out.println(testinfo + "** Start ****************");
        String value1 = "";
        String value2 = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value1 = "2018/12/12";
            value2 = "2018/12/24";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkPastDate(value1, value2, "過去日チェック1", "過去日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = "";
            value2 = "";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkPastDate(value1, value2, "過去日チェック1", "過去日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = null;
            value2 = null;
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkPastDate(value1, value2, "過去日チェック1", "過去日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value1 = "2018/12/24";
            value2 = "2018/12/12";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkPastDate(value1, value2, "過去日チェック1", "過去日チェック2", selector));
            checkMessage(msg, "E00061", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** checkFutureDate() テスト */
    @Test
    public void checkFutureDateTest() throws Exception {
        String testinfo = "checkFutureDate():";
        System.out.println(testinfo + "** Start ****************");
        String value1 = "";
        String value2 = "";
        String msg = "";
        String selector = "selector";
        // -- OK -- //
        {
            value1 = "2018/12/24";
            value2 = "2018/12/12";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkFutureDate(value1, value2, "未来日チェック1", "未来日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = "";
            value2 = "";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkFutureDate(value1, value2, "未来日チェック1", "未来日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        {
            value1 = null;
            value2 = null;
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkFutureDate(value1, value2, "未来日チェック1", "未来日チェック2", selector));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value1 = "2018/12/12";
            value2 = "2018/12/24";
            msg = testinfo + "OK[" + value1 + ":" + value2 + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkFutureDate(value1, value2, "未来日チェック1", "未来日チェック2", selector));
            checkMessage(msg, "E00062", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkItemList() テスト */
    @Test
    public void checkItemListTest() throws Exception {
        String testinfo = "checkItemList():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "1";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkItemList(value, Stat.values(), "種別チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkItemList(value, Stat.values(), "種別チェック"));
            checkMessage(msg, "", messageList);
        }
        {
            value = null;
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkItemList(value, Stat.values(), "種別チェック"));
            checkMessage(msg, "", messageList);
        }
        // -- NG -- //
        {
            value = "2";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkItemList(value, Stat.values(), "種別チェック"));
            checkMessage(msg, "E00040", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** checkHalfText() テスト */
    @Test
    public void checkHalfTextTest() throws Exception {
        String testinfo = "checkHalfText():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "abc123@";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfText(value, true, 7, "半角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfText(value, false, 7, "半角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfText(value, true, 7, "半角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "abc-123@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfText(value, true, 7, "半角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "abc12３@";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfText(value, true, 7, "半角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkFullText() テスト */
    @Test
    public void checkFullTextTest() throws Exception {
        String testinfo = "checkFullText():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "Ａbc１23@";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 7, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, false, 7, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 7, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "Ａbc１23@x";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 7, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // ▼NEC機種依存文字:13区
        {
            value = "Ａbc[①]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "Ａbc[㎏]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
        }
        // NECのIBM拡張文字:89区
        {
            value = "Ａbc[侊]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // NECのIBM拡張文字:90区
        {
            value = "Ａbc[犱]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // NECのIBM拡張文字:91区
        {
            value = "Ａbc[罇]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // NECのIBM拡張文字:92区
        {
            value = "Ａbc[霳]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // IBM拡張文字:115区
        {
            value = "Ａbc[彅]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "Ａbc[￤]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // IBM拡張文字:116区
        {
            value = "Ａbc[嵭]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // IBM拡張文字:117区
        {
            value = "Ａbc[琇]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // IBM拡張文字:118区
        {
            value = "Ａbc[詹]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        // IBM拡張文字:119区
        {
            value = "Ａbc[鮏]１23@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullText(value, true, 10, "全角項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
    }

    /** checkFullKanaText() テスト */
    @Test
    public void checkFullKanaTextTest() throws Exception {
        String testinfo = "checkFullKanaText():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "アイウ（ (オカキ)　）";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullKanaText(value, true, 12, "全角項目カナチェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullKanaText(value, false, 12, "全角項目カナチェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullKanaText(value, true, 7, "全角項目カナチェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "アイウ（ (オカキ)　 ）";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullKanaText(value, true, 7, "全角項目カナチェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "アＸウ（ (オカキ)　 ）";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkFullKanaText(value, true, 7, "全角項目カナチェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkKind() テスト */
    @Test
    public void checkKindTest() throws Exception {
        String testinfo = "checkKind():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "1";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkKind(value, true, Stat.values(), "種別項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkKind(value, false, Stat.values(), "種別項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkKind(value, true, Stat.values(), "種別項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "2";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkKind(value, true, Stat.values(), "種別項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkDate() テスト */
    @Test
    public void checkDateTest() throws Exception {
        String testinfo = "checkDate():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "2018/03/29";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkDate(value, true, "日付項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkDate(value, false, "日付項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkDate(value, true, "日付項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "2018/02/29";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkDate(value, true, "日付項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkTime() テスト */
    @Test
    public void checkTimeTest() throws Exception {
        String testinfo = "checkTime():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        String name = "時刻項目チェック";
        String selector = "selector";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "12:45";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "1245";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, false, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "asd:asd";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "25:32";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "09:60";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "9:12";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTime(value, true, name, selector);
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkHalfNum() テスト */
    @Test
    public void checkHalfNumTest() throws Exception {
        String testinfo = "checkHalfNum():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "1234567";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, false, 7, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "1234-56";
            msg = testinfo + "OK[例外(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "-", "半角項目数字チェック", "(「-」を含む)", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "12345678";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "1234-56";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "12X4-56";
            msg = testinfo + "NG[例外(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNum(value, true, 7, "-", "半角項目数字チェック", "(「-」を含む)", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkHalfAlpNum() テスト */
    @Test
    public void checkHalfAlpNumTest() throws Exception {
        String testinfo = "checkHalfAlpNum():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "A1234567";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, false, 8, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "A1234-56";
            msg = testinfo + "OK[例外(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "-", "半角項目数字チェック", "(「-」を含む)", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "A12345678";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "A1234-56";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "半角項目数字チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "A12!4-56";
            msg = testinfo + "NG[例外(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfAlpNum(value, true, 8, "-", "半角項目数字チェック", "(「-」を含む)", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** checkHalfNumeric() テスト */
    @Test
    public void checkHalfNumericTest() throws Exception {
        String testinfo = "checkHalfNumeric():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "99999";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, false, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "1";
            msg = testinfo + "NG[範囲(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 10, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "100000";
            msg = testinfo + "NG[範囲(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 10, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "1.5";
            msg = testinfo + "NG[小数点(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "-15";
            msg = testinfo + "NG[マイナス(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "123-6";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkHalfNumeric(value, true, 0, 99999, "半角項目数値チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** checkPostalCode() テスト */
    @Test
    public void checkPostalCodeTest() throws Exception {
        String testinfo = "checkPostalCode():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "1234567";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, false, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "123456";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "12345678";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "123-4567";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "123-456";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "123X456";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPostalCode(value, true, "郵便番号項目チェック", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkTelNo() テスト */
    @Test
    public void checkTelNoTest() throws Exception {
        String testinfo = "checkTelNo():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "01-2345-6789";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "012-3456-7890";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "0123-45-6789";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "0123-456-7890";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, false, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "0123-4567-8901";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "0X2-3456-7890";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "０１２-3456-7890";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "112-3456-7890";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "012-345-67890";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkTelNo(value, true, "電話番号項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkEmail() テスト */
    @Test
    public void checkEmailTest() throws Exception {
        String testinfo = "checkEmail():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij.123"
                    + "@"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + ".78901234";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, false, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij.123"
                    + "@"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + ".789012345";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "ＡbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij.123"
                    + "@"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + ".78901234";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij.123"
                    + ""
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + ".78901234";
            msg = testinfo + "NG[種別(@なし)(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "Abcdefghij.123"
                    + "@@"
                    + "Abcdefghij.123";
            msg = testinfo + "NG[種別(@重複)(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij.1234"
                    + "@"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij"
                    + ".7890123";
            msg = testinfo + "NG[種別(ローカル部オーバー)(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkEmail(value, true, "メールアドレス項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkLoginId() テスト */
    @Test
    public void checkLoginIdTest() throws Exception {
        String testinfo = "checkLoginId():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkLoginId(value, true, "ログインID項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkLoginId(value, false, "ログインID項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkLoginId(value, true, "ログインID項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkLoginId(value, true, "ログインID項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "ＡBCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkLoginId(value, true, "ログインID項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkPassword() テスト */
    @Test
    public void checkPasswordTest() throws Exception {
        String testinfo = "checkPassword():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";

        List<Message> messageList = new ArrayList<>();
        target.setMessageList(messageList);

        // -- OK -- //
        {
            value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@";
            msg = testinfo + "OK[" + value + "]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPassword(value, true, "パスワード項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "";
            msg = testinfo + "OK[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPassword(value, false, "パスワード項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, true, actual);
            System.out.println(msg + " -OK- ");
        }
        // -- NG -- //
        {
            value = "";
            msg = testinfo + "NG[必須(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPassword(value, true, "パスワード項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@@";
            msg = testinfo + "NG[長さ(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPassword(value, true, "パスワード項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        {
            value = "ＡBCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------@";
            msg = testinfo + "NG[種別(" + value + ")]";
            // テスト.
            target.getMessageList().clear();
            boolean actual = target.checkPassword(value, true, "パスワード項目", "selector");
            for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
            assertEquals(msg, false, actual);
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkSpace() テスト */
    @Test
    public void checkSpaceTest() throws Exception {
        String testinfo = "checkSpace():";
        System.out.println(testinfo + "** Start ****************");
        String value = "";
        String msg = "";
        // -- OK -- //
        {
            value = "abcd";
            msg = testinfo + "OK[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "", messageList);
        }
        {
            value = "";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, true, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "", messageList);
        }

        // -- NG -- //
        {
            value = "abcd efg";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "E00016", messageList);
        }
        {
            value = "abcd　efg";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "E00016", messageList);
        }
        {
            value = "abcd\tefg";
            msg = testinfo + "NG[" + value + "]";
            List<Message> messageList = new ArrayList<>();
            target.setMessageList(messageList);
            // テスト.
            assertEquals(msg, false, target.checkSpace(value, false, "スペース項目", "space_item"));
            checkMessage(msg, "E00016", messageList);
        }
        System.out.println(testinfo + "**   End ****************");
    }


}
