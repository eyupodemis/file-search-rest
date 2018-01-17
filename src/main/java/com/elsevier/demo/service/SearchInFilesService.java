package com.elsevier.demo.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SearchInFilesService {

	private Map<String, List<String>> loadFilesMap = new HashMap<>();

	public List<String> makeSearchInFiles(List<String> searchKeywords, String rootDirectory) {

		List<String> keywordFoundFileList = new ArrayList<>();

		loadFiles(rootDirectory);

		loadFilesMap.forEach((filePath, wordsInFileList) -> {
			
			// Converted to Hash
			Set<String> hashedWordsInList = new HashSet<String>(wordsInFileList);
			
			// Compare Hash Words in file and Keyword List
			if (hashedWordsInList.containsAll(searchKeywords))
				keywordFoundFileList.add(filePath);
		});
		return keywordFoundFileList;
	}

	public void loadFiles(String directoryPath) {
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();

		try {
			for (File file : files) {
				if (file.isFile()) {
					List<String> parsedFileArray = parseFile(file.getAbsolutePath());
					loadFilesMap.put(file.getAbsolutePath(), parsedFileArray);
				} else {
					loadFiles(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<String> parseFile(String directoryPath) {

		Path pathFile = Paths.get(directoryPath);
		List<String> parsedFileArray = new ArrayList<>();

		try {
			Files.readAllLines(pathFile).parallelStream()
			                            .map(line -> line.toUpperCase().split("[,\\s*]"))
			                            .flatMap(Arrays::stream)
			                            .forEach(line -> parsedFileArray.addAll(Arrays.asList(line)));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return parsedFileArray;
	}

}
