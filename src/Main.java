import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream<Person> underage = persons.stream();
        Stream<Person> recruit = persons.stream();
        Stream<Person> workable = persons.stream();

        Long underages = underage.filter(u -> u.getAge() < 18).count();

        List<String> recruits = recruit
                .filter(r -> r.getSex() == Sex.MAN)
                .filter(r -> r.getAge() >= 18)
                .filter(r -> r.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        List<Person> workables = workable
                .filter(w -> w.getEducation() == Education.HIGHER)
                .filter(e -> e.getSex() == Sex.MAN
                        ? e.getAge() >= 18 && e.getAge() <= 65
                        : e.getAge() >= 18 && e.getAge() <= 60)
                .sorted(Comparator.comparing(Person::getFamily, Comparator.naturalOrder()))
                .collect(Collectors.toList());

        System.out.println("В лондоне проживают:\n" + underages + " - несовершеннолетних\n" +
                recruits.size() + " - мужчин призывного возраста\n" + workables.size()
                + " - потенциально работоспособных людей");

    }
}
