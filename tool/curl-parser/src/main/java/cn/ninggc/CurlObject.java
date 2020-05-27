package cn.ninggc;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
class CurlObject {
    private String url;
    private String type;
    private String header;
    private JsonObject body;
}
