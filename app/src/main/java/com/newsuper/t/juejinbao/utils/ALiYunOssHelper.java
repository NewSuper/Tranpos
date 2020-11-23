package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.ALiYunOssEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.ResponseEntity;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.io.File;
import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class ALiYunOssHelper {
    private OSS oss;
    private static Context mContext;

    private String objectKey;
    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private String bucket = "jjlmobile";
    private ALiYunOssHelper(){}
    private static ALiYunOssHelper instance;
    public static ALiYunOssHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new ALiYunOssHelper();
        }
        return instance;
    }

    public void uploadFile(String dirName,File file,final OssUpCallback callback) {
        Log.v("ossupload",file.getPath());
        if(!file.exists()) {
            ToastUtils.getInstance().show(mContext,"文件不存在");
        }
        initOss(dirName,file,callback);
    }

    public void initOss(String dirName,final File file,final OssUpCallback callback) {
        try {
//            String[] split = file.getName().split("\\.");
//            objectKey = "activity" + File.separator + dirName + File.separator + split[0].hashCode() + "." + split[1];
            int index = file.getName().lastIndexOf(".");
            objectKey = "activity" + File.separator + dirName + File.separator + file.getName().substring(0,index).hashCode() + "." + file.getName().substring(index + 1);
        } catch (Exception e) {
            objectKey = "activity" + File.separator + dirName + File.separator + file.getName();
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();

        rx.Observable<ResponseEntity<ALiYunOssEntity>> observable = RetrofitManager.getInstance(mContext).create(ApiService.class).
                initOss(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<ResponseEntity<ALiYunOssEntity>>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ResponseEntity<ALiYunOssEntity>>() {
            @Override
            public void next(ResponseEntity<ALiYunOssEntity> responseEntity) {
                ALiYunOssEntity data = responseEntity.getData();
                getOss(data,file,callback);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                callback.failVideo("");
            }
        }, mContext, false);
        RetrofitManager.getInstance(mContext).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    private void getOss(ALiYunOssEntity data,File file,final OssUpCallback callback) {
        if(data == null || TextUtils.isEmpty(data.getSecurityToken())) {
            callback.failVideo("获取oss配置失败");
        }else {
            //使用OSSAuthCredentialsProvider token过期可以及时更新
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data.getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken());
            //该配置类如果不设置，会有默认配置，具体可看该类
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(10 * 1000);// 连接超时，默认15秒
            conf.setSocketTimeout(10 * 1000);// socket超时，默认15秒
            conf.setMaxConcurrentRequest(5);// 最大并发请求数，默认5个
            conf.setMaxErrorRetry(2);// 失败后最大重试次数，默认2次
            oss = new OSSClient(mContext.getApplicationContext(), endpoint, credentialProvider);

            upVideo(data, file, callback);
        }
    }

    public void upVideo(ALiYunOssEntity data,File file,final OssUpCallback callback) {
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, file.getAbsolutePath());

        // 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                callback.inProgress(currentSize,totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                callback.successVideo(oss.presignPublicObjectURL(bucket,objectKey));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                callback.failVideo("上传失败");
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public interface OssUpCallback {
        void successVideo(String video_url);
        void failVideo(String msg);
        void inProgress(long progress, long zong);
    }
}
