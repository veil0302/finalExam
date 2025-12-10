package com.mysite.sbb.book.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.sbb.book.dto.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookSearchService {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    // 기존: JSON 문자열 그대로 반환
    public String searchBooks(String query) {
        try {
            String text = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book?query=" + text;

            HttpURLConnection conn = (HttpURLConnection) new URL(apiURL).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream()
            ));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response.toString()); // 정상 출력 확인 json
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"API 호출 중 오류 발생\"}";
        }
    }

    public List<BookDto> searchBooksAsDto(String query) {
        List<BookDto> list = new ArrayList<>();

        try {
            String json = searchBooks(query);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode items = root.get("items");

            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    BookDto dto = new BookDto();
                    dto.setTitle(item.get("title").asText());
                    dto.setAuthor(item.get("author").asText());
                    dto.setPublisher(item.get("publisher").asText());
                    dto.setImage(item.get("image").asText());
                    dto.setDescription(item.get("description").asText());
                    dto.setLink(item.get("link").asText());
                    // Category를 Map 유틸을 통해 문자열로 변환 후 설저ㅇ
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<BookDto> getRecommendedBooks() {
        return searchBooksAsDto("추천 도서");
    }

}
