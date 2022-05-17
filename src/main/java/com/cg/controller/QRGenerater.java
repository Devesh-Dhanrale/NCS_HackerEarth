package com.cg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.service.ControllerService;

@RestController
@RequestMapping
public class QRGenerater {

	private static final Logger log = LoggerFactory.getLogger(QRGenerater.class);

	@Autowired
	ControllerService s;

	@GetMapping("/generateQR")
	public void generate(@RequestParam("queryParameter") String queryParameter) {
		log.info(queryParameter);

		if (s.generateQR(queryParameter)) {
			log.info("Qr Code is created in the path where you gave");
		} else {
			log.info("Qr Code is Not created");
		}

	}

	@GetMapping("/shorternURL")
	public void shortURL(@RequestParam("queryParameter2") String queryParameter2) {
		if (s.validUrl(queryParameter2)) {
			s.shorturi(queryParameter2);
		} else {
			log.info("Invalid URL");
		}
	}

}
