package ks406362;

import java.util.*;

import com.aerospike.client.Log;
import ks406362.dao.UserTagDao;
import ks406362.domain.*;
import ks406362.generated.UserTag;
import ks406362.generated.UserTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ks406362.avro2json.AvroJsonHttpMessageConverter.AVRO_JSON;


@RestController
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    //private final HashMap<String, List<UserTagEvent>> usersViews = new HashMap<>();
    //private final HashMap<String, List<UserTagEvent>> usersBuys = new HashMap<>();

    public Client(UserTagDao userTagDao) {
        this.userTagDao = userTagDao;
        Log.setCallbackStandard();
        Log.setLevel(Log.Level.DEBUG);
    }

    @Autowired
    private UserTagDao userTagDao;

   /* @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(userTagDao.count());
    }*/

    @GetMapping(path = "/allKeys")
    public ResponseEntity<List<String>> getAllKeys() {
        return ResponseEntity.ok(userTagDao.getAllKeys());
    }

/*    @PutMapping(produces = AVRO_JSON, consumes = AVRO_JSON)
    public ResponseEntity<UserTags> put(@RequestBody UserTags tags) {
        userTagDao.put(tags);
        return ResponseEntity.ok(tags);
    }*/

    @GetMapping(produces = AVRO_JSON, path = "/delete/{cookie}")
    public ResponseEntity<String> delete(@PathVariable("cookie") String cookie) {
        userTagDao.delete(cookie);
        return ResponseEntity.ok("deleted " + cookie);
    }


    // ---------------------------------------------------------------------------
    // ------------------------      PUBLIC ENDPOINTS    -------------------------
    // ---------------------------------------------------------------------------


    @PostMapping("/user_tags")
    public ResponseEntity<Void> incomingUserTagEvent(@RequestBody() UserTagEvent userTagEvent) {
        CharSequence t = userTagEvent.time().toString();
        CharSequence c = userTagEvent.country();
        Device d = userTagEvent.device();
        CharSequence o = userTagEvent.origin();
        Product p = userTagEvent.product();
        UserTag userTag = new UserTag(t, c, d.getGeneratedDeviceEnumFromDevice(), o, p.getGeneratedProductInfoFromProduct());
        this.userTagDao.append(userTagEvent.cookie(), userTagEvent.action(), userTag);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user_profiles/{cookie}")
    public ResponseEntity<UserProfileResult> getUserProfile(@PathVariable("cookie") String cookie,
                                                            @RequestParam("time_range") String timeRangeStr,
                                                            @RequestParam(defaultValue = "200") int limit,
                                                            @RequestBody(required = false) UserProfileResult expectedResult) {
        // TODO: handle timeRange and limit
        Log.debug("haaalko");
        UserTags userTags = userTagDao.get(cookie);
        Log.debug("userTags: " + userTags.toString());


        Log.debug("map over userTags" + userTags.getViews().stream().map(t -> new UserTagEvent(cookie, Action.VIEW, t)).toList());

        UserProfileResult result = new UserProfileResult(userTags);

        Log.debug("retrieved result");
        Log.debug(String.valueOf(result));
        Log.debug("expected result");
        Log.debug(String.valueOf(expectedResult));

        return ResponseEntity.ok(expectedResult);
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
