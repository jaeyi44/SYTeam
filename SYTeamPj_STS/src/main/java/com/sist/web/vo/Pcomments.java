package com.sist.web.vo;

public class Pcomments {
	
	String pcSeq;
	String pcPseq;
	String pcRegdate;
	String pcWriter;
	String pcContent;
	
	public Pcomments() {
		this.pcSeq = null;
		this.pcPseq = null;
		this.pcRegdate = null;
		this.pcWriter = null;
		this.pcContent = null;
	}
	
	public String getPcSeq() {
		return pcSeq;
	}
	public void setPcSeq(String pcSeq) {
		this.pcSeq = pcSeq;
	}
	public String getPcPseq() {
		return pcPseq;
	}
	public void setPcPseq(String pcPseq) {
		this.pcPseq = pcPseq;
	}
	public String getPcRegdate() {
		return pcRegdate;
	}
	public void setPcRegdate(String pcRegdate) {
		this.pcRegdate = pcRegdate;
	}
	public String getPcWriter() {
		return pcWriter;
	}
	public void setPcWriter(String pcWriter) {
		this.pcWriter = pcWriter;
	}
	public String getPcContent() {
		return pcContent;
	}
	public void setPcContent(String pcContent) {
		this.pcContent = pcContent;
	}
	
	

}
