import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Suppose we abstract our file system by a string in the following manner:

  The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

  dir
      subdir1
      subdir2
          file.ext

  The directory dir contains an empty sub-directory subdir1 and a sub-directory 
  subdir2 containing a file file.ext.

  The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" 
  represents:

  dir
      subdir1
          file1.ext
          subsubdir1
      subdir2
          subsubdir2
              file2.ext

  The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file 
  file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level 
  sub-directory subsubdir2 containing a file file2.ext.

  We are interested in finding the longest (number of characters) absolute path to a file within
  our file system. For example, in the second example above, the longest absolute path is 
  "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

  Given a string representing the file system in the above format, return the length of the longest 
  absolute path to file in the abstracted file system. If there is no file in the system, return 0.

  Note:
  The name of a file contains at least a . and an extension.
  The name of a directory or sub-directory will not contain a ..

  Time complexity required: O(n) where n is the size of the input string.
  Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path 
  aaaaaaaaaaaaaaaaaaaaa/sth.png.
*/

public class Longest_Absolute_File_Path {
  private static final String LINE_DELIMITER = "\\\\n";
  private static final String FILE_DELIMITER = "\t";

  private static class Path_Depth {
    int level;
    int length;

    public Path_Depth(int level, int length) {
      this.level = level;
      this.length = length;
    }
  }

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

          System.out.println("Given path = " + path);

          long start = System.currentTimeMillis();
          int max_length_longest_abs_file_path_1 = find_longest_absolute_file_path_1(path);          
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Length of longest absolute file path:");
          System.out.println("METHOD 1: " + max_length_longest_abs_file_path_1 
              + ", execution time = " + execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_longest_absolute_file_path_1(String line) {
    System.out.println("Line length = " + line.length());
    if (line.isEmpty()) {
      return 0;
    }

    int max_length = 0;
    String[] path_segments = line.split(LINE_DELIMITER);
    System.out.println("path_segments.length = " + path_segments.length);
    LinkedList<Path_Depth> stack = new LinkedList<>();

    for (String segment : path_segments) {
      System.out.println("segment = " + segment);
      int j = 0;
      int num_of_tab = 0;
      while (j < segment.length()) {
        // System.out.println(segment.substring(j, j+2));
        if (segment.substring(j, j + 2).equals(FILE_DELIMITER)) {
          j += 2;
          num_of_tab++;
        } else {
          break;
        }
      }

      while (!stack.isEmpty() && num_of_tab <= stack.getFirst().level) {
        stack.removeFirst();
      }

      if (segment.contains(".")) {
        max_length = Math.max(max_length, segment.length() 
          + (stack.isEmpty() ? 0 : stack.getFirst().length) - num_of_tab);
      } else {
        stack.push(new Path_Depth(num_of_tab, segment.length() 
          + (stack.isEmpty() ? 0 : stack.getFirst().length) - num_of_tab + 1));
      }
    }

    return max_length;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    return line;
  }

  private static String remove_extra_spaces_1(String str) {
    str = str.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || Character.isSpace(str.charAt(i + 1))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i < str.length() - 1) {
          new_str.append(" ");
        }
      }
    }

    return new_str.toString();
  }
}