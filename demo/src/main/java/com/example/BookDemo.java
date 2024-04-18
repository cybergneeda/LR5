package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class BookDemo {
   
public static void main(String[] args) throws IOException
{
    System.out.print("\033[H\033[2J");
    ArrayList<Book> books=readFile("Books.txt");
    System.out.println(findEarliestPublishedBook(books));
    System.out.println("Книги, написанные одним человеком: ");
    iteratorDemo(books);
    String[] mapDemoResult=mapDemo(books);
    System.out.println("\nАвтор, участвовавший в написании наибольшего числа ("+ mapDemoResult[2]+") книг: "+mapDemoResult[1]);
    System.out.println(hashSetDemo(books, mapDemoResult[1]));
    File file1=new File("result.txt");
    if (file1.exists())
    {
        System.err.printf("File %s already exists\n", file1.getPath());
        file1.delete();
    }
    else {
        try (var writer = new BufferedWriter(new FileWriter(file1)))
            {
            writer.write(findEarliestPublishedBook(books));
            writer.write("\nАвтор, участвовавший в написании наибольшего числа ("+ mapDemoResult[2]+") книг: "+mapDemoResult[1]);
            writer.write(hashSetDemo(books, mapDemoResult[1]));
            }}}

public static ArrayList<Book> readFile(String pathname) throws FileNotFoundException, IOException
{
    ArrayList<Book> books=new ArrayList<>();
    File file = new File(pathname);
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
    }}}
    return books;
}

public static String findEarliestPublishedBook(ArrayList<Book> books)
{
    int earliestPublishedBookIndex=0;
    int earliestPubYear=books.get(earliestPublishedBookIndex).getPubYear();
    
    for (int i=0;i<books.size();i++) {
        if (books.get(i).getPubYear()<earliestPubYear)
        {
            earliestPubYear=books.get(i).getPubYear();
            earliestPublishedBookIndex=i;
        }}
    return "Книга, изданная раньше всех:\n"+books.get(earliestPublishedBookIndex)+"\n";
}

public static void iteratorDemo(ArrayList<Book> books)
{
    Iterator<Book> bookIterator=books.iterator();
    while (bookIterator.hasNext()) {
        Book thisBook=bookIterator.next();
        Iterator<String> authorsIterator=thisBook.authors.iterator();
        authorsIterator.next();
        if(!authorsIterator.hasNext())
        {
            System.out.println(thisBook);     
        }}}

public static String[] mapDemo(ArrayList<Book> books)
{
    String maxAuthor=null;
    int maxAutorship=0;
    Map<String,Integer> map=new HashMap<>();
    for (Book book:books) {
        for (String author:book.getAuthors()) {
            if (!map.containsKey(author))
        {
            map.put(author, 1);
        }
        else
        {
            map.put(author, map.put(author, 0)+1);
        }}}
    for(var m:map.entrySet())
    {
        if (m.getValue()>maxAutorship) {
            maxAutorship=m.getValue();
            maxAuthor=m.getKey();
        }}
    String[] result = new String[3];
    result[0]="\nАвтор, участвовавший в написании наибольшего числа ("+ maxAutorship+") книг: "+maxAuthor;
    result[1]=maxAuthor;
    result[2]=Integer.toString(maxAutorship);
    return result;
}

public static String hashSetDemo(ArrayList<Book> books,String maxAuthor)
{
    HashSet<String> coAuthors=new HashSet<>();
    for (Book book:books) {
        for (String author:book.getAuthors()) {
            if (author==maxAuthor)
            {
                for (String otherAuthor:book.getAuthors()) {
                    if (otherAuthor!=maxAuthor)
                    {
                        coAuthors.add(otherAuthor);
                    }}}}}
    return "\nСоавторы автора, участвовавшего в написании наибольшего числа книг: "+coAuthors;
}}