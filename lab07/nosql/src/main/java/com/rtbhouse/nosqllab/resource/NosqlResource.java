package com.rtbhouse.nosqllab.resource;

import static com.rtbhouse.nosqllab.avro2json.AvroJsonHttpMessageConverter.AVRO_JSON;

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
import com.rtbhouse.nosqllab.UserTag;

@RestController
@RequestMapping("/endpoint")
public class NosqlResource {

    @Autowired
    private UserTagDao userTagDao;

    @GetMapping(produces = AVRO_JSON, path = "/{cookie}")
    public ResponseEntity<UserTag> get(@PathVariable("cookie") String cookie) {
        return ResponseEntity.of(Optional.ofNullable(userTagDao.get(cookie)));
    }

    @PutMapping(produces = AVRO_JSON, consumes = AVRO_JSON)
    public ResponseEntity<UserTag> put(@RequestBody UserTag tag) {
        userTagDao.put(tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(userTagDao.count());
    }




}
