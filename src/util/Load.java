package util;

import model.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Load {

    public static Map<Long, City> loadFromFile() {
        File fileInput = new File("Задача ВС Java Сбер.csv");
        Map<Long, City> cities = new HashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileInput))) {
            while (fileReader.ready()) {
                String[] parts = fileReader.readLine().split(";");
                long id = Long.parseLong(parts[0].trim());
                String name = parts[1].trim();
                String region = parts[2].trim();
                String district = parts[3].trim();
                int population = Integer.parseInt(parts[4].trim());
                String foundation;
                if (parts.length == 6) {
                    foundation = parts[5].trim();
                } else {
                    foundation = "";
                }

                City city = new City(name, region, district, population, foundation);
                cities.put(id, city);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        cities.values().stream()
                .forEach(System.out::println);
        return cities;
    }

    public static void sortByName(Map<Long, City> cities) {
        // Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра
        cities.values().stream()
                .sorted(Comparator.comparing(city -> city.getName().toLowerCase()))
                .forEach(System.out::println);

    }

    public static void sortByDistrictAndName(Map<Long, City> cities) {
        // Сортировка списка городов по федеральному округу и наименованию города внутри каждого
        // федерального округа в алфавитном порядке по убыванию с учетом регистра
        cities.values().stream()
                .sorted(Load::compare)
                .forEach(System.out::println);

    }

    public static void findMaxPopulation(Map<Long, City> cities) {
        //поиск города с наибольшим количеством жителей
        City cityMax = cities.values().stream()
                .sorted((city1, city2) -> city2.getPopulation() - city1.getPopulation())
                .findFirst()
                .get();
        for (Map.Entry<Long, City> city : cities.entrySet()) {
            if (city.getValue().equals(cityMax)) {
                System.out.println(String.format("[%d] = %,3d", city.getKey() - 1, city.getValue().getPopulation()));
                // индекс в массиве начинается с 0
            }
        }
    }

    public static void findAmountCities(Map<Long, City> cities) {
        //определение количество городов в каждом регионе
        Map<String, Integer> amountCities = new HashMap<>();
        int k;
        for (Map.Entry<Long, City> city : cities.entrySet()) {
            k = 0;
            if (amountCities.containsKey(city.getValue().getRegion())) {
                k = amountCities.get(city.getValue().getRegion());
            }
            amountCities.put(city.getValue().getRegion(), k + 1);
        }
        for (Map.Entry<String, Integer> city : amountCities.entrySet()) {
            System.out.println(String.format("%s - %d", city.getKey(), city.getValue()));
        }
    }

    private static int compare(City city1, City city2) {
        int districtCompare = city1.getDistrict().compareTo(city2.getDistrict());
        if (districtCompare != 0) {
            return districtCompare;
        }
        return city1.getName().compareTo(city2.getName());
    }

}