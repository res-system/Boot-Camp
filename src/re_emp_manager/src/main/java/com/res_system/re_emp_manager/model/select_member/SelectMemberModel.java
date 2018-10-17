package com.res_system.re_emp_manager.model.select_member;

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

/**
 * メンバー選択処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class SelectMemberModel implements IMessage {

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
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final SelectMemberForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<SelectMemberData> list = 
                common.findList(SelectMemberData.class
                        , searchCond
                        , dao.getSql(SelectMemberData.class, "find_list")
                        , common.makeWhere(searchCond, dao.getSql(SelectMemberData.class, "find_status"))
                        , common.getOrder(searchCond
                                , SelectMemberData.class, SelectMemberData.SORT_MIN, SelectMemberData.SORT_MAX)
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

}
