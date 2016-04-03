package com.fractureof.demos.location.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DatePlansContent {


    public static void loadFromJson(JSONArray arr) {


        try {
            for (int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                 addItem(new DatePlanItem(
                         obj.get("id").toString(),
                         String.format("%s, %s...",
                                 obj.get("name"),
                                 obj.get("date_time")
                                 ),
                         ""
                 ));

            }
        }
        catch (JSONException ex) {}
    }
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DatePlanItem> ITEMS = new ArrayList<DatePlanItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DatePlanItem> ITEM_MAP = new HashMap<String, DatePlanItem>();

    private static final int COUNT = 25;

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

    private static void addItem(DatePlanItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

//    private static DatePlanItem createDummyItem(int position) {
//        return new DatePlanItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
        return "";
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DatePlanItem {
        public final String id;
        public final String content;
        public final String details;

        public DatePlanItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
