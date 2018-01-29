package com.res_system.re_employee_manager.model.employee_search;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.CommonModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.commons.model.entities.CommonEntity;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.commons.model.kind.EmStatKind;
import com.res_system.re_employee_manager.commons.model.kind.OnOffKind;

/**
 * <pre>
 * 社員情報検索[ 一覧 ]画面 モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class EmployeeSearchModel {

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

    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel commonModel;



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
     * 画面表示データを設定します.
     * @param form 対象データ.
     * @throws SimpleDaoException
     */
    public void setShowFormData(final EmployeeSearchForm form) throws SimpleDaoException {
        form.setGroupItemlist(commonModel.getGroupList());
    }

    /**
     * 検索処理.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean search(final EmployeeSearchForm form) throws Exception {
        boolean result = true;

        // 検索キーワード取得.
        String[] keywords = dao.makeKeywords(form.getData().getKeyword());
        // 全状態を含むチェック.
        boolean isAll = !ReUtil.isEmpty(form.getData().getIs_all());
        // 総データ件数取得.
        int totalSize = getTotalSize(keywords, isAll);
        // 現在ページ取得.
        int page = ReUtil.toInt(form.getData().getPage(), 1);

        // データ取得.
        form.setList(getListData(keywords, isAll, page));
        form.getData().setPage(String.valueOf(page));
        form.getData().setTotal_size(String.valueOf(totalSize));
        form.getData().setTotal_page(String.valueOf((int)Math.ceil((double)totalSize / PAGE_SIZE)));
        if (totalSize <= 0) {
            addMessage(msgModel.getMessage("E00004", "指定の条件のデータ"));
            result = false;
        }
        return result;
    }

    /**
     * 更新処理.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean update(final EmployeeSearchForm form) throws Exception {
        boolean result = true;
        try {
            dao.begin();

            List<EmployeeInfo> list = form.getList();
            for (EmployeeInfo data : list) {
                if (OnOffKind.ON.equals(data.getIs_change())) {
                    if (OnOffKind.ON.equals(data.getIs_delete())) {
                        // 削除.
                        commonModel.deleteEmployee(ReUtil.toLong(data.getEmployee_id()));
                    } else {
                        // 更新.
                        EmpSearchMEmployee entity = new EmpSearchMEmployee();
                        entity.setDefault_group_id(ReUtil.toLong(data.getGroup_id()));
                        entity.setUpdated_id(authModel.getAccountId());
                        entity.setId(ReUtil.toLong(data.getEmployee_id()));
                        result = (dao.update(entity, "update_group_id") > 0);
                        if (!result) {
                            break;
                        }
                    }
                }
            }

            if (result) {
                dao.commit();
            } else {
                dao.rollback();
                result = false;
                addMessage(msgModel.getMessage("E00005", "変更内容の反映"));
            }
        } catch (Exception e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * 完了処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean complete(final EmployeeSearchForm form) throws Exception {
        addMessage(msgModel.getMessage("I20001", "変更内容の反映"));
        return search(form);
    }



    //---------------------------------------------- [private].
    /**
     * 総データ件数取得.
     * @param keywords 検索キーワード.
     * @param isAll 全状態を含むチェック.
     * @return 総データ件数.
     * @throws SimpleDaoException
     */
    private int getTotalSize(String[] keywords, boolean isAll) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS `count` FROM (");
        sql.append(dao.getSql(EmployeeInfo.class, "find_employee_list"));
        sql.append(") VW");
        makeWhere(sql, keywords, isAll); //-- 条件設定 --//

        // SQL実行.
        CommonEntity entity = dao.executeQuery(CommonEntity.class
                , sql.toString()
                , (st) -> {
                    st.setLong(1, authModel.getSelectedGroupId());
                    dao.setKeywordsParam(st, 2, keywords); });
        return entity.getCount();
    }

    /**
     * データ取得.
     * @param keywords 検索キーワード.
     * @param isAll 全状態を含むチェック.
     * @param page 現在ページ.
     * @return 取得データ.
     * @throws SimpleDaoException
     */
    private List<EmployeeInfo> getListData(String[] keywords, boolean isAll, int page) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (");
        sql.append(dao.getSql(EmployeeInfo.class, "find_employee_list"));
        sql.append(") VW");
        makeWhere(sql, keywords, isAll); //-- 条件設定 --//
        sql.append(dao.getSql(EmployeeInfo.class, "find_employee_list_order")); //-- ソート設定 --//
        sql.append(" LIMIT ").append((page - 1) * PAGE_SIZE).append(",").append(PAGE_SIZE); //-- 件数設定 --//

        // SQL実行.
        return dao.executeQueryList(EmployeeInfo.class
                , sql.toString()
                , (st) -> {
                    st.setLong(1, authModel.getSelectedGroupId());
                    dao.setKeywordsParam(st, 2, keywords); });
    }

    /**
     * Where句作成.
     * @param sql SQL.
     * @param keywords 検索キーワード.
     * @param isAll 全状態を含むチェック.
     */
    private void makeWhere(StringBuilder sql, String[] keywords, boolean isAll) {
        sql.append(" WHERE 1 = 1");
        // 検索条件（キーワード）作成.
        if (!isAll) {
            sql.append(" AND `emp_status` IN");
            sql.append("('").append(EmStatKind.TEMP_REGIST.getValue()).append("'");
            sql.append(",'").append(EmStatKind.IN_OFFICE.getValue()).append("'");
            sql.append(")");
        }
        // 検索条件（キーワード）作成.
        String where = dao.makeKeywordsWhere(keywords);
        if (!ReUtil.isEmpty(where)) {
            sql.append(" AND ").append(where);
        }
    }

}
