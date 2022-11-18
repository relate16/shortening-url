package com.example.shorteningurl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shorteningurl.entity.UrlType1;
import com.example.shorteningurl.entity.UrlType2;
import com.example.shorteningurl.repository.UrlType1Repository;
import com.example.shorteningurl.repository.UrlType2Repository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlService {

	private final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	private final UrlType1Repository urlType1Repository;
	private final UrlType2Repository urlType2Repository;

	@Transactional
	public Long saveUrlType1(String originalURL) {
		UrlType1 urlType1 = new UrlType1(originalURL);
		UrlType1 saveUrlType1 = urlType1Repository.save(urlType1);
		return saveUrlType1.getId();
	}
	
	@Transactional
	public Long saveUrlType2(String originalURL) {
		UrlType2 urlType2 = new UrlType2(originalURL);
		UrlType2 saveUrlType2 = urlType2Repository.save(urlType2);
		return saveUrlType2.getId();
	}

	/**
	 * getShortURL은 https://사이트경로/getShortURL 에 위치하는 URL을 반환해준다.
	 */
	public String getShortURL(Long urlType1Id) {
		String resultURL = encodingShortURL(urlType1Id);
		return resultURL;
	}

	public String getOriginalURL(String shortURL) {
		String originalURL = "";

		// resultId는 UrlType1에 있는 id일 수도 있고 UrlType2에 있는 id일 수도 있다.
		Long resultId = getUrlTypeId(shortURL);
		
		/*
		 * shortURL 기반이 pk이므로 pk로 UrlType1 table인지, UrlType2 table인지 따로 구분해서 검색 불가 
		 * 그럼으로 저장 빈도가 훨씬 낮은 긴 원본url table를 먼저 검색
		 */
		Optional<UrlType2> findUrlType2 = urlType2Repository.findById(resultId);
		
		if(findUrlType2.isEmpty()) {
			// 검색시 긴 원본 url 테이블에 없으면 짧은 원본 url 테이블 검색
			UrlType1 url = getUrlType1(resultId);
			originalURL = url.getOriginalURL();
		} else {
			// 검색시 긴 원본 url 테이블에 있으면 아래 나머지 로직 수행
			UrlType2 url = 
					findUrlType2.orElseThrow(
							() -> new RuntimeException("해당 URL이 없습니다."));
			//오류 return 넣으려면 넣기
			originalURL = url.getOriginalURL();
		}
		
		return originalURL;
	}

	public UrlType1 getUrlType1(Long urlType1Id) {
		Optional<UrlType1> findUrlType1 = urlType1Repository.findById(urlType1Id);
		UrlType1 url = 
				findUrlType1.orElseThrow(
						() -> new RuntimeException("해당 URL이 없습니다."));
		//오류 return 넣으려면 넣기
		return url;
	}

	
	
	// ↓ private 메서드

	/**
	 * pk인 id를 62진수로 바꾸는 메서드이다. 바꿔진 62진수는 ShortURL로 사용한다.
	 */
	private String encodingShortURL(Long urlType1Id) {
		StringBuilder shortURL = new StringBuilder();

		while (urlType1Id > 0) {
			shortURL.append(BASE62[urlType1Id.intValue() % 62]);
			urlType1Id /= 62;
		}

		String resultURL = shortURL.reverse().toString();
		return resultURL;
	}

	private Long getUrlTypeId(String shortURL) {
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
