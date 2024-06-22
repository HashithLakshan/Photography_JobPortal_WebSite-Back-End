package com.example.Ceylone.Snippers.Back.End.services;

import com.cloudinary.Cloudinary;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import com.example.Ceylone.Snippers.Back.End.entity.User;
import com.example.Ceylone.Snippers.Back.End.repository.PhotographerProfilesRepository;
import com.example.Ceylone.Snippers.Back.End.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;
    private final SiteRollsService siteRollsService;
    private final PhotographerService photographerService;
    private final CatogoryService catogoryService;

    private final PhotographerProfilesRepository photographerProfilesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotographerProfilesRepository photographerProfileRepo;

    public CloudinaryService(SiteRollsService siteRollsService, PhotographerService photographerService, CatogoryService catogoryService, PhotographerProfilesRepository photographerProfilesRepository) {
        this.siteRollsService = siteRollsService;
        this.photographerService = photographerService;
        this.catogoryService = catogoryService;
        this.photographerProfilesRepository = photographerProfilesRepository;
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dxjvdwi1y");
        valuesMap.put("api_key", "336927255747395");
        valuesMap.put("api_secret", "DTrQLQhumrZgGQFQrk5mJW_r6To");
        cloudinary = new Cloudinary(valuesMap);
    }

    public ResponseEntity<?> saveImgs(MultipartFile img1, MultipartFile img2, String profileID) throws IOException {
        String image1 = null;
        String image2 = null;

        int randomID = Integer.parseInt(profileID);

        try {
            if (img1 != null){
                File file = convert(img1);
                Map result = cloudinary.uploader().upload(file, Collections.emptyMap());
                image1=(String) result.get("url");
                System.out.println("img1:" + image1);
            }
            if (img2 != null){
                File file = convert(img2);
                Map result = cloudinary.uploader().upload(file, Collections.emptyMap());
                image2=(String) result.get("url");
                System.out.println("img2:" + image2);
            }

            PhotographersProfiles photographersProfiles= photographerProfilesRepository.findById(Long.valueOf(randomID)).get();
            if(photographersProfiles!=null){
                photographersProfiles.setProfileName(photographersProfiles.getProfileName());
                photographersProfiles.setAbout(photographersProfiles.getAbout());
                photographersProfiles.setOfficialEmail(photographersProfiles.getOfficialEmail());
                photographersProfiles.setOfficialPhoneNo(photographersProfiles.getOfficialPhoneNo());
                photographersProfiles.setProfilePhotoImageUrl(image1);
                photographersProfiles.setCoverPhotoImageUrl(image2);
                photographersProfiles.setCommonStatus(photographersProfiles.getCommonStatus());
                photographersProfiles.setCommonStatus(photographersProfiles.getCommonStatus());
                photographerProfileRepo.save(photographersProfiles);
            }
            return new ResponseEntity<>(photographersProfiles, HttpStatus.CREATED);
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            String errorMessage = "Maximum upload size exceeded please try with Different Image";
            return new ResponseEntity<>(errorMessage, HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred during image upload.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public ResponseEntity<?> upload(MultipartFile image1, String userID) throws IOException {
        User user=new User();
        String image = null;

        try {
            if (image1 != null){
                File file = convert(image1);
                Map result = cloudinary.uploader().upload(file, Collections.emptyMap());
                image=(String) result.get("url");
                System.out.println("img1:" + image);
            }


            User exitsUser= userRepository.findById(Long.valueOf(userID)).get();
            if(exitsUser!=null){
                exitsUser.setUserProfileImgUrl(image);
                userRepository.save(exitsUser);
            }

            return new ResponseEntity<>("Image Saved Success", HttpStatus.CREATED);
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
            String errorMessage = "Maximum upload size exceeded please try with Different Image";
            return new ResponseEntity<>(errorMessage, HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during image upload.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }


    private int genrateRandomID(){
        // Generate random number between min and max (inclusive)
        int min = 1; // Adjust minimum value as needed
        int max = 100; // Adjust maximum value as needed

        Random random = new Random(); // Create a Random object

        int randomNumber = random.nextInt(max - min + 1) + min;

        System.out.println("Your random number: " + randomNumber);
        return randomNumber;
    }
}


