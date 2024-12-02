package com.sung.jpaexs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long id;
	
	private String name;
	
	// 종속 관계 One:부모, Many:자식
	// The following example will cascade the remove operation to the orphaned order entity
	// when the customer entitiy is deleted:
	@OneToMany(cascade = CascadeType.ALL,
				mappedBy = "customer",
				orphanRemoval = true)
	private Set<Order> orders = new HashSet<>();
	
	public void addOrder(Order order) {
		order.setCustomer(this);
		orders.add(order);
	}
	
	public void removeOrder(Order order) {
		orders.remove(order);
		order.setCustomer(null);
	}
	
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

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
