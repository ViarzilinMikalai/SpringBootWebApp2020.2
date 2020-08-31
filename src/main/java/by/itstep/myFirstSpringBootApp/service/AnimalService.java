package by.itstep.myFirstSpringBootApp.service;


import by.itstep.myFirstSpringBootApp.domain.Animal;
import by.itstep.myFirstSpringBootApp.repository.AnimalRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AnimalService {

    final AnimalRepo animalRepo;


    public AnimalService(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }


    public void addAnimal(Animal animal) {
        animalRepo.save(animal);
    }


    public void removeAnimal(Animal animal) {
        if (animal != null) {
            animal.setDeleted(true);
            animalRepo.save(animal);
        }
    }


    public void repairAnimal(Animal animal) {
        if (animal != null) {
            animal.setDeleted(false);
            animalRepo.save(animal);
        }
    }


    public Page<Animal> findAllAnimals(String nameFilter, String speciesFilter, Pageable pageable) {

        if (StringUtils.isEmpty(nameFilter) && StringUtils.isEmpty(speciesFilter)) {
            return animalRepo.findAll(pageable);
        } else {
            return animalRepo.findByNameStartingWithIgnoreCaseAndSpeciesStartingWithIgnoreCase(nameFilter, speciesFilter, pageable);
        }
    }


    public Page<Animal> findAllAnimals(Pageable pageable) {
        return animalRepo.findAll(pageable);
    }
}