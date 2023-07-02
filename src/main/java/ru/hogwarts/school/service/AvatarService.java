package ru.hogwarts.school.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }
    public void uploadAvatar(Long studentId, MultipartFile avatarImage) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Path filePath = Path.of("avatarsDir", studentId + "." + getExtension(avatarImage.getOriginalFilename()));
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectory(filePath.getParent());
        }
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarImage.getInputStream();
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId)
                .orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarImage.getSize());
        avatar.setMediaType(avatarImage.getContentType());
        avatar.setData(avatarImage.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
