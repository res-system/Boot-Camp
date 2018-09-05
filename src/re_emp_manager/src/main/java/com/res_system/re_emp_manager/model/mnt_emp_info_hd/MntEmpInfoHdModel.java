package com.res_system.re_emp_manager.model.mnt_emp_info_hd;

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
import com.res_system.re_emp_manager.commons.kind.CheckKbn;
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.kind.ReqFlg;
import com.res_system.re_emp_manager.commons.kind.Stat;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * 社員情報ヘッダーマスタメンテナンス処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class MntEmpInfoHdModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "社員情報ヘッダー";
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
    private MessageModel msgModel;
    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checkerModel;



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
    public MntEmpInfoHdForm initIndex(final MntEmpInfoHdForm form) throws Exception {
        // 初期化.
        form.getSearchCond().setKeyword("");
        form.getSearchCond().setIs_all(CheckKbn.OFF.getValue());
        findList(form);
        return form;
    }


    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final MntEmpInfoHdForm form) throws Exception {
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

    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final MntEmpInfoHdForm form) throws Exception {
        form.setData(dao.findByKey(MntEmpInfoHdData.class, form.getData().getId()));
        return (form.getData() != null);
    }

    /**
     * 最大表示順を取得します.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean getMaxSeq(final MntEmpInfoHdForm form) throws Exception {
        String maxSeq = String.valueOf(dao.getMaxSeq(dao.getSql(MntEmpInfoHdData.class, "get_max_seq")
                , (st)->{ st.setString(1, auth.getLogin_root_group_id()); }));
        form.setMaxSeq(maxSeq);
        return true;
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkSize(final MntEmpInfoHdForm form) throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(MntEmpInfoHdData.class, "check_count")
                , (st)->{ st.setString(1, auth.getLogin_root_group_id()); });
        if (count >= MAX_SIZE) {
            addMessage(msgModel.getMessage("E00015", MAX_SIZE));
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
    public boolean checkInput(final MntEmpInfoHdForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";
        boolean isRequired = false;

        // 入力チェックモデルにメッセージリストを設定する.
        checkerModel.setMessageList(messageList);

        if (!CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "ID";
            selector = "#data_id";
            value = form.getData().getId();
            if (!checkerModel.checkRequired(value, name, selector)) { result = false; }
        } else {
            if (!checkSize(form)) { result = false; }
        }

        //----------------
        name = "表示順";
        selector = "#data_seq";
        value = form.getData().getSeq();
        if (!checkerModel.checkHalfNumeric(value, true, 1, MAX_SIZE, name, selector)) { result = false; }
        if (!checkSeq(value, form.getData().getId(), name, selector)) { result = false; }
        //----------------
        name = "ヘッダラベル";
        selector = "#data_label";
        value = form.getData().getLabel();
        isRequired = !(InfType.SPACE.equals(form.getData().getRequired()));
        if (!checkerModel.checkFullText(value, isRequired, 40, name, selector)) { result = false; }
        //----------------
        name = "タイプ";
        selector = "#data_type";
        value = form.getData().getType();
        if (!checkerModel.checkKind(value, true, InfType.values(), name, selector)) { result = false; }
        //----------------
        name = "必須フラグ";
        selector = "#data_required";
        value = form.getData().getRequired();
        if (!checkerModel.checkKind(value, true, ReqFlg.values(), name, selector)) { result = false; }
        //----------------
        name = "長さ";
        selector = "#data_maxlength";
        value = form.getData().getMaxlength();
        if (!checkerModel.checkHalfNumeric(value, true, 0, 999, name, selector)) { result = false; }
        //----------------
        name = "状態";
        selector = "#data_status";
        value = form.getData().getStatus();
        if (!checkerModel.checkKind(value, true, Stat.values(), name, selector)) { result = false; }
        //----------------
        name = "備考";
        selector = "#data_memo";
        value = form.getData().getMemo();
        if (!checkerModel.checkFullText(value, false, 200, name, selector)) { result = false; }

        return result;
    }

    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(final MntEmpInfoHdForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntEmpInfoHdData data = form.getData();
            data.setId(null);
            data.setRoot_group_id(auth.getLogin_root_group_id());
            common.setUpdateInfo(data);
            result = (dao.insert(data) > 0);
            if (result) {
                data.setId(dao.getLastInsertId());
                dao.commit();
                addMessage(msgModel.getMessage("I00002", PROC_NAME + "の追加"));
            } else {
                dao.rollback();
                addMessage(msgModel.getMessage("E00005", PROC_NAME + "の追加"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final MntEmpInfoHdForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntEmpInfoHdData data = form.getData();
            common.setUpdateInfo(data);
            result = (dao.update(data) > 0);
            if (result) {
                dao.commit();
                addMessage(msgModel.getMessage("I00002", PROC_NAME + "の更新"));
            } else {
                dao.rollback();
                addMessage(msgModel.getMessage("E00005", PROC_NAME + "の更新"));
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
    public boolean deleteData(final MntEmpInfoHdForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            MntEmpInfoHdData data = form.getData();
            result = (dao.delete(data) > 0);
            if (result) {
                dao.commit();
                addMessage(msgModel.getMessage("I00002", PROC_NAME + "の削除"));
            } else {
                dao.rollback();
                addMessage(msgModel.getMessage("E00005", PROC_NAME + "の削除"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            if (dao.isForeignKeyError(e)) {
                addMessage(msgModel.getMessage("E00013"));
            } else {
                throw e;
            }
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
                , dao.getSql(MntEmpInfoHdData.class, "find_list")
                , common.makeWhere(searchCond, dao.getSql(MntEmpInfoHdData.class, "find_status"))
                , (st) -> {
                    st.setString(1, auth.getLogin_root_group_id());
                    dao.setKeywordsParam(st, 2, searchCond.getKeywords()); 
                });
    }

    /**
     * データ取得.
     * @param searchCond 検索条件.
     * @return 取得データ.
     * @throws SimpleDaoException
     */
    private List<MntEmpInfoHdData> getListData(final SearchCondition searchCond) throws SimpleDaoException {
        return common.getListData(MntEmpInfoHdData.class, searchCond
                , dao.getSql(MntEmpInfoHdData.class, "find_list")
                , common.makeWhere(searchCond, dao.getSql(MntEmpInfoHdData.class, "find_status"))
                , getOrder(searchCond)
                , CommonModel.PAGE_SIZE
                , (st) -> {
                    st.setString(1, auth.getLogin_root_group_id());
                    dao.setKeywordsParam(st, 2, searchCond.getKeywords()); 
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
        if (MntEmpInfoHdData.SORT_MIN <= sort && sort <= MntEmpInfoHdData.SORT_MAX) {
            order = dao.getSql(MntEmpInfoHdData.class, "find_list_order_" + searchCond.getSort());
        } else {
            order = dao.getSql(MntEmpInfoHdData.class, "find_list_order");
        }
        return order;
    }

    /**
     * 表示順重複チェックを行います.
     * @param seq 対象の連番.
     * @param id 対象のID.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkSeq(
            final String seq, final String id, final String name, final String selector
            ) throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(seq)) {
            int count = dao.getCount(dao.getSql(MntEmpInfoHdData.class, "check_seq")
                    , (st)->{
                        st.setString(1, auth.getLogin_root_group_id());
                        st.setString(2, seq);
                        st.setString(3, id);
                    });
            if (count > 0) {
                addMessage(msgModel.getMessage("E00014", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }


}
