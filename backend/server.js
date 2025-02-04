// server.js
const express = require('express');
const mysql = require('mysql');
const cors = require('cors');
require('dotenv').config();

const app = express();
app.use(cors({ origin: 'http://localhost:3000' })); 
app.use(express.json());

const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
});

db.connect(err => {
    if (err) throw err;
    console.log('MySQL Connected...');
});

// Get all recipes
app.get('/api/recipes', (req, res) => {
    db.query('SELECT * FROM recipes', (err, results) => {
        if (err) {
            console.error('Database query error:', err); // Log the error details
            return res.status(500).send(err);
        }
        res.json(results);
    });
});

app.post('/api/recipes', (req, res) => {
    const { name, description, ingredients, instructions } = req.body;
    console.log('Received data:', req.body); // Log received data

    if (!name || !description || !ingredients || !instructions) {
        return res.status(400).json({ message: 'All fields are required' });
    }

    const query = 'INSERT INTO recipes (name, description, ingredients, instructions) VALUES (?, ?, ?, ?)';
    db.query(query, [name, description, ingredients, instructions], (err, results) => {
        if (err) {
            console.error('Error inserting recipe:', err); // Log database error
            return res.status(500).send(err);
        }
        res.status(201).json({ message: 'Recipe added successfully!', recipeId: results.insertId });
    });
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
