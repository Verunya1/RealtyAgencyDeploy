package app.realtyagency.service;

import app.realtyagency.entity.Realty;
import app.realtyagency.entity.RealtyImage;
import app.realtyagency.repository.RealtyImageRepository;
import app.realtyagency.repository.RealtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealtiesService {

    private final RealtyRepository realtyRepository;
    private final RealtyImageRepository realtyImageRepository;
    private final FileService fileService;


    public List<Realty> getAll() {
        return realtyRepository.findAll();
    }

    public List<RealtyImage> getAllImages(Long id) {
        return realtyImageRepository.getAllByRealtyId(id);
    }

        public Realty saveRealty(Realty realty, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        realty = realtyRepository.save(realty);
        realtyImageRepository.saveAll(List.of(
                new RealtyImage(0L, realty.getId(), fileService.saveFile("img" + realty.getId() + "_1", file1)),
                new RealtyImage(0L, realty.getId(), fileService.saveFile("img" + realty.getId() + "_2", file2)),
                new RealtyImage(0L, realty.getId(), fileService.saveFile("img" + realty.getId() + "_3", file3))
        ));
        return realty;
    }

    public void deleteRealty(Long id) {
        realtyImageRepository.deleteAll(realtyImageRepository.getAllByRealtyId(id));
        realtyRepository.deleteById(id);
    }

    public Realty getRealtyByID(Long id) {
        return realtyRepository.findById(id).orElseThrow();
    }
}
