package app.realtyagency.repository;

import app.realtyagency.entity.Realty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtyRepository extends JpaRepository<Realty, Long> {
}