package com.aladdin.schedule;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aladdin.oss.OssService;
import com.aladdin.properties.OssProperties;
import com.aladdin.properties.ZipProperties;
import com.aladdin.render.Watcher;
import com.aladdin.render.entity.RenderTask;
import com.aladdin.render.service.RenderTaskService;
import com.aliyun.oss.model.PutObjectResult;

@Component
public class ScheduleTask {
	
	@Autowired
	private ZipProperties zipProperties;
	@Autowired
	private RenderTaskService renderTaskService;
	@Autowired
	private OssService ossService;
	@Autowired
	private OssProperties ossProperties;
	@Autowired
	private Watcher watcher;
	
	static final Integer render_task_complete=2;
	
	@Scheduled(fixedRate=20000)
	public void unzipfile(){
		//System.out.println(zipProperties.getSourceUrl()+"--"+new Date());
		
		List<RenderTask> renderTasks=renderTaskService.findNotRendered();
		if(renderTasks!=null && renderTasks.size()>0){
			
			for(RenderTask task:renderTasks){
				if(task!=null){
					String inputurl=task.getInputUrl();
					
					//下载文件
					InputStream inputStream=ossService.download(inputurl);
					
					//解压文件,返回需要渲染的文件路径
					String unzipurl=unzipFile(inputStream);
					
					//渲染后文件存储路径
					String resturl=zipProperties.getHandlePicUrl()+UUID.randomUUID().toString()+".png";//加工过得文件
					
					//加工文件
					String command = "/home/software/vray/bin/linux_x64/gcc-4.4/vray.bin -sceneFile="+unzipurl+" -imgFile="+resturl+" -display=0 -verboseLevel=4";
					
					String[] cmd = new String[]{"/bin/sh","-c",command};
					Process pro;
					//上传加工后文件
					String uploadurl="";
					try {
						//渲染文件
						pro = Runtime.getRuntime().exec(cmd);
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));   
						String reader="";
						while ((reader=bufferedReader.readLine()) != null){
						 System.out.println("read==="+reader);   
						}
						pro.waitFor();
						bufferedReader.close();
						pro.destroy();
						System.out.println(inputurl+"文件渲染成功");
						
						//上传文件
						File file = new File(resturl);
						String fileName = UUID.randomUUID().toString()+".png";
						PutObjectResult result = ossService.upload(fileName, file);
						
						if (result.getETag() != null) {
							uploadurl = ossProperties.getUrl() + "/" + fileName;
						}

					} catch (Exception e) {
						System.out.println(inputurl+"文件处理失败");
						e.printStackTrace();
						
					}
					
					//保存数据库
					task.setOutputUrl(uploadurl);
					task.setEndTime(new Date());
					task.setState(render_task_complete);
					
					renderTaskService.update(task);
				}
			}
			
		}
		
	}
	
	public void renderfile(RenderTask task){
		
		if(task!=null){
			String inputurl=task.getInputUrl();
			if(StringUtils.isNotEmpty(inputurl)){
				//下载文件
				InputStream inputStream=ossService.download(inputurl);
				//解压文件,返回需要渲染的文件路径
				String unzipurl=unzipFile(inputStream);				
				//渲染后文件存储路径
				String resturl=zipProperties.getHandlePicUrl()+UUID.randomUUID().toString()+".png";
				//加工文件
				String command = "/home/software/vray/bin/linux_x64/gcc-4.4/vray.bin -sceneFile="+unzipurl+" -imgFile="+resturl+" -display=0 -verboseLevel=4";
				
				String[] cmd = new String[]{"/bin/sh","-c",command};
				Process pro;
				//上传加工后文件
				String uploadurl="";
				try {
					//渲染文件
					pro = Runtime.getRuntime().exec(cmd);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));   
					String reader="";
					while ((reader=bufferedReader.readLine()) != null){
					 System.out.println("read==="+reader);   
					}
					pro.waitFor();
					bufferedReader.close();
					pro.destroy();
					System.out.println(inputurl+"文件渲染成功");				
					//上传文件
					File file = new File(resturl);
					String fileName = UUID.randomUUID().toString()+".png";
					PutObjectResult result = ossService.upload(fileName, file);
					
					if (result.getETag() != null) {
						uploadurl = ossProperties.getUrl() + "/" + fileName;
					}
					//保存数据库
					task.setOutputUrl(uploadurl);
					task.setEndTime(new Date());
					task.setState(render_task_complete);					
					renderTaskService.update(task);
				} catch (Exception e) {
					System.out.println(inputurl+"文件处理失败");
					e.printStackTrace();					
				}								
			}			
		}
	}

	//解压文件，返回.vrscene文件路径
	public String unzipFile(InputStream is){		
		File zipfile=new File(zipProperties.getSourceUrl()+UUID.randomUUID()+".zip");	
		String unzipfileurl="";
		try {
			if(!(zipfile.exists())){
				zipfile.createNewFile();
			}
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(zipfile));
			int read=0;
			byte[] buffer=new byte[1024];
			while ((read=is.read(buffer, 0, buffer.length))!=-1) {
				bos.write(buffer, 0, read);
			}			
			bos.flush();
			bos.close();
			is.close();		
			unzipfileurl=unzipfile(zipfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("获取压缩文件失败");
			e.printStackTrace();
		}	
		return 	unzipfileurl;	
	}
	
	//解压文件，返回.vrscene文件路径
	public String unzipfile(File source){	
		String sourcename=source.getName();
		String descDir=zipProperties.getDestinationUrl()+sourcename.substring(0, sourcename.lastIndexOf("."));
		//File source=new File("C:\\Users\\Administrator\\Desktop\\test.zip");		
		File pathFile = new File(descDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}		
		String outPath="";				
		try {
			ZipFile zip = new ZipFile(source);
			for(Enumeration entries = zip.entries();entries.hasMoreElements();){
				ZipEntry entry = (ZipEntry)entries.nextElement();
				String zipEntryName = entry.getName();
				
				String outfilepath= descDir+"/"+zipEntryName;

				if(zipEntryName.endsWith(".vrscene")){
					outPath=outfilepath;
				}
							
				InputStream in = zip.getInputStream(entry);					 
					//判断路径是否存在,不存在则创建文件路径
					File file = new File(outfilepath);
					if(!file.exists()){
						file.createNewFile();
					}
					OutputStream out = new FileOutputStream(outfilepath);
					byte[] buf1 = new byte[1024];
					int len;
					while((len=in.read(buf1))>0){
						out.write(buf1,0,len);
					}	
					in.close();
					out.close();				
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(source.getName()+"文件解压失败");
			e.printStackTrace();
		}
		return outPath;		
	}
	
	@Scheduled(fixedRate=60000)
	public void watch(){
		System.out.println("更新计算机状态:---------------"+new DateTime().toString("YYYY-MM-dd HH:mm:ss"));
		int num = watcher.watch();
		if(num==0){
			for(int i=0;i<3;i++){
				num = watcher.watch();
				if(num>0){break;}
			}
			if(num==0){
				System.out.println("此次更新失败---------------------"+new DateTime().toString("YYYY-MM-dd HH:mm:ss"));
			}
		}
		System.out.println("更新完成:-------------------"+new DateTime().toString("YYYY-MM-dd HH:mm:ss"));
	}
	

	

	
//	public static void main(String[] args) throws Exception {
	
//		String source = args[0];
//		String command = "/home/software/vray/bin/linux_x64/gcc-4.4/vray.bin -sceneFile="+source+" -imgFile='/home/tmp/vray/"+UUID.randomUUID().toString()+".png' -display=0 -verboseLevel=4";
//		System.out.println(command);
//		String[] cmd = new String[]{"/bin/sh","-c",command};
//		Process pro = Runtime.getRuntime().exec(cmd);
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));   
//		String reader="";
//		while ((reader=bufferedReader.readLine()) != null){
//		 System.out.println("read==="+reader);   
//		}
//		pro.waitFor();
//	}
	
}
