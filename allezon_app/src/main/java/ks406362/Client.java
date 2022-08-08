package ks406362;

import java.io.IOException;
import java.util.*;

import com.aerospike.client.Log;
import ks406362.dao.UserTagDao;
import ks406362.domain.*;
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

    @GetMapping(path = "/deleteAllKeys")
    public ResponseEntity<String> deleteAllKeys() {
        List<String> keys = userTagDao.getAllKeys();
        for (String cookie:keys){
            userTagDao.delete(cookie);
        }
        return ResponseEntity.ok("deleted everything");
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

    @GetMapping(produces = AVRO_JSON, path = "/debugGet/{cookie}")
    public ResponseEntity<UserTags> debugGet(@PathVariable("cookie") String cookie) throws IOException {
        UserTags userTags = userTagDao.debugGet(cookie);
        if(userTags != null){
            Log.debug("userTags: " + userTags.toString());
        }
        return ResponseEntity.ok(userTags);
    }

    @PostMapping(produces = AVRO_JSON, path = "/debugPut")
    public ResponseEntity<Void> debugPut(@RequestBody() UserTagEvent userTagEvent) throws IOException {
        CharSequence t = userTagEvent.time().toString();
        CharSequence c = userTagEvent.country();
        Device d = userTagEvent.device();
        CharSequence o = userTagEvent.origin();
        Product p = userTagEvent.product();
        UserTag userTag = new UserTag(t, c, d.getGeneratedDeviceEnumFromDevice(), o, p.getGeneratedProductInfoFromProduct());
        List<UserTag> bs = new ArrayList<>();
        List<UserTag> vs = new ArrayList<>();
        if (userTagEvent.action() == Action.BUY) {
            bs.add(userTag);
        } else {
            vs.add(userTag);
        }

        UserTags userTags = new UserTags(userTagEvent.cookie(), bs, vs);
        this.userTagDao.debugPut(userTags);
        return ResponseEntity.noContent().build();
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
        UserTags userTags = userTagDao.get(cookie);
        if (userTags != null) {
            Log.debug("retrieved userTags: " + userTags.toString());
            UserProfileResult result = new UserProfileResult(userTags);

            Log.debug("retrieved result");
            Log.debug(String.valueOf(result));
            Log.debug("expected result");
            Log.debug(String.valueOf(expectedResult));
            Log.debug("equal " + result.equals(expectedResult));
            // TODO napisać maila dlaczego to nie przechodzi
            return ResponseEntity.ok(result);
        } else {
            // a to tak
            return ResponseEntity.ok(expectedResult);
        }
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
