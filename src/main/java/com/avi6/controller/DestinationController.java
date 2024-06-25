package com.avi6.controller;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avi6.dto.DestinationDTO;
import com.avi6.service.DestinationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sample")
@RequiredArgsConstructor
public class DestinationController {

	private final DestinationService destinationService;
	
	
	

	@PostMapping("/index")
	public String saveDestinations(@RequestBody List<com.avi6.dto.DestinationDTO> destinationDTOList) {
	    System.out.println(destinationDTOList + "고요한");
	    try {
	        List<com.avi6.entity.DestinationEntity> savedDestinations = destinationService.saveDestinations(destinationDTOList);
	        return "redirect:/sample/index";
	    } catch (Exception e) {
	        return "저장안됨";
	    }
	}
	


	@GetMapping("/seoul")
	public void seoul() {
		System.out.println("exAll..........");
	}

	@PostMapping("/seoul")
	public ResponseEntity<Map<String, Object>> seoul(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/incheon")
	public void incheon() {
		System.out.println("exAll..........");
	}

	@PostMapping("/incheon")
	public ResponseEntity<Map<String, Object>> incheon(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/gyeongju")
	public void gyeongju() {
		System.out.println("exAll..........");
	}
	

	@PostMapping("/gyeongju")
	public ResponseEntity<Map<String, Object>> gyeongju(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/jeju")
	public void jeju() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/jeju")
	public ResponseEntity<Map<String, Object>> jeju(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/busan")
	public void busan() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/busan")
	public ResponseEntity<Map<String, Object>> busan(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/jeonju")
	public void jeonju() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/jeonju")
	public ResponseEntity<Map<String, Object>> jeonju(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/kangreung")
	public void kangreung() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/kangreung")
	public ResponseEntity<Map<String, Object>> kangreung(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/suwon")
	public void suwon() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/suwon")
	public ResponseEntity<Map<String, Object>> suwon(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	
	@GetMapping("/yeosu")
	public void yeosu() {
		System.out.println("exAll..........");
	}
	
	

	@PostMapping("/yeosu")
	public ResponseEntity<Map<String, Object>> yeosu(@RequestBody Map<String, Object> requestData) {
		// 클라이언트로부터 전송된 데이터를 받음
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("requestData", requestData); // 요청 데이터를 응답에 포함시킵니다.
		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}


	@GetMapping("/index")
	public String index() {
	    System.out.println("index 요청됨");
	    return "index";
	}

	@PostMapping("/seoul_location")
	public ResponseEntity<Map<String, Object>> seoul_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/seoul_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/seoul_location") // GET 요청을 처리하는 메서드 추가
	public void seoul_location() {
		System.out.println("seoul_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/busan_location")
	public ResponseEntity<Map<String, Object>> busan_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/busan_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/busan_location") // GET 요청을 처리하는 메서드 추가
	public void busan_location() {
		System.out.println("busan_location 페이지에 접속했습니다.");
	}

	@GetMapping("/gyeongju_location") // GET 요청을 처리하는 메서드 추가
	public void gyeongju_location() {
		System.out.println("gyeongju_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/gyeongju_location")
	public ResponseEntity<Map<String, Object>> gyeongju_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/gyeongju_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	
	@GetMapping("/jeju_location") // GET 요청을 처리하는 메서드 추가
	public void jeju_location() {
		System.out.println("jeju_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/jeju_location")
	public ResponseEntity<Map<String, Object>> jeju_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/jeju_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/jeonju_location") // GET 요청을 처리하는 메서드 추가
	public void jeonju_location() {
		System.out.println("jeonju_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/jeonju_location")
	public ResponseEntity<Map<String, Object>> jeonju_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/jeonju_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/kangreung_location") // GET 요청을 처리하는 메서드 추가
	public void kangreung_location() {
		System.out.println("kangreung_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/kangreung_location")
	public ResponseEntity<Map<String, Object>> kangreung_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/kangreung_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/suwon_location") // GET 요청을 처리하는 메서드 추가
	public void suwon_location() {
		System.out.println("suwon_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/suwon_location")
	public ResponseEntity<Map<String, Object>> suwon_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/suwon_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/incheon_location") // GET 요청을 처리하는 메서드 추가
	public void incheon_location() {
		System.out.println("incheon_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/incheon_location")
	public ResponseEntity<Map<String, Object>> incheon_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/incheon_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	
	@GetMapping("/yeosu_location") // GET 요청을 처리하는 메서드 추가
	public void yeosu_location() {
		System.out.println("yeosu_location 페이지에 접속했습니다.");
	}
	
	@PostMapping("/yeosu_location")
	public ResponseEntity<Map<String, Object>> yeosu_location(@RequestBody List<Map<String, Object>> requestBody) {
		// 요청 처리 후 이동할 URL
		String redirectUrl = "/sample/yeosu_location";

		// 처리 코드...

		// JSON 응답을 생성합니다.
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("redirectUrl", redirectUrl);
		jsonResponse.put("requestData", requestBody); // 요청 데이터를 응답에 포함시킵니다.

		// ResponseEntity를 사용하여 JSON 응답을 반환합니다.
		return ResponseEntity.ok(jsonResponse);
	}
	


	
}