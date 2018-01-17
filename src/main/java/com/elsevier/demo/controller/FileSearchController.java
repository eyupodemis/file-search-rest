package com.elsevier.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elsevier.demo.service.SearchInFilesService;

@RestController
@RequestMapping
public class FileSearchController {

	@Autowired
	private SearchInFilesService searchInFilesService;

	@Value("${api.config.rootDirectory}")
	private String rootDirectory;

	@GetMapping("/List/{searchKeywords}")
	public List<String> getKeywordsFoundFileList(@PathVariable("searchKeywords") List<String> searchKeywords) {

		searchKeywords.replaceAll(String::toUpperCase);
		return searchInFilesService.makeSearchInFiles(searchKeywords, rootDirectory);
	
	}

}
