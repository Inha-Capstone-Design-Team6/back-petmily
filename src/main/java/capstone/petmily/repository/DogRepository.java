package capstone.petmily.repository;

import java.util.HashMap;
import java.util.Map;

public class DogRepository {

    private static Map<String, String> dogBreedsMap = new HashMap<String, String>();
    private static final DogRepository instance = new DogRepository();

    private DogRepository() {}

    public static DogRepository getInstance() {
        return instance;
    }

    public void save(String breedName, String breedCode) {
        dogBreedsMap.put(breedName, breedCode);
    }

    public String findByBreedName(String breedName) {
        return dogBreedsMap.get(breedName);
    }
}
