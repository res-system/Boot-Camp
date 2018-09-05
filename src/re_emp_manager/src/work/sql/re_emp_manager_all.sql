-- 外部キーを無効
SET FOREIGN_KEY_CHECKS=0;

-- ////////////////////////////////////////////////////////////////////////////
-- 
-- スキーマー名
--   re_emp_manager
-- 
-- ユーザーID
--   re_emp_manager
-- 
-- パスワード
--   re_emp_manager
-- 
-- 照合順序
--   utf8mb4_bin
-- 
-- m_account	アカウントマスタ	ACC
-- m_user	ユーザーマスタ	USR
-- s_user_key	ユーザーキーサブマスタ	USRKY
-- t_login	ログイン認証トラン	LGN
-- m_group	グループマスタ	GRP
-- s_grp_account	グループアカウントサブマスタ	GRPAC
-- r_grp_member	グループメンバー関連マスタ	GRPMB
-- m_authority	権限マスタ	AUT
-- m_permission	権限許可マスタ	PER
-- r_grant	権限付与関連マスタ	GRT
-- r_menu	メニュー関連マスタ	MNU
-- m_employee	社員マスタ	EMP
-- m_emp_info_hd	社員情報ヘッダーマスタ	EMPHD
-- s_emp_info	社員情報サブマスタ	EMPIF
-- s_emp_addr	社員住所サブマスタ	EMPAD
-- s_emp_tel	社員電話番号サブマスタ	EMPTL
-- s_emp_email	社員メールアドレスサブマスタ	EMPEL
-- s_emp_family	社員家族サブマスタ	EMPFM
-- g_kind	種別マスタ	KND
-- g_personal	個人マスタ	PSN
-- g_addr	住所マスタ	ADD
-- 
-- ////////////////////////////////////////////////////////////////////////////



-- ////////////////////////////////////////////////////////////////////////////
-- テーブル.
-- ////////////////////////////////////////////////////////////////////////////
-- 
-- アカウントマスタ `m_account`
-- generated at 2018/08/08. 
-- 
DROP TABLE IF EXISTS `m_account`;
CREATE TABLE `m_account` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'アカウントID'
,`login_id` VARCHAR(191) UNIQUE NOT NULL COMMENT 'ログインID'
,`registration_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '登録日'
,`acc_status` CHAR(1) DEFAULT '0' NOT NULL COMMENT 'アカウント状態'
,`user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='アカウントマスタ';

-- 
-- ユーザーマスタ `m_user`
-- generated at 2018/08/16. 
-- 
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ユーザーID'
,`name` TEXT NOT NULL COMMENT 'ユーザー名'
,`user_kbn` CHAR(1) NOT NULL COMMENT 'ユーザー区分'
,`default_authority_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'デフォルト権限ID'
,`email_addr` TEXT NULL COMMENT 'メールアドレス'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='ユーザーマスタ';

-- 
-- ユーザーキーサブマスタ `s_user_key`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `s_user_key`;
CREATE TABLE `s_user_key` (
 `user_id` BIGINT(20) UNSIGNED NULL COMMENT 'ユーザーID'
,`seq` INT(9) DEFAULT 1 NOT NULL COMMENT '連番'
,`key` VARCHAR(512) NOT NULL COMMENT 'キー'
,`expiration_time` TIMESTAMP NULL COMMENT '有効期限'
,`initial_flg` CHAR(1) DEFAULT '0' NOT NULL COMMENT '初期値フラグ'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`seq`)
) ENGINE = InnoDB COMMENT='ユーザーキーサブマスタ';

-- 
-- ログイン認証トラン `t_login`
-- generated at 2018/08/08. 
-- 
DROP TABLE IF EXISTS `t_login`;
CREATE TABLE `t_login` (
 `token` VARCHAR(191) UNIQUE NOT NULL COMMENT '認証トークン'
,`login_dt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'ログイン日時'
,`expiration_time` TIMESTAMP NOT NULL COMMENT '有効期限'
,`save_flg` CHAR(1) DEFAULT '0' NOT NULL COMMENT '保存フラグ'
,`account_id` BIGINT(20) UNSIGNED NULL COMMENT 'アカウントID'
,`user_kbn` CHAR(1) NOT NULL COMMENT 'ユーザー区分'
,`user_id` BIGINT(20) UNSIGNED NULL COMMENT 'ユーザーID'
,`group_id` BIGINT(20) UNSIGNED NULL COMMENT 'グループID'
,`authority_id` BIGINT(20) UNSIGNED NULL COMMENT '権限ID'
,`client_info` TEXT NULL COMMENT 'クライアント情報'
,PRIMARY KEY(`token`)
) ENGINE = InnoDB COMMENT='ログイン認証トラン';

-- 
-- グループマスタ `m_group`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `m_group`;
CREATE TABLE `m_group` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'グループID'
,`code` VARCHAR(191) UNIQUE NULL COMMENT '識別コード'
,`name` TEXT NOT NULL COMMENT 'グループ名'
,`root_group_id` BIGINT(20) UNSIGNED NULL COMMENT 'ルートグループID'
,`owner_account_id` BIGINT(20) UNSIGNED NULL COMMENT 'オーナーアカウントID'
,`grp_status` CHAR(1) DEFAULT '0' NOT NULL COMMENT 'グループ状態'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='グループマスタ';

-- 
-- グループアカウントサブマスタ `s_grp_account`
-- generated at 2018/08/16. 
-- 
DROP TABLE IF EXISTS `s_grp_account`;
CREATE TABLE `s_grp_account` (
 `root_group_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ルートグループID'
,`user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`default_group_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'デフォルトグループID'
,`account_id` BIGINT(20) UNSIGNED NULL COMMENT 'アカウントID'
,`login_id` VARCHAR(191) NULL COMMENT 'ログインID'
,`gpac_status` CHAR(1) DEFAULT '0' NOT NULL COMMENT 'グループアカウント状態'
,`registration_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '登録日'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`root_group_id`,`user_id`)
) ENGINE = InnoDB COMMENT='グループアカウントサブマスタ';

-- 
-- グループメンバー関連マスタ `r_grp_member`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `r_grp_member`;
CREATE TABLE `r_grp_member` (
 `group_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'グループID'
,`user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`authority_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '権限ID'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`group_id`,`user_id`)
) ENGINE = InnoDB COMMENT='グループメンバー関連マスタ';

-- 
-- 権限マスタ `m_authority`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `m_authority`;
CREATE TABLE `m_authority` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '権限ID'
,`name` TEXT NOT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='権限マスタ';

-- 
-- 権限許可マスタ `m_permission`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `m_permission`;
CREATE TABLE `m_permission` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '許可ID'
,`key` VARCHAR(191) UNIQUE NOT NULL COMMENT '許可キー'
,`default_url` TEXT NULL COMMENT 'デフォルトURL'
,`name` TEXT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='権限許可マスタ';

-- 
-- 権限付与関連マスタ `r_grant`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `r_grant`;
CREATE TABLE `r_grant` (
 `authority_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '権限ID'
,`permission_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '許可ID'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`authority_id`,`permission_id`)
) ENGINE = InnoDB COMMENT='権限付与関連マスタ';

-- 
-- メニュー関連マスタ `r_menu`
-- generated at 2018/08/13. 
-- 
DROP TABLE IF EXISTS `r_menu`;
CREATE TABLE `r_menu` (
 `no` INT(9) DEFAULT 1 NOT NULL COMMENT 'メニュー番号'
,`seq` INT(9) DEFAULT 1 NOT NULL COMMENT '連番'
,`level` INT(1) DEFAULT 1 NOT NULL COMMENT 'レベル'
,`permission_id` BIGINT(20) UNSIGNED NULL COMMENT '許可ID'
,`title` TEXT NULL COMMENT 'タイトル'
,`description` TEXT NULL COMMENT '説明'
,`icon` TEXT NULL COMMENT 'アイコン'
,`url` TEXT NULL COMMENT 'URL'
,`status` CHAR(1) DEFAULT '0' NOT NULL COMMENT '状態'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`no`,`seq`)
) ENGINE = InnoDB COMMENT='メニュー関連マスタ';

-- 
-- 社員マスタ `m_employee`
-- generated at 2018/08/08. 
-- 
DROP TABLE IF EXISTS `m_employee`;
CREATE TABLE `m_employee` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`personal_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '個人ID'
,`situation` CHAR(1) DEFAULT '0' NOT NULL COMMENT '就業状況'
,`employee_no` TEXT NULL COMMENT '社員番号'
,`hire_date` DATE NULL COMMENT '入社日'
,`retirement_date` DATE NULL COMMENT '退職日'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`)
) ENGINE = InnoDB COMMENT='社員マスタ';

-- 
-- 社員情報ヘッダーマスタ `m_emp_info_hd`
-- generated at 2018/08/07. 
-- 
DROP TABLE IF EXISTS `m_emp_info_hd`;
CREATE TABLE `m_emp_info_hd` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '社員情報ヘッダーID'
,`root_group_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ルートグループID'
,`seq` INT(9) DEFAULT 1 NOT NULL COMMENT '連番'
,`label` TEXT NULL COMMENT 'ヘッダラベル'
,`type` CHAR(2) DEFAULT '0' NOT NULL COMMENT 'タイプ'
,`required` CHAR(1) DEFAULT '0' NOT NULL COMMENT '必須フラグ'
,`maxlength` INT DEFAULT 0 NULL COMMENT '長さ'
,`status` CHAR(1) DEFAULT '0' NOT NULL COMMENT '状態'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='社員情報ヘッダーマスタ';

-- 
-- 社員情報サブマスタ `s_emp_info`
-- generated at 2018/08/08. 
-- 
DROP TABLE IF EXISTS `s_emp_info`;
CREATE TABLE `s_emp_info` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`emp_info_hd_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '社員情報ヘッダーID'
,`value` TEXT NULL COMMENT '内容'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`emp_info_hd_id`)
) ENGINE = InnoDB COMMENT='社員情報サブマスタ';

-- 
-- 社員住所サブマスタ `s_emp_addr`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `s_emp_addr`;
CREATE TABLE `s_emp_addr` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`addr_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '住所ID'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員住所サブマスタ';

-- 
-- 社員電話番号サブマスタ `s_emp_tel`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `s_emp_tel`;
CREATE TABLE `s_emp_tel` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`tel_no` TEXT NULL COMMENT '電話番号'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員電話番号サブマスタ';

-- 
-- 社員メールアドレスサブマスタ `s_emp_email`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `s_emp_email`;
CREATE TABLE `s_emp_email` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`email_addr` TEXT NULL COMMENT 'メールアドレス'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員メールアドレスサブマスタ';

-- 
-- 社員家族サブマスタ `s_emp_family`
-- generated at 2018/08/08. 
-- 
DROP TABLE IF EXISTS `s_emp_family`;
CREATE TABLE `s_emp_family` (
 `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'ユーザーID'
,`seq` INT(8) DEFAULT 1 NOT NULL COMMENT '連番'
,`relationship` TEXT NOT NULL COMMENT '続柄'
,`personal_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '個人ID'
,`addr_id` BIGINT(20) UNSIGNED NULL COMMENT '住所ID'
,`tel_no` TEXT NULL COMMENT '電話番号'
,`email_addr` TEXT NULL COMMENT 'メールアドレス'
,`living` CHAR(1) DEFAULT '0' NULL COMMENT '同居区分'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`user_id`,`seq`)
) ENGINE = InnoDB COMMENT='社員家族サブマスタ';

-- 
-- 種別マスタ `g_kind`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `g_kind`;
CREATE TABLE `g_kind` (
 `kbn` VARCHAR(8) NOT NULL COMMENT '区分'
,`seq` INT(9) DEFAULT 0 NOT NULL COMMENT '連番'
,`code` VARCHAR(10) NULL COMMENT 'コード'
,`name` TEXT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`kbn`,`seq`)
) ENGINE = InnoDB COMMENT='種別マスタ';

-- 
-- 個人マスタ `g_personal`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `g_personal`;
CREATE TABLE `g_personal` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '個人ID'
,`family_name` TEXT NULL COMMENT '氏名（姓）'
,`first_name` TEXT NULL COMMENT '氏名（名）'
,`family_name_kana` TEXT NULL COMMENT 'カナ氏名（姓）'
,`first_name_kana` TEXT NULL COMMENT 'カナ氏名（名）'
,`maiden_name` TEXT NULL COMMENT '旧姓'
,`maiden_name_kana` TEXT NULL COMMENT 'カナ旧姓'
,`initial` TEXT NULL COMMENT 'イニシャル'
,`sex` CHAR(1) DEFAULT '2' NULL COMMENT '性別'
,`birthday` DATE NULL COMMENT '生年月日'
,`mynumber` VARCHAR(20) NULL COMMENT 'マイナンバー'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='個人マスタ';

-- 
-- 住所マスタ `g_addr`
-- generated at 2018/08/06. 
-- 
DROP TABLE IF EXISTS `g_addr`;
CREATE TABLE `g_addr` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '住所ID'
,`postal_code` CHAR(7) NULL COMMENT '郵便番号'
,`addr1` TEXT NULL COMMENT '住所1'
,`addr2` TEXT NULL COMMENT '住所2'
,`nearest_station` TEXT NULL COMMENT '最寄り駅'
,`updated_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '更新者ID'
,`updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
,`created_id` BIGINT(20) UNSIGNED DEFAULT 1 NOT NULL COMMENT '作成者ID'
,`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='住所マスタ';



-- ////////////////////////////////////////////////////////////////////////////
-- 外部キー.
-- ////////////////////////////////////////////////////////////////////////////
ALTER TABLE m_account DROP FOREIGN KEY IF EXISTS m_account_user_id; ALTER TABLE m_account ADD CONSTRAINT m_account_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE m_user DROP FOREIGN KEY IF EXISTS m_user_default_authority_id; ALTER TABLE m_user ADD CONSTRAINT m_user_default_authority_id FOREIGN KEY (default_authority_id) REFERENCES m_authority(id);
ALTER TABLE s_user_key DROP FOREIGN KEY IF EXISTS s_user_key_user_id; ALTER TABLE s_user_key ADD CONSTRAINT s_user_key_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE t_login DROP FOREIGN KEY IF EXISTS t_login_account_id; ALTER TABLE t_login ADD CONSTRAINT t_login_account_id FOREIGN KEY (account_id) REFERENCES m_account(id);
ALTER TABLE t_login DROP FOREIGN KEY IF EXISTS t_login_user_id; ALTER TABLE t_login ADD CONSTRAINT t_login_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE t_login DROP FOREIGN KEY IF EXISTS t_login_group_id; ALTER TABLE t_login ADD CONSTRAINT t_login_group_id FOREIGN KEY (group_id) REFERENCES m_group(id);
ALTER TABLE t_login DROP FOREIGN KEY IF EXISTS t_login_authority_id; ALTER TABLE t_login ADD CONSTRAINT t_login_authority_id FOREIGN KEY (authority_id) REFERENCES m_authority(id);
ALTER TABLE m_group DROP FOREIGN KEY IF EXISTS m_group_root_group_id; ALTER TABLE m_group ADD CONSTRAINT m_group_root_group_id FOREIGN KEY (root_group_id) REFERENCES m_group(id);
ALTER TABLE m_group DROP FOREIGN KEY IF EXISTS m_group_owner_account_id; ALTER TABLE m_group ADD CONSTRAINT m_group_owner_account_id FOREIGN KEY (owner_account_id) REFERENCES m_account(id);
ALTER TABLE s_grp_account DROP FOREIGN KEY IF EXISTS s_grp_account_root_group_id; ALTER TABLE s_grp_account ADD CONSTRAINT s_grp_account_root_group_id FOREIGN KEY (root_group_id) REFERENCES m_group(id);
ALTER TABLE s_grp_account DROP FOREIGN KEY IF EXISTS s_grp_account_user_id; ALTER TABLE s_grp_account ADD CONSTRAINT s_grp_account_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_grp_account DROP FOREIGN KEY IF EXISTS s_grp_account_default_group_id; ALTER TABLE s_grp_account ADD CONSTRAINT s_grp_account_default_group_id FOREIGN KEY (default_group_id) REFERENCES m_group(id);
ALTER TABLE s_grp_account DROP FOREIGN KEY IF EXISTS s_grp_account_account_id; ALTER TABLE s_grp_account ADD CONSTRAINT s_grp_account_account_id FOREIGN KEY (account_id) REFERENCES m_account(id);
ALTER TABLE r_grp_member DROP FOREIGN KEY IF EXISTS r_grp_member_group_id; ALTER TABLE r_grp_member ADD CONSTRAINT r_grp_member_group_id FOREIGN KEY (group_id) REFERENCES m_group(id);
ALTER TABLE r_grp_member DROP FOREIGN KEY IF EXISTS r_grp_member_user_id; ALTER TABLE r_grp_member ADD CONSTRAINT r_grp_member_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE r_grp_member DROP FOREIGN KEY IF EXISTS r_grp_member_authority_id; ALTER TABLE r_grp_member ADD CONSTRAINT r_grp_member_authority_id FOREIGN KEY (authority_id) REFERENCES m_authority(id);
ALTER TABLE r_grant DROP FOREIGN KEY IF EXISTS r_grant_authority_id; ALTER TABLE r_grant ADD CONSTRAINT r_grant_authority_id FOREIGN KEY (authority_id) REFERENCES m_authority(id);
ALTER TABLE r_grant DROP FOREIGN KEY IF EXISTS r_grant_permission_id; ALTER TABLE r_grant ADD CONSTRAINT r_grant_permission_id FOREIGN KEY (permission_id) REFERENCES m_permission(`id`);
ALTER TABLE r_menu DROP FOREIGN KEY IF EXISTS r_menu_permission_id; ALTER TABLE r_menu ADD CONSTRAINT r_menu_permission_id FOREIGN KEY (permission_id) REFERENCES m_permission(`id`);
ALTER TABLE m_employee DROP FOREIGN KEY IF EXISTS m_employee_user_id; ALTER TABLE m_employee ADD CONSTRAINT m_employee_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE m_employee DROP FOREIGN KEY IF EXISTS m_employee_personal_id; ALTER TABLE m_employee ADD CONSTRAINT m_employee_personal_id FOREIGN KEY (personal_id) REFERENCES g_personal(id);
ALTER TABLE s_emp_info DROP FOREIGN KEY IF EXISTS s_emp_info_user_id; ALTER TABLE s_emp_info ADD CONSTRAINT s_emp_info_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_emp_info DROP FOREIGN KEY IF EXISTS s_emp_info_emp_info_hd_id; ALTER TABLE s_emp_info ADD CONSTRAINT s_emp_info_emp_info_hd_id FOREIGN KEY (emp_info_hd_id) REFERENCES m_emp_info_hd(id);
ALTER TABLE s_emp_addr DROP FOREIGN KEY IF EXISTS s_emp_addr_user_id; ALTER TABLE s_emp_addr ADD CONSTRAINT s_emp_addr_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_emp_addr DROP FOREIGN KEY IF EXISTS s_emp_addr_addr_id; ALTER TABLE s_emp_addr ADD CONSTRAINT s_emp_addr_addr_id FOREIGN KEY (addr_id) REFERENCES g_addr(id);
ALTER TABLE s_emp_tel DROP FOREIGN KEY IF EXISTS s_emp_tel_user_id; ALTER TABLE s_emp_tel ADD CONSTRAINT s_emp_tel_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_emp_email DROP FOREIGN KEY IF EXISTS s_emp_email_user_id; ALTER TABLE s_emp_email ADD CONSTRAINT s_emp_email_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_user_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_user_id FOREIGN KEY (user_id) REFERENCES m_user(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_personal_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_personal_id FOREIGN KEY (personal_id) REFERENCES g_personal(id);
ALTER TABLE s_emp_family DROP FOREIGN KEY IF EXISTS s_emp_family_addr_id; ALTER TABLE s_emp_family ADD CONSTRAINT s_emp_family_addr_id FOREIGN KEY (addr_id) REFERENCES g_addr(id);



-- ////////////////////////////////////////////////////////////////////////////
-- その他.
-- ////////////////////////////////////////////////////////////////////////////
-- 種別マスタ重複防止
ALTER TABLE `g_kind` ADD UNIQUE uk_g_kind (`kbn`, `code`);
-- グループアカウントログインID重複防止
ALTER TABLE `s_grp_account` ADD UNIQUE uk_s_grp_account (`root_group_id`, `login_id`);
-- グループアカウントアカウントID重複防止
ALTER TABLE `s_grp_account` ADD UNIQUE uk_s_grp_account_account_id(`root_group_id`, `account_id`);



-- ////////////////////////////////////////////////////////////////////////////
-- IDリセット.
-- ////////////////////////////////////////////////////////////////////////////
ALTER TABLE m_authority AUTO_INCREMENT = 1;
ALTER TABLE m_permission AUTO_INCREMENT = 1;
ALTER TABLE m_account AUTO_INCREMENT = 1;
ALTER TABLE m_user AUTO_INCREMENT = 1;
ALTER TABLE m_group AUTO_INCREMENT = 1;
ALTER TABLE m_emp_info_hd AUTO_INCREMENT = 1;
ALTER TABLE g_personal AUTO_INCREMENT = 1;
ALTER TABLE g_addr AUTO_INCREMENT = 1;



-- 外部キーを有効
SET FOREIGN_KEY_CHECKS=1;
