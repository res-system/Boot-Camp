package com.res_system.re_emp_manager.model.emp_search;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
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
    private MessageModel msg;
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
        form.getSearchCond().reset();
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
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<EmpSearchData> list = 
                common.findList(EmpSearchData.class
                        , searchCond
                        , dao.getSql(EmpSearchData.class, "find_list")
                        , common.makeWhere(searchCond, dao.getSql(EmpSearchData.class, "find_status"))
                        , common.getOrder(searchCond
                                , EmpSearchData.class, EmpSearchData.SORT_MIN, EmpSearchData.SORT_MAX)
                        , CommonModel.PAGE_SIZE
                        , (st)->{
                            st.setString(1, auth.getLogin_group_id());
                            st.setString(2, auth.getLogin_group_id());
                            dao.setKeywordsParam(st, 3, searchCond.getKeywords()); 
                        });
        form.setList(list);
        if (searchCond.getTotalSize() <= 0L) {
            addMessage(msg.getMessage("E00012"));
            result = false;
        }
        return result;
    }

}
