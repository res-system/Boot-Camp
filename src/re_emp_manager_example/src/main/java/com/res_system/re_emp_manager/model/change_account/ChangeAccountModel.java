package com.res_system.re_emp_manager.model.change_account;

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
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.kind.SaveFlg;
import com.res_system.re_emp_manager.commons.kind.UsrKbn;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthMUser;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRAccCooperation;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * グループアカウント変更画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class ChangeAccountModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "グループアカウント";
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



    //---------------------------------------------- [public] 業務処理(メニュー処理).
    /**
     * 初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public ChangeAccountForm init(final ChangeAccountForm form) throws Exception {
        findList(form);
        return form;
    }



    //---------------------------------------------- [public] 業務処理(グループアカウント処理).
    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final ChangeAccountForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<AuthRAccCooperation> list = 
                common.findList(AuthRAccCooperation.class
                        , searchCond
                        , dao.getSql(AuthRAccCooperation.class, "find_list")
                        , common.makeWhere(searchCond, dao.getSql(AuthRAccCooperation.class, "find_status"))
                        , common.getOrder(searchCond
                                , AuthRAccCooperation.class, AuthRAccCooperation.SORT_MIN, AuthRAccCooperation.SORT_MAX)
                        , CommonModel.PAGE_SIZE
                        , (st)->{
                            st.setString(1, auth.getLogin_user_id()); 
                            st.setString(2, auth.getLogin_account_id()); 
                            dao.setKeywordsParam(st, 3, searchCond.getKeywords()); 
                        });
        form.setList(list);
        if (searchCond.getTotalSize() <= 0L) {
            addMessage(msg.getMessage("E00012"));
            result = false;
        }
        return result;
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkSize(final ChangeAccountForm form) throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(AuthRAccCooperation.class, "check_count")
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
    public boolean checkInput(final ChangeAccountForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        if (CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "グループ識別コード";
            selector = "#code";
            value = form.getCode();
            if (!checker.checkCode(value, true, name, selector)) { result = false; }
            //----------------
            name = "ログインID";
            selector = "#id";
            value = form.getId();
            if (!checker.checkLoginId(value, true, name, selector)) { result = false; }
            //----------------
            name = "パスワード";
            selector = "#key";
            value = form.getKey();
            if (!checker.checkPassword(value, true, name, selector)) { result = false; }
            //----------------
            name = "ログイン情報を保存する";
            selector = "#save";
            value = form.getSave();
            if (!checker.checkKind(value, false, SaveFlg.values(), name, selector)) { result = false; }
            //----------------
            // サイズチェック.
            if (!checkSize(form)) { result = false; }
            //----------------
            // 重複チェック.
            if (!checkDuplicate(form.getCode(), form.getId(), form.getKey())) {
                result = false;
            }

        } else {

            // アカウントユーザー取得.
            form.setData(auth.getCoopAcc(form.getSearchCond().getSelected_id(), form.getSearchCond().getSelected_sub_id()));

            if (CommonModel.MODE_UPD.equals(form.getMode())) {
                if (!SaveFlg.SAVE.equals(form.getData().getSave_flg())) {
                    //----------------
                    name = "ログインID";
                    selector = "#id";
                    value = form.getId();
                    if (!checker.checkLoginId(value, true, name, selector)) { result = false; }
                    //----------------
                    name = "パスワード";
                    selector = "#key";
                    value = form.getKey();
                    if (!checker.checkPassword(value, true, name, selector)) { result = false; }

                }
                //----------------
                name = "ログイン情報を保持する";
                selector = "#save";
                value = form.getSave();
                if (!checker.checkKind(value, false, SaveFlg.values(), name, selector)) { result = false; }

            } else if (CommonModel.MODE_DEL.equals(form.getMode())) {
                if (!checkOwner(form.getSearchCond().getSelected_id(), form.getSearchCond().getSelected_sub_id())) {
                // オーナーチェック.
                    result = false;
                }
            }

        }
        return result;
    }

    /**
     * グループアカウントの連携処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(final ChangeAccountForm form) throws Exception {
        boolean result = false;
        if (!ReUtil.isEmpty(form.getCode()) && !ReUtil.isEmpty(form.getId()) && !ReUtil.isEmpty(form.getKey())) {
            try {
                dao.begin();

                // グループアカウント追加.
                if (auth.addAccUser(form.getCode(), form.getId(), form.getKey(), form.getSave())) {
                    // ログイン情報更新.
                    auth.doReLoadLoginInfo();
                    result = true;
                    dao.commit();
                } else {
                    dao.rollback();
                }

            } catch (SimpleDaoException e) {
                dao.rollback();
                throw e;
            }
        }
        if (result) {
            addMessage(msg.getMessage("I10010"));
        } else {
            addMessage(msg.getMessage("E10011"));
        }
        return result;
    }

    /**
     * グループアカウントの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final ChangeAccountForm form) throws Exception {
        boolean result = false;
        String group_id = form.getData().getRoot_group_id();
        String user_id = form.getData().getUser_id();
        if (!ReUtil.isEmpty(group_id) && !ReUtil.isEmpty(user_id)) {
            if (group_id.equals(auth.getLogin_group_id()) && user_id.equals(auth.getLogin_user_id())) {
                // 同じ場合はスキップ.
                return result;
            }
            try {
                dao.begin();

                // グループアカウント変更.
                if (auth.changeAccUser(form.getData(), form.getId(), form.getKey(), form.getSave())) {
                    // ログイン情報更新.
                    auth.doReLoadLoginInfo();
                    result = true;
                    dao.commit();
                } else {
                    dao.rollback();
                }

            } catch (SimpleDaoException e) {
                dao.rollback();
                throw e;
            }
        }
        if (result) {
            addMessage(msg.getMessage("I10010"));
        } else {
            addMessage(msg.getMessage("E00005", PROC_NAME + "の変更"));
        }
        return result;
    }

    /**
     * グループアカウントの連携解除処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean deleteData(final ChangeAccountForm form) throws Exception {
        boolean result = false;
        String group_id = form.getData().getRoot_group_id();
        String user_id = form.getData().getUser_id();
        if (!ReUtil.isEmpty(group_id) && !ReUtil.isEmpty(user_id)) {
            try {
                dao.begin();

                // グループアカウント連携解除.
                if (auth.delAccUser(form.getData())) {
                    result = true;
                    dao.commit();
                } else {
                    dao.rollback();
                }

            } catch (SimpleDaoException e) {
                dao.rollback();
                throw e;
            }
        }
        if (result) {
            addMessage(msg.getMessage("I10011"));
        } else {
            addMessage(msg.getMessage("E00005", PROC_NAME + "の連携解除"));
        }
        return result;
    }



    //---------------------------------------------- [private] チェック処理.
    /**
     * 重複チェックを行います.
     * @param code 識別コード.
     * @param login_id ログインID。
     * @param password パスワード.
     * @return 結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkDuplicate(final String code, final String login_id, final String password) 
            throws SimpleDaoException, SQLException {
        AuthMUser user = auth.checkLoginIdAndPassword(login_id, password, UsrKbn.NORMAL.getValue(), code);
        if (user != null) {
            int count = dao.getCount(dao.getSql(AuthRAccCooperation.class, "check_duplicate")
                    , (st)->{
                        st.setString(1, auth.getLogin_account_id());
                        st.setString(2, auth.getLogin_root_group_id());
                        st.setString(3, user.getId());
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E00014", PROC_NAME));
                return false;
            }
        }
        return true;
    }

    /**
     * オーナーユーザーチェックを行います.
     * @param root_group_id 対象のルートグループID.
     * @param user_id 対象のユーザーID.
     * @return 結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkOwner(final String root_group_id, final String user_id) 
            throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(root_group_id)) {
            int count = dao.getCount(dao.getSql(AuthRAccCooperation.class, "check_owner")
                    , (st)->{
                        st.setString(1, auth.getLogin_account_id());
                        st.setString(2, root_group_id);
                        st.setString(3, user_id);
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E10005"));
                return false;
            }
        }
        return true;
    }

}
