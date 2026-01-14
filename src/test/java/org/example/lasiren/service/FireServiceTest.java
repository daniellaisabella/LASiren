package org.example.lasiren.service;

import org.example.lasiren.exception.NotFoundException;
import org.example.lasiren.model.Fire;
import org.example.lasiren.model.Siren;
import org.example.lasiren.model.Status;
import org.example.lasiren.repository.FireRepository;
import org.example.lasiren.repository.SirenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    FireRepository fireRepository;

    @Mock
    SirenRepository sirenRepository;

    @InjectMocks
    FireService fireService;

    @Test
    void closeFire_shouldCloseFireAndResetSirens() {
        // Arrange
        Fire fire = new Fire();
        fire.setId(1L);
        fire.setClosed(false);

        Siren siren1 = new Siren();
        siren1.setStatus(Status.FARE);
        siren1.setFire(fire);

        Siren siren2 = new Siren();
        siren2.setStatus(Status.FARE);
        siren2.setFire(fire);

        when(fireRepository.findById(1L)).thenReturn(Optional.of(fire));
        when(sirenRepository.findByFire(fire)).thenReturn(List.of(siren1, siren2));

        // Act
        Fire result = fireService.closeFire(1L);

        // Assert
        assertTrue(result.isClosed());

        assertEquals(Status.FRED, siren1.getStatus());
        assertNull(siren1.getFire());

        assertEquals(Status.FRED, siren2.getStatus());
        assertNull(siren2.getFire());

        verify(fireRepository).save(fire);
        verify(sirenRepository).saveAll(List.of(siren1, siren2));
    }

    @Test
    void closeFire_shouldThrowNotFoundException_ifFireDoesNotExist() {
        // Arrange
        when(fireRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NotFoundException.class, () -> {
            fireService.closeFire(99L);
        });

        verify(fireRepository, never()).save(any());
        verify(sirenRepository, never()).saveAll(any());
    }

}