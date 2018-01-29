package com.res_system.re_employee_manager.commons.model.entities.table;

import static org.junit.Assert.assertEquals;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;

/**
 *
 * TableEntity テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class TableEntityTest {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ @AfterClass ]");
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
    // Test.
    /** Employee() テスト */
    @Test
    public void EmployeeTest() throws Exception {
        String testinfo = "[ TableEntity Test ] Employee テスト:";
        System.out.println(testinfo + "** Start ****************");
        {
            try(ReEmployeeManagerDao target = new ReEmployeeManagerDao()) {
                target.setup();
                {
                    String name = "SQL_NAME_SELECT";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_SELECT);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
                {
                    String name = "SQL_NAME_KEY_WHERE";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_KEY_WHERE);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
                {
                    String name = "SQL_NAME_KEY_WHERE_WITH_T";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_KEY_WHERE_WITH_T);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
                {
                    String name = "SQL_NAME_INSERT";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_INSERT);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
                {
                    String name = "SQL_NAME_UPDATE";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_UPDATE);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
                {
                    String name = "SQL_NAME_DELETE";
                    String sql = target.getSql(MEmployee.class, SqlMaker.SQL_NAME_DELETE);
                    System.out.println(name + ":" + sql);
                    assertEquals(name, true, (sql != null));
                }
            }
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
