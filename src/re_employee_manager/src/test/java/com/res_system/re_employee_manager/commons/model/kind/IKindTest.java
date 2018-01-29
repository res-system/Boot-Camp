package com.res_system.re_employee_manager.commons.model.kind;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.commons.mvc.model.kind.KindUtil;

/**
 *
 * IKind テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class IKindTest {

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
    /** AccountStatusKind() テスト */
    @Test
    public void AccountStatusKindTest() throws Exception {
        String testinfo = "[ IKind Test ] AccountStatusKind テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "種別クラス(AccountStatusKind)のチェックを行います.";
        {
            String msg = title + "[ 基本 ]";
            assertEquals(msg, "0", AcStatKind.ENABLE.getValue());
            assertEquals(msg, "1", AcStatKind.DISABLE.getValue());
            assertEquals(msg, "有効", AcStatKind.ENABLE.getText());
            assertEquals(msg, "無効", AcStatKind.DISABLE.getText());
        }
        {
            String msg = title + "[ KindUtil ]";
            IKind[] values = AcStatKind.values();
            List<IListItem> list = KindUtil.toItemList(values);
            assertEquals(msg, 2, list.size());
            assertEquals(msg, true, KindUtil.isExist(values, "0"));
            assertEquals(msg, true, KindUtil.isExist(values, "1"));
            assertEquals(msg, list.get(0).getText(), KindUtil.getText(values, "0"));
            assertEquals(msg, list.get(1).getText(), KindUtil.getText(values, "1"));
            for(IListItem item : list) {
                System.out.println(" value:" + item.getValue() + "/text:" + item.getText());
            }
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** EmployeeStatusKind() テスト */
    @Test
    public void EmployeeStatusKindTest() throws Exception {
        String testinfo = "[ IKind Test ] EmployeeStatusKind テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "種別クラス(EmployeeStatusKind)のチェックを行います.";
        {
            String msg = title + "[ 基本 ]";
            assertEquals(msg, "00", EmStatKind.TEMP_REGIST.getValue());
            assertEquals(msg, "10", EmStatKind.IN_OFFICE.getValue());
            assertEquals(msg, "80", EmStatKind.ON_LEAVE.getValue());
            assertEquals(msg, "90", EmStatKind.RETIREMENT.getValue());
            assertEquals(msg, "仮登録", EmStatKind.TEMP_REGIST.getText());
            assertEquals(msg, "在籍中", EmStatKind.IN_OFFICE.getText());
            assertEquals(msg, "休職中", EmStatKind.ON_LEAVE.getText());
            assertEquals(msg, "退社済", EmStatKind.RETIREMENT.getText());
        }
        {
            String msg = title + "[ KindUtil ]";
            IKind[] values = EmStatKind.values();
            List<IListItem> list = KindUtil.toItemList(values);
            assertEquals(msg, 4, list.size());
            assertEquals(msg, true, KindUtil.isExist(values, "00"));
            assertEquals(msg, true, KindUtil.isExist(values, "10"));
            assertEquals(msg, true, KindUtil.isExist(values, "80"));
            assertEquals(msg, true, KindUtil.isExist(values, "90"));
            assertEquals(msg, list.get(0).getText(), KindUtil.getText(values, "00"));
            assertEquals(msg, list.get(1).getText(), KindUtil.getText(values, "10"));
            assertEquals(msg, list.get(2).getText(), KindUtil.getText(values, "80"));
            assertEquals(msg, list.get(3).getText(), KindUtil.getText(values, "90"));
            for(IListItem item : list) {
                System.out.println(" value:" + item.getValue() + "/text:" + item.getText());
            }
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** SexKind() テスト */
    @Test
    public void SexKindTest() throws Exception {
        String testinfo = "[ IKind Test ] SexKind テスト:";
        System.out.println(testinfo + "** Start ****************");
        String title = "種別クラス(SexKind)のチェックを行います.";
        {
            String msg = title + "[ 基本 ]";
            assertEquals(msg, "0", SexKind.MALE.getValue());
            assertEquals(msg, "1", SexKind.FEMALE.getValue());
            assertEquals(msg, "2", SexKind.UNKNOWN.getValue());
            assertEquals(msg, "男性", SexKind.MALE.getText());
            assertEquals(msg, "女性", SexKind.FEMALE.getText());
            assertEquals(msg, "不明", SexKind.UNKNOWN.getText());
        }
        {
            String msg = title + "[ KindUtil ]";
            IKind[] values = SexKind.values();
            List<IListItem> list = KindUtil.toItemList(values);
            assertEquals(msg, 3, list.size());
            assertEquals(msg, true, KindUtil.isExist(values, "0"));
            assertEquals(msg, true, KindUtil.isExist(values, "1"));
            assertEquals(msg, true, KindUtil.isExist(values, "2"));
            assertEquals(msg, list.get(0).getText(), KindUtil.getText(values, "0"));
            assertEquals(msg, list.get(1).getText(), KindUtil.getText(values, "1"));
            assertEquals(msg, list.get(2).getText(), KindUtil.getText(values, "2"));
            for(IListItem item : list) {
                System.out.println(" value:" + item.getValue() + "/text:" + item.getText());
            }
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
