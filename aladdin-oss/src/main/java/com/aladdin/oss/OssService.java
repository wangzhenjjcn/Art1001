package com.aladdin.oss;

import java.io.File;
import java.io.InputStream;

import com.aliyun.oss.model.PutObjectResult;

public interface OssService {

	PutObjectResult upload(String key, File file);

	PutObjectResult upload(String key, InputStream inputStream);

	InputStream download(String key);
	
}