package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * 社員入力情報 Entityクラス.
 * </pre>
 * @author res.
 */
@Sqls({
    @Sql(name="find_employee_list",
            sql = "SELECT"
                    + " `EMP`.`employee_id` AS `employee_id`"
                    + ",`EMP`.`employee_no` AS `employee_no`"
                    + ",`EMP`.`personal_id` AS `personal_id`"
                    + ",`EMP`.`emp_status` AS `emp_status`"
                    + ",`EmStat`.`name` AS `emp_status_name`"
                    + ",`EMP`.`position` AS `position`"
                    + ",DATE_FORMAT(`EMP`.`joining_date`,'%Y/%m/%d') AS `joining_date`"
                    + ",DATE_FORMAT(`EMP`.`leaving_date`,'%Y/%m/%d') AS `leaving_date`"
                    + ",`PSN`.`family_name` AS `family_name`"
                    + ",`PSN`.`first_name` AS `first_name`"
                    + ",`PSN`.`family_name_kana` AS `family_name_kana`"
                    + ",`PSN`.`first_name_kana` AS `first_name_kana`"
                    + ",`EMP`.`group_id` AS `group_id`"
                    + ",`GRP`.`name` AS `group_name`"
                    + ",`EMP`.`group_level` AS `group_level`"
                    + ",`EMP`.`account_id` AS `account_id`"
                    // -- キーワード検索項目. --//
                    + ",CONCAT(IFNULL(`employee_no`,'')"
                    +    ",' ',IFNULL(`PSN`.`family_name`,'')"
                    +    ",' ',IFNULL(`PSN`.`first_name`,'')"
                    +    ",' ',IFNULL(`PSN`.`family_name_kana`,'')"
                    +    ",' ',IFNULL(`PSN`.`first_name_kana`,'')"
                    +    ",' ',IFNULL(`EmStat`.`name`,'')"
                    +    ",' ',IFNULL(`EMP`.`position`,'')"
                    +    ",' ',IFNULL(`GRP`.`name`,'')) AS `keyword`"
                    + " FROM ("
                    // -- 関連するグループに関わる社員すべて対象. --//
                    +       "SELECT"
                    +       " `W1`.*"
                    +       ",`W2`.`path_length` AS `group_level`"
                    +       " FROM ("
                    +           "SELECT"
                    +           " `W11`.`id` AS `employee_id`"
                    +           ",`W11`.`employee_no` AS `employee_no`"
                    +           ",`W11`.`personal_id` AS `personal_id`"
                    +           ",`W11`.`emp_status` AS `emp_status`"
                    +           ",`W11`.`position` AS `position`"
                    +           ",`W11`.`joining_date`"
                    +           ",`W11`.`leaving_date`"
                    +           ",`W11`.`account_id`"
                    +           ",IFNULL(`W12`.`group_id`,`W11`.`default_group_id`) AS `group_id`"
                    +           " FROM `m_employee` `W11`  "
                    +               " LEFT OUTER JOIN `c_r_grp_account` `W12` ON `W11`.`account_id` =`W12`.`account_id`"
                    +         ") `W1`"
                    +         " INNER JOIN ("
                    +           "SELECT"
                    +           " `GPCN`.`descendant_id`"
                    +           ",`GPCN`.`path_length`"
                    +           " FROM `c_r_grp_construction` `GPCN`"
                    +           " WHERE `GPCN`.`ancestor_id` = ?"
                    +         ") `W2` ON `W1`.`group_id` = `W2`.`descendant_id`"
                    + " ) `EMP` "
                    +   " LEFT OUTER JOIN `c_g_personal` `PSN` ON `EMP`.`personal_id`=`PSN`.`id` "
                    +   " LEFT OUTER JOIN `c_g_kind`  `EmStat` ON `EMP`.`emp_status` =`EmStat`.`code` AND `EmStat`.`seq`<>0 AND `EmStat`.`kbn`='EmStat'"
                    +   " LEFT OUTER JOIN `c_m_group`    `GRP` ON `EMP`.`group_id`   =`GRP`.`id` ")
    ,@Sql(name="find_employee_list_order",
            sql = " ORDER BY `group_level` ASC, `group_name` ASC, `employee_no` ASC, `joining_date` ASC ")
    ,@Sql(name="find_employee_info",
            sql = "SELECT "
                    + " `EMP`.`id` AS `employee_id`"
                    + ",`EMP`.`employee_no` AS `employee_no`"
                    + ",`EMP`.`personal_id` AS `personal_id`"
                    + ",`PSN`.`family_name` AS `family_name`"
                    + ",`PSN`.`first_name` AS `first_name`"
                    + ",`PSN`.`family_name_kana` AS `family_name_kana`"
                    + ",`PSN`.`first_name_kana` AS `first_name_kana`"
                    + ",`PSN`.`initial` AS `initial`"
                    + ",`PSN`.`sex` AS `sex`"
                    + ",`Sex`.`name` AS `sex_name`"
                    + ",DATE_FORMAT(`PSN`.`birthday`, '%Y/%m/%d') AS `birthday`"
                    + ",`PSN`.`mynumber` AS `mynumber`"
                    + ",`EMP`.`emp_status` AS `emp_status`"
                    + ",`EmStat`.`name` AS `emp_status_name`"
                    + ",`EMP`.`position` AS `position`"
                    + ",DATE_FORMAT(`EMP`.`joining_date`, '%Y/%m/%d') AS `joining_date`"
                    + ",DATE_FORMAT(`EMP`.`leaving_date`, '%Y/%m/%d') AS `leaving_date`"
                    + ",DATE_FORMAT(`EMP`.`acquisition_date`, '%Y/%m/%d') AS `acquisition_date`"
                    + ",`EMP`.`insurance_no` AS `insurance_no`"
                    + ",`EMP`.`pension_no` AS `pension_no`"
                    + ",`EMP`.`default_group_id` AS `default_group_id`"
                    + ",`EMP`.`account_id` AS `account_id`"
                    + ",`EMAD1`.`addr_id` AS `addr_id`"
                    + ",`ADD1`.`postal_code` AS `postal_code`"
                    + ",`ADD1`.`addr1` AS `addr1`"
                    + ",`ADD1`.`addr2` AS `addr2`"
                    + ",`ADD1`.`nearest_station` AS `nearest_station`"
                    + ",`EMTL1`.`tel_id` AS `tel_id`"
                    + ",`TEL1`.`tel_no` AS `tel_no`"
                    + ",`EMTL2`.`tel_id` AS `tel_mb_id`"
                    + ",`TEL2`.`tel_no` AS `tel_no_mb`"
                    + ",`EMEL1`.`email_id` AS `email_id`"
                    + ",`EML1`.`email` AS `email`"
                    + ",`EMEL2`.`email_id` AS `email_mb_id`"
                    + ",`EML2`.`email` AS `email_mb`"
                    + ",`EMMM1`.`memo` AS `memo`"
                    + "  FROM `m_employee` `EMP`"
                    +   " LEFT OUTER JOIN `c_g_personal`  `PSN` ON `EMP`.`personal_id`=`PSN`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_addr`  `EMAD1` ON `EMP`.`id`=`EMAD1`.`employee_id` AND `EMAD1`.`seq`=1"
                    +   " LEFT OUTER JOIN `c_g_addr`     `ADD1` ON `EMAD1`.`addr_id`=`ADD1`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_tel`   `EMTL1` ON `EMP`.`id`=`EMTL1`.`employee_id` AND `EMTL1`.`seq`=1"
                    +   " LEFT OUTER JOIN `c_g_tel`      `TEL1` ON `EMTL1`.`tel_id`=`TEL1`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_tel`   `EMTL2` ON `EMP`.`id`=`EMTL2`.`employee_id` AND `EMTL2`.`seq`=2"
                    +   " LEFT OUTER JOIN `c_g_tel`      `TEL2` ON `EMTL2`.`tel_id`=`TEL2`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_email` `EMEL1` ON `EMP`.`id`=`EMEL1`.`employee_id` AND `EMEL1`.`seq`=1"
                    +   " LEFT OUTER JOIN `c_g_email`    `EML1` ON `EMEL1`.`email_id`=`EML1`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_email` `EMEL2` ON `EMP`.`id`=`EMEL2`.`employee_id` AND `EMEL2`.`seq`=2"
                    +   " LEFT OUTER JOIN `c_g_email`    `EML2` ON `EMEL2`.`email_id`=`EML2`.`id`"
                    +   " LEFT OUTER JOIN `s_emp_memo`  `EMMM1` ON `EMP`.`id`=`EMMM1`.`employee_id` AND `EMMM1`.`seq`=1"
                    +   " LEFT OUTER JOIN `c_g_kind`   `EmStat` ON `EMP`.`emp_status`=`EmStat`.`code` AND `EmStat`.`seq`<>0 AND `EmStat`.`kbn`='EmStat'"
                    +   " LEFT OUTER JOIN `c_g_kind`      `Sex` ON `PSN`.`Sex`       =`Sex`.`code` AND     `Sex`.`seq`<>0 AND     `Sex`.`kbn`='Sex'")
    ,@Sql(name="where_by_account_id",
            sql = " WHERE `EMP`.`account_id` = ?")
    ,@Sql(name="where_by_employee_id",
            sql = " WHERE `EMP`.`id` = ?")
})
public class EmployeeInfo implements IEntity {

    /** 社員ID. */
    @Param
    private String employee_id;
    /** 社員番号. */
    @Param
    private String employee_no;
    /** 個人ID. */
    @Param
    private String personal_id;
    /** 社員氏名（姓）. */
    @Param
    private String family_name;
    /** 社員氏名（名）. */
    @Param
    private String first_name;
    /** 社員カナ氏名（姓）. */
    @Param
    private String family_name_kana;
    /** 社員カナ氏名（名）. */
    @Param
    private String first_name_kana;
    /** 社員イニシャル. */
    @Param
    private String initial;
    /** 性別. */
    @Param
    private String sex;
    /** 性別名称. */
    @Param
    private String sex_name;
    /** 生年月日. */
    @Param
    private String birthday;
    /** マイナンバー. */
    @Param
    private String mynumber;
    /** 状態. */
    @Param
    private String emp_status;
    /** 状態名称. */
    @Param
    private String emp_status_name;
    /** 役職. */
    @Param
    private String position;
    /** 入社日. */
    @Param
    private String joining_date;
    /** 退社日. */
    @Param
    private String leaving_date;
    /** 雇用保険資格取得日. */
    @Param
    private String acquisition_date;
    /** 雇用保険被保険番号. */
    @Param
    private String insurance_no;
    /** 基礎年金番号. */
    @Param
    private String pension_no;
    /** デフォルトグループID. */
    @Param
    private String default_group_id;
    /** アカウントID. */
    @Param
    private String account_id;

    /** 社員住所ID. */
    @Param
    private String addr_id;
    /** 郵便番号. */
    @Param
    private String postal_code;
    /** 住所1. */
    @Param
    private String addr1;
    /** 住所2. */
    @Param
    private String addr2;
    /** 最寄り駅. */
    @Param
    private String nearest_station;

    /** 社員電話番号ID. */
    @Param
    private String tel_id;
    /** 電話番号. */
    @Param
    private String tel_no;
    /** 社員電話番号（携帯）ID. */
    @Param
    private String tel_mb_id;
    /** 電話番号（携帯）. */
    @Param
    private String tel_no_mb;

    /** 社員メールアドレスID. */
    @Param
    private String email_id;
    /** メールアドレス. */
    @Param
    private String email;
    /** 社員メールアドレス（携帯）ID. */
    @Param
    private String email_mb_id;
    /** メールアドレス（携帯）. */
    @Param
    private String email_mb;

    /** 特記事項. */
    @Param
    private String memo;

    /** グループID. */
    @Param
    private String group_id;
    /** グループ名. */
    @Param
    private String group_name;

    /** キーワード. */
    @Param
    private String keyword;

    /** 変更の有無. */
    @Param
    private String is_change;

    /** 削除の有無. */
    @Param
    private String is_delete;


    //-- setter / getter. --//
    /** 社員ID を取得します. */
    public String getEmployee_id() { return employee_id; }
    /** 社員ID を設定します. */
    public void setEmployee_id(String employee_id) { this.employee_id = employee_id; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を設定します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }
    /** 個人ID を取得します. */
    public String getPersonal_id() { return personal_id; }
    /** 個人ID を設定します. */
    public void setPersonal_id(String personal_id) { this.personal_id = personal_id; }
    /** 社員氏名（姓） を取得します. */
    public String getFamily_name() { return family_name; }
    /** 社員氏名（姓） を設定します. */
    public void setFamily_name(String family_name) { this.family_name = family_name; }
    /** 社員氏名（名） を取得します. */
    public String getFirst_name() { return first_name; }
    /** 社員氏名（名） を設定します. */
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    /** 社員カナ氏名（姓） を取得します. */
    public String getFamily_name_kana() { return family_name_kana; }
    /** 社員カナ氏名（姓） を設定します. */
    public void setFamily_name_kana(String family_name_kana) { this.family_name_kana = family_name_kana; }
    /** 社員カナ氏名（名） を取得します. */
    public String getFirst_name_kana() { return first_name_kana; }
    /** 社員カナ氏名（名） を設定します. */
    public void setFirst_name_kana(String first_name_kana) { this.first_name_kana = first_name_kana; }
    /** 社員イニシャル を取得します. */
    public String getInitial() { return initial; }
    /** 社員イニシャル を設定します. */
    public void setInitial(String initial) { this.initial = initial; }
    /** 性別 を取得します. */
    public String getSex() { return sex; }
    /** 性別 を設定します. */
    public void setSex(String sex) { this.sex = sex; }
    /** 性別名称 を取得します. */
    public String getSex_name() { return sex_name; }
    /** 性別名称 を設定します. */
    public void setSex_name(String sex_name) { this.sex_name = sex_name; }
    /** 生年月日 を取得します. */
    public String getBirthday() { return birthday; }
    /** 生年月日 を設定します. */
    public void setBirthday(String birthday) { this.birthday = birthday; }
    /** マイナンバー を取得します. */
    public String getMynumber() { return mynumber; }
    /** マイナンバー を設定します. */
    public void setMynumber(String mynumber) { this.mynumber = mynumber; }
    /** 状態 を取得します. */
    public String getEmp_status() { return emp_status; }
    /** 状態 を設定します. */
    public void setEmp_status(String emp_status) { this.emp_status = emp_status; }
    /** 状態名称 を取得します. */
    public String getEmp_status_name() { return emp_status_name; }
    /** 状態名称 を設定します. */
    public void setEmp_status_name(String emp_status_name) { this.emp_status_name = emp_status_name; }
    /** 役職 を取得します. */
    public String getPosition() { return position; }
    /** 役職 を設定します. */
    public void setPosition(String position) { this.position = position; }
    /** 入社日 を取得します. */
    public String getJoining_date() { return joining_date; }
    /** 入社日 を設定します. */
    public void setJoining_date(String joining_date) { this.joining_date = joining_date; }
    /** 退社日 を取得します. */
    public String getLeaving_date() { return leaving_date; }
    /** 退社日 を設定します. */
    public void setLeaving_date(String leaving_date) { this.leaving_date = leaving_date; }
    /** 雇用保険資格取得日 を取得します. */
    public String getAcquisition_date() { return acquisition_date; }
    /** 雇用保険資格取得日 を設定します. */
    public void setAcquisition_date(String acquisition_date) { this.acquisition_date = acquisition_date; }
    /** 雇用保険被保険番号 を取得します. */
    public String getInsurance_no() { return insurance_no; }
    /** 雇用保険被保険番号 を設定します. */
    public void setInsurance_no(String insurance_no) { this.insurance_no = insurance_no; }
    /** 基礎年金番号 を取得します. */
    public String getPension_no() { return pension_no; }
    /** 基礎年金番号 を設定します. */
    public void setPension_no(String pension_no) { this.pension_no = pension_no; }
    /** デフォルトグループID を取得します. */
    public String getDefault_group_id() { return default_group_id; }
    /** デフォルトグループID を設定します. */
    public void setDefault_group_id(String default_group_id) { this.default_group_id = default_group_id; }
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }

    /** 社員住所ID を取得します. */
    public String getAddr_id() { return addr_id; }
    /** 社員住所ID を設定します. */
    public void setAddr_id(String addr_id) { this.addr_id = addr_id; }
    /** 郵便番号 を取得します. */
    public String getPostal_code() { return postal_code; }
    /** 郵便番号 を設定します. */
    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
    /** 住所1 を取得します. */
    public String getAddr1() { return addr1; }
    /** 住所1 を設定します. */
    public void setAddr1(String addr1) { this.addr1 = addr1; }
    /** 住所2 を取得します. */
    public String getAddr2() { return addr2; }
    /** 住所2 を設定します. */
    public void setAddr2(String addr2) { this.addr2 = addr2; }
    /** 最寄り駅 を取得します. */
    public String getNearest_station() { return nearest_station; }
    /** 最寄り駅 を設定します. */
    public void setNearest_station(String nearest_station) { this.nearest_station = nearest_station; }

    /** 社員電話番号ID を取得します. */
    public String getTel_id() { return tel_id; }
    /** 社員電話番号ID を設定します. */
    public void setTel_id(String tel_id) { this.tel_id = tel_id; }
    /** 電話番号 を取得します. */
    public String getTel_no() { return tel_no; }
    /** 電話番号 を設定します. */
    public void setTel_no(String tel_no) { this.tel_no = tel_no; }
    /** 社員電話番号（携帯）ID を取得します. */
    public String getTel_mb_id() { return tel_mb_id; }
    /** 社員電話番号（携帯）ID を設定します. */
    public void setTel_mb_id(String tel_mb_id) { this.tel_mb_id = tel_mb_id; }
    /** 電話番号（携帯） を取得します. */
    public String getTel_no_mb() { return tel_no_mb; }
    /** 電話番号（携帯） を設定します. */
    public void setTel_no_mb(String tel_no_mb) { this.tel_no_mb = tel_no_mb; }

    /** 社員メールアドレスID を取得します. */
    public String getEmail_id() { return email_id; }
    /** 社員メールアドレスID を設定します. */
    public void setEmail_id(String email_id) { this.email_id = email_id; }
    /** メールアドレス を取得します. */
    public String getEmail() { return email; }
    /** メールアドレス を設定します. */
    public void setEmail(String email) { this.email = email; }
    /** 社員メールアドレス（携帯）ID を取得します. */
    public String getEmail_mb_id() { return email_mb_id; }
    /** 社員メールアドレス（携帯）ID を設定します. */
    public void setEmail_mb_id(String email_mb_id) { this.email_mb_id = email_mb_id; }
    /** メールアドレス（携帯） を取得します. */
    public String getEmail_mb() { return email_mb; }
    /** メールアドレス（携帯） を設定します. */
    public void setEmail_mb(String email_mb) { this.email_mb = email_mb; }
    /** 特記事項 を取得します. */
    public String getMemo() { return memo; }
    /** 特記事項 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }

    /** グループID を取得します. */
    public String getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(String group_id) { this.group_id = group_id; }
    /** グループ名 を取得します. */
    public String getGroup_name() { return group_name; }
    /** グループ名 を設定します. */
    public void setGroup_name(String group_name) { this.group_name = group_name; }

    /** キーワード を取得します. */
    public String getKeyword() { return keyword; }
    /** キーワード を設定します. */
    public void setKeyword(String keyword) { this.keyword = keyword; }

    /** 変更の有無 を取得します. */
    public String getIs_change() { return is_change; }
    /** 変更の有無 を設定します. */
    public void setIs_change(String is_change) { this.is_change = is_change; }
    /** 削除の有無 を取得します. */
    public String getIs_delete() { return is_delete; }
    /** 削除の有無 を設定します. */
    public void setIs_delete(String is_delete) { this.is_delete = is_delete; }

}
