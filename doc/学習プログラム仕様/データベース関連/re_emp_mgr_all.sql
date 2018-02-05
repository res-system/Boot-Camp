-- 外部キーを無効
SET FOREIGN_KEY_CHECKS=0;

-- スキーマー名
--   re_emp_mgr
--
-- ユーザーID
--   re_emp_mgr
--
-- パスワード
--   re_emp_mgr
--
-- 照合順序
--   utf8mb4_bin

-- c_m_account	アカウントマスタ	ACC
-- c_s_acct_key	アカウントキーサブマスタ	ACKY
-- c_s_acct_contact	アカウント連絡先サブマスタ	ACCT
-- c_t_login	ログイン認証トラン	LGN
-- c_m_permission	権限許可マスタ	PER
-- c_m_authority	権限マスタ	AUT
-- c_r_auth_grant	権限付与関連マスタ	AUGT
-- c_m_group	グループマスタ	GRP
-- c_r_grp_construction	グループ構成関連マスタ	GPCN
-- c_r_grp_account	グループアカウント関連マスタ	GPAC
-- c_g_personal	個人マスタ	PSN
-- c_g_addr	住所マスタ	ADD
-- c_g_tel	電話番号マスタ	TEL
-- c_g_email	メールアドレスマスタ	EML
-- c_g_kind	種別マスタ	KND
-- m_employee	社員マスタ	EMP
-- s_emp_addr	社員住所サブマスタ	EMAD
-- s_emp_tel	社員電話番号サブマスタ	EMTL
-- s_emp_email	社員メールアドレスサブマスタ	EMEL
-- s_emp_family	社員家族サブマスタ	EMFM
-- s_emp_emergency	社員緊急連絡先サブマスタ	EMEG
-- s_emp_memo	社員備考サブマスタ	EMMM

-- 
-- アカウントマスタ `c_m_account`
-- generated at 2018/01/23. 
-- 
DROP TABLE IF EXISTS `c_m_account`;
CREATE TABLE `c_m_account` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'アカウントID'
,`login_id` VARCHAR(191) UNIQUE NOT NULL COMMENT 'ログインID'
,`name` TEXT NOT NULL COMMENT 'アカウント名'
,`name_kana` TEXT NULL COMMENT 'アカウント名(カナ)'
,`default_authority_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'デフォルト権限ID'
,`registration_date` DATE NOT NULL COMMENT '登録日'
,`deletion_date` DATE NULL COMMENT '抹消日'
,`salt` VARCHAR(10) NOT NULL COMMENT 'ソルト'
,`acc_status` CHAR(2) DEFAULT 0 NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='アカウントマスタ';

-- 
-- アカウントキーサブマスタ `c_s_acct_key`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_s_acct_key`;
CREATE TABLE `c_s_acct_key` (
 `account_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'アカウントID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`key` VARCHAR(512) NOT NULL COMMENT 'キー'
,`expiration_time` TIMESTAMP NULL COMMENT '有効期限'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`account_id`,`seq`)
) ENGINE = InnoDB COMMENT='アカウントキーサブマスタ';

-- 
-- アカウント連絡先サブマスタ `c_s_acct_contact`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_s_acct_contact`;
CREATE TABLE `c_s_acct_contact` (
 `account_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'アカウントID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`contact_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '連絡先ID'
,`kbn` CHAR(2) DEFAULT '00' NOT NULL COMMENT '区分'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`account_id`,`seq`)
) ENGINE = InnoDB COMMENT='アカウント連絡先サブマスタ';

-- 
-- ログイン認証トラン `c_t_login`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_t_login`;
CREATE TABLE `c_t_login` (
 `token` VARCHAR(191) UNIQUE NOT NULL COMMENT '認証トークン'
,`kbn` CHAR(2) DEFAULT '00' NOT NULL COMMENT '区分'
,`account_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'アカウントID'
,`selected_group_id` BIGINT(20) UNSIGNED NULL COMMENT '選択グループID'
,`login_dt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'ログイン日時'
,`expiration_time` TIMESTAMP NOT NULL COMMENT '有効期限'
,`save_flg` CHAR(1) DEFAULT 0 NULL COMMENT '保存フラグ'
,`client_info` TEXT NULL COMMENT 'クライアント情報'
,PRIMARY KEY(`token`)
) ENGINE = InnoDB COMMENT='ログイン認証トラン';

-- 
-- 権限許可マスタ `c_m_permission`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_m_permission`;
CREATE TABLE `c_m_permission` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '許可ID'
,`key` VARCHAR(191) UNIQUE NULL COMMENT '機能'
,`default_url` VARCHAR(1024) NULL COMMENT 'デフォルトURL'
,`flg` INT(10) UNSIGNED NULL COMMENT '対象フラグ'
,`name` TEXT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`status` CHAR(1) DEFAULT 0 NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='権限許可マスタ';

-- 
-- 権限マスタ `c_m_authority`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_m_authority`;
CREATE TABLE `c_m_authority` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '権限ID'
,`name` TEXT NOT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`status` CHAR(1) DEFAULT 0 NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='権限マスタ';

-- 
-- 権限付与関連マスタ `c_r_auth_grant`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_r_auth_grant`;
CREATE TABLE `c_r_auth_grant` (
 `authority_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '権限ID'
,`permission_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '許可ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`kbn` CHAR(1) DEFAULT 0 NOT NULL COMMENT '区分'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`authority_id`,`permission_id`)
) ENGINE = InnoDB COMMENT='権限付与関連マスタ';

-- 
-- グループマスタ `c_m_group`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_m_group`;
CREATE TABLE `c_m_group` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'グループID'
,`code` VARCHAR(100) UNIQUE NOT NULL COMMENT '識別コード'
,`name` TEXT NULL COMMENT 'グループ名'
,`name_kana` TEXT NULL COMMENT 'グループ名(カナ)'
,`kbn` CHAR(4) DEFAULT '0' NULL COMMENT '区分'
,`memo` TEXT NULL COMMENT '備考'
,`root_group_id` BIGINT(20) UNSIGNED NULL COMMENT 'ルートグループID'
,`status` CHAR(1) DEFAULT 0 NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='グループマスタ';

-- 
-- グループ構成関連マスタ `c_r_grp_construction`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_r_grp_construction`;
CREATE TABLE `c_r_grp_construction` (
 `ancestor_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '祖先グループID'
,`descendant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '子孫グループID'
,`path_length` INT(8) DEFAULT 0 NOT NULL COMMENT '階層'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`ancestor_id`,`descendant_id`)
) ENGINE = InnoDB COMMENT='グループ構成関連マスタ';

-- 
-- グループ別アカウント関連マスタ `c_r_grp_account`
-- generated at 2018/01/23. 
-- 
DROP TABLE IF EXISTS `c_r_grp_account`;
CREATE TABLE `c_r_grp_account` (
 `group_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'グループID'
,`account_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'アカウントID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`account_seq` INT(8) DEFAULT 1 NOT NULL COMMENT 'アカウント内順番'
,`group_authority_id` BIGINT(20) UNSIGNED NULL COMMENT 'グループ内権限ID'
,`memo` TEXT NULL COMMENT '備考'
,`status` CHAR(1) DEFAULT 0 NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`group_id`,`account_id`)
) ENGINE = InnoDB COMMENT='グループ別アカウント関連マスタ';

-- 
-- 個人マスタ `c_g_personal`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_g_personal`;
CREATE TABLE `c_g_personal` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '個人ID'
,`family_name` TEXT NULL COMMENT '氏名（姓）'
,`first_name` TEXT NULL COMMENT '氏名（名）'
,`family_name_kana` TEXT NULL COMMENT 'カナ氏名（姓）'
,`first_name_kana` TEXT NULL COMMENT 'カナ氏名（名）'
,`maiden_name` TEXT NULL COMMENT '旧姓'
,`maiden_name_kana` TEXT NULL COMMENT 'カナ旧姓'
,`initial` TEXT NULL COMMENT 'イニシャル'
,`sex` CHAR(1) DEFAULT 2 NULL COMMENT '性別'
,`birthday` DATE NULL COMMENT '生年月日'
,`mynumber` VARCHAR(20) NULL COMMENT 'マイナンバー'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='個人マスタ';

-- 
-- 住所マスタ `c_g_addr`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_g_addr`;
CREATE TABLE `c_g_addr` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '住所ID'
,`postal_code` CHAR(7) NULL COMMENT '郵便番号'
,`addr1` TEXT NULL COMMENT '住所1'
,`addr2` TEXT NULL COMMENT '住所2'
,`nearest_station` TEXT NULL COMMENT '最寄り駅'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='住所マスタ';

-- 
-- 電話番号マスタ `c_g_tel`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_g_tel`;
CREATE TABLE `c_g_tel` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '電話番号ID'
,`tel_no` VARCHAR(20) NULL COMMENT '電話番号'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='電話番号マスタ';

-- 
-- メールアドレスマスタ `c_g_email`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_g_email`;
CREATE TABLE `c_g_email` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'メールアドレスID'
,`email` VARCHAR(512) NULL COMMENT 'メールアドレス'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='メールアドレスマスタ';

-- 
-- 種別マスタ `c_g_kind`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `c_g_kind`;
CREATE TABLE `c_g_kind` (
 `kbn` VARCHAR(8) NOT NULL COMMENT '区分'
,`seq` INT(4) DEFAULT 0 NOT NULL COMMENT '連番'
,`code` VARCHAR(10) NULL COMMENT 'コード'
,`name` TEXT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`kbn`,`seq`)
) ENGINE = InnoDB COMMENT='種別マスタ';

-- 
-- 社員マスタ `m_employee`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `m_employee`;
CREATE TABLE `m_employee` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '社員ID'
,`employee_no` VARCHAR(128) NULL COMMENT '社員番号'
,`personal_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '個人ID'
,`emp_status` CHAR(2) NOT NULL COMMENT '状態'
,`position` TEXT NULL COMMENT '役職'
,`joining_date` DATE NOT NULL COMMENT '入社日'
,`leaving_date` DATE NULL COMMENT '退社日'
,`acquisition_date` DATE NULL COMMENT '雇用保険資格取得日'
,`insurance_no` VARCHAR(20) NULL COMMENT '雇用保険被保険番号'
,`pension_no` VARCHAR(20) NULL COMMENT '基礎年金番号'
,`default_group_id` BIGINT(20) UNSIGNED NULL COMMENT 'デフォルトグループID'
,`account_id` BIGINT(20) UNSIGNED NULL COMMENT 'アカウントID'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='社員マスタ';

-- 
-- 社員住所サブマスタ `s_emp_addr`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_addr`;
CREATE TABLE `s_emp_addr` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`addr_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '住所ID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員住所サブマスタ';

-- 
-- 社員電話番号サブマスタ `s_emp_tel`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_tel`;
CREATE TABLE `s_emp_tel` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`tel_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '電話番号ID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員電話番号サブマスタ';

-- 
-- 社員メールアドレスサブマスタ `s_emp_email`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_email`;
CREATE TABLE `s_emp_email` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`email_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'メールアドレスID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員メールアドレスサブマスタ';

-- 
-- 社員家族サブマスタ `s_emp_family`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_family`;
CREATE TABLE `s_emp_family` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`relationship` TEXT NOT NULL COMMENT '続柄'
,`personal_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '個人ID'
,`addr_id` BIGINT(20) UNSIGNED NULL COMMENT '住所ID'
,`tel_id` BIGINT(20) UNSIGNED NULL COMMENT '電話番号ID'
,`email_id` BIGINT(20) UNSIGNED NULL COMMENT 'メールアドレスID'
,`living` CHAR(1) NULL COMMENT '同居区分'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員家族サブマスタ';

-- 
-- 社員緊急連絡先サブマスタ `s_emp_emergency`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_emergency`;
CREATE TABLE `s_emp_emergency` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`personal_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '個人ID'
,`addr_id` BIGINT(20) UNSIGNED NULL COMMENT '住所ID'
,`tel_id` BIGINT(20) UNSIGNED NULL COMMENT '電話番号ID'
,`email_id` BIGINT(20) UNSIGNED NULL COMMENT 'メールアドレスID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員緊急連絡先サブマスタ';

-- 
-- 社員備考サブマスタ `s_emp_memo`
-- generated at 2018/01/04. 
-- 
DROP TABLE IF EXISTS `s_emp_memo`;
CREATE TABLE `s_emp_memo` (
 `employee_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員ID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`employee_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員備考サブマスタ';




-- 
-- 外部キー.
-- 
ALTER TABLE c_m_account DROP FOREIGN KEY IF EXISTS c_m_account_default_authority_id; ALTER TABLE c_m_account ADD CONSTRAINT c_m_account_default_authority_id FOREIGN KEY (default_authority_id) REFERENCES c_m_authority(id);
ALTER TABLE c_s_acct_key DROP FOREIGN KEY IF EXISTS c_s_acct_key_account_id; ALTER TABLE c_s_acct_key ADD CONSTRAINT c_s_acct_key_account_id FOREIGN KEY (account_id) REFERENCES c_m_account(id);
ALTER TABLE c_s_acct_contact DROP FOREIGN KEY IF EXISTS c_s_acct_contact_account_id; ALTER TABLE c_s_acct_contact ADD CONSTRAINT c_s_acct_contact_account_id FOREIGN KEY (account_id) REFERENCES c_m_account(id);
ALTER TABLE c_t_login DROP FOREIGN KEY IF EXISTS c_t_login_account_id; ALTER TABLE c_t_login ADD CONSTRAINT c_t_login_account_id FOREIGN KEY (account_id) REFERENCES c_m_account(id);
ALTER TABLE c_t_login DROP FOREIGN KEY IF EXISTS c_t_login_selected_group_id; ALTER TABLE c_t_login ADD CONSTRAINT c_t_login_selected_group_id FOREIGN KEY (selected_group_id) REFERENCES c_m_group(id);
ALTER TABLE c_r_auth_grant DROP FOREIGN KEY IF EXISTS c_r_auth_grant_authority_id; ALTER TABLE c_r_auth_grant ADD CONSTRAINT c_r_auth_grant_authority_id FOREIGN KEY (authority_id) REFERENCES c_m_authority(id);
ALTER TABLE c_r_auth_grant DROP FOREIGN KEY IF EXISTS c_r_auth_grant_permission_id; ALTER TABLE c_r_auth_grant ADD CONSTRAINT c_r_auth_grant_permission_id FOREIGN KEY (permission_id) REFERENCES c_m_permission(id);
ALTER TABLE c_m_group DROP FOREIGN KEY IF EXISTS c_m_group_root_group_id; ALTER TABLE c_m_group ADD CONSTRAINT c_m_group_root_group_id FOREIGN KEY (root_group_id) REFERENCES c_m_group(id);
ALTER TABLE c_r_grp_construction DROP FOREIGN KEY IF EXISTS c_r_grp_construction_ancestor_id; ALTER TABLE c_r_grp_construction ADD CONSTRAINT c_r_grp_construction_ancestor_id FOREIGN KEY (ancestor_id) REFERENCES c_m_group(id);
ALTER TABLE c_r_grp_construction DROP FOREIGN KEY IF EXISTS c_r_grp_construction_descendant_id; ALTER TABLE c_r_grp_construction ADD CONSTRAINT c_r_grp_construction_descendant_id FOREIGN KEY (descendant_id) REFERENCES c_m_group(id);
ALTER TABLE c_r_grp_account DROP FOREIGN KEY IF EXISTS c_r_grp_account_group_id; ALTER TABLE c_r_grp_account ADD CONSTRAINT c_r_grp_account_group_id FOREIGN KEY (group_id) REFERENCES c_m_group(id);
ALTER TABLE c_r_grp_account DROP FOREIGN KEY IF EXISTS c_r_grp_account_account_id; ALTER TABLE c_r_grp_account ADD CONSTRAINT c_r_grp_account_account_id FOREIGN KEY (account_id) REFERENCES c_m_account(id);
ALTER TABLE c_r_grp_account DROP FOREIGN KEY IF EXISTS c_r_grp_account_group_authority_id; ALTER TABLE c_r_grp_account ADD CONSTRAINT c_r_grp_account_group_authority_id FOREIGN KEY (group_authority_id) REFERENCES c_m_authority(id);
ALTER TABLE m_employee DROP FOREIGN KEY IF EXISTS m_employee_personal_id; ALTER TABLE m_employee ADD CONSTRAINT m_employee_personal_id FOREIGN KEY (personal_id) REFERENCES c_g_personal(id);
ALTER TABLE m_employee DROP FOREIGN KEY IF EXISTS m_employee_default_group_id; ALTER TABLE m_employee ADD CONSTRAINT m_employee_default_group_id FOREIGN KEY (default_group_id) REFERENCES c_m_group(id);
ALTER TABLE m_employee DROP FOREIGN KEY IF EXISTS m_employee_account_id; ALTER TABLE m_employee ADD CONSTRAINT m_employee_account_id FOREIGN KEY (account_id) REFERENCES c_m_account(id);
ALTER TABLE s_emp_addr DROP FOREIGN KEY IF EXISTS s_emp_addr_employee_id; ALTER TABLE s_emp_addr ADD CONSTRAINT s_emp_addr_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);
ALTER TABLE s_emp_addr DROP FOREIGN KEY IF EXISTS s_emp_addr_addr_id; ALTER TABLE s_emp_addr ADD CONSTRAINT s_emp_addr_addr_id FOREIGN KEY (addr_id) REFERENCES c_g_addr(id);
ALTER TABLE s_emp_tel DROP FOREIGN KEY IF EXISTS s_emp_tel_employee_id; ALTER TABLE s_emp_tel ADD CONSTRAINT s_emp_tel_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);
ALTER TABLE s_emp_tel DROP FOREIGN KEY IF EXISTS s_emp_tel_tel_id; ALTER TABLE s_emp_tel ADD CONSTRAINT s_emp_tel_tel_id FOREIGN KEY (tel_id) REFERENCES c_g_tel(id);
ALTER TABLE s_emp_email DROP FOREIGN KEY IF EXISTS s_emp_email_employee_id; ALTER TABLE s_emp_email ADD CONSTRAINT s_emp_email_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);
ALTER TABLE s_emp_email DROP FOREIGN KEY IF EXISTS s_emp_email_email_id; ALTER TABLE s_emp_email ADD CONSTRAINT s_emp_email_email_id FOREIGN KEY (email_id) REFERENCES c_g_email(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_employee_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_personal_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_personal_id FOREIGN KEY (personal_id) REFERENCES c_g_personal(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_addr_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_addr_id FOREIGN KEY (addr_id) REFERENCES c_g_addr(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_tel_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_tel_id FOREIGN KEY (tel_id) REFERENCES c_g_tel(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_email_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_email_id FOREIGN KEY (email_id) REFERENCES c_g_email(id);
ALTER TABLE s_emp_emergency DROP FOREIGN KEY IF EXISTS s_emp_emergency_employee_id; ALTER TABLE s_emp_emergency ADD CONSTRAINT s_emp_emergency_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);
ALTER TABLE s_emp_emergency DROP FOREIGN KEY IF EXISTS s_emp_emergency_personal_id; ALTER TABLE s_emp_emergency ADD CONSTRAINT s_emp_emergency_personal_id FOREIGN KEY (personal_id) REFERENCES c_g_personal(id);
ALTER TABLE s_emp_emergency DROP FOREIGN KEY IF EXISTS s_emp_emergency_addr_id; ALTER TABLE s_emp_emergency ADD CONSTRAINT s_emp_emergency_addr_id FOREIGN KEY (addr_id) REFERENCES c_g_addr(id);
ALTER TABLE s_emp_emergency DROP FOREIGN KEY IF EXISTS s_emp_emergency_tel_id; ALTER TABLE s_emp_emergency ADD CONSTRAINT s_emp_emergency_tel_id FOREIGN KEY (tel_id) REFERENCES c_g_tel(id);
ALTER TABLE s_emp_emergency DROP FOREIGN KEY IF EXISTS s_emp_emergency_email_id; ALTER TABLE s_emp_emergency ADD CONSTRAINT s_emp_emergency_email_id FOREIGN KEY (email_id) REFERENCES c_g_email(id);
ALTER TABLE s_emp_memo DROP FOREIGN KEY IF EXISTS s_emp_memo_employee_id; ALTER TABLE s_emp_memo ADD CONSTRAINT s_emp_memo_employee_id FOREIGN KEY (employee_id) REFERENCES m_employee(id);



-- 
-- IDリセット.
-- 
ALTER TABLE c_m_account AUTO_INCREMENT = 1;
ALTER TABLE c_m_permission AUTO_INCREMENT = 1;
ALTER TABLE c_m_authority AUTO_INCREMENT = 1;
ALTER TABLE c_m_group AUTO_INCREMENT = 1;
ALTER TABLE c_g_personal AUTO_INCREMENT = 1;
ALTER TABLE c_g_addr AUTO_INCREMENT = 1;
ALTER TABLE c_g_tel AUTO_INCREMENT = 1;
ALTER TABLE c_g_email AUTO_INCREMENT = 1;
ALTER TABLE m_employee AUTO_INCREMENT = 1;



-- 外部キーを有効
SET FOREIGN_KEY_CHECKS=1;
