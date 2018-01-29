package com.res_system.re_employee_manager.commons.model;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.commons.model.entities.LoginInfo;
import com.res_system.re_employee_manager.model.top.TopForm;
import com.res_system.re_employee_manager.model.top.TopModel;

/**
 *
 * TopModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class TopModelTest {

    @Inject
    private TopModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ TopModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ TopModel Test @AfterClass ]");
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


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** checkInput() テスト */
    @Test
    public void checkInputTest() throws Exception {
        String testinfo = "checkInput() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                target.setup();
                TopForm form = new TopForm();
                form.setLogin_id("admin");
                form.setPassword("test");

                msg = "[ OK ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, true, actual);
            }
            {
                target.setup();
                TopForm form = new TopForm();
                form.setLogin_id(
                          "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                        + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                        + "123456789012345678901234567890123456789012345-250-123456");
                form.setPassword(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-123456");
                msg = "[ OK ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, true, actual);
            }
            {
                target.setup();
                TopForm form = new TopForm();
                msg = "[ 必須 ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, "E00011", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00011", target.getMessageList().get(1).getCode());
            }
            {
                target.setup();
                TopForm form = new TopForm();
                form.setLogin_id("ａｄｍｉｎ");
                form.setPassword("ｔｅｓｔ");
                msg = "[ 文字種 ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, "E00013", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00013", target.getMessageList().get(1).getCode());
            }
            {
                target.setup();
                TopForm form = new TopForm();
                form.setLogin_id(
                          "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                        + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                        + "123456789012345678901234567890123456789012345-250-1234567");
                form.setPassword(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-1234567");
                msg = "[ 桁数 ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, "E00015", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00015", target.getMessageList().get(1).getCode());
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** doLogin() テスト */
    @Test
    public void doLoginTest() throws Exception {
        String testinfo = "doLogin() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                TopForm form = new TopForm();
                form.setLogin_id("admin");
                form.setPassword("test");

                msg = "[ login ]";
                boolean actual = target.doLogin(form);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, true, actual);

                LoginInfo loginInfo = authModel.getLoginInfo();
                assertEquals(msg, true, (loginInfo != null));
                SandBox.showObject("  ", loginInfo);

            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
