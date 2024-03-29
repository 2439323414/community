package life.majiang.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.proxy-suffix}")
    private String proxySuffix;
    @Value("${ucloud.ufile.expires-durationx}")
    private int expiresDuration;

    public String upload(InputStream fileStream,String mimeType,String fileName){

        String generateFileName = "";
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length>1){
            generateFileName=UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FALL);
        }
        try {
            // 对象相关API的授权器
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, proxySuffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();

                    if (response!=null && response.getRetCode()==0){
                        String url = UfileClient.object(objectAuthorization, config)
                                .getDownloadUrlFromPrivateBucket(generateFileName, bucketName, expiresDuration)
                                .createUrl();
                        return url;
                    }else {
                            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FALL);
                    }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FALL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FALL);
        }
    }
}
