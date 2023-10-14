package app.realtyagency.service;

import app.realtyagency.entity.Realty;
import app.realtyagency.entity.RealtyImage;
import app.realtyagency.repository.RealtyImageRepository;
import app.realtyagency.repository.RealtyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealtiesServiceTest {

    @Mock
    RealtyRepository realtyRepository;

    @Mock
    RealtyImageRepository imageRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    RealtiesService realtiesService;


    private List<Realty> getRealty() {
        Realty firstRealty = new Realty();
        Realty secondRealty = new Realty();
        firstRealty.setId(1L);
        firstRealty.setUserId(1L);
        firstRealty.setPrice(100000000);
        firstRealty.setType("квартира");
        firstRealty.setDescription("продать");
        secondRealty.setId(2L);
        secondRealty.setUserId(1L);
        secondRealty.setPrice(30000000);
        secondRealty.setType("квартира");
        secondRealty.setDescription("продать");
        return List.of(firstRealty, secondRealty);
    }

    @Test
    void getAll() {
        List<Realty> realties = getRealty();

        when(realtyRepository.findAll()).thenReturn(realties);

        List<Realty> result = realtiesService.getAll();

        Assertions.assertNotNull(result);
    }


    @Test
    void deleteRealty() {

        Long id = 1L;
        List<RealtyImage> realtyImages = new ArrayList<>();
        when(imageRepository.getAllByRealtyId(id)).thenReturn(realtyImages);

        realtiesService.deleteRealty(id);

        verify(imageRepository, times(1)).getAllByRealtyId(id);
        verify(imageRepository, times(1)).deleteAll(anyList());
        verify(realtyRepository, times(1)).deleteById(id);

    }

    @Test
    void getRealtyByID() {

        Long id = 1L;
        Realty realty = getRealty().get(0);
        when(realtyRepository.findById(id)).thenReturn(Optional.of(realty));

        Realty result = realtiesService.getRealtyByID(id);

        assertEquals(realty, result);
        verify(realtyRepository, times(1)).findById(id);
    }

    @Test
    public void testSaveRealty() throws IOException {
        Realty realty = new Realty();
        realty.setId(1L);

        MockMultipartFile file1 = new MockMultipartFile("file1", "file1.jpg", "image/jpeg", new byte[10]);
        MockMultipartFile file2 = new MockMultipartFile("file2", "file2.jpg", "image/jpeg", new byte[10]);
        MockMultipartFile file3 = new MockMultipartFile("file3", "file3.jpg", "image/jpeg", new byte[10]);

        Mockito.when(realtyRepository.save(realty)).thenReturn(realty);
        Mockito.when(fileService.saveFile(Mockito.anyString(), Mockito.any(MockMultipartFile.class))).thenReturn("image_path");

        Realty savedRealty = realtiesService.saveRealty(realty, file1, file2, file3);

        assertEquals(realty, savedRealty);
        verify(realtyRepository, times(1)).save(realty);
        verify(imageRepository, times(1)).saveAll(Mockito.anyList());
        verify(fileService, times(3)).saveFile(Mockito.anyString(), Mockito.any(MockMultipartFile.class));
    }
}
