package ks406362.domain;

import ks406362.generated.device_enum;

public enum Device {
    MOBILE, PC, TV;

    public device_enum getGeneratedDeviceEnumFromDevice() {
        return switch (this) {
            case MOBILE -> device_enum.MOBILE;
            case PC -> device_enum.PC;
            case TV -> device_enum.TV;
        };
    }

    public static Device getDeviceFromGeneratedDeviceEnum(device_enum d) {
        if (d == device_enum.PC) return PC;
        if (d == device_enum.MOBILE) return MOBILE;
        return TV;
    }
}


