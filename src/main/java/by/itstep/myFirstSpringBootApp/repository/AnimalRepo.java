package by.itstep.myFirstSpringBootApp.repository;


import by.itstep.myFirstSpringBootApp.domain.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepo extends CrudRepository<Animal, Long> {

//    Animal findBySpecies();
    Page<Animal> findAll(Pageable pageable);

    Page<Animal> findByNameStartingWithIgnoreCaseAndSpeciesStartingWithIgnoreCase(
            @Param("name") String name,
            @Param("species") String species,
            Pageable pageable
    );
}
