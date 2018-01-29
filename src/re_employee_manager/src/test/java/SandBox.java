

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * CommonModel テストクラス.
 *
 * @author res.
 *
 */
public class SandBox {
    @Test
    public void SandBoxTest() throws Exception {
        String testinfo = "[ SandBox ]";
        System.out.println(testinfo + "** Start ****************");
        {
            String msg = "[ makeWhereKeywords(通常) ]";
            String[] expected = new String[]{"あ", "い", "うえお"};
            String[] actual = makeWhereKeywords("あ い　うえお");
            assertEquals(msg, true, actual != null);
            assertEquals(msg, expected.length, actual.length);
            for(int i = 0, imax = expected.length; i < imax; i++) {
                assertEquals(msg, expected[i], actual[i]);
            }
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = "[ makeWhereKeywords(単発) ]";
            String[] expected = new String[]{"あいうえお"};
            String[] actual = makeWhereKeywords("あいうえお");
            assertEquals(msg, true, actual != null);
            assertEquals(msg, expected.length, actual.length);
            for(int i = 0, imax = expected.length; i < imax; i++) {
                assertEquals(msg, expected[i], actual[i]);
            }
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = "[ makeWhereKeywords(null) ]";
            String[] expected = new String[]{};
            String[] actual = makeWhereKeywords(null);
            assertEquals(msg, true, actual != null);
            assertEquals(msg, expected.length, actual.length);
            for(int i = 0, imax = expected.length; i < imax; i++) {
                assertEquals(msg, expected[i], actual[i]);
            }
            System.out.println(msg + " -OK- ");
        }
        {
            String msg = "[ makeWhereKeywords(空) ]";
            String[] expected = new String[]{};
            String[] actual = makeWhereKeywords("");
            assertEquals(msg, true, actual != null);
            assertEquals(msg, expected.length, actual.length);
            for(int i = 0, imax = expected.length; i < imax; i++) {
                assertEquals(msg, expected[i], actual[i]);
            }
            System.out.println(msg + " -OK- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    private String[] makeWhereKeywords(String keyword) {
        String[] keywords;
        if (keyword != null && keyword.length() > 0) {
            keywords = keyword.split(" |　");
        } else {
            keywords = new String[]{};
        }
        return keywords;
    }

}
