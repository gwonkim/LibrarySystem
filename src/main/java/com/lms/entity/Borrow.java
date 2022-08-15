package com.lms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.Data;

@Data
@Entity
public class Borrow {
	
	Date borrow_date;
	Date return_date;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    
    @ManyToOne
    @JoinColumn(name = "state_id")
    State state;
}
