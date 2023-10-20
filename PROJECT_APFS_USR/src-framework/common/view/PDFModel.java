package common.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.util.FileUtils;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("rawtypes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PDFModel {
	
	private String imagePath;
	
	private Map<String, Object> data;
	
	private Map<String, List> lists;
	
	private Map<String, IImageProvider> images;
	
	public void putList(String key, List list) {
		if (lists == null)
			lists = new HashMap<String, List>();
		
		lists.put(key, list);
	}
	
	public void putImage(String key, IImageProvider provider) {
		if (images == null)
			images = new HashMap<String, IImageProvider>();
		
		images.put(key, provider);
	}
	public void putImage(String key, String imageName) {
		if (images == null)
			images = new HashMap<String, IImageProvider>();
		
		IImageProvider provider = FileUtils.getImageProvider(imagePath, imageName);
		
		if (provider != null) {
			images.put(key, provider);
		}
	}
}
