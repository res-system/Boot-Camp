package com.res_system.re_emp_manager.model.mnt_emp_user;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_emp_manager.SandBox;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.kind.GAcStat;
import com.res_system.re_emp_manager.commons.kind.Sitch;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.entities.MUser;
import com.res_system.re_emp_manager.commons.model.entities.SGrpAccount;
import com.res_system.re_emp_manager.model.mnt_emp_user.MntEmpUserData;
import com.res_system.re_emp_manager.model.mnt_emp_user.MntEmpUserForm;
import com.res_system.re_emp_manager.model.mnt_emp_user.MntEmpUserModel;

/**
 *
 * MntEmpUserModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class MntEmpUserModelTest {

    @Inject
    private MntEmpUserModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ MntEmpUserModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ MntEmpUserModel Test @AfterClass ]"); }
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

            msg = "[ OK(グループ(アカウントあり)) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "test@res-system.com", "test1234", false));
            //-- OK. --//
            {
                msg = testinfo + "[ OK(全検索) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(MntEmpUserData data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(Keywordあり) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.getSearchCond().setKeyword("２");
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(MntEmpUserData data : form.getList()) {
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

    /** findData() テスト */
    @Test
    //@Ignore
    public void findDataTest() throws Exception {
        String testinfo = "findData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));
            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.getData().setId("11");
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkSize() テスト */
    @Test
    @Ignore
    public void checkSizeTest() throws Exception {
        String testinfo = "checkSize() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                boolean actual = target.checkSize(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            {
                //test data
                for (int i = 0; i < 99; i++) {
                    MUser entity = new MUser();
                    entity.setUser_kbn("2");
                    entity.setName("test");
                    entity.setDefault_authority_id("301");
                    entity.setCreated_id("1");
                    entity.setUpdated_id("1");
                    dao.insert(entity);
                    entity.setId(dao.getLastInsertId());

                    SGrpAccount entity2 = new SGrpAccount();
                    entity2.setRoot_group_id(auth.getLogin_root_group_id());
                    entity2.setUser_id(entity.getId());
                    entity2.setDefault_group_id(auth.getLogin_root_group_id());
                    entity2.setGpac_status(GAcStat.ENABLE.getValue());
                    entity2.setCreated_id("1");
                    entity2.setUpdated_id("1");
                    dao.insert(entity2);
                }

                msg = testinfo + "[ NG ]";
                MntEmpUserForm form = new MntEmpUserForm();
                boolean actual = target.checkSize(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
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

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setFirst_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
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
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 5, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_UPD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_UPD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 6, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_DEL) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_DEL);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 6, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(桁 MODE_ADD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdefg");
                entity.setFamily_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                entity.setFirst_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 4, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(種別 MODE_ADD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdeあ");
                entity.setFamily_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setFirst_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setSituation("99");
                entity.setGpac_status("10");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ OK(オーナーチェック MODE_UPD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_UPD);
                MntEmpUserData entity = form.getData();
                entity.setId("12");
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setFirst_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(オーナーチェック MODE_ADD) ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_DEL);
                MntEmpUserData entity = form.getData();
                entity.setId("11");
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setFirst_name("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
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

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));


            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("山田");
                entity.setFirst_name("太郎");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("テストアカウントです。");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.checkInput(form));
                boolean actual = target.insertData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());

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

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            String targetId = "";
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("山田");
                entity.setFirst_name("太郎");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("テストアカウントです。");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.checkInput(form) && target.insertData(form));
                targetId = form.getData().getId();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_UPD);
                MntEmpUserData entity = form.getData();
                entity.setId(targetId);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // update.
                entity = form.getData();
                entity.setId(targetId);
                entity.setSituation(Sitch.ON_LEAVE.getValue());
                entity.setGpac_status(GAcStat.DISABLE.getValue());
                entity.setMemo("上書きシマス。");

                // test.
                target.getMessageList().clear();
                boolean actual = target.updateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", true, target.findData(form));
                SandBox.showObject(msg + "[ after ]", form.getData());
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

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            String targetId = "";
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpUserData entity = form.getData();
                entity.setEmployee_no("1234567890abcdef");
                entity.setFamily_name("山田");
                entity.setFirst_name("太郎");
                entity.setSituation(Sitch.IN_OFFICE.getValue());
                entity.setGpac_status(GAcStat.ENABLE.getValue());
                entity.setMemo("テストアカウントです。");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.checkInput(form) && target.insertData(form));
                targetId = form.getData().getId();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpUserForm form = new MntEmpUserForm();
                form.setMode(CommonModel.MODE_UPD);
                MntEmpUserData entity = form.getData();
                entity.setId(targetId);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // test.
                target.getMessageList().clear();
                boolean actual = target.deleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", false, target.findData(form));
                SandBox.showObject(msg + "[ after ]", form.getData());
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }
}
