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

import com.rtbhouse.nosqllab.Message;
import com.rtbhouse.nosqllab.dao.MessageDao;

@RestController
@RequestMapping("/endpoint")
public class NosqlResource {

    @Autowired
    private MessageDao messageDao;

    @GetMapping(produces = AVRO_JSON, path = "/{id}")
    public ResponseEntity<Message> get(@PathVariable("id") Integer id) {
        return ResponseEntity.of(Optional.ofNullable(messageDao.get(id)));
    }

    @PutMapping(produces = AVRO_JSON, consumes = AVRO_JSON)
    public ResponseEntity<Message> put(@RequestBody Message message) {
        messageDao.put(message);
        return ResponseEntity.ok(message);
    }

}
