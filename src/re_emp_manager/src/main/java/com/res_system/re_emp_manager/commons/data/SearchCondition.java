package com.res_system.re_emp_manager.commons.data;

import java.io.Serializable;

import com.res_system.commons.mvc.model.form.Param;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.kind.CheckKbn;

/**
 * 検索条件 データクラス.
 * @author res.
 */
@SuppressWarnings("serial")
public class SearchCondition implements Serializable {

    //---------------------------------------------- properies [private].
    /** 検索キーワード. */
    @Param
    private String keyword;
    /** 全検索チェック. */
    @Param
    private String is_all;
    /** ページ番号. */
    @Param
    private String page;
    /** 総ページ数. */
    @Param
    private String total_page;
    /** 総件数. */
    @Param
    private String total_size;
    /** 選択ID. */
    @Param
    private String selected_id;
    /** 選択Sub-ID. */
    @Param
    private String selected_sub_id;
    /** 並び順. */
    @Param
    private String sort;
 
    /** 検索キーワード(配列). */
    String[] keywords;


    //-- setter / getter. --//
    /** 検索キーワード を取得します. */
    public String getKeyword() { return keyword; }
    /** 検索キーワード を設定します. */
    public void setKeyword(String keyword) { this.keyword = keyword; }
    /** 全検索チェック を取得します. */
    public String getIs_all() { return is_all; }
    /** 全検索チェック を設定します. */
    public void setIs_all(String is_all) { this.is_all = is_all; }
    /** ページ番号 を取得します. */
    public String getPage() { return page; }
    /** ページ番号 を取得します. */
    public void setPage(String page) { this.page = page; }
    /** 総ページ数 を取得します. */
    public String getTotal_page() { return total_page; }
    /** 総ページ数 を取得します. */
    public void setTotal_page(String total_page) { this.total_page = total_page; }
    /** 総件数 を取得します. */
    public String getTotal_size() { return total_size; }
    /** 総件数 を取得します. */
    public void setTotal_size(String total_size) { this.total_size = total_size; }
    /** 選択ID を取得します. */
    public String getSelected_id() { return selected_id; }
    /** 選択ID を設定します. */
    public void setSelected_id(String selected_id) { this.selected_id = selected_id; }
    /** 選択Sub-ID を設定します. */
    public String getSelected_sub_id() { return selected_sub_id; }
    /** 選択Sub-ID を取得します. */
    public void setSelected_sub_id(String selected_sub_id) { this.selected_sub_id = selected_sub_id; }
    /** 並び順 を設定します. */
    public String getSort() { return sort; }
    /** 並び順 を取得します. */
    public void setSort(String sort) { this.sort = sort; }

    /** 検索キーワード(配列) を取得します. */
    public String[] getKeywords() { return keywords; }
    /** 検索キーワード(配列) を設定します. */
    public void setKeywords(String[] keywords) { this.keywords = keywords; }


    //---------------------------------------------- [public].
    /** ページ番号 を取得します. */
    public int getPageInt() { 
        int page = ReUtil.toInt(getPage(), 1);
        setPage(String.valueOf(page));
        return page; 
    }

    /** 全検索チェック を取得します. */
    public boolean isAll() { 
        return CheckKbn.ON.equals(getIs_all()); 
    }

    /** リセットします. */
    public void reset() {
        setKeyword("");
        setIs_all(CheckKbn.OFF.getValue());
        setPage("1");
    }

}
