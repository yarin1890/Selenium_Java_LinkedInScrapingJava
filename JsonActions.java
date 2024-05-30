import javax.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.io.File;

public class JsonActions {
    // Export the user's profile info, and its connections
    public static void exportAsJson(Map<String, String> profileInfo, List<Map<String, String>> connectionsData) {
        JsonObject jsonData = Json.createObjectBuilder()
                .add("myName", profileInfo.get("myName"))
                .add("myWorkplace", profileInfo.get("myWorkplace"))
                .add("city", profileInfo.get("city"))
                .add("connections", createConnectionsJsonArray(connectionsData))
                .build();

        String filePath = "C:" + File.separator + "connections.json";
        try (FileWriter file = new FileWriter(filePath)) {
            JsonWriter jsonWriter = Json.createWriter(file);
            jsonWriter.writeObject(jsonData);
        } catch (IOException e) {
            System.out.println("Failed to export data to a JSON file: " + e.getMessage());
        }
    }

    // Transfer the connections map we have to a JsonArrayBuilder that will be added to the output Json
    private static JsonArray createConnectionsJsonArray(List<Map<String, String>> connectionsData) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Map<String, String> connection : connectionsData) {
            JsonObject jsonConnection = Json.createObjectBuilder()
                    .add("name", connection.get("name"))
                    .add("job", connection.get("job"))
                    .add("time", "Connected " + connection.get("time"))
                    .build();
            jsonArrayBuilder.add(jsonConnection);
        }

        return jsonArrayBuilder.build();
    }
}