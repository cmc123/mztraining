package cn.mezeron.service.fileupload;

import javax.jws.WebMethod;  
import javax.jws.WebParam;  
import javax.jws.WebService;

import cn.mezeron.common.util.upload.Resume;  
  
@WebService  
@javax.xml.ws.soap.MTOM  
public interface ResumeUploadService {  
    @WebMethod  
    public void uploadResume(@WebParam(name = "resume") Resume resume);  
} 