package com.res_system.re_emp_manager.model.mnt_group;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import com.res_system.re_emp_manager.commons.data.ListItem;
import com.res_system.re_emp_manager.commons.kind.Authority;
import com.res_system.re_emp_manager.commons.kind.CheckKbn;
import com.res_system.re_emp_manager.commons.kind.GrpStat;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 *
 * MntGroupModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class MntGroupModelTest {

    @Inject
    private MntGroupModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ MntGroupModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ MntGroupModel Test @AfterClass ]"); }
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
            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntGroupForm form = new MntGroupForm();
                form.getData().setId("10");
                assertEquals(msg, true, target.findData(form));
                int count = 0;
                SandBox.showObject(msg, form.getData());
                for(MntGroupMemberData data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                MntGroupForm form = new MntGroupForm();
                form.getData().setId("11");
                assertEquals(msg, true, target.findData(form));
                int count = 0;
                SandBox.showObject(msg, form.getData());
                for(MntGroupMemberData data : form.getList()) {
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

    /** getGrpAuthList() テスト */
    @Test
    //@Ignore
    public void getGrpAuthListTest() throws Exception {
        String testinfo = "getGrpAuthList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                List<ListItem> list = target.getGrpAuthList();
                for(ListItem data : list) {
                    SandBox.showObject(msg, data);
                }
                assertEquals(msg, true, list.size() == 3);
                System.out.println(msg + " -- OK -- ");
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
                msg = testinfo + "[ OK ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.LEADER.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 0, target.getMessageList().size());
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");
            }

            //-- NG. --//
            {
                msg = testinfo + "[ NG (グループID) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupMemberData memberData = new MntGroupMemberData();
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(状況) ]";
                MntGroupForm form = new MntGroupForm();
                form.setMode(CommonModel.MODE_UPD);
                MntGroupData entity = form.getData();
                entity.setId("11");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(有効データ削除) ]";
                MntGroupForm form = new MntGroupForm();
                form.setMode(CommonModel.MODE_DEL);
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(ルート削除) ]";
                MntGroupForm form = new MntGroupForm();
                form.setMode(CommonModel.MODE_DEL);
                MntGroupData entity = form.getData();
                entity.setId("10");
                entity.setGrp_status(GrpStat.DISABLE.getValue());
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバー有削除) ]";
                MntGroupForm form = new MntGroupForm();
                form.setMode(CommonModel.MODE_DEL);
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.DISABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.LEADER.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(サイズ) ]";
                MntGroupForm form = new MntGroupForm();
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkSize(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(その他) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status("999");
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこx");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 2, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //-- メンバー. --//
            {
                msg = testinfo + "[ NG(メンバ 件数) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                for(int i = 0, imax = 100; i < imax; i++) {
                    MntGroupMemberData memberData = new MntGroupMemberData();
                    memberData.setId("14");
                    memberData.setAuthority_id(Authority.LEADER.getValue());
                    memberData.setIs_existing(CheckKbn.ON.getValue());
                    form.getList().add(memberData);
                }
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ 権限 必須) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id("");
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ 権限 範囲外) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.SYSTEM.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ 権限 不正) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id("as");
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ 重複) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.MEMBER.getValue());
                memberData.setIs_existing(CheckKbn.OFF.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ デフォルト削除) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.MEMBER.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                memberData.setIs_delete(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ オーナー権限変更) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("10");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("11");
                memberData.setAuthority_id(Authority.MEMBER.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            msg = "[ 別グループ ログイン ]";
            assertEquals(msg, true, auth.doLogin("group-b.com", "member05", "test1234", false));

            //-- 正しさ. --//
            {
                msg = testinfo + "[ NG(グループ 正しさ) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("11");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, 1, target.getMessageList().size());
                assertEquals(msg, false, actual);
                System.out.println(msg + " -OK- ");
            }
            {
                msg = testinfo + "[ NG(メンバ 正しさ) ]";
                MntGroupForm form = new MntGroupForm();
                MntGroupData entity = form.getData();
                entity.setId("20");
                entity.setGrp_status(GrpStat.ENABLE.getValue());
                entity.setMemo("");
                MntGroupMemberData memberData = new MntGroupMemberData();
                memberData.setId("14");
                memberData.setAuthority_id(Authority.LEADER.getValue());
                memberData.setIs_existing(CheckKbn.ON.getValue());
                form.getList().add(memberData);
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form);
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

            //-- OK. --//
            {
                MntGroupForm form = new MntGroupForm();
                form.getData().setId("11");

                // 確認.
                msg = testinfo + "[ before ]";
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());
                for (MntGroupMemberData data : form.getList()) { SandBox.showObject(msg, data); }

                // update.
                MntGroupData entity = form.getData();
                entity.setGrp_status(GrpStat.CESSATION.getValue());
                entity.setMemo("あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ" 
                        + "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこ");
                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form) && target.updateData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                msg = testinfo + "[ after ]";
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());
                for (MntGroupMemberData data : form.getList()) { SandBox.showObject(msg, data); }
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
        String testinfo = "deleteData";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ ログイン ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member05", "test1234", false));

            //-- OK. --//
            {
                MntGroupForm form = new MntGroupForm();
                form.getData().setId("12");

                // 確認.
                msg = testinfo + "[ before ]";
                assertEquals(msg, true, target.findData(form));
                SandBox.showObject(msg, form.getData());
                for (MntGroupMemberData data : form.getList()) { SandBox.showObject(msg, data); }

                // test.
                target.getMessageList().clear();
                boolean actual = target.checkInput(form) && target.deleteData(form);
                for (Message msgData : target.getMessageList()) { System.out.println("  " + msgData.getCode() + " - " + msgData.getText()); }
                assertEquals(msg, true, actual);
                System.out.println(msg + " -OK- ");

                // 確認.
                msg = testinfo + "[ after ]";
                assertEquals(msg, false, target.findData(form));
                SandBox.showObject(msg, form.getData());
                for (MntGroupMemberData data : form.getList()) { SandBox.showObject(msg, data); }
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
