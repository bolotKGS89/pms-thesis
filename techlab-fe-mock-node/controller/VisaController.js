// controllers/visaController.js
import express from 'express';
import config from '../config.js';
import axios from 'axios';
const router = express.Router();

const createPayment = (req, res) => {
  axios.post(`${config.ewalletUrl}/create`, req.body)
  .then(response => {
    console.log('Response:', response.data);
    res.send(response.data);
  })
  .catch(error => {
    console.error('Error:', error.message);
    res.send(error.message);
  });
}

const retrievePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  

  axios.get(`${config.ewalletUrl}/retrieve/visa/${id}`)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const confirmPayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  

  axios.get(`${config.ewalletUrl}/confirm/visa/${id}`)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const refundPayment = (req, res) => {

  axios.post(`${config.ewalletUrl}/refund`, req.body)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const capturePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  

  axios.get(`${config.ewalletUrl}/capture/visa/${id}`)
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
router.post("/api/visa/create", createPayment);

router.get("/api/visa/retrieve/:id", retrievePayment);

router.get("/api/visa/confirm/:id", confirmPayment);

router.post("/api/visa/refund", refundPayment);

router.get("/api/visa/capture/:id", capturePayment);



export default router;