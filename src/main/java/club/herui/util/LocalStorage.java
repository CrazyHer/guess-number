package club.herui.util;

import javafx.util.Pair;

import java.io.*;
import java.util.Hashtable;

public class LocalStorage {
    private static Hashtable<String, String> hashtable;
    private static String filePath;

    public static void init() throws IOException, ClassNotFoundException {
        init("./.guess_archive");
    }

    public static void init(String path) throws IOException, ClassNotFoundException {
        filePath = path;
        var file = new File(path);
        if (!file.exists()) {
            hashtable = new Hashtable<String, String>();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(hashtable);
            objectOutputStream.flush();
            objectOutputStream.close();
        } else {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            hashtable = (Hashtable<String, String>) objectInputStream.readObject();
            objectInputStream.close();
        }
    }

    public static void setString(String key, String value) throws IOException {
        if (hashtable != null && filePath != null) {
            hashtable.put(key, value);
            save();
        }
    }

    public static String getString(String key) {
        if (hashtable != null && filePath != null) {
            return hashtable.get(key);
        } else return null;
    }

    public static Pair<String, String>[] getAll() {
        Pair<String, String>[] res = new Pair[hashtable.size()];
        int i = 0;
        for (var entry : hashtable.entrySet()) {
            res[i] = new Pair<>(entry.getKey(), entry.getValue());
            ++i;
        }
        return res;
    }

    private synchronized static void save() throws IOException {
        if (hashtable != null && filePath != null) {
            var file = new File(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(hashtable);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }
}
