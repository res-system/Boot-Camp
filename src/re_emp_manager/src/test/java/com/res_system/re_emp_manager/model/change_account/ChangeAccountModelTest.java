package com.res_system.re_emp_manager.model.change_account;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_emp_manager.SandBox;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.kind.SaveFlg;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRAccCooperation;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 *
 * ChangeAccountModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class ChangeAccountModelTest {

    @Inject
    private ChangeAccountModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ ChangeAccountModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ ChangeAccountModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** findList() テスト */
    @Test
    //@Ignore
    public void findListTest() throws Exception {
        String testinfo = "findList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "group-ab@res-system.com", "test1234", false));
            //-- OK. --//
            {
                msg = testinfo + "[ OK(全検索) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRAccCooperation data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(Keywordあり) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.getSearchCond().setKeyword("Ｂ");
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRAccCooperation data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(sort) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.getSearchCond().setSort("2");
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRAccCooperation data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkSize() テスト */
    @Test
    //@Ignore
    public void checkSizeTest() throws Exception {
        String testinfo = "checkSize() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "group-ab@res-system.com", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                ChangeAccountForm form = new ChangeAccountForm();
                boolean actual = target.checkSize(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkInput() テスト */
    @Test
    //@Ignore
    public void checkInputTest() throws Exception {
        String testinfo = "checkInput() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "test@res-system.com", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_ADD);
                form.setCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-1234567.890------------------------------------------------------------------------------------------------------------------------------");
                form.setId("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890-----------------------------------------------------------------------------------------------------------------------------");
                form.setKey("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890-----------------------------------------------------------------------------------------------------------------------------");
                form.setSave(SaveFlg.SAVE.getValue());
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(必須 MODE_ADD) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_ADD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_UPD) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_UPD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(種別 MODE_ADD) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_ADD);
                form.setCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-1234567.890-----------------------------------------------------------------------------------------------------------------------------@");
                form.setId("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------/");
                form.setKey("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------?");
                form.setSave("2");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 4, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(種別 MODE_UPD) ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_UPD);
                form.setCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-1234567.890-----------------------------------------------------------------------------------------------------------------------------@");
                form.setId("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------/");
                form.setKey("ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-@1234567.890----------------------------------------------------------------------------------------------------------------------------?");
                form.setSave("2");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** insertData() テスト */
    @Test
    //@Ignore
    public void insertDataTest() throws Exception {
        String testinfo = "insertData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "test@res-system.com", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_ADD);
                form.setCode("group-b.com");
                form.setId("member05");
                form.setKey("test1234");
                form.setSave(SaveFlg.SAVE.getValue());
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form) && target.insertData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg, data); }
                }

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** updateData() テスト */
    @Test
    //@Ignore
    public void updateDataTest() throws Exception {
        String testinfo = "updateData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "group-ab@res-system.com", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_UPD);

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- after  -]", data); }
                }

                // update.
                form.getSearchCond().setSelected_id("10");
                form.getSearchCond().setSelected_sub_id("16");
                form.setSave(SaveFlg.NO_SAVE.getValue());

                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form) && target.updateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- before -]", data); }
                }

                // update.
                form.getSearchCond().setSelected_id("20");
                form.getSearchCond().setSelected_sub_id("26");
                form.setId("member06");
                form.setKey("test1234");
                form.setSave(SaveFlg.SAVE.getValue());

                // test.
                target.getMessageList().clear();
                actual = target.checkInput(form) && target.updateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- before -]", data); }
                }

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** deleteData() テスト */
    @Test
    //@Ignore
    public void deleteDataTest() throws Exception {
        String testinfo = "deleteData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("", "group-ab@res-system.com", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                ChangeAccountForm form = new ChangeAccountForm();
                form.setMode(CommonModel.MODE_DEL);

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- after  -]", data); }
                }

                // delete.
                form.getSearchCond().setSelected_id("10");
                form.getSearchCond().setSelected_sub_id("16");

                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form) && target.deleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- before -]", data); }
                }

                // delete.
                form.getSearchCond().setSelected_id("20");
                form.getSearchCond().setSelected_sub_id("26");

                // test.
                target.getMessageList().clear();
                actual = target.checkInput(form) && target.deleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                {
                    System.out.println(msg + "【 確認 】");
                    List<AuthRAccCooperation> list = auth.getCoopAccList();
                    for (AuthRAccCooperation data : list) { SandBox.showObject(msg + "[- before -]", data); }
                }

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
