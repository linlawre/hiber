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

@Table(name = "author")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @Column(name = "author_id")
    private String id;

    @Column(name = "author_name")
    private String name;

    @OneToMany(mappedBy = "a", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<B_A> Author_student = new ArrayList<>();

    public List<B_A> getAuthor_student() {
        return Author_student;
    }

    public void setAuthor_student(List<B_A> teacher_students) {
        this.Author_student = teacher_students;
    }

    public void addAuthor_student(B_A ts) {
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
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

