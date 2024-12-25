package service;

import data.dao.UserDAOImpl;
import data.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class AdminStatisticsService {
    private final UserDAOImpl userDAO;

    public AdminStatisticsService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    // Total number of users
    public int getTotalNumberOfUsers() {
        return userDAO.getAllUsers().size();
    }

    // Get the most dominant fields of work (top 15)
    public Map<String, Long> getTop15FieldsOfWork() {
        List<User> users = userDAO.getAllUsers();

        return users.stream()
                .collect(Collectors.groupingBy(User::getFieldOfWork, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(15)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // Age distribution for bar chart
    public Map<String, Long> getAgeDistribution() {
        List<User> users = userDAO.getAllUsers();

        return users.stream()
                .collect(Collectors.groupingBy(user -> {
                    int age = user.getAge();
                    if (age < 20) return "Under 20";
                    else if (age < 30) return "20-29";
                    else if (age < 40) return "30-39";
                    else if (age < 50) return "40-49";
                    else return "50+";
                }, Collectors.counting()));
    }
}
