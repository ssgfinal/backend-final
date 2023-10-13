package ssg.com.houssg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ssg.com.houssg.dto.AccommodationOcrDto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class NaverOcrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NaverOcrService.class);
    private static final String API_URL = "https://3snvg02e3l.apigw.ntruss.com/custom/v1/25405/50404fba2102167bd4f06f9ea6f87a3c345290a031c8a8e167a05662b891975c/infer";
    private static final int SUCCESS_RESPONSE = 200; // HTTP OK

    @Value("${naver.ocr.client.secret}")
    private String clientSecret;

    public AccommodationOcrDto callNaverCloudOcr(MultipartFile file) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", clientSecret);

            JSONObject json = new JSONObject();
            json.put("version", "V1");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());

            // 이미지 정보를 JSON 배열에 추가
            JSONArray images = new JSONArray();
            JSONObject image = new JSONObject();
            image.put("format", "png");
            image.put("name", "demo");
            images.put(image);
            json.put("images", images);

            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            byte[] fileBytes = file.getBytes();
            writeMultiPart(wr, postParams, fileBytes, boundary);
            wr.close();

            if (con.getResponseCode() == SUCCESS_RESPONSE) {
                String response = readResponse(con.getInputStream());
                LOGGER.info("OCR Response for file {}: {}", file.getOriginalFilename(), response);

                // OCR 결과를 파싱하여 DTO에 데이터 설정
                AccommodationOcrDto dto = parseOcrResponse(response);
                return dto;
            } else {
                String errorResponse = readResponse(con.getErrorStream());
                LOGGER.error("Error during OCR call for file {}: {}", file.getOriginalFilename(), errorResponse);
                return new AccommodationOcrDto();
            }
        } catch (IOException e) {
            LOGGER.error("Exception during Naver Cloud OCR call for file " + file.getOriginalFilename(), e);
            return new AccommodationOcrDto();
        } finally {
            closeResources(null, null);
        }
    }

    private AccommodationOcrDto parseOcrResponse(String response) {
        AccommodationOcrDto dto = new AccommodationOcrDto();
        try {
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.has("images")) {
                JSONArray images = jsonResponse.getJSONArray("images");
                if (images.length() > 0) {
                    JSONObject image = images.getJSONObject(0);

                    if (image.has("fields")) {
                        JSONArray fields = image.getJSONArray("fields");
                        for (int i = 0; i < fields.length(); i++) {
                            JSONObject field = fields.getJSONObject(i);
                            String name = field.optString("name");
                            String inferText = field.optString("inferText");

                            if ("accomName".equals(name)) {
                                dto.setAccomName(inferText);
                            } else if ("businessNumber".equals(name)) {
                            	String extractedNumber = inferText.replaceAll("[:\\s]+", "");
                                dto.setBusinessNumber(extractedNumber);
                            }
                            // 필요한 다른 항목도 추가할 수 있습니다.
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error parsing OCR response", e);
        }
        return dto;
    }

    private String readResponse(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    private void closeResources(DataOutputStream wr, HttpURLConnection con) {
        try {
            if (wr != null)
                wr.close();
            if (con != null)
                con.disconnect();
        } catch (IOException e) {
            LOGGER.error("Error closing resources", e);
        }
    }

    private void writeMultiPart(DataOutputStream wr, String postParams, byte[] fileBytes, String boundary)
            throws IOException {
        // 텍스트 파트 추가
        wr.writeBytes("--" + boundary + "\r\n");
        wr.writeBytes("Content-Disposition: form-data; name=\"message\"\r\n\r\n");
        wr.writeBytes(postParams + "\r\n");

        // 파일 파트 추가
        wr.writeBytes("--" + boundary + "\r\n");
        wr.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.png\"\r\n");
        wr.writeBytes("Content-Type: image/png\r\n\r\n");
        wr.write(fileBytes);

        // 마지막 바운더리 추가
        wr.writeBytes("\r\n--" + boundary + "--\r\n");
        wr.flush();
    }
}
