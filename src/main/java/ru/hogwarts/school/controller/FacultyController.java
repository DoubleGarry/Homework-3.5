package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public FacultyDtoOut create(@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.create(facultyDtoIn);
    }

    @GetMapping("{id}")
    public FacultyDtoOut get(@PathVariable long id) {
        return facultyService.get(id);
    }

    @PutMapping("/{id}")
    public FacultyDtoOut update(@PathVariable long id, @RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.update(id, facultyDtoIn);
    }

    @DeleteMapping("{id}")
    public FacultyDtoOut delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/filter")
    public Collection<FacultyDtoOut> filter(@RequestParam String colorOrName) {
        return facultyService.findByColorOrName(colorOrName);
    }

    @GetMapping("/{facultyName}/students")
    public Collection<StudentDtoOut> findAllStudentsOnFaculty(@PathVariable String facultyName) {
        return facultyService.findStudentsByFaculty(facultyName);
    }
}
