package com.res_system.re_employee_manager.model.employee_info;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.mvc.model.kind.KindUtil;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.CheckerModel;
import com.res_system.re_employee_manager.commons.model.CommonModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.SelectedEmpModel;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.commons.model.entities.table.CGAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.CGEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.CGPersonal;
import com.res_system.re_employee_manager.commons.model.entities.table.CGTel;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpMemo;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpTel;
import com.res_system.re_employee_manager.commons.model.kind.EmStatKind;
import com.res_system.re_employee_manager.commons.model.kind.SexKind;

/**
 * <pre>
 * 社員個人情報管理画面 モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class EmployeeInfoModel {

    //---------------------------------------------- [private] モデルクラス.
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;

    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checkerModel;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel commonModel;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;

    /** 社員選択処理 モデルクラス. */
    @Inject
    private SelectedEmpModel selectedEmployeeModel;



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
    public void destroy() {}



    //---------------------------------------------- [public] 業務処理.
    /**
     * 検索処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean find(final EmployeeInfoForm form) throws Exception {
        boolean result = true;

        // 検索条件.
        String whereSqlName;
        String whereValue;
        if (selectedEmployeeModel.isSelectedEmployee()) {
            whereSqlName = "where_by_employee_id";
            whereValue = selectedEmployeeModel.getEmployee_id();
        } else {
            whereSqlName = "where_by_account_id";
            whereValue = authModel.getAccountId().toString();
        }
        if (!ReUtil.isEmpty(whereValue)) {

            // SQL.
            StringBuilder sql = new StringBuilder();
            sql.append(dao.getSql(EmployeeInfo.class, "find_employee_info"));
            sql.append(dao.getSql(EmployeeInfo.class, whereSqlName));

            // 検索.
            EmployeeInfo entity = dao.executeQuery(EmployeeInfo.class
                    , sql.toString()
                    , (st) -> { st.setString(1, whereValue); });
            if (entity != null) {
                form.setData(entity);
            } else {
                addMessage(msgModel.getMessage("E00004", "社員情報"));
                return false;
            }
        }

        return result;
    }


    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     */
    public boolean checkInput(EmployeeInfoForm form) {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";
        String description = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checkerModel.setMessageList(messageList);

        //----------------
        name = "社員番号";
        selector = "#data_employee_no";
        value = form.getData().getEmployee_no();
        if (!checkerModel.checkHalfText(value, true, 10, name, selector)) { result = false; }
        //----------------
        name = "氏名（姓）";
        selector = "#data_family_name";
        value = form.getData().getFamily_name();
        if (!checkerModel.checkFullText(value, true, 20, name, selector)) { result = false; }
        name = "氏名（名）";
        selector = "#data_first_name";
        value = form.getData().getFirst_name();
        if (!checkerModel.checkFullText(value, true, 20, name, selector)) { result = false; }
        //----------------
        name = "カナ氏名（姓）";
        selector = "#data_family_name_kana";
        value = form.getData().getFamily_name_kana();
        if (!checkerModel.checkFullKanaText(value, true, 40, name, selector)) { result = false; }
        name = "カナ氏名（名）";
        selector = "#data_first_name_kana";
        value = form.getData().getFirst_name_kana();
        if (!checkerModel.checkFullKanaText(value, true, 40, name, selector)) { result = false; }
        //----------------
        name = "役職";
        selector = "#data_position";
        value = form.getData().getPosition();
        if (!checkerModel.checkFullText(value, false, 20, name, selector)) { result = false; }
        //----------------
        name = "状態";
        selector = "#data_emp_status";
        value = form.getData().getEmp_status();
        if (!checkerModel.checkKind(value, true, EmStatKind.values(), name, selector)) { result = false; }
        //----------------
        name = "入社日";
        selector = "#data_joining_date";
        value = form.getData().getJoining_date();
        if (!checkerModel.checkDate(value, true, name, selector)) { result = false; }
        //----------------
        name = "退社日";
        selector = "#data_leaving_date";
        value = form.getData().getLeaving_date();
        if (!checkerModel.checkDate(value, false, name, selector)) { result = false; }
        //----------------
        name = "性別";
        selector = "#data_sex_0";
        value = form.getData().getSex();
        if (!checkerModel.checkKind(value, true, SexKind.values(), name, selector)) { result = false; }
        //----------------
        name = "生年月日";
        selector = "#data_birthday";
        value = form.getData().getBirthday();
        if (!checkerModel.checkDate(value, true, name, selector)) { result = false; }
        //----------------
        name = "マイナンバー";
        description = "(「-」の入力も可です)";
        selector = "#data_mynumber";
        value = form.getData().getMynumber();
        if (!checkerModel.checkHalfNum(value, false, 20, "-", name, description, selector)) { result = false; }
        //----------------
        name = "雇用保険資格取得日";
        selector = "#data_acquisition_date";
        value = form.getData().getAcquisition_date();
        if (!checkerModel.checkDate(value, false, name, selector)) { result = false; }
        //----------------
        name = "雇用保険被保険番号";
        description = "(「-」の入力も可です)";
        selector = "#data_insurance_no";
        value = form.getData().getInsurance_no();
        if (!checkerModel.checkHalfNum(value, false, 20, "-", name, description, selector)) { result = false; }
        //----------------
        name = "基礎年金番号";
        description = "(「-」の入力も可です)";
        selector = "#data_pension_no";
        value = form.getData().getPension_no();
        if (!checkerModel.checkHalfNum(value, false, 20, "-", name, description, selector)) { result = false; }
        //----------------
        name = "郵便番号";
        selector = "#data_postal_code";
        value = form.getData().getPostal_code();
        if (!checkerModel.checkPostalCode(value, false, name, selector)) { result = false; }
        //----------------
        name = "住所1";
        selector = "#data_addr1";
        value = form.getData().getAddr1();
        if (!checkerModel.checkFullText(value, false, 50, name, selector)) { result = false; }
        name = "住所2";
        selector = "#data_addr2";
        value = form.getData().getAddr2();
        if (!checkerModel.checkFullText(value, false, 50, name, selector)) { result = false; }
        //----------------
        name = "最寄り駅";
        selector = "#data_nearest_station";
        value = form.getData().getNearest_station();
        if (!checkerModel.checkFullText(value, false, 40, name, selector)) { result = false; }
        //----------------
        name = "電話番号";
        selector = "#data_tel_no";
        value = form.getData().getTel_no();
        if (!checkerModel.checkTelNo(value, false, name, selector)) { result = false; }
        name = "電話番号（携帯）";
        selector = "#data_tel_no_mb";
        value = form.getData().getTel_no_mb();
        if (!checkerModel.checkTelNo(value, false, name, selector)) { result = false; }
        //----------------
        name = "メールアドレス";
        selector = "#data_email";
        value = form.getData().getEmail();
        if (!checkerModel.checkEmail(value, false, name, selector)) { result = false; }
        name = "メールアドレス（携帯）";
        selector = "#data_email_mb";
        value = form.getData().getEmail_mb();
        if (!checkerModel.checkEmail(value, false, name, selector)) { result = false; }
        //----------------
        name = "特記事項";
        selector = "#data_memo";
        value = form.getData().getMemo();
        if (!checkerModel.checkFullText(value, false, 200, name, selector)) { result = false; }

        //----------------
        if (authModel.getSelectedGroupId() <= 0) {
            addMessage(msgModel.getMessage("E00002", "グループ"));
            result = false;
        }

        return result;
    }

    /**
     * 保存処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean save(final EmployeeInfoForm form) throws Exception {
        boolean result = true;
        try {
            dao.begin();
            if (saveEmployeeInfo(form.getData())) {
                dao.commit();
            } else {
                dao.rollback();
                result = false;
                addMessage(msgModel.getMessage("E00005", "社員個人情報の保存"));
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
     * @throws Exception
     */
    public void complete(final EmployeeInfoForm form) throws Exception {
        addMessage(msgModel.getMessage("I20001", "社員個人情報の保存"));
    }

    /**
     * 画面表示データを設定します.
     * @param form 対象データ.
     * @throws SimpleDaoException
     */
    public void setShowFormData(final EmployeeInfoForm form) throws SimpleDaoException {
        form.setMenuDataList(commonModel.getMenuDataList(CommonModel.PERFLG_EMPTAB));
    }

    /**
     * 画面入力用データを設定します.
     * @param form 対象データ.
     */
    public void setInputFormData(final EmployeeInfoForm form) {
        form.setStatusList(KindUtil.toItemList(EmStatKind.values()));
        form.setSexList(KindUtil.toItemList(SexKind.values()));
    }

    /**
     * 戻り先URLを取得します.
     * @return 対象データ戻り先URL.
     */
    public String getReturnUrl() {
        if (!ReUtil.isEmpty(selectedEmployeeModel.getReturnUrl())) {
            return selectedEmployeeModel.getReturnUrl();
        } else {
            return "/main_menu";
        }
    }



    //---------------------------------------------- [private].
    /**
     * 社員入力情報の保存.
     * @param empInfo　社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveEmployeeInfo(final EmployeeInfo empInfo) throws Exception {

        // 個人情報の保存.
        if (!saveMPersonal(empInfo)) { return false; }
        // 住所の保存.
        if (!saveMAddr(empInfo)) { return false; }
        // 電話番号の保存.
        if (!saveMTel(empInfo)) { return false; }
        // 電話番号（携帯）の保存.
        if (!saveMTelMb(empInfo)) { return false; }
        // メールアドレスの保存の保存.
        if (!saveMEmail(empInfo)) { return false; }
        // メールアドレス（携帯）の保存.
        if (!saveMEmailMb(empInfo)) { return false; }

        // 社員情報の保存.
        if (!saveMEmployee(empInfo)) { return false; }
        // 社員住所情報の保存.
        if (!saveMEmployeeAddr(empInfo)) { return false; }
        // 社員電話番号情報の保存.
        if (!saveMEmployeeTel(empInfo)) { return false; }
        // 社員電話番号情報（携帯）の保存.
        if (!saveMEmployeeTelMb(empInfo)) { return false; }
        // 社員メールアドレスマスタ情報の保存.
        if (!saveMEmployeeEmail(empInfo)) { return false; }
        // 社員メールアドレスマスタ情報（携帯）の保存.
        if (!saveMEmployeeEmailMb(empInfo)) { return false; }
        // 社員備考マスタの保存.
        if (!saveMEmployeeMemo(empInfo)) { return false; }

        return true;
    }


    /**
     * 個人情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMPersonal(final EmployeeInfo empInfo) throws Exception {
        CGPersonal cgPersonal = new CGPersonal();
        cgPersonal.setId(ReUtil.toLong(empInfo.getPersonal_id()));
        cgPersonal.setFamily_name(empInfo.getFamily_name());
        cgPersonal.setFirst_name(empInfo.getFirst_name());
        cgPersonal.setFamily_name_kana(empInfo.getFamily_name_kana());
        cgPersonal.setFirst_name_kana(empInfo.getFirst_name_kana());
        cgPersonal.setSex(empInfo.getSex());
        cgPersonal.setBirthday(ReDateUtil.toDate(empInfo.getBirthday()));
        cgPersonal.setMynumber(empInfo.getMynumber());
        if (!commonModel.saveCGPersonal(cgPersonal)) {
            return false;
        } else {
            empInfo.setPersonal_id(cgPersonal.getId().toString());
        }
        return true;
    }

    /**
     * 住所の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMAddr(final EmployeeInfo empInfo) throws Exception {
        CGAddr cgAddr = new CGAddr();
        cgAddr.setId(ReUtil.toLong(empInfo.getAddr_id()));
        cgAddr.setPostal_code(empInfo.getPostal_code());
        cgAddr.setAddr1(empInfo.getAddr1());
        cgAddr.setAddr2(empInfo.getAddr2());
        cgAddr.setNearest_station(empInfo.getNearest_station());
        if (!commonModel.saveCGAddr(cgAddr)) {
            return false;
        } else {
            empInfo.setAddr_id(cgAddr.getId().toString());
        }
        return true;
    }

    /**
     * 電話番号の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMTel(final EmployeeInfo empInfo) throws Exception {
        CGTel cgTel = new CGTel();
        cgTel.setId(ReUtil.toLong(empInfo.getTel_id()));
        cgTel.setTel_no(empInfo.getTel_no());
        if (!commonModel.saveCGTel(cgTel)) {
            return false;
        } else {
            empInfo.setTel_id(cgTel.getId().toString());
        }
        return true;
    }

    /**
     * 電話番号（携帯）の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMTelMb(final EmployeeInfo empInfo) throws Exception {
        CGTel cgTel = new CGTel();
        cgTel.setId(ReUtil.toLong(empInfo.getTel_mb_id()));
        cgTel.setTel_no(empInfo.getTel_no_mb());
        if (!commonModel.saveCGTel(cgTel)) {
            return false;
        } else {
            empInfo.setTel_mb_id(cgTel.getId().toString());
        }
        return true;
    }

    /**
     * メールアドレスの保存の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmail(final EmployeeInfo empInfo) throws Exception {
        CGEmail cgEmail = new CGEmail();
        cgEmail.setId(ReUtil.toLong(empInfo.getEmail_id()));
        cgEmail.setEmail(empInfo.getEmail());
        if (!commonModel.saveCGEmail(cgEmail)) {
            return false;
        } else {
            empInfo.setEmail_id(cgEmail.getId().toString());
        }
        return true;
    }

    /**
     * メールアドレス（携帯）の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmailMb(final EmployeeInfo empInfo) throws Exception {
        CGEmail cgEmail = new CGEmail();
        cgEmail.setId(ReUtil.toLong(empInfo.getEmail_mb_id()));
        cgEmail.setEmail(empInfo.getEmail_mb());
        if (!commonModel.saveCGEmail(cgEmail)) {
            return false;
        } else {
            empInfo.setEmail_mb_id(cgEmail.getId().toString());
        }
        return true;
    }


    /**
     * 社員情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployee(final EmployeeInfo empInfo) throws Exception {
        final EmpInfoMEmployee mEmployee = new EmpInfoMEmployee();
        mEmployee.setId(ReUtil.toLong(empInfo.getEmployee_id()));
        mEmployee.setEmployee_no(empInfo.getEmployee_no());
        mEmployee.setPersonal_id(ReUtil.toLong(empInfo.getPersonal_id()));
        mEmployee.setEmp_status(empInfo.getEmp_status());
        mEmployee.setPosition(empInfo.getPosition());
        mEmployee.setJoining_date(ReDateUtil.toDate(empInfo.getJoining_date()));
        mEmployee.setLeaving_date(ReDateUtil.toDate(empInfo.getLeaving_date()));
        mEmployee.setAcquisition_date(ReDateUtil.toDate(empInfo.getAcquisition_date()));
        mEmployee.setInsurance_no(empInfo.getInsurance_no());
        mEmployee.setPension_no(empInfo.getPension_no());
        mEmployee.setDefault_group_id(authModel.getSelectedGroupId());
        if (!selectedEmployeeModel.isSelectedEmployee()) {
            mEmployee.setAccount_id(authModel.getAccountId());
        }
        if (!commonModel.saveIdMst(mEmployee, "update_employee_info")) {
            return false;
        } else {
            empInfo.setEmployee_id(mEmployee.getId().toString());
            if (selectedEmployeeModel.isSelectedEmployee()) {
                selectedEmployeeModel.getData().setSelected_employee_id(mEmployee.getId().toString());
            }
        }
        return true;
    }

    /**
     * 社員住所情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeAddr(final EmployeeInfo empInfo) throws Exception {
        SEmpAddr sEmpAddr = new SEmpAddr();
        sEmpAddr.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpAddr.setSeq(1);
        sEmpAddr.setAddr_id(ReUtil.toLong(empInfo.getAddr_id()));
        sEmpAddr.setMemo("現住所");
        if (!commonModel.saveEmpSub(sEmpAddr)) {
            return false;
        }
        return true;
    }

    /**
     * 社員電話番号情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeTel(final EmployeeInfo empInfo) throws Exception {
        SEmpTel sEmpTel = new SEmpTel();
        sEmpTel.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpTel.setSeq(1);
        sEmpTel.setTel_id(ReUtil.toLong(empInfo.getTel_id()));
        sEmpTel.setMemo("自宅");
        if (!commonModel.saveEmpSub(sEmpTel)) {
            return false;
        }
        return true;
    }

    /**
     * 社員電話番号情報（携帯）の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeTelMb(final EmployeeInfo empInfo) throws Exception {
        SEmpTel sEmpTel = new SEmpTel();
        sEmpTel.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpTel.setSeq(2);
        sEmpTel.setTel_id(ReUtil.toLong(empInfo.getTel_mb_id()));
        sEmpTel.setMemo("携帯");
        if (!commonModel.saveEmpSub(sEmpTel)) {
            return false;
        }
        return true;
    }

    /**
     * 社員メールアドレスマスタ情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeEmail(final EmployeeInfo empInfo) throws Exception {
        SEmpEmail sEmpEmail = new SEmpEmail();
        sEmpEmail.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpEmail.setSeq(1);
        sEmpEmail.setEmail_id(ReUtil.toLong(empInfo.getEmail_id()));
        sEmpEmail.setMemo("ＰＣ");
        if (!commonModel.saveEmpSub(sEmpEmail)) {
            return false;
        }
        return true;
    }

    /**
     * 社員メールアドレスマスタ情報（携帯）の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeEmailMb(final EmployeeInfo empInfo) throws Exception {
        SEmpEmail sEmpEmail = new SEmpEmail();
        sEmpEmail.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpEmail.setSeq(2);
        sEmpEmail.setEmail_id(ReUtil.toLong(empInfo.getEmail_mb_id()));
        sEmpEmail.setMemo("携帯");
        if (!commonModel.saveEmpSub( sEmpEmail)) {
            return false;
        }
        return true;
    }

    /**
     * 社員備考マスタの保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    private boolean saveMEmployeeMemo(final EmployeeInfo empInfo) throws Exception {
        SEmpMemo sEmpMemo = new SEmpMemo();
        sEmpMemo.setEmployee_id(ReUtil.toLong(empInfo.getEmployee_id()));
        sEmpMemo.setSeq(1);
        sEmpMemo.setMemo(empInfo.getMemo());
        if (!commonModel.saveEmpSub(sEmpMemo)) {
            return false;
        }
        return true;
    }

}
