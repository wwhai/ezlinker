package com.ezlinker.app.wsintegeration;

import lombok.Data;

/**
 * @author wangwenhai
 * @date 2020/8/23
 * File description: Websocket Session
 */
@Data
class RequestSession {
    // 请求ID
    private String requestId;
    // 消息来自，比如on-click
    private String caller;
    // 回调位置，HTML端监听这个值，然后去执行具体的操作
    /**
     * 案例1：按钮按下，发送一个指令到后端，后端执行完了，结果回馈到前端
     * caller:Button --> onClick
     * callback: -> print 自定义的K，意思就是如果返回这个K，则证明是caller调用的。
     * 出发点是这里去留一个函数名，然后前端去动态执行这个函数。
     */
    private String callback;
}
