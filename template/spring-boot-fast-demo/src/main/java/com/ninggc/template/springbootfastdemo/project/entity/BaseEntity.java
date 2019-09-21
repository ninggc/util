package com.ninggc.template.springbootfastdemo.project.entity;

import com.ninggc.util.morphia.domain.IMorphiaPO;
import dev.morphia.annotations.Id;
import lombok.Data;
import org.bson.Document;
import org.bson.types.ObjectId;

@Data
public class BaseEntity implements IMorphiaPO {

    @Id
    private ObjectId id;

    @Override
    public Document toDocument() {
        return null;
    }
}
