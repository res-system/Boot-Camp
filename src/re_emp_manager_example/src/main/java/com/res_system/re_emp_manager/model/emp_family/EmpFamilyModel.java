package com.res_system.re_emp_manager.model.emp_family;

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
import com.res_system.re_emp_manager.commons.kind.Living;
import com.res_system.re_emp_manager.commons.kind.Sex;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.employee.EmployeeConvModel;

/**
 * 社員家族情報管理処理用 モデルクラス.
 * @author res.
 */
@RequestScoped
public class EmpFamilyModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "社員家族情報管理";
    /** 最大レコード数. */
    private static final int MAX_SIZE = 20;



    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel common;
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msg;
    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checker;
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
     * 初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpFamilyForm init(final EmpFamilyForm form) throws Exception {
        conv.end();
        findList(form);
        return form;
    }

    /**
     * 画面表示初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpFamilyForm initShow(final EmpFamilyForm form) throws Exception {
        findList(form);
        return form;
    }


    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final EmpFamilyForm form) throws Exception {
        form.getData().setUser_id(conv.getSelectedUserId());
        return doFindList(form);
    }

    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final EmpFamilyForm form) throws Exception {
        form.getData().setUser_id(conv.getSelectedUserId());
        return doFindData(form);
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkSize(final EmpFamilyForm form) throws SimpleDaoException, SQLException {
        form.getData().setUser_id(conv.getSelectedUserId());
        return doCheckSize(form);
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final EmpFamilyForm form) throws SimpleDaoException, SQLException {
        form.getData().setUser_id(conv.getSelectedUserId());
        return doCheckInput(form);
    }


    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(final EmpFamilyForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            result = doInsertData(form);
            if (result) {
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

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final EmpFamilyForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            result = doUpdateData(form);
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の更新"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の更新"));
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
    public boolean deleteData(final EmpFamilyForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            result = doDeleteData(form);
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の削除"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の削除"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            if (dao.isForeignKeyError(e)) {
                addMessage(msg.getMessage("E00013"));
            } else {
                throw e;
            }
        }
        return result;
    }



    //---------------------------------------------- [protected] 内部処理.
    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    protected boolean doFindList(final EmpFamilyForm form) throws Exception {
        boolean result = true;
        // 検索.
        List<EmpFamilyData> list = dao.executeQueryList(EmpFamilyData.class
                , dao.getSql(EmpFamilyData.class, "find_list")
                , (st)->{
                    st.setString(1, form.getData().getUser_id());
                });
        form.setList(list);
        if (list.size() <= 0) {
            addMessage(msg.getMessage("E00012"));
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
    protected boolean doFindData(final EmpFamilyForm form) throws Exception {
        form.setData(dao.findByKey(EmpFamilyData.class
                , form.getData().getUser_id(), form.getData().getSeq()));
        return (form.getData() != null);
    }


    /**
     * 件数チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    protected boolean doCheckSize(final EmpFamilyForm form) throws SimpleDaoException, SQLException {
        int count = dao.getCount(dao.getSql(EmpFamilyData.class, "check_count")
                , (st)->{ st.setString(1, form.getData().getUser_id()); });
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
    protected boolean doCheckInput(final EmpFamilyForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        // キーチェック.
        if (!CommonModel.MODE_ADD.equals(form.getMode())) {
            //----------------
            name = "ユーザーID";
            selector = "#data_id";
            value = form.getData().getUser_id();
            if (!checker.checkRequired(value, name, selector)) {
            // 必須チェック.
                result = false;
            } else if (!common.checkIdUser(value)) { 
            // 正しさチェック.
                addMessage(msg.getMessage("E00002"));
                result = false; 
            }
            //----------------
            name = "連番";
            selector = "#data_seq";
            value = form.getData().getSeq();
            if (!checker.checkRequired(value, name, selector)) { result = false; }
            //----------------
            name = "個人ID";
            selector = "#data_personal_id";
            value = form.getData().getPersonal_id();
            if (!checker.checkRequired(value, name, selector)) {
            // 必須チェック.
                result = false;
            } else if (!common.checkIdFmlPersonal(form.getData().getUser_id(), form.getData().getSeq(), value)) { 
            // 正しさチェック.
                addMessage(msg.getMessage("E00002"));
                result = false; 
            }
        } else {
            //----------------
            // サイズチェック.
            if (!doCheckSize(form)) { result = false; }
        }

        //----------------
        name = "続柄";
        selector = "#data_relationship";
        value = form.getData().getRelationship();
        if (!checker.checkFullText(value, true, 16, name, selector)) { result = false; }
        if (!checker.checkSpace(value, false, name, selector)) { result = false; }
        //----------------
        name = "同居区分";
        selector = "#data_living";
        value = form.getData().getLiving();
        if (!checker.checkKind(value, true, Living.values(), name, selector)) { result = false; }
        //----------------
        name = "氏名（姓）";
        selector = "#data_family_name";
        value = form.getData().getFamily_name();
        if (!checker.checkFullText(value, true, 40, name, selector)) { result = false; }
        if (!checker.checkSpace(value, false, name, selector)) { result = false; }
        //----------------
        name = "氏名（名）";
        selector = "#data_first_name";
        value = form.getData().getFirst_name();
        if (!checker.checkFullText(value, true, 40, name, selector)) { result = false; }
        if (!checker.checkSpace(value, false, name, selector)) { result = false; }
        //----------------
        name = "氏名カナ（名）";
        selector = "#data_family_name_kana";
        value = form.getData().getFamily_name_kana();
        if (!checker.checkFullKanaText(value, false, 80, name, selector)) { result = false; }
        //----------------
        name = "氏名カナ（姓）";
        selector = "#data_first_name_kana";
        value = form.getData().getFirst_name_kana();
        if (!checker.checkFullKanaText(value, false, 80, name, selector)) { result = false; }
        //----------------
        name = "性別";
        selector = "#data_sex";
        value = form.getData().getSex();
        if (!checker.checkKind(value, false, Sex.values(), name, selector)) { result = false; }
        //----------------
        name = "生年月日";
        selector = "#data_birthday";
        value = form.getData().getBirthday();
        if (!checker.checkDate(value, false, name, selector)) { result = false; }
        //----------------
        name = "マイナンバー";
        selector = "#data_mynumber";
        value = form.getData().getMynumber();
        if (!checker.checkNumCode(value, false, 14, name, selector)) { result = false; }
        //----------------
        name = "備考";
        selector = "#data_memo";
        value = form.getData().getMemo();
        if (!checker.checkFullText(value, false, 200, name, selector)) { result = false; }

        return result;
    }


    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    protected boolean doInsertData(final EmpFamilyForm form) throws Exception {
        boolean result = false;

        EmpFamilyData data = form.getData();

        // g_personal.
        EmpFamilyGPersonal personal = new EmpFamilyGPersonal();
        personal.setFamily_name(data.getFamily_name());
        personal.setFirst_name(data.getFirst_name());
        personal.setFamily_name_kana(data.getFamily_name_kana());
        personal.setFirst_name_kana(data.getFirst_name_kana());
        personal.setSex(data.getSex());
        personal.setBirthday(ReUtil.toNull(data.getBirthday()));
        personal.setMynumber(data.getMynumber());
        common.setUpdateInfo(personal);
        result = (dao.insert(personal) > 0);
        if (result) {
            // s_emp_family.
            int max_seq = dao.getMaxSeq(dao.getSql(EmpFamilyData.class, "get_max_seq")
                    , (st)->{ st.setString(1, form.getData().getUser_id()); });
            data.setSeq(String.valueOf(max_seq));
            data.setPersonal_id(dao.getLastInsertId());
            common.setUpdateInfo(data);
            result = (dao.insert(data) > 0);
        }

        return result;
    }


    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    protected boolean doUpdateData(final EmpFamilyForm form) throws Exception {
        boolean result = false;

        EmpFamilyData data = form.getData();

        // g_personal.
        EmpFamilyGPersonal personal = new EmpFamilyGPersonal();
        personal.setId(data.getPersonal_id());
        personal.setFamily_name(data.getFamily_name());
        personal.setFirst_name(data.getFirst_name());
        personal.setFamily_name_kana(data.getFamily_name_kana());
        personal.setFirst_name_kana(data.getFirst_name_kana());
        personal.setSex(data.getSex());
        personal.setBirthday(ReUtil.toNull(data.getBirthday()));
        personal.setMynumber(data.getMynumber());
        common.setUpdateInfo(personal);
        result = (dao.update(personal) > 0);
        if (result) {
            // s_emp_family.
            common.setUpdateInfo(data);
            result = (dao.update(data) > 0);
        }

        return result;
    }

    /**
     * データの削除処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean doDeleteData(final EmpFamilyForm form) throws Exception {
        boolean result = false;

        EmpFamilyData data = form.getData();

        // s_emp_family.
        result = (dao.delete(data) > 0);
        if (result) {
            // g_personal.
            EmpFamilyGPersonal personal = new EmpFamilyGPersonal();
            personal.setId(data.getPersonal_id());
            result = (dao.delete(personal) > 0);
        }

        return result;
    }



    //---------------------------------------------- [private].

}
