/*
Copyright (c) 2023, skmoOcean
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree. 
*/

package ca.skmo.catbridge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CatFrame {
	
	public static String clanSaveFilePath = "";
	public static String clanCatsFilePath = "";
	public static String catsToAddFolderPath = "";
	
	static JFrame frame;
	
	public CatFrame() {
		
		int frameSizeX = 420;
		int frameSizeY = 310;
		
		int frameStartX = (Toolkit.getDefaultToolkit().getScreenSize().width / 4) - (frameSizeX / 2);                
		int frameStartY = (Toolkit.getDefaultToolkit().getScreenSize().height / 4) - (frameSizeY / 2);
		
		frame = new JFrame("CatBridge");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameSizeX, frameSizeY);
		frame.setLocation(frameStartX, frameStartY);
		frame.setLayout(null);
		
		frame.setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().getImage(CatBridge.class.getResource("/catbridge-icon.png"))).getImage());
		
		JLabel savedCatsFolderLabel = new JLabel("CatGen savedcats folder path (E.g. Catgen-master\\savedcats)");
		savedCatsFolderLabel.setBounds(10, 10, 375, 20);
		frame.add(savedCatsFolderLabel);
		
		JTextField savedCatsFolderPath = new JTextField();
		savedCatsFolderPath.setBounds(10, 30, 308, 20);
		frame.add(savedCatsFolderPath);
		
		JButton savedCatsFolderBrowse = new JButton("Browse...");
		savedCatsFolderBrowse.setBounds(320, 30, 75, 19);
		savedCatsFolderBrowse.setMargin(new Insets(0, 5, 0, 5));
		frame.add(savedCatsFolderBrowse);
		
		savedCatsFolderBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open folder explorer
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showDialog(null, "Select Folder");
				
				// Check if folder is selected
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					savedCatsFolderPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					catsToAddFolderPath = savedCatsFolderPath.getText();
					System.out.println("Selected Folder: " + savedCatsFolderPath.getText());
				} else {
					System.out.println("No folder selected.");
				}
			}
		});
		
		JLabel clanCatsJsonLabel = new JLabel("Clan Cats File (E.g. ClanGen\\saves\\<clanname>\\clan_cats.json)");
		clanCatsJsonLabel.setBounds(10, 60, 375, 20);
		frame.add(clanCatsJsonLabel);
		
		JTextField clanCatsJsonPath = new JTextField();
		clanCatsJsonPath.setBounds(10, 80, 308, 20);
		frame.add(clanCatsJsonPath);
		
		JButton clanCatsJsonBrowse = new JButton("Browse...");
		clanCatsJsonBrowse.setBounds(320, 80, 75, 19);
		clanCatsJsonBrowse.setMargin(new Insets(0, 5, 0, 5));
		frame.add(clanCatsJsonBrowse);
		
		clanCatsJsonBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open folder explorer
				File defaultClanCatsPath = new File(System.getenv("LOCALAPPDATA") + "\\ClanGen\\ClanGen\\saves\\");
				JFileChooser fileChooser = null;
				if (defaultClanCatsPath.exists() && defaultClanCatsPath.isDirectory()) {
					fileChooser = new JFileChooser(defaultClanCatsPath);
				} else {
					fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				}
				
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser.showDialog(null, "Select Clan Cats File");
				
				// Check if folder is selected
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					clanCatsJsonPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("Selected Clan Cats Json: " + clanCatsJsonPath.getText());
				} else {
					System.out.println("No Clan Cats Json selected.");
				}
			}
		});
		
		
		JLabel clanSaveJsonLabel = new JLabel("Clan Save File (E.g. ClanGen\\saves\\<clanname>clan.json)");
		clanSaveJsonLabel.setBounds(10, 110, 375, 20);
		frame.add(clanSaveJsonLabel);
		
		JTextField clanSaveJsonPath = new JTextField();
		clanSaveJsonPath.setBounds(10, 130, 308, 20);
		frame.add(clanSaveJsonPath);
		
		JButton clanSaveJsonBrowse = new JButton("Browse...");
		clanSaveJsonBrowse.setBounds(320, 130, 75, 19);
		clanSaveJsonBrowse.setMargin(new Insets(0, 5, 0, 5));
		frame.add(clanSaveJsonBrowse);
		
		clanSaveJsonBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open folder explorer
				File defaultClanSavePath = new File(System.getenv("LOCALAPPDATA") + "\\ClanGen\\ClanGen\\saves\\");
				JFileChooser fileChooser = null;
				if (defaultClanSavePath.exists() && defaultClanSavePath.isDirectory()) {
					fileChooser = new JFileChooser(defaultClanSavePath);
				} else {
					fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				}
				
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser.showDialog(null, "Select Clan Save File");
				
				// Check if folder is selected
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					clanSaveJsonPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("Selected Clan Save Json: " + clanSaveJsonPath.getText());
				} else {
					System.out.println("No Clan Save Json selected.");
				}
			}
		});
		
		JLabel importAllCatsLabel = new JLabel("This will import all cats from CatGen's savedcats folder.");
		importAllCatsLabel.setBounds(10, 170, 400, 20);
		importAllCatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(importAllCatsLabel);
		
		
		
		JLabel pleaseBackupLabel = new JLabel("Don't forget to backup your clan cats/clan save files first!");
		pleaseBackupLabel.setBounds(10, 190, 400, 20);
		pleaseBackupLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(pleaseBackupLabel);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(175, 215, 70, 19);
		submitButton.setMargin(new Insets(0, 5, 0, 5));
		frame.add(submitButton);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				importCats(savedCatsFolderPath.getText(), clanCatsJsonPath.getText(), clanSaveJsonPath.getText());
				
			}
		});
		
		frame.setVisible(true);
		
	}
	
	public static JLabel completedLabel() {
		JLabel completedLabel = new JLabel("Completed!");
		completedLabel.setBounds(10, 240, 400, 20);
		completedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font completedLabelFont = completedLabel.getFont();
		completedLabel.setFont(new Font(completedLabelFont.getName(), Font.BOLD, 16));
		completedLabel.setForeground(new Color(0, 150, 0));
		
		return completedLabel;
	}
	
	public static JLabel failedLabel() {
		JLabel completedLabel = new JLabel("Failed.");
		completedLabel.setBounds(10, 240, 400, 20);
		completedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font completedLabelFont = completedLabel.getFont();
		completedLabel.setFont(new Font(completedLabelFont.getName(), Font.BOLD, 16));
		completedLabel.setForeground(new Color(150, 0, 0));
		
		return completedLabel;
	}
	
	public void importCats(String pathSavedCatsFolder, String pathToClanCatsFile, String pathToClanSaveFile) {

	    List<String> catContents = new ArrayList<>();
	    
	    File folder = new File(pathSavedCatsFolder);
	    
	    File[] files = folder.listFiles();
	    
	    if (files != null) {
	    	for (File file : files) {
	    		String builder = "";
	    		if (file.isFile() && file.getName().endsWith(".json")) {
	    			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line = "";
						while ((line = br.readLine()) != null) {
							builder += line;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}
	    		
	    		catContents.add(builder);
	    	}
	    }
		
	    System.out.println("Found " + catContents.size() + " cats to import!");
	    
	    addToClanFile(catContents, pathToClanSaveFile, pathToClanCatsFile);
	}
	
	public static void addToClanFile(List<String> catContents, String pathToClanSaveFile, String pathToClanCatsFile) {
		try {
			
			// !get clan cats file as string
			// !convert to json array
			
			// !for each cat in catContents
			// !convert to json object
			// !copy id to idsToAdd string
			// !add to array
			
			// !convert array back to string
			// !save new string to clan cats file
			
			// !get clan save file as string
			// !convert to json object
			// !add idsToAdd to current clan cats parameter
			// !convert object back to string
			// !save new string as clan save file
			
			String idsToAddToClanSaveFile = "";
			
			// Get clan cats file
			File clanCatsFile = new File(pathToClanCatsFile);
			
			// Get as string
			String clanCatsFileStringBuilder = "";
    		if (clanCatsFile.isFile() && clanCatsFile.getName().endsWith(".json")) {
    			try (BufferedReader br = new BufferedReader(new FileReader(clanCatsFile))) {
					String line = "";
					while ((line = br.readLine()) != null) {
						clanCatsFileStringBuilder += line;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    		
    		// Convert clan cats file to JSON Array
			JSONArray clanArray = new JSONArray(new JSONTokener(clanCatsFileStringBuilder));
			
			// For each cat in catContents convert to json object, save id, and add object to array
			for (String cat : catContents) {
				JSONObject newCat = new JSONObject(cat);
				idsToAddToClanSaveFile += "," + newCat.getString("ID");
				clanArray.put(newCat);
				System.out.println("Adding cat: " + newCat.getString("ID") + " " + newCat.getString("name_prefix") + newCat.getString("name_suffix"));
			}
			
			// Convert array back to string
			String newClanCatsFileString = clanArray.toString(2);
			
			// Write string back to file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToClanCatsFile))) {
				writer.write(newClanCatsFileString);
				System.out.println("Saved new " + clanCatsFile.getName() + " file");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			// Get clan save file
			File clanSaveFile = new File(pathToClanSaveFile);
			
			// Get as string
			String clanSaveFileStringBuilder = "";
			if (clanSaveFile.isFile() && clanSaveFile.getName().endsWith(".json")) {
				try (BufferedReader br = new BufferedReader(new FileReader(clanSaveFile))) {
					String line = "";
					while ((line = br.readLine()) != null) {
						clanSaveFileStringBuilder += line;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// Convert clan save file to JSON Object
			JSONObject clanSave = new JSONObject(clanSaveFileStringBuilder);
			
			// Get clan_cats ids as string
			String clanCatsIds = clanSave.getString("clan_cats");
			
			// Update clan_cats ids list
			clanSave.put("clan_cats", clanCatsIds + idsToAddToClanSaveFile);
			System.out.println("Adding cat ids to clan save file");
			
			String newClanSaveFileString = clanSave.toString(2);
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToClanSaveFile))){
				writer.write(newClanSaveFileString);
				System.out.println("Saved new " + clanSaveFile.getName() + " file");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			frame.add(completedLabel());
			frame.repaint();
			System.out.println("Process Completed. Enjoy your new cats!");
			System.out.println("");
			System.out.println("NOTE: Don't forget to remove cats from savedcats folder!");
			System.out.println("You may now close the program");
			
		} catch (Exception e) {
			frame.add(failedLabel());
			frame.repaint();
			e.printStackTrace();
		}
		
	}
	
	public static String readFileAsString(String filePath) throws IOException {
	    StringBuilder content = new StringBuilder();
	    System.out.println("Attempting to read file: " + filePath);
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            content.append(line).append("\n");
	        }
	    }
	    return content.toString();
	}
}
