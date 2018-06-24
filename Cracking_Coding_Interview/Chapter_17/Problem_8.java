import java.util.*;

public class Problem_8 {
  private static class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person person_1, Person person_2) {
      return person_1.height != person_2.height ? person_2.height - person_1.height :
        person_2.weight - person_1.weight;
    }
  }

  private static class Person {
    int height;
    int weight;

    public Person(int height, int weight) {
      this.height = height;
      this.weight = weight;
    }

    public boolean can_be_above(Person person) {
      return this.height < person.height && this.weight < person.weight;
    }

    @Override
    public String toString() {
      return String.format("Height = %5d; Weight = %5d", height, weight);
    }
  }

  public static void main(String[] args) {
    Person[] all_persons_1 = {
      new Person(180, 70), new Person(147, 73), new Person(163, 60), new Person(190, 80), new Person(200, 80),
      new Person(140, 40), new Person(300, 120), new Person(139, 54), new Person(190, 79), new Person(138, 89),
      new Person(193, 67), new Person(177, 89), new Person(183, 85), new Person(186, 93), new Person(132, 90),
      new Person(178, 76), new Person(127, 43), new Person(119, 59), new Person(190, 71), new Person(199, 95),
      new Person(95, 25), new Person(128, 31), new Person(141, 37), new Person(150, 43), new Person(159, 47),
    };

    List<Person> tallest_tower_1 = find_tallest_tower_1(all_persons_1);
    List<Person> tallest_tower_2 = find_tallest_tower_2(all_persons_1);

    System.out.println("Tallest people's tower:");
    System.out.println("Method 1:");
    for (int i = 0; i < tallest_tower_1.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, tallest_tower_1.get(i));
    }
    System.out.println();

    System.out.println("Method 2:");
    for (int i = 0; i < tallest_tower_2.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, tallest_tower_2.get(i));
    }
    System.out.println();
  }

  private static List<Person> find_tallest_tower_1(Person[] all_persons) {
    Arrays.sort(all_persons, new PersonComparator());
    List<List<Person>> longest_decreasing_sequence_at_index = new ArrayList<>();

    for (int i = 0; i < all_persons.length; i++) {
      List<Person> longest_seq = new ArrayList<>();
      for (int j = 0; j < i; j++) {
        List<Person> longest_seq_at_j = longest_decreasing_sequence_at_index.get(j);
        Person last_person_in_seq = longest_seq_at_j.get(longest_seq_at_j.size() - 1);
        if (all_persons[i].can_be_above(last_person_in_seq) && longest_seq_at_j.size() > longest_seq.size()) {
          longest_seq = longest_seq_at_j;
        }
      }

      List<Person> new_seq = new ArrayList<>(longest_seq);
      new_seq.add(all_persons[i]);
      longest_decreasing_sequence_at_index.add(new_seq);
    }

    List<Person> longest_seq = new ArrayList<>();
    for (List<Person> seq : longest_decreasing_sequence_at_index) {
      if (longest_seq.size() < seq.size()) {
        longest_seq = seq;
      }
    }
    return longest_seq;
  }

  private static List<Person> find_tallest_tower_2(Person[] all_persons) {
    Arrays.sort(all_persons, new PersonComparator());
    List<List<Person>> all_sequences = new ArrayList<>();
    find_tallest_tower_2(all_persons, 0, all_sequences);

    List<Person> longest_seq = new ArrayList<>();
    for (List<Person> seq : all_sequences) {
      if (longest_seq.size() < seq.size()) {
        longest_seq = seq;
      }
    }
    return longest_seq; 
  }

  private static void find_tallest_tower_2(Person[] all_persons, int index, List<List<Person>> all_sequences) {
    if (index >= all_persons.length) {
      return;
    }

    List<Person> longest_seq = new ArrayList<>();
    for (int i = 0; i < index; i++) {
      List<Person> longest_seq_at_i = all_sequences.get(i);
      Person last_person_in_seq = longest_seq_at_i.get(longest_seq_at_i.size() - 1);
      if (all_persons[index].can_be_above(last_person_in_seq) && longest_seq_at_i.size() > longest_seq.size()) {
        longest_seq = longest_seq_at_i;
      }
    }

    List<Person> new_seq = new ArrayList<>(longest_seq);
    new_seq.add(all_persons[index]);
    all_sequences.add(new_seq);
    find_tallest_tower_2(all_persons, index + 1, all_sequences);
  }
}