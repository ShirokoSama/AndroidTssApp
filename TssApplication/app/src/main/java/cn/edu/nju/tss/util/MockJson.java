package cn.edu.nju.tss.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Srf on 2017/6/18
 */

public class MockJson {

    public static JSONObject getMockAssignmentScore() {
        String jsonStr = "{\n" +
                "    \"assignmentId\": 3,\n" +
                "    \"questions\": [\n" +
                "      {\n" +
                "        \"questionInfo\":{\n" +
                "          \"id\": 1,\n" +
                "          \"title\": \"题目1\",\n" +
                "          \"description\": \"题目1\",\n" +
                "          \"type\": \"exam\"\n" +
                "        },\n" +
                "        \"students\":[\n" +
                "          {\n" +
                "            \"studentId\": 227,\n" +
                "            \"studentName\": \"nanguangtailang\",\n" +
                "            \"studentNumber\": \"111000111\",\n" +
                "            \"score\": 100,\n" +
                "            \"scored\": true\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject getMockAssignmentResult() {
        String jsonStr = "{\n" +
                "  \"studentId\":227,\n" +
                "  \"assignmentId\":3,\n" +
                "  \"questionResults\":[\n" +
                "  \t{\n" +
                "\t    \"questionId\":1,\n" +
                "\t    \"questionTitle\":\"题目1\",\n" +
                "\t    \"scoreResult\":{\n" +
                "\t      \"git_url\":\"xxx.git\",\n" +
                "\t      \"score\":100,\n" +
                "\t      \"scored\":true\n" +
                "\t    },\n" +
                "\t    \"testResult\":{\n" +
                "\t      \"git_url\":\"xxx.git\",\n" +
                "\t      \"compile_succeeded\":true,\n" +
                "\t      \"tested\":true,\n" +
                "\t      \"testcases\":[\n" +
                "\t      \t{\n" +
                "\t        \"name\":\"test1\",\n" +
                "\t        \"passed\":true\n" +
                "\t        }\n" +
                "\t      ]\n" +
                "\t    },\n" +
                "\t    \"metricData\":{\n" +
                "\t      \"git_url\":\"xxx.git\",\n" +
                "\t      \"measured\":true,\n" +
                "\t      \"total_line_count\":158,\n" +
                "\t      \"comment_line_count\":35,\n" +
                "\t      \"field_count\":5,\n" +
                "\t      \"method_count\":5,\n" +
                "\t      \"max_coc\":2\n" +
                "\t    }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject obj = new JSONObject();
        try {
            obj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
