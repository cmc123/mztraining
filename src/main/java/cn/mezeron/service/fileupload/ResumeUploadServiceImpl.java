package cn.mezeron.service.fileupload;

import cn.mezeron.common.util.upload.Resume;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  
import javax.activation.DataHandler;

import org.springframework.stereotype.Service; 

@Service("resumeUploadService")
public class ResumeUploadServiceImpl implements ResumeUploadService {

	public void uploadResume(Resume resume) {
		System.out.println("1");  
        DataHandler handler = resume.getResume();   
        try {   
            System.out.println("2");  
        InputStream is = handler.getInputStream();   
        OutputStream os = new FileOutputStream(new File("F:\\"   
        + resume.getCandidateName() +"."+   
        resume.getResumeFileType()));   
        byte[] b = new byte[100000];   
        int bytesRead = 0;   
        while ((bytesRead = is.read(b)) != -1) {   
        os.write(b, 0, bytesRead);   
        }   
        System.out.println("3");  
        os.flush();   
        os.close();   
        is.close();   
        } catch (IOException e){  
            e.printStackTrace();          
        }  
	}

}
