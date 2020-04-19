package com.ezlinker.app.modules.wxappexchange;

import com.ezlinker.app.common.exchange.R;

/**
 * EZLINKER验证签名回调
 */
public interface WxAppExchangeCallback {
    /**
     * 验签
     * @param token
     * @return
     */
    R auth(String token);
}
