package com.csii.common.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.csii.common.vo.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ResponseUtil {

    /**
     * 使用response输出JSON
     *
     * @param response
     */
    public static <T> void out(HttpServletResponse response, Result<T> result) {

        ServletOutputStream out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("输出JSON出错", e);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 使用response输出JSON
     *
     * @param response
     * @param result
     * @param <T>
     */
    public static <T> void write(HttpServletResponse response, Result<T> result) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONUtil.toJsonStr(new JSONObject(result, false)));
            out.flush();
        } catch (Exception e) {
            log.error("response write error", e);
        } finally {
            IoUtil.close(out);
        }
    }

    public static <T> void write(HttpServletResponse response, Object obj) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONUtil.toJsonStr(new JSONObject(obj, false)));
            out.flush();
        } catch (Exception e) {
            log.error("response write error", e);
        } finally {
            IoUtil.close(out);
        }
    }
}
