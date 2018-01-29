package com.res_system.commons.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.res_system.commons.util.ReChecker;

public class ReCheckerTest {

    //---------------------------------------------- class method [public] 基本.
    private static final String H_NUM = "0123456789";
    private static final String H_ALP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String H_KIG  = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final String H_KNA = "ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｧｨｩｪｫｬｭｮｯｰﾞﾟ";
    private static final String H_KNAK = "｡｢｣､･";
    private static final String Z_NUM = "０１２３４５６７８９";
    private static final String Z_ALP = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
    private static final String Z_KIG = "！”＃＄％＆’（）＊＋，－．／：；＜＝＞？＠［￥］＾＿‘｛｜｝￣";
    private static final String Z_KNA = "ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶー゛゜";
    private static final String Z_KNAK = "。「」、・";
    private static final String Z_HKNA = "ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろゎわゐゑをん";

    @Test
    public void checkEmptyTest() {
        System.out.println("◆◆◆ checkEmptyTest() ◆◆◆");
        assertEquals(true, ReChecker.checkEmpty(null));
        assertEquals(true, ReChecker.checkEmpty(""));
        assertEquals(false, ReChecker.checkEmpty("a"));
    }

    @Test
    public void checkNotEmptyTest() {
        System.out.println("◆◆◆ checkNotEmptyTest() ◆◆◆");
        assertEquals(false, ReChecker.checkNotEmpty(null));
        assertEquals(false, ReChecker.checkNotEmpty(""));
        assertEquals(true, ReChecker.checkNotEmpty("a"));
    }

    @Test
    public void checkByteSizeTest() {
        System.out.println("◆◆◆ checkByteSizeTest() ◆◆◆");
        assertEquals(true, ReChecker.checkByteSize(null, 10));
        assertEquals(true, ReChecker.checkByteSize("", 10));
        assertEquals(true, ReChecker.checkByteSize("a", 10));
        assertEquals(true, ReChecker.checkByteSize("abcdefghij", 10));
        assertEquals(false, ReChecker.checkByteSize("abcdefghijk", 10));
        assertEquals(true, ReChecker.checkByteSize("あいうえお", 15));
        assertEquals(false, ReChecker.checkByteSize("あいうえお", 14));

        assertEquals(true, ReChecker.checkByteSize(null, 0, 10));
        assertEquals(true, ReChecker.checkByteSize(null, 1, 10));
        assertEquals(true, ReChecker.checkByteSize("", 0, 10));
        assertEquals(true, ReChecker.checkByteSize("", 1, 10));
        assertEquals(true, ReChecker.checkByteSize("a", 1, 10));
        assertEquals(false, ReChecker.checkByteSize("a", 4, 10));
        assertEquals(true, ReChecker.checkByteSize("abcde", 5, 10));
        assertEquals(false, ReChecker.checkByteSize("abcd", 5, 10));
        assertEquals(true, ReChecker.checkByteSize("abcdefghij", 5, 10));
        assertEquals(false, ReChecker.checkByteSize("abcdefghijk", 5, 10));
        assertEquals(true, ReChecker.checkByteSize("あいうえお", 8, 15));
        assertEquals(false, ReChecker.checkByteSize("あいう", 10, 15));
        assertEquals(false, ReChecker.checkByteSize("あいうえお", 10, 14));
    }

    @Test
    public void checkLengthTest() {
        System.out.println("◆◆◆ checkLengthTest() ◆◆◆");
        assertEquals(true, ReChecker.checkLength(null, 10));
        assertEquals(true, ReChecker.checkLength("", 10));
        assertEquals(true, ReChecker.checkLength("a", 10));
        assertEquals(true, ReChecker.checkLength("abcdefghij", 10));
        assertEquals(false, ReChecker.checkLength("abcdefghijk", 10));
        assertEquals(true, ReChecker.checkLength("あいうえお", 5));
        assertEquals(false, ReChecker.checkLength("あいうえお?", 5));

        assertEquals(true, ReChecker.checkLength(null, 0, 10));
        assertEquals(true, ReChecker.checkLength(null, 1, 10));
        assertEquals(true, ReChecker.checkLength("", 0, 10));
        assertEquals(true, ReChecker.checkLength("", 1, 10));
        assertEquals(true, ReChecker.checkLength("a", 1, 10));
        assertEquals(false, ReChecker.checkLength("a", 4, 10));
        assertEquals(true, ReChecker.checkLength("abcde", 5, 10));
        assertEquals(false, ReChecker.checkLength("abcd", 5, 10));
        assertEquals(true, ReChecker.checkLength("abcdefghij", 5, 10));
        assertEquals(false, ReChecker.checkLength("abcdefghijk", 5, 10));
        assertEquals(true, ReChecker.checkLength("あいうえお", 4, 5));
        assertEquals(false, ReChecker.checkLength("あいう", 4, 5));
        assertEquals(false, ReChecker.checkLength("あいうえお?", 4, 5));
    }

    @Test
    public void checkNumericTest() {
        System.out.println("◆◆◆ checkNumericTest() ◆◆◆");
        String checkStriong = "";
        BigDecimal checkNum = null;

        checkStriong = null;
        assertEquals(true, ReChecker.checkNumeric(checkStriong));

        checkStriong = "";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));

        checkStriong = "a";
        assertEquals(false, ReChecker.checkNumeric(checkStriong));

        checkStriong = "0";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = "-0";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = "123456789";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = "-123456789";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = "+123456789";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = "123456.789";
        assertEquals(true, ReChecker.checkNumeric(checkStriong));
        checkNum = new BigDecimal(checkStriong);
        System.out.println(checkNum);

        checkStriong = ".789";
        assertEquals(false, ReChecker.checkNumeric(checkStriong));

        checkStriong = "123456.";
        assertEquals(false, ReChecker.checkNumeric(checkStriong));

    }

    @Test
    public void checkNumberTest() {
        System.out.println("◆◆◆ checkNumberTest() ◆◆◆");
        assertEquals(true, ReChecker.checkNumber(null));
        assertEquals(true, ReChecker.checkNumber(""));
        assertEquals(true, ReChecker.checkNumber(H_NUM));
        assertEquals(false, ReChecker.checkNumber(H_ALP));
        assertEquals(false, ReChecker.checkNumber(H_NUM + H_ALP));
        assertEquals(false, ReChecker.checkNumber(H_KIG));
        assertEquals(false, ReChecker.checkNumber(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkNumber(H_KNA));
        assertEquals(false, ReChecker.checkNumber(H_KNAK));
        assertEquals(false, ReChecker.checkNumber(Z_NUM));
        assertEquals(false, ReChecker.checkNumber(Z_ALP));
        assertEquals(false, ReChecker.checkNumber(Z_NUM + Z_ALP));
        assertEquals(false, ReChecker.checkNumber(Z_KIG));
        assertEquals(false, ReChecker.checkNumber(Z_KNA));
        assertEquals(false, ReChecker.checkNumber(Z_KNAK));
        assertEquals(false, ReChecker.checkNumber(Z_HKNA));
        assertEquals(false, ReChecker.checkNumber("a"));
        assertEquals(true, ReChecker.checkNumber("0"));
        assertEquals(false, ReChecker.checkNumber("@"));

        assertEquals(true, ReChecker.checkNumber("123456789"));
        assertEquals(false, ReChecker.checkNumber("123456.789"));
        assertEquals(true, ReChecker.checkNumber("123456.789", "\\."));
        assertEquals(false, ReChecker.checkNumber("123456-789"));
        assertEquals(true, ReChecker.checkNumber("123456-789", "-"));
    }

    @Test
    public void checkAlphaTest() {
        System.out.println("◆◆◆ checkAlphaTest() ◆◆◆");
        assertEquals(true, ReChecker.checkAlpha(null));
        assertEquals(true, ReChecker.checkAlpha(""));
        assertEquals(false, ReChecker.checkAlpha(H_NUM));
        assertEquals(true, ReChecker.checkAlpha(H_ALP));
        assertEquals(false, ReChecker.checkAlpha(H_NUM + H_ALP));
        assertEquals(false, ReChecker.checkAlpha(H_KIG));
        assertEquals(false, ReChecker.checkAlpha(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkAlpha(H_KNA));
        assertEquals(false, ReChecker.checkAlpha(H_KNAK));
        assertEquals(false, ReChecker.checkAlpha(Z_NUM));
        assertEquals(false, ReChecker.checkAlpha(Z_ALP));
        assertEquals(false, ReChecker.checkAlpha(Z_NUM + Z_ALP));
        assertEquals(false, ReChecker.checkAlpha(Z_KIG));
        assertEquals(false, ReChecker.checkAlpha(Z_KNA));
        assertEquals(false, ReChecker.checkAlpha(Z_KNAK));
        assertEquals(false, ReChecker.checkAlpha(Z_HKNA));
        assertEquals(true, ReChecker.checkAlpha("a"));
        assertEquals(false, ReChecker.checkAlpha("0"));
        assertEquals(false, ReChecker.checkAlpha("@"));

        assertEquals(false, ReChecker.checkAlpha("test01@test.com"));
        assertEquals(true, ReChecker.checkAlpha("test01@test.com", "01@\\."));
    }

    @Test
    public void checkAlphaNumberTest() {
        System.out.println("◆◆◆ checkAlphaNumber() ◆◆◆");
        assertEquals(true, ReChecker.checkAlphaNumber(null));
        assertEquals(true, ReChecker.checkAlphaNumber(""));
        assertEquals(true, ReChecker.checkAlphaNumber(H_NUM));
        assertEquals(true, ReChecker.checkAlphaNumber(H_ALP));
        assertEquals(true, ReChecker.checkAlphaNumber(H_NUM + H_ALP));
        assertEquals(false, ReChecker.checkAlphaNumber(H_KIG));
        assertEquals(false, ReChecker.checkAlphaNumber(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkAlphaNumber(H_KNA));
        assertEquals(false, ReChecker.checkAlphaNumber(H_KNAK));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_NUM));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_ALP));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_NUM + Z_ALP));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_KIG));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_KNA));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_KNAK));
        assertEquals(false, ReChecker.checkAlphaNumber(Z_HKNA));
        assertEquals(true, ReChecker.checkAlphaNumber("a"));
        assertEquals(true, ReChecker.checkAlphaNumber("0"));
        assertEquals(false, ReChecker.checkAlphaNumber("@"));

        assertEquals(false, ReChecker.checkAlphaNumber("test01@test.com"));
        assertEquals(true, ReChecker.checkAlphaNumber("test01@test.com", "@\\."));
    }

    @Test
    public void checkAsciiTest() {
        System.out.println("◆◆◆ checkAsciiTest() ◆◆◆");
        assertEquals(true, ReChecker.checkAscii(null));
        assertEquals(true, ReChecker.checkAscii(""));
        assertEquals(true, ReChecker.checkAscii(H_NUM));
        assertEquals(true, ReChecker.checkAscii(H_ALP));
        assertEquals(true, ReChecker.checkAscii(H_NUM + H_ALP));
        assertEquals(true, ReChecker.checkAscii(H_KIG));
        assertEquals(true, ReChecker.checkAscii(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkAscii(H_KNA));
        assertEquals(false, ReChecker.checkAscii(H_KNAK));
        assertEquals(false, ReChecker.checkAscii(Z_NUM));
        assertEquals(false, ReChecker.checkAscii(Z_ALP));
        assertEquals(false, ReChecker.checkAscii(Z_NUM + Z_ALP));
        assertEquals(false, ReChecker.checkAscii(Z_KIG));
        assertEquals(false, ReChecker.checkAscii(Z_KNA));
        assertEquals(false, ReChecker.checkAscii(Z_KNAK));
        assertEquals(false, ReChecker.checkAscii(Z_HKNA));
        assertEquals(true, ReChecker.checkAscii("a"));
        assertEquals(true, ReChecker.checkAscii("0"));
        assertEquals(true, ReChecker.checkAscii("@"));
    }

    @Test
    public void checkEmTest() {
        System.out.println("◆◆◆ checkEmTest() ◆◆◆");
        assertEquals(true, ReChecker.checkEm(null));
        assertEquals(true, ReChecker.checkEm(""));
        assertEquals(false, ReChecker.checkEm(H_NUM));
        assertEquals(false, ReChecker.checkEm(H_ALP));
        assertEquals(false, ReChecker.checkEm(H_NUM + H_ALP));
        assertEquals(false, ReChecker.checkEm(H_KIG));
        assertEquals(false, ReChecker.checkEm(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkEm(H_KNA));
        assertEquals(false, ReChecker.checkEm(H_KNAK));
        assertEquals(true, ReChecker.checkEm(Z_NUM));
        assertEquals(true, ReChecker.checkEm(Z_ALP));
        assertEquals(true, ReChecker.checkEm(Z_NUM + Z_ALP));
        assertEquals(true, ReChecker.checkEm(Z_KIG));
        assertEquals(true, ReChecker.checkEm(Z_KNA));
        assertEquals(true, ReChecker.checkEm(Z_KNAK));
        assertEquals(true, ReChecker.checkEm(Z_HKNA));
        assertEquals(false, ReChecker.checkEm("a"));
        assertEquals(false, ReChecker.checkEm("0"));
        assertEquals(false, ReChecker.checkEm("@"));
        assertEquals(true, ReChecker.checkEm("漢字も大丈夫か確認する！"));
    }

    @Test
    public void checkKanaTest() {
        System.out.println("◆◆◆ checkKanaTest() ◆◆◆");
        assertEquals(true, ReChecker.checkKana(null));
        assertEquals(true, ReChecker.checkKana(""));
        assertEquals(false, ReChecker.checkKana(H_NUM));
        assertEquals(false, ReChecker.checkKana(H_ALP));
        assertEquals(false, ReChecker.checkKana(H_NUM + H_ALP));
        assertEquals(false, ReChecker.checkKana(H_KIG));
        assertEquals(false, ReChecker.checkKana(H_NUM + H_ALP + H_KIG));
        assertEquals(false, ReChecker.checkKana(H_KNA));
        assertEquals(false, ReChecker.checkKana(H_KNAK));
        assertEquals(false, ReChecker.checkKana(Z_NUM));
        assertEquals(false, ReChecker.checkKana(Z_ALP));
        assertEquals(false, ReChecker.checkKana(Z_NUM + Z_ALP));
        assertEquals(false, ReChecker.checkKana(Z_KIG));
        assertEquals(true, ReChecker.checkKana(Z_KNA));
        assertEquals(false, ReChecker.checkKana(Z_KNAK));
        assertEquals(false, ReChecker.checkKana(Z_HKNA));
        assertEquals(false, ReChecker.checkKana("a"));
        assertEquals(false, ReChecker.checkKana("0"));
        assertEquals(false, ReChecker.checkKana("@"));
        assertEquals(false, ReChecker.checkKana("カナ０６"));
        assertEquals(true, ReChecker.checkKana("カナ０６", Z_NUM));
    }

    @Test
    public void checkNumericSizeTest() {
        System.out.println("◆◆◆ checkNumericSizeTest() ◆◆◆");
        assertEquals(true, ReChecker.checkNumericSize(null, null));
        assertEquals(true, ReChecker.checkNumericSize(null, null, null));
        assertEquals(false, ReChecker.checkNumericSize("123", null));
        assertEquals(true, ReChecker.checkNumericSize("", "123"));
        assertEquals(true, ReChecker.checkNumericSize("a", "123"));
        assertEquals(true, ReChecker.checkNumericSize("１２３", "123"));
        assertEquals(false, ReChecker.checkNumericSize("123", null, null));
        assertEquals(false, ReChecker.checkNumericSize("123", "10", null));
        assertEquals(false, ReChecker.checkNumericSize("123", null, "123"));
        assertEquals(true, ReChecker.checkNumericSize("", "10", "123"));
        assertEquals(true, ReChecker.checkNumericSize("a", "10", "123"));
        assertEquals(true, ReChecker.checkNumericSize("１２３", "10", "123"));

        assertEquals(true, ReChecker.checkNumericSize("1", "12"));
        assertEquals(true, ReChecker.checkNumericSize("122", "123"));
        assertEquals(true, ReChecker.checkNumericSize("123", "123"));
        assertEquals(false, ReChecker.checkNumericSize("123", "122"));
        assertEquals(true, ReChecker.checkNumericSize("123", "123", "123"));
        assertEquals(false, ReChecker.checkNumericSize("122", "123", "123"));
        assertEquals(false, ReChecker.checkNumericSize("124", "123", "123"));

        assertEquals(true, ReChecker.checkNumericSize("1.1", "1.1"));
        assertEquals(false, ReChecker.checkNumericSize("1.1", "1.0"));
        assertEquals(true, ReChecker.checkNumericSize("123456789012.123", "123456789012.123"));
        assertEquals(false, ReChecker.checkNumericSize("123456789012.124", "123456789012.123"));
        assertEquals(true, ReChecker.checkNumericSize("123456789012123456789012.123456789012", "123456789012123456789012.123456789012", "123456789012123456789012.123456789012"));
        assertEquals(false, ReChecker.checkNumericSize("123456789012123456789012.1234567890123", "123456789012123456789012.123456789012", "123456789012123456789012.123456789012"));

        assertEquals(true, ReChecker.checkNumericSize("-15", "-20", "20"));
        assertEquals(false, ReChecker.checkNumericSize("-21", "-20", "20"));
    }

    @Test
    public void checkDateFormatTest() {
        System.out.println("◆◆◆ checkDateFormatTest() ◆◆◆");
        assertEquals(true,  ReChecker.checkDateFormat(null, null));
        assertEquals(true,  ReChecker.checkDateFormat("", ""));
        assertEquals(true,  ReChecker.checkDateFormat(null));
        assertEquals(true,  ReChecker.checkDateFormat(""));
        assertEquals(false, ReChecker.checkDateFormat("20160708"));
        assertEquals(true,  ReChecker.checkDateFormat("2016/7/8"));
        assertEquals(true,  ReChecker.checkDateFormat("2016/07/08"));
        assertEquals(true,  ReChecker.checkDateFormat("2016-7-8"));
        assertEquals(true,  ReChecker.checkDateFormat("2016-07-08"));
        assertEquals(false, ReChecker.checkDateFormat("2016-13-08"));
        assertEquals(false, ReChecker.checkDateFormat("2016-06-31"));
        assertEquals(false, ReChecker.checkDateFormat("2016-07-32"));
        assertEquals(true,  ReChecker.checkDateFormat("2016-02-29"));
        assertEquals(false, ReChecker.checkDateFormat("2015-02-29"));
        assertEquals(false, ReChecker.checkDateFormat("2016/07/08", "asd"));
        assertEquals(false, ReChecker.checkDateFormat("2016-07-08", "234"));
        assertEquals(false, ReChecker.checkDateFormat("2016/07/08", "yyyy-MM-dd"));
        assertEquals(false, ReChecker.checkDateFormat("2016-07-08", "yyyy/MM/dd"));
        assertEquals(true,  ReChecker.checkDateFormat("2016/07/08", "yyyy/MM/dd"));
        assertEquals(true,  ReChecker.checkDateFormat("2016-07-08", "yyyy-MM-dd"));

        assertEquals(true,  ReChecker.checkDateTimeFormat(null));
        assertEquals(true,  ReChecker.checkDateTimeFormat(""));
        assertEquals(false, ReChecker.checkDateTimeFormat("20160708"));
        assertEquals(false, ReChecker.checkDateTimeFormat("2016/07/08"));
        assertEquals(false, ReChecker.checkDateTimeFormat("2016-07-08"));
        assertEquals(true,  ReChecker.checkDateTimeFormat("2016/7/8 4:3:6"));
        assertEquals(true,  ReChecker.checkDateTimeFormat("2016-7-8 4:3:6"));
        assertEquals(true,  ReChecker.checkDateTimeFormat("2016/07/08 14:30:56"));
        assertEquals(true,  ReChecker.checkDateTimeFormat("2016-07-08 14:30:56"));
        assertEquals(false, ReChecker.checkDateTimeFormat("14:30:56"));
        assertEquals(false, ReChecker.checkDateTimeFormat("14:30:56"));

        assertEquals(true,  ReChecker.checkTimeFormat(null));
        assertEquals(true,  ReChecker.checkTimeFormat(""));
        assertEquals(false, ReChecker.checkTimeFormat("20160708"));
        assertEquals(false, ReChecker.checkTimeFormat("2016/07/08"));
        assertEquals(false, ReChecker.checkTimeFormat("2016-07-08"));
        assertEquals(false, ReChecker.checkTimeFormat("2016/07/08 14:30:56"));
        assertEquals(false, ReChecker.checkTimeFormat("2016-07-08 14:30:56"));
        assertEquals(true,  ReChecker.checkTimeFormat("4:3:6"));
        assertEquals(true,  ReChecker.checkTimeFormat("4:3:6"));
        assertEquals(true,  ReChecker.checkTimeFormat("14:30:56"));
        assertEquals(true,  ReChecker.checkTimeFormat("14:30:56"));
    }

    @Test
    public void checkNotZeroTest() {
        System.out.println("◆◆◆ checkNotZeroTest() ◆◆◆");
        assertEquals(true, ReChecker.checkNotZero(null));
        assertEquals(true, ReChecker.checkNotZero(""));
        assertEquals(true, ReChecker.checkNotZero("a"));
        assertEquals(false, ReChecker.checkNotZero("0"));
        assertEquals(false, ReChecker.checkNotZero("0.0"));
        assertEquals(true, ReChecker.checkNotZero("1"));
        assertEquals(true, ReChecker.checkNotZero("0.1"));
        assertEquals(true, ReChecker.checkNotZero("-1"));
        assertEquals(true, ReChecker.checkNotZero("-0.1"));
        assertEquals(false, ReChecker.checkNotZero("00000000"));
        assertEquals(true, ReChecker.checkNotZero("01001001"));
    }

    @Test
    public void checkNotMinusTest() {
        System.out.println("◆◆◆ checkNotMinusTest() ◆◆◆");
        assertEquals(true, ReChecker.checkNotMinus(null));
        assertEquals(true, ReChecker.checkNotMinus(""));
        assertEquals(true, ReChecker.checkNotMinus("a"));
        assertEquals(true, ReChecker.checkNotMinus("0"));
        assertEquals(true, ReChecker.checkNotMinus("0.0"));
        assertEquals(true, ReChecker.checkNotMinus("1"));
        assertEquals(true, ReChecker.checkNotMinus("0.1"));
        assertEquals(false, ReChecker.checkNotMinus("-1"));
        assertEquals(false, ReChecker.checkNotMinus("-0.1"));
        assertEquals(true, ReChecker.checkNotMinus("00000000"));
        assertEquals(true, ReChecker.checkNotMinus("01001001"));
    }

    @Test
    public void checkDecimalSizeTest() {
        System.out.println("◆◆◆ checkDecimalPointTest() ◆◆◆");
        assertEquals(true, ReChecker.checkDecimalSize(null, 1, 0));
        assertEquals(true, ReChecker.checkDecimalSize("", 1, 0));
        assertEquals(true, ReChecker.checkDecimalSize("a", 1, 0));
        assertEquals(true, ReChecker.checkDecimalSize("0", 1, 0));
        assertEquals(true, ReChecker.checkDecimalSize("1", 1, 0));
        assertEquals(false, ReChecker.checkDecimalSize("1.0", 1, 0));
        assertEquals(true, ReChecker.checkDecimalSize("1.0", 1, 1));
        assertEquals(false, ReChecker.checkDecimalSize("1.00000", 1, 4));
        assertEquals(true, ReChecker.checkDecimalSize("1.00000", 1, 5));
        assertEquals(true, ReChecker.checkDecimalSize("1.00000", 1, 6));
        assertEquals(false, ReChecker.checkDecimalSize("1.0", 1, -6));
    }

}
