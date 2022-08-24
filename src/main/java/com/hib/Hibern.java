package com.hib;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.Session;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class Hibern {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Zxc11223344";
    static final String QUERY = "SELECT * FROM test.book";


    private DataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("Zxc11223344");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/test");

        return mysqlDataSource;
    }

    private Properties getProperties() {

        final Properties properties = new Properties();
        properties.put( "hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect" );
        properties.put( "hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver" );

        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com/hib");
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties );
        em.setPersistenceUnitName( "demo-unit" );
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    public static void main(String[] args) {

        Hibern jpaDemo = new Hibern();
        DataSource dataSource = jpaDemo.getDataSource();
        Properties properties = jpaDemo.getProperties();

        EntityManagerFactory entityManagerFactory = jpaDemo.entityManagerFactory(dataSource, properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();

        update(em);
    }
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================

    private static void insertToBook(EntityManager em) {
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();
            Query query = em.createNativeQuery("INSERT INTO test.book (book_id, book_name) VALUES (?, ?)");
            query.setParameter(1, 19);
            query.setParameter(2, "Jerry");
            query.executeUpdate();
            tx.commit();
        }
        catch(Exception e) {
            System.out.println("Error");
            tx.rollback();
        }

        em.close();

    }

    private static void getBookById(EntityManager em) {
        List<Object[]> s = em.createNativeQuery("SELECT test.book.book_name b, test.author.author_name a FROM test.ab INNER JOIN test.book on book_id = test.ab.ab_b INNER JOIN test.author on author_id = test.ab.ab_a" ).getResultList();
        System.out.println("Book" + "      " + "Author");
        for(Object[] a : s)
            System.out.println(a[0] + "          " + a[1]);
    }


    private static void addToJunctionTable(EntityManager em) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Query query = em.createNativeQuery("INSERT INTO ab (num, ab_a, ab_b) VALUES (?, ?, ?)");
            query.setParameter(1, 100);
            query.setParameter(2, 100);
            query.setParameter(3, 100);
            query.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            System.out.println("Error");
            tx.rollback();
        }

        em.close();

    }



    private static void notOrphanRemoveBiRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<Object[]> h = em.createNativeQuery("SELECT * FROM test.book b left join test.ab a on b.book_id = a.ab_b Where b.book_id = 2" ).getResultList();

            Book s = (Book) h;

            Author t = em.find(Author.class, "3");


            List<B_A> author_student = new ArrayList<>();
            for (B_A ts : s.getAuthor_student()) {
                if (ts.getA().getId().equals(t.getId())) {
                    author_student.add(ts);
                    em.remove(ts);
                }
            }

            s.getAuthor_student().removeAll(author_student);
            t.getAuthor_student().removeAll(author_student);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }

        em.close();

    }

    private static void remove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();
            Query query = em.createNativeQuery("DELETE FROM test.book WHERE book_id = ?");
            query.setParameter(1, 18);

            query.executeUpdate();
            tx.commit();
        }
        catch(Exception e) {
            System.out.println("Error");
            tx.rollback();
        }

        em.close();

    }


    private static void update(EntityManager em) {
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();
            Query query = em.createNativeQuery("UPDATE test.book SET book_name = ? WHERE book_id = ?");
            query.setParameter(1, "GG");
            query.setParameter(2, 10);
            query.executeUpdate();
            tx.commit();
        }
        catch(Exception e) {
            System.out.println("Error");
            tx.rollback();
        }

        em.close();

    }

    //=========================================================================================================

    private static void getAllBook(EntityManager em) {

        List<Object[]> s = em.createNativeQuery("SELECT * FROM test.book" ).getResultList();

        for(Object[] a : s)
            System.out.println(a[0] + "  " + a[1]);
    }

    private static void getAllAuthor(EntityManager em) {

        List<Object[]> t = em.createNativeQuery("SELECT * FROM test.author" ).getResultList();

        for(Object[] a : t)
            System.out.println(a[0] + "  " + a[1]);
    }


    private static void getAllAB(EntityManager em) {

        List<Object[]> t = em.createNativeQuery("SELECT * FROM test.ab" ).getResultList();

        for(Object[] a : t)
            System.out.println(a[0] + "  " + a[1] + "  " + a[2]);
    }

    private static void test(EntityManager em) {

//        List<Object[]> t = em.createNativeQuery("SELECT * FROM test.book b left join test.ab a on b.book_id = a.ab_b Where b.book_id = 2" ).getResultList();
//        Object[] a = t.get(0);
//        Book b = (Book) a;
//
//        System.out.println(a[0] + "  " + a[1] + "  " + a[2]);
//

        Query query = em.createQuery("select s from Book s left join fetch s.Author_student ts where s.id = ?1");
        query.setParameter(1, "3");
        Book s = (Book)query.getSingleResult();
        System.out.println(s);

    }
}