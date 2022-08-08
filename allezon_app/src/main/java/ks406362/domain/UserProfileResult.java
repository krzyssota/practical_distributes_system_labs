package ks406362.domain;

import ks406362.UserTags;

import java.util.List;

public record UserProfileResult(String cookie, List<UserTagEvent> views, List<UserTagEvent> buys) {

    public UserProfileResult(UserTags tags) {
        this(tags.getCookie().toString(),
                tags.getViews().stream().map(t -> new UserTagEvent(tags.getCookie().toString(), Action.VIEW, t)).toList(),
                tags.getBuys().stream().map(t -> new UserTagEvent(tags.getCookie().toString(), Action.BUY, t)).toList());
    }
}