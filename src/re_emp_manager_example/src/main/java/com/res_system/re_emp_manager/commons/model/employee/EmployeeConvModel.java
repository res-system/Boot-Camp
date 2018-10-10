package com.res_system.re_emp_manager.commons.model.employee;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.conv.ConvModel;

/**
 * 社員情報処理用会話スコープモデルクラス.
 * @author res.
 */
@SuppressWarnings("serial")
@ConversationScoped
public class EmployeeConvModel implements Serializable {

    //---------------------------------------------- [private] モデルクラス.
    /** 会話スコープ制御処理 モデルクラス. */
    @Inject 
    private ConvModel conv;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;


    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;



    //---------------------------------------------- properies [private].
    /** 検索条件. */
    private SearchCondition searchCond;

    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; setSelectedUserId(); }

    /** 保存パラメタ を取得します. */
    public String getParams(final String key) { return conv.getParams(key); }
    /** 保存パラメタ を設定します. */
    public void setParams(final String key, final String value) { conv.setParams(key, value); }

    /** 初期化を行います. */
    public void init() {
        this.searchCond = null;
    }


    //---------------------------------------------- [public] 業務処理.
    /**
     * 会話スコープを開始します.
     */
    public void begin() {
        if (conv.isTransient()) {
            conv.begin();
        }
    }

    /**
     * 会話スコープを開始します.
     * @param searchCond 検索条件.
     */
    public void begin(final SearchCondition searchCond) {
        if (conv.isTransient()) {
            conv.begin();
        }
        setSearchCond(searchCond);
    }

    /**
     * 会話スコープを終了します.
     */
    public void end() {
        init();
        conv.end();
    }


    /**
     * cidを取得します.
     * @return cid.
     */
    public String getCid() {
        return conv.getCid();
    }

    /**
     * cidをリクエストの属性に設定します.
     */
    public void setConversationId() {
        conv.setConversationId();
    }


    /**
     * 選択されたユーザーIDを設定します.
     */
    public void setSelectedUserId() {
        if (isSelectedUser()) {
            request.setAttribute("selected_emp_user", getSearchCond().getSelected_id());
        } else {
            request.setAttribute("selected_emp_user", null);
        }
    }

    /**
     * 選択されたユーザーIDを取得します.
     * <br />※ ユーザーIDがない場合はログイン情報から取得します.
     * @return ユーザーID.
     */
    public String getSelectedUserId() {
        String selectedUserId;
        if (isSelectedUser()) {
            selectedUserId = getSearchCond().getSelected_id();
        } else {
            selectedUserId = auth.getLogin_user_id();
        }
        return selectedUserId;
    }

    /**
     * 選択されたユーザーIDか判別します.
     * @return 判別結果.
     */
    public boolean isSelectedUser() {
        if (getSearchCond() != null && !ReUtil.isEmpty(getSearchCond().getSelected_id())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 社員データの取得を行います.
     * @return 社員データ.
     * @throws Exception
     */
    public EmployeeData findEmployeeData() throws Exception {
        return findEmployeeData(getSelectedUserId());
    }

    /**
     * 社員データの取得を行います.
     * @param user_id ユーザーID.
     * @return 社員データ.
     * @throws Exception
     */
    public EmployeeData findEmployeeData(final String user_id) throws Exception {
        return dao.findByKey(EmployeeData.class, user_id);
    }

}
