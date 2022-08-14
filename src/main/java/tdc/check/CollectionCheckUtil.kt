package tdc.check

/**
 * コレクションのためのチェックユーティリティ.
 * <pre>
 * ・状態を持たないこと（定数はよい）
 * ・boolean is～、void validate～ throws Exceptionメソッドのみ定義可能
</pre> *
 *
 * @author Takagi Satoshi
 */
object CollectionCheckUtil {
    private val BLANK: Any = ""
    @JvmStatic
    fun isEmpty(list: List<String?>?): Boolean {
        return list?.stream()?.allMatch { s: String? -> s == null || s == BLANK } ?: true
    }

    @JvmStatic
    fun isNotEmpty(values: List<String?>?): Boolean {
        return !isEmpty(values)
    }
}