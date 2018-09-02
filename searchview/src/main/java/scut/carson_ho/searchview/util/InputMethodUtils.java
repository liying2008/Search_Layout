package scut.carson_ho.searchview.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/2 21:06
 * Description: 输入法相关工具类
 * Remarks:
 * =======================================================
 */
public class InputMethodUtils {

    /**
     * 打开输入法面板
     *
     * @param context
     * @param view
     */
    public static void openKeyboard(@Nullable Context context, View view) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }

    /**
     * 关闭输入法面板
     *
     * @param context
     * @param view
     */
    public static void closeKeyboard(@Nullable Context context, View view) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }
    }
}
