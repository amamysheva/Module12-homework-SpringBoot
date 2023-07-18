package springbootcore;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoteService {
    private static final List<Note> listNote = new ArrayList<>();

    public List<Note> listAll() {
        return listNote;
    }

    public Note add(Note note) {
        note.setId(generateId());
        listNote.add(note);
        return note;
    }

    private long generateId() {
        long id = new SecureRandom().nextLong(500);
        boolean isIdMatch = listNote.stream()
                .map(Note::getId)
                .anyMatch(noteId -> noteId == id);
        return isIdMatch ? generateId() : id;
    }

    public void deleteById(long id) {
        listNote.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .ifPresentOrElse(listNote::remove, () -> {throw new NoSuchElementException();});
    }

    public void update(Note note) {
        listNote.stream()
                .filter(noteToUpdate -> note.getId() == noteToUpdate.getId())
                .findFirst()
                .ifPresentOrElse(noteToUpdate ->
                {
                    noteToUpdate.setContent(note.getContent());
                    noteToUpdate.setTitle(note.getTitle());
                }, () -> {throw new NoSuchElementException();});
    }

    public Note getById(long id) {
        return listNote.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
