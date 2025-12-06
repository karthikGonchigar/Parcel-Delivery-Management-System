package parcels.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import parcels.demo.entity.Parcels;

public interface MyRepository extends JpaRepository<Parcels, Integer> {

}