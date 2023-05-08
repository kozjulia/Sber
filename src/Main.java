import model.City;
import util.Load;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Long, City> cities = new HashMap<>(Load.loadFromFile());
        Load.sortByName(cities);
        Load.sortByDistrictAndName(cities);
        Load.findMaxPopulation(cities);
        Load.findAmountCities(cities);
    }

}