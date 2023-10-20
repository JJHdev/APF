package common.file;

import org.springframework.http.MediaType;

public enum FileDocument {
	
	GIF ("gif" ,MediaType.IMAGE_GIF,"image/gif","GIF Image"),
	JPG ("jpg" ,MediaType.IMAGE_JPEG,"image/jpeg","JPEG Image"),
	JPEG("jpeg",MediaType.IMAGE_JPEG,"image/jpeg","JPEG Image"),
	PNG ("png" ,MediaType.IMAGE_PNG,"image/png","PNG Image"),
	PDF ("pdf" ,MediaType.APPLICATION_PDF,"application/pdf","Adobe Document Format"),
	TXT ("txt" ,MediaType.TEXT_PLAIN,"text/plain","Text"),
	CSV ("csv" ,null,"text/csv","Comma-separated Values"),
	HWP ("hwp" ,null,"application/x-hwp", "한글 문서"),
	DOC ("doc" ,null,"application/doc","Microsoft Word"), // "application/msword"
	XLS ("xls" ,null,"application/xls","Microsoft Excel"), // ,"application/vnd.ms-excel"
	PPT ("ppt" ,null,"application/ppt","Microsoft PowerPoint"), // "application/vnd.ms-powerpoint"
	DOCX("docx",null,"application/docx","Microsoft Word"),
	XLSX("xlsx",null,"application/xlsx","Microsoft Excel"),
	PPTX("pptx",null,"application/pptx","Microsoft PowerPoint"),
	SVG ("svg" ,null,"image/svg+xml","SVG Image")
	;
	
    private String extension;
	private String mimeType;
	private String description;
	private MediaType mediaType;
	
	private FileDocument(String extension, MediaType mediaType, String mimeType, String description) {
		this.extension   = extension;
		this.mediaType   = mediaType;
		this.mimeType    = mimeType;
		this.description = description;
	}
	
	public static FileDocument get(String extension) {
		if (extension == null)
			return null;
		for (FileDocument c : values()) {
			if (extension.equalsIgnoreCase(c.getExtension()))
				return c;
		}
		return null;
	}
	
	public static String getContentMimeType(String extension) {
		FileDocument o = get(extension);
		if (o == null)
			return null;
		return o.getMimeType();
	}

	public static MediaType getContentMediaType(String extension) {
		FileDocument o = get(extension);
		if (o == null)
			return null;
		return o.getMediaType();
	}

	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getMimeType() {
		return mimeType;
	}


	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public MediaType getMediaType() {
		if (mediaType == null)
			return MediaType.parseMediaType(mimeType);
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
}
