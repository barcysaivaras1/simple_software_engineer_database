package com.aivarasz;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class SoftwareEngineerServiceTest {

    @Mock
    private SoftwareEngineerRepository softwareEngineerRepository;

    @InjectMocks
    private SoftwareEngineerService softwareEngineerService;

    @Test
    void getAllSoftwareEngineers_success() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(1,"Mike", "java, python");
        SoftwareEngineer softwareEngineer2 = new SoftwareEngineer(2,"Aiv", "java, Spring Boot, javascript");

        //When
        given(softwareEngineerRepository.findAll())
                .willReturn(List.of(softwareEngineer1, softwareEngineer2));
        List<SoftwareEngineer> softwareEngineersList = softwareEngineerService.getAllSoftwareEngineers();

        //Then
        assertThat(softwareEngineersList).isNotNull();
        assertThat(softwareEngineersList.size()).isEqualTo(2);
    }

    @Test
    void getSoftwareEngineerById_success() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(1,"Mike", "java, python");

        //When
        given(softwareEngineerRepository.findById(1)).willReturn(Optional.of(softwareEngineer1));
        SoftwareEngineer expectedSoftwareEngineer = softwareEngineerService.getSoftwareEngineerById(1);

        //Then
        assertThat(expectedSoftwareEngineer).isNotNull();
        assertThat(expectedSoftwareEngineer.getName()).isEqualTo(softwareEngineer1.getName());
    }

    @Test
    void getSoftwareEngineerByName_success() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(1,"Mike", "java, python");

        //When
        given(softwareEngineerRepository.findAll()).willReturn(List.of(softwareEngineer1));
        SoftwareEngineer expectedSoftwareEngineer = softwareEngineerService.getSoftwareEngineerByName("Mike").getFirst();

        //Then
        assertThat(expectedSoftwareEngineer).isNotNull();
        assertThat(expectedSoftwareEngineer.getName()).isEqualTo(softwareEngineer1.getName());
    }

    @Test
    void insertSoftwareEngineer_success() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(1,"Mike", "java, python");

        //When
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer1);

        //Then
        verify(softwareEngineerRepository).save(softwareEngineer1);
    }

    @Test
    void deleteSoftwareEngineer_success() {
        //When
        given(softwareEngineerRepository.existsById(1)).willReturn(true);
        softwareEngineerService.deleteSoftwareEngineer(1);

        //Then
        verify(softwareEngineerRepository).existsById(1);
        verify(softwareEngineerRepository).deleteById(1);

    }

    @Test
    void deleteSoftwareEngineerById_NotFound() {
        //When
        given(softwareEngineerRepository.existsById(1)).willReturn(false);
        try {
            softwareEngineerService.deleteSoftwareEngineer(1);
        }
        //Then
        catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Software Engineer with id 1 not found");
        }
        verify(softwareEngineerRepository).existsById(1);
        verify(softwareEngineerRepository, never()).deleteById(1);
    }

    @Test
    void updateSoftwareEngineer_success() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(2,"Mike", "java, python, c#");
        SoftwareEngineer expectedSoftwareEngineer = new SoftwareEngineer(1,"Mike", "java, python, c#");


        //When
        given(softwareEngineerRepository.existsById(1)).willReturn(true);
        softwareEngineerService.updateSoftwareEngineer(1, softwareEngineer1);

        //Then
        ArgumentCaptor<SoftwareEngineer> softwareEngineerArgumentCaptor = ArgumentCaptor.forClass(SoftwareEngineer.class);

        verify(softwareEngineerRepository).existsById(1);
        verify(softwareEngineerRepository).save(softwareEngineerArgumentCaptor.capture());

        SoftwareEngineer capturedSoftwareEngineer = softwareEngineerArgumentCaptor.getValue();
        assertThat(expectedSoftwareEngineer.getId()).isEqualTo(capturedSoftwareEngineer.getId());
        assertThat(expectedSoftwareEngineer.getTechStack()).isEqualTo(capturedSoftwareEngineer.getTechStack());
    }

    @Test
    void updateSoftwareEngineer_NotFound() {
        //Given
        SoftwareEngineer softwareEngineer1 = new SoftwareEngineer(2,"Mike", "java, python, c#");
        SoftwareEngineer expectedSoftwareEngineer = new SoftwareEngineer(1,"Mike", "java, python, c#");


        //When
        given(softwareEngineerRepository.existsById(1)).willReturn(false);
        try {
            softwareEngineerService.updateSoftwareEngineer(1, softwareEngineer1);
        }
        //Then
        catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Software Engineer with id 1 not found");
        }
        verify(softwareEngineerRepository).existsById(1);
        verify(softwareEngineerRepository, never()).save(expectedSoftwareEngineer);
    }
}
