package com.aladdin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zip", locations = "classpath:conf/zip.properties")
public class ZipProperties {
	
	private String sourceUrl;
	
	private String destinationUrl;
	
	private String handlePicUrl;

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getDestinationUrl() {
		return destinationUrl;
	}

	public void setDestinationUrl(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}
	
	public String getHandlePicUrl() {
		return handlePicUrl;
	}

	public void setHandlePicUrl(String handlePicUrl) {
		this.handlePicUrl = handlePicUrl;
	}

}
