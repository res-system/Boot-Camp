package com.res_system.re_employee_manager.model.employee_family;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.model.CommonModel;
import com.res_system.re_employee_manager.commons.model.SelectedEmpModel;
import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 * <pre>
 * 社員家族情報管理画面 モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class EmployeeFamilyModel {

    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel commonModel;

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
    public boolean find(final EmployeeFamilyForm form) throws Exception {
        boolean result = true;
        return result;
    }

    /**
     * 画面表示データを設定します.
     * @param form 対象データ.
     * @throws SimpleDaoException
     */
    public void setShowFormData(final EmployeeFamilyForm form) throws SimpleDaoException {
        form.setMenuDataList(commonModel.getMenuDataList(CommonModel.PERFLG_EMPTAB));
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

}
