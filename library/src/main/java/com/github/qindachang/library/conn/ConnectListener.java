package com.github.qindachang.library.conn;

/**
 * Created by qindachang on 2017/3/9.
 */

public interface ConnectListener {
    void connecting();

    void connected();

    void disconnect();

    void onServiceDiscover();

    void error();
}
