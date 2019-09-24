package com.res_system.commons.mvc.model.form;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * FormUtil テストクラス.
 *
 * @author res.
 *
 */
public class FormUtilTest {

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
    /**
     * getSql [ OK ] テスト
     *
     * @throws Exception
     */
    @Test
    public void makeOKTest() throws Exception {
        String testinfo = "[ FormUtil Test ] make() [ OK ]:";
        System.out.println(testinfo + "** Start ****************");

        final MultivaluedMap<String, String> params = new MultivaluedStringMap();
        params.add("id",   "id");
        params.add("code", "code");
        params.add("name", "name");
        params.add("memo", "memo");
        params.add("checks", "checks1");
        params.add("checks", "checks2");
        params.add("checks", "checks3");
        params.add("data.id",   "data.id");
        params.add("data.code", "data.code");
        params.add("data.name", "data.name");
        params.add("data.memo", "data.memo");
        params.add("list_size",    "3");
        params.add("list[0].id",   "list[0].id");
        params.add("list[0].code", "list[0].code");
        params.add("list[0].name", "list[0].name");
        params.add("list[0].memo", "list[0].memo");
        params.add("list[1].id",   "list[1].id");
        params.add("list[1].code", "list[1].code");
        params.add("list[1].name", "list[1].name");
        params.add("list[1].memo", "list[1].memo");
        params.add("list[2].id",   "list[2].id");
        params.add("list[2].code", "list[2].code");
        params.add("list[2].name", "list[2].name");
        params.add("list[2].memo", "list[2].memo");
        params.add("dummy", "dummy");
        params.add("ignoreType01", "101");
        params.add("ignoreType02", "102");
        params.add("ignoreType03", "103");
        params.add("ignoreType04", "104");
        params.add("ignoreType05", "105");
        String msg = "";

        {
            TestForm form = FormUtil.make(TestForm.class, params);
            assertEquals(msg, "id", form.getId());
            assertEquals(msg, "code", form.getCode());
            assertEquals(msg, "name", form.getName());
            assertEquals(msg, "memo", form.getMemo());
            assertEquals(msg, null  , form.getNothing());
            List<String> check = form.getChecks();
            for (int i = 0, imax = check.size(); i < imax; i++) {
                assertEquals(msg, "checks" + String.valueOf(i+1), check.get(i));
            }

            assertEquals(msg, "data.id", form.getData().getId());
            assertEquals(msg, "data.code", form.getData().getCode());
            assertEquals(msg, "data.name", form.getData().getName());
            assertEquals(msg, "data.memo", form.getData().getMemo());
            assertEquals(msg, null,        form.getData().getNothing());

            List<TestFormData> list = form.getList();
            for (int i = 0, imax = 3; i < imax; i++) {
                assertEquals(msg, "list[" + String.valueOf(i) + "].id", list.get(i).getId());
                assertEquals(msg, "list[" + String.valueOf(i) + "].code", list.get(i).getCode());
                assertEquals(msg, "list[" + String.valueOf(i) + "].name", list.get(i).getName());
                assertEquals(msg, "list[" + String.valueOf(i) + "].memo", list.get(i).getMemo());
            }

            assertEquals(msg, null, form.getDummy());
            assertEquals(msg, null, form.getIgnoreType01());
            assertEquals(msg, null, form.getIgnoreType02());
            assertEquals(msg, null, form.getIgnoreType03());
            assertEquals(msg, 0, form.getIgnoreType04());
            assertEquals(msg, 0, form.getIgnoreType05());
        }


        System.out.println(testinfo + "**   End ****************");
    }


}
