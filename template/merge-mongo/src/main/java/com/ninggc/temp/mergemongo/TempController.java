package com.ninggc.temp.mergemongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temp")
public class TempController {
    static String mongodbUri = "mongodb://ninggc.cn:27017/clinical-research-renji-fix";
    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TempController.class);

    @GetMapping("/count/{coll}")
    public long count(@PathVariable String coll) {
        return mongoTemplate.count(new Query(), coll);
    }

    @GetMapping("/findAll/{coll}")
    public List<HashMap> findAll(@PathVariable String coll) {
        return mongoTemplate.findAll(HashMap.class, coll);
    }

    @GetMapping("/findAllId/{coll}")
    public List<OnlyId> findAllId(@PathVariable String coll) {
        return mongoTemplate.findAll(OnlyId.class, coll);
    }


    @GetMapping("/merge")
    public HashMap<String, Object> merge(@ModelAttribute MongoFO mongoFO) {
        List<HashMap> all = mongoTemplate.findAll(HashMap.class, "menu");
        try {
            mongoTemplate.createCollection("menu1");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        int successCount = 0;
        int duplicateCount = 0;
        int otherCount = 0;

        for (Map hashMap : all) {
            try {
                mongoTemplate.insert(hashMap, "menu1");
                ++successCount;
            } catch (DuplicateKeyException e) {
                ++duplicateCount;
                LOGGER.error(e.getMessage());
            } catch (Exception e) {
                ++otherCount;
                LOGGER.error(e.getMessage());
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("success count", successCount);
        map.put("duplicate error count", duplicateCount);
        map.put("other error count", otherCount);
        return map;
    }
}
