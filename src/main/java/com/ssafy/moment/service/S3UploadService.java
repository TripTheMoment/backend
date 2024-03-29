package com.ssafy.moment.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String defaultUrl;

    public String upload(MultipartFile uploadFile, String path) {
        String origName = uploadFile.getOriginalFilename();
        String keyName;
        try {
            // 파일 확장자 추출
            final String ext = origName.substring(origName.lastIndexOf('.'));
            // 파일이름 암호화
            final String fileName = getUuid() + ext;
            // 파일 객체 생성
            // System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
            File file = new File(System.getProperty("user.dir") + "/" + fileName);

            keyName = path + "/" + fileName;
            // 파일 변환
            uploadFile.transferTo(file);
            // S3 파일 업로드
            uploadOnS3(path, keyName, file);

            // 파일 삭제
            file.delete();
        } catch (StringIndexOutOfBoundsException e) {
            keyName = null;
            log.error("uploadService.upload() : "+e.getMessage());
        } catch (IOException e) {
            keyName = null;
            log.error("uploadService.upload() : "+e.getMessage());
        }
        return keyName;
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void uploadOnS3(String path, final String keyName, final File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, keyName, file);
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void delete(String keyName) {
        amazonS3Client.deleteObject(bucket, keyName);
    }

}