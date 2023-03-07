package persistence;

import model.Account;
import model.Leaderboard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Modeled after Json Serialization Demo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads the leaderboard from file and returns it
    // throws IOException if an error occurs reading data from file
    public Leaderboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeaderboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // TODO I casted the object retrieved from JsonObject as an Account,
    //  not sure if this is what I was supposed to do
    // EFFECTS: parses Leaderboard from JSON object and returns it
    private Leaderboard parseLeaderboard(JSONObject jsonObject) {
        //ArrayList<Account> accounts = jsonObject.get("accounts");

        JSONArray jsonAccounts = jsonObject.getJSONArray("accounts");
        ArrayList<Account> accounts = new ArrayList<>();
        for (int i = 0; i < jsonAccounts.length(); i++) {
            Account a = (Account) jsonAccounts.get(i);
            accounts.add(a);
        }

        return new Leaderboard(accounts);
    }

}
