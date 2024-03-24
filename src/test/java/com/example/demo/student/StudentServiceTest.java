package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.isInstanceOf;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;


    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddNewStudent() {
        //given
        Student student = new Student(
                "Jamilia", "jamila@gmail.com", LocalDate.of(2010, Month.JANUARY, 5));
        //when
        underTest.addNewStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        StudentRepository studentRepositoryMock = mock(StudentRepository.class); // Mock StudentRepository oluşturuluyor
        StudentService underTest = new StudentService(studentRepositoryMock);

        Student student = new Student(
                "Jamilia", "jamila@gmail.com", LocalDate.of(2010, Month.JANUARY, 5));

// Eğer belirtilen e-posta adresine sahip bir öğrenci bulunursa, findStudentByEmail metodu bu öğrenciyi içeren bir Optional nesnesi döndürür.
        when(studentRepositoryMock.findStudentByEmail(student.getEmail())).thenReturn(Optional.of(student));

        //when, then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");
        //.save() metotu asla calismasin
        verify(studentRepositoryMock, never()).save(any());

        // Optional.of(student) yerine Optional.empty() kullanarak emailin alınmadığı durumu da test edebilirsiniz
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

    @Test
    @Disabled
    void updateStudent() {
    }

    @Test
    @Disabled
    void testUpdateStudent() {
    }
}