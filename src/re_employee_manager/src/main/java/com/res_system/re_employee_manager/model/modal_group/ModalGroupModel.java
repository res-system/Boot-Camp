package com.res_system.re_employee_manager.model.modal_group;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.commons.model.entities.AuthCTLogin;
import com.res_system.re_employee_manager.commons.model.entities.CommonEntity;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.commons.model.kind.AuthKind;

/**
 * <pre>
 * グループ選択ダイアログ モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class ModalGroupModel {

    //---------------------------------------------- const [public].
    /** ページ毎表示件数. */
    public static final int PAGE_SIZE = 8;



    //---------------------------------------------- [private] モデルクラス.
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;



    //---------------------------------------------- properies [private].
    /** メッセージリスト. */
    private List<Message> messageList;

    //-- setter / getter. --//
    /** メッセージリスト を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージリスト を設定します. */
    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }
    /** メッセージリスト を追加します. */
    public void addMessage(Message message) { this.messageList.add(message); }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = new ArrayList<>();
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] 業務処理.
    /**
     * 検索処理.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean search(final ModalGroupForm form) throws Exception {
        boolean result = true;

        // 選択グループ設定.
        form.setModal_group_selected_group_id(authModel.getSelectedGroupId().toString());
        form.setModal_group_selected_group_name(authModel.getSelectedGroupName());

        // SQL.
        String sqlName = "";
        if (AuthKind.SYSTEM.equals(authModel.getDefaultAuthorityId())) {
            sqlName = "find_group_list_for_system";
        } else if (authModel.IsRootManager()) {
            sqlName = "find_group_list_for_root";
        } else {
            sqlName = "find_group_list_for_account";
        }
        // 検索キーワード取得.
        String[] keywords = dao.makeKeywords(form.getModal_group_keyword());
        // 総データ件数取得.
        int totalSize = getTotalSize(sqlName, keywords);
        // 現在ページ取得.
        int page = ReUtil.toInt(form.getModal_group_page(), 1);

        // データ取得.
        form.setList(getListData(sqlName, keywords, page));
        form.setModal_group_page(String.valueOf(page));
        form.setModal_group_total_size(String.valueOf(totalSize));
        form.setModal_group_total_page(String.valueOf((int)Math.ceil((double)totalSize / PAGE_SIZE)));
        if (form.getList().size() <= 0) {
            addMessage(msgModel.getMessage("E00004", "指定の条件のデータ"));
            result = false;
        }
        return result;
    }

    /**
     * グループの変更処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean doChangeGroup(final ModalGroupForm form) throws Exception {
        try {
            dao.begin();
            if (!ReUtil.isEmpty(form.getModal_group_selected_group_id())) {
                AuthCTLogin entity = new AuthCTLogin();
                entity.setSelected_group_id(ReUtil.toLong(form.getModal_group_selected_group_id()));
                entity.setToken(authModel.getToken());
                if (dao.update(entity, "update_group") > 0) {
                    authModel.getLoginInfo().setSelected_group_id(ReUtil.toLong(form.getModal_group_selected_group_id()));
                    authModel.getLoginInfo().setSelected_group_name(form.getModal_group_selected_group_name());
                    dao.commit();
                    return true;
                }
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        dao.rollback();
        addMessage(msgModel.getMessage("E00005", "グループの変更処理").addSelector("#login_id").addSelector("#password"));
        return false;
    }



    //---------------------------------------------- [private].
    /**
     * 総データ件数取得.
     * @param sqlName SQL名.
     * @param keywords 検索キーワード.
     * @param isAll 全状態を含むチェック.
     * @return 総データ件数.
     * @throws SimpleDaoException
     */
    private int getTotalSize(String sqlName, String[] keywords) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS `count` FROM (");
        sql.append(dao.getSql(MdGrpCMGroup.class, sqlName));
        sql.append(") VW");
        makeWhere(sql, keywords); //-- 条件設定 --//

        // SQL実行.
        CommonEntity entity = dao.executeQuery(CommonEntity.class
                , sql.toString()
                , (st) -> {
                    int index = 1;
                    if (AuthKind.SYSTEM.equals(authModel.getDefaultAuthorityId())) {
                    } else if (authModel.IsRootManager()) {
                        st.setLong(index++, authModel.getSelectedGroupId());
                    } else {
                        st.setLong(index++, authModel.getAccountId());
                    }
                    dao.setKeywordsParam(st, index, keywords); });
        return entity.getCount();
    }

    /**
     * データ取得.
     * @param sqlName SQL名.
     * @param keywords 検索キーワード.
     * @param page 現在ページ.
     * @return 取得データ.
     * @throws SimpleDaoException
     */
    private List<MdGrpCMGroup> getListData(String sqlName, String[] keywords, int page) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (");
        sql.append(dao.getSql(MdGrpCMGroup.class, sqlName));
        sql.append(") VW");
        makeWhere(sql, keywords); //-- 条件設定 --//
        sql.append(dao.getSql(EmployeeInfo.class, "find_group_list_order")); //-- ソート設定 --//
        sql.append(" LIMIT ").append((page - 1) * PAGE_SIZE).append(",").append(PAGE_SIZE); //-- 件数設定 --//

        // SQL実行.
        return dao.executeQueryList(MdGrpCMGroup.class
                , sql.toString()
                , (st) -> {
                    int index = 1;
                    if (AuthKind.SYSTEM.equals(authModel.getDefaultAuthorityId())) {
                    } else if (authModel.IsRootManager()) {
                        st.setLong(index++, authModel.getSelectedGroupId());
                    } else {
                        st.setLong(index++, authModel.getAccountId());
                    }
                    dao.setKeywordsParam(st, index, keywords); });
    }

    /**
     * Where句作成.
     * @param sql SQL.
     * @param keywords 検索キーワード.
     * @param isAll 全状態を含むチェック.
     */
    private void makeWhere(StringBuilder sql, String[] keywords) {
        sql.append(" WHERE 1 = 1");
        // 検索条件（キーワード）作成.
        String where = dao.makeKeywordsWhere(keywords);
        if (!ReUtil.isEmpty(where)) {
            sql.append(" AND ").append(where);
        }
    }

}
