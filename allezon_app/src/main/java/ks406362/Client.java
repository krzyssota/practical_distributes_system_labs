package ks406362;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ks406362.domain.Action;
import ks406362.domain.Aggregate;
import ks406362.domain.AggregatesQueryResult;
import ks406362.domain.UserProfileResult;
import ks406362.domain.UserTagEvent;

@RestController
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private HashMap<String, List<UserTagEvent>> usersViews = new HashMap<>();
    private HashMap<String, List<UserTagEvent>> usersBuys = new HashMap<>();

    @PostMapping("/user_tags")
    public ResponseEntity<Void> addUserTag(@RequestBody(required = false) UserTagEvent userTag) {
        HashMap<String, List<UserTagEvent>> userEvents = userTag.action() == Action.BUY ? usersBuys : usersViews;
        String cookie = userTag.cookie();
        List<UserTagEvent> events = userEvents.get(cookie);
        if (events != null) {
            events.add(userTag);
            userEvents.replace(cookie, events);
        } else {
            List<UserTagEvent> newEvents = new LinkedList<>();
            userEvents.put(cookie, newEvents);
        }

        // debug
        log.info("added", userTag);

        List<UserTagEvent> buys = usersBuys.get(cookie);
        List<UserTagEvent> views = usersViews.get(cookie);

        log.info("debug");
        log.info(String.valueOf(buys));
        log.info(String.valueOf(views));

        //

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/user_profiles/{cookie}")
    public ResponseEntity<UserProfileResult> getUserProfile(@PathVariable("cookie") String cookie,
            @RequestParam("time_range") String timeRangeStr,
            @RequestParam(defaultValue = "200") int limit,
            @RequestBody(required = false) UserProfileResult expectedResult) {
        List<UserTagEvent> buys = usersBuys.get(cookie);
        if (buys == null) {
            buys = new LinkedList<>();
        }
        List<UserTagEvent> views = usersViews.get(cookie);
        if (views == null) {
            views = new LinkedList<>();
        }
        UserProfileResult result = new UserProfileResult(cookie, views, buys);
        log.info("retrieved result", String.valueOf(result));
        log.info("expected result", String.valueOf(expectedResult));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/aggregates")
    public ResponseEntity<AggregatesQueryResult> getAggregates(@RequestParam("time_range") String timeRangeStr,
            @RequestParam("action") Action action,
            @RequestParam("aggregates") List<Aggregate> aggregates,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "brand_id", required = false) String brandId,
            @RequestParam(value = "category_id", required = false) String categoryId,
            @RequestBody(required = false) AggregatesQueryResult expectedResult) {

        return ResponseEntity.ok(expectedResult);
    }
}
