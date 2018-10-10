package com.res_system.re_emp_manager.model.emp_family;

import static org.junit.Assert.assertEquals;

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
import com.res_system.re_emp_manager.commons.kind.Living;
import com.res_system.re_emp_manager.commons.kind.Sex;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 *
 * EmpFamilyModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class EmpFamilyModelTest {

    @Inject
    private EmpFamilyModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ EmpFamilyModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ EmpFamilyModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** doFindList() テスト */
    @Test
    //@Ignore
    public void doFindListTest() throws Exception {
        String testinfo = "doFindList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            String targetId = "13";
            //-- OK. --//
            {
                msg = testinfo + "[ OK (" + targetId + ")]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.getData().setUser_id(targetId);
                assertEquals(msg, true, target.doFindList(form));
                for(EmpFamilyData data : form.getList()) {
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** doFindData() テスト */
    @Test
    //@Ignore
    public void doFindDataTest() throws Exception {
        String testinfo = "doFindData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            String targetId = "13";
            //-- OK. --//
            {
                msg = testinfo + "[ OK (" + targetId + ")]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.getData().setUser_id(targetId);
                form.getData().setSeq("1");
                assertEquals(msg, true, target.doFindData(form));
                SandBox.showObject(msg, form.getData());
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** doCheckInput() テスト */
    @Test
    //@Ignore
    public void doCheckInputTest() throws Exception {
        String testinfo = "doCheckInput() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            //-- NG. --//
            {
                msg = testinfo + "[ NG ]";
                EmpFamilyForm form = new EmpFamilyForm();
                EmpFamilyData entity = form.getData();
                entity.setUser_id("");
                entity.setSeq("");
                entity.setRelationship("");
                entity.setLiving("");
                entity.setFamily_name("");
                entity.setFirst_name("");
                entity.setFamily_name_kana("あ");
                entity.setFirst_name_kana("a");
                entity.setSex("a");
                entity.setBirthday("asd");
                entity.setMynumber("sadf");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");

                // test.
                target.getMessageList().clear();
                boolean actual = target.doCheckInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, false, actual);
                assertEquals(msg, 13, target.getMessageList().size());
                System.out.println(msg + " -OK- ");

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** doInsertData() テスト */
    @Test
    //@Ignore
    public void doInsertDataTest() throws Exception {
        String testinfo = "doInsertData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            String targetId = auth.getLogin_user_id();
            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.setMode(CommonModel.MODE_ADD);
                EmpFamilyData entity = form.getData();
                entity.setUser_id(targetId);
                entity.setSeq("");
                entity.setRelationship("子");
                entity.setLiving(Living.SEPARATELY.getValue());
                entity.setFamily_name("山田");
                entity.setFirst_name("花子");
                entity.setFamily_name_kana("ヤマダ");
                entity.setFirst_name_kana("ハナコ");
                entity.setSex(Sex.FEMALE.getValue());
                entity.setBirthday("1996/08/26");
                entity.setMynumber("1234-5678-9012");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");

                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.doCheckInput(form));
                boolean actual = target.doInsertData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg, true, target.doFindData(form));
                SandBox.showObject(msg, form.getData());

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** doUpdateData() テスト */
    @Test
    //@Ignore
    public void doUpdateDataTest() throws Exception {
        String testinfo = "doUpdateData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            String targetId = auth.getLogin_user_id();
            String targetSeq = "";
            {
                msg = testinfo + "[ OK ]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.setMode(CommonModel.MODE_ADD);
                EmpFamilyData entity = form.getData();
                entity.setUser_id(targetId);
                entity.setSeq("");
                entity.setRelationship("子");
                entity.setLiving(Living.SEPARATELY.getValue());
                entity.setFamily_name("山田");
                entity.setFirst_name("花子");
                entity.setFamily_name_kana("ヤマダ");
                entity.setFirst_name_kana("ハナコ");
                entity.setSex(Sex.FEMALE.getValue());
                entity.setBirthday("1996/08/26");
                entity.setMynumber("1234-5678-9012");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.doCheckInput(form) && target.doInsertData(form));
                targetSeq = entity.getSeq();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.setMode(CommonModel.MODE_UPD);
                EmpFamilyData entity = form.getData();
                entity.setUser_id(targetId);
                entity.setSeq(targetSeq);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.doFindData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // update.
                entity = form.getData();
                entity.setRelationship("息子");
                entity.setLiving(Living.TOGETHER.getValue());
                entity.setFamily_name("山田X");
                entity.setFirst_name("花子Z");
                entity.setFamily_name_kana("ヤマダダ");
                entity.setFirst_name_kana("ハナココ");
                entity.setSex(Sex.MALE.getValue());
                entity.setBirthday("1996/08/27");
                entity.setMynumber("1234-5678-9013");
                entity.setMemo("メモ");

                // test.
                target.getMessageList().clear();
                boolean actual = target.doUpdateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", true, target.doFindData(form));
                SandBox.showObject(msg + "[ after ]", form.getData());
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** doDeleteData() テスト */
    @Test
    //@Ignore
    public void doDeleteDataTest() throws Exception {
        String testinfo = "doDeleteData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            String targetId = auth.getLogin_user_id();
            String targetSeq = "";
            {
                msg = testinfo + "[ OK ]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.setMode(CommonModel.MODE_ADD);
                EmpFamilyData entity = form.getData();
                entity.setUser_id(targetId);
                entity.setSeq("");
                entity.setRelationship("子");
                entity.setLiving(Living.SEPARATELY.getValue());
                entity.setFamily_name("山田");
                entity.setFirst_name("花子");
                entity.setFamily_name_kana("ヤマダ");
                entity.setFirst_name_kana("ハナコ");
                entity.setSex(Sex.FEMALE.getValue());
                entity.setBirthday("1996/08/26");
                entity.setMynumber("1234-5678-9012");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.doCheckInput(form) && target.doInsertData(form));
                targetSeq = entity.getSeq();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                EmpFamilyForm form = new EmpFamilyForm();
                form.setMode(CommonModel.MODE_DEL);
                EmpFamilyData entity = form.getData();
                entity.setUser_id(targetId);
                entity.setSeq(targetSeq);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.doFindData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // test.
                target.getMessageList().clear();
                boolean actual = target.doDeleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", false, target.doFindData(form));
                SandBox.showObject(msg + "[ after ]", form.getData());
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
