package capstone.petmily.service;

import capstone.petmily.repository.CatRepository;
import capstone.petmily.repository.DogRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;

@Service
public class BreedLoadService implements CommandLineRunner {

    private DogRepository dogRepository = DogRepository.getInstance();
    private CatRepository catRepository = CatRepository.getInstance();

    @Override
    public void run(String... args) throws Exception {

        saveBreeds("dog.json");
        saveBreeds("cat.json");
    }

    public void saveBreeds(String breedFileName) {

        ClassPathResource classPathResource = new ClassPathResource(breedFileName);
        JSONParser jsonParser = new JSONParser();

        JSONArray resultArray = null;
        try {
            JSONObject data = (JSONObject) jsonParser.parse(new InputStreamReader(classPathResource.getInputStream(), "UTF-8"));
            JSONObject parseResponse = (JSONObject) data.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject) parseBody.get("items");
            resultArray = (JSONArray) parseItems.get("item");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i< resultArray.size(); i++) {
            JSONObject result = (JSONObject) resultArray.get(i);
            String name = (String) result.get("knm");
            String code = (String) result.get("kindCd");
            if(breedFileName.equals("dog.json"))
                dogRepository.save(name.replace(" ", ""), code);
            else
                catRepository.save(name.replace(" ", ""), code);
        }
    }
}
