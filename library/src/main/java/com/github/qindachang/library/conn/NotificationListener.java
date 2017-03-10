package com.github.qindachang.library.conn;

import com.github.qindachang.library.exception.BleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface NotificationListener {
    void onNotify();

    void error(BleException e);
}
