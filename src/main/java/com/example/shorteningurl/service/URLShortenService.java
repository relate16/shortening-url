package com.example.shorteningurl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shorteningurl.entity.URLShorten;
import com.example.shorteningurl.repository.URLShortenRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class URLShortenService {

	private final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	
	private final URLShortenRepository urlShortenRepository;

	@Transactional
	public Long saveURLShorten(String originalURL) {
		URLShorten urlShorten = new URLShorten(originalURL);
		URLShorten saveUrlShorten = urlShortenRepository.save(urlShorten);
		return saveUrlShorten.getId();
	}
	
	/**
	 * getShortURL은 https://사이트경로/getShortURL 에 위치하는 URL을 반환해준다.
	 */
	public String getShortURL(Long urlShortenId) {
		String resultURL = encodingShortURL(urlShortenId);
		return resultURL;
	}
	
	public String getOriginalURL(String shortURL) {
		
		Long resultId = getURLShortenId(shortURL);

		Optional<URLShorten> findURLShorten = urlShortenRepository.findById(resultId);

		URLShorten urlShorten = findURLShorten.orElseThrow(() -> new RuntimeException("해당 URL이 없습니다."));
		// 400 오류 넣으려면 넣기

		String orginalURL = urlShorten.getOriginalURL();
		return orginalURL;
	}
	
	
	
	
	
	// ↓ private 메서드

	/**
	 * pk인 id를 62진수로 바꾸는 메서드이다. 
	 * 바꿔진 62진수는 ShortURL로 사용한다. 
	 */
	private String encodingShortURL(Long urlShortenId) {
		StringBuilder shortURL = new StringBuilder();

		while (urlShortenId > 0) {
			shortURL.append(BASE62[urlShortenId.intValue() % 62]);
			urlShortenId /= 62;
		}

		String resultURL = shortURL.reverse().toString();
		return resultURL;
	}
	
	private Long getURLShortenId(String shortURL) {
		Long resultId = decodingShortURL(shortURL);
		return resultId;
	}

	private Long decodingShortURL(String shortURL) {
		String id = "";
		int start = 0;
		int end = 62;
		int mid = (start + end) / 2;

		char[] shortURLArray = shortURL.toCharArray();
		
		for (char i : shortURLArray) {
			while (true) {
				if (BASE62[mid] > i) {
					end = mid - 1;
					mid = (start + end) / 2;
				} else if (BASE62[mid] < i) {
					start = mid + 1;
					mid = (start + end) / 2;
				} else {
					id += mid;
					break;
				}
			}
		}

		Long resultId = Long.parseLong(id);
		return resultId;
	}

}
