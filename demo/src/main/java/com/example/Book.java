package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.var;

@Data
@AllArgsConstructor
public class Book {
   private String title;
   private int pubYear;
   ArrayList<String> authors;
   public static void main(String[] args) throws IOException
   {
    System.out.print("\033[H\033[2J");
    ArrayList<Book> books=new ArrayList<>();
   File file = new File("Books.txt");
   if (!file.exists()) {
    System.err.printf("File %s doesn't exist\n", file.getPath());
   }
   else
   {
    try (var reader = new BufferedReader(new FileReader(file)))
    {
        String stringToSplit = new String();
        stringToSplit= reader.readLine();
        while (stringToSplit!=null) {
            String[] arr= stringToSplit.split(", ");
            arr[0]=arr[0].substring(1, arr[0].length()-1);
            ArrayList<String> theseAuthors = new ArrayList<>();
            for (int j = 2; j < arr.length; j++) {
                theseAuthors.add(arr[j]);
            }
            books.add(new Book(arr[0],Integer.parseInt(arr[1]),theseAuthors));
            stringToSplit=reader.readLine();
        }
    }
   }

File file1=new File("result.txt");
if (file1.exists())
{
    System.err.printf("File %s already exists\n", file1.getPath());
    file1.delete();
}
else {
    try (var writer = new BufferedWriter(new FileWriter(file1)))
    {
int earliestPublishedBookIndex=0;
int earliestPubYear=books.get(earliestPublishedBookIndex).pubYear;

for (int i=0;i<books.size();i++) {
    if (books.get(i).pubYear<earliestPubYear)
    {
        earliestPubYear=books.get(i).pubYear;
        earliestPublishedBookIndex=i;
    }
}
System.out.println("Книга, изданная раньше всех:\n"+books.get(earliestPublishedBookIndex)+"\n");
writer.write("Книга, изданная раньше всех:\n"+books.get(earliestPublishedBookIndex)+"\n");

System.out.println("Книги, написанные одним человеком: ");
Iterator<Book> bookIterator=books.iterator();
while (bookIterator.hasNext()) {
    Book thisBook=bookIterator.next();
    Iterator<String> authorsIterator=thisBook.authors.iterator();
    authorsIterator.next();
    if(!authorsIterator.hasNext())
    {
        System.out.println(thisBook);     
    }
}

Map<String,Integer> map=new HashMap<>();
for (int i = 0; i < books.size(); i++) {
    for (int j = 0; j < books.get(i).authors.size(); j++) {
        if (!map.containsKey(books.get(i).authors.get(j)))
    {
        map.put(books.get(i).authors.get(j), 1);
    }
    else
    {
        map.put(books.get(i).authors.get(j), map.put(books.get(i).authors.get(j), 0)+1);
    }
    }
}

int maxAutorship=0;
String maxAuthor=null;

for(var m:map.entrySet())
{
    if (m.getValue()>maxAutorship) {
        maxAutorship=m.getValue();
        maxAuthor=m.getKey();
    }
}
System.out.println("\nАвтор, участвовавший в написании наибольшего числа ("+ maxAutorship+") книг: "+maxAuthor);
writer.write("\nАвтор, участвовавший в написании наибольшего числа ("+ maxAutorship+") книг: "+maxAuthor);

HashSet<String> coAuthors=new HashSet<>();
for (int i = 0; i < books.size(); i++) {
    for (int j = 0; j < books.get(i).authors.size(); j++) {
        if (books.get(i).authors.get(j)==maxAuthor)
        {
            for (int k = 0; k < books.get(i).authors.size(); k++) {
                if (books.get(i).authors.get(k)!=maxAuthor)
                {
                    coAuthors.add(books.get(i).authors.get(k));
                }
            }
        }
    }
}
System.out.println("\nСоавторы автора, участвоваiего в написании наибольшего числа книг: "+coAuthors);
writer.write("\nСоавторы автора, участвовашего в написании наибольшего числа книг: "+coAuthors);
}
}
}

@Override
public String toString() {
    if(authors.size()==1)
    {
    return "Название: " + title + ", год издания: " + pubYear + ", автор: " + authors;
    }
    else
    {
        return "Название: " + title + ", год издания: " + pubYear + ", авторы: " + authors;
    }
}

   
}