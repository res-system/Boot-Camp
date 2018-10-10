package com.res_system.re_emp_manager.model.select_group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.kind.GrpStat;
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * グループ選択処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class SelectGroupModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "グループ";
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
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final SelectGroupForm form) throws Exception {
        boolean result = true;
        // 検索キーワード取得.
        SearchCondition searchCond = form.getSearchCond();
        // 検索.
        List<SelectGroupMGroup> list = 
                common.findList(SelectGroupMGroup.class
                        , searchCond
                        , dao.getSql(SelectGroupMGroup.class, "find_list")
                        , common.makeWhere(searchCond, dao.getSql(SelectGroupMGroup.class, "find_status"))
                        , common.getOrder(searchCond
                                , SelectGroupMGroup.class, SelectGroupMGroup.SORT_MIN, SelectGroupMGroup.SORT_MAX)
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
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final SelectGroupForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";
        boolean isRequired = false;

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        // 最大件数チェック.
        if (!checkSize()) { result = false; }

        //----------------
        name = "グループ名";
        selector = "#modal_select_group_name";
        value = form.getData().getName();
        isRequired = !(InfType.SPACE.equals(form.getData().getName()));
        if (!checker.checkFullText(value, isRequired, 40, name, selector)) { result = false; }

        return result;
    }


    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(final SelectGroupForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();

            SelectGroupMGroup data = form.getData();
            data.setRoot_group_id(auth.getLogin_root_group_id());
            data.setGrp_status(GrpStat.ENABLE.getValue());
            common.setUpdateInfo(data);
            result = (dao.insert(data) > 0);
            if (result) {
                data.setId(dao.getLastInsertId());
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



    //---------------------------------------------- [private].
    /**
     * 件数チェックを行います.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    private boolean checkSize() throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(SelectGroupMGroup.class, "check_count")
                , (st)->{ st.setString(1, auth.getLogin_root_group_id()); });
        if (count >= MAX_SIZE) {
            addMessage(msg.getMessage("E00015", MAX_SIZE));
            return false;
        }
        return true;
    }

}
