package com.rpg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data loading
 */
public class DataUtil {
    private static ArrayList<GameLevel> levels = new ArrayList<>();

    public static boolean loadData() {
        try {
            String filePath = "./resources/map.json";
            String dataStr = readFile(filePath);
            if (isStringEmpty(dataStr)) {
                return false;
            }
            JSONArray jsonArray = new JSONArray(dataStr);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        int[][] map = convertJSONToArray(jsonObject.getJSONArray("map"));
                        if (map != null) {
                            GameLevel level = new GameLevel();
                            level.setName(jsonObject.getString("name"));
                            level.setObserveTime(jsonObject.getInt("observeTime"));
                            level.setMap(map);
                            levels.add(level);
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[][] convertJSONToArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.length() <= 0) {
            return null;
        }
        try {
            int[][] array = new int[jsonArray.length()][16];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray rowArray = jsonArray.getJSONArray(i);
                if (rowArray != null) {
                    for (int j = 0; j < rowArray.length(); j++) {
                        array[i][j] = rowArray.getInt(j);
                    }
                }
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameLevel getLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            return null;
        }
        return levels.get(index);
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
