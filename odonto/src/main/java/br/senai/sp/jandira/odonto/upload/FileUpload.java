package br.senai.sp.jandira.odonto.upload;

public class FileUpload {

	private String fileName;
	private String base64;
	private String mimeType;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimiType) {
		this.mimeType = mimiType;
	}
	@Override
	public String toString() {
		return "FileUpload [fileName=" + fileName + ", base64=" + base64 + ", mimeType=" + mimeType + "]";
	}
	
	
	
}
