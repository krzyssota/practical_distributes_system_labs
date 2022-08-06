package com.rtbhouse.nosqllab.resource;

import static com.rtbhouse.nosqllab.avro2json.AvroJsonHttpMessageConverter.AVRO_JSON;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtbhouse.nosqllab.dao.UserTagDao;
import com.rtbhouse.nosqllab.UserTags;
import com.rtbhouse.nosqllab.UserTag;


@RestController
@RequestMapping("/endpoint")
public class NosqlResource {

    public NosqlResource(UserTagDao userTagDao) {
        this.userTagDao = userTagDao;
    }

    @Autowired
    private UserTagDao userTagDao;

    @GetMapping(produces = AVRO_JSON, path = "/{cookie}")
    public ResponseEntity<UserTags> get(@PathVariable("cookie") String cookie) {
        return ResponseEntity.of(Optional.ofNullable(userTagDao.get(cookie)));
    }

    @PutMapping(produces = AVRO_JSON, consumes = AVRO_JSON)
    public ResponseEntity<UserTags> put(@RequestBody UserTags tags) {
        userTagDao.put(tags);
        return ResponseEntity.ok(tags);
    }

/*    @PutMapping(consumes = AVRO_JSON)
    public ResponseEntity<String> append(@RequestBody UserTag tag) {
        userTagDao.append(tag);
        return ResponseEntity.ok("appended");
    }*/

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(userTagDao.count());
    }

    @GetMapping(path = "/allKeys")
    public ResponseEntity<List<String>> getAllKeys() {

        return ResponseEntity.ok(userTagDao.getAllKeys());
    }

    @GetMapping(produces = AVRO_JSON, path = "/delete/{cookie}")
    public ResponseEntity<String> delete(@PathVariable("cookie") String cookie) {
        userTagDao.delete(cookie);
        return ResponseEntity.ok("deleted " + cookie);
    }




}
