package org.example.lasiren.service;

import org.example.lasiren.exception.NotFoundException;
import org.example.lasiren.model.Siren;
import org.example.lasiren.repository.SirenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SirenServiceTest {
    @Mock
    SirenRepository sirenRepository;

    @InjectMocks
    SirenService sirenService;

    @Test
    void findById_shouldReturnSiren_whenExists() {
        Siren siren = new Siren();
        siren.setId(1L);

        when(sirenRepository.findById(1L)).thenReturn(Optional.of(siren));

        Siren result = sirenService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowNotFound_whenNotExists() {
        when(sirenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sirenService.findById(99L);
        });
    }

    @Test
    void delete_shouldCallRepository_whenExists() {
        when(sirenRepository.existsById(1L)).thenReturn(true);

        sirenService.delete(1L);

        verify(sirenRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowNotFound_whenNotExists() {
        when(sirenRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            sirenService.delete(99L);
        });
    }

}