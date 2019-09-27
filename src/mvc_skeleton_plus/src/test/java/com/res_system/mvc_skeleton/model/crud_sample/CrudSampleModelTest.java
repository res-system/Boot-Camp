package com.res_system.mvc_skeleton.model.crud_sample;

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

import com.res_system.mvc_skeleton.SandBox;
import com.res_system.mvc_skeleton.commons.dao.AppDao;
import com.res_system.mvc_skeleton.commons.model.common.CommonModel;
import com.res_system.mvc_skeleton.commons.model.message.Message;

/**
 *
 * CrudSampleModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class CrudSampleModelTest {

    @Inject
    private CrudSampleModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;



    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ CrudSampleModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass()  throws Exception { System.out.println("[ CrudSampleModel Test @AfterClass  ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after()  throws Exception { System.out.println("[ @After  ]"); }



    // ------------------------------------------------------------------------------------------------------------------------
    // setting.
    private CrudSampleData setData(String code) throws Exception {
        CrudSampleForm form = new CrudSampleForm();
        CrudSampleData data = form.getData();
        data.setCode(code);
        data.setName("name" + code);
        data.setMemo("memo-" + code);
        data.setCheck("1");
        data.setRadio("2");
        data.setSelect("3");
        target.insertData(form);
        return data;
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** findList() テスト */
    @Test
    @Ignore
    public void findListTest() throws Exception {
        String testinfo = "findList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            for (int i = 0, imax = 10; i < imax; i++) {
                setData("test" + String.valueOf(i));
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();
                assertEquals(msg, true, target.findList(form));
                for (CrudSampleData data : form.getList()) { SandBox.showObject(msg, data); }
                assertEquals(msg, true, form.getList().size() >= 10);
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** findData() テスト */
    @Test
    @Ignore
    public void findDataTest() throws Exception {
        String testinfo = "findList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            for (int i = 0, imax = 10; i < imax; i++) {
                setData("test" + String.valueOf(i));
            }

            String id = null;
            {
                CrudSampleForm form = new CrudSampleForm();
                target.findList(form);
                for (CrudSampleData data : form.getList()) {
                    id = data.getId();
                    break;
                }
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();
                form.getData().setId(id);
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());
                System.out.println(msg + " -- OK -- ");
            }
            //-- NG. --//
            {
                msg = testinfo + "[ NG ]";
                CrudSampleForm form = new CrudSampleForm();
                assertEquals(msg, false, target.findData(form));
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** checkData() テスト */
    @Test
    //@Ignore
    public void checkDataTest() throws Exception {
        String testinfo = "checkData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = form.getData();
                data.setCode("1234567890");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("1");
                data.setRadio("2");
                data.setSelect("3");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(必須 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_UPD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_UPD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(種別 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = form.getData();
                data.setCode("123456789０");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("X");
                data.setRadio("x");
                data.setSelect("X");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 4, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(桁 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = form.getData();
                data.setCode("12345678901");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ1");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ1");
                data.setCheck("2");
                data.setRadio("3");
                data.setSelect("5");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 6, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkList() テスト */
    @Test
    //@Ignore
    public void checkListTest() throws Exception {
        String testinfo = "checkList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK(MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = new CrudSampleData();
                data.setCode("1234567890");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("1");
                data.setRadio("2");
                data.setSelect("3");
                form.getList().add(data);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(必須 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = new CrudSampleData();
                form.getList().add(data);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(必須 MODE_UPD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_UPD);
                CrudSampleData data = new CrudSampleData();
                form.getList().add(data);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 3, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(種別 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = new CrudSampleData();
                data.setCode("123456789０");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("X");
                data.setRadio("x");
                data.setSelect("X");
                form.getList().add(data);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 4, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                msg = testinfo + "[ NG(桁 MODE_ADD) ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = new CrudSampleData();
                data.setCode("12345678901");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ1");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ1");
                data.setCheck("2");
                data.setRadio("4");
                data.setSelect("5");
                form.getList().add(data);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, 6, target.getMessageList().size());
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
    @Ignore
    public void insertDataTest() throws Exception {
        String testinfo = "insertData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_ADD);
                CrudSampleData data = form.getData();
                data.setCode("1234567890");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("1");
                data.setRadio("2");
                data.setSelect("3");
                // test.
                target.getMessageList().clear();
                boolean actual = target.editData(form) && target.checkData(form) && target.insertData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
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
    @Ignore
    public void updateDataTest() throws Exception {
        String testinfo = "updateData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CrudSampleData data = setData("test1");

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_UPD);
                form.setData(data);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                data = form.getData();
                data.setCode("1234567890");
                data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                        + "あいうえおかきくけこあいうえおかきくけこ");
                data.setCheck("0");
                data.setRadio("1");
                data.setSelect("4");

                // test.
                target.getMessageList().clear();
                boolean actual = target.editData(form) && target.checkData(form) && target.updateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
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
    @Ignore
    public void deleteDataTest() throws Exception {
        String testinfo = "deleteData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CrudSampleData data = setData("test1");

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();
                form.setMode(CommonModel.MODE_UPD);
                form.setData(data);

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findData(form));
                SandBox.showObject(msg + "[ before ]", form.getData());

                // test.
                target.getMessageList().clear();
                boolean actual = target.editData(form) && target.checkData(form) && target.deleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", false, target.findData(form));

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** updateList() テスト */
    @Test
    //@Ignore
    public void uupdateListTest() throws Exception {
        String testinfo = "updateList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            for (int i = 0, imax = 4; i < imax; i++) {
                setData("test" + String.valueOf(i));
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                CrudSampleForm form = new CrudSampleForm();

                // 確認.
                assertEquals(msg + "[ before ]", true, target.findList(form));
                for (CrudSampleData data : form.getList()) { SandBox.showObject(msg + "[ before ]", data); }

                for (CrudSampleData data : form.getList()) {
                    data.setCode("1234567890");
                    data.setName("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                    data.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ"
                            + "あいうえおかきくけこあいうえおかきくけこ");
                    data.setCheck("0");
                    data.setRadio("1");
                    data.setSelect("4");
                }

                // test.
                target.getMessageList().clear();
                boolean actual = target.editList(form) && target.checkList(form) && target.updateList(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                assertEquals(msg + "[ after ]", true, target.findList(form));
                for (CrudSampleData data : form.getList()) { SandBox.showObject(msg + "[ after ]", data); }

            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }




}
