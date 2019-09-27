-- 外部キーを無効
SET FOREIGN_KEY_CHECKS=0;

-- ////////////////////////////////////////////////////////////////////////////
-- 
-- スキーマー名
--   mvc_skeleton_db
-- 
-- ユーザーID
--   mvc_skeleton_db
-- 
-- 照合順序
--   utf8mb4_bin
-- 
-- t_crud_sample CRUDサンプルテーブル  TCS
-- m_employee  従業員マスタ  EMP
-- 
-- 
-- 
-- 
-- ////////////////////////////////////////////////////////////////////////////



-- ////////////////////////////////////////////////////////////////////////////
-- テーブル.
-- ////////////////////////////////////////////////////////////////////////////
-- 
-- CRUDサンプルテーブル `t_crud_sample` `TCS`
-- generated at 2019/09/25. 
-- 
DROP TABLE IF EXISTS `t_crud_sample`;
CREATE TABLE `t_crud_sample` (
 `id` BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID'
,`code` CHAR(10) NOT NULL COMMENT 'コード'
,`name` VARCHAR(40) NOT NULL COMMENT '名称'
,`memo` TEXT NULL COMMENT '備考'
,`check` INT(1) NULL COMMENT 'チェックボックス'
,`radio` INT(1) NULL COMMENT 'ラジオボタン'
,`select` INT(1) NULL COMMENT 'コンボボックス'
,`updated` TIMESTAMP NOT NULL COMMENT '更新日時'
,PRIMARY KEY(`id`)
) ENGINE = InnoDB COMMENT='CRUDサンプルテーブル';






-- ////////////////////////////////////////////////////////////////////////////
-- IDリセット.
-- ////////////////////////////////////////////////////////////////////////////
ALTER TABLE `t_crud_sample` AUTO_INCREMENT = 1;



-- 外部キーを有効
SET FOREIGN_KEY_CHECKS=1;
