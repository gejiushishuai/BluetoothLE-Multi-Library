/*
 * Copyright (c) 2017, Qin Dachang
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.qindachang.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

public class BluetoothLe {
    private static HashSet<Integer> numberSet = new HashSet<>();
    private static HashSet<Map<Object, BleManagerImpl>> objectSet = new LinkedHashSet<>();

    public static BleManagerImpl getDefault() {
        return getDefault(0);
    }

    public static BleManagerImpl getDefault(int deviceNumber) {
        if (!numberSet.contains(deviceNumber)) {
            BleManagerImpl bleManager = new BleManager();
            Map<Object, BleManagerImpl> map = new HashMap<>();
            map.put(deviceNumber, bleManager);
            objectSet.add(map);
            numberSet.add(deviceNumber);
            return bleManager;
        } else {
            BleManagerImpl b = null;
            for (Map<Object, BleManagerImpl> map : objectSet) {
                if (map.containsKey(deviceNumber)) {
                    b = map.get(deviceNumber);
                }
            }
            return b;
        }
    }
}
