package common;

public class RequestBodies {
    public static String createUser = "{" +
            "\"userName\": \"PARAM1\"," +
            "\"password\": \"PARAM2\"" +
            "}";

    public static String collectionOfIsbns = "{" +
            "\"userId\": \"PARAM1\"," +
            "\"collectionOfIsbns\":[{\"isbn\":\"PARAM2\"}]}";

    public static String deleteIsbn = "{" +
            "\"userId\": \"PARAM1\"," +
            "\"isbn\":\"PARAM2\"}";

    public static String putIsbn = "{" +
            "\"userId\": \"PARAM1\"," +
            "\"isbn\":\"PARAM2\"}";

}

