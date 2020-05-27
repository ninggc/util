package cn.ninggc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CurlParser {
    public static CurlObject parse(String curlCommand) {
        CurlObject curlObject = new CurlObject();

        curlCommand = curlCommand.replace("\n", "")
                .replace("\t", "")
                .replace("'", "");
        while (curlCommand.contains("  ")) {
            curlCommand = curlCommand.replace("  ", " ");
        }

        String[] split = curlCommand.split(" ");
        for (int i = 0; i < split.length; i++) {
            String item = split[i];

            if (item.contains("-")) {
                switch (item) {
                    case "--location":
                        break;
                    case "--request":
                        curlObject.setType(split[++i]);
                        curlObject.setUrl(split[++i]);
                        break;
                    case "--header":
                        if (curlObject.getHeader() == null) {
                            curlObject.setHeader("");
                        }
                        curlObject.setHeader(curlObject.getHeader() + " " + split[++i] + " " + split[++i]);
                        break;
                    case "--data-raw":
                        StringBuilder stringBuilder = new StringBuilder();
                        while (i < split.length - 1) {
                            stringBuilder.append(split[++i]).append(" ");
                        }
                        curlObject.setBody(new Gson().fromJson(stringBuilder.toString(), JsonObject.class));
                        break;
                }
            } else {

            }
        }

        return curlObject;
    }

    public static void main(String[] args) {
//        String curlCommand = "curl --location --request POST 'http://wonders.ninggc.cn:8080/scientific_research_platform/api/scientific/project/flow/commentary/isread'  --header 'Content-Type: application/json'  --header 'userId: 286'  --data-raw '{\"projectId\":15}'";
        String curlCommand = "curl --location --request POST 'http://wonders.ninggc.cn:8080/scientific_research_platform/api/scientific/project/flow/file/createIITfile'  --header 'Content-Type: application/json'  --header 'userId: 286'  --data-raw '{\"projectId\":15,\"stepId\":1,\"revisionInstruction\":\"1\",\"amendmentDetails\":\"1\"}' \n";
        CurlObject parse = parse(curlCommand);


        System.out.println("");
    }

}
