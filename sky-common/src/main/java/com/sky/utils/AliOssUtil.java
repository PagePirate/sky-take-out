package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes      文件字节数组
     * @param objectName 对象名称（包含路径）
     * @return 文件访问URL
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 构建文件访问URL
        String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
        log.info("文件上传到:{}", fileUrl);

        return fileUrl;
    }

    /**
     * 生成按日期分类的文件路径
     * 例如：2026/03/image.jpg
     *
     * @param originalFilename 原始文件名
     * @return 带日期路径的对象名称
     */
    public String buildObjectName(String originalFilename) {
        // 获取当前日期
        LocalDate now = LocalDate.now();

        // 格式化日期为年/月
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM"));

        // 生成文件名（使用时间戳防止重名）
        String fileName = System.currentTimeMillis() + "_" + originalFilename;

        // 拼接完整的对象名称（日期路径 + 文件名）
        return datePath + "/" + fileName;
    }
}