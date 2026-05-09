import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<task> cats = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public void start() {
        cats.add(new task("Kitsune", 4));
        cats.add(new task("Seth", 12));
        cats.add(new task("Toby", 13));

        while (true) {
            printTable();
            System.out.println("\nВыберите действие из меню:");
            System.out.println("Меню: 1-Кормить, 2-Играть, 3-Гулять, n-Добавить кота, n-Следующий день, q-Выход");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) break;

            switch (input.toLowerCase()) {
                case "a" -> addNewCat();
                case "n" -> nextDay();
                case "1", "2", "3" -> applyAction(input);
                default -> System.out.println("Неверная команда!");
            }
        }
    }

    private void printTable() {
        List<task> sorted = cats.stream().sorted(Comparator.comparingDouble(task::getAverageLevel).reversed()).collect(Collectors.toList());

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.printf("| # | %-10s | %-3s | %-5s | %-5s | %-5s | %-15s |\n", "Имя", "Возраст", "Здоровье", "Настроение", "Сытость", "Средний уровень");
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < sorted.size(); i++) {
            task c = sorted.get(i);
            String marker = c.isActionDoneToday() ? "* " : "  ";
            System.out.printf("|%s%d| %-10s | %-7d | %-8d | %-10d | %-7d | %-15.1f |\n", marker, i + 1, c.getName(), c.getAge(), c.getHealth(), c.getMood(), c.getSatiety(), c.getAverageLevel());
        }
    }

    private void applyAction(String actionType) {
        System.out.print("Введите номер кота: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            List<task> sorted = cats.stream().sorted(Comparator.comparingDouble(task::getAverageLevel).reversed()).collect(Collectors.toList());

            task target = sorted.get(idx);

            if (target.isActionDoneToday()) {
                System.out.println("Кот " + target.getName() + " уже совершил действие сегодня!");
                return;
            }

            int up = (target.getAge() <= 5) ? 7 : (target.getAge() <= 10) ? 5 : 4;
            int down = (target.getAge() <= 5) ? 3 : (target.getAge() <= 10) ? 5 : 6;

            switch (actionType) {
                case "1" -> {
                    target.updateSatiety(up);
                    target.updateMood(up);
                    System.out.println("Вы покормили кота — " + target.getName());
                }
                case "2" -> {
                    target.updateMood(up);
                    target.updateHealth(up);
                    target.updateSatiety(-down);
                    System.out.println("Вы поиграли с котом — " + target.getName());
                }
                case "3" -> {
                    target.updateHealth(up);
                    target.updateSatiety(-down);
                    target.updateMood(-down);
                    System.out.println("Вы погуляли с котом — " + target.getName());
                }
            }
            target.setActionDoneToday(true);

            checkCemetery();

        } catch (Exception e) {
            System.out.println("Ошибка выбора кота!");
        }
    }

    private void nextDay() {
        cats.forEach(c -> {
            c.updateSatiety(-(random.nextInt(5) + 1));
            c.updateMood(random.nextInt(7) - 3);
            c.updateHealth(random.nextInt(7) - 3);
            c.setActionDoneToday(false);
        });

        checkCemetery();
        System.out.println("Прошли сутки... Наступил новый день.");
    }

    private void checkCemetery() {
        cats.stream().filter(task::isDead).forEach(c -> System.out.println("К сожалению, кот " + c.getName() + " умер."));

        cats.removeIf(task::isDead);
    }

    private void addNewCat() {
        try {
            System.out.print("Имя кота: ");
            String name = scanner.nextLine();

            System.out.print("Возраст (1-18): ");
            int age = Integer.parseInt(scanner.nextLine());

            if (age < 1 || age > 18) {
                System.out.println("Ошибка: возраст кота должен быть от 1 до 18 лет!");
                return;
            }

            task newCat = new task(name, age);
            cats.add(newCat);
            System.out.println("Вы добавли нового кота — " + name + " (" + age + " лет)");

        } catch (Exception e) {
            System.out.println("Ошибка: некорректный формат данных при добавлении кота!");
        }
    }

}