package com.hib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ab")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class B_A {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ab_b")
    private Book b;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ab_a")
    private Author a;

    @Override
    public String toString() {
        return "Teacher_Student{" +
                "id='" + id + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        B_A that = (B_A) o;
        return Objects.equals(id, that.id) && Objects.equals(b, that.b) && Objects.equals(a, that.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, a, b);
    }
}
