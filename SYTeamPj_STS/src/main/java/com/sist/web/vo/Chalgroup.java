package com.sist.web.vo;

public class Chalgroup {
   private String gid;
   private String title;
   private String content;
   private String admin;

   private String kind;
   private String likeit;
   private String filesrc;
   
   private String startd;
   private String endd;
   
   public String getStartd() {
      return startd;
   }
   public void setStartd(String startd) {
      this.startd = startd;
   }
   public String getEndd() {
      return endd;
   }
   public void setEndd(String endd) {
      this.endd = endd;
   }
   public String getGid() {
      return gid;
   }
   public void setGid(String gid) {
      this.gid = gid;
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
   public String getAdmin() {
      return admin;
   }
   public void setAdmin(String admin) {
      this.admin = admin;
   }
   public String getKind() {
      return kind;
   }
   public void setKind(String kind) {
      this.kind = kind;
   }
   public String getLikeit() {
      return likeit;
   }
   public void setLikeit(String likeit) {
      this.likeit = likeit;
   }
   public String getFilesrc() {
      return filesrc;
   }
   public void setFilesrc(String filesrc) {
      this.filesrc = filesrc;
   }
   @Override
   public String toString() {
      return "Chalgroup [gid=" + gid + ", title=" + title + ", content=" + content + ", admin=" + admin + ", kind="
            + kind + ", likeit=" + likeit + ", filesrc=" + filesrc + ", startd=" + startd + ", endd=" + endd + "]";
   }

   

   
   
}