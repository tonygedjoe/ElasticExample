package com.tg.elastic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.elasticsearch.client.RestHighLevelClient;


@RestController
public class ExampleController {
	
	@GetMapping("/esget")
	public String esGet() {
		
		
		return "";
		
	}

}
