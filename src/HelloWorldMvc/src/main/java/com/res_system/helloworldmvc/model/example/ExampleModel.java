package com.res_system.helloworldmvc.model.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.res_system.helloworldmvc.commons.model.dao.ExampleDao;

/**
 * <pre>
 * モデルクラス(テスト用).
 *
 * [テスト用テーブル]
 * DROP TABLE `test_table`;
 * CREATE TABLE `test_table` (
 *   `id` bigint(16) unsigned  not null auto_increment COMMENT 'id' ,
 *   `code` char(10) not null COMMENT 'コード' ,
 *   `name` varchar(40) not null COMMENT '名称' ,
 *   `memo` text null COMMENT 'メモ' ,
 *   `created` datetime not null COMMENT '作成日' ,
 *   PRIMARY KEY  (`id`)
 * ) ENGINE=InnoDB;
 * INSERT INTO `test_table` (`id`, `code`, `name`, `memo`, `created`) VALUES (NULL, '0000000001', 'TEST DATA', 'MEMO', now());
 *
 * </pre>
 * @author res.
 */
@Dependent
public class ExampleModel {

    //---------------------------------------------- [private] モデルクラス.
    /** データアクセスクラス. */
    @Inject
    private ExampleDao dao;



    //---------------------------------------------- [public] 業務処理.
    /**
     * SQLを直接実行してデータ取得する.
     * @param form
     */
    public void find(ExampleForm form) {
        String sql = "SELECT * FROM `test_table` WHERE code = ?";
        try (PreparedStatement pstmt = dao.prepareStatement(sql)) {
            pstmt.setString(1, "0000000001");
            try (ResultSet rs = dao.executeQuery(pstmt)) {
                if (rs.next()) {
                    form.setMessage(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * SQLを直接実行してデータ更新する.
     * @param form
     * @return 結果.
     * @throws Exception
     */
    public boolean update(ExampleForm form) throws Exception {
        dao.begin();
        String sql = "UPDATE `test_table` SET name = ? WHERE id = ?";
        try (PreparedStatement pstmt = dao.prepareStatement(sql)) {
            pstmt.setString(1, form.getMessage());
            pstmt.setString(2, "1");
            int result = dao.executeUpdate(pstmt);
            dao.commit();
            return (result > 0);
        } catch (Exception e) {
            dao.rollback();
            throw e;
        }
    }


}
