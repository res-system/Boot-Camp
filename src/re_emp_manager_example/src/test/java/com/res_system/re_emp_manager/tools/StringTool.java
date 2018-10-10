package com.res_system.re_emp_manager.tools;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.res_system.commons.util.ReUtil;

/**
 *
 * 文字列ツール.
 *
 * @author res.
 *
 */
public class StringTool {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ StringTool -------------------------------- ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ -------------------------------- CryptTool ]");
   }


    // ------------------------------------------------------------------------------------------------------------------------
    /** 
     * <pre>
     * 認証トークン[ ReUtil.makeToken() ]. 
     * 
     * 文字数を設定します。
     * JUnit実行後にコンソールに結果が出力されます.
     * </pre>
     */
    @Test
    public void makeToken() throws Exception {
        // 文字数.
        int[] targets = {
                 10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,4
                ,8
                };

        // 処理. ----------------------------------------- //
        System.out.println("【 認証トークン[ ReUtil.makeToken() ] 】");
        if (targets.length > 0) {
            for (int len : targets) {
                if (len > 0) {
                    System.out.println("makeToken(" + len + ")->[" + ReUtil.makeToken(len) + "]");
                }
            }
        }
    }

    /** 
     * <pre>
     * ランダム文字列生成[ RandomStringUtils.random() ]. 
     * 
     * 文字数を設定します。
     * JUnit実行後にコンソールに結果が出力されます.
     * </pre>
     */
    @Test
    public void randNumStr() throws Exception {
        // 文字数.
        int[] targets = {
                 10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,4
                ,8
                };

        // 処理. ----------------------------------------- //
        System.out.println("【 ランダム文字列生成[  RandomStringUtils.random() ] 】");
        if (targets.length > 0) {
            for (int len : targets) {
                if (len > 0) {
                    //System.out.println("RandomStringUtils.random()(" + len + ")->[" + RandomStringUtils.random(len, CommonModel.HF_ALP_NUM) + "]");
                }
            }
        }
    }

    /** 
     * <pre>
     * ランダム数字生成[ ReUtil.randNum() ]. 
     * 
     * 文字数を設定します。
     * JUnit実行後にコンソールに結果が出力されます.
     * </pre>
     */
    @Test
    public void randNum() throws Exception {
        // 文字数.
        int[] targets = {
                 10
                ,12
                ,10
                ,10
                ,10
                ,10
                ,10
                ,10
                ,4
                ,8
                };

        // 処理. ----------------------------------------- //
        System.out.println("【 ランダム数字生成[ ReUtil.randNum() ] 】");
        if (targets.length > 0) {
            for (int len : targets) {
                if (len > 0) {
                    System.out.println("randNum(" + len + ")->[" + ReUtil.randNum(len) + "]");
                }
            }
        }
    }

    /** 
     * <pre>
     * パスワード生成. 
     * 
     * 指定したパスワードとソルトでDBに格納するパスワードを設定します。
     * JUnit実行後にコンソールに結果が出力されます.
     * </pre>
     */
    @Test
    public void makePassword() throws Exception {
        // 対象の文字列と組織ID.
        String[][] targets = {
                {"test1234", "AFGJ89YU3G"}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               ,{"", ""}
               };


        // 処理. ----------------------------------------- //
        System.out.println("【 パスワード生成 】");
        if (targets.length > 0) {
            for (String[] strs : targets) {
                if (!ReUtil.isEmpty(strs) && !ReUtil.isEmpty(strs[0]) && !ReUtil.isEmpty(strs[1])) {
                    System.out.println("(" + strs[0] + "." + strs[1] + ")->[" + ReUtil.toHashValueSha2(strs[0] + strs[1])+ "]");
                }
            }
        }
    }

}