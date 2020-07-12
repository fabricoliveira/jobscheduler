package com.fabricio.jobscheduler.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	/*
	 * filename: Enter the path/filename of the mass data.
	 * startFromLine: Enter the number of the line that you want to start reading.
	 * maxNumberOfLines: Enter the number of lines to read after startFromLine. Enter 0 for all lines.
	 * 
	 * For example: if you want to the first line only, enter startFromLine=1 and maxNumberOfLines=1
	 */
	public String readFile(String filename, Integer startFromLine, Integer maxNumberOfLines) {		
		StringBuilder text = new StringBuilder();
		
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			Iterator<String> iterator = stream.iterator();
			int counter = 1;
			
			while (iterator.hasNext()) {
				String line = iterator.next();
				if(counter >= startFromLine) {
					text.append(line);
				}
				if(counter == maxNumberOfLines && maxNumberOfLines != 0) {
					break;
				}
				counter++;
			}
			return text.toString().trim();
		} catch (IOException e) {
			System.err.println("Text " + e.getMessage() + " could not be opened");
		}
		return "";
	}
	
	public void writeFile(String content, String filename) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write(content);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
