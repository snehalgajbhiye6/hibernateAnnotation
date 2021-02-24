package com.app;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Test {
	public static SessionFactory  factory() {
		Properties pr=new Properties();
		pr.setProperty("hibernate.connectiont.driver_class", "com.mysql.cj.jdbc.Driver");
		pr.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/person");
		pr.setProperty("hibernate.connection.username", "root");
		pr.setProperty("hibernate.connection.password", "root");
		pr.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		Configuration con=new Configuration();
		con.addAnnotatedClass(Person.class);
		con.setProperties(pr);
		
		StandardServiceRegistryBuilder builder=new StandardServiceRegistryBuilder();
		builder.applySettings(con.getProperties());
		ServiceRegistry sr=builder.build();
		return con.buildSessionFactory(sr);
	}
	
	public void save()
	{
		Session session=factory().openSession();
		Person per=new Person();
		per.setName("snehal");
		per.setAge(24);
		
		session.save(per);
		session.beginTransaction().commit();
		
	}
	
	public void update()
	{
		Session s=factory().openSession();
		Person p=(Person)s.get(Person.class, 1);
		p.setName("deepali");
		s.update(p);
		s.beginTransaction().commit();
		//count not apply on null values.
	}

	@SuppressWarnings("unchecked")
	public void selectAll()
	{
		factory().openSession().createCriteria(Person.class).list().forEach(System.out::println);
	}
	
	@SuppressWarnings("unchecked")
	public void findAllEx()
	{
	   factory().openSession().getNamedQuery("findAll").list().forEach(System.out::println);
	   System.out.println("_____________________________________________");
		factory().openSession().getNamedQuery("findByName").setParameter("ename","snehal").list().forEach(System.out::println);
		System.out.println("_____________________________________________");
		factory().openSession().getNamedQuery("findById").setParameter("eid",3).list().forEach(System.out::println);
		System.out.println("_____________________________________________");
		factory().openSession().getNamedQuery("findByAge").setParameter("eage",20).list().forEach(System.out::println);


	}
	
	public static void main(String[] args) {
		Test test=new Test();
		//test.save();
        //test.update();
		//test.selectAll();
		test.findAllEx();
	}

}
