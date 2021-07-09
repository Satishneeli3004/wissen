package mavenproject.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;

/**
 * 统一返回类
 *
 * @author jinghong
 */
public class R<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final int SUCCESS = 0;
    private static final int FAIL = 1;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "success");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> ok(IPage iPage) {
        return restResult(iPage.getRecords(), SUCCESS, "success").put("count", iPage.getTotal());
    }

    public static <T> R<T> failed() {
        return restResult(null, FAIL, "操作失败");
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.put("code", code);
        apiResult.put("data", data);
        apiResult.put("msg", msg);
        return apiResult;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}