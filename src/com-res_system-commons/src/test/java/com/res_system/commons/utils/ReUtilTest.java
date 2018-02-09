package com.res_system.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.junit.Test;

import com.res_system.commons.util.ReUtil;

public class ReUtilTest {

    private static final String hira = "ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞ"
                + "ただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽ"
                + "まみむめもゃやゅゆょよらりるれろゎわゐゑをん";
    private static final String hira_plus = "う゛かけ";
    private static final String kana = "ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾ"
                + "タダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポ"
                + "マミムメモャヤュユョヨラリルレロヮワヰヱヲン";
    private static final String kana_plus = "ヴヵヶ";

    private static final String han_num = "0123456789+-*/%^=<>:.,() ";
    private static final String zen_num = "０１２３４５６７８９＋－＊／％＾＝＜＞：．，（）　";
    private static final String han_alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
    private static final String zen_alpha = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ－＿";

    //---------------------------------------------- class method [public] text.
    @Test
    public void isEmptyTest() {
        System.out.println("◆◆◆ checkEmptyTest() ◆◆◆");
        String value = null;
        assertEquals(true, ReUtil.isEmpty(value));
        assertEquals(true, ReUtil.isEmpty(""));
        assertEquals(false, ReUtil.isEmpty("a"));
        Long id = null;
        assertEquals(true, ReUtil.isEmpty(id));
        id = -1L;
        assertEquals(true, ReUtil.isEmpty(id));
        id = 0L;
        assertEquals(true, ReUtil.isEmpty(id));
        id = 1L;
        assertEquals(false, ReUtil.isEmpty(id));
    }


    //---------------------------------------------- class method [public] base64.
    @Test
    public void fromToBase64Test() throws UnsupportedEncodingException {
        System.out.println("◆◆◆ fromToBase64Test() ◆◆◆");

        // - to      ----//
        assertEquals("", ReUtil.toBase64(null));
        assertEquals("", ReUtil.toBase64(""));
        assertEquals("YWJjZGVmZw==", ReUtil.toBase64("abcdefg"));
        assertEquals("44GC44GE44GG44GI44GK", ReUtil.toBase64("あいうえお"));
        assertEquals("44GC44GE44GG44GI44GKYeODvOOBleOBly0hIiMkJSYnKCl+Jw==", ReUtil.toBase64("あいうえおaーさし-!\"#$%&'()~'"));
        assertEquals("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY/jgZHjgZMNCuOBleOBl+OBmQ==", ReUtil.toBase64("あいうえお\r\nかきくけこ\r\nさしす"));

        assertEquals("", ReUtil.toBase64URL(null));
        assertEquals("", ReUtil.toBase64URL(""));
        assertEquals("YWJjZGVmZw%3D%3D", ReUtil.toBase64URL("abcdefg"));
        assertEquals("44GC44GE44GG44GI44GK", ReUtil.toBase64URL("あいうえお"));
        assertEquals("44GC44GE44GG44GI44GKYeODvOOBleOBly0hIiMkJSYnKCl%2BJw%3D%3D", ReUtil.toBase64URL("あいうえおaーさし-!\"#$%&'()~'"));
        assertEquals("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY%2FjgZHjgZMNCuOBleOBl%2BOBmQ%3D%3D", ReUtil.toBase64URL("あいうえお\r\nかきくけこ\r\nさしす"));


        // - from    ----//
        assertEquals("", ReUtil.fromBase64(null));
        assertEquals("", ReUtil.fromBase64(""));
        assertEquals("abcdefg", ReUtil.fromBase64("YWJjZGVmZw=="));
        assertEquals("あいうえお", ReUtil.fromBase64("44GC44GE44GG44GI44GK"));
        assertEquals("あいうえおaーさし-!\"#$%&'()~'", ReUtil.fromBase64("44GC44GE44GG44GI44GKYeODvOOBleOBly0hIiMkJSYnKCl+Jw=="));
        assertEquals("あいうえお\r\nかきくけこ\r\nさしす", ReUtil.fromBase64("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY/jgZHjgZMNCuOBleOBl+OBmQ=="));
        try {
            ReUtil.fromBase64("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY/jgZHjgZMNCuOBleOBl+OBmQ==sdfsdf");
            fail("変換できないパターン");
        } catch (RuntimeException e) {
            System.out.println("変換できないパターン:OK -- " + e.getMessage() + " --");
        }

        assertEquals("", ReUtil.fromBase64URL(null));
        assertEquals("", ReUtil.fromBase64URL(""));
        assertEquals("abcdefg", ReUtil.fromBase64URL("YWJjZGVmZw%3D%3D"));
        assertEquals("あいうえお", ReUtil.fromBase64URL("44GC44GE44GG44GI44GK"));
        assertEquals("あいうえおaーさし-!\"#$%&'()~'", ReUtil.fromBase64URL("44GC44GE44GG44GI44GKYeODvOOBleOBly0hIiMkJSYnKCl%2BJw%3D%3D"));
        assertEquals("あいうえお\r\nかきくけこ\r\nさしす", ReUtil.fromBase64URL("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY%2FjgZHjgZMNCuOBleOBl%2BOBmQ%3D%3D"));
        try {
            ReUtil.fromBase64URL("44GC44GE44GG44GI44GKDQrjgYvjgY3jgY/jgZHjgZMNCuOBleOBl+OBmQ==");
            fail("変換できないパターン");
        } catch (RuntimeException e) {
            System.out.println("変換できないパターン:OK -- " + e.getMessage() + " --");
        }

    }



    @Test
    public void formatDecimalTest() {
        System.out.println("◆◆◆ formatDecimalTest() ◆◆◆");
        assertEquals(null, ReUtil.formatDecimal(null, null));
        assertEquals("", ReUtil.formatDecimal("", ""));
        assertEquals("1,234,567,890", ReUtil.formatDecimal("1234567890", "#,###"));
        assertEquals("1,234,567,890", ReUtil.formatDecimal("1234567890", "#,###.##"));
        assertEquals("1,234,567,890.12", ReUtil.formatDecimal("1234567890.12", "#,###.##"));
        assertEquals("1,234,567,890.00", ReUtil.formatDecimal("1234567890", "#,###.00"));
        assertEquals("1,234,567,890.12", ReUtil.formatDecimal("1234567890.12", "#,###.00"));
    }

    @Test
    public void setCheckDigitModulus11Test() {
        System.out.println("◆◆◆ setCheckDigitModulus11Test() ◆◆◆");
        assertEquals(null, ReUtil.setCheckDigitModulus11(null));
        assertEquals("", ReUtil.setCheckDigitModulus11(""));
        assertEquals("abc", ReUtil.setCheckDigitModulus11("abc"));
        assertEquals("1237", ReUtil.setCheckDigitModulus11("123"));
        assertEquals("1234567897", ReUtil.setCheckDigitModulus11("123456789"));
        assertEquals("123456789012345678900", ReUtil.setCheckDigitModulus11("12345678901234567890"));
        assertEquals("330", ReUtil.setCheckDigitModulus11("33"));
        assertEquals("340", ReUtil.setCheckDigitModulus11("34"));
        assertEquals("359", ReUtil.setCheckDigitModulus11("35"));
        assertEquals("368", ReUtil.setCheckDigitModulus11("36"));
    }

    @Test
    public void checkCheckDigitModulus11Test() {
        System.out.println("◆◆◆ checkCheckDigitModulus11Test() ◆◆◆");
        assertEquals(false, ReUtil.checkCheckDigitModulus11(null));
        assertEquals(false, ReUtil.checkCheckDigitModulus11(""));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("abc"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("1237"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("1234567897"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("123456789012345678900"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("330"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("340"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("359"));
        assertEquals(true, ReUtil.checkCheckDigitModulus11("368"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("1234"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("1234567890"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("123456789012345678901"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("333"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("345"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("356"));
        assertEquals(false, ReUtil.checkCheckDigitModulus11("367"));
    }

    @Test
    public void strLenTest() {
        System.out.println("◆◆◆ strLenTest() ◆◆◆");
        assertEquals("null", 0, ReUtil.strLen(null));
        assertEquals("通常文字数チェック", 2, "𠀋".length());
        assertEquals("サロゲートペア文字数チェック", 1, ReUtil.strLen("𠀋"));
        assertEquals("通常文字数チェック", 2, "𪗱".length());
        assertEquals("サロゲートペア文字数チェック", 1, ReUtil.strLen("𪗱"));
    }

    @Test
    public void byteLenTest() {
        System.out.println("◆◆◆ byteLenTest() ◆◆◆");
        assertEquals("null", 0, ReUtil.byteLen(null));
        assertEquals("文字数", 1, "あ".length());
        assertEquals("バイト数(デフォルト)", 3, ReUtil.byteLen("あ"));
        assertEquals("バイト数(Shift-JIS)", 2, ReUtil.byteLen("あ", "Shift-JIS"));
        assertEquals("バイト数(UTF-8)", 3, ReUtil.byteLen("あ", "UTF-8"));
        assertEquals("不正な文字コード", 0, ReUtil.byteLen("あ", "sss"));
        assertEquals("サロゲートペア文字数チェック(length)", 2, "𠀋".length());
        assertEquals("サロゲートペア文字数チェック(byteLen)", 4, ReUtil.byteLen("𠀋"));
    }

    @Test
    public void toEmptyNullTest() {
        System.out.println("◆◆◆ toEmptyNullTest() ◆◆◆");
        assertEquals("toEmpty(あ)", "あ", ReUtil.toEmpty("あ"));
        assertEquals("toEmpty('')", "", ReUtil.toEmpty(""));
        assertEquals("toEmpty(null)", "", ReUtil.toEmpty(null));
        assertEquals("toNull(あ)", "あ", ReUtil.toNull("あ"));
        assertEquals("toNull('')", null, ReUtil.toNull(""));
        assertEquals("toNull(null)", null, ReUtil.toNull(null));
    }

    @Test
    public void toNumTest() {
        System.out.println("◆◆◆ toEmptyNullTest() ◆◆◆");

        assertEquals("toInt(1)", 1, ReUtil.toInt("1"));
        assertEquals("toInt(あ)", 0, ReUtil.toInt("あ"));
        assertEquals("toInt('')", 0, ReUtil.toInt(""));
        assertEquals("toInt(null)", 0, ReUtil.toInt(null));

        assertEquals("toInt(1, 9)", 1, ReUtil.toInt("1", 9));
        assertEquals("toInt(あ, 9)", 9, ReUtil.toInt("あ", 9));
        assertEquals("toInt('', 9)", 9, ReUtil.toInt("", 9));
        assertEquals("toInt(null, 9)", 9, ReUtil.toInt(null, 9));

        assertEquals("toInteger(1)", new Integer(1), ReUtil.toInteger("1"));
        assertEquals("toInteger(あ)", null, ReUtil.toInteger("あ"));
        assertEquals("toInteger('')", null, ReUtil.toInteger(""));
        assertEquals("toInteger(null)", null, ReUtil.toInteger(null));

        assertEquals("toInteger(1, 9)", new Integer(1), ReUtil.toInteger("1", 9));
        assertEquals("toInteger(あ, 9)", new Integer(9), ReUtil.toInteger("あ", 9));
        assertEquals("toInteger('', 9)", new Integer(9), ReUtil.toInteger("", 9));
        assertEquals("toInteger(null, 9)", new Integer(9), ReUtil.toInteger(null, 9));

        assertEquals("toLong(1)", new Long(1L), ReUtil.toLong("1"));
        assertEquals("toLong(あ)", null, ReUtil.toLong("あ"));
        assertEquals("toLong('')", null, ReUtil.toLong(""));
        assertEquals("toLong(null)", null, ReUtil.toLong(null));

        assertEquals("toLong(1, 9L)", new Long(1L), ReUtil.toLong("1", 9L));
        assertEquals("toLong(あ, 9L)", new Long(9L), ReUtil.toLong("あ", 9L));
        assertEquals("toLong('', 9L)", new Long(9L), ReUtil.toLong("", 9L));
        assertEquals("toLong(null, 9L)", new Long(9L), ReUtil.toLong(null, 9L));

        assertEquals("toBigDecimal(1)", new BigDecimal(1), ReUtil.toBigDecimal("1"));
        assertEquals("toBigDecimal(あ)", null, ReUtil.toBigDecimal("あ"));
        assertEquals("toBigDecimal('')", null, ReUtil.toBigDecimal(""));
        assertEquals("toBigDecimal(null)", null, ReUtil.toBigDecimal(null));

        assertEquals("toBigDecimal(1, new BigDecimal(9))", new BigDecimal(1), ReUtil.toBigDecimal("1", new BigDecimal(9)));
        assertEquals("toBigDecimal(あ, new BigDecimal(9))", new BigDecimal(9), ReUtil.toBigDecimal("あ", new BigDecimal(9)));
        assertEquals("toBigDecimal('', new BigDecimal(9))", new BigDecimal(9), ReUtil.toBigDecimal("", new BigDecimal(9)));
        assertEquals("toBigDecimal(null, new BigDecimal(9))", new BigDecimal(9), ReUtil.toBigDecimal(null, new BigDecimal(9)));
    }


    @Test
    public void makeTokenTest() {
        System.out.println("◆◆◆ makeToken() ◆◆◆");
        assertEquals("空", 0, ReUtil.makeToken(0).length());
        assertEquals("2", 2, ReUtil.makeToken(2).length());    System.out.println("2:" + ReUtil.makeToken(2));
        assertEquals("3", 2, ReUtil.makeToken(3).length());    System.out.println("3:" + ReUtil.makeToken(3));
        assertEquals("32", 32, ReUtil.makeToken(32).length()); System.out.println("32:" + ReUtil.makeToken(32));
    }

    @Test
    public void toHashValueTest() {
        System.out.println("◆◆◆ toHashValue() ◆◆◆");
        // NG.
        assertEquals("null", null, ReUtil.toHashValue(null, null));
        assertEquals("空", "", ReUtil.toHashValue("", ""));
        assertEquals("空", "123", ReUtil.toHashValue("", "123"));
        assertEquals("null", null, ReUtil.toHashValueSha2(null));
        assertEquals("空", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", ReUtil.toHashValueSha2(""));
        // OK.
        assertEquals("test", "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", ReUtil.toHashValueSha2("test"));    System.out.println("test:" + ReUtil.toHashValueSha2("test"));
    }

    @Test
    public void toHiraToKanaTest() {
        System.out.println("◆◆◆ toHiraToKana() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toHiraToKana(null));
        assertEquals("空", "", ReUtil.toHiraToKana(""));
        // OK.
        assertEquals("hira→kana", kana, ReUtil.toHiraToKana(hira));
        assertEquals("kana→kana", kana, ReUtil.toHiraToKana(kana));
    }

    @Test
    public void toKanaToHiraTest() {
        System.out.println("◆◆◆ toKanaToHira() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toKanaToHira(null));
        assertEquals("空", "", ReUtil.toKanaToHira(""));
        // OK.
        assertEquals("kana→hira", hira, ReUtil.toKanaToHira(kana));
        assertEquals("hira→hira", hira, ReUtil.toKanaToHira(hira));
        assertEquals("kana_plus→hira_plus", hira_plus, ReUtil.toKanaToHira(kana_plus));
    }

    @Test
    public void toHanAlphaTest() {
        System.out.println("◆◆◆ toHanAlpha() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toHanAlpha(null));
        assertEquals("空", "", ReUtil.toHanAlpha(""));
        // OK.
        assertEquals("zen_alpha→han_alpha", han_alpha, ReUtil.toHanAlpha(zen_alpha));
        assertEquals("han_alpha→han_alpha", han_alpha, ReUtil.toHanAlpha(han_alpha));
    }

    @Test
    public void toZenAlphaTest() {
        System.out.println("◆◆◆ toZenAlpha() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toZenAlpha(null));
        assertEquals("空", "", ReUtil.toZenAlpha(""));
        // OK.
        assertEquals("han_alpha→zen_alpha", zen_alpha, ReUtil.toZenAlpha(han_alpha));
        assertEquals("zen_alpha→zen_alpha", zen_alpha, ReUtil.toZenAlpha(zen_alpha));
    }

    @Test
    public void toHanNumTest() {
        System.out.println("◆◆◆ toHanNum() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toHanNum(null));
        assertEquals("空", "", ReUtil.toHanNum(""));
        // OK.
        assertEquals("zen_num→han_num", han_num, ReUtil.toHanNum(zen_num));
        assertEquals("han_num→han_num", han_num, ReUtil.toHanNum(han_num));
    }

    @Test
    public void toZenNumTest() {
        System.out.println("◆◆◆ toZenNum() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toZenNum(null));
        assertEquals("空", "", ReUtil.toZenNum(""));
        // OK.
        assertEquals("han_num→zen_num", zen_num, ReUtil.toZenNum(han_num));
        assertEquals("zen_num→zen_num", zen_num, ReUtil.toZenNum(zen_num));
    }

    @Test
    public void toHanAlphaNumTest() {
        System.out.println("◆◆◆ toHanAlphaNum() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toHanAlphaNum(null));
        assertEquals("空", "", ReUtil.toHanAlphaNum(""));
        // OK.
        assertEquals("zen_num+zen_alpha→han_num+han_alpha", han_num+han_alpha, ReUtil.toHanAlphaNum(zen_num+zen_alpha));
        assertEquals("han_num+han_alpha→han_num+han_alpha", han_num+han_alpha, ReUtil.toHanAlphaNum(han_num+han_alpha));
    }

    @Test
    public void toZenAlphaNumTest() {
        System.out.println("◆◆◆ toZenAlphaNum() ◆◆◆");

        // NG.
        assertEquals("null", null, ReUtil.toZenAlphaNum(null));
        assertEquals("空", "", ReUtil.toZenAlphaNum(""));
        // OK.
        assertEquals("han_num+han_alpha→zen_num+zen_alpha", zen_num+zen_alpha, ReUtil.toZenAlphaNum(han_num+han_alpha));
        assertEquals("zen_num+zen_alpha→zen_num+zen_alpha", zen_num+zen_alpha, ReUtil.toZenAlphaNum(zen_num+zen_alpha));
    }

}
