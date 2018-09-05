package com.res_system.re_emp_manager.model.emp_search;

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
import com.res_system.re_emp_manager.commons.kind.CheckKbn;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.employee.EmployeeConvModel;

/**
 * 社員情報検索処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class EmpSearchModel implements IMessage {

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
    private MessageModel msgModel;

    /** 会話処理 モデルクラス. */
    @Inject 
    private EmployeeConvModel conv;



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
    public EmpSearchForm initIndex(final EmpSearchForm form) throws Exception {
        conv.end();
        form.getSearchCond().setKeyword("");
        form.getSearchCond().setIs_all(CheckKbn.OFF.getValue());
        findList(form);
        return form;
    }

    /**
     * 一覧画面の戻り時の初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpSearchForm initReturn(final EmpSearchForm form) throws Exception {
        if (conv.getSearchCond() != null) {
            form.setSearchCond(conv.getSearchCond());
            findList(form);
            return form;
        } else {
            return initIndex(form);
        }
    }


    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final EmpSearchForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        form.getSearchCond().setKeywords(dao.makeKeywords(form.getSearchCond().getKeyword()));
        // 総データ件数取得.
        int totalSize = getTotalSize(form.getSearchCond());
        // データ取得.
        form.setList(getListData(form.getSearchCond()));
        // 件数設定.
        form.getSearchCond().setTotal_size(String.valueOf(totalSize));
        form.getSearchCond().setTotal_page(String.valueOf(common.getTotalPageSize(totalSize, CommonModel.PAGE_SIZE)));
        if (totalSize <= 0) {
            addMessage(msgModel.getMessage("E00012"));
            result = false;
        }
        return result;
    }



    //---------------------------------------------- [private].
    /**
     * 総データ件数取得.
     * @param searchCond 検索条件.
     * @return 総データ件数.
     * @throws SimpleDaoException
     */
    private int getTotalSize(final SearchCondition searchCond) throws SimpleDaoException {
        return common.getTotalSize(searchCond
                , dao.getSql(EmpSearchData.class, "find_list")
                , common.makeWhere(searchCond, dao.getSql(EmpSearchData.class, "find_status"))
                , (st) -> {
                    st.setString(1, auth.getLogin_group_id());
                    st.setString(2, auth.getLogin_group_id());
                    dao.setKeywordsParam(st, 3, searchCond.getKeywords()); 
                });
    }

    /**
     * データ取得.
     * @param searchCond 検索条件.
     * @return 取得データ.
     * @throws SimpleDaoException
     */
    private List<EmpSearchData> getListData(final SearchCondition searchCond) throws SimpleDaoException {
        return common.getListData(EmpSearchData.class, searchCond
                , dao.getSql(EmpSearchData.class, "find_list")
                , common.makeWhere(searchCond, dao.getSql(EmpSearchData.class, "find_status"))
                , getOrder(searchCond)
                , CommonModel.PAGE_SIZE
                , (st) -> {
                    st.setString(1, auth.getLogin_group_id());
                    st.setString(2, auth.getLogin_group_id());
                    dao.setKeywordsParam(st, 3, searchCond.getKeywords()); 
                });
    }

    /**
     * 並び順取得.
     * @param searchCond 検索条件.
     * @return 並び順.
     * @throws SimpleDaoException
     */
    private String getOrder(final SearchCondition searchCond) throws SimpleDaoException {
        String order;
        int sort = ReUtil.toInt(searchCond.getSort(), 0);
        if (EmpSearchData.SORT_MIN <= sort && sort <= EmpSearchData.SORT_MAX) {
            order = dao.getSql(EmpSearchData.class, "find_list_order_" + searchCond.getSort());
        } else {
            order = dao.getSql(EmpSearchData.class, "find_list_order");
        }
        return order;
    }

}
