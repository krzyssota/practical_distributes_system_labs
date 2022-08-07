package ks406362.domain;

import ks406362.device_enum;

import java.util.Objects;

public enum Action {
    VIEW, BUY;

    public static Action fromString(String a) {
        if (Objects.equals(a, "BUY")) return BUY;
        return VIEW;
    }
}
