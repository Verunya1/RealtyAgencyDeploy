package app.realtyagency.repository;

import app.realtyagency.entity.RealtyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealtyImageRepository extends JpaRepository<RealtyImage, Long> {
    List<RealtyImage> getAllByRealtyId(Long realtyId);
}