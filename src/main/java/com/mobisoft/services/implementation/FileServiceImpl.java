package com.mobisoft.services.implementation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mobisoft.services.FileServiceI;

@Service
public class FileServiceImpl implements FileServiceI{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//first get the file name
		String name = file.getOriginalFilename();
				
		//randomly file name generated
		String randomID = UUID.randomUUID().toString();
				
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
					
		//create new path 
//		String filePath = path+File.separator+name;
		String filePath = path+File.separator+fileName1;  //create new path randomly
					
		//create folder for images if not exists
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
					
		//upload file on server(copy date on filepath)
		Files.copy(file.getInputStream(), Paths.get(filePath));
					
		//return file name
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName; 
		InputStream is = new FileInputStream(fullPath);
		
		//db logic to return input stream
		return is;
	}

}
