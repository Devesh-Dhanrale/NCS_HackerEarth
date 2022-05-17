package com.cg.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import com.cg.controller.QRGenerater;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class ControllerServiceIMP implements ControllerService {

	private static final Logger log = LoggerFactory.getLogger(QRGenerater.class);

	Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

	@Override
	public boolean validUrl(String url) {

		String regex = "((http|https)://)(www.)?" + "[a-zA-Z0-9@:%._\\+~#?&//=]" + "{2,256}\\.[a-z]"
				+ "{2,6}\\b([-a-zA-Z0-9@:%" + "._\\+~#?&//=]*)";
		Pattern p = Pattern.compile(regex);
		if (url == null) {
			return false;
		}
		Matcher m = p.matcher(url);
		log.info("valided");
		return m.matches();
	}

	@Override
	public boolean generateQR(String url) {
		String path = "D:\\STS Maven\\NCS_HackerEarth\\QR.png";
		if (validUrl(url)) {
			hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			try {
				generateQRcode(url, path, "UTF-8", hashMap, 200, 200);
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			log.info("Not a valid String");
		}
		return false;

	}

	public void generateQRcode(String data, String path, String charset, Map map, int h, int w)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, w, h);
		MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
	}

	@Override
	public void shorturi(String url) {
		int n = 439995964;
		String shorturl = idToShortURL(n);
		System.out.println("Generated short url is " + shorturl);
		System.out.println("Id from url is " +
							shortURLtoID(url));
	}

	String idToShortURL(int n) {

		char map[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

		StringBuffer shorturl = new StringBuffer();

		while (n > 0) {

			shorturl.append(map[n % 62]);
			n = n / 62;
		}

		return shorturl.reverse().toString();
	}

	int shortURLtoID(String shortURL) {
		int id = 0;

		for (int i = 0; i < shortURL.length(); i++) {
			if ('a' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'z')
				id = id * 62 + shortURL.charAt(i) - 'a';
			if ('A' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'Z')
				id = id * 62 + shortURL.charAt(i) - 'A' + 26;
			if ('0' <= shortURL.charAt(i) && shortURL.charAt(i) <= '9')
				id = id * 62 + shortURL.charAt(i) - '0' + 52;
		}
		return id;
	}
}
