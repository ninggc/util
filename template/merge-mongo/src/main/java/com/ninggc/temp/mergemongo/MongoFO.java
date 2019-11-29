package com.ninggc.temp.mergemongo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MongoFO {
    private String db1;
    private String db2;
    private String db3;
}
