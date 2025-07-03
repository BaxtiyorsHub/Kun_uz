package dasturlash.uz.util;

public class PageCheckingUtil {
    public static int checkPage(int page) {
        if (page <= 0) return 0;
        return page-1;
    }
}
