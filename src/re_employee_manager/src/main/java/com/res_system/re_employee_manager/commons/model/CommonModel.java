package com.res_system.re_employee_manager.commons.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.exceptions.AppException;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.entities.CommonEntity;
import com.res_system.re_employee_manager.commons.model.entities.GroupItem;
import com.res_system.re_employee_manager.commons.model.entities.IAcctSubEntity;
import com.res_system.re_employee_manager.commons.model.entities.IEmpSubEntity;
import com.res_system.re_employee_manager.commons.model.entities.IGenMsEntity;
import com.res_system.re_employee_manager.commons.model.entities.IIdMsEntity;
import com.res_system.re_employee_manager.commons.model.entities.MenuData;
import com.res_system.re_employee_manager.commons.model.entities.table.CGAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.CGEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.CGPersonal;
import com.res_system.re_employee_manager.commons.model.entities.table.CGTel;
import com.res_system.re_employee_manager.commons.model.entities.table.MEmployee;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpAddr;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpEmail;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpEmergency;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpFamily;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpMemo;
import com.res_system.re_employee_manager.commons.model.entities.table.SEmpTel;

/**
 * <pre>
 * 共通処理 モデルクラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@SessionScoped
public class CommonModel implements Serializable {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    /** 権限許可フラグ[メインメニュー用]. */
    public static final int PERFLG_MAINMENU = 0b0000000000000001;
    /** 権限許可フラグ[者委員情報タブ用]. */
    public static final int PERFLG_EMPTAB = 0b0000000000000010;



    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] 共通取得処理.
    /**
     * 更新者IDを返却します。
     * @return
     */
    public Long getUpdatedId() {
        Long accountId = authModel.getAccountId();
        if (accountId > 0) {
            return accountId;
        }
        return 1L;
    }

    /**
     * 最後に保存したIDを取得します.
     * @param entity 対象のEntity.
     * @return 保存したID.
     * @throws SimpleDaoException
     */
    public Long getLastInsertId() throws SimpleDaoException {
        CommonEntity commonEntity = dao.executeQuery(
                CommonEntity.class, "SELECT LAST_INSERT_ID() AS `id`");
        if (commonEntity == null) {
            throw new AppException("IDの取得に失敗しました.");
        }
        return commonEntity.getId();
    }


    /**
     * メニューデータリストを取得します.
     * @param permission_flg 権限許可フラグ.
     * @return メニューデータリスト.
     * @throws SimpleDaoException
     */
    public List<MenuData> getMenuDataList(int permission_flg) throws SimpleDaoException {
        List<MenuData> list = dao.findList(MenuData.class
                , "find_list_by_group_id_and_account_id"
                , (st) -> {
                    st.setInt(1, permission_flg);
                    st.setLong(2, authModel.getSelectedGroupId());
                    st.setLong(3, authModel.getAccountId());
                } );
        // ルートで取得できない場合、デフォルトのアカウントIDのみで再取得する.
        if (list.size() <= 0 && authModel.IsRootManager()) {
            list = dao.findList(MenuData.class
                    , "find_list_by_account_id"
                    , (st) -> {
                        st.setInt(1, permission_flg);
                        st.setLong(2, authModel.getAccountId());
                    } );
        }
        return list;
    }


    /**
     * グループリストを取得します.
     * @return グループリスト.
     * @throws SimpleDaoException
     */
    public List<GroupItem> getGroupList() throws SimpleDaoException {
        return getGroupListByAccountId(authModel.getAccountId().toString());
    }

    /**
     * グループリストを取得します.
     * @param account_id アカウントID.
     * @return グループリスト.
     * @throws SimpleDaoException
     */
    public List<GroupItem> getGroupListByAccountId(String account_id) throws SimpleDaoException {
        if (!ReUtil.isEmpty(account_id)) {
            return dao.findList(GroupItem.class
                    , "find_list_by_account_id"
                    , (st) -> { st.setString(1, account_id); });
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * グループリストの選択が可能か判断します.
     * @return 選択可能の有無.
     * @throws SimpleDaoException
     */
    public boolean IsSelectGroup() throws SimpleDaoException {
        if (!authModel.IsRootManager()) {
            GroupItem result = dao.find(GroupItem.class
                    , "check_group_id_by_account_id"
                    , (st) -> { st.setLong(1, authModel.getAccountId()); });
            if (result != null && result.getCount() > 1) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }



    //---------------------------------------------- [public] 共通更新処理.
    /**
     * メインマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @param updateSqlName 更新時のSQL名.
     * @return 処理結果.
     */
    public boolean saveIdMst(IIdMsEntity entity, String updateSqlName) {
        if (entity != null) {
            try {
                // 更新ID設定.
                Long accountId = getUpdatedId();
                if (entity.getCreated_id() == null) {
                    entity.setCreated_id(accountId);
                }
                if (entity.getUpdated_id() == null) {
                    entity.setUpdated_id(accountId);
                }
                // 更新処理.
                if (entity.getId() == null) {
                //-- Insert --//
                    if (dao.insert(entity) > 0) {
                        entity.setId(getLastInsertId());
                        return true;
                    }
                } else {
                //-- Update --//
                    if (!ReUtil.isEmpty(updateSqlName)) {
                        return (dao.update(entity, updateSqlName) > 0);
                    } else {
                        return (dao.update(entity) > 0);
                    }
                }
            } catch (Exception e) {
                throw new AppException(dao.getTableName(entity.getClass()) + "の保存に失敗しました.", e);
            }
        }
        return false;
    }

    /**
     * メインマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveIdMst(IIdMsEntity entity) throws Exception {
        return saveIdMst(entity, null);
    }


    /**
     * 汎用マスタEntityを保存します.
     * @param entity 対象のEntity.
     * @param updateSqlName 更新時のSQL名.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveGenMst(IGenMsEntity entity, String updateSqlName) throws Exception {
        if (entity != null) {
            try {
                // 更新ID設定.
                Long accountId = getUpdatedId();
                if (entity.getUpdated_id() == null) {
                    entity.setUpdated_id(accountId);
                }
                // 更新処理.
                if (entity.getId() == null) {
                //-- Insert --//
                    if (dao.insert(entity) > 0) {
                        entity.setId(getLastInsertId());
                        return true;
                    }
                } else {
                //-- Update --//
                    if (!ReUtil.isEmpty(updateSqlName)) {
                        return (dao.update(entity, updateSqlName) > 0);
                    } else {
                        return (dao.update(entity) > 0);
                    }
                }
            } catch (Exception e) {
                throw new AppException(dao.getTableName(entity.getClass()) + "の保存に失敗しました.", e);
            }
        }
        return false;
    }

    /**
     * 汎用マスタEntityを保存します.
     * @param entity 対象のEntity.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveGenMst(IGenMsEntity entity) throws Exception {
        return saveGenMst(entity, null);
    }


    /**
     * アカウントサブマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @param updateSqlName 更新時のSQL名.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveAcctSub(IAcctSubEntity entity, String updateSqlName) throws Exception {
        if (entity != null) {
            try {
                // 更新ID設定.
                Long accountId = getUpdatedId();
                if (entity.getCreated_id() == null) {
                    entity.setCreated_id(accountId);
                }
                if (entity.getUpdated_id() == null) {
                    entity.setUpdated_id(accountId);
                }
                // 更新処理.
                String tableName = dao.getSqlMaker().getTableName(entity.getClass());
                CommonEntity checkData = dao.executeQuery(CommonEntity.class
                        , "SELECT COUNT(*) AS `count` FROM " +  tableName
                        + " WHERE account_id = ? AND seq = ?"
                        , (st) -> {
                            st.setLong(1, entity.getAccount_id());
                            st.setInt(2, entity.getSeq());
                        });
                if (checkData.getCount() <= 0) {
                //-- Insert --//
                    return (dao.insert(entity) > 0);
                } else {
                //-- Update --//
                    if (!ReUtil.isEmpty(updateSqlName)) {
                        return (dao.update(entity, updateSqlName) > 0);
                    } else {
                        return (dao.update(entity) > 0);
                    }
                }
            } catch (Exception e) {
                throw new AppException(dao.getTableName(entity.getClass()) + "の保存に失敗しました.", e);
            }
        }
        return false;
    }

    /**
     * アカウントサブマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveAcctSub(IAcctSubEntity entity) throws Exception {
        return saveAcctSub(entity, null);
    }


    /**
     * 社員サブマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @param updateSqlName 更新時のSQL名.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveEmpSub(IEmpSubEntity entity, String updateSqlName) throws Exception {
        if (entity != null) {
            try {
                // 更新ID設定.
                Long accountId = getUpdatedId();
                if (entity.getCreated_id() == null) {
                    entity.setCreated_id(accountId);
                }
                if (entity.getUpdated_id() == null) {
                    entity.setUpdated_id(accountId);
                }
                // 更新処理.
                String tableName = dao.getSqlMaker().getTableName(entity.getClass());
                CommonEntity checkData = dao.executeQuery(CommonEntity.class
                        , "SELECT COUNT(*) AS `count` FROM " +  tableName
                        + " WHERE employee_id = ? AND seq = ?"
                        , (st) -> {
                            st.setLong(1, entity.getEmployee_id());
                            st.setInt(2, entity.getSeq());
                        });
                if (checkData.getCount() <= 0) {
                //-- Insert --//
                    return (dao.insert(entity) > 0);
                } else {
                //-- Update --//
                    if (!ReUtil.isEmpty(updateSqlName)) {
                        return (dao.update(entity, updateSqlName) > 0);
                    } else {
                        return (dao.update(entity) > 0);
                    }
                }
            } catch (Exception e) {
                throw new AppException(dao.getTableName(entity.getClass()) + "の保存に失敗しました.", e);
            }
        }
        return false;
    }

    /**
     * 社員サブマスタEntityを保存します.
     * @param entity 対象のEntity.
     * @return 処理結果.
     * @throws Exception.
     */
    public boolean saveEmpSub(IEmpSubEntity entity) throws Exception {
        return saveEmpSub(entity, null);
    }



    //---------------------------------------------- [public] 汎用系マスタ処理.
    /**
     * 個人情報の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean saveCGPersonal(CGPersonal mPersonal) throws Exception {
        return saveGenMst(mPersonal);
    }

    /**
     * 住所の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean saveCGAddr(CGAddr mAddr) throws Exception {
        return saveGenMst(mAddr);
    }

    /**
     * 電話番号の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean saveCGTel(CGTel mTel) throws Exception {
        return saveGenMst(mTel);
    }

    /**
     * メールアドレスの保存の保存.
     * @param empInfo 社員入力情報.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean saveCGEmail(CGEmail mEmail) throws Exception {
        return saveGenMst(mEmail);
    }



    //---------------------------------------------- [public] 社員情報処理.
    /**
     * 社員情報の削除.
     * @param employee_id 社員ID.
     * @return 処理件数.
     * @throws Exception
     */
    public int deleteEmployee(final Long employee_id) throws Exception {
        int result = 0;
        MEmployee employee = new MEmployee();
        employee.setId(employee_id);
        employee = dao.find(employee);
        if (employee != null) {

            List<SEmpAddr> empAddrList = dao.executeQueryList(SEmpAddr.class
                    , "SELECT * FROM s_emp_addr WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpAddr empAddr : empAddrList) {
                // 社員住所サブマスタ 削除.
                result += dao.delete(empAddr);
                // 住所マスタ 削除.
                CGAddr addr = new CGAddr();
                addr.setId(empAddr.getAddr_id());
                result += dao.delete(addr);
            }

            List<SEmpTel> empTelList = dao.executeQueryList(SEmpTel.class
                    , "SELECT * FROM s_emp_tel WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpTel empTel : empTelList) {
                // 社員電話番号サブマスタ 削除.
                result += dao.delete(empTel);
                // 電話番号マスタ 削除.
                CGTel tel = new CGTel();
                tel.setId(empTel.getTel_id());
                result += dao.delete(tel);
            }

            List<SEmpEmail> empEmailList = dao.executeQueryList(SEmpEmail.class
                    , "SELECT * FROM s_emp_email WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpEmail empEmail : empEmailList) {
                // 社員メールアドレスサブマスタ 削除.
                result += dao.delete(empEmail);
                // メールアドレスマスタ 削除.
                CGEmail email = new CGEmail();
                email.setId(empEmail.getEmail_id());
                result += dao.delete(email);
            }

            List<SEmpFamily> empFamilyList = dao.executeQueryList(SEmpFamily.class
                    , "SELECT * FROM s_emp_family WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpFamily empFamily : empFamilyList) {
                // 社員家族サブマスタ 削除.
                result += dao.delete(empFamily);
                // 個人マスタ(家族) 削除.
                CGPersonal personal = new CGPersonal();
                personal.setId(empFamily.getPersonal_id());
                result += dao.delete(personal);
                // 住所マスタ(家族) 削除.
                CGAddr addr = new CGAddr();
                addr.setId(empFamily.getAddr_id());
                result += dao.delete(addr);
                // 電話番号マスタ(家族) 削除.
                CGTel tel = new CGTel();
                tel.setId(empFamily.getTel_id());
                result += dao.delete(tel);
                // メールアドレスマスタ(家族) 削除.
                CGEmail email = new CGEmail();
                email.setId(empFamily.getEmail_id());
                result += dao.delete(email);
            }

            List<SEmpEmergency> empEmergencyList = dao.executeQueryList(SEmpEmergency.class
                    , "SELECT * FROM s_emp_emergency WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpEmergency empEmergency : empEmergencyList) {
                // 社員緊急連絡先サブマスタ 削除.
                result += dao.delete(empEmergency);
                // 個人マスタ(緊急連絡先) 削除.
                CGPersonal personal = new CGPersonal();
                personal.setId(empEmergency.getPersonal_id());
                result += dao.delete(personal);
                // 住所マスタ(緊急連絡先) 削除.
                CGAddr addr = new CGAddr();
                addr.setId(empEmergency.getAddr_id());
                result += dao.delete(addr);
                // 電話番号マスタ(緊急連絡先) 削除.
                CGTel tel = new CGTel();
                tel.setId(empEmergency.getTel_id());
                result += dao.delete(tel);
                // メールアドレスマスタ(緊急連絡先) 削除.
                CGEmail email = new CGEmail();
                email.setId(empEmergency.getEmail_id());
                result += dao.delete(email);
            }

            List<SEmpMemo> empMemoList = dao.executeQueryList(SEmpMemo.class
                    , "SELECT * FROM s_emp_memo WHERE employee_id = ?"
                    , (st) -> { st.setLong(1, employee_id); } );
            for (SEmpMemo empMemo : empMemoList) {
                // 社員備考サブマスタ 削除.
                result += dao.delete(empMemo);
            }

            // 社員マスタ 削除.
            result += dao.delete(employee);
            {
                // 個人マスタ 削除.
                CGPersonal personal = new CGPersonal();
                personal.setId(employee.getPersonal_id());
                result += dao.delete(personal);
            }

        }
        return result;
    }



    //---------------------------------------------- [public] その他処理.

}
