package com.hib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "book")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "book_id")
    private String id;

    @Column(name = "book_name")
    private String name;



    @OneToMany(mappedBy = "b", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = false)
    private List<B_A> Author_student = new ArrayList<>();

    public List<B_A> getAuthor_student() {
        return Author_student;
    }

    public void setAuthor_student(List<B_A> ts) {
        this.Author_student = ts;
    }

    public void addTeacher_student(B_A ts) {
        this.Author_student.add(ts);
    }






    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book student = (Book) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}