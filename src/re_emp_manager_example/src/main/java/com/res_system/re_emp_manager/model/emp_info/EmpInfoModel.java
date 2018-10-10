package com.res_system.re_emp_manager.model.emp_info;

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
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.kind.ReqFlg;
import com.res_system.re_emp_manager.commons.kind.Sex;
import com.res_system.re_emp_manager.commons.kind.Sitch;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.employee.EmployeeConvModel;

/**
 * 社員個人情報管理処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class EmpInfoModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "社員個人情報管理";



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
    public EmpInfoForm init(final EmpInfoForm form) throws Exception {
        conv.end();
        findData(form);
        return form;
    }

    /**
     * 画面表示初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpInfoForm initShow(final EmpInfoForm form) throws Exception {
        findData(form);
        return form;
    }


    /**
     * 入力画面表示初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpInfoForm initInput(final EmpInfoForm form) throws Exception {
        conv.begin();
        findData(form);
        return form;
    }

    /**
     * 更新完了初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpInfoForm initComplete(final EmpInfoForm form) throws Exception {
        findData(form);
        addMessage(msg.getMessage("I00002", PROC_NAME + "の更新"));
        return form;
    }


    /**
     * 選択処理初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public EmpInfoForm initNext(final EmpInfoForm form) throws Exception {
        conv.begin(form.getSearchCond());
        findData(form);
        return form;
    }


    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final EmpInfoForm form) throws Exception {
        form.getData().setId(conv.getSelectedUserId());
        return dofindData(form);
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final EmpInfoForm form) throws SimpleDaoException, SQLException {
        form.getData().setId(conv.getSelectedUserId());
        return doCheckInput(form);
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final EmpInfoForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            form.getData().setId(conv.getSelectedUserId());
            result = doUpdateData(form);
            if (result) {
                if (!conv.isSelectedUser()) {
                    // ログイン情報更新.
                    auth.doReLoadLoginInfo();
                }
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
     * 現住所の取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean getCurrentAddr(final EmpInfoForm form) throws Exception {
        if (form.getEmpAddrList().size() > 0) {
            form.setEmpAddrList(dao.findList(EmpInfoSEmpAddr.class, "get_current_addr"
                    , (st)->{ st.setString(1, form.getEmpAddrList().get(0).getAddr_id()); }));
            return (form.getEmpAddrList().size() > 0);
        }
        return false;
    }



    //---------------------------------------------- [protected] 内部処理.
    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    protected boolean dofindData(final EmpInfoForm form) throws Exception {
        //----------------
        // メインデータ.
        form.setData(dao.findByKey(EmpInfoData.class, form.getData().getId()));
        //----------------
        // 社員メールアドレスリスト.
        form.setEmpEmailList(dao.findList(EmpInfoSEmpEmail.class, "find_list"
                , (st)->{ st.setString(1, form.getData().getId()); }));
        //----------------
        // 社員電話番号リスト.
        form.setEmpTelList(dao.findList(EmpInfoSEmpTel.class, "find_list"
                , (st)->{ st.setString(1, form.getData().getId()); }));
        //----------------
        // 社員住所リスト.
        form.setEmpAddrList(dao.findList(EmpInfoSEmpAddr.class, "find_list"
                , (st)->{ st.setString(1, form.getData().getId()); }));
        //----------------
        // 社員サブ情報リスト.
        form.setEmpInfoList(dao.findList(EmpInfoSEmpInfo.class, "find_list"
                , (st)->{ 
                    st.setString(1, form.getData().getId());
                    st.setString(2, auth.getLogin_root_group_id());
                }));
        return (form.getData() != null);
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    protected boolean doCheckInput(final EmpInfoForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";
        int index = 0;
        String type = "";
        boolean isRequired = false;
        int maxlength = 0;

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        //----------------
        name = "ユーザーID";
        selector = "#data_id";
        value = form.getData().getId();
        if (!checker.checkRequired(value, name, selector)) {
        // 必須チェック.
            result = false;
        } else if (!common.checkIdUser(value)) { 
        // 正しさチェック.
            addMessage(msg.getMessage("E00002"));
            result = false; 
        }
        //----------------
        name = "個人ID";
        selector = "#data_personal_id";
        value = form.getData().getPersonal_id();
        if (!checker.checkRequired(value, name, selector)) {
        // 必須チェック.
            result = false;
        } else if (!common.checkIdEmpPersonal(form.getData().getId(), value)) { 
        // 正しさチェック.
            addMessage(msg.getMessage("E00002"));
            result = false; 
        }
        //----------------
        name = "社員番号";
        selector = "#data_employee_no";
        value = form.getData().getEmployee_no();
        if (!checker.checkCode(value, true, 16, name, selector)) { result = false; }
        if (!checkEmployeeNo(value, form.getData().getId(), name, selector)) { result = false; }
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
        name = "旧姓";
        selector = "#data_maiden_name";
        value = form.getData().getMaiden_name();
        isRequired = (!ReUtil.isEmpty(form.getData().getMaiden_name_kana()));
        if (!checker.checkFullText(value, isRequired, 40, name, selector)) { result = false; }
        if (!checker.checkSpace(value, false, name, selector)) { result = false; }
        //----------------
        name = "カナ旧姓";
        selector = "#data_maiden_name_kana";
        value = form.getData().getMaiden_name_kana();
        if (!checker.checkFullKanaText(value, false, 80, name, selector)) { result = false; }
        //----------------
        name = "就業状況";
        selector = "#data_situation";
        value = form.getData().getSituation();
        if (!checker.checkKind(value, true, Sitch.values(), name, selector)) { result = false; }
        //----------------
        name = "入社日";
        selector = "#data_hire_date";
        value = form.getData().getHire_date();
        isRequired = (!Sitch.PLANS.equals(form.getData().getSituation()));
        if (!checker.checkDate(value, isRequired, name, selector)) { result = false; }
        //----------------
        name = "退職日";
        selector = "#data_retirement_date";
        value = form.getData().getRetirement_date();
        isRequired = Sitch.RETIRING.equals(form.getData().getSituation());
        if (!checker.checkDate(value, isRequired, name, selector)) { result = false; }
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
        // 社員メールアドレスリスト.
        index = 0;
        for (EmpInfoSEmpEmail emp_email : form.getEmpEmailList()) {
            name = "メールアドレス (" + emp_email.getMemo() + ") ";
            selector = "#empEmailList_" + index + "_email_addr";
            value = emp_email.getEmail_addr();
            if (!checker.checkEmail(value, false, name, selector)) { result = false; }
            index++;
        }
        //----------------
        // 社員電話番号リスト.
        index = 0;
        for (EmpInfoSEmpTel emp_tel : form.getEmpTelList()) {
            name = "電話番号 (" + emp_tel.getMemo() + ") ";
            selector = "#empTelList_" + index + "_tel_no";
            value = emp_tel.getTel_no();
            if (!checker.checkTelNo(value, false, name, selector)) { result = false; }
            index++;
        }
        //----------------
        // 社員住所リスト.
        index = 0;
        for (EmpInfoSEmpAddr emp_addr : form.getEmpAddrList()) {
            if (!common.checkIdEmpAddr(form.getData().getId(), emp_addr.getSeq(), emp_addr.getAddr_id())) { 
            // 正しさチェック.
                addMessage(msg.getMessage("E00002"));
                result = false; 
            }
            //----------------
            name = emp_addr.getMemo() + " 郵便番号";
            selector = "#empAddrList_" + index + "_postal_code";
            value = emp_addr.getPostal_code();
            if (!checker.checkPostalCode(value, false, name, selector)) { result = false; }
            //----------------
            name = emp_addr.getMemo() + " 住所1";
            selector = "#empAddrList_" + index + "_addr1";
            value = emp_addr.getAddr1();
            if (!checker.checkFullText(value, false, 50, name, selector)) { result = false; }
            name = emp_addr.getMemo() + " 住所2";
            selector = "#empAddrList_" + index + "_addr2";
            value = emp_addr.getAddr2();
            if (!checker.checkFullText(value, false, 50, name, selector)) { result = false; }
            //----------------
            name = emp_addr.getMemo() + " 最寄り駅";
            selector = "#empAddrList_" + index + "_nearest_station";
            value = emp_addr.getNearest_station();
            if (!checker.checkFullText(value, false, 50, name, selector)) { result = false; }
            index++;
        }
        //----------------
        // 社員サブ情報リスト.
        index = 0;
        for (EmpInfoSEmpInfo emp_info : form.getEmpInfoList()) {
            if (!common.checkIdEmpInfoHd(emp_info.getEmp_info_hd_id())) { 
            // 正しさチェック.
                addMessage(msg.getMessage("E00002"));
                result = false; 
            }
            name = emp_info.getLabel();
            selector = "#empInfoList_" + index + "_value";
            value = emp_info.getValue();
            // check.
            type = emp_info.getType();
            isRequired = ReqFlg.REQUIRED.equals(emp_info.getRequired());
            maxlength = ReUtil.toInt(emp_info.getMaxlength(), 0);
            if (InfType.TEXT.equals(type)) {
                if (!checker.checkFullText(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.MEMO.equals(type)) {
                if (!checker.checkFullText(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.DATE.equals(type)) {
                if (!checker.checkDate(value, isRequired, name, selector)) { result = false; }
            } else if (InfType.NUMERIC.equals(type)) {
                if (!checker.checkHalfNumeric(value, isRequired, 0, 999999999, name, selector)) { result = false; }
            } else if (InfType.NUMBER.equals(type)) {
                if (!checker.checkHalfNum(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.ALPHA_NUMBER.equals(type)) {
                if (!checker.checkHalfAlpNum(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.KANA.equals(type)) {
                if (!checker.checkFullKanaText(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.TEL_NO.equals(type)) {
                if (!checker.checkTelNo(value, isRequired, name, selector)) { result = false; }
            } else if (InfType.EMAIL_ADDR.equals(type)) {
                if (!checker.checkEmail(value, isRequired, name, selector)) { result = false; }
            } else if (InfType.CODE.equals(type)) {
                if (!checker.checkCode(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.CODE_NUM.equals(type)) {
                if (!checker.checkNumCode(value, isRequired, maxlength, name, selector)) { result = false; }
            } else if (InfType.POSTAL_CODE.equals(type)) {
                if (!checker.checkPostalCode(value, isRequired, name, selector)) { result = false; }
            }
            index++;
        }
        //----------------
        name = "備考";
        selector = "#data_memo";
        value = form.getData().getMemo();
        if (!checker.checkFullText(value, false, 200, name, selector)) { result = false; }

        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    protected boolean doUpdateData(final EmpInfoForm form) throws Exception {
        boolean result = false;
        EmpInfoData data = form.getData();

        //----------------
        // メインデータ.
        // g_personal.
        EmpInfoGPersonal personal = new EmpInfoGPersonal();
        personal.setId(data.getPersonal_id());
        personal.setFamily_name(data.getFamily_name());
        personal.setFirst_name(data.getFirst_name());
        personal.setFamily_name_kana(data.getFamily_name_kana());
        personal.setFirst_name_kana(data.getFirst_name_kana());
        personal.setMaiden_name(data.getMaiden_name());
        personal.setMaiden_name_kana(data.getMaiden_name_kana());
        personal.setSex(data.getSex());
        personal.setBirthday(ReUtil.toNull(data.getBirthday()));
        personal.setMynumber(data.getMynumber());
        common.setUpdateInfo(personal);
        result = (dao.update(personal) > 0);
        if (result) {
            // m_user.
            EmpInfoMUser user = new EmpInfoMUser();
            user.setId(data.getId());
            user.setName(common.getName(data));
            common.setUpdateInfo(user);
            result = (dao.update(user) > 0);
        }
        if (result) {
            // m_employee.
            EmpInfoMEmployee employee = new EmpInfoMEmployee();
            employee.setUser_id(data.getId());
            employee.setSituation(data.getSituation());
            employee.setEmployee_no(data.getEmployee_no());
            employee.setHire_date(ReUtil.toNull(data.getHire_date()));
            employee.setRetirement_date(ReUtil.toNull(data.getRetirement_date()));
            employee.setMemo(data.getMemo());
            common.setUpdateInfo(employee);
            result = (dao.update(employee) > 0);
        }
        if (result) {
            //----------------
            // 社員メールアドレスリスト.
            for (EmpInfoSEmpEmail emp_email : form.getEmpEmailList()) {
                if (!ReUtil.isEmpty(emp_email.getEmail_addr())) {
                    // s_emp_email.
                    emp_email.setUser_id(data.getId());
                    common.setUpdateInfo(emp_email);
                    result = (dao.update(emp_email) > 0);
                    if (!result) { break; }
                }
            }
        }
        if (result) {
            //----------------
            // 社員電話番号リスト.
            for (EmpInfoSEmpTel emp_tel : form.getEmpTelList()) {
                if (!ReUtil.isEmpty(emp_tel.getTel_no())) {
                    // s_emp_tel.
                    emp_tel.setUser_id(data.getId());
                    common.setUpdateInfo(emp_tel);
                    result = (dao.update(emp_tel) > 0);
                    if (!result) { break; }
                }
            }
        }
        if (result) {
            //----------------
            // 社員住所リスト.
            for (EmpInfoSEmpAddr emp_addr : form.getEmpAddrList()) {
                if (ReUtil.isEmpty(emp_addr.getAddr_id())) {
                    if (!ReUtil.isEmpty(emp_addr.getPostal_code()) ||
                            !ReUtil.isEmpty(emp_addr.getAddr1()) ||
                            !ReUtil.isEmpty(emp_addr.getAddr2()) ||
                            !ReUtil.isEmpty(emp_addr.getNearest_station())) {
                        // g_addr.
                        EmpInfoGAddr addr = new EmpInfoGAddr();
                        addr.setPostal_code(emp_addr.getPostal_code());
                        addr.setAddr1(emp_addr.getAddr1());
                        addr.setAddr2(emp_addr.getAddr2());
                        addr.setNearest_station(emp_addr.getNearest_station());
                        common.setUpdateInfo(addr);
                        result = (dao.insert(addr) > 0);
                        if (!result) { break; }
                        // s_emp_addr.
                        emp_addr.setUser_id(data.getId());
                        emp_addr.setAddr_id(dao.getLastInsertId());
                        common.setUpdateInfo(emp_addr);
                        result = (dao.insert(emp_addr) > 0);
                        if (!result) { break; }
                    }
                } else {
                    // g_addr.
                    EmpInfoGAddr addr = new EmpInfoGAddr();
                    addr.setId(emp_addr.getAddr_id());
                    addr.setPostal_code(emp_addr.getPostal_code());
                    addr.setAddr1(emp_addr.getAddr1());
                    addr.setAddr2(emp_addr.getAddr2());
                    addr.setNearest_station(emp_addr.getNearest_station());
                    common.setUpdateInfo(addr);
                    result = (dao.update(addr) > 0);
                    if (!result) { break; }
                }
            }
        }
        if (result) {
            //----------------
            // 社員サブ情報リスト.
            for (EmpInfoSEmpInfo emp_info : form.getEmpInfoList()) {
                if (!ReUtil.isEmpty(emp_info.getValue())) {
                    // s_emp_info.
                    emp_info.setUser_id(data.getId());
                    emp_info.setValue(ReUtil.toNull(emp_info.getValue()));
                    common.setUpdateInfo(emp_info);
                    result = (dao.update(emp_info) > 0);
                    if (!result) { break; }
                }
            }
        }
        return result;
    }



    //---------------------------------------------- [private].
    /**
     * 社員番号重複チェックを行います.
     * @param employee_no 対象の社員番号.
     * @param user_id 対象の社員ID
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    private boolean checkEmployeeNo(
            final String employee_no, final String user_id, final String name, final String selector
            ) throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(employee_no)) {
            int count = dao.getCount(dao.getSql(EmpInfoData.class, "check_employee_no")
                    , (st)->{
                        st.setString(1, auth.getLogin_root_group_id());
                        st.setString(2, employee_no);
                        st.setString(3, user_id);
                    });
            if (count > 0) {
                addMessage(msg.getMessage("E00014", name).setSelector(selector));
                return false;
            }
        }
        return true;
    }

}
