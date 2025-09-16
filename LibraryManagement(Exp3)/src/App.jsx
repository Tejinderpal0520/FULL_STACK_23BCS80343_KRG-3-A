import React, { useState, useEffect } from 'react';

function App() {
  const [books, setBooks] = useState([]);
  const [formData, setFormData] = useState({ title: '', author: '' });
  const [searchTerm, setSearchTerm] = useState('');
  const [editingBookId, setEditingBookId] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    fetch('http://localhost:3001/books')
      .then(res => res.json())
      .then(data => {
        setBooks(data);
        setLoading(false);
      });
  }, []);

  const handleChange = e => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (editingBookId) {
      fetch(`http://localhost:3001/books/${editingBookId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      })
        .then(res => res.json())
        .then(updatedBook => {
          setBooks(prevBooks =>
            prevBooks.map(book =>
              book.id === editingBookId ? updatedBook : book
            )
          );
          setEditingBookId(null);
          setFormData({ title: '', author: '' });
        });
    } else {
      fetch('http://localhost:3001/books', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      })
        .then(res => res.json())
        .then(newBook => {
          setBooks(prevBooks => [...prevBooks, newBook]);
          setFormData({ title: '', author: '' });
        });
    }
  };

  const handleEdit = book => {
    setEditingBookId(book.id);
    setFormData({ title: book.title, author: book.author });
  };

  const handleDelete = id => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      fetch(`http://localhost:3001/books/${id}`, {
        method: 'DELETE',
      }).then(() => {
        setBooks(prevBooks => prevBooks.filter(book => book.id !== id));
      });
    }
  };

  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="app-container">
      <h2>Library Management</h2>

      <form onSubmit={handleSubmit} className="book-form">
        <input
          name="title"
          placeholder="Title"
          value={formData.title}
          onChange={handleChange}
          required
        />
        <input
          name="author"
          placeholder="Author"
          value={formData.author}
          onChange={handleChange}
          required
        />
        <button type="submit">{editingBookId ? 'Update' : 'Add'} Book</button>
      </form>

      <input
        placeholder="Search by title..."
        value={searchTerm}
        onChange={e => setSearchTerm(e.target.value)}
        className="search-input"
      />

      {loading && <p>Loading books...</p>}

      <ul className="book-list">
        {filteredBooks.map(book => (
          <li key={book.id} className="book-item">
            <div className="book-info">
              <strong>{book.title}</strong> by {book.author}
            </div>
            <div className="book-actions">
              <button onClick={() => handleEdit(book)} className="edit-btn">Edit</button>
              <button onClick={() => handleDelete(book.id)} className="delete-btn">Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
