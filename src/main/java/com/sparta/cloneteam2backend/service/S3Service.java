package com.sparta.cloneteam2backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.cloneteam2backend.model.Img;
import com.sparta.cloneteam2backend.model.Imgtarget;
import com.sparta.cloneteam2backend.model.Imgtarget;
import com.sparta.cloneteam2backend.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
	private final ImgRepository imgRepository;

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadFile(MultipartFile[] files, String targetDirectory, Long targetId) {
		ObjectMetadata omd = new ObjectMetadata();
		for (MultipartFile file : files) {
			String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.KOREA))
					+ "_" + Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
			if(!(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith("jpeg"))) {
				throw new IllegalArgumentException();
			}
			omd.setContentType(file.getContentType());
			omd.setContentLength(file.getSize());
			omd.setHeader("filename", file.getOriginalFilename());
			try {
				amazonS3.putObject(new PutObjectRequest(bucket + "/" + targetDirectory,
						fileName, file.getInputStream(), omd)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) { throw new IllegalArgumentException(); }
			String imagePath = amazonS3.getUrl(bucket + "/" + targetDirectory, fileName).toString();
			Img image = Img.builder()
					.imgTarget(Imgtarget.valueOf(targetDirectory))
					.targetId(targetId)
					.imgUrl(imagePath)
					.build();
			imgRepository.save(image);
		}
	}

	@Transactional
	public void deleteFile(Imgtarget target, Long targetId) {
		List<Img> imagefiles = imgRepository.findAllByTargetId(target, targetId);
		if(imagefiles.size() != 0) {
			for (Img imagefile : imagefiles) {
				String url = imagefile.getImgUrl();
				amazonS3.deleteObject(bucket, url.split(bucket + "/", 2)[1]);
			}
		}
		imgRepository.deleteAllByTargetId(target, targetId);
	}

	@Transactional
	public void updateFile(List<String> deleteFiles, MultipartFile[] newFiles, String targetDirectory, Long targetId) {
		deleteFiles.forEach(url -> amazonS3.deleteObject(bucket, url.split(bucket + "/", 2)[1]));
		deleteFiles.forEach(imgRepository::deleteByImgUrl);
		uploadFile(newFiles, targetDirectory, targetId);
	}
}