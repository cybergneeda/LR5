package com.example;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Book {
    private String title;
   private int pubYear;
   ArrayList<String> authors;
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
