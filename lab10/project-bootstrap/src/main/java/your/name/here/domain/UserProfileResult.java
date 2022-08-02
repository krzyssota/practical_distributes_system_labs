package your.name.here.domain;

import java.util.List;

public final class UserProfileResult {
    private final String cookie;
    private final List<UserTagEvent> views;
    private final List<UserTagEvent> buys;

    public UserProfileResult(String cookie, List<UserTagEvent> views, List<UserTagEvent> buys) {
        this.cookie = cookie;
        this.views = views;
        this.buys = buys;
    }

    public String cookie() {
        return cookie;
    }

    public List<UserTagEvent> views() {
        return views;
    }

    public List<UserTagEvent> buys() {
        return buys;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserProfileResult) obj;
        return java.util.Objects.equals(this.cookie, that.cookie) &&
                java.util.Objects.equals(this.views, that.views) &&
                java.util.Objects.equals(this.buys, that.buys);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(cookie, views, buys);
    }

    @java.lang.Override
    public String toString() {
        return "UserProfileResult[" +
                "cookie=" + cookie + ", " +
                "views=" + views + ", " +
                "buys=" + buys + ']';
    }

}