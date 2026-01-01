package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class ConfigFile {
    public final Path filePath;
    public HashMap<String, String[]> configuration = new HashMap<String, String[]>();
    public ConfigFile(String[] contents, Path path) {
        filePath = path;
        HashMap<String, ArrayList<String>> configurationArray = new HashMap<String, ArrayList<String>>();
        String currentSection = "";
        for(int i = 0; i < contents.length; i++) {
            String currentLine = contents[i];
            if(currentLine.charAt(0) == '#') {
                continue;
            }
            if(currentLine.charAt(0) == '[' && currentLine.charAt(currentLine.length()-1) == ']') {
                currentSection = currentLine.substring(1,currentLine.length()-1).toUpperCase();
                continue;
            }
            if(currentLine.length() < 1) {
                continue;
            }
            if(configurationArray.containsKey(currentSection)) {
                configurationArray.get(currentSection).add(currentLine);
            } else {
                configurationArray.put(currentSection, new ArrayList<String>(Arrays.asList(currentLine)));
            }
        }
        Set<String> keys = configurationArray.keySet();
        for(String key : keys) {
            this.configuration.put(key, configurationArray.get(key).toArray(new String[0]));
        }
    }

    public static ConfigFile parseConfigFile(String filename) {
        return ConfigFile.parseConfigFile(Paths.get(filename));
    }

    public static ConfigFile parseConfigFile(File file) {
        return ConfigFile.parseConfigFile(file.toPath());
    }

    public static ConfigFile parseConfigFile(Path path) {
        String[] lines = {};
        try {
            lines = new String(Files.readAllBytes(path)).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ConfigFile(lines, path);
    }
}
