package com.sist.web.vo;

public class QnABoard {
   
   private String qSeq;
   private String title;
   private String content;
   private String status;
   private String fileSrc;
   private String writer;
   private String regdate;
   private String reply;
   private String profilePic;

   public String getqSeq() {
      return qSeq;
   }
   public void setqSeq(String qSeq) {
      this.qSeq = qSeq;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }
   public void setContent(String content) {
      this.content = content;
   }

   public String getStatus() {
      return status;
   }
   public void setStatus(String status) {
      this.status = status;
   }
   
   public String getFileSrc() {
	   return fileSrc;
	}
	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}
	public String getWriter() {
	      return writer;
   }
   public void setWriter(String writer) {
      this.writer = writer;
   }
   public String getRegdate() {
      return regdate;
   }
   public void setRegdate(String regdate) {
      this.regdate = regdate;
   }
   
   public String getReply() {
      return reply;
   }
   public void setReply(String reply) {
      this.reply = reply;
   }
   
   
   public String getProfilePic() {
	return profilePic;
	}
   
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
@Override
   public String toString() {
      return "QnABoard [qSeq=" + qSeq + ", title=" + title + ", content=" + content
            + ", status=" + status + ", fileSrc=" + fileSrc + ", writer=" + writer
            + ", regdate=" + regdate + "]";
   }
   
   

}