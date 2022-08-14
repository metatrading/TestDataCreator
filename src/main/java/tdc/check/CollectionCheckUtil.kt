package tdc.check;

import java.util.List;

/**
 * コレクションのためのチェックユーティリティ.
 * <pre>
 * ・状態を持たないこと（定数はよい）
 * ・boolean is～、void validate～ throws Exceptionメソッドのみ定義可能
 * </pre>
 *
 * @author Takagi Satoshi
 */
public final class CollectionCheckUtil {

    private CollectionCheckUtil() {
    }

    private static final Object BLANK = "";

    public static boolean isEmpty(List<String> list) {
        if (list == null)
            return true;
        return list.stream().allMatch(s -> s == null || s.equals(BLANK) ? true : false);
    }

    public static boolean isNotEmpty(List<String> values) {
        return !isEmpty(values);
    }
}
