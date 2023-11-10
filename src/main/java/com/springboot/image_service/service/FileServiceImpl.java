package com.springboot.image_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.*;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${aws.s3.bucket-name}")
    private String BUCKET_NAME;

    private final S3Client s3Client;

    @Autowired
    public FileServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public boolean upload(String fileName, InputStream inputStream) throws IOException {
        log.info("--> FileServiceImpl: upload");

        final boolean[] isUploaded = new boolean[]{false};

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                //.acl("public-read")//TODO: software.amazon.awssdk.services.s3.model.S3Exception: The bucket does not allow ACLs
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromInputStream(inputStream, inputStream.available())
        );


        //wait until folder is created and then do some other tasks
        S3Waiter waiter = s3Client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
        waitResponse.matched().response().ifPresent(x -> {
            log.info(String.valueOf(x));
            isUploaded[0] = true;
        });

        log.info("The file " + fileName + " is ready");
        return isUploaded[0];
    }

    @Override
    public boolean download(String keyName) throws IOException {
        log.info("--> FileServiceImpl: download");
        //keyName - is a full path to file on AWS S3

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                //.acl("public-read")//TODO: software.amazon.awssdk.services.s3.model.S3Exception: The bucket does not allow ACLs
                .build();

        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(request);

        String fileName = new File(keyName).getName();

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = responseInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        responseInputStream.close();
        outputStream.close();

        //TODO: add check if the file was downloaded
        return true;
    }

    public byte[] downloadRest(String keyName) {
        log.info("--> FileServiceImpl: downloadRest");
        //keyName - is a full path to file on AWS S3
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                //.acl("public-read")//TODO: software.amazon.awssdk.services.s3.model.S3Exception: The bucket does not allow ACLs
                .build();

        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(request);

        String fileName = new File(keyName).getName();

        byte[] content = null;
        try {
            content = responseInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public boolean delete(String keyName) {
        log.info("--> FileServiceImpl: delete");
        //keyName - is a full path to file on AWS S3

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                //.acl("public-read")//TODO: software.amazon.awssdk.services.s3.model.S3Exception: The bucket does not allow ACLs
                .build();

        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(request);
        log.info("--> deleteObjectResponse=" + deleteObjectResponse);
        log.info("--> deleteObjectResponse.deleteMarker()=" + deleteObjectResponse.deleteMarker());
        //TODO: add check if the file was deleted
        return true;
    }

    @Override
    public void listAll() {
        log.info("--> FileServiceImpl: listAll");

        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket(BUCKET_NAME)
                .build();

        ListObjectsResponse response = s3Client.listObjects(request);
        List<S3Object> s3Objects = response.contents();

        for (S3Object s3Object : s3Objects) {
            log.info("Key: " + s3Object.key());
            log.info("Owner: " + s3Object.owner());
            log.info("Size: " + s3Object.size());
        }
    }
}
