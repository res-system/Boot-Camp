-- ================================================================================================
-- 外部キーを無効
SET FOREIGN_KEY_CHECKS=0;



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



-- ////////////////////////////////////////////////////////////////////////////
-- データリセット.
-- ////////////////////////////////////////////////////////////////////////////
TRUNCATE TABLE `m_account`;
TRUNCATE TABLE `r_acc_cooperation`;
TRUNCATE TABLE `m_user`;
TRUNCATE TABLE `s_user_key`;
TRUNCATE TABLE `t_login`;
TRUNCATE TABLE `m_group`;
TRUNCATE TABLE `s_grp_account`;
TRUNCATE TABLE `r_grp_member`;
TRUNCATE TABLE `m_authority`;
TRUNCATE TABLE `m_permission`;
TRUNCATE TABLE `r_grant`;
TRUNCATE TABLE `r_menu`;
TRUNCATE TABLE `m_employee`;
TRUNCATE TABLE `m_emp_info_hd`;
TRUNCATE TABLE `s_emp_info`;
TRUNCATE TABLE `s_emp_addr`;
TRUNCATE TABLE `s_emp_tel`;
TRUNCATE TABLE `s_emp_email`;
TRUNCATE TABLE `s_emp_family`;
TRUNCATE TABLE `g_kind`;
TRUNCATE TABLE `g_personal`;
TRUNCATE TABLE `g_addr`;



-- ////////////////////////////////////////////////////////////////////////////
-- 共通データ.
-- ////////////////////////////////////////////////////////////////////////////
-- g_kind --
INSERT INTO `g_kind`
  (`kbn`, `seq`, `code`, `name`, `memo`) 
VALUES 
  ('Stat',    0,  NULL, '状態', NULL)
 ,('Stat',    1,  '0',  '有効', NULL)
 ,('Stat',    2,  '1',  '無効', NULL)
 ,('AcStat',  0,  NULL, 'アカウント状態', NULL)
 ,('AcStat',  1,  '0', '仮登録',          NULL)
 ,('AcStat',  2,  '1', '有効',            NULL)
 ,('AcStat',  3,  '8', '休止',            NULL)
 ,('AcStat',  4,  '9', '無効',            NULL)
 ,('UsrKbn',  0,  NULL, 'ユーザー区分', NULL)
 ,('UsrKbn',  1,  '1',  'システム',     NULL)
 ,('UsrKbn',  2,  '2',  '一般',         NULL)
 ,('UsrKbn',  3,  '3',  'グループ',     NULL)
 ,('InitFlg', 0,  NULL, '初期値フラグ', NULL)
 ,('InitFlg', 1,  '0',  '初期値',       NULL)
 ,('InitFlg', 2,  '1',  '変更済',       NULL)
 ,('SaveFlg', 0,  NULL, '保存フラグ',   NULL)
 ,('SaveFlg', 1,  '0',  '保存しない',   NULL)
 ,('SaveFlg', 2,  '1',  '保存する',     NULL)
 ,('GrpStat', 0,  NULL, 'グループ状態', NULL)
 ,('GrpStat', 1,  '0',  '仮登録',       NULL)
 ,('GrpStat', 2,  '1',  '有効',         NULL)
 ,('GrpStat', 3,  '8',  '休止',         NULL)
 ,('GrpStat', 4,  '9',  '無効',         NULL)
 ,('GAcStat', 0,  NULL, 'グループアカウント状態', NULL)
 ,('GAcStat', 1,  '0',  '招待中',                 NULL)
 ,('GAcStat', 2,  '1',  '有効',                   NULL)
 ,('GAcStat', 3,  '8',  '休止',                   NULL)
 ,('GAcStat', 4,  '9',  '無効',                   NULL)
 ,('Sitch',   0,  NULL, '就業状況', NULL)
 ,('Sitch',   1,  '0',  '予定',     NULL)
 ,('Sitch',   2,  '1',  '在職',     NULL)
 ,('Sitch',   3,  '8',  '休職',     NULL)
 ,('Sitch',   4,  '9',  '退職',     NULL)
 ,('InfType', 0,  NULL, '情報タイプ',     NULL)
 ,('InfType', 1,  '00', 'ラベル',         NULL)
 ,('InfType', 2,  '01', '文字',           NULL)
 ,('InfType', 3,  '02', 'メモ',           NULL)
 ,('InfType', 4,  '03', '日付',           NULL)
 ,('InfType', 5,  '04', '数値',           NULL)
 ,('InfType', 6,  '05', '数字',           NULL)
 ,('InfType', 7,  '06', '英数字',         NULL)
 ,('InfType', 8,  '07', 'カナ',           NULL)
 ,('InfType', 9,  '08', '電話番号',       NULL)
 ,('InfType', 10, '09', 'メールアドレス', NULL)
 ,('InfType', 11, '10', 'コード',         NULL)
 ,('InfType', 12, '11', 'コード(数値)',   NULL)
 ,('InfType', 13, '12', '郵便番号',       NULL)
 ,('InfType', 14, '90', 'スペース',       NULL)
 ,('ReqFlg',  0,  NULL, '必須フラグ', NULL)
 ,('ReqFlg',  1,  '0',  '任意',       NULL)
 ,('ReqFlg',  2,  '1',  '必須',       NULL)
 ,('Living',  0,  NULL, '同居区分', NULL)
 ,('Living',  1,  '0',  '同居',     NULL)
 ,('Living',  2,  '1',  '別居',     NULL)
 ,('Sex',     0,  NULL, '性別', NULL)
 ,('Sex',     1,  '0',  '男性', NULL)
 ,('Sex',     2,  '1',  '女性', NULL)
 ,('Sex',     3,  '2',  '不明', NULL)
  ;



-- ////////////////////////////////////////////////////////////////////////////
-- 権限データ.
-- ////////////////////////////////////////////////////////////////////////////
-- m_authority --
INSERT INTO `m_authority` 
  (`id`, `name`, `memo`)
VALUES
  (100, 'システム',     'システム管理者ユーザです。')
 ,(200, 'サービス',     'サービス契約を行ったオーナー用のユーザです。')
 ,(300, 'グループ',     'グループユーザです。')
 ,(301, 'マネージャー', 'グループのマネージャーユーザです。')
 ,(302, 'リーダー',     'グループのリーダーユーザです。')
 ,(303, 'メンバー',     'グループのメンバーユーザです。')
  ;

-- m_permission --
INSERT INTO `m_permission` 
  (`id`, `key`, `default_url`, `name`, `memo`)
VALUES 
  (1010, 'main_menu',           '/main_menu',           'メインメニュー',         '')
 ,(1020, 'change_account_name', '/change_account_name', 'アカウント名変更',       'アカウント名を変更します。')
 ,(1030, 'change_password',     '/change_password',     'パスワード変更',         'パスワードを変更します。')
 ,(1040, 'change_group',        '/change_group',        'グループ変更',           '操作するグループを変更します。')
 ,(1050, 'change_account',      '/change_account',      'グループアカウント変更', 'グループアカウントを変更します。')
 ,(2010, 'emp_search',          '/emp_search',          '社員情報検索',     '社員の情報を検索します。')
 ,(3010, 'emp_info',            '/emp_info/init',       '社員個人情報管理', '社員の個人情報を表示します。')
 ,(3020, 'emp_family',          '/emp_family/init',     '社員家族情報管理', '社員の家族情報を表示します。')
 ,(3030, 'emp_attendance',      '/emp_attendance/init', '社員勤怠情報管理', '社員の勤怠情報を表示します。')
 ,(3040, 'emp_expenses',        '/emp_expenses/init',   '社員経費情報管理', '社員の経費情報を表示します。')
 ,(9010, 'mnt_emp_info_hd',     '/mnt_emp_info_hd',     '社員情報ヘッダーマスタメンテナンス', '社員情報ヘッダーマスタのメンテナンスを行います。')
 ,(9020, 'mnt_group_acc',       '/mnt_group_acc',       'グループアカウント情報メンテナンス', 'グループアカウント情報のメンテナンスを行います。')
 ,(9030, 'mnt_group',           '/mnt_group',           'グループ情報メンテナンス',           'グループ情報のメンテナンスを行います。')
  ;

-- r_grant --
INSERT INTO `r_grant` 
  (`authority_id`, `permission_id`, `kbn`)
VALUES 
  (100, 1010 ,NULL)
 ,(100, 1020 ,'2')
 ,(100, 1030 ,NULL)
 ,(100, 1040 ,NULL)
 ,(100, 1050 ,'2')
 ,(100, 2010 ,NULL)
 ,(100, 3010 ,NULL)
 ,(100, 3020 ,NULL)
 ,(100, 3030 ,NULL)
 ,(100, 3040 ,NULL)
 ,(100, 9010 ,NULL)
 ,(100, 9020 ,NULL)
 ,(100, 9030 ,NULL)

 ,(200, 1010 ,NULL)
 ,(200, 1020 ,'2')
 ,(200, 1030 ,NULL)
 ,(200, 1050 ,'2')

 ,(300, 1010 ,NULL)
 ,(300, 1020 ,'2')
 ,(300, 1030 ,NULL)

 ,(301, 1010 ,NULL)
 ,(301, 1020 ,'2')
 ,(301, 1030 ,NULL)
 ,(301, 1040 ,NULL)
 ,(301, 1050 ,'2')
 ,(301, 2010 ,NULL)
 ,(301, 3010 ,NULL)
 ,(301, 3020 ,NULL)
 ,(301, 3030 ,NULL)
 ,(301, 3040 ,NULL)
 ,(301, 9010 ,NULL)
 ,(301, 9020 ,NULL)
 ,(301, 9030 ,NULL)

 ,(302, 1010 ,NULL)
 ,(302, 1020 ,'2')
 ,(302, 1030 ,NULL)
 ,(302, 1040 ,NULL)
 ,(302, 1050 ,'2')
 ,(302, 2010 ,NULL)
 ,(302, 3010 ,NULL)
 ,(302, 3020 ,NULL)
 ,(302, 3030 ,NULL)
 ,(302, 3040 ,NULL)

 ,(303, 1010 ,NULL)
 ,(303, 1020 ,'2')
 ,(303, 1030 ,NULL)
 ,(303, 1040 ,NULL)
 ,(303, 1050 ,'2')
 ,(303, 3010 ,NULL)
 ,(303, 3020 ,NULL)
 ,(303, 3030 ,NULL)
 ,(303, 3040 ,NULL)
  ;

-- r_menu --
INSERT INTO `r_menu` 
  (`no`, `seq`, `level`, `permission_id`, `title`, `description`, `icon`, `url`, `status`)
VALUES 
  (1, 1, 1, 1010, 'TOP',                NULL, 'fa-bars',        NULL, '0')
 ,(1, 2, 1, NULL, 'My社員情報',         NULL, 'fa-id-card',     NULL, '0')
 ,(1, 3, 2, 3010, NULL,                 NULL, 'fa-user-circle', NULL, '0')
 ,(1, 4, 2, 3020, NULL,                 NULL, 'fa-users',       NULL, '0')
 ,(1, 5, 2, 3030, NULL,                 NULL, 'fa-calendar',    NULL, '0')
 ,(1, 6, 2, 3040, NULL,                 NULL, 'fa-dollar',      NULL, '0')
 ,(1, 7, 1, 2010, NULL,                 NULL, 'fa-list',        NULL, '0')
 ,(1, 8, 1, NULL, 'メンテナンス',       NULL, 'fa-cog',         NULL, '0')
 ,(1, 9, 2, 9010, '社員情報ヘッダー',   NULL, 'fa-wrench',      NULL, '0')
 ,(1,10, 2, 9020, 'アカウント情報',     NULL, 'fa-wrench',      NULL, '0')
 ,(1,11, 2, 9030, 'グループ情報',       NULL, 'fa-wrench',      NULL, '0')

 ,(2, 1, 1, 1020, NULL,                 NULL, NULL,             NULL, '0')
 ,(2, 2, 1, 1030, NULL,                 NULL, NULL,             NULL, '0')
 ,(2, 3, 1, 1040, NULL,                 NULL, NULL,             NULL, '0')
 ,(2, 4, 1, 1050, 'アカウント変更',     NULL, NULL,             NULL, '0')

 ,(3, 1, 1, 3010, '個人情報',           NULL, 'fa-user-circle', '/emp_info/show', '0')
 ,(3, 2, 1, 3020, '家族情報',           NULL, 'fa-users',       '/emp_family/show', '0')
 ,(3, 3, 1, 3030, '勤怠情報',           NULL, 'fa-calendar',    '/emp_attendance/show', '0')
 ,(3, 4, 1, 3040, '経費情報',           NULL, 'fa-dollar',      '/emp_expenses/show', '0')
  ;



-- ////////////////////////////////////////////////////////////////////////////
-- ユーザーデータ.
-- ////////////////////////////////////////////////////////////////////////////
-- m_user --
INSERT INTO `m_user` 
  (`id`, `name`, `user_kbn`, `default_authority_id`) 
VALUES 
  (1,   'システム',                 '1',  100)
 ,(10,  'RESオーナーユーザー',      '2',  200)
 ,(20,  'グループBオーナーユーザー','2',  200)
 ,(31,  'エラーユーザー１',         '2',  200)
 ,(32,  'エラーユーザー２',         '2',  200)
 ,(40,  'テストユーザーＸ',         '2',  200)

 ,(11,  '山田 太郎',                '3',  300)
 ,(12,  '山田 次郎',                '3',  300)
 ,(13,  '山田 三郎',                '3',  300)
 ,(14,  '山田 四郎',                '3',  300)
 ,(15,  '山田 五郎',                '3',  300)
 ,(16,  '山田 六郎',                '3',  300)
 ,(17,  '山田 七郎',                '3',  300)

 ,(21,  '鈴木 太郎',                '3',  300)
 ,(22,  '鈴木 次郎',                '3',  300)
 ,(23,  '鈴木 三郎',                '3',  300)
 ,(24,  '鈴木 四郎',                '3',  300)
 ,(25,  '鈴木 五郎',                '3',  300)
 ,(26,  '鈴木 六郎',                '3',  300)
  ;

-- s_user_key --
INSERT INTO `s_user_key` 
  (`user_id`, `seq`, `key`, `expiration_time`, `initial_flg`) 
VALUES 
  (1,   1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(10,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(20,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(31,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(32,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(40,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')

 ,(11,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(12,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(13,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(14,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(15,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(16,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')

 ,(21,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(22,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(23,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(24,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(25,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
 ,(26,  1, 'bd3badf9273a2b46ce2b20b12359ac0c98a91bc1f893d6072685251b550bc317', '2020-03-31', '1')
  ;



-- ////////////////////////////////////////////////////////////////////////////
-- アカウントデータ.
-- ////////////////////////////////////////////////////////////////////////////
-- m_account --
INSERT INTO `m_account` 
  (`id`, `login_id`, `registration_date`, `acc_status`, `user_id`, `memo`) 
VALUES 
  (1,   'system@res-system.com',  '2018-04-01', '1',  1,  'システムユーザーです。変更できません。')
 ,(10,  'test@res-system.com',    '2018-04-01', '1',  10, 'res-system.comのオーナーユーザーです。')
 ,(20,  'group-b@res-system.com', '2018-04-01', '1',  20, 'group-b.comのオーナーユーザーです。')
 ,(31,  'error-1@res-system.com', '2018-04-01', '8',  31, 'エラーユーザーです。')
 ,(32,  'error-2@res-system.com', '2018-04-01', '9',  32, 'エラーユーザーです。')
 ,(40,  'group-ab@res-system.com','2018-04-01', '1',  40, 'グループ複数ユーザーです。')
 ;



-- ////////////////////////////////////////////////////////////////////////////
-- グループデータ.
-- ////////////////////////////////////////////////////////////////////////////
-- m_group --
INSERT INTO `m_group` 
  (`id`, `code`, `name`, `root_group_id`, `owner_account_id`, `grp_status`, `memo`) 
VALUES 
  ('1',   'system',         'システム',     1,  1,    '1', NULL) 
 ,('10',  'res-system.com', 'リスタート',   10, 10,   '1', NULL) 
 ,('11',  NULL,             '特別企画部',   10, NULL, '1', NULL) 
 ,('12',  NULL,             'システム部',   10, NULL, '1', NULL) 
 ,('20',  'group-b.com',    'グループＢ',   20, 20,   '1', NULL) 
  ;

-- s_grp_account --
INSERT INTO `s_grp_account` 
  (`root_group_id`, `user_id`, `default_group_id`, `account_id`, `login_id`, `gpac_status`, `registration_date`, `memo`) 
VALUES 
  (10,  11, 10, 10,   'member01', '1', '2020-03-31', 'オーナーです。') 
 ,(10,  12, 10, NULL, 'member02', '1', '2020-03-31', 'グループメンバーです。') 
 ,(10,  13, 10, NULL, 'member03', '1', '2020-03-31', 'グループメンバーです。') 
 ,(10,  14, 11, NULL, 'member04', '1', '2020-03-31', 'グループメンバーです。') 
 ,(10,  15, 11, NULL, 'member05', '1', '2020-03-31', 'グループメンバーです。') 
 ,(10,  16, 10, NULL, 'member06', '1', '2020-03-31', 'グループメンバーです。') 
 ,(10,  17, 10, NULL, NULL,       '1', '2020-03-31', 'グループメンバーです。') 

 ,(20,  21, 20, 20,   'member01', '1', '2020-03-31', 'オーナーです。') 
 ,(20,  22, 20, NULL, 'member02', '1', '2020-03-31', 'グループメンバーです。') 
 ,(20,  23, 20, NULL, 'member03', '1', '2020-03-31', 'グループメンバーです。') 
 ,(20,  24, 20, NULL, 'member04', '1', '2020-03-31', 'グループメンバーです。') 
 ,(20,  25, 20, NULL, 'member05', '1', '2020-03-31', 'グループメンバーです。') 
 ,(20,  26, 20, NULL, 'member06', '1', '2020-03-31', 'グループメンバーです。') 
  ;

-- r_grp_member --
INSERT INTO `r_grp_member` 
  (`group_id`, `user_id`, `authority_id`) 
VALUES 
  (1,   1,  100) 

 ,(10,  11, 301) 
 ,(10,  12, 302) 
 ,(10,  13, 303) 
 ,(10,  14, 303) 
 ,(10,  16, 303) 
 ,(10,  17, 303) 

 ,(11,  14, 302) 
 ,(11,  15, 303) 
 ,(11,  16, 303) 
 ,(11,  11, 303) 

 ,(20,  21, 301) 
 ,(20,  22, 302) 
 ,(20,  23, 303) 
 ,(20,  24, 303) 
 ,(20,  25, 303) 
 ,(20,  26, 303) 
  ;

-- r_acc_cooperation --
INSERT INTO `r_acc_cooperation` 
  (`account_id`, `root_group_id`, `user_id`, `save_flg`) 
VALUES 
  (10, 10, 11, '1') 
 ,(20, 20, 21, '0') 
 ,(40, 10, 16, '1') 
 ,(40, 20, 26, '0') 
  ;



-- ////////////////////////////////////////////////////////////////////////////
-- 社員データ.
-- ////////////////////////////////////////////////////////////////////////////
-- m_emp_info_hd --
INSERT INTO `m_emp_info_hd`
  (`id`, `root_group_id`, `seq`, `label`, `type`, `required`, `maxlength`, `status`, `memo`) 
VALUES
  (1, 10, 1,  '緊急連絡先',                   '00', '0', 40,  '0', '')
 ,(2, 10, 2,  '緊急連絡先 氏名',              '01', '0', 40,  '0', '')
 ,(3, 10, 3,  '緊急連絡先 氏名(カナ)',        '07', '0', 80,  '0', '')
 ,(4, 10, 4,  '緊急連絡先 郵便番号',          '12', '0', 0,   '1', '')
 ,(5, 10, 5,  '緊急連絡先 住所',              '01', '0', 100, '1', '')
 ,(6, 10, 6,  '緊急連絡先 電話番号',          '08', '0', 0,   '0', '')
 ,(7, 10, 7,  '緊急連絡先 メールアドレス',    '09', '0', 0,   '1', '')
 ,(8, 10, 8,  '',                             '90', '0', 0,   '0', '')
 ,(9, 10, 9,  '雇用保険',                     '00', '0', 0,   '0', '')
 ,(10,10, 10, '雇用保険 資格取得日',          '03', '0', 0,   '0', '')
 ,(11,10, 11, '雇用保険 被保険者番号',        '11', '0', 20,  '0', '')
 ,(12,10, 12, '',                             '90', '0', 0,   '0', '')
 ,(13,10, 13, '社会保険',                     '00', '0', 0,   '0', '')
 ,(14,10, 14, '社会保険 年金手帳の記録番号',  '11', '0', 20,  '0', '')
 ,(15,10, 15, '',                             '90', '0', 0,   '0', '')
 ,(16,10, 16, 'その他',                       '00', '0', 0,   '0', '')
 ,(17,10, 17, '最終学歴',                     '02', '0', 200, '0', '※卒業(中退)年月と学校名・学部と卒業か中退かを記入してください。\r\n　例） ２０００年３月 〇〇大学 経済学部 卒業')
 ,(18,10, 18, '保有資格',                     '02', '0', 500, '0', '')
 ;

-- g_personal --
INSERT INTO `g_personal`
  (`id`, `family_name`, `first_name`, `family_name_kana`, `first_name_kana`, `maiden_name`, `maiden_name_kana`, `initial`, `sex`, `birthday`, `mynumber`) 
VALUES
  (11, '山田', '太郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '0', '1978-08-01', '111122223333')
 ,(12, '山田', '次郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '0', '1980-08-02', NULL)
 ,(13, '山田', '三郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '0', '1981-08-03', NULL)
 ,(14, '山田', '四郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '1', '1998-08-04', NULL)
 ,(15, '山田', '五郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '0', '1968-08-05', NULL)
 ,(16, '山田', '六郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '0', '1978-08-06', NULL)
 ,(17, '山田', '七郎', 'ヤマダ', 'ナニガシ', NULL, NULL, NULL, '1', '1996-08-06', NULL)
 ,(21, '鈴木', '太郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1988-08-07', NULL)
 ,(22, '鈴木', '次郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1988-08-08', NULL)
 ,(23, '鈴木', '三郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1968-08-09', NULL)
 ,(24, '鈴木', '四郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1948-08-10', NULL)
 ,(25, '鈴木', '五郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1958-08-11', NULL)
 ,(26, '鈴木', '六郎', 'スズキ', 'ナニガシ', NULL, NULL, NULL, '0', '1948-08-12', NULL)
 ,(27, '山田', '花子', 'ヤマダ', 'ハナコ',   NULL, NULL, NULL, '1', '1988-08-08', '111122223334')
 ,(28, '山田', '宏',   'ヤマダ', 'ヒロシ',   NULL, NULL, NULL, '0', '2014-08-27', '111122223335')
  ;

-- g_addr --
INSERT INTO `g_addr`
  (`id`, `postal_code`, `addr1`, `addr2`, `nearest_station`) 
VALUES
  (1, '1234567', '大阪府大阪市大坂町', '１－１－１', 'JR大阪')
  ;

-- m_employee --
INSERT INTO `m_employee`
  (`user_id`, `personal_id`, `situation`, `employee_no`, `hire_date`, `retirement_date`, `memo`)
VALUES
  (11, 11, '1', '00001', '2005-04-01', NULL, NULL)
 ,(12, 12, '1', '00002', '2010-04-01', NULL, NULL)
 ,(13, 13, '1', '00003', '2012-04-01', NULL, NULL)
 ,(14, 14, '1', '00004', '2014-04-01', NULL, NULL)
 ,(15, 15, '1', '00005', '2013-04-01', '2018-12-31', NULL)
 ,(16, 16, '1', '00006', '2017-08-01', NULL, NULL)
 ,(17, 17, '1', '00007', '2018-04-01', NULL, NULL)

 ,(21, 21, '1', 'X00001', '2002-04-01', NULL, NULL)
 ,(22, 22, '1', 'X00002', '2004-04-01', NULL, NULL)
 ,(23, 23, '1', 'X00003', '2004-04-01', NULL, NULL)
 ,(24, 24, '1', 'X00004', '2010-04-01', NULL, NULL)
 ,(25, 25, '1', 'X00005', '2012-04-01', '2018-12-31', NULL)
 ,(26, 26, '1', 'X00006', '2012-08-01', NULL, NULL)
  ;

-- s_emp_tel --
INSERT INTO `s_emp_tel` 
  (`user_id`, `seq`, `tel_no`, `memo`) 
VALUES
  (11, 1, '06-7777-8888', '自宅')
  ;

-- s_emp_email --
INSERT INTO `s_emp_email` 
  (`user_id`, `seq`, `email_addr`, `memo`) 
VALUES
  (11, 1, 'test@res-system.com', 'PC')
  ;

-- s_emp_addr --
INSERT INTO `s_emp_addr` 
  (`user_id`, `seq`, `addr_id`, `memo`) 
VALUES
  (11, 1, 1, '現住所')
  ;

-- s_emp_family --
INSERT INTO `s_emp_family` 
  (`user_id`, `seq`, `relationship`, `personal_id`, `addr_id`, `tel_no`, `email_addr`, `living`, `memo`) 
VALUES
  (11, 1, '妻', 27, NULL, NULL, NULL, '0', '配偶者')
 ,(11, 2, '子', 28, NULL, NULL, NULL, '0', '息子')
  ;



-- 外部キーを有効
SET FOREIGN_KEY_CHECKS=1;
-- ================================================================================================
