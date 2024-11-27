package capstone.petmily.repository;

import java.util.HashMap;
import java.util.Map;

public class CatRepository {

    private static Map<String, String> catBreedsMap = new HashMap<String, String>();
    private static final CatRepository instance = new CatRepository();

    private CatRepository() {}

    public static CatRepository getInstance() {
        return instance;
    }

    public void save(String breedName, String breedCode) {
        catBreedsMap.put(breedName, breedCode);
    }

    public String findByBreedName(String breedName) {
        return catBreedsMap.get(breedName);
    }
}
