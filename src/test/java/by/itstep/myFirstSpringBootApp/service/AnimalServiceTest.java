package by.itstep.myFirstSpringBootApp.service;

import by.itstep.myFirstSpringBootApp.domain.Animal;
import by.itstep.myFirstSpringBootApp.repository.AnimalRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalServiceTest {

    @Autowired
    AnimalRepo animalRepo;

//    Animal animal = new Animal("sdfsdf", "dsfsdf", "sdf");
//    Animal animal2 = new Animal("sdfsdfs", "ddsfsdf", "sffffdf");

    @Test
    public void addAnimal() {
//        animalRepo.save(animal);
//        animalRepo.save(animal2);
    }

    @Test
    public void findAllAnimals() {
    }
}