package com.github.qindachang.library.server;

import java.util.List;
import java.util.UUID;

/**
 * Created by David Qin on 2017/4/19.
 */

public final class ServiceSettings {

    private ServiceSettings(UUID serviceUuid, int serviceType, List<ServiceProfile> serviceProfiles) {
        this.serviceUuid = serviceUuid;
        this.serviceType = serviceType;
        mServiceProfiles = serviceProfiles;
    }

    private UUID serviceUuid;
    private int serviceType;
    private List<ServiceProfile> mServiceProfiles;

    public UUID getServiceUuid() {
        return serviceUuid;
    }

    public int getServiceType() {
        return serviceType;
    }

    public List<ServiceProfile> getServiceProfiles() {
        return mServiceProfiles;
    }

    public static final class Builder {
        private UUID serviceUuid;
        private int serviceType;
        private List<ServiceProfile> mServiceProfiles;

        public Builder setServiceUuid(UUID serviceUuid) {
            this.serviceUuid = serviceUuid;
            return this;
        }

        public Builder setServiceType(int serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder addServiceProfiles(List<ServiceProfile> serviceProfiles) {
            mServiceProfiles = serviceProfiles;
            return this;
        }

        public ServiceSettings build() {
            return new ServiceSettings(serviceUuid, serviceType, mServiceProfiles);
        }
    }
}
