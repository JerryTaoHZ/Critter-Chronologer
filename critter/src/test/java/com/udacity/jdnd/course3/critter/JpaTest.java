package com.udacity.jdnd.course3.critter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class JpaTest {

//    private static SessionFactory factory;

//    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.get(Calendar.DAY_OF_WEEK);
//    }

//    @Test
//    public void testSession() {
//        Session session = factory.openSession();
//        session.persist();
//    }

    @Test
    public void testDateToWeek() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek);
    }
}
