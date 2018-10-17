package com.res_system.re_emp_manager.model.mnt_group_acc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.kind.Authority;
import com.res_system.re_emp_manager.commons.kind.CheckKbn;
import com.res_system.re_emp_manager.commons.kind.GAcStat;
import com.res_system.re_emp_manager.commons.kind.InitFlg;
import com.res_system.re_emp_manager.commons.kind.Sitch;
import com.res_system.re_emp_manager.commons.kind.UsrKbn;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.entities.GPersonal;
import com.res_system.re_emp_manager.commons.model.entities.MEmployee;
import com.res_system.re_emp_manager.commons.model.entities.RGrpMember;
import com.res_system.re_emp_manager.commons.model.entities.SUserKey;

/**
 * グループアカウント情報メンテナンス処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class MntGroupAccModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "グループアカウント情報";
    /** 最大レコード数. */
    private static final int MAX_SIZE = 99;



    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel common;
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;
    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msg;
    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checker;



    //---------------------------------------------- properies [private].
    /** メッセージリスト. */
    private List<Message> messageList;
    //-- setter / getter. --//
    /** メッセージリスト を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージリスト を設定します. */
    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }
    /** メッセージリスト を追加します. */
    public IMessage addMessage(Message message) { this.messageList.add(message); return this; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = new ArrayList<>();
    }
    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {}



    //---------------------------------------------- [public] 業務処理.
    /**
     * 一覧画面の初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public MntGroupAccForm initIndex(final MntGroupAccForm form) throws Exception {
        form.getSearchCond().reset();
        findList(form);
        return form;
    }


    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final MntGroupAccForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<MntGroupAccData> list = 
                common.findList(MntGroupAccData.class
                        , searchCond
                        , dao.getSql(MntGroupAccData.class, "find_list")
                        , common.makeWhere(searchCond, dao.getSql(MntGroupAccData.class, "find_status"))
                        , common.getOrder(searchCond
                                , MntGroupAccData.class, MntGroupAccData.SORT_MIN, MntGroupAccData.SORT_MAX)
                        , CommonModel.PAGE_SIZE
                        , (st)->{
                            st.setString(1, auth.getLogin_root_group_id());
                            dao.setKeywordsParam(st, 2, searchCond.getKeywords()); 
                        });
        form.setList(list);
        if (searchCond.getTotalSize() <= 0L) {
            addMessage(msg.getMessage("E00012"));
            result = false;
        }
        return result;
    }

    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final MntGroupAccForm form) throws Exception {
        form.setData(dao.findByKey(MntGroupAccData.class, auth.getLogin_root_group_id(), form.getData().getId()));
        return (form.getData() != null);
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkSize(final MntGroupAccForm form) throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(MntGroupAccData.class, "check_count")
                , (st)->{ st.setString(1, auth.getLogin_root_group_id()); });
        if (count >= MAX_SIZE) {
            addMessage(msg.getMessage("E00015", MAX_SIZE));
            return false;
        }
        return true;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final MntGroupAccForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        // オーナーフラグ設定.
        form.getData().setIs_owner(
                common.checkOwnerUser(form.getData().getId()) 
                ? CheckKbn.OFF.getValue() : CheckKbn.ON.getValue() );

        if (!CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "ID";
            selector = "#data_id";
            value = form.getData().getId();
            if (!checker.checkRequired(value, name, selector)) {
            // 必須チェック.
                result = false;
            } else if (!common.checkIdUser(value)) { 
            // 正しさチェック.
                addMessage(msg.getMessage("E00002"));
                result = false; 
            }
            if (CommonModel.MODE_DEL.equals(form.getMode())) {
                if (GAcStat.ENABLE.equals(form.getData().getGpac_status())) {
                // 有効データチェック.
                    addMessage(msg.getMessage("E00017", "有効なデータ"));
                    result = false;
                }
                if (CheckKbn.ON.equals(form.getData().getIs_owner())) {
                // オーナーチェック.
                    addMessage(msg.getMessage("E10005"));
                    result = false;
                }
            }

        } else {
            if (!checkSize(form)) { result = false; }
        }
        if (CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "社員番号";
            selector = "#data_employee_no";
            value = form.getData().getEmployee_no();
            if (!checker.checkCode(value, true, 16, name, selector)) { result = false; }
            if (!checkEmployeeNo(value, form.getData().getId(), name, selector)) { result = false; }
            //----------------
            name = "氏名（姓）";
            selector = "#data_family_name";
            value = form.getData().getFamily_name();
            if (!checker.checkFullText(value, true, 40, name, selector)) { result = false; }
            if (!checker.checkSpace(value, false, name, selector)) { result = false; }
            //----------------
            name = "氏名（名）";
            selector = "#data_first_name";
            value = form.getData().getFirst_name();
            if (!checker.checkFullText(value, false, 40, name, selector)) { result = false; }
            if (!checker.checkSpace(value, false, name, selector)) { result = false; }
            //----------------
            name = "就業状況";
            selector = "#data_situation";
            value = form.getData().getSituation();
            if (!checker.checkKind(value, true, Sitch.values(), name, selector)) { result = false; }
        }
        //----------------
        name = "グループアカウント状態";
        selector = "#data_gpac_status";
        value = form.getData().getGpac_status();
        if (!checker.checkKind(value, true, GAcStat.values(), name, selector)) { result = false; }
        //----------------
        name = "連絡用メールアドレス";
        selector = "#data_email_addr";
        value = form.getData().getEmail_addr();
        if (CheckKbn.ON.equals(form.getData().getIs_invite())) {
            if (!checker.checkRequired(value, "招待する場合、" + name, selector)) { result = false; }
        }
        if (!checker.checkEmail(value, false, name, selector)) { result = false; }
        //----------------
        name = "備考";
        selector = "#data_memo";
        value = form.getData().getMemo();
        if (!checker.checkFullText(value, false, 200, name, selector)) { result = false; }

        return result;
    }


    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(final MntGroupAccForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();

            MntGroupAccData data = form.getData();

            // m_user.
            MntGroupAccMUser user = new MntGroupAccMUser();
            user.setUser_kbn(UsrKbn.GROUP.getValue());
            user.setName(common.getName(data));
            user.setDefault_authority_id(Authority.GROUP.getValue());
            user.setEmail_addr(data.getEmail_addr());
            common.setUpdateInfo(user);
            result = (dao.insert(user) > 0);
            if (result) {
                user.setId(dao.getLastInsertId());
            }
            if (result) {
                // s_grp_account.
                MntGroupAccSGrpAccount grp_account = new MntGroupAccSGrpAccount();
                grp_account.setRoot_group_id(auth.getLogin_root_group_id());
                grp_account.setUser_id(user.getId());
                grp_account.setDefault_group_id(auth.getLogin_root_group_id());
                grp_account.setAccount_id(null);
                grp_account.setLogin_id(null);
                grp_account.setGpac_status(data.getGpac_status());
                grp_account.setMemo(data.getMemo());
                common.setUpdateInfo(grp_account);
                result = (dao.insert(grp_account) > 0);
            }
            if (result) {
                // r_grp_member.
                RGrpMember grp_member = new RGrpMember();
                grp_member.setGroup_id(auth.getLogin_root_group_id());
                grp_member.setUser_id(user.getId());
                grp_member.setAuthority_id(Authority.MEMBER.getValue());
                common.setUpdateInfo(grp_member);
                result = (dao.insert(grp_member) > 0);
            }
            if (result) {
                // g_personal.
                GPersonal personal = new GPersonal();
                personal.setFamily_name(data.getFamily_name());
                personal.setFirst_name(data.getFirst_name());
                common.setUpdateInfo(personal);
                result = (dao.insert(personal) > 0);
                if (result) {
                    personal.setId(dao.getLastInsertId());
                }
                if (result) {
                    // m_employee.
                    MEmployee employee = new MEmployee();
                    employee.setUser_id(user.getId());
                    employee.setPersonal_id(personal.getId());
                    employee.setSituation(data.getSituation());
                    employee.setEmployee_no(data.getEmployee_no());
                    common.setUpdateInfo(employee);
                    result = (dao.insert(employee) > 0);
                }
            }
            if (result) {
                data.setId(user.getId());
                data.setName(user.getName());
            }
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の追加"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の追加"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final MntGroupAccForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();

            MntGroupAccData data = form.getData();

            // m_user.
            MntGroupAccMUser user = new MntGroupAccMUser();
            user.setId(data.getId());
            user.setEmail_addr(data.getEmail_addr());
            common.setUpdateInfo(user);
            result = (dao.update(user) > 0);
            if (result) {
                // s_grp_account.
                MntGroupAccSGrpAccount grp_account = new MntGroupAccSGrpAccount();
                grp_account.setRoot_group_id(auth.getLogin_root_group_id());
                grp_account.setUser_id(data.getId());
                grp_account.setGpac_status(data.getGpac_status());
                grp_account.setMemo(data.getMemo());
                common.setUpdateInfo(grp_account);
                if (CheckKbn.ON.equals(form.getData().getIs_owner())) {
                    result = (dao.update(grp_account, SqlMaker.SQL_NAME_UPDATE + "_OWNER") > 0);
                } else {
                    result = (dao.update(grp_account) > 0);
                }
            }
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の更新"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の更新"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * データの削除処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean deleteData(final MntGroupAccForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntGroupAccData data = form.getData();

            // s_user_key.
            dao.delete(MntGroupAccData.class, "delete_user_key"
                    , (st)->{ st.setString(1, data.getId()); });
            // t_login.
            dao.delete(MntGroupAccData.class, "delete_login"
                    , (st)->{ st.setString(1, data.getId()); });
            // m_employee.
            dao.delete(MntGroupAccData.class, "delete_employee"
                    , (st)->{ st.setString(1, data.getId()); });
            // s_emp_info.
            dao.delete(MntGroupAccData.class, "delete_emp_info"
                    , (st)->{ st.setString(1, data.getId()); });
            // s_emp_addr.
            dao.delete(MntGroupAccData.class, "delete_emp_addr"
                    , (st)->{ st.setString(1, data.getId()); });
            // s_emp_tel.
            dao.delete(MntGroupAccData.class, "delete_emp_tel"
                    , (st)->{ st.setString(1, data.getId()); });
            // s_emp_email.
            dao.delete(MntGroupAccData.class, "delete_emp_email"
                    , (st)->{ st.setString(1, data.getId()); });
            // s_emp_family.
            dao.delete(MntGroupAccData.class, "delete_emp_family"
                    , (st)->{ st.setString(1, data.getId()); });

            // s_grp_account.
            result = (dao.delete(MntGroupAccData.class, "delete_grp_account"
                    , (st)->{ st.setString(1, data.getId()); }) > 0);
            if (result) {
                // r_grp_member.
                result = (dao.delete(MntGroupAccData.class, "delete_grp_member"
                        , (st)->{ st.setString(1, data.getId()); }) > 0);
            }
            if (result) {
                // m_user.
                result = (dao.delete(MntGroupAccData.class, "delete_user"
                        , (st)->{ st.setString(1, data.getId()); }) > 0);
            }
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の削除"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の削除"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            if (dao.isForeignKeyError(e)) {
                addMessage(msg.getMessage("E00013"));
            } else {
                throw e;
            }
        }
        return result;
    }



    /**
     * ログイン情報の入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkLoginInfo(final MntGroupAccForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        //----------------
        name = "ID";
        selector = "#data_id";
        value = form.getData().getId();
        if (!checker.checkRequired(value, name, selector)) {
        // 必須チェック.
            result = false;
        } else if (!common.checkIdUser(value)) { 
        // 正しさチェック.
            addMessage(msg.getMessage("E00002"));
            result = false; 
        } else if (!common.checkOwnerUser(value) 
                && CommonModel.MODE_DEL.equals(form.getMode())) {
            // オーナーチェック.
            addMessage(msg.getMessage("E10005"));
            result = false;
        }
        if (CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "ログインID";
            selector = "#data_login_id";
            value = form.getData().getLogin_id();
            if (!checker.checkLoginId(value, true, name, selector)) { result = false; }
            if (!checkLoginId(value, form.getData().getId(), name, selector)) { result = false; }
        }
        //----------------
        name = "パスワード";
        selector = "#data_password";
        value = form.getData().getPassword();
        if (!checker.checkPassword(value, true, name, selector)) { result = false; }

        if (result && !CommonModel.MODE_ADD.equals(form.getMode())) {
            if (!auth.checkPassword(form.getData().getId(), form.getData().getPassword())) {
                addMessage(msg.getMessage("E10001"));
                result = false; 
            }
        }

        return result;
    }

    /**
     * ログイン情報の追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean addLoginInfo(final MntGroupAccForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();

            MntGroupAccData data = form.getData();

            // s_grp_account.
            MntGroupAccSGrpAccount grp_account = new MntGroupAccSGrpAccount();
            grp_account.setRoot_group_id(auth.getLogin_root_group_id());
            grp_account.setUser_id(data.getId());
            grp_account.setLogin_id(data.getLogin_id());
            common.setUpdateInfo(grp_account);
            result = (dao.update(grp_account, "update_login_id") > 0);
            if (result) {
                // s_user_key.
                SUserKey userKey = new SUserKey();
                userKey.setUser_id(data.getId());
                userKey.setSeq("1");
                userKey.setKey(auth.makePassword(data.getPassword()));
                userKey.setInitial_flg(InitFlg.INITIAL_VALUE.getValue());
                common.setUpdateInfo(userKey);
                result = (dao.insert(userKey) > 0);
            }
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", "ログイン情報の追加"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", "ログイン情報の追加"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * ログイン情報の削除処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean delLoginInfo(final MntGroupAccForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();

            MntGroupAccData data = form.getData();

            // s_user_key.
            dao.delete(MntGroupAccData.class, "delete_user_key"
                    , (st)->{ st.setString(1, data.getId()); });

            // s_grp_account.
            MntGroupAccSGrpAccount grp_account = new MntGroupAccSGrpAccount();
            grp_account.setRoot_group_id(auth.getLogin_root_group_id());
            grp_account.setUser_id(data.getId());
            grp_account.setLogin_id(null);
            common.setUpdateInfo(grp_account);
            result = (dao.update(grp_account, "update_login_id") > 0);
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の削除"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の削除"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }



    //---------------------------------------------- [private].
    /**
     * 社員番号重複チェックを行います.
     * @param employee_no 対象の社員番号.
     * @param user_id 対象のユーザーID.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkEmployeeNo(
            final String employee_no, final String user_id, final String name, final String selector
            ) throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(employee_no)) {
            int count = dao.getCount(dao.getSql(MntGroupAccData.class, "check_employee_no")
                    , (st)->{
                        st.setString(1, auth.getLogin_root_group_id());
                        st.setString(2, employee_no);
                        st.setString(3, user_id);
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E00014", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }

    /**
     * ログインIDチェックを行います.
     * @param login_id ログインID.
     * @param user_id 対象のユーザーID.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkLoginId(
            final String login_id, final String user_id, final String name, final String selector
            ) throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(login_id)) {
            int count = dao.getCount(dao.getSql(MntGroupAccSGrpAccount.class, "check_login_id")
                    , (st)->{
                        st.setString(1, auth.getLogin_root_group_id());
                        st.setString(2, login_id);
                        st.setString(3, user_id);
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E00014", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }

}
