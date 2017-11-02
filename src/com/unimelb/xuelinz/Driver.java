package com.unimelb.xuelinz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Driver {
	public static void main(String[] args) {

		File resultFile = new File("result.csv");
		
		// Store game names
		Map<String, Double> gameMap = new LinkedHashMap();

		try {
			BufferedReader gameReader = new BufferedReader(
					new InputStreamReader(new FileInputStream("Data.csv"), "gbk"));
			BufferedReader orderReader = new BufferedReader(
					new InputStreamReader(new FileInputStream("Order.csv"), "gbk"));

			if (!resultFile.exists()){
				resultFile.createNewFile();
			}
			
			// Game arraylist
			String game;
			int line = 0;
			while ((game = orderReader.readLine()) != null) {
				line++;
				if (line > 1) {
					String company = game.split(",")[0];
					String gameName = game.split(",")[1];
					gameMap.put(gameName, 0.0);
				}
			}
			
			String gameInfo;
			int dataLine = 0;
			while((gameInfo = gameReader.readLine())!=null){
				dataLine++;
				if(dataLine>1){
					String gameName = gameInfo.split(",")[1];
					Double money = Double.parseDouble(gameInfo.split(",")[6]);
					if(gameMap.containsKey(gameName)){
						Double totalMoney = gameMap.get(gameName);
						gameMap.put(gameName, totalMoney+money);
					}else{
						gameMap.put(gameName, money);
					}
				}
			}

			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(resultFile),"gbk");      
	        BufferedWriter writer=new BufferedWriter(write);
	        
			System.out.println(gameMap.size());
			int i = 0;
			Iterator it = gameMap.entrySet().iterator();
			while (it.hasNext()) {
				i++;
				System.out.println(i);
				Map.Entry e = (Map.Entry) it.next();
				System.out.println(e.getKey() + "," + e.getValue());
				writer.write(e.getKey() + "," + e.getValue()+"\n");
			}
	     
	        writer.close(); 
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
