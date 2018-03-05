package com.aladdin.oss;

import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.properties.OssProperties;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

@Service
public class OssServiceImpl implements OssService {
	
	@Autowired
	private OSSClient ossClient;
	
	@Autowired
	private OssProperties ossProperties;
	
	@Override
	public PutObjectResult upload(String key, File file) {
		PutObjectResult result = ossClient.putObject(ossProperties.getBucket(), key, file);
		return result;
	}

	@Override
	public PutObjectResult upload(String key, InputStream inputStream) {
		PutObjectResult result = ossClient.putObject(ossProperties.getBucket(), key,
				inputStream);
		return result;
	}

	@Override
	public InputStream download(String key) {
		OSSObject object = ossClient.getObject(ossProperties.getBucket(), key);
		InputStream inputStream = object.getObjectContent();
		return inputStream;
	}

}
