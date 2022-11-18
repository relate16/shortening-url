package com.example.shorteningurl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shorteningurl.entity.Url;
import com.example.shorteningurl.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlService {

	private final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	private final UrlRepository urlRepository;

	@Transactional
	public Long saveUrl(String originalUrl) {
		Url urlType1 = new Url(originalUrl);
		Url saveUrlType1 = urlRepository.save(urlType1);
		return saveUrlType1.getId();
	}
	

	/**
	 * getShortURL은 https://사이트경로/getShortURL 에 위치하는 URL을 반환해준다.
	 */
	public String getShortUrl(Long urlId) {
		String resultURL = encodingShortUrl(urlId);
		return resultURL;
	}

	/**
	 * 짧은 URL을 받아 인코딩 후 
	 * 원본 URL을 반환해주는 메서드
	 */
	public String getOriginalUrl(String shortUrl) {
		Long resultId = getUrlId(shortUrl);
		Url url = getUrl(resultId);
		
		return url.getOriginalURL();
	}

	public Url getUrl(Long urlId) {
		Optional<Url> findUrl = urlRepository.findById(urlId);
		Url url = 
				findUrl.orElseThrow(
						() -> new RuntimeException("해당 URL이 없습니다."));
		//오류 return 넣으려면 넣기
		return url;
	}

	
	
	// ↓ private 메서드

	/**
	 * pk인 id를 62진수로 바꾸는 메서드이다. 바꿔진 62진수는 ShortURL로 사용한다.
	 */
	private String encodingShortUrl(Long urlId) {
		StringBuilder shortUrl = new StringBuilder();

		while (urlId > 0) {
			shortUrl.append(BASE62[urlId.intValue() % 62]);
			urlId /= 62;
		}

		String resultUrl = shortUrl.reverse().toString();
		return resultUrl;
	}

	/**
	 * shortURL을 디코딩하면 Url객체의 pk값(id)이 나오는데 
	 * 그 id 값을 반환하는 메서드 
	 */
	private Long getUrlId(String shortURL) {
		Long resultId = decodingShortURL(shortURL);
		return resultId;
	}

	/**
	 * 인코딩된 shortURL을 디코딩해서 
	 * Url 객체의 pk값(id)을 반환하는 메서드
	 */
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
