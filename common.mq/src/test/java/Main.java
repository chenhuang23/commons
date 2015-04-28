/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import com.github.commons.utils.format.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main.java
 *
 * @author zhouxiaofeng 4/27/15
 */
public class Main {

    public static void main(String[] args) {

        String s = " [\"40e76bf3-640b-4dbd-8eaa-087a6cd24d55\", \"queuecmd_crawl\", null, 0, 0, [[\"{\"userName\": \"maojie\", \"cookies\": null, \"retry\": 0, \"text\": null, \"retry_times\": 3, \"data\": null, \"time\": 1430098627.231, \"password\": \"aaa\", \"type\": \"HZGjjSpider\", \"id\": \"ea3422f0-ec7d-11e4-b17c-d43d7ec81402\", \"channel\": null}\"], {}]] ";

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(s);

        System.out.println(jsonElement);

        JsonArray asJsonArray = parser.parse(s).getAsJsonArray();

        System.out.println(asJsonArray);
    }
}
