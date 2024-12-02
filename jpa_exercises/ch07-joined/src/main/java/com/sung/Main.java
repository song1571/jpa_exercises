package com.sung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.sung.entity.Album;
import com.sung.entity.Book;
import com.sung.entity.Item;
import com.sung.entity.Movie;

public class Main {
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Album album = new Album();
			album.setName("haris");
			album.setPrice(15000);
			album.setArtist("swift");
			em.persist(album);
			
			Book book = new Book();
			book.setName("haris");
			book.setPrice(15000);
			book.setAuthor("kim");
			book.setIsbn("12345");
			em.persist(book);
			
			Movie movie = new Movie();
			movie.setName("haris");
			movie.setPrice(15000);
			movie.setAuthor("jungjae");
			movie.setDirector("son");
			em.persist(movie);
			
			em.flush();
			em.clear();
			
//			Book gotBook = em.find(Book.class, book.getId());
//			Album gotalbum = em.find(Album.class, album.getId());
			Item item = em.find(Item.class, book.getId()); // union all 쿼리 수행...
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
