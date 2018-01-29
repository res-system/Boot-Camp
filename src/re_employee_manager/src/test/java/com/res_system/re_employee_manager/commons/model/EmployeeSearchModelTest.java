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
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.model.employee_search.EmployeeSearchForm;
import com.res_system.re_employee_manager.model.employee_search.EmployeeSearchModel;

/**
 *
 * EmployeeSearchModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class EmployeeSearchModelTest {

    @Inject
    private EmployeeSearchModel target;

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
        System.out.println("[ EmployeeSearchModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ EmployeeSearchModel Test @AfterClass ]");
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
    /** search() テスト */
    @Test
    public void searchTest() throws Exception {
        String testinfo = "search() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getData().getTotal_size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setIs_all("1");
                form.setData(data);

                msg = testinfo + "[ (ALL) (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getData().getTotal_size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** search(keyword) テスト */
    @Test
    //@Ignore
    public void searchByKeyWordTest() throws Exception {
        String testinfo = "search(keyword) :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("b0000", "test");
                String keyword = "メンバ";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("d0000", "test");
                String keyword = "メンバ";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");
                String keyword = "グループ2";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");
                String keyword = "グループ1 しゃいん 01";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");
                String keyword = "%";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");
                String keyword = "\\";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
            {
                setAuth("manager", "test");
                String keyword = "_";

                EmployeeSearchForm form = new EmployeeSearchForm();
                EmpSearchCondition data = new EmpSearchCondition();
                data.setKeyword(keyword);
                form.setData(data);

                msg = testinfo + "[ (G:" + authModel.getSelectedGroupId() + "/ A:" + authModel.getAccountId() + "/ K:" + keyword + ")]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getList().size() + "件");
                int count = 0;
                for (EmployeeInfo employeeInfo : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + employeeInfo.getEmployee_id() + "/" + "/" + employeeInfo.getKeyword());
                }

            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
