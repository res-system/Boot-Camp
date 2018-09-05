package com.res_system.re_emp_manager.model.emp_info;

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
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.kind.Sex;
import com.res_system.re_emp_manager.commons.kind.Sitch;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;

/**
 *
 * EmpInfoModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class EmpInfoModelTest {

    @Inject
    private EmpInfoModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ EmpInfoModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ EmpInfoModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** dofindData() テスト */
    @Test
    //@Ignore
    public void dofindDataTest() throws Exception {
        String testinfo = "dofindData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ OK(グループ) ]";
            //assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));
            assertEquals(msg, true, auth.doLogin("res-system.com", "test@res-system.com", "test1234", false));

            String targetId = auth.getLogin_user_id();
            //-- OK. --//
            {
                msg = testinfo + "[ OK (" + targetId + ")]";
                EmpInfoForm form = new EmpInfoForm();
                form.getData().setId(targetId);
                assertEquals(msg, true, target.dofindData(form));
                // 社員個人情報.
                SandBox.showObject(msg, form.getData());
                // 社員メールアドレスリスト.
                for(EmpInfoSEmpEmail data : form.getEmpEmailList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員電話番号リスト.
                for(EmpInfoSEmpTel data : form.getEmpTelList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員住所リスト.
                for(EmpInfoSEmpAddr data : form.getEmpAddrList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員サブ情報リスト.
                for(EmpInfoSEmpInfo data : form.getEmpInfoList()) {
                    SandBox.showObject(msg, data);
                }
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

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "test@res-system.com", "test1234", false));

            String targetId = auth.getLogin_user_id();
            //-- OK. --//
            {
                // test data.
                EmpInfoForm form = new EmpInfoForm();
                form.getData().setId(targetId);
                assertEquals(msg, true, target.dofindData(form));

                msg = testinfo + "[ NG (" + targetId + ")]";
                form.getData().setId("");
                form.getData().setEmployee_no("");
                form.getData().setFamily_name("");
                form.getData().setFirst_name("");
                form.getData().setFamily_name_kana("あ");
                form.getData().setFirst_name_kana("a");
                form.getData().setSituation("");
                form.getData().setHire_date("");
                form.getData().setRetirement_date("asdf");
                form.getData().setSex("a");
                form.getData().setBirthday("asd");
                form.getData().setMynumber("sadf");
                form.getData().setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                for (EmpInfoSEmpEmail emp_email : form.getEmpEmailList()) {
                    emp_email.setEmail_addr("asdas");
                }
                for (EmpInfoSEmpTel emp_tel : form.getEmpTelList()) {
                    emp_tel.setTel_no("asdas");
                }
                for (EmpInfoSEmpAddr emp_addr : form.getEmpAddrList()) {
                    emp_addr.setPostal_code("sdasd");
                    emp_addr.setAddr1("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                    emp_addr.setAddr2("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                    emp_addr.setNearest_station("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                }
                for (EmpInfoSEmpInfo emp_info : form.getEmpInfoList()) {
                    emp_info.setValue("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                            + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                }
                // test.
                target.getMessageList().clear();
                boolean actual = target.doCheckInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- OK. --//
            {
                // test data.
                EmpInfoForm form = new EmpInfoForm();
                form.getData().setId(targetId);
                assertEquals(msg, true, target.dofindData(form));

                msg = testinfo + "[ NG (" + targetId + ")]";
                form.getData().setId(targetId + "12");
                // test.
                target.getMessageList().clear();
                boolean actual = target.doCheckInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
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
        int index = 0;
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ OK(グループ) ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "test@res-system.com", "test1234", false));

            String targetId = auth.getLogin_user_id();
            //-- OK. --//
            {
                // test data.
                EmpInfoForm form = new EmpInfoForm();
                form.getData().setId(targetId);
                assertEquals(msg, true, target.dofindData(form));

                msg = testinfo + "[ OK (" + targetId + ")]";
                form.getData().setEmployee_no("1234567890123456");
                form.getData().setFamily_name("鈴木");
                form.getData().setFirst_name("一郎");
                form.getData().setFamily_name_kana("スズキ");
                form.getData().setFirst_name_kana("イチロウ");
                form.getData().setSituation(Sitch.IN_OFFICE.getValue());
                form.getData().setHire_date("2018/04/01");
                form.getData().setRetirement_date(null);
                form.getData().setSex(Sex.FEMALE.getValue());
                form.getData().setBirthday("1988/04/01");
                form.getData().setMynumber("123456789012");
                form.getData().setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                index = 0;
                for (EmpInfoSEmpEmail emp_email : form.getEmpEmailList()) {
                    emp_email.setEmail_addr("test@test" + index);
                    index++;
                }
                index = 0;
                for (EmpInfoSEmpTel emp_tel : form.getEmpTelList()) {
                    emp_tel.setTel_no("06-1234-567" + index);
                    index++;
                }
                index = 0;
                for (EmpInfoSEmpAddr emp_addr : form.getEmpAddrList()) {
                    emp_addr.setPostal_code("123456" + index);
                    emp_addr.setAddr1("大阪府大阪市大阪町" + index);
                    emp_addr.setAddr2("１−２−" + index);
                    emp_addr.setNearest_station("阪急 梅田" + index);
                    index++;
                }
                for (EmpInfoSEmpInfo emp_info : form.getEmpInfoList()) {
                    String type = emp_info.getType();
                    if (InfType.TEXT.equals(type)) {
                        emp_info.setValue("あいうえおかきくけこ");
                    } else if (InfType.MEMO.equals(type)) {
                        emp_info.setValue("あいうえおかきくけこ\nあいうえおかきくけこ");
                    } else if (InfType.DATE.equals(type)) {
                        emp_info.setValue("2018/08/15");
                    } else if (InfType.NUMERIC.equals(type)) {
                        emp_info.setValue("12345");
                    } else if (InfType.NUMBER.equals(type)) {
                        emp_info.setValue("12345");
                    } else if (InfType.ALPHA_NUMBER.equals(type)) {
                        emp_info.setValue("A1234");
                    } else if (InfType.KANA.equals(type)) {
                        emp_info.setValue("アイウエオカキクケコ");
                    } else if (InfType.TEL_NO.equals(type)) {
                        emp_info.setValue("099-1111-1111");
                    } else if (InfType.EMAIL_ADDR.equals(type)) {
                        emp_info.setValue("test@test");
                    } else if (InfType.CODE.equals(type)) {
                        emp_info.setValue("N1234");
                    } else if (InfType.CODE_NUM.equals(type)) {
                        emp_info.setValue("12345");
                    }
                }
                // test.
                target.getMessageList().clear();
                boolean actual = target.doCheckInput(form) && target.doUpdateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText() + " - " + msgData.getSelector()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }
            // 確認,
            {
                msg = testinfo + "[ OK (" + targetId + ")]";
                EmpInfoForm form = new EmpInfoForm();
                form.getData().setId(targetId);
                assertEquals(msg, true, target.dofindData(form));
                // 社員個人情報.
                SandBox.showObject(msg, form.getData());
                // 社員メールアドレスリスト.
                for(EmpInfoSEmpEmail data : form.getEmpEmailList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員電話番号リスト.
                for(EmpInfoSEmpTel data : form.getEmpTelList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員住所リスト.
                for(EmpInfoSEmpAddr data : form.getEmpAddrList()) {
                    SandBox.showObject(msg, data);
                }
                // 社員サブ情報リスト.
                for(EmpInfoSEmpInfo data : form.getEmpInfoList()) {
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
