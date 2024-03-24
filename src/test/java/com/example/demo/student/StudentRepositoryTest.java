package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        // Given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamilia", "jamila@gmail.com", LocalDate.of(2010, Month.JANUARY, 5));
        underTest.save(student);

        // When
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);

        // Then
        assertTrue(studentOptional.isPresent()); // Öğrenci var mı diye kontrol et
        assertEquals(email, studentOptional.get().getEmail()); // Öğrencinin e-posta adresi beklenen değere eşit mi diye kontrol et
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        // Given
        String email = "nonexistent@gmail.com"; // Var olmayan bir e-posta adresi
        // Eğer bu e-posta adresine sahip bir öğrenci varsa, test başarısız olmalıdır.

        // When
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);

        // Then
        assertFalse(studentOptional.isPresent());
        System.out.println(studentOptional.isPresent());// Belirli e-posta adresine sahip öğrenci var mı diye kontrol et
    }
}


