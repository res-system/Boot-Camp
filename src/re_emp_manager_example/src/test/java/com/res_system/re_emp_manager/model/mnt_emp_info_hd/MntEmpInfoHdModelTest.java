package com.res_system.re_emp_manager.model.mnt_emp_info_hd;

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
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.kind.ReqFlg;
import com.res_system.re_emp_manager.commons.kind.Stat;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 *
 * MntEmpInfoHdModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class MntEmpInfoHdModelTest {

    @Inject
    private MntEmpInfoHdModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ MntEmpInfoHdModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ MntEmpInfoHdModel Test @AfterClass ]"); }
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
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));

            // test data.
            {
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                for (int i = 0, imax = 10; i < imax; i++) {
                    MntEmpInfoHdData data = new MntEmpInfoHdData();
                    data.setSeq(String.valueOf(i));
                    data.setLabel("test - " + i );
                    data.setType(InfType.TEXT.getValue());
                    data.setRequired(ReqFlg.REQUIRED.getValue());
                    data.setMaxlength("10");
                    data.setStatus(Stat.ENABLE.getValue());
                    form.setData(data);
                    assertEquals(msg, true, target.insertData(form));
                }
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK(全検索) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(MntEmpInfoHdData data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(Keywordあり) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.getSearchCond().setKeyword("２");
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(MntEmpInfoHdData data : form.getList()) {
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

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            // test data.
            String targetId = "";
            {
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                for (int i = 0, imax = 10; i < imax; i++) {
                    MntEmpInfoHdData data = new MntEmpInfoHdData();
                    data.setSeq(String.valueOf(i));
                    data.setLabel("test - " + i );
                    data.setType(InfType.TEXT.getValue());
                    data.setRequired(ReqFlg.REQUIRED.getValue());
                    data.setMaxlength("10");
                    data.setStatus(Stat.ENABLE.getValue());
                    form.setData(data);
                    assertEquals(msg, true, target.insertData(form));
                    targetId = form.getData().getId();
                }
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK (" + targetId + ")]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.getData().setId(targetId);
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

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                boolean actual = target.checkSize(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            {
                // test data.
                {
                    MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                    for (int i = 0, imax = 10; i < imax; i++) {
                        MntEmpInfoHdData data = new MntEmpInfoHdData();
                        data.setSeq(String.valueOf(i));
                        data.setLabel("test - " + i );
                        data.setType(InfType.TEXT.getValue());
                        data.setRequired(ReqFlg.REQUIRED.getValue());
                        data.setMaxlength("10");
                        data.setStatus(Stat.ENABLE.getValue());
                        form.setData(data);
                        assertEquals(msg, true, target.insertData(form));
                    }
                }

                msg = testinfo + "[ NG ]";
                target.getMessageList().clear();
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
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

    /** getMaxSeq() テスト */
    @Test
    //@Ignore
    public void getMaxSeqTest() throws Exception {
        String testinfo = "getMaxSeq() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                // test.
                target.getMessageList().clear();
                target.getMaxSeq(form);
                System.out.println("  " + form.getMaxSeq());
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
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("10");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("999");
                entity.setStatus(Stat.ENABLE.getValue());
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
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 6, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_UPD) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_UPD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 7, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_DEL) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_DEL);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 7, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(桁(上限) MODE_ADD) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("100");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("1000");
                entity.setStatus(Stat.ENABLE.getValue());
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
            {
                msg = testinfo + "[ NG(桁(下限) MODE_ADD) ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("0");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("-1");
                entity.setStatus(Stat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
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
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("x");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType("99");
                entity.setRequired("2");
                entity.setMaxlength("x");
                entity.setStatus("2");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 5, target.getMessageList().size());
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
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));


            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("10");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("999");
                entity.setStatus(Stat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
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

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            String targetId = "";
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("10");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("999");
                entity.setStatus(Stat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.checkInput(form) && target.insertData(form));
                targetId = form.getData().getId();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_UPD);
                MntEmpInfoHdData entity = form.getData();
                entity.setId(targetId);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // update.
                entity = form.getData();
                entity.setId(targetId);
                entity.setSeq("1");
                entity.setLabel("ラベル");
                entity.setType(InfType.CODE.getValue());
                entity.setRequired(ReqFlg.ANYTHING.getValue());
                entity.setMaxlength("1");
                entity.setStatus(Stat.DISABLE.getValue());
                entity.setMemo("メモ");

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

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            String targetId = "";
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_ADD);
                MntEmpInfoHdData entity = form.getData();
                entity.setSeq("10");
                entity.setLabel("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                entity.setType(InfType.TEXT.getValue());
                entity.setRequired(ReqFlg.REQUIRED.getValue());
                entity.setMaxlength("999");
                entity.setStatus(Stat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                assertEquals(msg, true, target.checkInput(form) && target.insertData(form));
                targetId = form.getData().getId();
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntEmpInfoHdForm form = new MntEmpInfoHdForm();
                form.setMode(CommonModel.MODE_DEL);
                MntEmpInfoHdData entity = form.getData();

                // 確認.
                entity.setId(targetId);
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
