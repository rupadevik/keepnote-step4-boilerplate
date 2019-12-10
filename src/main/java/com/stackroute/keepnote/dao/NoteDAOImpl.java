package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {
	

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
@Autowired
private SessionFactory sessionFactory;
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		sessionFactory.getCurrentSession().save(note);
		return true;


	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		Session session =sessionFactory.getCurrentSession();
		Note note=session.byId(Note.class).load(noteId);
		if(note == null) {
			return false;
		}
		session.delete(note);
		return true;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		Session session = sessionFactory.getCurrentSession();
	      CriteriaBuilder cb = session.getCriteriaBuilder();
	      CriteriaQuery<Note> cq = cb.createQuery(Note.class);
	      Root<Note>root =cq.from(Note.class);
	      cq.select(root);
	      Query query= session.createQuery(cq);
		return query.getResultList();

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Session session =sessionFactory.getCurrentSession();
		Note note=session.byId(Note.class).load(noteId);
		if(note == null) {
			throw new NoteNotFoundException("Note is not found"+ noteId);
		}
		return note;


	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		sessionFactory.getCurrentSession().update(note);		
		return true;


	}

}
