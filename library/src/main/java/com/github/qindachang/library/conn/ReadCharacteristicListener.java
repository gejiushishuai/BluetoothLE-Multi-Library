package com.github.qindachang.library.conn;

import com.github.qindachang.library.exception.ReadBleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface ReadCharacteristicListener {
    void onRead();

    void error(ReadBleException e);
}
