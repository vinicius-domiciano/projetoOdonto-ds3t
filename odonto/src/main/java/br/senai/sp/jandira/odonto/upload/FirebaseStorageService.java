package br.senai.sp.jandira.odonto.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {

	@PostConstruct
	private void init() throws IOException {
		if(FirebaseApp.getApps().isEmpty()) {
			InputStream serviceAccount = FirebaseStorageService
					.class
					.getResourceAsStream("/myrecyclerview-bdec0-firebase-adminsdk-9zwxl-eb116c6fbc.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setStorageBucket("myrecyclerview-bdec0.appspot.com")
					.build();
			
			FirebaseApp.initializeApp(options);
		}
	}
	
	public String upload(FileUpload fileUpload) {
		Bucket bucket = StorageClient.getInstance().bucket();
		
		byte[] bytes = Base64.getDecoder().decode(fileUpload.getBase64());
		
		String fileName = fileUpload.getFileName();
		
		Blob blob = bucket.create(fileName, bytes, fileUpload.getMimeType());
		
		blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		
		return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);
	}
	
}
