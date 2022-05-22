package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType,Integer> {
    
    // ubaciti rating i maxPersonNum kao atribute pa dodati i to
    @Query(value = "SELECT * FROM entities", nativeQuery = true)
    public List<EntityType> getByParams();

    public List<EntityType> findAllByPriceLessThan(Double price);

}
