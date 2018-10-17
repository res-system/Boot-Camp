package com.res_system.re_emp_manager.model.change_group;

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
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRGrpMember;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * グループ変更画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class ChangeGroupModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "グループ";



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
     * 初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public ChangeGroupForm init(final ChangeGroupForm form) throws Exception {
        form.setList(auth.getGroupList());
        return form;
    }

    /**
     * グループの変更を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public boolean changeGroup(final ChangeGroupForm form) throws Exception {
        boolean result = false;
        String group_id = form.getSearchCond().getSelected_id();
        if (!ReUtil.isEmpty(group_id)) {
            if (group_id.equals(auth.getLogin_group_id())) {
                return result;
            }
            try {
                dao.begin();
                if (auth.changeGroupId(group_id)) {
                    // ログイン情報更新.
                    auth.doReLoadLoginInfo();
                    dao.commit();
                    result = true;
                } else {
                    dao.rollback();
                }
            } catch (SimpleDaoException e) {
                dao.rollback();
                throw e;
            }
        }
        if (!result) {
            addMessage(msg.getMessage("E00005", PROC_NAME + "の変更"));
        }
        return result;
    }


    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final ChangeGroupForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<AuthRGrpMember> list = 
                common.findList(AuthRGrpMember.class
                        , searchCond
                        , dao.getSql(AuthRGrpMember.class, "find_list")
                        , common.makeWhere(searchCond, "")
                        , common.getOrder(searchCond
                                , AuthRGrpMember.class, AuthRGrpMember.SORT_MIN, AuthRGrpMember.SORT_MAX)
                        , CommonModel.PAGE_SIZE
                        , (st)->{
                            st.setString(1, auth.getLogin_user_id()); 
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
