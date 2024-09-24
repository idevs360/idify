package com.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.entities.IdCard;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/idcard")
public class HomeApi {

    private final String uploadDir = "src/main/resources/static/uploads";  // Folder to save images
    private Path pathOfUploadDirectory = Paths.get("src/main/resources/static/uploads").toAbsolutePath();
    
    // A list to store IdCard objects in memory
    private List<IdCard> idCardList = new ArrayList<>();

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam MultipartFile uploadimage,
            @RequestParam String sname,
            @RequestParam String sclass,
            @RequestParam String rollNo,
            @RequestParam String section,
            @RequestParam String phone,
            @RequestParam String address) {

        Map<String, Object> map = new HashMap<>();
        
        try {
            if (uploadimage.isEmpty()) {
                throw new Exception("File not found");
            } 

            // Create upload directory if not exists
            if (!Files.exists(pathOfUploadDirectory)) {
                Files.createDirectories(pathOfUploadDirectory);
            }

            // Define destination file
            File destinationFile = pathOfUploadDirectory.resolve(uploadimage.getOriginalFilename()).toFile();

            // Check if file already exists
            if (destinationFile.exists()) {
                map.put("message", "File already exists: " + destinationFile.getAbsolutePath());
                return new ResponseEntity<>(map, HttpStatus.CONFLICT);  // 409 Conflict
            }

            // Save the file
            uploadimage.transferTo(destinationFile);

            // Create and store the IdCard object in memory
            IdCard idCard = new IdCard();
            idCard.setSName(sname);
            idCard.setSClass(sclass);
            idCard.setRollNo(Integer.parseInt(rollNo));
            idCard.setSection(section);
            idCard.setPhone(phone);
            idCard.setAddress(address);
            idCard.setProfilePictureName(uploadimage.getOriginalFilename());

            // Add to the list
            idCardList.add(idCard);

            map.put("message", "File uploaded successfully at: " + destinationFile.getAbsolutePath());
            map.put("idCard", idCard);  // Include the saved ID card data for response
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	
    @GetMapping("/list")
    public ResponseEntity<?> getFileList() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            // Check if upload directory exists
            if (Files.exists(pathOfUploadDirectory)) {
                
                // Get the file names from the directory
                List<String> fileList = Files.list(pathOfUploadDirectory)
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                
                // Fetch all IdCards from in-memory storage
                //map.put("files", fileList);
                map.put("idCards", idCardList);  // Add the in-memory ID cards to the response

                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                map.put("message", "Something went wrong!");
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/by_roll_no/{rollNo}")
    public ResponseEntity<?> getMethodName(@PathVariable int rollNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		
    		Optional<IdCard> idCardOptional = idCardList.stream()
    			    .filter(id -> id.getRollNo() == rollNo)
    			    .findFirst();
    		
    		if (idCardOptional.isPresent()) {
    		    IdCard idCard = idCardOptional.get();
    		    map.put("idCard", idCard);
    		    
    		} else {
    			map.put("idCard", new IdCard());
    		}
    		return new ResponseEntity<>(map, HttpStatus.OK);
		} 
    	catch (Exception e) {
			e.printStackTrace();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
}
