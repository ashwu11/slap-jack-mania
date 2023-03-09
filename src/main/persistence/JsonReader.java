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

/*
 * Represents a reader that reads workroom from JSON data stored in file
 * Modeled after Json Serialization Demo
 */

public class JsonReader {
    private final String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads the leaderboard from file and returns it
    // if an error occurs reading data from file, throws an IOException
    public Leaderboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeaderboard(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Leaderboard from JSON object and returns it
    private Leaderboard parseLeaderboard(JSONObject jsonObject) {
        JSONArray jsonAccounts = jsonObject.getJSONArray("accounts");
        ArrayList<Account> accounts = new ArrayList<>();

        for (int i = 0; i < jsonAccounts.length(); i++) {
            JSONObject a = (JSONObject) jsonAccounts.get(i);
            String user = a.getString("username");
            int wins = a.getInt("wins");
            int games = a.getInt("gamesPlayed");
            Account acc = new Account(user);
            acc.setWins(wins);
            acc.setGamesPlayed(games);
            accounts.add(acc);
        }

        return new Leaderboard(accounts);
    }

}
