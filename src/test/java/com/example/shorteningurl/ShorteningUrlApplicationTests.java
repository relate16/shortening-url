package com.example.shorteningurl;

import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shorteningurl.entity.Url;
import com.example.shorteningurl.service.UrlService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class ShorteningUrlApplicationTests {
	
	@Autowired UrlService urlService;

	@Test
	void contextLoads() {

		
		int threadNumber = 8;
		ForkJoinPool pool = new ForkJoinPool(threadNumber);
		// force the creation of all worker threads
		pool.invokeAll(Collections.nCopies(threadNumber*2, () -> { Thread.sleep(500); return ""; }));
		int oldNum = pool.getPoolSize();
		System.out.println(oldNum+" threads; waiting for dying threads");
		long t0 = System.nanoTime();
		while(oldNum > 0) {
		    while(pool.getPoolSize()==oldNum)
		        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
		    long t1 = System.nanoTime();
		    oldNum = pool.getPoolSize();
		    System.out.println(threadNumber-oldNum+" threads terminated after "
		        +TimeUnit.NANOSECONDS.toSeconds(t1 - t0)+"s");
		}
		
		for (int i = 0; i < 7; i++) {
			Url url = new Url("www.naver.com");
			Long id = urlService.saveUrl(url.getOriginalUrl());
		}
		
		
	}
}
