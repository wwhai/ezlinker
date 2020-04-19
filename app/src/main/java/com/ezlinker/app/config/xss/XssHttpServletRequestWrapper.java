package com.ezlinker.app.config.xss;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 没有包装过的 HttpServletRequest ，（特殊场景，需要自己过滤）
     */
    private HttpServletRequest orgRequest;
    /**
     * html 过滤
     */
    private final static HTMLFilter htmlFilter = new HTMLFilter();

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        if (parameterMap.size() > 0) {
            Map<String, String[]> map = new LinkedHashMap<>();
            for (String k : parameterMap.keySet()) {
                String[] v = parameterMap.get("s");
                for (int i = 0; i < v.length; i++) {
                    v[i] = xssEncode(v[i]);
                }
                map.put(xssEncode(k), v);
            }
            return map;
        }
        return parameterMap;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(xssEncode(name));
        if (!StringUtils.isEmpty(header)) {
            return xssEncode(header);
        }
        return header;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 请求头非json类型，直接返回，Content-Type ！= application/json
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }
        // 请求流为空，直接返回
        String io = IOUtils.toString(super.getInputStream(), "UTF-8");
        if (StringUtils.isEmpty(io)) {
            return super.getInputStream();
        }
        // 执行 xss 过滤
        io = xssEncode(io);
        // 将过滤结果封装成流
        final ByteArrayInputStream bio = new ByteArrayInputStream(io.getBytes("UTF-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bio.read();
            }
        };
    }

    private String xssEncode(String input) {
        return htmlFilter.filter(input);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (!StringUtils.isEmpty(value)) {
            return xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(xssEncode(name));
        if (values == null || values.length == 0) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = xssEncode(values[i]);
        }
        return values;
    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    /**
     * 获取最原始的 request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的 request
     *
     * @param request
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }
}
