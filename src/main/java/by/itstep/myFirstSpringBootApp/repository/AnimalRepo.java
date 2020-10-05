package by.itstep.myFirstSpringBootApp.repository;

import by.itstep.myFirstSpringBootApp.domain.Animal;
import by.itstep.myFirstSpringBootApp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    @Override
    Page<Animal> findAll(Pageable pageable);
    Page<Animal> findByNameStartingWithIgnoreCaseAndSpeciesStartingWithIgnoreCase(
            @Param("name") String name,
            @Param("species") String species,
            Pageable pageable
    );
    List<Animal> findByAnimalOwner(User user);
}
