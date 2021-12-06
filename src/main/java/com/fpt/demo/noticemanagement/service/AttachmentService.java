package com.fpt.demo.noticemanagement.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import com.fpt.demo.noticemanagement.entity.Attachment;
import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.repository.AttachmentRepository;

/**
 * @author DuyHT7
 */

@Service
public class AttachmentService {

	protected final static Logger LOGGER = LoggerFactory.getLogger(AttachmentService.class);
	@Autowired
	private AttachmentRepository attachmentRepository;

	private final Path root = Paths.get("src/main/resources/attachments").toAbsolutePath().normalize();;

	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			LOGGER.info("Error:{}", e.getMessage());
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	public void addAttachments(List<MultipartFile> attachments, Notice notice) throws NoticeManagementException {
		Attachment entity = new Attachment();
		try {

			attachments.stream().forEach(attachment -> {
				entity.setFileName(attachment.getOriginalFilename());
				entity.setFileSize(String.valueOf(attachment.getSize()));
				entity.setFileType(attachment.getContentType());
				entity.setNotice(notice);
				entity.setFileUrl(this.root.toString() + "\\" + attachment.getOriginalFilename());
				attachmentRepository.save(entity);
				try {
					Files.copy(attachment.getInputStream(), this.root.resolve(attachment.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					LOGGER.info("Could not save the file. Error:{}", e.getMessage());
					throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Can not save attachment files");
		}
	}

	public Resource loadAttachment(String attachmentName) {
		try {
			Path file = root.resolve(attachmentName);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				LOGGER.info("Could not load the file!");
				throw new RuntimeException("Could not load the file!");
			}
		} catch (MalformedURLException e) {
			LOGGER.info("Error:{}", e.getMessage());
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			LOGGER.info("Error:{}", e.getMessage());
			throw new RuntimeException("Could not load the files!");
		}
	}

	public void clearAttachments() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}
}
