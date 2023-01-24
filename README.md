# shortening-url

* 긴 url 짧은 url로 변환 :
	* base62를 가지고 인코딩 디코딩 :
		* 디코딩시 baase62를 이진탐색을 사용해 변환
	* 원본 url 형태가 어떻게 들어오던 정상으로 접속할 수 있게 디코딩 :
	  * https://www.google.com 이 아닌
		* www.google.com 으로 변환 시도하면
		* //www.google.com으로 반환해 해당 서버 프로토콜으로 접속하게 함

* 변환한 url 목록 게시판 추가 
* 변환한 url 목록 삭제 기능 추가

### home화면
![shorturlHome](https://user-images.githubusercontent.com/70901928/214249618-ab3da5ad-dea6-447c-87ec-d26db1f341f4.png)

### 원본url 짧은url로 변환(인코딩)
![changeurl](https://user-images.githubusercontent.com/70901928/214249649-59103a92-9266-4607-a0ca-b53e0089c9fe.png)
![changeurl2](https://user-images.githubusercontent.com/70901928/214249663-f03f9468-6ac2-43d8-bf9d-48235fe8cd9d.png)

### 변환된 url 목록
![shorturlBoard](https://user-images.githubusercontent.com/70901928/214249684-ce8b9228-024b-46d4-b744-c615c7762d97.png)
