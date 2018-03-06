import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem Description:
  Given an absolute path for a file (Unix-style), simplify it.
  For example,
  path = "/home/", => "/home"
  path = "/a/./b/../../c/", => "/c"
  path = "/../", => "/"
  path = "/home//foo/", => "/home/foo"
*/

public class Simplify_Path {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String path = "";
          if (line.indexOf("Customized:") >= 0) {
            path = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("Original path: " + path);
          String simplified_path_1 = simplify_path_1(path);

          System.out.println("Simplified path:");
          System.out.println("SOLUTION 1: " + simplified_path_1);
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static String simplify_path_1(String path) {
    int end_index = path.length() - 1;
    while (end_index >= 0 && path.charAt(end_index) == '/') {
      end_index--;
    }

    LinkedList<String> queue = new LinkedList<>();
    int start = 0;
    for (int i = 1; i <= end_index; i++) {
      if (path.charAt(i) == '/') {
        queue.addLast(path.substring(start, i));
        start = i;
      } else if (i == end_index) {
        queue.addLast(path.substring(start, end_index + 1));
      }
    }

    List<String> parts_of_simplified_path = new ArrayList<>();
    while (!queue.isEmpty()) {
      String first = queue.removeFirst();
      if (first.equals("/") || first.equals("/.")) {
        continue;
      } else if (first.equals("/..")) {
        if (!parts_of_simplified_path.isEmpty()) {
          parts_of_simplified_path.remove(parts_of_simplified_path.size() - 1);
        }
      } else {
        parts_of_simplified_path.add(first);
      }
    }

    if (parts_of_simplified_path.isEmpty()) {
      return "/";
    }

    StringBuilder simplified_path = new StringBuilder();
    for (String part : parts_of_simplified_path) {
      simplified_path.append(part);
    }
    return simplified_path.toString();
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }
}