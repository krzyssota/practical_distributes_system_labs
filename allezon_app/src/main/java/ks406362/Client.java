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

import com.google.common.collect.EvictingQueue;

@RestController
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private final HashMap<String, Queue<UserTagEvent>> usersViews = new HashMap<>();
    private final HashMap<String, Queue<UserTagEvent>> usersBuys = new HashMap<>();

    Queue<UserTagEvent> fifo = EvictingQueue.create(200);

    @PostMapping("/user_tags")
    public ResponseEntity<Void> addUserTag(@RequestBody(required = false) UserTagEvent userTag) {
        //HashMap<String, List<UserTagEvent>> userEvents = userTag.action() == Action.BUY ? usersBuys : usersViews;
        HashMap<String, Queue<UserTagEvent>> userEvents = userTag.action() == Action.BUY ? usersBuys : usersViews;

        String cookie = userTag.cookie();
        Queue<UserTagEvent> events = userEvents.get(cookie);
        if (events != null) {
            log.debug("adding one more event");
            log.debug(String.valueOf(userTag));
            events.add(userTag);
            userEvents.replace(cookie, events);
        } else {
            log.debug("first event of user");
            log.debug(String.valueOf(userTag));
            //List<UserTagEvent> newEvents = new LinkedList<>(Collections.singleton(userTag));
            Queue<UserTagEvent> newEvents = EvictingQueue.create(200);
            newEvents.add(userTag);
            userEvents.put(cookie, newEvents);
        }

        // debug
        Queue<UserTagEvent> buys = usersBuys.get(cookie);
        Queue<UserTagEvent> views = usersViews.get(cookie);

        if (buys != null) {
            log.debug("buys");
            log.debug(String.valueOf(buys));
        }
        if (views != null) {
            log.debug("views");
            log.debug(String.valueOf(views));
        }
        //

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/user_profiles/{cookie}")
    public ResponseEntity<UserProfileResult> getUserProfile(@PathVariable("cookie") String cookie,
            @RequestParam("time_range") String timeRangeStr,
            @RequestParam(defaultValue = "200") int limit,
            @RequestBody(required = false) UserProfileResult expectedResult) {

        Queue<UserTagEvent> qBuys = usersBuys.get(cookie);
        List<UserTagEvent> buys = qBuys == null ? new LinkedList<>() : qBuys.stream().toList();

        Queue<UserTagEvent> qViews = usersViews.get(cookie);
        List<UserTagEvent> views = qViews == null ? new LinkedList<>() : qViews.stream().toList();

        UserProfileResult result = new UserProfileResult(cookie, views, buys);

        log.debug("retrieved result");
        log.debug(String.valueOf(result));
        log.debug("expected result");
        log.debug(String.valueOf(expectedResult));

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
