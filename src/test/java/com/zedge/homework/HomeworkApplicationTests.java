package com.zedge.homework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeworkApplicationTests {
	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testProtectedRoutes_shouldReturn403() throws Exception {
		ResponseEntity<String> resArtists = this.call("/artists");
		assertTrue(resArtists.getStatusCode().is4xxClientError());

		ResponseEntity<String> resAlbums = this.call("/artists/123/albums/top5");
		assertTrue(resAlbums.getStatusCode().is4xxClientError());
	}

	private ResponseEntity<String> call(String uri) throws Exception {
		return restTemplate.getForEntity(new URL("http://localhost:" + port + uri).toString(), String.class);
	}
}
