import java.io.*;
import java.util.*;

public class SaveManager {
    private static final String SAVE_FILE = "userdata.txt";

    public static class UserProgress {
        public String username;
        public int easyUnlocked = 1;
        public int mediumUnlocked = 1;
        public int hardUnlocked = 1;

        public UserProgress(String username) {
            this.username = username;
        }
    }

    public static void save(UserProgress progress) {
        Map<String, UserProgress> allSaves = loadAll();
        allSaves.put(progress.username.toLowerCase(), progress);
        
        try (PrintWriter out = new PrintWriter(new FileWriter(SAVE_FILE))) {
            for (UserProgress up : allSaves.values()) {
                out.println(up.username + ":" + up.easyUnlocked + ":" + up.mediumUnlocked + ":" + up.hardUnlocked);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserProgress load(String username) {
        Map<String, UserProgress> allSaves = loadAll();
        return allSaves.getOrDefault(username.toLowerCase(), new UserProgress(username));
    }

    private static Map<String, UserProgress> loadAll() {
        Map<String, UserProgress> saves = new HashMap<>();
        File file = new File(SAVE_FILE);
        if (!file.exists()) return saves;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length >= 4) {
                    UserProgress up = new UserProgress(parts[0]);
                    up.easyUnlocked = Integer.parseInt(parts[1]);
                    up.mediumUnlocked = Integer.parseInt(parts[2]);
                    up.hardUnlocked = Integer.parseInt(parts[3]);
                    saves.put(up.username.toLowerCase(), up);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saves;
    }
}
