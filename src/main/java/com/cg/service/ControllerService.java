package com.cg.service;

import org.springframework.stereotype.Service;

@Service
public interface ControllerService {

	public boolean validUrl(String url);
	public boolean generateQR(String url);
	public void shorturi(String url);
}
