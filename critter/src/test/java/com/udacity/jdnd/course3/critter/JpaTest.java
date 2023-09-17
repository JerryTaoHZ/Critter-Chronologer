package com.udacity.jdnd.course3.critter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class JpaTest {

    private static SessionFactory factory;

    @Test
    public void testSession() {
        Session session = factory.openSession();
//        session.persist();
    }
}
