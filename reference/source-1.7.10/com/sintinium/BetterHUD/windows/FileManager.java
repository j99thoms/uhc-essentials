package com.sintinium.BetterHUD.windows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FileManager {
	private String fileName;
	private File file;
	private BufferedReader FR;
	private BufferedWriter FW;
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
		if (files != null) { // some JVMs return null for empty dirs
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
			FileReader GFR = new FileReader(file);
			FR = new BufferedReader(GFR);

			for (int i = 0; i < amount; i++) {
				data.add(Double.parseDouble(FR.readLine()));
			}

			FR.close();
			GFR.close();
		} catch (Exception e) {
		}
	}

	private void writeToFile() {
		clearFile();

		try {
			FileWriter GFR = new FileWriter(file);
			FW = new BufferedWriter(GFR);

			for (int i = 0; i < data.size(); i++) {
				FW.write(data.get(i) + "");
				FW.newLine();
			}
			FW.close();
			GFR.close();
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
