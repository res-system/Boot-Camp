package com.res_system.re_employee_manager.commons.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.entities.GroupItem;
import com.res_system.re_employee_manager.commons.model.entities.MenuData;
import com.res_system.re_employee_manager.commons.model.entities.table.CGAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.CGEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.CGPersonal;
import com.res_system.re_employee_manager.commons.model.entities.table.CGTel;
import com.res_system.re_employee_manager.commons.model.entities.table.CMAuthority;
import com.res_system.re_employee_manager.commons.model.entities.table.CSAcctKey;
import com.res_system.re_employee_manager.commons.model.entities.table.MEmployee;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpEmergency;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpFamily;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpMemo;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpTel;
import com.res_system.re_employee_manager.commons.model.kind.EmStatKind;

/**
 *
 * CommonModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class CommonModelTest {

    @Inject
    private CommonModel target;

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
        System.out.println("[ CommonModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ CommonModel Test @AfterClass ]");
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
    /** saveIdMst() テスト */
    @Test
    public void saveIdMstTest() throws Exception {
        String testinfo = "saveIdMst() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");

                CMAuthority expected;
                CMAuthority entity = new CMAuthority();
                entity.setName("名前I");
                entity.setStatus("0");

                msg = testinfo + "CMAuthority[ INSERT ]";
                target.saveIdMst(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getName(), entity.getName());
                System.out.println(msg + "(" + entity.getId()  + "/" + entity.getName()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");

                msg = testinfo + "CMAuthority[ UPDATE ]";
                entity.setName("名前U");
                target.saveIdMst(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getName(), entity.getName());
                System.out.println(msg + "(" + entity.getId()  + "/" + entity.getName()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** saveGenMst() テスト */
    @Test
    public void saveGenMstTest() throws Exception {
        String testinfo = "saveGenMst() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");

                CGPersonal expected;
                CGPersonal entity = new CGPersonal();
                entity.setFamily_name("名前I");

                msg = testinfo + "CGPersonal[ INSERT ]";
                target.saveGenMst(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getFamily_name(), entity.getFamily_name());
                System.out.println(msg + "(" + entity.getId()  + "/" + entity.getFamily_name()  + "/" + entity.getUpdated_id() + ") -OK- ");

                msg = testinfo + "CGPersonal[ UPDATE ]";
                entity.setFamily_name("名前U");
                target.saveGenMst(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getFamily_name(), entity.getFamily_name());
                System.out.println(msg + "(" + entity.getId()  + "/" + entity.getFamily_name()  + "/" + entity.getUpdated_id() + ") -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** saveAcctSub() テスト */
    @Test
    public void saveAcctSubTest() throws Exception {
        String testinfo = "saveAcctSub() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");

                CSAcctKey expected;
                CSAcctKey entity = new CSAcctKey();
                entity.setAccount_id(1L);
                entity.setSeq(2);
                entity.setKey("KeyI");
                entity.setExpiration_time(null);

                msg = testinfo + "CSAcctKey[ INSERT ]";
                target.saveAcctSub(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getKey(), entity.getKey());
                System.out.println(msg + "(" + entity.getAccount_id() + "/" + entity.getSeq() + "/" + entity.getKey()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");

                msg = testinfo + "CSAcctKey[ UPDATE ]";
                entity.setKey("KeyU");
                target.saveAcctSub(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getKey(), entity.getKey());
                System.out.println(msg + "(" + entity.getAccount_id() + "/" + entity.getSeq() + "/" + entity.getKey()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** saveEmpSub() テスト */
    @Test
    public void saveEmpSubTest() throws Exception {
        String testinfo = "saveEmpSub() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");

                SEmpAddr expected;
                SEmpAddr entity = new SEmpAddr();
                entity.setEmployee_id(1L);
                entity.setSeq(1);
                entity.setAddr_id(1L);

                msg = testinfo + "SEmpAddr[ INSERT ]";
                target.saveEmpSub(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getAddr_id(), entity.getAddr_id());
                System.out.println(msg + "(" + entity.getEmployee_id() + "/" + entity.getSeq() + "/" + entity.getAddr_id()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");

                msg = testinfo + "SEmpAddr[ UPDATE ]";
                entity.setAddr_id(2L);
                target.saveEmpSub(entity);
                expected = dao.find(entity);
                assertEquals(msg, expected.getAddr_id(), entity.getAddr_id());
                System.out.println(msg + "(" + entity.getEmployee_id() + "/" + entity.getSeq() + "/" + entity.getAddr_id()
                        + "/" + entity.getCreated_id()  + "/" + entity.getUpdated_id() + ") -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getMenuDataList() テスト */
    @Test
    public void getMenuDataListTest() throws Exception {
        String testinfo = "getMenuDataList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            setAuth("manager", "test");
            {
                msg = testinfo + "[ PERFLG_MAINMENU ]";
                List<MenuData> actual = target.getMenuDataList(CommonModel.PERFLG_MAINMENU);
                assertEquals(msg, true, actual.size() >= 6);
                System.out.println(msg + " -OK- ");
                for (MenuData data : actual) { SandBox.showObject("  ", data); }
            }
            {
                msg = testinfo + "[ PERFLG_EMPTAB ]";
                List<MenuData> actual = target.getMenuDataList(CommonModel.PERFLG_EMPTAB);
                assertEquals(msg, 4, actual.size());
                System.out.println(msg + " -OK- ");
                System.out.println(msg + " -OK- ");
                for (MenuData data : actual) { SandBox.showObject("  ", data); }
            }
            {
                msg = testinfo + "[ PERFLG_MAINMENU | PERFLG_EMPTAB ]";
                List<MenuData> actual = target.getMenuDataList(CommonModel.PERFLG_MAINMENU | CommonModel.PERFLG_EMPTAB);
                assertEquals(msg, true, actual.size() >= 6);
                System.out.println(msg + " -OK- ");
                System.out.println(msg + " -OK- ");
                for (MenuData data : actual) { SandBox.showObject("  ", data); }
            }
            {
                msg = testinfo + "[ 0 (" +  Integer.toBinaryString(0) + ") ]";
                List<MenuData> actual = target.getMenuDataList(0);
                assertEquals(msg, 0, actual.size());
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ -1 (" +  Integer.toBinaryString(-1) + ") ]";
                List<MenuData> actual = target.getMenuDataList(-1);
                assertEquals(msg, true, actual.size() >= 6);
                System.out.println(msg + " -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getGroupList() テスト */
    @Test
    public void getGroupListTest() throws Exception {
        String testinfo = "getGroupList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            setAuth("system", "test");
            {
                msg = testinfo + "[ system ]";
                List<GroupItem> actual = target.getGroupList();
                assertEquals(msg, 0, actual.size());
                assertEquals(msg, true, target.IsSelectGroup());
                System.out.println(msg + " -OK- ");
                for (GroupItem data : actual) { SandBox.showObject("  ", data); }

            }
            setAuth("admin", "test");
            {
                msg = testinfo + "[ admin ]";
                List<GroupItem> actual = target.getGroupList();
                assertEquals(msg, 2, actual.size());
                assertEquals(msg, true, target.IsSelectGroup());
                System.out.println(msg + " -OK- ");
                for (GroupItem data : actual) { SandBox.showObject("  ", data); }
            }
            setAuth("manager", "test");
            {
                msg = testinfo + "[ manager ]";
                List<GroupItem> actual = target.getGroupList();
                assertEquals(msg, 2, actual.size());
                assertEquals(msg, true, target.IsSelectGroup());
                System.out.println(msg + " -OK- ");
                for (GroupItem data : actual) { SandBox.showObject("  ", data); }
            }
            setAuth("employeeA", "test");
            {
                msg = testinfo + "[ employeeA ]";
                List<GroupItem> actual = target.getGroupList();
                assertEquals(msg, 2, actual.size());
                assertEquals(msg, true, target.IsSelectGroup());
                System.out.println(msg + " -OK- ");
                for (GroupItem data : actual) { SandBox.showObject("  ", data); }
            }
            setAuth("other01", "test");
            {
                msg = testinfo + "[ other01 ]";
                List<GroupItem> actual = target.getGroupList();
                assertEquals(msg, 1, actual.size());
                System.out.println(msg + " -OK- ");
                assertEquals(msg, false, target.IsSelectGroup());
                for (GroupItem data : actual) { SandBox.showObject("  ", data); }
            }
            {
                msg = testinfo + "[ null ]";
                List<GroupItem> actual = target.getGroupListByAccountId(null);
                assertEquals(msg, 0, actual.size());
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ 空 ]";
                List<GroupItem> actual = target.getGroupListByAccountId("");
                assertEquals(msg, 0, actual.size());
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ 空 ]";
                List<GroupItem> actual = target.getGroupListByAccountId("xxx");
                assertEquals(msg, 0, actual.size());
                System.out.println(msg + " -OK- ");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** deleteEmployee() テスト */
    @Test
    public void deleteEmployeeTest() throws Exception {
        String testinfo = "deleteEmployee() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("manager", "test");
                int actual;
                int expected;

                {
                    msg = testinfo + "NULL";
                    expected = 0;
                    actual = target.deleteEmployee(null);
                    assertEquals(msg, expected, actual);
                    System.out.println(msg + ":" + actual);
                }
                {
                    msg = testinfo + "データ無し";
                    expected = 0;
                    actual = target.deleteEmployee(0L);
                    assertEquals(msg, expected, actual);
                    System.out.println(msg + ":" + actual);
                }
                {
                    Long employee_id = makeEmptyData_MEmployee();
                    makeEmptyData_SEmpAddr(employee_id, 1);
                    makeEmptyData_SEmpAddr(employee_id, 2);
                    makeEmptyData_SEmpTel(employee_id, 1);
                    makeEmptyData_SEmpTel(employee_id, 2);
                    makeEmptyData_SEmpEmail(employee_id, 1);
                    makeEmptyData_SEmpEmail(employee_id, 2);
                    makeEmptyData_SEmpMemo(employee_id, 1);
                    makeEmptyData_SEmpMemo(employee_id, 2);
                    makeEmptyData_SEmpFamily(employee_id, 1);
                    makeEmptyData_SEmpEmergency(employee_id, 1);

                    msg = testinfo + "フル";
                    expected = 26;
                    actual = target.deleteEmployee(employee_id);
                    assertEquals(msg, expected, actual);
                    System.out.println(msg + ":(" + employee_id + ")" + actual);
                }

            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /* -- テストデータ作成. -- */
    private Long makeEmptyData_MEmployee() throws SimpleDaoException {
        CGPersonal personal = new CGPersonal();
        personal.setUpdated_id(authModel.getAccountId());
        dao.insert(personal);
        Long personal_id = target.getLastInsertId();

        MEmployee employee = new MEmployee();
        employee.setPersonal_id(personal_id);
        employee.setEmp_status(EmStatKind.TEMP_REGIST.getValue());
        employee.setJoining_date(ReDateUtil.nowDt());
        employee.setCreated_id(authModel.getAccountId());
        employee.setUpdated_id(authModel.getAccountId());
        dao.insert(employee);
        return target.getLastInsertId();
    }

    private void makeEmptyData_SEmpAddr(Long employee_id, Integer seq) throws SimpleDaoException {
        CGAddr addr = new CGAddr();
        addr.setUpdated_id(authModel.getAccountId());
        dao.insert(addr);
        Long addr_id = target.getLastInsertId();

        SEmpAddr empAddr = new SEmpAddr();
        empAddr.setEmployee_id(employee_id);
        empAddr.setSeq(seq);
        empAddr.setAddr_id(addr_id);
        empAddr.setCreated_id(authModel.getAccountId());
        empAddr.setUpdated_id(authModel.getAccountId());
        dao.insert(empAddr);
    }

    private void makeEmptyData_SEmpTel(Long employee_id, Integer seq) throws SimpleDaoException {
        CGTel tel = new CGTel();
        tel.setUpdated_id(authModel.getAccountId());
        dao.insert(tel);
        Long tel_id = target.getLastInsertId();

        SEmpTel empTel = new SEmpTel();
        empTel.setEmployee_id(employee_id);
        empTel.setSeq(seq);
        empTel.setTel_id(tel_id);
        empTel.setCreated_id(authModel.getAccountId());
        empTel.setUpdated_id(authModel.getAccountId());
        dao.insert(empTel);
    }

    private void makeEmptyData_SEmpEmail(Long employee_id, Integer seq) throws SimpleDaoException {
        CGEmail email = new CGEmail();
        email.setUpdated_id(authModel.getAccountId());
        dao.insert(email);
        Long email_id = target.getLastInsertId();

        SEmpEmail empEmail = new SEmpEmail();
        empEmail.setEmployee_id(employee_id);
        empEmail.setSeq(seq);
        empEmail.setEmail_id(email_id);
        empEmail.setCreated_id(authModel.getAccountId());
        empEmail.setUpdated_id(authModel.getAccountId());
        dao.insert(empEmail);
    }

    private void makeEmptyData_SEmpMemo(Long employee_id, Integer seq) throws SimpleDaoException {
        SEmpMemo empMemo = new SEmpMemo();
        empMemo.setEmployee_id(employee_id);
        empMemo.setSeq(seq);
        empMemo.setCreated_id(authModel.getAccountId());
        empMemo.setUpdated_id(authModel.getAccountId());
        dao.insert(empMemo);
    }

    private void makeEmptyData_SEmpFamily(Long employee_id, Integer seq) throws SimpleDaoException {
        CGPersonal personal = new CGPersonal();
        personal.setUpdated_id(authModel.getAccountId());
        dao.insert(personal);
        Long personal_id = target.getLastInsertId();

        CGAddr addr = new CGAddr();
        addr.setUpdated_id(authModel.getAccountId());
        dao.insert(addr);
        Long addr_id = target.getLastInsertId();

        CGTel tel = new CGTel();
        tel.setUpdated_id(authModel.getAccountId());
        dao.insert(tel);
        Long tel_id = target.getLastInsertId();

        CGEmail email = new CGEmail();
        email.setUpdated_id(authModel.getAccountId());
        dao.insert(email);
        Long email_id = target.getLastInsertId();

        SEmpFamily empFamily = new SEmpFamily();
        empFamily.setEmployee_id(employee_id);
        empFamily.setSeq(seq);
        empFamily.setRelationship("誰？");
        empFamily.setPersonal_id(personal_id);
        empFamily.setAddr_id(addr_id);
        empFamily.setTel_id(tel_id);
        empFamily.setEmail_id(email_id);
        empFamily.setCreated_id(authModel.getAccountId());
        empFamily.setUpdated_id(authModel.getAccountId());
        dao.insert(empFamily);
    }

    private void makeEmptyData_SEmpEmergency(Long employee_id, Integer seq) throws SimpleDaoException {
        CGPersonal personal = new CGPersonal();
        personal.setUpdated_id(authModel.getAccountId());
        dao.insert(personal);
        Long personal_id = target.getLastInsertId();

        CGAddr addr = new CGAddr();
        addr.setUpdated_id(authModel.getAccountId());
        dao.insert(addr);
        Long addr_id = target.getLastInsertId();

        CGTel tel = new CGTel();
        tel.setUpdated_id(authModel.getAccountId());
        dao.insert(tel);
        Long tel_id = target.getLastInsertId();

        CGEmail email = new CGEmail();
        email.setUpdated_id(authModel.getAccountId());
        dao.insert(email);
        Long email_id = target.getLastInsertId();

        SEmpEmergency empEmergency = new SEmpEmergency();
        empEmergency.setEmployee_id(employee_id);
        empEmergency.setSeq(seq);
        empEmergency.setPersonal_id(personal_id);
        empEmergency.setAddr_id(addr_id);
        empEmergency.setTel_id(tel_id);
        empEmergency.setEmail_id(email_id);
        empEmergency.setCreated_id(authModel.getAccountId());
        empEmergency.setUpdated_id(authModel.getAccountId());
        dao.insert(empEmergency);
    }



    //---------------------------------------------- daoテスト.
    /** makeKeywords() テスト */
    @Test
    public void makeKeywordsTest() throws Exception {
        String testinfo = "makeKeywords() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();

            // makeWhereKeywords
            {
                msg = testinfo + "makeKeywords[]";
                String keyword = "ABC 123　あいう";
                String[] expected = {"ABC", "123", "あいう"};
                String[] actual = dao.makeKeywords(keyword);
                assertEquals(msg, Arrays.toString(expected), Arrays.toString(actual));
                System.out.println(msg + " -OK- " + Arrays.toString(actual));
            }
            {
                msg = testinfo + "makeKeywords[空]";
                String keyword = "";
                String[] expected = {};
                String[] actual = dao.makeKeywords(keyword);
                assertEquals(msg, Arrays.toString(expected), Arrays.toString(actual));
                System.out.println(msg + " -OK- " + Arrays.toString(actual));
            }
            {
                msg = testinfo + "makeKeywords[null]";
                String keyword = null;
                String[] expected = {};
                String[] actual = dao.makeKeywords(keyword);
                assertEquals(msg, Arrays.toString(expected), Arrays.toString(actual));
                System.out.println(msg + " -OK- " + Arrays.toString(actual));
            }

            // makeKeywordsWhere
            {
                msg = testinfo + "makeKeywordsWhere[true]";
                String[] keywords = {"ABC", "123", "あいう", "カキク"};
                String expected = "(`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +    " AND (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +    " AND (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +    " AND (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)";
                String actual = dao.makeKeywordsWhere(keywords);
                assertEquals(msg, expected, actual);
                System.out.println(msg + " -OK- " + actual);
            }
            {
                msg = testinfo + "makeKeywordsWhere[false]";
                String[] keywords = {"ABC", "123", "あいう", "カキク"};
                String expected = "(`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +     " OR (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +     " OR (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)"
                        +     " OR (`keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ? OR `keyword` like ?)";
                String actual = dao.makeKeywordsWhere(keywords, false);
                assertEquals(msg, expected, actual);
                System.out.println(msg + " -OK- " + actual);
            }
            {
                msg = testinfo + "makeKeywordsWhere[空]";
                String[] keywords = {};
                String expected = "";
                String actual = dao.makeKeywordsWhere(keywords);
                assertEquals(msg, expected, actual);
                System.out.println(msg + " -OK- " + actual);
            }
            {
                msg = testinfo + "makeKeywordsWhere[null]";
                String[] keywords = null;
                String expected = "";
                String actual = dao.makeKeywordsWhere(keywords);
                assertEquals(msg, expected, actual);
                System.out.println(msg + " -OK- " + actual);
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** escapeLikeValue() テスト */
    @Test
    public void escapeLikeValueTest() throws Exception {
        String testinfo = "escapeLikeValue() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        {
            String keyword = "ABC 123　100% あいう code_name \\@";
            msg = testinfo + "[" + keyword + "]";
            String expected = "ABC 123　100\\% あいう code\\_name \\\\@";
            String actual = dao.escapeLikeValue(keyword);
            assertEquals(msg, expected, actual);
            System.out.println(msg + " -OK- " + actual);
        }

        System.out.println(testinfo + "**   End ****************");
    }

}
