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
import com.res_system.re_employee_manager.commons.model.data.EmpSearchCondition;
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.model.employee_info.EmployeeInfoForm;
import com.res_system.re_employee_manager.model.employee_info.EmployeeInfoModel;

/**
 *
 * EmployeeInfoModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class EmployeeInfoModelTest {

    @Inject
    private EmployeeInfoModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;

    /** 社員選択処理 モデルクラス. */
    @Inject
    private SelectedEmpModel selectedEmployeeModel;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ EmployeeInfoModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ EmployeeInfoModel Test @AfterClass ]");
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

    /** find() テスト */
    @Test
    public void findTest() throws Exception {
        String testinfo = "find() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("employeeA", "test");
                EmployeeInfoForm form = new EmployeeInfoForm();
                msg = testinfo + "[ find by account_id ]";
                assertEquals(msg, true, target.find(form));
                System.out.println(msg + " -OK- ");
                SandBox.showObject(testinfo, form.getData());

            }
            {
                setAuth("manager", "test");

                selectedEmployeeModel.begin();
                EmpSearchCondition condition = new EmpSearchCondition();
                condition.setSelected_employee_id("20");
                selectedEmployeeModel.setData(condition);

                EmployeeInfoForm form = new EmployeeInfoForm();
                msg = testinfo + "[ find by employee_id]";
                assertEquals(msg, true, target.find(form));
                System.out.println(msg + " -OK- ");
                SandBox.showObject(testinfo, form.getData());

                selectedEmployeeModel.end();
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
            {
                setAuth("manager", "test");
                {
                    msg = testinfo + "[ checkInput ]エラー無し";
                    target.getMessageList().clear();
                    EmployeeInfoForm form = new EmployeeInfoForm();
                    EmployeeInfo data = new EmployeeInfo();

                    data.setEmployee_no("0000000005");
                    data.setFamily_name("社員");
                    data.setFirst_name("五太");
                    data.setFamily_name_kana("シャイン");
                    data.setFirst_name_kana("ゴタ");
                    data.setPosition("ひら");
                    data.setEmp_status("10");
                    data.setJoining_date("2017/10/23");
                    data.setLeaving_date("2018/3/31");

                    data.setSex("2");
                    data.setBirthday("1977/12/21");

                    data.setMynumber(    "11111111111111111111");
                    data.setAcquisition_date("1997/12/21");
                    data.setInsurance_no("22222222222222222222");
                    data.setPension_no(  "33333333333333333333");

                    data.setPostal_code("1231234");
                    data.setAddr1("住所１");
                    data.setAddr2("住所２");
                    data.setNearest_station("もより");

                    data.setTel_no("111-1111-1111");
                    data.setTel_no_mb("222-2222-2222");
                    data.setEmail("test@test1");
                    data.setEmail_mb("test@test2");

                    data.setMemo("メモ");

                    form.setData(data);
                    boolean actual = target.checkInput(form);
                    for (Message msgData : target.getMessageList()) {
                        System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                    }
                    assertEquals(msg, true, actual);
                    System.out.println(msg + " -OK- ");
                }
                {
                    msg = testinfo + "[ checkInput ]フル桁";
                    target.getMessageList().clear();
                    EmployeeInfoForm form = new EmployeeInfoForm();
                    EmployeeInfo data = new EmployeeInfo();

                    data.setEmployee_no("1234567891");
                    data.setFamily_name("あいうえおかきくけ１あいうえおかきくけ２");
                    data.setFirst_name ("12345678911234567892");
                    data.setFamily_name_kana("アイウエオカキクケコアイウエオカキクケコアイウエオカキクケコアイウエオカキクケコ");
                    data.setFirst_name_kana ("アイウエオカキクケコアイウエオカキクケコアイウエオカキクケコアイウエオカキクケコ");
                    data.setPosition("あいうえおかきくけ１あいうえおかきくけ２");
                    data.setEmp_status("10");
                    data.setJoining_date("2017/10/23");
                    data.setLeaving_date("2018/3/31");

                    data.setSex("2");
                    data.setBirthday("1977/12/21");

                    data.setMynumber("1111-1111-1111-11111");
                    data.setAcquisition_date("1997/12/21");
                    data.setInsurance_no("1111-1111-1111-11112");
                    data.setPension_no("1111-1111-1111-11113");

                    data.setPostal_code("1231234");
                    data.setAddr1("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５");
                    data.setAddr2("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５");
                    data.setNearest_station("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４");

                    data.setTel_no("1111-1111-1111-11114");
                    data.setTel_no_mb("1111-1111-1111-11115");
                    data.setEmail("abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0@abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0.abcdefghi1");
                    data.setEmail_mb("abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0@abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0.abcdefghi2");

                    data.setMemo("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５");

                    form.setData(data);
                    boolean actual = target.checkInput(form);
                    for (Message msgData : target.getMessageList()) {
                        System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                    }
                    assertEquals(msg, true, actual);
                    System.out.println(msg + " -OK- ");
                }
                 {
                    msg = testinfo + "[ checkInput ]必須";
                    target.getMessageList().clear();
                    EmployeeInfoForm form = new EmployeeInfoForm();
                    boolean actual = target.checkInput(form);
                    for (Message msgData : target.getMessageList()) {
                        System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                    }
                    assertEquals(msg, false, actual);
                    assertEquals(msg, 9, target.getMessageList().size());
                    System.out.println(msg + " -OK- ");
                }
                {
                    msg = testinfo + "[ checkInput ]桁数";
                    target.getMessageList().clear();
                    EmployeeInfoForm form = new EmployeeInfoForm();
                    EmployeeInfo data = new EmployeeInfo();

                    data.setEmployee_no("1234567891x");
                    data.setFamily_name("あいうえおかきくけ１あいうえおかきくけ２Ｘ");
                    data.setFirst_name ("12345678911234567892X");
                    data.setFamily_name_kana("アイウエオカキクケコアイウエオカキクケコアイウエオカキクケコアイウエオカキクケココ");
                    data.setFirst_name_kana ("アイウエオカキクケコアイウエオカキクケコアイウエオカキクケコアイウエオカキクケココ");
                    data.setPosition("あいうえおかきくけ１あいうえおかきくけ２Ｘ");
                    data.setEmp_status("10");
                    data.setJoining_date("2017/10/23");
                    data.setLeaving_date("2018/3/31");

                    data.setSex("2");
                    data.setBirthday("1977/12/21");

                    data.setMynumber("1111-1111-1111-11111-");
                    data.setAcquisition_date("1997/12/21");
                    data.setInsurance_no("1111-1111-1111-11112-");
                    data.setPension_no("1111-1111-1111-11113-");

                    data.setPostal_code("1231234-");
                    data.setAddr1("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５Ｘ");
                    data.setAddr2("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５Ｘ");
                    data.setNearest_station("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４Ｘ");

                    data.setTel_no("1111-1111-1111-11114-");
                    data.setTel_no_mb("1111-1111-1111-11115-");
                    data.setEmail("abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0@abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0.abcdefghi1abcdefghi2abcdefghi1abcdefghi1abcdefghi1abcdefghi1");
                    data.setEmail_mb("abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0@abcdefghi1abcdefghi2abcdefghi3abcdefghi4abcdefghi5abcdefghi6abcdefghi7abcdefghi8abcdefghi9abcdefghi0.abcdefghi2abcdefghi2abcdefghi1abcdefghi1abcdefghi1abcdefghi1");

                    data.setMemo("あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５"
                                +"あいうえおかきくけ１あいうえおかきくけ２あいうえおかきくけ３あいうえおかきくけ４あいうえおかきくけ５Ｘ");

                    form.setData(data);
                    boolean actual = target.checkInput(form);
                    for (Message msgData : target.getMessageList()) {
                        System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                    }
                    assertEquals(msg, false, actual);
                    assertEquals(msg, 18, target.getMessageList().size());
                    System.out.println(msg + " -OK- ");
                }
                {
                    msg = testinfo + "[ checkInput ]形式";
                    target.getMessageList().clear();
                    EmployeeInfoForm form = new EmployeeInfoForm();
                    EmployeeInfo data = new EmployeeInfo();

                    data.setEmployee_no("あ");
                    data.setFamily_name("あ");
                    data.setFirst_name ("あ");
                    data.setFamily_name_kana("あ");
                    data.setFirst_name_kana("あ");

                    data.setEmp_status("99");
                    data.setJoining_date("2017/10/44");
                    data.setLeaving_date("2018/3/66");

                    data.setSex("D");
                    data.setBirthday("1977/12/78");

                    data.setMynumber(    "X");
                    data.setAcquisition_date("1997/44/21");
                    data.setInsurance_no("X");
                    data.setPension_no(  "X");

                    data.setPostal_code("123１234");

                    data.setTel_no("111-1111-1111cc");
                    data.setTel_no_mb("222-2222-2222ccc");
                    data.setEmail("test@test1あ");
                    data.setEmail_mb("test@test2あ");


                    form.setData(data);
                    boolean actual = target.checkInput(form);
                    for (Message msgData : target.getMessageList()) {
                        System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                    }
                    assertEquals(msg, false, actual);
                    assertEquals(msg, 17, target.getMessageList().size());
                    System.out.println(msg + " -OK- ");
                }
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** save() テスト */
    @Test
    //@Ignore
    public void saveTest() throws Exception {
        String testinfo = "save() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();

            setAuth("employeeA", "test");

            System.out.println(testinfo + "// アカウント紐付け社員情報更新.");
            {
                msg = testinfo + "[ save (new) ]";
                target.getMessageList().clear();
                EmployeeInfoForm form = new EmployeeInfoForm();
                EmployeeInfo data = new EmployeeInfo();

                data.setEmployee_no("9999999999");
                data.setFamily_name("社員");
                data.setFirst_name("九十九太");
                data.setFamily_name_kana("シャイン");
                data.setFirst_name_kana("クジュクタ");
                data.setPosition("ひら");
                data.setEmp_status("10");
                data.setJoining_date("2017/4/1");
                data.setLeaving_date("2018/3/31");

                data.setSex("1");
                data.setBirthday("1977/12/21");
                data.setMynumber("11111111111111111111");
                data.setAcquisition_date("1997/12/21");
                data.setInsurance_no("22222222222222222222");
                data.setPension_no("33333333333333333333");
                data.setPostal_code("1231234");
                data.setAddr1("住所１");
                data.setAddr2("住所２");
                data.setNearest_station("もより");

                data.setTel_no("111-1111-1111");
                data.setTel_no_mb("222-2222-2222");

                data.setEmail("test@test1");
                data.setEmail_mb("test@test2");

                data.setMemo("メモ");

                form.setData(data);

                assertEquals(msg, true, target.save(form));
                System.out.println(msg + " -OK- ");
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }

                // 確認.
                {
                    selectedEmployeeModel.begin();
                    EmpSearchCondition condition = new EmpSearchCondition();
                    condition.setSelected_employee_id(form.getData().getEmployee_id());
                    selectedEmployeeModel.setData(condition);

                    assertEquals(msg, true, target.find(form));
                    assertEquals(msg, authModel.getSelectedGroupId().toString(), form.getData().getDefault_group_id());
                    assertEquals(msg, authModel.getAccountId().toString(), form.getData().getAccount_id());
                    SandBox.showObject(testinfo, form.getData());

                    selectedEmployeeModel.end();
                }


                msg = testinfo + "[ save (update) ]";
                target.getMessageList().clear();

                data.setEmployee_no("9999999998");
                data.setFamily_name("社員Z");
                data.setFirst_name("九十九太Z");
                data.setFamily_name_kana("シャインゼット");
                data.setFirst_name_kana("クジュクタゼット");
                data.setPosition("ひらひら");
                data.setEmp_status("80");
                data.setJoining_date("2017/4/2");
                data.setLeaving_date("2018/3/30");

                data.setSex("2");
                data.setBirthday("1977/12/22");
                data.setMynumber("11111111111111111112");
                data.setAcquisition_date("1997/12/22");
                data.setInsurance_no("22222222222222222223");
                data.setPension_no("33333333333333333334");
                data.setPostal_code("1231235");
                data.setAddr1("住所１2");
                data.setAddr2("住所２2");
                data.setNearest_station("もより2");

                data.setTel_no("111-1111-1112");
                data.setTel_no_mb("222-2222-2223");

                data.setEmail("test@test12");
                data.setEmail_mb("test@test22");

                data.setMemo("メモ2");

                form.setData(data);

                assertEquals(msg, true, target.save(form));
                System.out.println(msg + " -OK- ");
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }

                // 確認.
                {
                    selectedEmployeeModel.begin();
                    EmpSearchCondition condition = new EmpSearchCondition();
                    condition.setSelected_employee_id(form.getData().getEmployee_id());
                    selectedEmployeeModel.setData(condition);

                    assertEquals(msg, true, target.find(form));
                    SandBox.showObject(testinfo, form.getData());

                    selectedEmployeeModel.end();
                }

            }

            System.out.println(testinfo + "// 選択社員情報更新.");
            {
                selectedEmployeeModel.begin();
                EmpSearchCondition condition = new EmpSearchCondition();
                condition.setSelected_employee_id(null);
                selectedEmployeeModel.setData(condition);

                msg = testinfo + "[ save (new) ]";
                target.getMessageList().clear();
                EmployeeInfoForm form = new EmployeeInfoForm();
                EmployeeInfo data = new EmployeeInfo();

                data.setEmployee_no("9999999999");
                data.setFamily_name("社員");
                data.setFirst_name("九十九太");
                data.setFamily_name_kana("シャイン");
                data.setFirst_name_kana("クジュクタ");
                data.setPosition("ひら");
                data.setEmp_status("10");
                data.setJoining_date("2017/4/1");
                data.setLeaving_date("2018/3/31");

                data.setSex("1");
                data.setBirthday("1977/12/21");
                data.setMynumber("11111111111111111111");
                data.setAcquisition_date("1997/12/21");
                data.setInsurance_no("22222222222222222222");
                data.setPension_no("33333333333333333333");
                data.setPostal_code("1231234");
                data.setAddr1("住所１");
                data.setAddr2("住所２");
                data.setNearest_station("もより");

                data.setTel_no("111-1111-1111");
                data.setTel_no_mb("222-2222-2222");

                data.setEmail("test@test1");
                data.setEmail_mb("test@test2");

                data.setMemo("メモ");

                form.setData(data);

                assertEquals(msg, true, target.save(form));
                System.out.println(msg + " -OK- ");
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }

                // 確認.
                condition.setSelected_employee_id(form.getData().getEmployee_id());
                selectedEmployeeModel.setData(condition);
                assertEquals(msg, true, target.find(form));
                assertEquals(msg, authModel.getSelectedGroupId().toString(), form.getData().getDefault_group_id());
                assertEquals(msg, null, form.getData().getAccount_id());
                SandBox.showObject(testinfo, form.getData());


                msg = testinfo + "[ save (update) ]";
                target.getMessageList().clear();

                data.setEmployee_no("9999999998");
                data.setFamily_name("社員Z");
                data.setFirst_name("九十九太Z");
                data.setFamily_name_kana("シャインゼット");
                data.setFirst_name_kana("クジュクタゼット");
                data.setPosition("ひらひら");
                data.setEmp_status("80");
                data.setJoining_date("2017/4/2");
                data.setLeaving_date("2018/3/30");

                data.setSex("2");
                data.setBirthday("1977/12/22");
                data.setMynumber("11111111111111111112");
                data.setAcquisition_date("1997/12/22");
                data.setInsurance_no("22222222222222222223");
                data.setPension_no("33333333333333333334");
                data.setPostal_code("1231235");
                data.setAddr1("住所１2");
                data.setAddr2("住所２2");
                data.setNearest_station("もより2");

                data.setTel_no("111-1111-1112");
                data.setTel_no_mb("222-2222-2223");

                data.setEmail("test@test12");
                data.setEmail_mb("test@test22");

                data.setMemo("メモ2");

                form.setData(data);

                assertEquals(msg, true, target.save(form));
                System.out.println(msg + " -OK- ");
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }

                // 確認.
                assertEquals(msg, true, target.find(form));
                SandBox.showObject(testinfo, form.getData());

                selectedEmployeeModel.end();
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
