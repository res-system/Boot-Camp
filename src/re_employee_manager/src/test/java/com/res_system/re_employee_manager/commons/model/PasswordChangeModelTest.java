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
import com.res_system.re_employee_manager.model.password_change.PasswordChangeEntity;
import com.res_system.re_employee_manager.model.password_change.PasswordChangeForm;
import com.res_system.re_employee_manager.model.password_change.PasswordChangeModel;

/**
 *
 * PasswordChangeModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class PasswordChangeModelTest {

    @Inject
    private PasswordChangeModel target;

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
        System.out.println("[ PasswordChangeModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ PasswordChangeModel Test @AfterClass ]");
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
    private void setAuth(String login_id, String password) throws Exception {
        authModel.doLogin(login_id, password, false);
    }


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
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword("test");
                form.setNew_password("new_test");
                form.setConfirmation_password("new_test");

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
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-123456");
                form.setNew_password(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-123456");
                form.setConfirmation_password(
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

            // NG.
            {
                target.setup();
                PasswordChangeForm form = new PasswordChangeForm();
                msg = "[ 必須 ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, "E00011", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00011", target.getMessageList().get(1).getCode());
                assertEquals(msg, "E00011", target.getMessageList().get(2).getCode());
            }
            {
                target.setup();
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword("ｔｅｓｔ");
                form.setNew_password("ｔｅｓｔ");
                form.setConfirmation_password("ｔｅｓｔ");
                msg = "[ 文字種 ]";
                boolean actual = target.checkInput(form);
                System.out.println(testinfo + msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, "E00013", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00013", target.getMessageList().get(1).getCode());
                assertEquals(msg, "E00013", target.getMessageList().get(2).getCode());
            }
            {
                target.setup();
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-1234567");
                form.setNew_password(
                        "1234567890123456789012345678901234567890123456-50-123456789012345678901234567890123456789012345-100-"
                      + "123456789012345678901234567890123456789012345-150-123456789012345678901234567890123456789012345-200-"
                      + "123456789012345678901234567890123456789012345-250-1234567");
                form.setConfirmation_password(
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
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, "E00015", target.getMessageList().get(0).getCode());
                assertEquals(msg, "E00015", target.getMessageList().get(1).getCode());
                assertEquals(msg, "E00015", target.getMessageList().get(2).getCode());
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** changePassword() テスト */
    @Test
    public void changePasswordTest() throws Exception {
        String testinfo = "changePassword() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            setAuth("employeeA", "test");
            {
                target.setup();
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword("testX");
                form.setNew_password("new_test");
                form.setConfirmation_password("new_test");

                msg = testinfo + "[ NG ]";
                boolean actual = target.changePassword(form);
                System.out.println(msg);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, "E01004", target.getMessageList().get(0).getCode());
            }
            {
                PasswordChangeForm form = new PasswordChangeForm();
                form.setPassword("test");
                form.setNew_password("new_test");
                form.setConfirmation_password("new_test");

                msg = testinfo + "[ OK ]";
                boolean actual = target.changePassword(form);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, true, actual);

                // 確認.
                PasswordChangeEntity entity = dao.find(PasswordChangeEntity.class
                        , "find_password"
                        , (st) -> {
                            st.setLong(1, authModel.getAccountId());
                            st.setInt(2, 1); });
                assertEquals(msg, true, (entity != null));
                System.out.println(msg + " -OK- ");
                SandBox.showObject("  ", entity);
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
