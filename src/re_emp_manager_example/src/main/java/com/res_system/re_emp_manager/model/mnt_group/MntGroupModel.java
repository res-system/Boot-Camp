package com.res_system.re_emp_manager.model.mnt_group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.ListItem;
import com.res_system.re_emp_manager.commons.kind.Authority;
import com.res_system.re_emp_manager.commons.kind.CheckKbn;
import com.res_system.re_emp_manager.commons.kind.GrpStat;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * グループ情報メンテナンス処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class MntGroupModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "グループ情報";
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
    public MntGroupForm initIndex(final MntGroupForm form) throws Exception {
        return form;
    }


    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final MntGroupForm form) throws Exception {
        boolean result = true;
        form.setData(dao.findByKey(MntGroupData.class
                , form.getData().getId(), auth.getLogin_root_group_id()));
        if (form.getData() != null) {
            form.setList(dao.findList(MntGroupMemberData.class, "find_list"
                    , (st)->{ st.setString(1, form.getData().getId()); }));
        } else {
            addMessage(msg.getMessage("E00012"));
            result = false;
        }
        return result;
    }

    /**
     * グループ用権限リストを取得します.
     * @param warehouse_id 倉庫ID.
     * @return 取得リスト.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public List<ListItem> getGrpAuthList() throws SimpleDaoException, SQLException {
        return dao.executeQueryList(ListItem.class, dao.getSql(MntGroupData.class, "get_grp_auth_list"));
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkSize(final MntGroupForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        if (!checker.checkSelected(form.getData().getId(), "グループ", "")) {
            result = false;
        } else {
            int count = dao.getCount(dao.getSql(MntGroupMemberData.class, "check_count")
                    , (st)->{ st.setString(1, form.getData().getId()); });
            if (count >= MAX_SIZE) {
                addMessage(msg.getMessage("E00015", MAX_SIZE));
                result = false;
            }
        }
        return result;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final MntGroupForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        MntGroupData data = form.getData();
        //----------------
        name = "グループ";
        selector = "#data_id";
        value = data.getId();
        // 必須チェック.
        if (!checker.checkSelected(value, name, selector)) { return false; }
        // 正しさチェック.
        if (!common.checkIdGroup(value)) {
            addMessage(msg.getMessage("E00002"));
            return false; 
        }
        // ルートグループ判定.
        data.setIs_root((isRootGroup(value)) ? CheckKbn.ON.getValue() : CheckKbn.OFF.getValue() );
        // モード別チェック.
        if (CommonModel.MODE_DEL.equals(form.getMode())) {
            if (GrpStat.ENABLE.equals(data.getGrp_status())) {
                addMessage(msg.getMessage("E00017", "有効なデータ"));
                result = false;
            } else if (CheckKbn.ON.equals(data.getIs_root())) { 
                addMessage(msg.getMessage("E10006").setSelector(selector));
                result = false; 
            } else if (form.getList().size() > 0) { 
                addMessage(msg.getMessage("E10008").setSelector(selector));
                result = false; 
            }
        }

        //----------------
        name = "状況";
        selector = "#data_grp_status";
        value = data.getGrp_status();
        if (!checker.checkKind(value, true, GrpStat.values(), name, selector)) { result = false; }
        if (CheckKbn.ON.equals(data.getIs_root()) && !GrpStat.ENABLE.equals(value)) { 
            addMessage(msg.getMessage("E10007").setSelector(selector));
            result = false;
        }
        //----------------
        name = "備考";
        selector = "#data_memo";
        value = data.getMemo();
        if (!checker.checkFullText(value, false, 200, name, selector)) { result = false; }

        //----------------
        // メンバーチェック.
        if (result) {
            // 件数チェック.
            if (form.getList().size() >= MAX_SIZE) {
                addMessage(msg.getMessage("E00015", MAX_SIZE));
                result = false;
            } else {
                String hd = "";
                int i = 0;
                for (MntGroupMemberData memberData : form.getList()) {
                    hd = "[" + (i+1) +"行目] ";
                    // オーナーユーザー判定.
                    memberData.setIs_owner(
                            (common.checkOwnerUser(memberData.getId())) 
                            ? CheckKbn.OFF.getValue() : CheckKbn.ON.getValue() );
                    //----------------
                    name = hd + "ユーザー";
                    selector = "#list_" + i + "_user_id";
                    value = memberData.getId();
                    if (!checker.checkSelected(value, name, selector)) { 
                    // 必須チェック.
                        result = false;
                    } else if (!common.checkIdUser(value)) { 
                    // 正しさチェック.
                        addMessage(msg.getMessage("E00002"));
                        result = false; 
                    } else if (!CheckKbn.ON.equals(memberData.getIs_existing()) 
                            && !checkDuplicateMem(data.getId(), value, name, selector)) {
                    // 重複チェック.
                        result = false; 
                    } else if (CheckKbn.ON.equals(memberData.getIs_delete()) && isDefault(data.getId(), value)) {
                    // デフォルトグループチェック.
                        selector = "#list_" + i + "_is_delete";
                        addMessage(msg.getMessage("E10009", hd).setSelector(selector));
                        result = false;
                    } else if (CheckKbn.ON.equals(data.getIs_root()) && CheckKbn.ON.equals(memberData.getIs_owner()) 
                            && (CheckKbn.ON.equals(memberData.getIs_delete()) 
                                    || !Authority.MANAGER.equals(memberData.getAuthority_id()))) {
                    // ルートグループオーナーユーザーチェック.
                        selector = "#list_" + i + "_is_delete";
                        addMessage(msg.getMessage("E10010", hd).setSelector(selector));
                        result = false;
                    } else {
                        //----------------
                        name = hd + "権限";
                        selector = "#list_" + i + "_authority_id";
                        value = memberData.getAuthority_id();
                        if (!checker.checkKind(value, true, Authority.values(), name, selector)) { result = false; }
                        else if (!checkGrpAuth(value, name, selector)) { result = false; }
                    }
                    i++;
                }
            }
        }

        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final MntGroupForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntGroupData data = form.getData();
            data.setRoot_group_id(auth.getLogin_root_group_id());

            // m_group.
            common.setUpdateInfo(data);
            result = (dao.update(data) > 0);
            if (result) {
                // r_grp_member.
                for (MntGroupMemberData memberData : form.getList()) {
                    memberData.setGroup_id(data.getId());
                    memberData.setUser_id(memberData.getId());
                    common.setUpdateInfo(memberData);
                    if (CheckKbn.ON.equals(memberData.getIs_existing())) {
                        if (CheckKbn.ON.equals(memberData.getIs_delete())) {
                            result = (dao.delete(memberData) > 0);
                        } else {
                            result = (dao.update(memberData) > 0);
                        }
                    } else {
                        if (!CheckKbn.ON.equals(memberData.getIs_delete())) {
                            result = (dao.insert(memberData) > 0);
                        }
                    }
                    if (result) {
                        if (!CheckKbn.ON.equals(memberData.getIs_delete()) 
                                && CheckKbn.ON.equals(memberData.getIs_default())
                                && !isDefault(data.getId(), memberData.getId())) {
                            // s_grp_account.
                            MntGroupSGrpAccount grpAccount = new MntGroupSGrpAccount();
                            grpAccount.setRoot_group_id(auth.getLogin_group_id());
                            grpAccount.setUser_id(memberData.getId());
                            grpAccount.setDefault_group_id(data.getId());
                            common.setUpdateInfo(grpAccount);
                            result = (dao.update(grpAccount) > 0);
                        }
                    }
                    if (!result) { break; }
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
    public boolean deleteData(final MntGroupForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntGroupData data = form.getData();
            data.setRoot_group_id(auth.getLogin_root_group_id());

            // t_login.
            dao.delete(MntGroupData.class, "delete_login"
                    , (st)->{ st.setString(1, data.getId()); });

            // m_group.
            result = (dao.delete(data) > 0);
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



    //---------------------------------------------- [private].
    /**
     * ルートグループか判定します.
     * @param group_id 対象のグループID.
     * @return 判定結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean isRootGroup(final String group_id) throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(MntGroupData.class, "is_root_group")
                , (st)->{ st.setString(1, group_id); });
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * デフォルトグループか判定します.
     * @param group_id 対象のグループID.
     * @param user_id 対象のユーザーID.
     * @return 判定結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean isDefault(final String group_id, final String user_id) 
            throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(group_id) && !ReUtil.isEmpty(user_id)) {
            int count = dao.getCount(dao.getSql(MntGroupSGrpAccount.class, "is_default")
                    , (st)->{
                        st.setString(1, auth.getLogin_root_group_id());
                        st.setString(2, user_id); 
                        st.setString(3, group_id); 
                    });
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * メンバーの重複チェックを行います.
     * @param group_id 対象のグループID.
     * @param user_id 対象のユーザーID.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkDuplicateMem(final String group_id, final String user_id, final String name, final String selector) 
            throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(group_id) && !ReUtil.isEmpty(user_id)) {
            int count = dao.getCount(dao.getSql(MntGroupMemberData.class, "check_duplicate_mem")
                    , (st)->{
                        st.setString(1, group_id);
                        st.setString(2, user_id);
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E00014", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }

    /**
     * グループ権限チェックを行います.
     * @param authority_id 対象の権限ID.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkGrpAuth(final String authority_id, final String name, final String selector) 
            throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(authority_id)) {
            int count = dao.getCount(dao.getSql(MntGroupData.class, "check_grp_auth")
                    , (st)->{
                        st.setString(1, authority_id);
                    });
            if (count <= 0) {
                addMessage(msg.getMessage("E00004", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }


}
