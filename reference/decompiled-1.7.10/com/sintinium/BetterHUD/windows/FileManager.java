/*
 * Decompiled with CFR 0.152.
 */
package com.sintinium.BetterHUD.windows;

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
    private BufferedReader FR;
    private BufferedWriter FW;
    private ArrayList<Double> data = new ArrayList();
    private int amount;
    private static String filePath = "UHC Essentials/configs";

    public FileManager(String fileName, int amount) {
        this.fileName = fileName;
        this.amount = amount;
        this.load();
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
                    FileManager.deleteFolder(f);
                    continue;
                }
                f.delete();
            }
        }
        folder.delete();
    }

    private void load() {
        File dir = new File(filePath);
        dir.mkdirs();
        this.file = new File(dir, this.fileName);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFile() {
        File dir = new File(filePath);
        dir.mkdirs();
        this.file = new File(dir, this.fileName);
        try {
            this.file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        this.load();
        this.data.clear();
        try {
            FileReader GFR = new FileReader(this.file);
            this.FR = new BufferedReader(GFR);
            for (int i = 0; i < this.amount; ++i) {
                this.data.add(Double.parseDouble(this.FR.readLine()));
            }
            this.FR.close();
            GFR.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void writeToFile() {
        this.clearFile();
        try {
            FileWriter GFR = new FileWriter(this.file);
            this.FW = new BufferedWriter(GFR);
            for (int i = 0; i < this.data.size(); ++i) {
                this.FW.write(this.data.get(i) + "");
                this.FW.newLine();
            }
            this.FW.close();
            GFR.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Double> getArray() {
        this.readFromFile();
        return this.data;
    }

    public void setArray(ArrayList<Double> doubles) {
        this.data = doubles;
        this.writeToFile();
    }
}
