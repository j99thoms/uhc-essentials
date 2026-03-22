package com.j99thoms.uhcessentials.windows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private String fileName;
    private File file;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ArrayList<Double> data = new ArrayList<Double>();
    private int amount;
    private static String filePath = "UHC Essentials/configs";

    public FileManager(String fileName, int amount) {
        this.fileName = fileName;
        this.amount = amount;
        load();
    }

    public static void deleteAll() {
        File folder = new File(filePath);
        FileManager.deleteFolder(folder);
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    private void load() {
        File dir = new File(filePath);
        dir.mkdirs();
        file = new File(dir, fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFile() {
        File dir = new File(filePath);
        dir.mkdirs();
        file = new File(dir, fileName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        load();
        data.clear();

        try {
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);

            for (int i = 0; i < amount; i++) {
                data.add(Double.parseDouble(reader.readLine()));
            }

            reader.close();
            fileReader.close();
        } catch (Exception e) {
        }
    }

    private void writeToFile() {
        clearFile();

        try {
            FileWriter fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i) + "");
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Double> getArray() {
        readFromFile();
        return data;
    }

    public void setArray(ArrayList<Double> doubles) {
        data = doubles;
        writeToFile();
    }
}
