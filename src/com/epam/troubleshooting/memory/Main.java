package com.epam.troubleshooting.memory;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/Task #5 - Data.txt"));
            for(String line; (line = br.readLine()) != null; ) {
                // Every line from file is read to new String object. Lines in file are 167 or 132 characters long.
                // When substring method is called on a line
                // then new String object is returned filled with reference to source string (char[] 167 or 132 characters long)
                // but with the offset set to 0 and count set to 4 :
                // new String(offset + beginIndex, endIndex - beginIndex, value);
                // This will also stop original string to be garbage collected in case if it doesn't have any live references
                // So instead of storing very small substrings we store the large string every time but with a different offset and length.

                // Memory leak occurs because a lot of objects are not garbage collected.

//                lines.add(line.substring(0, 4));

                // One of the solutions is to trim string returned by substring method to 3 characters using constructor
                // (to preserve more memory clean)
                lines.add(new String(line.substring(0, 4)));

                // Another way to solve this problem is to call intern() method on substring
                // which will than fetch an existing string from pool or add it if necessary.
                // Since the String in the pool is a real string it only take space as much it requires.

                // One more way to fix the problem is to use JDK 1.7.0_06:
                // there the String returned by String.substring will no longer share the same char[].

            }

            System.in.read();
        } catch (IOException e) {
        }

        // Attached are Leak Suspects reports before and after the memory leak fix respectively
    }
}
