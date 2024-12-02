package com.sung.entity;

import javax.persistence.*;

import static java.lang.System.out;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
	
	@Id
	@GeneratedValue // (strategy = GenerationType.IDENTITY) 
					// : IDENTITY 전략을 사용하면 SUB Class의 테이블을 생성할 수 없음
					// :  Cannot use identity column key generation with <union-subclass> mapping for: com.sung.entity.Movieat org.hibernate.persister.entity.UnionSubclassEntityPersister.
					// -> Item.class로 EntityManger.find를 호출할 경우,
					//    오로지 하위 클래스들을 구별할 방법은 primary key 밖에 없기 때문에,
					//    테이블 당 자동 생성 프라이머리 키를 사용하면 안된다.
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	
	private int price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public static void main(String[] args) {

	}

}
