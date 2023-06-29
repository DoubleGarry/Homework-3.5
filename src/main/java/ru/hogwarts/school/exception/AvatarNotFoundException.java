package ru.hogwarts.school.exception;

public class AvatarNotFoundException extends RuntimeException{
    private final long id;

    public AvatarNotFoundException(Long studentId) {
        this.id = studentId;
    }

    public String getMessage() {
        return "У студента с ID:" + id + "аватарка не найдена.";
    }
}
