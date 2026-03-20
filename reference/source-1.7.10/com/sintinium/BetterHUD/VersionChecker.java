package com.sintinium.BetterHUD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class VersionChecker {

	private double version;
	private ArrayList data = new ArrayList();
	
	public ArrayList getCurrentVersion() {
		try {
			URL versionChecker
			versionChecker = new URL("http://pastebin.com/raw.php?i=dQYWGxw5");
			BufferedReader in = new BufferedReader(new InputStreamReader(versionChecker.openStream()));
			data.clear();

			String inputLine;
			while((inputLine = in.readLine()) != null) {
				data.add(inputLine);
			}
			in.close();
		} catch (Exception e) {
			System.out.println("[UHC ESSENTIALS] Couldn't fetch UHC Essentials Version.");
		}
		return data;
	}
}
