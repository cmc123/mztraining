package cn.mezeron.common.util.upload;

import javax.activation.DataHandler;  

public class Resume {
	private String candidateName;   
    private String resumeFileType;   
    private DataHandler resume;  
    public String getCandidateName() {  
        return candidateName;  
    }  
    public void setCandidateName(String candidateName) {  
        this.candidateName = candidateName;  
    }  
    public String getResumeFileType() {  
        return resumeFileType;  
    }  
    public void setResumeFileType(String resumeFileType) {  
        this.resumeFileType = resumeFileType;  
    }  
    public DataHandler getResume() {  
        return resume;  
    }  
    public void setResume(DataHandler resume) {  
        this.resume = resume;  
    }  
}
