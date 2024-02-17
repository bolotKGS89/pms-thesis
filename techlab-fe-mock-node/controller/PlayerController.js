// controllers/visaController.js
import express from 'express';
import config from '../config.js';
import axios from 'axios';
import { v4 } from 'uuid';
const router = express.Router();

const requestHeaders = {
  'X-Request-ID' : v4().toString(),
  'Service-Name' : 'techlab-fe-mock-node'
}

const createPlayer = (req, res) => {
  axios.post(`${config.ugoUrl}`, req.body, {
    headers: requestHeaders
  })
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
  }
  
const retrievePlayer = (req, res) => {
      // Use req.params.id to access the id parameter
    const { id } = req.params;

    axios.get(`${config.ugoUrl}/${id}`,{
      headers: requestHeaders
    })
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const updatePlayer = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.put(`${config.ugoUrl}/${id}`, req.body, {
    headers: requestHeaders
  })
  .then(response => {
    console.log('Response:', response.data);
    res.send(response.data);
  })
  .catch(error => {
    console.error('Error:', error.message);
    res.send(error.message);
  });
}

const deletePlayer = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.delete(`${config.ugoUrl}/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    console.log('Response:', response.data);
    res.send(response.data);
  })
  .catch(error => {
    console.error('Error:', error.message);
    res.send(error.message);
  });
}

// Define routes within the controller
router.post("/api/player/create", createPlayer);

router.get("/api/player/retrieve/:id", retrievePlayer);

router.put("/api/player/update/:id", updatePlayer);

router.delete("/api/player/delete/:id", deletePlayer);

export default router;