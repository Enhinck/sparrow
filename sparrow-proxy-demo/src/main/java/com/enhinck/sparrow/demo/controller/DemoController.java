package com.enhinck.sparrow.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

	/**
	 * 清除Redis中 accesstoken refreshtoken
	 *
	 * @param accesstoken accesstoken
	 * @return true/false
	 */
	@PostMapping("/test")
	public String removeToken(String accesstoken) {

		return accesstoken;
	}
}