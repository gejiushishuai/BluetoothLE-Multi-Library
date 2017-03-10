package com.github.qindachang.library.conn;

import com.github.qindachang.library.exception.WriteBleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface WriteCharacteristicListener {
    void onWrite();
    void error(WriteBleException e);
}
